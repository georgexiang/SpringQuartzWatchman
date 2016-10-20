package com.websystique.spring.scheduling;

import org.springframework.stereotype.Component;

import com.baidu.yun.core.log.YunLogEvent;
import com.baidu.yun.core.log.YunLogHandler;
import com.baidu.yun.push.auth.PushKeyPair;
import com.baidu.yun.push.client.BaiduPushClient;
import com.baidu.yun.push.constants.BaiduPushConstants;
import com.baidu.yun.push.exception.PushClientException;
import com.baidu.yun.push.exception.PushServerException;
import com.baidu.yun.push.model.PushMsgToAllRequest;
import com.baidu.yun.push.model.PushMsgToAllResponse;


@Component("myBean")
public class MyBean {

	public void printMessage() {
		System.out.println("I am called by MethodInvokingJobDetailFactoryBean using SimpleTriggerFactoryBean");
                
                try {
//                    baiduPushToAll();
                    
                     IoTRestAPITest  ioTRestAPITest=new IoTRestAPITest();              
                                     ioTRestAPITest.retriveAlertMsg(); 
                                     
                }catch (Exception e){
                    e.printStackTrace();
                }
	}
        
        
        
        
        
        
	
}
