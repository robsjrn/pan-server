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
@Table(name = "iprserrors")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Iprserrors.findAll", query = "SELECT i FROM Iprserrors i"),
    @NamedQuery(name = "Iprserrors.findByTid", query = "SELECT i FROM Iprserrors i WHERE i.tid = :tid"),
    @NamedQuery(name = "Iprserrors.findByDocid", query = "SELECT i FROM Iprserrors i WHERE i.docid = :docid"),
    @NamedQuery(name = "Iprserrors.findByDocoption", query = "SELECT i FROM Iprserrors i WHERE i.docoption = :docoption"),
    @NamedQuery(name = "Iprserrors.findByMessage", query = "SELECT i FROM Iprserrors i WHERE i.message = :message"),
    @NamedQuery(name = "Iprserrors.findByStatusid", query = "SELECT i FROM Iprserrors i WHERE i.statusid = :statusid"),
    @NamedQuery(name = "Iprserrors.findByStatusname", query = "SELECT i FROM Iprserrors i WHERE i.statusname = :statusname"),
    @NamedQuery(name = "Iprserrors.findByEntrydate", query = "SELECT i FROM Iprserrors i WHERE i.entrydate = :entrydate")})
public class Iprserrors implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "tid")
    private Integer tid;
    @Size(max = 30)
    @Column(name = "docid")
    private String docid;
    @Size(max = 20)
    @Column(name = "docoption")
    private String docoption;
    @Size(max = 200)
    @Column(name = "message")
    private String message;
    @Column(name = "statusid")
    private Integer statusid;
    @Size(max = 100)
    @Column(name = "statusname")
    private String statusname;
    @Column(name = "entrydate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date entrydate;

    public Iprserrors() {
    }

    public Iprserrors(Integer tid) {
        this.tid = tid;
    }

    public Integer getTid() {
        return tid;
    }

    public void setTid(Integer tid) {
        this.tid = tid;
    }

    public String getDocid() {
        return docid;
    }

    public void setDocid(String docid) {
        this.docid = docid;
    }

    public String getDocoption() {
        return docoption;
    }

    public void setDocoption(String docoption) {
        this.docoption = docoption;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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

    public Date getEntrydate() {
        return entrydate;
    }

    public void setEntrydate(Date entrydate) {
        this.entrydate = entrydate;
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
        if (!(object instanceof Iprserrors)) {
            return false;
        }
        Iprserrors other = (Iprserrors) object;
        if ((this.tid == null && other.tid != null) || (this.tid != null && !this.tid.equals(other.tid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.panafrica.umash.model.Iprserrors[ tid=" + tid + " ]";
    }
    
}
