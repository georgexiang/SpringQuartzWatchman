/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.websystique.spring.scheduling;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.websystique.spring.AppMain;
import java.text.DecimalFormat;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 *
 * @author gexiang
 */
public class IoTRestAPITest {
    
    
    
    public static void main(String[] args) {
	  IoTRestAPITest  ioTRestAPITest=new IoTRestAPITest();              
              ioTRestAPITest.retriveAlertMsg();   
	}
    
      public  void retriveAlertMsg( ) {
	try {

		Client client = Client.create();

		WebResource webResource = client
		   .resource("https://iotpmjapac1641-seoracletrial13180.iot.us.oraclecloud.com/iot/api/v2/messages?type=alert&limit=2");
                  
//                webResource.header("Authorization", "Basic eXVrdWkuamluQG9yYWNsZS5jb206VGVtcCMxMjM=");
//                webResource.setProperty("Authorization", "Basic eXVrdWkuamluQG9yYWNsZS5jb206VGVtcCMxMjM=");
                
		ClientResponse response = webResource.accept("application/json").header("Authorization", "Basic eXVrdWkuamluQG9yYWNsZS5jb206VGVtcCMxMjM=")
                   .get(ClientResponse.class);

		if (response.getStatus() != 200) {
		   throw new RuntimeException("Failed : HTTP error code : "
			+ response.getStatus());
                   
		}

		String output = response.getEntity(String.class);
               
                 final JSONObject obj = new JSONObject(output);
                 final JSONArray alertData = obj.getJSONArray("items");
                 final int n = alertData.length();
                for (int i = 0; i < n; ++i) {
                   JSONObject alert = alertData.getJSONObject(i);
                   
                   String alertID=alert.getString("id");
                   
                   if(!AppMain.setAlertId.contains(alertID)){
                       AppMain.setAlertId.add(alertID);
                   }else {
                       System.out.println("Alert haven been sended" + alertID );
                       continue; 
                   }
                   
                   JSONObject payload= alert.getJSONObject("payload");
                   String description=payload.getString("description") ;
                   JSONObject data=payload.getJSONObject("data");
                   String msgKey= data.keys().next();

                   DecimalFormat df = new DecimalFormat("#.##");               
//                   data.getDouble(msgKey);
//                   Double  doubleMsgValue = Double.parseDouble( String.valueOf( data.getDouble(msgKey)) );
//                   String   msgValue=  String.valueOf( doubleMsgValue );
                   String   msgValue= String.format("%.2f", data.getDouble(msgKey) )  ;
//                   data.getd
                             
                  System.out.println(alertID);
                  System.out.println(description);
                  System.out.println(msgKey);
                  System.out.println(msgValue);
                  
                  String pushTitle="甲骨文空气质量监控 告警";
//                  甲醛参数超标 1.08 毫克/立方米!
                  String pushDescription=msgKey +" " + description+":" +msgValue ;
                  BaiduPushSender.baiduPushToAll(pushTitle , pushDescription);
                  
                  System.out.println(pushDescription);
                  
                  
                  
                }
//                 System.out.println(alertData);

	  } catch (Exception e) {

		e.printStackTrace();

	  }
    
    }
}
