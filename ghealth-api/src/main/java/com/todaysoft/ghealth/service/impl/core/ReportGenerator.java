package com.todaysoft.ghealth.service.impl.core;

import com.todaysoft.document.generate.sdk.DocumentGenerateSDK;
import com.todaysoft.document.generate.sdk.request.DocumentGenerateContents;
import com.todaysoft.document.generate.sdk.response.TemplateFile;
import com.todaysoft.ghealth.config.AliyunOSSConfig;
import com.todaysoft.ghealth.config.ObjectStorageConfig;
import com.todaysoft.ghealth.config.RootContext;
import com.todaysoft.ghealth.mybatis.mapper.OrderMapper;
import com.todaysoft.ghealth.mybatis.mapper.ReportGenerateTaskMapper;
import com.todaysoft.ghealth.mybatis.mapper.TestingEvaluateMapper;
import com.todaysoft.ghealth.mybatis.model.*;
import com.todaysoft.ghealth.portal.mgmt.facade.report.*;
import com.todaysoft.ghealth.service.IOrderHistoryService;
import com.todaysoft.ghealth.service.impl.ErrorCode;
import com.todaysoft.ghealth.service.impl.ServiceException;
import com.todaysoft.ghealth.service.model.AliyunStorageObject;
import com.todaysoft.ghealth.service.model.LocalStorageObject;
import com.todaysoft.ghealth.service.model.StorageObject;
import com.todaysoft.ghealth.utils.IdGen;
import com.todaysoft.ghealth.utils.ZipUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Component
public class ReportGenerator
{
    private static Logger log = LoggerFactory.getLogger(ReportGenerator.class);
    
    @Autowired
    private OrderMapper orderMapper;
    
    @Autowired
    private TestingEvaluateMapper testingEvaluateMapper;
    
    @Autowired
    private ReportGenerateTaskMapper reportGenerateTaskMapper;
    
    @Autowired
    private DocumentGenerateConfig generateConfig;
    
    @Autowired
    private IOrderHistoryService orderHistoryService;
    
    @Autowired
    private TestingItemEvaluatorRequest testingItemEvaluatorRequest;
    
    @Transactional
    public void generate(ReportGenerateContext context)
    {
        try
        {
            evaluate(context);
            context.setOrderHistories(orderHistoryService.getOrderHistoriesByOrderId(context.getOrder().getId()));
            ReportContents contents = generateContents(context);
            List<File> files = doGenerate(context, contents);
            generateSuccess(context, files, false);
        }
        catch (ServiceException e)
        {
            generateFailure(context.getGenerateKey(), e.getErrorCode(), e.getMessage());
        }
        catch (Exception e)
        {
            log.error("Generate report error.", e);
            
            String message = e.getMessage();
            
            if (StringUtils.isEmpty(message))
            {
                message = "内部错误";
            }
            
            if (message.length() > 20)
            {
                message = message.substring(0, 20);
            }
            
            generateFailure(context.getGenerateKey(), ErrorCode.UNDEFINED_ERROR, message);
        }
    }
    
    private ReportContents generateContents(ReportGenerateContext context)
    {
        List<ReportContentsGenerator> generators = new ArrayList<ReportContentsGenerator>();
        generators.add(new DefaultReportContentGenerator());
        generators.add(new TestingItemContentsGenerator());
        generators.add(new PharmacyReportContentsGenerator());
        generators.add(new TuseContentsGenerator());
        
        ReportContentsGenerator contentsGenerator = new MultiReportContentsGenerator(generators);
        return contentsGenerator.generate(context);
    }
    
