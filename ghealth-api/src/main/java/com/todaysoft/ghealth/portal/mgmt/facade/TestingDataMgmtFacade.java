package com.todaysoft.ghealth.portal.mgmt.facade;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URL;
import java.text.MessageFormat;
import java.util.*;
import java.util.stream.Collectors;

import com.aliyun.oss.OSSClient;
import com.fasterxml.jackson.core.type.TypeReference;
import com.todaysoft.ghealth.config.AliyunOSSConfig;
import com.todaysoft.ghealth.config.ObjectStorageConfig;
import com.todaysoft.ghealth.config.RootContext;
import com.todaysoft.ghealth.mybatis.model.*;
import com.todaysoft.ghealth.service.IOrderService;
import com.todaysoft.ghealth.service.impl.core.AliyunOSSHandler;
import com.todaysoft.ghealth.service.model.AliyunStorageObject;
import com.todaysoft.ghealth.utils.DateOperateUtils;
import com.todaysoft.ghealth.utils.JsonUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;

import com.todaysoft.ghealth.base.request.QueryDetailsRequest;
import com.todaysoft.ghealth.base.response.ObjectResponse;
import com.todaysoft.ghealth.base.response.Pager;
import com.todaysoft.ghealth.base.response.PagerResponse;
import com.todaysoft.ghealth.base.response.model.TestingDataUploadRecordDTO;
import com.todaysoft.ghealth.mgmt.model.OrderTestingDataDTO;
import com.todaysoft.ghealth.mgmt.request.QueryTestingDataUploadRecordsRequest;
import com.todaysoft.ghealth.mgmt.request.TestingDataUploadRequest;
import com.todaysoft.ghealth.mybatis.searcher.TestingDataUploadRecordSearcher;
import com.todaysoft.ghealth.portal.mgmt.MgmtErrorCode;
import com.todaysoft.ghealth.portal.mgmt.facade.wrapper.TestingDataUploadRecordWrapper;
import com.todaysoft.ghealth.service.IAccountService;
import com.todaysoft.ghealth.service.ITestingDataService;
import com.todaysoft.ghealth.service.impl.PagerQueryer;
import com.todaysoft.ghealth.service.impl.ServiceException;
import com.todaysoft.ghealth.service.wrapper.PagerWrapper;
import com.todaysoft.ghealth.utils.IdGen;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Component
public class TestingDataMgmtFacade
{
    private static Logger log = LoggerFactory.getLogger(TestingDataMgmtFacade.class);

    @Autowired
    private IAccountService accountService;
    
    @Autowired
    private ITestingDataService testingDataService;
    
    @Autowired
    private TestingDataUploadRecordWrapper wrapper;

    @Autowired
    private IOrderService orderService;

    @Autowired
    private AliyunOSSConfig config;
    
    public PagerResponse<TestingDataUploadRecordDTO> pager(@RequestBody QueryTestingDataUploadRecordsRequest request)
    {
        int pageNo = null == request.getPageNo() ? 1 : request.getPageNo();
        int pageSize = null == request.getPageSize() ? 10 : request.getPageSize();
        
        TestingDataUploadRecordSearcher searcher = new TestingDataUploadRecordSearcher();
        searcher.setUploaderName(request.getUploaderName());
        
        if (null != request.getUploadTimeStart())
        {
            Date start = new Date(request.getUploadTimeStart());
            searcher.setUploadTimeStart(DateUtils.truncate(start, Calendar.DATE));
        }
        
        if (null != request.getUploadTimeEnd())
        {
            Date end = new Date(request.getUploadTimeEnd());
            searcher.setUploadTimeEnd(DateUtils.addDays(DateUtils.truncate(end, Calendar.DATE), 1));
        }
        
        PagerQueryer<TestingDataUploadRecord> queryer = new PagerQueryer<TestingDataUploadRecord>(testingDataService);
        Pager<TestingDataUploadRecord> pager = queryer.query(searcher, pageNo, pageSize);
        return new PagerResponse<TestingDataUploadRecordDTO>(new PagerWrapper<TestingDataUploadRecord, TestingDataUploadRecordDTO>(wrapper).wrap(pager));
    }
    
