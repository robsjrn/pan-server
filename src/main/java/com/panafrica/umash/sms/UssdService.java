/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panafrica.umash.sms;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.simple.JSONObject;

/**
 *
 * @author User
 */
public class UssdService {
 public void  sendMpesaRequest(String phone,String amount,String virtualaccount){ 
     CloseableHttpResponse response =null;
     System.out.println("Sending ussd request for mpesa");
      try {
    CloseableHttpClient client = HttpClients.createDefault();
    HttpPost httpPost = new HttpPost("http://127.0.0.1:8585/UmashMpesa/umash/MpesaPaymentRequest");
     JSONObject resObj= new JSONObject() ;
          resObj.put("phonenumber", phone);
          resObj.put("amount", amount);
          resObj.put("virtualaccount", virtualaccount);
    String json = resObj.toString();
    StringEntity entity = new StringEntity(json);
    httpPost.setEntity(entity);
    httpPost.setHeader("Accept", "application/json");
    httpPost.setHeader("Content-type", "application/json");
 
     response= client.execute(httpPost);
     System.out.println("Response is " + response);
    
     } catch (IOException ex) {
             ex.printStackTrace();
         } finally {
             if (response != null) {
                 try {
                     response.close();
                 } catch (IOException ex) {
                      ex.printStackTrace();
                 }
             }
         }
 }
    
}
