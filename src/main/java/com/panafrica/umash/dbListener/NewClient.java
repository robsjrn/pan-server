/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panafrica.umash.dbListener;

import com.panafrica.umash.model.Clients;
import com.panafrica.umash.model.Ussdtemp;
import com.panafrica.umash.task.ussdtasks;
import javax.persistence.PostPersist;



public class NewClient {
    ussdtasks task;
    
    @PostPersist
    public void addNewClientc(Ussdtemp cli ){
        System.out.println(" Saved a USSD client ...  ");
       
        task = new ussdtasks();
        task.processUssd(cli);
       
    }
    
}
