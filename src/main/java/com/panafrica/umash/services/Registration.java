/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panafrica.umash.services;

import com.panafrica.umash.controllers.BeneficiariesJpaController;
import com.panafrica.umash.controllers.ChildrensJpaController;
import com.panafrica.umash.controllers.ClientsJpaController;
import com.panafrica.umash.controllers.ClientspoductsJpaController;
import com.panafrica.umash.controllers.IprserrorsJpaController;
import com.panafrica.umash.controllers.ParentsJpaController;
import com.panafrica.umash.controllers.SpouseJpaController;
import com.panafrica.umash.jms.Producer;
import com.panafrica.umash.model.Beneficiaries;
import com.panafrica.umash.model.Childrens;
import com.panafrica.umash.model.Clients;
import com.panafrica.umash.model.Clientspoducts;
import com.panafrica.umash.model.Parents;
import com.panafrica.umash.model.Spouse;
import com.panafrica.umash.sms.SmsService;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author User
 */
public class Registration {
    ClientsJpaController clientsJpaController;
    ChildrensJpaController childrensJpaController;
    ParentsJpaController parentsJpaController;
    BeneficiariesJpaController beneficiariesJpaController;
    SmsService smsService;
    SpouseJpaController spouseJpaController;
    ClientspoductsJpaController clientspoductsJpaController;
    IprserrorsJpaController iprserrorsJpaController;
  private static org.apache.logging.log4j.Logger log = LogManager.getLogger(Registration.class);
  static Producer iprs = new Producer() ;
  
    public String generateInvoice(){
        return "";
    }
    
    public String SaveDetails(String details) throws Exception{
          JSONObject obj;
          String resp="";
           clientsJpaController = new ClientsJpaController();
              Clients client; 
     try{    
          JSONParser parser = new JSONParser();
         obj= (JSONObject) parser.parse(details);
         
         
      
      List<Clients>  cl=clientsJpaController.findClients(obj.get("idnumber").toString());
       System.out.println("Client Size " + cl.size() );
          if (cl ==null || cl.size()==0){
               
             resp= processClient(obj);
          }
          else {
             if ((boolean)obj.get("agreed")){
               smsService = new SmsService();
               String num = "254"+obj.get("contact").toString().substring(1);
               String msg="";
               boolean umashexist=false;
               boolean cashplanhexist=false;
               Integer sa=0;
               clientspoductsJpaController = new ClientspoductsJpaController();
                List<Clientspoducts> cps =clientspoductsJpaController.getClientProducts(obj.get("idnumber").toString());
                Iterator it =  cps.iterator();
                System.out.println("Product  Size " + cps.size() );
                   
                
                 while (it.hasNext()) {
                    Clientspoducts cp =(Clientspoducts) it.next();
                      System.out.println("Client Product " + cp.getProductname());
                     if (cp.getProductname().equalsIgnoreCase("umash")){
                         sa=sa+cp.getSumassured();
                         umashexist=true;
                     }else {
                         sa=sa+cp.getSumassured();
                         cashplanhexist=true;
                     }
                     
                 }
                 System.out.println("Total Sum Assured " + sa);
                 if (sa >= 600000){
                         msg=" Sorry You Have Reached The Maximum Number of Product (Umash and Cash Plan) That You Can Have  "; 
                       JSONObject resObj= new JSONObject() ;
                        resObj.put("code", 5);
                        resObj.put("Status", "Umash");
                        resObj.put("StatusMessage", msg);
                        resp = resObj.toJSONString();
                         smsService.sendSms(num,msg); 
                   }
                 else if (umashexist && obj.get("productname").toString().equalsIgnoreCase("umash")){
                      msg=" Sorry You Already Have Umash Plan ( limited to One Per Client ). Can We Interest You with  Another of Our Product Called Cash Plan ?";
                     JSONObject resObj= new JSONObject() ;
                        resObj.put("code", 5);
                        resObj.put("Status", "Umash");
                        resObj.put("StatusMessage", msg);
                         resp = resObj.toJSONString();
                          smsService.sendSms(num,msg); 
                  }
                  else if (umashexist && obj.get("productname").toString().equalsIgnoreCase("cash-plan")){
                        if (sa <= 600000){
                           resp=  processClient(obj);
                          // msg= " Thank You For Your Interest In "+obj.get("productname").toString()+" Product .You Request is Being Processed ";
                        } else {
                            msg=" Sorry You Have Reached The Maximum Number of Plans (Umash and Cash Plan) That You Can Have  "; 
                       JSONObject resObj= new JSONObject() ;
                        resObj.put("code", 6);
                        resObj.put("Status", "Umash");
                        resObj.put("StatusMessage", msg);
                         resp = resObj.toJSONString();
                          smsService.sendSms(num,msg); 
                        }
                  }
                 else if (cashplanhexist && obj.get("productname").toString().equalsIgnoreCase("cash-plan")){
                       if (sa <= 400000){
                          resp=  processClient(obj);
                          // msg= " Thank You For Your Interest In "+obj.get("productname").toString()+" Plan .You Request is Being Processed ";
                       }else {
                            msg=" Sorry You Have Reached The Maximum Number of Plans (Umash and Cash Plan) That You Can Have  "; 
                       JSONObject resObj= new JSONObject() ;
                        resObj.put("code", 6);
                        resObj.put("Status", "Umash");
                        resObj.put("StatusMessage", msg);
                        resp = resObj.toJSONString();
                         smsService.sendSms(num,msg); 
                        }
                  }
                  
                 
                
              
             }
          } 
     }catch(Exception ex){
         log.error(ex);
         JSONObject resObj= new JSONObject() ;
         resObj.put("code", 6);
         resObj.put("Status", "Umash");
         resObj.put("StatusMessage", "Sorry We Could Not Process Your Request Kindly Retry Later");
         resp = resObj.toJSONString();
     }    
     return resp;
    }
    
