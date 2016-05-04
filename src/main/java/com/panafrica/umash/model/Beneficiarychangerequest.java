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
@Table(name = "beneficiarychangerequest")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Beneficiarychangerequest.findAll", query = "SELECT b FROM Beneficiarychangerequest b"),
    @NamedQuery(name = "Beneficiarychangerequest.findByTid", query = "SELECT b FROM Beneficiarychangerequest b WHERE b.tid = :tid"),
    @NamedQuery(name = "Beneficiarychangerequest.findByContact", query = "SELECT b FROM Beneficiarychangerequest b WHERE b.contact = :contact"),
    @NamedQuery(name = "Beneficiarychangerequest.findByDetails", query = "SELECT b FROM Beneficiarychangerequest b WHERE b.details = :details"),
    @NamedQuery(name = "Beneficiarychangerequest.findByStatusid", query = "SELECT b FROM Beneficiarychangerequest b WHERE b.statusid = :statusid"),
    @NamedQuery(name = "Beneficiarychangerequest.findByStatusname", query = "SELECT b FROM Beneficiarychangerequest b WHERE b.statusname = :statusname"),
    @NamedQuery(name = "Beneficiarychangerequest.findByEntryby", query = "SELECT b FROM Beneficiarychangerequest b WHERE b.entryby = :entryby"),
    @NamedQuery(name = "Beneficiarychangerequest.findByProcessedby", query = "SELECT b FROM Beneficiarychangerequest b WHERE b.processedby = :processedby"),
    @NamedQuery(name = "Beneficiarychangerequest.findByStageid", query = "SELECT b FROM Beneficiarychangerequest b WHERE b.stageid = :stageid"),
    @NamedQuery(name = "Beneficiarychangerequest.findByStagename", query = "SELECT b FROM Beneficiarychangerequest b WHERE b.stagename = :stagename"),
    @NamedQuery(name = "Beneficiarychangerequest.findByRequestdate", query = "SELECT b FROM Beneficiarychangerequest b WHERE b.requestdate = :requestdate"),
    @NamedQuery(name = "Beneficiarychangerequest.findByProcesseddate", query = "SELECT b FROM Beneficiarychangerequest b WHERE b.processeddate = :processeddate")})
public class Beneficiarychangerequest implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "tid")
    private Integer tid;
    @Size(max = 30)
    @Column(name = "Contact")
    private String contact;
    @Size(max = 100)
    @Column(name = "details")
    private String details;
    @Column(name = "statusid")
    private Integer statusid;
    @Size(max = 60)
    @Column(name = "statusname")
    private String statusname;
    @Size(max = 40)
    @Column(name = "entryby")
    private String entryby;
    @Size(max = 50)
    @Column(name = "processedby")
    private String processedby;
    @Column(name = "stageid")
    private Integer stageid;
    @Size(max = 50)
    @Column(name = "stagename")
    private String stagename;
    @Column(name = "Requestdate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date requestdate;
    @Column(name = "processeddate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date processeddate;

    public Beneficiarychangerequest() {
    }

    public Beneficiarychangerequest(Integer tid) {
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

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
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

    public String getEntryby() {
        return entryby;
    }

    public void setEntryby(String entryby) {
        this.entryby = entryby;
    }

    public String getProcessedby() {
        return processedby;
    }

    public void setProcessedby(String processedby) {
        this.processedby = processedby;
    }

    public Integer getStageid() {
        return stageid;
    }

    public void setStageid(Integer stageid) {
        this.stageid = stageid;
    }

    public String getStagename() {
        return stagename;
    }

    public void setStagename(String stagename) {
        this.stagename = stagename;
    }

    public Date getRequestdate() {
        return requestdate;
    }

    public void setRequestdate(Date requestdate) {
        this.requestdate = requestdate;
    }

    public Date getProcesseddate() {
        return processeddate;
    }

    public void setProcesseddate(Date processeddate) {
        this.processeddate = processeddate;
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
        if (!(object instanceof Beneficiarychangerequest)) {
            return false;
        }
        Beneficiarychangerequest other = (Beneficiarychangerequest) object;
        if ((this.tid == null && other.tid != null) || (this.tid != null && !this.tid.equals(other.tid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.panafrica.umash.model.Beneficiarychangerequest[ tid=" + tid + " ]";
    }
    
}
