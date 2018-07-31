package com.todaysoft.ghealth.service.sms;

import com.cloopen.rest.sdk.CCPRestSDK;
import org.springframework.stereotype.Component;

/**
 * @Author: xjw
 * @Date: 2018/7/17 9:03
 */
@Component
public class CcpRestApi
{
    private final String SERVER_IP = "app.cloopen.com";
    
    private final String SERVER_PORT = "8883";
    
    private final String ACCOUNT_SID = "8a216da85c62c9ad015caaaa6f0419eb";
    
    private final String ACCOUNT_TOKEN = "e03254f4da9f4ff0945b65eabddefb56";
    
    private final String APP_ID = "8a216da85c62c9ad015caaaa705919f2";
    
    private CCPRestSDK init()
    {
        CCPRestSDK restAPI = new CCPRestSDK();
        // 初始化服务器地址和端口，格式如下，服务器地址不需要写https://
        restAPI.init(SERVER_IP, SERVER_PORT);
        // 初始化主帐号和主帐号TOKEN
        restAPI.setAccount(ACCOUNT_SID, ACCOUNT_TOKEN);
        // 初始化应用ID
        restAPI.setAppId(APP_ID);
        return restAPI;
    }
    
    public void messageSend(String phone, String templateId,String[] agrs)
    {
        init().sendTemplateSMS(phone, templateId, agrs);
    }
}
