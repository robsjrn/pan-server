/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panafrica.umash.endpoint;

import com.panafrica.umash.controllers.IprserrorsJpaController;
import com.panafrica.umash.jms.Producer;
import com.panafrica.umash.model.Audit;
import com.panafrica.umash.model.Beneficiarychangerequest;
import com.panafrica.umash.model.Claims;
import com.panafrica.umash.model.Clients;
import com.panafrica.umash.model.Feedback;
import com.panafrica.umash.model.Iprserrors;
import com.panafrica.umash.model.Mpesatransactions;
import com.panafrica.umash.model.Payments;
import com.panafrica.umash.model.Sms;
import com.panafrica.umash.services.PortalService;
import com.panafrica.umash.services.Registration;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import org.apache.logging.log4j.LogManager;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author User
 */
@Path("/portal")
public class Portal {
    PortalService portalService;
   private static org.apache.logging.log4j.Logger log = LogManager.getLogger(Portal.class);
  static Producer iprs = new Producer() ;
    
     @GET
    @Path("summary")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public String getsummary(@Context HttpHeaders headers) {
      portalService = new PortalService();
        
        return portalService.getSummary();
      }
    
    
    @POST
     @Path("createuser")
     @Consumes(MediaType.APPLICATION_JSON)
     public String createuser(@Context HttpHeaders headers,String userdetails){
        PortalService login = new PortalService(); 
        String token =headers.getRequestHeader("token").get(0);  
        return login.createuser(userdetails,token);
     }
    
    
     @GET
    @Path("newrequests")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public List<Clients> getClientDet(@Context HttpHeaders headers) {
      portalService = new PortalService();
        
        return portalService.getNewRequests();
      }
    
     @GET
    @Path("registeredclients")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public List<Clients> getregisteredclients(@Context HttpHeaders headers) {
      portalService = new PortalService();
        
        return portalService.getregisteredclients();
      }
    
    @GET
    @Path("audittrail")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public List<Audit> getaudittrail(@Context HttpHeaders headers) {
      portalService = new PortalService();
        
        return portalService.getaudits();
      }
    
    
     @GET
    @Path("unpaid")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public List<Clients> getunpaid(@Context HttpHeaders headers) {
      portalService = new PortalService();
        
        return portalService.getUnpaid();
      }
     @GET
    @Path("paid")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public List<Clients> getpaid(@Context HttpHeaders headers) {
      portalService = new PortalService();
        
        return portalService.getSuccessfulRegistrations();
      }
    
     @GET
    @Path("TEuploads")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public List<Clients> getTeUploads(@Context HttpHeaders headers) {
      portalService = new PortalService();
        
        return portalService.getTEUploads();
      }
     @GET
    @Path("sms")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public List<Sms> getSms(@Context HttpHeaders headers) {
      portalService = new PortalService();
        
        return portalService.getSms();
      } 
    
     @GET
    @Path("mpesatrxn")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public List<Mpesatransactions> getMpesatrxn(@Context HttpHeaders headers) {
      portalService = new PortalService();
        
        return portalService.getMpesaTransactions();
      } 
    
       @POST
       @Path("generatefile")
       @Consumes(MediaType.APPLICATION_JSON)
     public String generateUploadfile(String details){
         JSONObject reqObj= new JSONObject() ;
          JSONObject resObj= new JSONObject() ;
        String response;
          JSONParser parser = new JSONParser();
        try {
            reqObj= (JSONObject) parser.parse(details);
            
          String docid= reqObj.get("docid").toString();
          portalService = new PortalService();
          response=portalService.generateTEFile(docid);
            if (response ==null){
                resObj.put("status", 0);
                resObj.put("statusMessage", "Could Not Generate Document");
                response=resObj.toJSONString();
            }else {
                 resObj.put("status", 1);
                resObj.put("file", response);
                response=resObj.toJSONString();
            }
        }catch(Exception ex){
             resObj.put("status", 0);
                resObj.put("statusMessage", "Error Processing Your Request");
                resObj.put("Developer Message", ex.getMessage());
                
                response=resObj.toJSONString();
        }
         return response;
     }   
     
       @POST
       @Path("generatefiles")
       @Consumes(MediaType.APPLICATION_JSON)
     public String generateUploadfiles(String details){
         portalService = new PortalService();
         
         return portalService.TEFile();
     }
     
       @POST
       @Path("process")
       @Consumes(MediaType.APPLICATION_JSON)
     public String process(@Context HttpHeaders headers,String details){
         String token =headers.getRequestHeader("token").get(0); 
         JSONObject reqObj= new JSONObject() ;
          JSONObject resObj= new JSONObject() ;
        String response;
          JSONParser parser = new JSONParser();
        try {
            reqObj= (JSONObject) parser.parse(details);
            portalService = new PortalService();
            
            //System.out.println(reqObj);
            
           response= portalService.ProcessClient(reqObj,token);
        }catch(Exception ex){
                resObj.put("status", 0);
                resObj.put("statusMessage", "Error Processing Your Request");
                resObj.put("Developer Message", ex.getMessage());
                
                response=resObj.toJSONString();
        }
        return response;
     }
     
