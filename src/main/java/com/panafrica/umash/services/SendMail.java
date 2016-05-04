/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panafrica.umash.services;

import com.panafrica.umash.configuration.Appconstants;
import com.panafrica.umash.model.Claims;
import java.util.Date;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;


public class SendMail {
    
     
    private static Appconstants appconst;
    private Session mailSession;

    
    public void SendClaimMail(Claims cl ){
        
          Properties props = System.getProperties();
        props = new Properties();
            props.put("mail.smtp.user", "umashapp@pan-africa.com");
            props.put("mail.smtp.host", appconst.getConfigdata().getAppEmailhost());
            props.put("mail.smtp.port", appconst.getConfigdata().getAppEmailport());
            props.put("mail.smtp.socketFactory.port", appconst.getConfigdata().getAppEmailport());
           mailSession = Session.getInstance(props); 
        String subject="New Claim Request";
        
        
        String body=claimBody(cl);
        
        
        MimeMessage message = new MimeMessage(mailSession);
        try {
           // message.setFrom(new InternetAddress(mailSession.getProperty("mail.from")));
          message.setFrom(new InternetAddress(appconst.getConfigdata().getAppEmailaddress()));

            InternetAddress[] address = {new InternetAddress(appconst.getConfigdata().getBancagroupemail())};
            message.setRecipients(Message.RecipientType.TO, address);
            message.setSubject(subject);
            message.setSentDate(new Date());
            message.setContent(body,"text/html");
            Transport.send(message);
        } catch (MessagingException ex) {
            ex.printStackTrace();
        }
    }
      
    public void SendSystemStatus(String subject ,Exception e){
        
           Properties props = System.getProperties();
        props = new Properties();
            props.put("mail.smtp.user", "umashapp@pan-africa.com");
            props.put("mail.smtp.host", appconst.getConfigdata().getAppEmailhost());
            props.put("mail.smtp.port", appconst.getConfigdata().getAppEmailport());
            props.put("mail.smtp.socketFactory.port", appconst.getConfigdata().getAppEmailport());
           mailSession = Session.getInstance(props);  
           
           String body = BodyError(e.getMessage(),e.toString());
        
        MimeMessage message = new MimeMessage(mailSession);
        try {
           // message.setFrom(new InternetAddress(mailSession.getProperty("mail.from")));
          message.setFrom(new InternetAddress(appconst.getConfigdata().getAppEmailaddress()));

            InternetAddress[] address = {new InternetAddress(appconst.getConfigdata().getITgroupemail())};
            message.setRecipients(Message.RecipientType.TO, address);
            message.setSubject(subject);
            message.setSentDate(new Date());
            message.setContent(body,"text/html");
            Transport.send(message);
        } catch (MessagingException ex) {
            ex.printStackTrace();
        }
        
    }
    
    public void SendTeuploadMail(String filepath){
        
       Properties props = System.getProperties();
        props = new Properties();
            props.put("mail.smtp.user", "umashapp@pan-africa.com");
            props.put("mail.smtp.host", appconst.getConfigdata().getAppEmailhost());
            props.put("mail.smtp.port", appconst.getConfigdata().getAppEmailport());
            props.put("mail.smtp.socketFactory.port", appconst.getConfigdata().getAppEmailport());
           mailSession = Session.getInstance(props);      
        String subject="New TE Upload File";       
        
        String body=tebody();
        
        
         MimeMessage message = new MimeMessage(mailSession);
        try {
           // message.setFrom(new InternetAddress(mailSession.getProperty("mail.from")));
          message.setFrom(new InternetAddress(appconst.getConfigdata().getAppEmailaddress()));

            InternetAddress[] address = {new InternetAddress(appconst.getConfigdata().getBancagroupemail())};
            message.setRecipients(Message.RecipientType.TO, address);
            message.setSubject(subject);      
            message.setSentDate(new Date());
            
            
             BodyPart messageBodyPart = new MimeBodyPart();
             messageBodyPart.setContent(body,"text/html");
             Multipart multipart = new MimeMultipart();
             multipart.addBodyPart(messageBodyPart);
             
               // Part two is attachment
         messageBodyPart = new MimeBodyPart();
         DataSource source = new FileDataSource(filepath);
         messageBodyPart.setDataHandler(new DataHandler(source));
         messageBodyPart.setFileName(filepath);
         multipart.addBodyPart(messageBodyPart);

         // Send the complete message parts
         message.setContent(multipart);
            
            
            
            Transport.send(message);
        } catch (MessagingException ex) {
            ex.printStackTrace();
        }
        
    }
    
