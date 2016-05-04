/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panafrica.umash.endpoint;

import com.panafrica.umash.controllers.BeneficiarychangerequestJpaController;
import com.panafrica.umash.controllers.ClaimsJpaController;
import com.panafrica.umash.controllers.ClientsJpaController;
import com.panafrica.umash.controllers.UssdinquiriesJpaController;
import com.panafrica.umash.controllers.UssdmpesarequestJpaController;
import com.panafrica.umash.controllers.UssdpasswordresetJpaController;
import com.panafrica.umash.model.Beneficiarychangerequest;
import com.panafrica.umash.model.Claims;
import com.panafrica.umash.model.Clients;
import com.panafrica.umash.model.Ussdinquiries;
import com.panafrica.umash.model.Ussdmpesarequest;
import com.panafrica.umash.model.Ussdpasswordreset;
import com.panafrica.umash.services.SendMail;
import com.panafrica.umash.services.UssdServices;
import com.panafrica.umash.sms.SmsService;
import com.panafrica.umash.sms.UssdService;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import org.apache.logging.log4j.LogManager;

/**
 *
 * @author User
 */


@Path("/ussd")
public class Ussd {
    UssdServices ussdServices;
     ClientsJpaController jp;
    SmsService smsService ;
    
     private static org.apache.logging.log4j.Logger log = LogManager.getLogger(Ussd.class);
    
    @POST
    public Response getSessionDetails(
           MultivaluedMap<String,String> multivaluedMap
    ){
        
        String text=multivaluedMap.getFirst("text");
        String contact=multivaluedMap.getFirst("phoneNumber");
         System.out.println("The Text is " + text + " And Contact "+ contact);
        String resp=null;
        
                    resp=ussdMenu(text,contact);
        
        
        
   
		return Response.status(201).entity(resp).build();
        
    }
    