        @POST
       @Path("iprsprocess")
       @Consumes(MediaType.APPLICATION_JSON)
     public String iprsreprocess(String details){
         
          JSONObject resObj= new JSONObject() ;
          JSONParser parser = new JSONParser();
        try {
           JSONObject  reqObj= (JSONObject) parser.parse(details);      
           IprserrorsJpaController ipjc = new IprserrorsJpaController();
           ipjc.destroy(((Long)reqObj.get("tid")).intValue());
            iprs.sendText(reqObj.get("docid").toString(), reqObj.get("docoption").toString());
            
            resObj.put("status", 1);
            resObj.put("message", "Request Sent to Iprs");
        }catch(Exception ex){
             resObj.put("status", 2);
             resObj.put("message", "Error");
             resObj.put("Developermessage", ex.getMessage());
        }
       return resObj.toString();
     }
     
     
        @POST
       @Path("iprsprocessdelete")
       @Consumes(MediaType.APPLICATION_JSON)
     public String iprsprocessdelete(String details){
         
          JSONObject resObj= new JSONObject() ;
          JSONParser parser = new JSONParser();
        try {
           JSONObject  reqObj= (JSONObject) parser.parse(details);      
           IprserrorsJpaController ipjc = new IprserrorsJpaController();
           ipjc.destroy(((Long)reqObj.get("tid")).intValue());
           
            resObj.put("status", 1);
            resObj.put("message", "Request Deleted");
        }catch(Exception ex){
             resObj.put("status", 2);
             resObj.put("message", "Error");
             resObj.put("Developermessage", ex.getMessage());
        }
       return resObj.toString();
     }
     
     @GET
    @Path("/getfile")
    public Response downloadPdfFile(@QueryParam("fileloc") String fileloc) 
    {
        
           final String file= fileloc;
         
       
        StreamingOutput fileStream =  new StreamingOutput() 
        {
            @Override
            public void write(java.io.OutputStream output) throws IOException, WebApplicationException 
            {
                try
                {
                    java.nio.file.Path path = Paths.get(file);
                    byte[] data = Files.readAllBytes(path);
                    output.write(data);
                    output.flush();
                } 
                catch (Exception e) 
                {
                    throw new WebApplicationException("File Not Found !!");
                }
            }
        };
        return Response
                .ok(fileStream, MediaType.APPLICATION_OCTET_STREAM)
                .header("content-disposition","attachment; filename = file.xls")
                .build();
    }
    
     @GET
    @Path("iprserrors")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public List<Iprserrors> getiprsErros(@Context HttpHeaders headers)  {
        portalService = new PortalService();
        return portalService.getiprsErros();
      }
    @GET
    @Path("beneficiaryAdd")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public List<Beneficiarychangerequest>beneficiaryadd(@Context HttpHeaders headers)  {
        portalService = new PortalService();
        return portalService.getAddBeneficiaryRequest();
      } 
     
     @GET
    @Path("beneficiaryEdit")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public List<Beneficiarychangerequest>beneficiaryEdit(@Context HttpHeaders headers)  {
        portalService = new PortalService();
        return portalService.getEditBeneficiaryRequest();
      } 
    @GET
    @Path("ClaimRequest")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public List<Claims>getClaims(@Context HttpHeaders headers)  {
        portalService = new PortalService();
        return portalService.getClaimsRequest();
      } 
    @GET
    @Path("Mpesatrxn")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public List<Mpesatransactions>getMpesa(@Context HttpHeaders headers)  {
        portalService = new PortalService();
        return portalService.getMpesaTransactions();
      }
     @GET
    @Path("Paymenttrxn")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public List<Payments>getPyments(@Context HttpHeaders headers)  {
        portalService = new PortalService();
        return portalService.getPayments();
      }
    
    @GET
    @Path("Overpayments")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public List<Clients> getOverpayments(@Context HttpHeaders headers) {
      portalService = new PortalService();
        
        return portalService.Overpayments();
      }
    
    @GET
    @Path("Underpayments")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public List<Clients> getUnderPayments(@Context HttpHeaders headers) {
      portalService = new PortalService();
        
        return portalService.getUnderpayments();
      }
    
    @GET
    @Path("feedbacks")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public List<Feedback> feedbacks(@Context HttpHeaders headers) {
      portalService = new PortalService();
        
        return portalService.Feedback();
      }
}