    private String tebody(){
        String bf= "<!DOCTYPE html>\n" +
"<html style=\"font-family: 'Helvetica Neue', 'Helvetica', Helvetica, Arial, sans-serif; font-size: 100%; line-height: 1.6em; margin: 0; padding: 0;\">\n" +
"<head>\n" +
"<meta name=\"viewport\" content=\"width=device-width\">\n" +
"<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n" +
"<title>TE Upload File </title>\n" +
"</head>\n" +
"<body bgcolor=\"#f6f6f6\" style=\"font-family: 'Helvetica Neue', 'Helvetica', Helvetica, Arial, sans-serif; font-size: 100%; line-height: 1.6em; -webkit-font-smoothing: antialiased; height: 100%; -webkit-text-size-adjust: none; width: 100% !important; margin: 0; padding: 0;\">\n" +
"\n" +
"<!-- body -->\n" +
"<table class=\"body-wrap\" bgcolor=\"#f6f6f6\" style=\"font-family: 'Helvetica Neue', 'Helvetica', Helvetica, Arial, sans-serif; font-size: 100%; line-height: 1.6em; width: 100%; margin: 0; padding: 20px;\"><tr style=\"font-family: 'Helvetica Neue', 'Helvetica', Helvetica, Arial, sans-serif; font-size: 100%; line-height: 1.6em; margin: 0; padding: 0;\">\n" +
"<td style=\"font-family: 'Helvetica Neue', 'Helvetica', Helvetica, Arial, sans-serif; font-size: 100%; line-height: 1.6em; margin: 0; padding: 0;\"></td>\n" +
"    <td class=\"container\" bgcolor=\"#FFFFFF\" style=\"font-family: 'Helvetica Neue', 'Helvetica', Helvetica, Arial, sans-serif; font-size: 100%; line-height: 1.6em; clear: both !important; display: block !important; max-width: 600px !important; Margin: 0 auto; padding: 20px; border: 1px solid #f0f0f0;\">\n" +
"\n" +
"      <!-- content -->\n" +
"      <div class=\"content\" style=\"font-family: 'Helvetica Neue', 'Helvetica', Helvetica, Arial, sans-serif; font-size: 100%; line-height: 1.6em; display: block; max-width: 600px; margin: 0 auto; padding: 0;\">\n" +
"      <table style=\"font-family: 'Helvetica Neue', 'Helvetica', Helvetica, Arial, sans-serif; font-size: 100%; line-height: 1.6em; width: 100%; margin: 0; padding: 0;\"><tr style=\"font-family: 'Helvetica Neue', 'Helvetica', Helvetica, Arial, sans-serif; font-size: 100%; line-height: 1.6em; margin: 0; padding: 0;\">\n" +
"<td style=\"font-family: 'Helvetica Neue', 'Helvetica', Helvetica, Arial, sans-serif; font-size: 100%; line-height: 1.6em; margin: 0; padding: 0;\">\n" +
"            <p style=\"font-family: 'Helvetica Neue', 'Helvetica', Helvetica, Arial, sans-serif; font-size: 14px; line-height: 1.6em; font-weight: normal; margin: 0 0 10px; padding: 0;\">Hi there,</p>\n" +
"            <p style=\"font-family: 'Helvetica Neue', 'Helvetica', Helvetica, Arial, sans-serif; font-size: 14px; line-height: 1.6em; font-weight: normal; margin: 0 0 10px; padding: 0;\">A New File has Been Prepared And Ready For Upload to TE </p>\n" +
"\n" +
"             <p style=\"font-family: 'Helvetica Neue', 'Helvetica', Helvetica, Arial, sans-serif; font-size: 14px; line-height: 1.6em; font-weight: normal; margin: 0 0 10px; padding: 0;\">Kindly find It Attached</p>\n" +
"    \n" +
"            <p style=\"font-family: 'Helvetica Neue', 'Helvetica', Helvetica, Arial, sans-serif; font-size: 14px; line-height: 1.6em; font-weight: normal; margin: 0 0 10px; padding: 0;\">For More Information Kindly log in to Umash App</p>\n" +
"           \n" +
"            <!-- button -->\n" +
"        \n" +
"            <p style=\"font-family: 'Helvetica Neue', 'Helvetica', Helvetica, Arial, sans-serif; font-size: 14px; line-height: 1.6em; font-weight: normal; margin: 0 0 10px; padding: 0;\">Thanks, have a lovely day.</p>\n" +
"        \n" +
"</div>\n" +
"      <!-- /content -->\n" +
"      \n" +
"    </td>\n" +
"    <td style=\"font-family: 'Helvetica Neue', 'Helvetica', Helvetica, Arial, sans-serif; font-size: 100%; line-height: 1.6em; margin: 0; padding: 0;\"></td>\n" +
"  </tr></table>\n" +
"<!-- /body --><!-- footer --><table class=\"footer-wrap\" style=\"font-family: 'Helvetica Neue', 'Helvetica', Helvetica, Arial, sans-serif; font-size: 100%; line-height: 1.6em; clear: both !important; width: 100%; margin: 0; padding: 0;\"><tr style=\"font-family: 'Helvetica Neue', 'Helvetica', Helvetica, Arial, sans-serif; font-size: 100%; line-height: 1.6em; margin: 0; padding: 0;\">\n" +
"<td style=\"font-family: 'Helvetica Neue', 'Helvetica', Helvetica, Arial, sans-serif; font-size: 100%; line-height: 1.6em; margin: 0; padding: 0;\"></td>\n" +
"    <td class=\"container\" style=\"font-family: 'Helvetica Neue', 'Helvetica', Helvetica, Arial, sans-serif; font-size: 100%; line-height: 1.6em; clear: both !important; display: block !important; max-width: 600px !important; margin: 0 auto; padding: 0;\">\n" +
"      \n" +
"      <!-- content -->\n" +
"      <div class=\"content\" style=\"font-family: 'Helvetica Neue', 'Helvetica', Helvetica, Arial, sans-serif; font-size: 100%; line-height: 1.6em; display: block; max-width: 600px; margin: 0 auto; padding: 0;\">\n" +
"        <table style=\"font-family: 'Helvetica Neue', 'Helvetica', Helvetica, Arial, sans-serif; font-size: 100%; line-height: 1.6em; width: 100%; margin: 0; padding: 0;\"><tr style=\"font-family: 'Helvetica Neue', 'Helvetica', Helvetica, Arial, sans-serif; font-size: 100%; line-height: 1.6em; margin: 0; padding: 0;\">\n" +
"<td align=\"center\" style=\"font-family: 'Helvetica Neue', 'Helvetica', Helvetica, Arial, sans-serif; font-size: 100%; line-height: 1.6em; margin: 0; padding: 0;\">\n" +
"           \n" +
"            </td>\n" +
"          </tr></table>\n" +
"</div>\n" +
"      <!-- /content -->\n" +
"      \n" +
"    </td>\n" +
"    <td style=\"font-family: 'Helvetica Neue', 'Helvetica', Helvetica, Arial, sans-serif; font-size: 100%; line-height: 1.6em; margin: 0; padding: 0;\"></td>\n" +
"  </tr></table>\n" +
"<!-- /footer -->\n" +
"</body>\n" +
"</html>";
        
        return bf;
    }
    