    private String ussdMenu(String level,String contact){
        String resp=null;
        
         switch(level){
             
             case "" :
                resp= "CON   Pan Africa Life  \n"+
                         " 1. Products    \n"+
                         " 2. Beneficiary \n" +
                         " 3. Enquiry     \n" +
                         " 4. Claim       \n" +
                         " 5. Make Payment( Mpesa Only )\n" +
                         " 6. Forgot Password \n" +
                         " 00.Exit ";
                 
                break;
             case "1"  :
                 
                  resp  = "CON   Products  \n"+
                         " 1. Umash     \n"+
                         " 2. Cash Plan    \n" ;
                  
                  break;
             case "1*1"  : 
                 
                   resp  = "CON   Umash \n"+
                         " Enter Your ID Number      \n";             
                 break ;
                 
            case "1*2"  : 
                 
                    resp  = "CON   Cash plan \n"+
                         " Select Your Sum Assurred      \n"+
                         " 1. 100,000     \n"+
                         " 2. 200,000     \n" ;              
                 break ;     
                 
                 
             case "1*2*1"  : 
                 System.out.println("Customer selected Cash plan sum assured 100000 " + level);
                    resp  = "CON   Cash Plan \n"+
                            " Enter Your ID Number      \n";
                         
                 
                 break ; 
                 
             case "1*2*2"  : 
                 System.out.println("Customer selected Cash plan sum assured 200000 " + level);
                    resp  = "CON   Cash Plan \n"+
                            " Enter Your ID Number      \n";
                         
                 
                 break ;  
                 
              case "2"  :
                 
                  resp  = "CON    Beneficiary   \n"+
                         " Choose     \n"+
                         " 1. To add      \n"+
                         " 2. To Change     \n" ; 
                  
                  break;
                  
                case "2*1"  :
                 
                  resp  = "CON    Beneficiary \n"+
                         " Add Beneficiary   \n"+
                         " Beneficiary Identification \n";
                        
                    
                  
                  break; 
                  
                  case "2*2"  :
                 
                  resp  = "CON    Beneficiary \n"+
                         " Change Beneficiary \n"+
                         " Enter name,Birth date, \n"+
                         " Identification \n" ; 
                  
                  break; 
                  
              case "3"  :
                 
                  resp  = "CON    Enquiry   \n"+
                          " 1.My Policy \n" +   
                          " 2.My Sum Assured    \n" +
                          " 3.My Premium Payable \n" +
                          " 4.My Application Status \n" ;
                  
                  break;  
               case "3*1"  :
                 
                  resp  = "CON    Enquiry \n"+
                          " ID Number ? \n" ;  
                        
                  
                  break; 
                case "3*2"  :
                 
                resp  = "CON    Enquiry \n"+
                          " ID Number ? \n" ;  
                        
                  
                  break;  
                 case "3*3"  :
                 
                  resp  = "CON    Enquiry \n"+
                          " ID Number ? \n" ;  
                         
                        
                  
                  break; 
                  
                  case "3*4"  :
                 
                  resp  = "CON    Enquiry \n"+
                          " ID Number ? \n" ;  
                                           
                  break;    
                  
                 
                  
               case "4"  :
                 
                  resp  = "CON    Claim   \n"+
                          " Enter Policy/Id of Main Member  \n";    
                         
                        
                  
                  break; 
                case "5"  :
                 
                  resp  = "CON  Mpesa Number \n"+
                          " 1.My Number  \n" +
                          " 2.Other Number  \n"
                          ;                    
                  break;
                  
                 case "5*2"  : 
                 
                   resp  = "CON  Mpesa Payment \n "+
                           " Enter Phone Number \n";             
                 break ;  
                 
              case "00"  :
                 
                  resp  = "END   Thank You   \n";
                              
                         
                        
                  
                  break; 
             default :
                 System.out.println("The request is " + level);
                resp= FurtherPrcessing(level,contact);
                 
         }
        
        
        return resp;
                
                
        
    }
    private String FurtherPrcessing(String req,String contact){
        String fp=null;
        
           boolean  umashidnum = req.startsWith("1*1"); 
           
           boolean  cashplan100 = req.startsWith("1*2*1"); 
           
           boolean  cashplan200 = req.startsWith("1*2*2"); 
           
           
            boolean  Selfumashpayment = req.startsWith("5*1"); 
            
            boolean  Otherumashpayment = req.startsWith("5*2"); 
            
             boolean  beneficiarydet = req.startsWith("2*1"); 
              boolean  Editbeneficiarydet = req.startsWith("2*2"); 
             
             boolean  claimrequest = req.startsWith("4"); 
             boolean  inquiryequest = req.startsWith("3*"); 
              boolean  forgotpassword = req.startsWith("6"); 
            
            if (inquiryequest ){
                
                ProcessInquiryrequest(req,contact);
                 fp ="END  Pan Africa Life \n"+
                            " Thank You . \n" +
                            " Your Request is Being \n"+
                            " Processed .You Will Recive \n" +
                            " Sms Shortly With The Details \n" ;
            }
            
            else if (forgotpassword){
              String[] result = req.split(Pattern.quote("*"));  
                   if( result.length == 1) {
                         fp  = "CON    Password Reset \n"+
                               " ID Number ? \n" ; 
                   }else {
                fp ="END  Pan Africa Life  \n"+
                            " Your Password Will .\n" +
                            " Be Reset and Sent To \n"+
                            " You Via Sms \n" ;
                
                passwordreset(req,contact);
                   }          
            }
             
            else if (beneficiarydet ){
                  
                  String[] result = req.split(Pattern.quote("*"));
                  
                    if( result.length < 4 )
                    {
                  
                        fp  = "CON    Beneficiary \n"+
                              " Add Beneficiary   \n"+  
                              " Beneficiary Phone \n";
                         
                    }else {
                       
                         fp ="END  Pan Africa Life \n"+
                            " Thank You .    \n" +
                            " Your Request is Being \n"+
                            " Processed  " ;
                         ProcessBeneficiaryrequest(req,contact,"Add");
                    }
                  
              }
             
           else  if (Editbeneficiarydet){
                  fp ="END  Pan Africa Life  \n"+
                            " Thank You .    \n" +
                            " Your Request is Being \n"+
                            " Processed  " ;
                         ProcessBeneficiaryrequest(req,contact,"Edit");
             }
           
           //here you should check if the user had already input birth date
            else  if (umashidnum){
                  
                  String[] result = req.split(Pattern.quote("*"));
                  
                    if( result.length < 4 ) // means dob is not included ..ask the user for dob
                    {
                  
                        fp  = "CON   Umash \n"+
                          " Enter Your Date of Birth      \n"+ 
                          " dd/mm/yyyy e.g 01/01/1990  \n"; 
                    }else {
                        //dob is included end 
                         fp ="END  Pan Africa Life  \n"+
                            " Thank You .    \n" +
                            " Your Request is Being \n"+
                            " Processed  " ;
                         ProcessUmashrequest(req,contact);
                    }
                  
              }
             else if (cashplan100){
                  
                  String[] result = req.split(Pattern.quote("*"));
                  System.out.println("Cash Plan 100 Length " + result.length);
                    if( result.length > 3 && result.length < 5 ) // means dob is not included ..ask the user for dob
                    {
                  
                        fp  = "CON   Umash \n"+
                          " Enter Your Date of Birth      \n"+ 
                          " dd/mm/yyyy e.g 01/01/1990  \n"; 
                    }else {
                        //dob is included end 
                         fp ="END  Pan Africa Life  \n"+
                            " Thank You .    \n" +
                            " Your Request is Being \n"+
                            " Processed  " ;
                         ProcessCashplanrequest(req,contact,1800);
                    }
                  
              }
             else if (claimrequest ){
                 processclaim(req,contact);
                 fp ="END  Pan Africa Life  \n"+
                            " Thank You .    \n" +
                            " Your Claim Request is Being \n"+
                            " Processed  " ;
             }
              else if (cashplan200){
                  
                  String[] result = req.split(Pattern.quote("*"));
                  System.out.println("Cash Plan 200 Length " + result.length);
                  
                    if( result.length > 3 && result.length < 5 ) // means dob is not included ..ask the user for dob
                    {
                  
                        fp  = "CON   Umash \n"+
                          " Enter Your Date of Birth      \n"+ 
                          " dd/mm/yyyy e.g 01/01/1990  \n"; 
                    }else {
                        //dob is included end 
                         fp ="END  Pan Africa Life  \n"+
                            " Thank You .    \n" +
                            " Your Request is Being \n"+
                            " Processed  " ;
                          ProcessCashplanrequest(req,contact,3600);
                    }
                  
              }
              
              else if (Selfumashpayment){
                   System.out.println("Payment request "+req);
                    String[] result = req.split(Pattern.quote("*"));
                    System.out.println("Result Length  "+result.length);
               
                  if( result.length == 2) 
                    {
                       
                       fp  = "CON   Policy Payment \n"+
                          " Enter Your Invoice Number     \n"+ 
                          " You Recived e.g (EU1)     \n"; 
                  
                  }else {
                     
                      processUmashpayment(req,contact.substring(1),contact.substring(1),result[2] );
                     fp ="END   Thank You. Your  \n"+
                            " Your Request Has Been   \n" +
                            " Sent To Mpesa \n"+
                            " For Processing. " ;
                  }
                  }
              else if (Otherumashpayment){
               
                    String[] result = req.split(Pattern.quote("*"));
                
                 
                       if( result.length ==3) 
                    {
                               System.out.println("Full Request"+req);
                         
                           fp  = "CON   Policy Payment \n"+
                          " Enter Your Invoice Number     \n"+ 
                          " You Recived e.g (EU1)     \n"; 
                       }else {
                      processUmashpayment(req,contact.substring(1),"254"+result[2].substring(1),result[3]);
                         
                      fp ="END   Thank You. Your  \n"+
                            " Your Request Has Been   \n" +
                            " Sent To Mpesa \n"+
                            " For Processing. " ;
                  }
                  
              }
              else {
                   fp ="END   Thank You. Your  \n"+
                            " Your Request Has Been   \n" +
                            " Sent To Mpesa \n"+
                            " For Processing. " ;
              }
        
        return fp;
                
    }
    private void ProcessRequest(String request) {
        System.out.println("determine the level and The request the Customer was in ");
        
        System.out.println(request);
        
       
        
    }
    