    private List<File> doGenerate(ReportGenerateContext context, ReportContents contents) throws Exception
    {
        DocumentGenerateContents generateContents = new DocumentGenerateContents();
        
        if (null != contents)
        {
            generateContents.setTextBookmarkContents(contents.getTextBookmarkContents());
            generateContents.setTableBookmarkContents(contents.getTableBookmarkContents());
            generateContents.setCaseBookmarkContents(contents.getCaseBookmarkContents());
        }
        
        DocumentGenerateSDK sdk = new DocumentGenerateSDK(generateConfig.getEndpoint(), generateConfig.getAccessKeyId(), generateConfig.getAccessKeySecret());
        TemplateFile rsp = sdk.generateDocument(context.getTemplateKey(), generateContents);
        
        if (null == rsp)
        {
            throw new IllegalStateException("生成报告失败");
        }
        
        String destDirectory = context.getDestDirectory();
        File dest = new File(destDirectory);
        
        if (!dest.exists())
        {
            dest.mkdirs();
        }
        
        String timestamp = DateFormatUtils.format(new Date(), "yyyyMMddHHmmss");
        File zip = new File(destDirectory, timestamp + ".zip");
        FileUtils.copyInputStreamToFile(rsp.getContent(), zip);
        List<File> files = ZipUtils.unzip(zip, destDirectory, timestamp);
        
        return files;
    }
    
    private void evaluate(ReportGenerateContext context)
    {
        testingEvaluateMapper.deleteOrderTestingItemEvaluateRecords(context.getOrder().getId());
        testingEvaluateMapper.deleteOrderTestingItemLocusEvaluateRecords(context.getOrder().getId());
        
        List<TestingItemAlgorithmConfig> configs = context.getTestingItemAlgorithmConfigs();
        
        TestingItemEvaluateResult result;
        List<TestingItemEvaluateResult> results = new ArrayList<TestingItemEvaluateResult>();
        for (TestingItemAlgorithmConfig config : configs)
        {
            result = evaluate(config, context);
            results.add(result);
        }
        
        context.setTestingItemEvaluateResults(results);
    }
    
    private TestingItemEvaluateResult evaluate(TestingItemAlgorithmConfig config, ReportGenerateContext context)
    {
        TestingItemEvaluateResult result = testingItemEvaluatorRequest.evaluate(config, context.getGenetypes(), context.getCustomer().getSex());
        
        result.getlocusEvaluateResults().forEach(locusEvaluateResult -> {
            TestingItemLocusEvaluateRecord entity = new TestingItemLocusEvaluateRecord();
            entity.setId(IdGen.uuid());
            entity.setOrderId(context.getOrder().getId());
            entity.setTestingItemId(config.getTestingItem().getId());
            entity.setLocusId(locusEvaluateResult.getLocus().getId());
            entity.setGenetype(locusEvaluateResult.getGenetype());
            entity.setEvaluateValue(locusEvaluateResult.getFactor());
            testingEvaluateMapper.insertTestingItemLocusEvaluateRecord(entity);
        });
        
        TestingItemEvaluateRecord entity = new TestingItemEvaluateRecord();
        entity.setId(IdGen.uuid());
        entity.setOrderId(context.getOrder().getId());
        entity.setTestingItemId(config.getTestingItem().getId());
        entity.setEvaluateValue(result.getTestingItemEvaluateValue());
        testingEvaluateMapper.insertTestingItemEvaluateRecord(entity);
        return result;
    }
    