    private String processClient(JSONObject obj) {
          JSONObject resObj= new JSONObject() ;
       
        clientsJpaController = new ClientsJpaController();
        childrensJpaController = new ChildrensJpaController();
        parentsJpaController = new ParentsJpaController();
        beneficiariesJpaController = new BeneficiariesJpaController();
        spouseJpaController = new SpouseJpaController();
        clientspoductsJpaController = new ClientspoductsJpaController();
        iprserrorsJpaController = new IprserrorsJpaController();
      
         Integer parent =0;
         Integer children=0;
         Integer spouse=0;
         String inv="";
         int appid=0;
         
         
        try {     
           
              
         if (checkChildrenDetails(obj)){          
             children=1;
         }
        try {
        if (checkParentDetails(obj)){
             parent =1;
        }}catch(Exception ex){
            log.error(ex);
        }
        try {
          if ((boolean)obj.get("spouseAdded")){
           
            spouse=1;
          
        }}catch(Exception ex){
            log.error(ex);
        }
               appid=clientsJpaController.create(getClientsDetailsFromJson(obj,children,parent,spouse));
                    Clientspoducts clientspoducts = new Clientspoducts();
                         clientspoducts.setClientid(obj.get("idnumber").toString());
                         clientspoducts.setPremiumpayable((Integer.parseInt(obj.get("premiumpayable").toString())));
                         clientspoducts.setSumassured(Integer.parseInt(obj.get("sumassured").toString()));
                         clientspoducts.setProductname(obj.get("productname").toString());
                         clientspoducts.setProductid(appid);
                         clientspoductsJpaController.create(clientspoducts);
                         
             inv=appid+""; 
            if (obj.get("productname").toString().equalsIgnoreCase("umash"))
            { inv = "EU"+inv;
            }
            else if ((obj.get("productname").toString().equalsIgnoreCase("cash-plan"))){
                inv = "EC"+inv;
            }
                resObj.put("invoice", inv);
                resObj.put("code", 1);
                resObj.put("Status", "Data Saved");
                
                
                 beneficiariesJpaController.create(getBeneficiaryFromJson(obj,obj.get("idnumber").toString(),appid));
                 childrensJpaController.Createlist(getChildrenDetailsfromJson(obj,obj.get("idnumber").toString(),appid));
                 parentsJpaController.Createlist(getParentsDetailsFromjson(obj,obj.get("idnumber").toString(),appid));
                 Spouse sp= getSpouseFromJson(obj,obj.get("idnumber").toString(),appid);
                  spouseJpaController.create(sp);
              
                    
                           
                
          
            
             
             if ((boolean)obj.get("agreed")){
        smsService = new SmsService();
        smsService.sendSms("254"+obj.get("contact").toString().substring(1)," Thank You For Your Interest In "+obj.get("productname").toString()+" Product .Your Request is Being Processed ");
   
             }
             
         JSONObject doc = (JSONObject)obj.get("doctype");    
           
             iprs.sendText(obj.get("idnumber").toString(), doc.get("docid").toString());       
          
   }catch(Exception ex){
         log.error(ex);
          resObj.put("code", 2);
          resObj.put("Status", "Error");
          resObj.put("DeveloperMessage", ex);
    
    }
    return resObj.toJSONString();
    }
    private boolean checkParentDetails(JSONObject obj){
        boolean status=false;
        try {
        JSONArray parentArray = (JSONArray)  obj.get("parentdetails");
        if (parentArray.toArray().length > 0){
            status=true;
        }}catch(Exception ex){
            log.error(ex);
        }
        
        return status;
    }
    