    private void ProcessUmashrequest(String request,String contact){
       System.out.println("Processing umash  ");  
        String[] result = request.split(Pattern.quote("*"));
        String docid= result[2]; 
        String dob= result[3];
        ussdServices = new UssdServices();
        ussdServices.SaveussdData(docid, contact, dob, "umash",2400);
        System.out.println(" umash Processed "); 
        
       
       
        
    }
    
     private void ProcessCashplanrequest(String request,String contact,Integer Amt){
       System.out.println("ProcessingCashPlan  ");  
        String[] result = request.split(Pattern.quote("*"));
        String docid= result[3]; 
        String dob= result[4];
        ussdServices = new UssdServices();
        ussdServices.SaveussdData(docid, contact, dob, "cash-plan",Amt);
        System.out.println(" Cash Plan Processed "); 
        
       
       
        
    }
    
        private void ProcessBeneficiaryrequest(String request,String contact,String toProcess){
        try {
            System.out.println("Processing Beneficiary ");
            System.out.println("Process Step  " + toProcess);
            System.out.println("details" + request);
            
            BeneficiarychangerequestJpaController benC = new BeneficiarychangerequestJpaController();
            Beneficiarychangerequest beneficiarychangerequest = new Beneficiarychangerequest();
            beneficiarychangerequest.setContact(contact);
            beneficiarychangerequest.setDetails(request);
            beneficiarychangerequest.setEntryby("System");
            beneficiarychangerequest.setStageid(1);
            beneficiarychangerequest.setStagename(toProcess);
            beneficiarychangerequest.setStatusid(1);
            beneficiarychangerequest.setStatusname("Unprocessed");
            
            benC.create(beneficiarychangerequest);
            
              smsService = new SmsService();
              smsService.sendSms(contact," Thank You Your Beneficiary Change Request is Being Processed "); 
        } catch (Exception ex) {
              System.out.println("Error" + ex);
        }
        
    }
    private void processUmashpayment(String req,String contact,String contact2,String invoice){
        try {
            Ussdmpesarequest Ussdmpesarequest = new Ussdmpesarequest();
            UssdmpesarequestJpaController ujc = new UssdmpesarequestJpaController ();
            
            Ussdmpesarequest.setContact(contact);
            Ussdmpesarequest.setInvoicenumber(invoice);
            Ussdmpesarequest.setRequestdate(new Date());
            Ussdmpesarequest.setRequestdetails(req);
            
            ujc.create(Ussdmpesarequest);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    private String RandomNumber(Integer rid){
        String randomInt="";
         Random randomGenerator = new Random();
    for (int idx = 1; idx <= 10; ++idx){
       randomInt = randomGenerator.nextInt(100)+"";
    
    }
    return "SD"+rid;
    }
    private void ProcessInquiryrequest(String req,String contact){
        
        try {
            System.out.println("Inquiry request");
            UssdinquiriesJpaController ussdinquiriesJpaController = new UssdinquiriesJpaController();
            Ussdinquiries ussdinquiries =new Ussdinquiries();
            ussdinquiries.setContact(contact);
            ussdinquiries.setInquirydate(new Date());
            ussdinquiries.setInquirydetails(req);
            
            ussdinquiriesJpaController.create(ussdinquiries);
            
            
        } catch (Exception ex) {
           System.out.println(ex);
        }
              
    }
    private void processclaim(String req,String contact){
        try {
            String[] result = req.split(Pattern.quote("*"));
            System.out.println("Processing Claim requst " + req);
            ClaimsJpaController   claimsJpaController = new ClaimsJpaController();
            Claims clms = new Claims();
            clms.setClaimcontact(contact);
            clms.setClaimdate(new Date());
            clms.setClaimid(result[1]);
            clms.setStatus(1);
            clms.setStatusname("New Claim Request");
            
            claimsJpaController.create(clms);
            
            
            
            //send mail
            SendMail sendMail = new SendMail();
            sendMail.SendClaimMail(clms);
        } catch (Exception ex) {
            Logger.getLogger(Ussd.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void passwordreset(String req,String contact){
        try {
            UssdpasswordresetJpaController prc = new UssdpasswordresetJpaController();
            Ussdpasswordreset ussdpasswordreset = new Ussdpasswordreset();
            
            ussdpasswordreset.setContact(contact);
            ussdpasswordreset.setRequestdate(new Date());
            ussdpasswordreset.setRequestdetails(req);
            
            prc.create(ussdpasswordreset);
        } catch (Exception ex) {
            log.error(ex);
        }
    }
}
