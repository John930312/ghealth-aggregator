package com.todaysoft.ghealth.portal.mgmt.facade;

import com.aliyun.oss.OSSClient;
import com.fasterxml.jackson.core.type.TypeReference;
import com.todaysoft.ghealth.base.response.ObjectResponse;
import com.todaysoft.ghealth.base.response.Pager;
import com.todaysoft.ghealth.base.response.PagerResponse;
import com.todaysoft.ghealth.base.response.model.MessageSend;
import com.todaysoft.ghealth.config.AliyunOSSConfig;
import com.todaysoft.ghealth.config.ObjectStorageConfig;
import com.todaysoft.ghealth.config.RootContext;
import com.todaysoft.ghealth.mgmt.request.MaintainMessageSendRequest;
import com.todaysoft.ghealth.mgmt.request.QueryMessageSendRequest;
import com.todaysoft.ghealth.mybatis.model.ManagementAccountDetails;
import com.todaysoft.ghealth.mybatis.searcher.MessageSendSearcher;
import com.todaysoft.ghealth.service.IAccountService;
import com.todaysoft.ghealth.service.IMessageSendService;
import com.todaysoft.ghealth.service.impl.PagerQueryer;
import com.todaysoft.ghealth.service.impl.core.AliyunOSSHandler;
import com.todaysoft.ghealth.service.model.AliyunStorageObject;
import com.todaysoft.ghealth.service.wrapper.MessageSendWrapper;
import com.todaysoft.ghealth.utils.DateOperateUtils;
import com.todaysoft.ghealth.utils.IdGen;
import com.todaysoft.ghealth.utils.JsonUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@Component
public class MessageSendMgmtFacade {
    private static Logger log = LoggerFactory.getLogger(MessageSendMgmtFacade.class);

    @Autowired
    private IAccountService accountService;

    @Autowired
    private IMessageSendService messageSendService;

    @Autowired
    private MessageSendWrapper wrapper;

    @Autowired
    private AliyunOSSConfig config;


    public PagerResponse<MessageSend> pager(@RequestBody QueryMessageSendRequest request)
    {
        int pageNo = null == request.getPageNo() ? 1 : request.getPageNo();
        int pageSize = null == request.getPageSize() ? 10 : request.getPageSize();

        MessageSendSearcher searcher = new MessageSendSearcher();
        BeanUtils.copyProperties(request, searcher, "startTime", "endTime");
        wrapperSearcher(request, searcher);
        PagerQueryer<com.todaysoft.ghealth.mybatis.model.MessageSend> queryer = new PagerQueryer<com.todaysoft.ghealth.mybatis.model.MessageSend>(messageSendService);
        Pager<com.todaysoft.ghealth.mybatis.model.MessageSend> pager = queryer.query(searcher, pageNo, pageSize);
        Pager<MessageSend> result = Pager.generate(pager.getPageNo(), pager.getPageSize(), pager.getTotalCount(), wrapper.wrap(pager.getRecords()));
        return new PagerResponse<MessageSend>(result);
    }

    private String transferLongToDate(String dateFormat, Long millSec)
    {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        Date date = new Date(millSec);
        return sdf.format(date);
    }

    private void wrapperSearcher(QueryMessageSendRequest request, MessageSendSearcher searcher)
    {
        if (StringUtils.isNotEmpty(request.getEndTime()))
        {
            searcher.setEndTime(transferLongToDate("yyyy-MM-dd 23:59:59", Long.valueOf(request.getEndTime())));
        }
        if (StringUtils.isNotEmpty(request.getStartTime()))
        {
            searcher.setStartTime(transferLongToDate("yyyy-MM-dd 00:00:00", Long.valueOf(request.getStartTime())));
        }
    }


