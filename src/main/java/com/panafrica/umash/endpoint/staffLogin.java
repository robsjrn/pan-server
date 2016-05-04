/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panafrica.umash.endpoint;

import com.panafrica.umash.services.LoginService;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


@Path("/Staffauthentication")
public class staffLogin {
     LoginService loginService;
     
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
     public String login(String logindetails){
         JSONObject reqObj= new JSONObject() ;
         JSONObject resObj= new JSONObject() ;
         
          JSONParser parser = new JSONParser();
        try {
            reqObj= (JSONObject) parser.parse(logindetails);
            loginService = new LoginService();
            
            if (loginService.staffExist(reqObj.get("username").toString(), reqObj.get("password").toString())){
                resObj.put("status", 1);
                resObj.put("token","tokendetails");
                
            }else {
                resObj.put("status", 2);
                resObj.put("Message","login failed Invalid Credential Passed");
            }
            
            
            
            
        } catch (ParseException ex) {
          //  Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            resObj.put("status", 2);
            resObj.put("Developer Message",ex);
        }
         
         return resObj.toJSONString();
     }
    
}