    public ObjectResponse<TestingDataUploadRecordDTO> get(@RequestBody QueryDetailsRequest request)
    {
        if (StringUtils.isEmpty(request.getId()))
        {
            throw new ServiceException(MgmtErrorCode.API_ILLEGAL_ARGUMENT);
        }
        
        TestingDataUploadRecord entity = testingDataService.get(request.getId());

        if (StringUtils.isNotEmpty(entity.getDownloadUrl())){
            String storageDetails = entity.getDownloadUrl();
            Map<String, String> attributes = JsonUtils.fromJson(storageDetails, new TypeReference<Map<String, String>>()
            {
            });

            OSSClient client = new OSSClient(config.getEndpoint(), config.getAccessKeyId(), config.getAccessKeySecret());
            URL url = client.generatePresignedUrl(attributes.get("bucketName"), attributes.get("objectKey"), DateOperateUtils.addOneSecond(new Date(), 60 * 3));
            String ur1 = url.toString();
            entity.setDownloadUrl(ur1);
        }

        return new ObjectResponse<TestingDataUploadRecordDTO>(wrapper.wrap(entity));
    }
    
    public Map<String,String> upload(TestingDataUploadRequest request)
    {
        ManagementAccountDetails account = accountService.getManagementAccountDetails(request.getToken());
        
        if (null == account)
        {
            throw new IllegalStateException();
        }

        //上传文件至OSS 并生成path
        RequestAttributes attributes = RequestContextHolder.currentRequestAttributes();
        HttpServletRequest requests = ((ServletRequestAttributes)attributes).getRequest();
        String rootPath = requests.getServletContext().getRealPath("/");
        Date timestamp = new Date();
        String suffix = request.getFilename().substring(request.getFilename().lastIndexOf("."));
        String filename = MessageFormat.format("{0}_{1}", request.getFilename().replaceAll(suffix, ""), DateFormatUtils.format(timestamp, "yyyyMMddHHmmss"));
        String path=null;
        if(request.getFileByte().length >0){
            File file  = new File(rootPath+filename);
            if(file.exists()){
                file.delete();
            }
            try {
                FileOutputStream fos = new FileOutputStream(file);
                fos.write(request.getFileByte(),0,request.getFileByte().length);
                fos.flush();
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            ObjectStorageConfig osc = RootContext.getBean(ObjectStorageConfig.class);

            AliyunOSSConfig config = RootContext.getBean(AliyunOSSConfig.class);
            AliyunOSSHandler handler = RootContext.getBean(AliyunOSSHandler.class);
            try
            {
                Date date = new Date();
                String directory = config.getDirectoryName();
                path = "notice/" + DateFormatUtils.format(date, "yyyyMM") + "/"  + file.getName() + suffix;

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

                AliyunStorageObject object = new AliyunStorageObject(config.getEndpoint(), config.getBucketName(), objectKey);

                path=object.toObjectStorageRecord().getStorageDetails();

                // 上传至OSS成功后删除本地文件
                FileUtils.deleteQuietly(file);
            }
            catch (Exception e)
            {
                log.error(e.getMessage(), e);
            }

        }
        
        TestingDataUploadRecord uploadRecord = new TestingDataUploadRecord();
        uploadRecord.setId(IdGen.uuid());
        uploadRecord.setTitle(request.getTitle());
        uploadRecord.setFilename(request.getFilename());
        if (StringUtils.isNotEmpty(request.getFilename())){
            uploadRecord.setDownloadUrl(path);
        }

        uploadRecord.setUploaderName(account.getName());
        uploadRecord.setUploadTime(new Date());
        
        OrderTestingData testingData;
        List<OrderTestingData> testingDatas = new ArrayList<OrderTestingData>();
        
        if (!CollectionUtils.isEmpty(request.getOrderGenetypes()))
        {
            for (OrderTestingDataDTO orderGenetypes : request.getOrderGenetypes())
            {
                testingData = new OrderTestingData();
                testingData.setId(IdGen.uuid());
                testingData.setOrderId(orderGenetypes.getOrderId());
                testingData.setUploadRecordId(uploadRecord.getId());
                testingData.setDetails(JsonUtils.toJson(orderGenetypes.getGenetypes()));
//                testingData.setFlag(false);
//
//                Order order = orderService.getOrderById(orderGenetypes.getOrderId());
//                List<String> loci = orderService.getLoci(orderGenetypes.getOrderId());
//                List<String> requestLoci = orderGenetypes.getGenetypes().stream().map(i->i.getLocus()).collect(Collectors.toList());
//                if (order.getProduct().getCode().equals("WGS002"))
//                {
//                    for(String requestLocus : requestLoci)
//                    {
//                        if (loci.contains(requestLocus))
//                        {
//                            testingData.setFlag(true);
//                            break;
//                        }
//                    }
//                }else
//                {
//                    if (requestLoci.containsAll(loci))
//                    {
//                        testingData.setFlag(true);
//                    }
//                }
                testingDatas.add(testingData);
            }
        }
        
       return testingDataService.upload(uploadRecord, testingDatas);
    }
}