    public void create(MaintainMessageSendRequest request)
    {
        ManagementAccountDetails account = accountService.getManagementAccountDetails(request.getToken());

        if (null == account)
        {
            throw new IllegalStateException();
        }
        RequestAttributes attributes = RequestContextHolder.currentRequestAttributes();
        HttpServletRequest requests = ((ServletRequestAttributes)attributes).getRequest();
        String rootPath = requests.getServletContext().getRealPath("/");
        String path=null;
        if(request.getFileByte().length >0){
            File file  = new File(rootPath+request.getPath());
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
                    path = "notice/" + DateFormatUtils.format(date, "yyyyMM") + "/"  + file.getName();

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
        com.todaysoft.ghealth.mybatis.model.MessageSend messageSend = new com.todaysoft.ghealth.mybatis.model.MessageSend();
        messageSend.setId(IdGen.uuid());
        messageSend.setTitle(request.getTitle());
        messageSend.setContent(request.getContent());
        if (StringUtils.isNotEmpty(request.getPath())){
            messageSend.setAddress(path);
        }
        messageSend.setPushTime(new Date());
        messageSendService.create(messageSend);


        String[] agencyIds = request.getAgencyId().split(",");
        for (String agenctId : agencyIds){
            com.todaysoft.ghealth.mybatis.model.MessageSendAgency messageSendAgency = new  com.todaysoft.ghealth.mybatis.model.MessageSendAgency();
            messageSendAgency.setId(IdGen.uuid());
            messageSendAgency.setMessageSendId(messageSend.getId());
            messageSendAgency.setAgencyId(agenctId);
            messageSendService.createMessageAgency(messageSendAgency);
        }

    }


    public ObjectResponse<MessageSend> get(MaintainMessageSendRequest request)
    {
        com.todaysoft.ghealth.mybatis.model.MessageSend messageSend = messageSendService.get(request.getId());
        if (StringUtils.isNotEmpty(messageSend.getAddress())){
            String storageDetails = messageSend.getAddress();
            Map<String, String> attributes = JsonUtils.fromJson(storageDetails, new TypeReference<Map<String, String>>()
            {
            });

            OSSClient client = new OSSClient(config.getEndpoint(), config.getAccessKeyId(), config.getAccessKeySecret());
            URL url = client.generatePresignedUrl(attributes.get("bucketName"), attributes.get("objectKey"), DateOperateUtils.addOneSecond(new Date(), 60 * 3));
            String ur1 = url.toString();
            messageSend.setUrl(ur1);
        }




        return new ObjectResponse<MessageSend>(wrapper.wrap(messageSend));
    }


    public ObjectResponse<Boolean> isTitleUnique(MaintainMessageSendRequest request)
    {
        boolean unique = messageSendService.isTitleUnique(request.getTitle());
        return new ObjectResponse<Boolean>(unique);
    }

    public void modify(MaintainMessageSendRequest request)
    {
        com.todaysoft.ghealth.mybatis.model.MessageSend messageSend = messageSendService.get(request.getId());
        messageSend.setTitle(request.getTitle());
        messageSend.setContent(request.getContent());
        messageSend.setPushTime(new Date());
        messageSendService.modify(messageSend);
    }



    public void delete(MaintainMessageSendRequest request)
    {
        ManagementAccountDetails account = accountService.getManagementAccountDetails(request.getToken());

        if (null == account)
        {
            throw new IllegalStateException();
        }

        com.todaysoft.ghealth.mybatis.model.MessageSend messageSend = messageSendService.get(request.getId());
        if (StringUtils.isNotEmpty(messageSend.getAddress())){
            String storageDetails = messageSend.getAddress();
            Map<String, String> attributes = JsonUtils.fromJson(storageDetails, new TypeReference<Map<String, String>>()
            {
            });
            OSSClient client = new OSSClient(config.getEndpoint(), config.getAccessKeyId(), config.getAccessKeySecret());
            client.deleteObject(attributes.get("bucketName"), attributes.get("objectKey"));
        }
        messageSendService.delete(request.getId());
        messageSendService.deleteById(request.getId());
    }

}
