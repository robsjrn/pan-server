/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panafrica.umash.Appnotification.gcm;

import java.io.IOException;

/**
 *
 * @author User
 */
public class SendAppNotification {
    
    Sender st = new Sender("AIzaSyAE8c-TEhWfjrUuWELp-hXIog1lwVjNdNI");
    public  String sendMessage(String message,String title,String deviceid) throws IOException{
       String resp="";
        Message msg =
      new Message.Builder()
              .addData("message", message)
              .addData("title", title)
              .addData("msgcnt","1") 
// Shows up in the notification in the status bar when you drag it down by the time
              .addData("soundname","beep.wav")
              .delayWhileIdle(true)
              .timeToLive(3000)
          .build();
   Result result = st.send(msg, deviceid, 4);  
if (result.getMessageId() != null) {
    String canonicalRegId = result.getCanonicalRegistrationId();
    if (canonicalRegId != null) {
     System.out.println("Notification Sent ");
     resp=canonicalRegId;
    }
    
  } else {
    String error = result.getErrorCodeName();
    if (error.equals(Constants.ERROR_NOT_REGISTERED)) {
     System.out.println("Notification Error Not Sent Client not registered");
    }
    resp=error;
  }   
        return resp;
        
    }
    
}