    private String claimBody(Claims cl){
        String bd="<!DOCTYPE html>\n" +
"<html style=\"font-family: 'Helvetica Neue', 'Helvetica', Helvetica, Arial, sans-serif; font-size: 100%; line-height: 1.6em; margin: 0; padding: 0;\">\n" +
"<head>\n" +
"<meta name=\"viewport\" content=\"width=device-width\">\n" +
"<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n" +
"<title>Claim Request</title>\n" +
"</head>\n" +
"<body bgcolor=\"#f6f6f6\" style=\"font-family: 'Helvetica Neue', 'Helvetica', Helvetica, Arial, sans-serif; font-size: 100%; line-height: 1.6em; -webkit-font-smoothing: antialiased; height: 100%; -webkit-text-size-adjust: none; width: 100% !important; margin: 0; padding: 0;\">\n" +
"\n" +
"<!-- body -->\n" +
"<table class=\"body-wrap\" bgcolor=\"#f6f6f6\" style=\"font-family: 'Helvetica Neue', 'Helvetica', Helvetica, Arial, sans-serif; font-size: 100%; line-height: 1.6em; width: 100%; margin: 0; padding: 20px;\"><tr style=\"font-family: 'Helvetica Neue', 'Helvetica', Helvetica, Arial, sans-serif; font-size: 100%; line-height: 1.6em; margin: 0; padding: 0;\">\n" +
"<td style=\"font-family: 'Helvetica Neue', 'Helvetica', Helvetica, Arial, sans-serif; font-size: 100%; line-height: 1.6em; margin: 0; padding: 0;\"></td>\n" +
"    <td class=\"container\" bgcolor=\"#FFFFFF\" style=\"font-family: 'Helvetica Neue', 'Helvetica', Helvetica, Arial, sans-serif; font-size: 100%; line-height: 1.6em; clear: both !important; display: block !important; max-width: 600px !important; Margin: 0 auto; padding: 20px; border: 1px solid #f0f0f0;\">\n" +
"\n" +
"      <!-- content -->\n" +
"      <div class=\"content\" style=\"font-family: 'Helvetica Neue', 'Helvetica', Helvetica, Arial, sans-serif; font-size: 100%; line-height: 1.6em; display: block; max-width: 600px; margin: 0 auto; padding: 0;\">\n" +
"      <table style=\"font-family: 'Helvetica Neue', 'Helvetica', Helvetica, Arial, sans-serif; font-size: 100%; line-height: 1.6em; width: 100%; margin: 0; padding: 0;\"><tr style=\"font-family: 'Helvetica Neue', 'Helvetica', Helvetica, Arial, sans-serif; font-size: 100%; line-height: 1.6em; margin: 0; padding: 0;\">\n" +
"<td style=\"font-family: 'Helvetica Neue', 'Helvetica', Helvetica, Arial, sans-serif; font-size: 100%; line-height: 1.6em; margin: 0; padding: 0;\">\n" +
"            <p style=\"font-family: 'Helvetica Neue', 'Helvetica', Helvetica, Arial, sans-serif; font-size: 14px; line-height: 1.6em; font-weight: normal; margin: 0 0 10px; padding: 0;\">Hi there,</p>\n" +
"            <p style=\"font-family: 'Helvetica Neue', 'Helvetica', Helvetica, Arial, sans-serif; font-size: 14px; line-height: 1.6em; font-weight: normal; margin: 0 0 10px; padding: 0;\">There has Been a New Claim Request .Policy Number "+cl.getClaimid()+"  Contacts "+cl.getClaimcontact()+"\n" +
"    \n" +
"            <p style=\"font-family: 'Helvetica Neue', 'Helvetica', Helvetica, Arial, sans-serif; font-size: 14px; line-height: 1.6em; font-weight: normal; margin: 0 0 10px; padding: 0;\">For More Information Kindly log in to Umash App</p>\n" +
"           \n" +
"            <!-- button -->\n" +
"        \n" +
"            <p style=\"font-family: 'Helvetica Neue', 'Helvetica', Helvetica, Arial, sans-serif; font-size: 14px; line-height: 1.6em; font-weight: normal; margin: 0 0 10px; padding: 0;\">Thanks, have a lovely day.</p>\n" +
"        \n" +
"</div>\n" +
"      <!-- /content -->\n" +
"      \n" +
"    </td>\n" +
"    <td style=\"font-family: 'Helvetica Neue', 'Helvetica', Helvetica, Arial, sans-serif; font-size: 100%; line-height: 1.6em; margin: 0; padding: 0;\"></td>\n" +
"  </tr></table>\n" +
"<!-- /body --><!-- footer --><table class=\"footer-wrap\" style=\"font-family: 'Helvetica Neue', 'Helvetica', Helvetica, Arial, sans-serif; font-size: 100%; line-height: 1.6em; clear: both !important; width: 100%; margin: 0; padding: 0;\"><tr style=\"font-family: 'Helvetica Neue', 'Helvetica', Helvetica, Arial, sans-serif; font-size: 100%; line-height: 1.6em; margin: 0; padding: 0;\">\n" +
"<td style=\"font-family: 'Helvetica Neue', 'Helvetica', Helvetica, Arial, sans-serif; font-size: 100%; line-height: 1.6em; margin: 0; padding: 0;\"></td>\n" +
"    <td class=\"container\" style=\"font-family: 'Helvetica Neue', 'Helvetica', Helvetica, Arial, sans-serif; font-size: 100%; line-height: 1.6em; clear: both !important; display: block !important; max-width: 600px !important; margin: 0 auto; padding: 0;\">\n" +
"      \n" +
"      <!-- content -->\n" +
"      <div class=\"content\" style=\"font-family: 'Helvetica Neue', 'Helvetica', Helvetica, Arial, sans-serif; font-size: 100%; line-height: 1.6em; display: block; max-width: 600px; margin: 0 auto; padding: 0;\">\n" +
"        <table style=\"font-family: 'Helvetica Neue', 'Helvetica', Helvetica, Arial, sans-serif; font-size: 100%; line-height: 1.6em; width: 100%; margin: 0; padding: 0;\"><tr style=\"font-family: 'Helvetica Neue', 'Helvetica', Helvetica, Arial, sans-serif; font-size: 100%; line-height: 1.6em; margin: 0; padding: 0;\">\n" +
"<td align=\"center\" style=\"font-family: 'Helvetica Neue', 'Helvetica', Helvetica, Arial, sans-serif; font-size: 100%; line-height: 1.6em; margin: 0; padding: 0;\">\n" +
"           \n" +
"            </td>\n" +
"          </tr></table>\n" +
"</div>\n" +
"      <!-- /content -->\n" +
"      \n" +
"    </td>\n" +
"    <td style=\"font-family: 'Helvetica Neue', 'Helvetica', Helvetica, Arial, sans-serif; font-size: 100%; line-height: 1.6em; margin: 0; padding: 0;\"></td>\n" +
"  </tr></table>\n" +
"<!-- /footer -->\n" +
"</body>\n" +
"</html>";
        
        return bd;
    }
    
