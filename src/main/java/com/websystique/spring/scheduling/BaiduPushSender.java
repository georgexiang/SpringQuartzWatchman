/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.websystique.spring.scheduling;

import com.baidu.yun.core.log.YunLogEvent;
import com.baidu.yun.core.log.YunLogHandler;
import com.baidu.yun.push.auth.PushKeyPair;
import com.baidu.yun.push.client.BaiduPushClient;
import com.baidu.yun.push.constants.BaiduPushConstants;
import com.baidu.yun.push.exception.PushClientException;
import com.baidu.yun.push.exception.PushServerException;
import com.baidu.yun.push.model.PushMsgToAllRequest;
import com.baidu.yun.push.model.PushMsgToAllResponse;
import com.baidu.yun.push.model.PushMsgToTagRequest;
import com.baidu.yun.push.model.PushMsgToTagResponse;

/**
 *
 * @author gexiang
 */
public class BaiduPushSender {
    
    
    public  static void baiduPushToAll(String title , String  description ) throws PushClientException,PushServerException {
		// 1. get apiKey and secretKey from developer console
		String apiKey = "MdZC4s0eZedaupfd13pSYEwi";
		String secretKey = "904SAMtToaMxcpg7H2vqx7GDit9RtCeF";
		PushKeyPair pair = new PushKeyPair(apiKey, secretKey);

		// 2. build a BaidupushClient object to access released interfaces
		BaiduPushClient pushClient = new BaiduPushClient(pair,
				BaiduPushConstants.CHANNEL_REST_URL);

		// 3. register a YunLogHandler to get detail interacting information
		// in this request.
		pushClient.setChannelLogHandler(new YunLogHandler() {
			@Override
			public void onHandle(YunLogEvent event) {
				System.out.println(event.getMessage());
			}
		});

		try {
			// 4. specify request arguments
//			PushMsgToAllRequest request = new PushMsgToAllRequest()
//					.addMsgExpires(new Integer(3600)).addMessageType(1)
////					.addMessage("111 Hello Air Monitor") //发送消息
//                                        .addMessage("{\"title\":\""+title+"\",\"description\":\""+description+"\"}")
////					.addSendTime(System.currentTimeMillis() / 1000 +120) // 设置定时推送时间，必需超过当前时间一分钟，单位秒.实例2分钟后推送
//					.addDeviceType(3);
//			// 5. http request
//			PushMsgToAllResponse response = pushClient.pushMsgToAll(request);
//			// Http请求结果解析打印
                        
                        // pushTagTpye = 1 for common tag pushing
			PushMsgToTagRequest request = new PushMsgToTagRequest()
					.addTagName("air")
					.addMsgExpires(new Integer(3600))
					.addMessageType(1)  // 添加透传消息
					// .addSendTime(System.currentTimeMillis() / 1000 + 120) //设置定时任务
                                        .addMessage("{\"title\":\""+title+"\",\"description\":\""+description+"\"}")  
//					.addMessage("Hello Baidu push")
					.addDeviceType(3);
			// 5. http request
			PushMsgToTagResponse response = pushClient.pushMsgToTag(request);
                        
			System.out.println("msgId: " + response.getMsgId() + ",sendTime: "
					+ response.getSendTime() + ",timerId: "
					+ response.getTimerId());
                        
		} catch (PushClientException e) {
			if (BaiduPushConstants.ERROROPTTYPE) {
				throw e;
			} else {
				e.printStackTrace();
			}
		} catch (PushServerException e) {
			if (BaiduPushConstants.ERROROPTTYPE) {
				throw e;
			} else {
				System.out.println(String.format(
						"requestId: %d, errorCode: %d, errorMessage: %s",
						e.getRequestId(), e.getErrorCode(), e.getErrorMsg()));
			}
		}
	}
    
}