    private boolean checkChildrenDetails(JSONObject obj){
        boolean status=false;
     
        try {
        JSONArray childrenArray = (JSONArray)  obj.get("children");
        if (childrenArray.toArray().length > 0){
            status=true;
        }
        }catch(Exception ex){
          log.error(ex);  
        }
        
        return status;
    }
    
    
    private Clients getClientsDetailsFromJson(JSONObject obj,Integer children,Integer parent,Integer spouse){
        
         Clients client= new Clients ();
         Integer statusid=1;
         String statusname="New Request (Pending Iprs Confirmation)";
        try { 
               if (!(boolean)obj.get("agreed")){
                  statusid=9;
                  statusname="Client Disagreed (T and C)";
                  //this means the client disagreed
               }
            
            client.setRid(0);
            client.setPremiumpayable(Integer.parseInt(obj.get("premiumpayable").toString()));
            client.setDocid(obj.get("idnumber").toString());
            client.setContacts(obj.get("contact").toString());
            client.setDob(obj.get("dob").toString());
            client.setPin(obj.get("pin").toString());
            client.setAge(Integer.parseInt(obj.get("age").toString()));
            client.setRegistrationdate(new Date());
            client.setChannelid(1);
            client.setParentAvailable(parent);
            client.setChildrenAvailable(children);
            client.setSpouseAvailable(spouse);
            client.setChannelname("App");
            client.setPaymentstatus(1);
             client.setPaymentDescription("Unpaid");
             client.setSumassured(Integer.parseInt(obj.get("sumassured").toString()));
            client.setStatusid(1);
            client.setStatusname(statusname);
            
              client.setProductname(obj.get("productname").toString());
            
             
                  try {
                      String email ="";
                      email = obj.get("email").toString();
                      client.setEmail(checkString(email));
                  }
                  catch(Exception ex){
                          System.out.println("Email Unavailable ");
                          }
            
           
                     
        }catch(Exception ex){
          log.error(ex);
        }
         return client;
        
    }
    private List<Childrens> getChildrenDetailsfromJson(JSONObject obj,String clientid,Integer productid){
        
        List<Childrens> childrens = new ArrayList<Childrens>();
      JSONArray childrenArray = (JSONArray)  obj.get("children");
      Iterator i = childrenArray.iterator();
      
         while (i.hasNext()) {
             Childrens child = new Childrens();
             try {
             
            JSONObject childobj = (JSONObject) i.next();
              child.setClientid(clientid);
              child.setProductid(productid);
              child.setChildName(checkString((String)childobj.get("childname")));
              child.setChildAge(Integer.parseInt(childobj.get("age").toString()));
              child.setChildDob((String)childobj.get("dob"));
              child.setRid(0);
              
              child.setChildDob(checkString((String)childobj.get("dob")));
             }catch (Exception ex){
                 log.error(ex);
             }
            childrens.add(child);
        }
        
        return childrens;
    }
    
    private List<Parents>  getParentsDetailsFromjson(JSONObject obj,String clientid,Integer productid){
           List<Parents> parents = new ArrayList<Parents>();
     try { 
         JSONArray parentArray = (JSONArray)  obj.get("parentdetails");
      Iterator i = parentArray.iterator();
      
         while (i.hasNext()) {
             Parents parent =new Parents();
             try {
             
            JSONObject parentobj = (JSONObject) i.next();
                    
            
                 parent.setClientid(clientid);
                 parent.setProductid(productid);
                 parent.setParentAge(Integer.parseInt(parentobj.get("age").toString()));
                 parent.setParentDob((String)parentobj.get("dob"));
                 parent.setParentNames((String)parentobj.get("name"));
           
             }
            catch (Exception ex){
                    log.error(ex);
                    }
              parents.add(parent);
        }
     }catch(Exception ex){
         log.error(ex);
     }
        return parents ;
    }
    
    private Beneficiaries getBeneficiaryFromJson(JSONObject obj,String clientid,Integer productid){
        Beneficiaries beneficiarie = new Beneficiaries();
        
        JSONObject benObj = (JSONObject)obj.get("beneficiary");
        try {
            beneficiarie.setClientid(clientid);
            beneficiarie.setProductid(productid);
          beneficiarie.setBeneficiaryNames(checkString((String)benObj.get("name")));
           beneficiarie.setBeneficiaryContacts(checkString((String)benObj.get("contacts")));
           beneficiarie.setBeneficiaryId(checkString((String)benObj.get("id")));
           
           beneficiarie.setBeneficiaryDob(checkString((String)benObj.get("dob")));
           beneficiarie.setBeneficiaryAge(Integer.parseInt(benObj.get("age").toString()));
            beneficiarie.setRid(0);
        }catch (Exception ex){
            log.error(ex);
        }
        return beneficiarie;
    }
    private Spouse getSpouseFromJson(JSONObject obj,String clientid,Integer productid){
        Spouse sp = new Spouse();
          try {
                if ((boolean)obj.get("spouseAdded")){
                 JSONObject spouseObj = (JSONObject)obj.get("spouse");
                   sp.setClientid(clientid);
                   sp.setProductid(productid);
                   sp.setSpousename(checkString(spouseObj.get("name").toString()));
                   sp.setSpouseid(checkString(spouseObj.get("id").toString()));
                   sp.setSpousephone(checkString(spouseObj.get("phonenumber").toString()));
                   sp.setSpousedob(checkString(spouseObj.get("dob").toString()));
                   sp.setSpouseage(Integer.parseInt(spouseObj.get("age").toString()));
                   
               }
          }catch(Exception ex){
              log.error(ex);
          }
        
        return sp;
    }
    private String checkString (String str){
        String re="";
        if(str != null && !str.isEmpty()) {
            re=str;
        }
        
        return re;
        
    }
}
