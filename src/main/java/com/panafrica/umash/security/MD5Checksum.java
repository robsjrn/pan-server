/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panafrica.umash.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


/**
 *
 * @author nhif
 */
public class MD5Checksum {
    public boolean Checkhash(String digest,String password){
        boolean status=false;
      
 
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(password.getBytes());
            
            byte byteData[] = md.digest();
            
            //convert the byte to hex format method 1
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < byteData.length; i++) {
                sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
            }
            
            if (digest.equals(sb.toString())){
                status=true;
                System.out.println("Password Match");
            }
            
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
         
        }
        
        return status;
    }
    
    public String hashPassword (String pwrd){
         StringBuffer sb = new StringBuffer();
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(pwrd.getBytes());
            
            
            byte byteData[] = md.digest();
            
            //convert the byte to hex format method 1
            
            for (int i = 0; i < byteData.length; i++) {
                sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
            }
            
           // System.out.println("Digest(in hex format):: " + sb.toString());
             //util.info(sb.toString());
            
            
         }catch(Exception ex){
            // util.Error(ex.toString());
            ex.printStackTrace();
             
         } 
         return sb.toString();
    }
        
    }
    

