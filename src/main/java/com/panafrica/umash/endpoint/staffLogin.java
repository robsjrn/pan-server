/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panafrica.umash.endpoint;

import com.panafrica.umash.security.LoginToken;
import com.panafrica.umash.services.LoginService;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


@Path("/Staffauthentication")
public class staffLogin {
     LoginService loginService;
     
     
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({ MediaType.APPLICATION_JSON })
     public Response login(String logindetails){
         JSONObject reqObj= new JSONObject() ;
         JSONObject resObj= new JSONObject() ;
         
          JSONParser parser = new JSONParser();
        try {
            reqObj= (JSONObject) parser.parse(logindetails);
            loginService = new LoginService();
            
            if (loginService.staffExist(reqObj.get("username").toString(), reqObj.get("password").toString())){

                LoginToken logintoken = new LoginToken();
                   org.json.JSONObject obj = new org.json.JSONObject();  
                   obj.put("username", reqObj.get("username").toString());
             String token =logintoken.createJWT(reqObj.get("username").toString(),  obj.toString());
                resObj.put("status", 1);
                resObj.put("token",token);
                
                 return Response.status(Response.Status.OK).entity(resObj.toJSONString()).build(); 
                
            }else {
                resObj.put("status", 2);
                resObj.put("Message","login failed Invalid Credential Passed");
                
                 return Response.status(Response.Status.FORBIDDEN).entity(resObj.toJSONString()).build(); 
            }
            
            
            
            
        } catch (ParseException ex) {
          //  Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            resObj.put("status", 2);
            resObj.put("Developer Message",ex);
             return Response.status(Response.Status.EXPECTATION_FAILED).entity(resObj.toJSONString()).build(); 
        }
         
      
     }
    
}
