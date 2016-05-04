/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panafrica.umash.model;

import com.panafrica.umash.dbListener.NewMpesarequest;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author User
 */
@Entity
@EntityListeners({NewMpesarequest.class})
@Table(name = "ussdmpesarequest")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Ussdmpesarequest.findAll", query = "SELECT u FROM Ussdmpesarequest u"),
    @NamedQuery(name = "Ussdmpesarequest.findByTid", query = "SELECT u FROM Ussdmpesarequest u WHERE u.tid = :tid"),
    @NamedQuery(name = "Ussdmpesarequest.findByRequestdate", query = "SELECT u FROM Ussdmpesarequest u WHERE u.requestdate = :requestdate"),
    @NamedQuery(name = "Ussdmpesarequest.findByInvoicenumber", query = "SELECT u FROM Ussdmpesarequest u WHERE u.invoicenumber = :invoicenumber"),
    @NamedQuery(name = "Ussdmpesarequest.findByContact", query = "SELECT u FROM Ussdmpesarequest u WHERE u.contact = :contact"),
    @NamedQuery(name = "Ussdmpesarequest.findByRequestdetails", query = "SELECT u FROM Ussdmpesarequest u WHERE u.requestdetails = :requestdetails")})
public class Ussdmpesarequest implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "tid")
    private Integer tid;
    @Column(name = "requestdate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date requestdate;
    @Size(max = 30)
    @Column(name = "invoicenumber")
    private String invoicenumber;
    @Size(max = 30)
    @Column(name = "contact")
    private String contact;
    @Size(max = 100)
    @Column(name = "requestdetails")
    private String requestdetails;

    public Ussdmpesarequest() {
    }

    public Ussdmpesarequest(Integer tid) {
        this.tid = tid;
    }

    public Integer getTid() {
        return tid;
    }

    public void setTid(Integer tid) {
        this.tid = tid;
    }

    public Date getRequestdate() {
        return requestdate;
    }

    public void setRequestdate(Date requestdate) {
        this.requestdate = requestdate;
    }

    public String getInvoicenumber() {
        return invoicenumber;
    }

    public void setInvoicenumber(String invoicenumber) {
        this.invoicenumber = invoicenumber;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getRequestdetails() {
        return requestdetails;
    }

    public void setRequestdetails(String requestdetails) {
        this.requestdetails = requestdetails;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tid != null ? tid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ussdmpesarequest)) {
            return false;
        }
        Ussdmpesarequest other = (Ussdmpesarequest) object;
        if ((this.tid == null && other.tid != null) || (this.tid != null && !this.tid.equals(other.tid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.panafrica.umash.model.Ussdmpesarequest[ tid=" + tid + " ]";
    }
    
}
