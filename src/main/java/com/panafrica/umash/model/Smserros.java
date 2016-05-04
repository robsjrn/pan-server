/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panafrica.umash.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name = "smserros")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Smserros.findAll", query = "SELECT s FROM Smserros s"),
    @NamedQuery(name = "Smserros.findByTid", query = "SELECT s FROM Smserros s WHERE s.tid = :tid"),
    @NamedQuery(name = "Smserros.findByContact", query = "SELECT s FROM Smserros s WHERE s.contact = :contact"),
    @NamedQuery(name = "Smserros.findByMessage", query = "SELECT s FROM Smserros s WHERE s.message = :message"),
    @NamedQuery(name = "Smserros.findBySmsdate", query = "SELECT s FROM Smserros s WHERE s.smsdate = :smsdate"),
    @NamedQuery(name = "Smserros.findByStatusid", query = "SELECT s FROM Smserros s WHERE s.statusid = :statusid"),
    @NamedQuery(name = "Smserros.findByStatusname", query = "SELECT s FROM Smserros s WHERE s.statusname = :statusname")})
public class Smserros implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "tid")
    private Integer tid;
    @Size(max = 50)
    @Column(name = "contact")
    private String contact;
    @Size(max = 200)
    @Column(name = "message")
    private String message;
    @Column(name = "smsdate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date smsdate;
    @Column(name = "statusid")
    private Integer statusid;
    @Size(max = 100)
    @Column(name = "statusname")
    private String statusname;

    public Smserros() {
    }

    public Smserros(Integer tid) {
        this.tid = tid;
    }

    public Integer getTid() {
        return tid;
    }

    public void setTid(Integer tid) {
        this.tid = tid;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getSmsdate() {
        return smsdate;
    }

    public void setSmsdate(Date smsdate) {
        this.smsdate = smsdate;
    }

    public Integer getStatusid() {
        return statusid;
    }

    public void setStatusid(Integer statusid) {
        this.statusid = statusid;
    }

    public String getStatusname() {
        return statusname;
    }

    public void setStatusname(String statusname) {
        this.statusname = statusname;
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
        if (!(object instanceof Smserros)) {
            return false;
        }
        Smserros other = (Smserros) object;
        if ((this.tid == null && other.tid != null) || (this.tid != null && !this.tid.equals(other.tid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.panafrica.umash.model.Smserros[ tid=" + tid + " ]";
    }
    
}