    private String BodyError(String Occurence,String Errordetails){
        String bf="<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n" +
"<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
"<head>\n" +
"<meta name=\"viewport\" content=\"width=device-width\" />\n" +
"<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\n" +
"<title>System Alert</title>\n" +
"<style>\n" +
"* -------------------------------------\n" +
"    GLOBAL\n" +
"------------------------------------- */\n" +
"* {\n" +
"  margin: 0;\n" +
"  padding: 0;\n" +
"  font-family: \"Helvetica Neue\", \"Helvetica\", Helvetica, Arial, sans-serif;\n" +
"  box-sizing: border-box;\n" +
"  font-size: 14px;\n" +
"}\n" +
"\n" +
"img {\n" +
"  max-width: 100%;\n" +
"}\n" +
"\n" +
"body {\n" +
"  -webkit-font-smoothing: antialiased;\n" +
"  -webkit-text-size-adjust: none;\n" +
"  width: 100% !important;\n" +
"  height: 100%;\n" +
"  line-height: 1.6;\n" +
"}\n" +
"\n" +
"/* Let's make sure all tables have defaults */\n" +
"table td {\n" +
"  vertical-align: top;\n" +
"}\n" +
"\n" +
"/* -------------------------------------\n" +
"    BODY & CONTAINER\n" +
"------------------------------------- */\n" +
"body {\n" +
"  background-color: #f6f6f6;\n" +
"}\n" +
"\n" +
".body-wrap {\n" +
"  background-color: #f6f6f6;\n" +
"  width: 100%;\n" +
"}\n" +
"\n" +
".container {\n" +
"  display: block !important;\n" +
"  max-width: 600px !important;\n" +
"  margin: 0 auto !important;\n" +
"  /* makes it centered */\n" +
"  clear: both !important;\n" +
"}\n" +
"\n" +
".content {\n" +
"  max-width: 600px;\n" +
"  margin: 0 auto;\n" +
"  display: block;\n" +
"  padding: 20px;\n" +
"}\n" +
"\n" +
"/* -------------------------------------\n" +
"    HEADER, FOOTER, MAIN\n" +
"------------------------------------- */\n" +
".main {\n" +
"  background: #fff;\n" +
"  border: 1px solid #e9e9e9;\n" +
"  border-radius: 3px;\n" +
"}\n" +
"\n" +
".content-wrap {\n" +
"  padding: 20px;\n" +
"}\n" +
"\n" +
".content-block {\n" +
"  padding: 0 0 20px;\n" +
"}\n" +
"\n" +
".header {\n" +
"  width: 100%;\n" +
"  margin-bottom: 20px;\n" +
"}\n" +
"\n" +
".footer {\n" +
"  width: 100%;\n" +
"  clear: both;\n" +
"  color: #999;\n" +
"  padding: 20px;\n" +
"}\n" +
".footer a {\n" +
"  color: #999;\n" +
"}\n" +
".footer p, .footer a, .footer unsubscribe, .footer td {\n" +
"  font-size: 12px;\n" +
"}\n" +
"\n" +
"/* -------------------------------------\n" +
"    GRID AND COLUMNS\n" +
"------------------------------------- */\n" +
".column-left {\n" +
"  float: left;\n" +
"  width: 50%;\n" +
"}\n" +
"\n" +
".column-right {\n" +
"  float: left;\n" +
"  width: 50%;\n" +
"}\n" +
"\n" +
"/* -------------------------------------\n" +
"    TYPOGRAPHY\n" +
"------------------------------------- */\n" +
"h1, h2, h3 {\n" +
"  font-family: \"Helvetica Neue\", Helvetica, Arial, \"Lucida Grande\", sans-serif;\n" +
"  color: #000;\n" +
"  margin: 40px 0 0;\n" +
"  line-height: 1.2;\n" +
"  font-weight: 400;\n" +
"}\n" +
"\n" +
"h1 {\n" +
"  font-size: 32px;\n" +
"  font-weight: 500;\n" +
"}\n" +
"\n" +
"h2 {\n" +
"  font-size: 24px;\n" +
"}\n" +
"\n" +
"h3 {\n" +
"  font-size: 18px;\n" +
"}\n" +
"\n" +
"h4 {\n" +
"  font-size: 14px;\n" +
"  font-weight: 600;\n" +
"}\n" +
"\n" +
"p, ul, ol {\n" +
"  margin-bottom: 10px;\n" +
"  font-weight: normal;\n" +
"}\n" +
"p li, ul li, ol li {\n" +
"  margin-left: 5px;\n" +
"  list-style-position: inside;\n" +
"}\n" +
"\n" +
"/* -------------------------------------\n" +
"    LINKS & BUTTONS\n" +
"------------------------------------- */\n" +
"a {\n" +
"  color: #348eda;\n" +
"  text-decoration: underline;\n" +
"}\n" +
"\n" +
".btn-primary {\n" +
"  text-decoration: none;\n" +
"  color: #FFF;\n" +
"  background-color: #348eda;\n" +
"  border: solid #348eda;\n" +
"  border-width: 10px 20px;\n" +
"  line-height: 2;\n" +
"  font-weight: bold;\n" +
"  text-align: center;\n" +
"  cursor: pointer;\n" +
"  display: inline-block;\n" +
"  border-radius: 5px;\n" +
"  text-transform: capitalize;\n" +
"}\n" +
"\n" +
"/* -------------------------------------\n" +
"    OTHER STYLES THAT MIGHT BE USEFUL\n" +
"------------------------------------- */\n" +
".last {\n" +
"  margin-bottom: 0;\n" +
"}\n" +
"\n" +
".first {\n" +
"  margin-top: 0;\n" +
"}\n" +
"\n" +
".padding {\n" +
"  padding: 10px 0;\n" +
"}\n" +
"\n" +
".aligncenter {\n" +
"  text-align: center;\n" +
"}\n" +
"\n" +
".alignright {\n" +
"  text-align: right;\n" +
"}\n" +
"\n" +
".alignleft {\n" +
"  text-align: left;\n" +
"}\n" +
"\n" +
".clear {\n" +
"  clear: both;\n" +
"}\n" +
"\n" +
"/* -------------------------------------\n" +
"    Alerts\n" +
"------------------------------------- */\n" +
".alert {\n" +
"  font-size: 16px;\n" +
"  color: #fff;\n" +
"  font-weight: 500;\n" +
"  padding: 20px;\n" +
"  text-align: center;\n" +
"  border-radius: 3px 3px 0 0;\n" +
"}\n" +
".alert a {\n" +
"  color: #fff;\n" +
"  text-decoration: none;\n" +
"  font-weight: 500;\n" +
"  font-size: 16px;\n" +
"}\n" +
".alert.alert-warning {\n" +
"  background: #ff9f00;\n" +
"}\n" +
".alert.alert-bad {\n" +
"  background: #d0021b;\n" +
"}\n" +
".alert.alert-good {\n" +
"  background: #68b90f;\n" +
"}\n" +
"\n" +
"/* -------------------------------------\n" +
"    INVOICE\n" +
"------------------------------------- */\n" +
".invoice {\n" +
"  margin: 40px auto;\n" +
"  text-align: left;\n" +
"  width: 80%;\n" +
"}\n" +
".invoice td {\n" +
"  padding: 5px 0;\n" +
"}\n" +
".invoice .invoice-items {\n" +
"  width: 100%;\n" +
"}\n" +
".invoice .invoice-items td {\n" +
"  border-top: #eee 1px solid;\n" +
"}\n" +
".invoice .invoice-items .total td {\n" +
"  border-top: 2px solid #333;\n" +
"  border-bottom: 2px solid #333;\n" +
"  font-weight: 700;\n" +
"}\n" +
"\n" +
"/* -------------------------------------\n" +
"    RESPONSIVE AND MOBILE FRIENDLY STYLES\n" +
"------------------------------------- */\n" +
"@media only screen and (max-width: 640px) {\n" +
"  h1, h2, h3, h4 {\n" +
"    font-weight: 600 !important;\n" +
"    margin: 20px 0 5px !important;\n" +
"  }\n" +
"\n" +
"  h1 {\n" +
"    font-size: 22px !important;\n" +
"  }\n" +
"\n" +
"  h2 {\n" +
"    font-size: 18px !important;\n" +
"  }\n" +
"\n" +
"  h3 {\n" +
"    font-size: 16px !important;\n" +
"  }\n" +
"\n" +
"  .container {\n" +
"    width: 100% !important;\n" +
"  }\n" +
"\n" +
"  .content, .content-wrapper {\n" +
"    padding: 10px !important;\n" +
"  }\n" +
"\n" +
"  .invoice {\n" +
"    width: 100% !important;\n" +
"  }\n" +
"}\n" +
"\n" +
"</style>\n" +
"</head>\n" +
"\n" +
"<body>\n" +
"\n" +
"<table class=\"body-wrap\">\n" +
"	<tr>\n" +
"		<td></td>\n" +
"		<td class=\"container\" width=\"600\">\n" +
"			<div class=\"content\">\n" +
"				<table class=\"main\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\">\n" +
"					<tr>\n" +
"						<td class=\"alert alert-warning\">\n" +
"							System Warning\n" +
"						</td>\n" +
"					</tr>\n" +
"					<tr>\n" +
"						<td class=\"content-wrap\">\n" +
"							<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\">\n" +
"								<tr>\n" +
"									<td class=\"content-block\">\n" +
"										There has Been an <strong> Error </strong> Occurence.\n" +
"									</td>\n" +
"									\n" +
"								</tr>\n" +
"								<tr>\n" +
"                                  <td class=\"content-block\">\n" +
"										The <strong> Error </strong> has Been Logged But Needs Your Attention  .\n" +
"									</td>\n" +
"							    </tr>\n" +
"							    <tr>\n" +
"                                  <td class=\"content-block\">\n" +
"										The  Occurence is <strong> "+Occurence+" </strong> \n" +
"									</td>\n" +
"							    </tr>\n" +
"\n" +
"								<tr>\n" +
"									<td class=\"content-block\">\n" +
"										<p>Error details </p>\n" +
"										<p>\n" + Errordetails  +                                                                     
"										</p>\n" +
"									</td>\n" +
"								</tr>\n" +
"								<tr>\n" +
"								</tr>\n" +
"								\n" +
"							</table>\n" +
"						</td>\n" +
"					</tr>\n" +
"				</table>\n" +
"			</div>\n" +
"		</td>\n" +
"		<td></td>\n" +
"	</tr>\n" +
"</table>\n" +
"\n" +
"</body>\n" +
"</html>";
        
        
        return bf;
    }
}