    public void generateSuccess(ReportGenerateContext context, List<File> files, Boolean isUpload)
    {
        Date timestamp = new Date();
        
        File pdf = null;
        File word = null;
        String filename;
        
        for (File file : files)
        {
            filename = file.getName();
            
            if (filename.contains(".doc") || filename.contains(".docx"))
            {
                word = file;
            }
            else if (filename.contains(".pdf"))
            {
                pdf = file;
            }
        }
        
        ObjectStorageConfig osc = RootContext.getBean(ObjectStorageConfig.class);
        StorageObject pdfStorageObject = storage(context, pdf, osc);
        StorageObject wordStorageObject = storage(context, word, osc);
        //删除父目录
        File parentFile;
        if (Objects.isNull(pdf))
        {
            parentFile = word.getParentFile();
        }
        else
        {
            parentFile = pdf.getParentFile();
        }
        FileUtils.deleteQuietly(parentFile);
        
        ReportGenerateTask task = new ReportGenerateTask();
        task.setId(context.getGenerateKey());
        task.setStatus(ReportGenerateTask.STATUS_SUCCESS);
        task.setFinishTime(timestamp);
        
        if (null != pdfStorageObject)
        {
            ObjectStorage entity = pdfStorageObject.toObjectStorageRecord();
            entity.setId(IdGen.uuid());
            reportGenerateTaskMapper.insertObjectStorageRecord(entity);
            task.setPdfFileUrl(entity.getId());
        }
        
        if (null != wordStorageObject)
        {
            ObjectStorage entity = wordStorageObject.toObjectStorageRecord();
            entity.setId(IdGen.uuid());
            reportGenerateTaskMapper.insertObjectStorageRecord(entity);
            task.setWordFileUrl(entity.getId());
        }
        if (isUpload)
        {
            task.setId(IdGen.uuid());
            task.setCreateTime(new Date());
            reportGenerateTaskMapper.insert(task);
        }
        else
        {
            reportGenerateTaskMapper.update(task);
        }
        
        Order order = context.getOrder();
        order.setReportGenerateTaskId(task.getId());
        order.setReportGenerateTime(timestamp);
        orderMapper.modify(order);
    }
    
    private StorageObject storage(ReportGenerateContext context, File file, ObjectStorageConfig osc)
    {
        if (null == file)
        {
            return null;
        }
        
        StorageObject object = null;
        
        if (osc.isAliyunOSS())
        {
            object = storageAsAliyunOSS(context, file);
        }
        
        if (null == object)
        {
            object = storageAsLocal(context, file);
        }
        
        return object;
    }
    
    private StorageObject storageAsLocal(ReportGenerateContext context, File file)
    {
        String filename = file.getName();
        String fileUri = context.getDestUri() + "/" + filename;
        LocalStorageObject object = new LocalStorageObject(fileUri, file.getAbsolutePath());
        return object;
    }
    
    private StorageObject storageAsAliyunOSS(ReportGenerateContext context, File file)
    {
        AliyunOSSConfig config = RootContext.getBean(AliyunOSSConfig.class);
        AliyunOSSHandler handler = RootContext.getBean(AliyunOSSHandler.class);
        
        try
        {
            Date date = new Date();
            String code = context.getOrder().getCode();
            String directory = config.getDirectoryName();
            String path = "report/" + DateFormatUtils.format(date, "yyyyMM") + "/" + code + "/" + file.getName();
            
            String objectKey;
            
            if (StringUtils.isEmpty(directory))
            {
                objectKey = path;
            }
            else
            {
                if (directory.startsWith("/"))
                {
                    directory = directory.substring(1);
                }
                
                if (StringUtils.isEmpty(directory))
                {
                    objectKey = path;
                }
                else
                {
                    if (!directory.endsWith("/"))
                    {
                        directory += "/";
                    }
                    
                    objectKey = directory + path;
                }
                
            }
            
            handler.upload(objectKey, file);
            
            // 上传至OSS成功后删除本地文件
            FileUtils.deleteQuietly(file);
            AliyunStorageObject object = new AliyunStorageObject(config.getEndpoint(), config.getBucketName(), objectKey);
            return object;
        }
        catch (Exception e)
        {
            log.error(e.getMessage(), e);
            return null;
        }
    }
    
    private void generateFailure(String id, String errorCode, String errorMessage)
    {
        ReportGenerateTask task = new ReportGenerateTask();
        task.setId(id);
        task.setStatus(ReportGenerateTask.STATUS_FAILURE);
        task.setFinishTime(new Date());
        task.setErrorCode(errorCode);
        task.setErrorMessage(errorMessage);
        reportGenerateTaskMapper.update(task);
    }
}
