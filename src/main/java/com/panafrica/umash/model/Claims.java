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
import javax.persistence.Lob;
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
@Table(name = "claims")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Claims.findAll", query = "SELECT c FROM Claims c"),
    @NamedQuery(name = "Claims.findByTid", query = "SELECT c FROM Claims c WHERE c.tid = :tid"),
    @NamedQuery(name = "Claims.findByClaimid", query = "SELECT c FROM Claims c WHERE c.claimid = :claimid"),
    @NamedQuery(name = "Claims.findByClaimdate", query = "SELECT c FROM Claims c WHERE c.claimdate = :claimdate"),
    @NamedQuery(name = "Claims.findByClaimcontact", query = "SELECT c FROM Claims c WHERE c.claimcontact = :claimcontact"),
    @NamedQuery(name = "Claims.findByStatus", query = "SELECT c FROM Claims c WHERE c.status = :status"),
    @NamedQuery(name = "Claims.findByStatusname", query = "SELECT c FROM Claims c WHERE c.statusname = :statusname"),
    @NamedQuery(name = "Claims.findByProcessedby", query = "SELECT c FROM Claims c WHERE c.processedby = :processedby"),
    @NamedQuery(name = "Claims.findByProcesseddate", query = "SELECT c FROM Claims c WHERE c.processeddate = :processeddate"),
    @NamedQuery(name = "Claims.findByPhotoavailable", query = "SELECT c FROM Claims c WHERE c.photoavailable = :photoavailable"),
    @NamedQuery(name = "Claims.findByPhotoname", query = "SELECT c FROM Claims c WHERE c.photoname = :photoname")})
public class Claims implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "tid")
    private Integer tid;
    @Size(max = 50)
    @Column(name = "claimid")
    private String claimid;
    @Column(name = "claimdate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date claimdate;
    @Size(max = 50)
    @Column(name = "claimcontact")
    private String claimcontact;
    @Column(name = "status")
    private Integer status;
    @Size(max = 50)
    @Column(name = "statusname")
    private String statusname;
    @Size(max = 50)
    @Column(name = "processedby")
    private String processedby;
    @Column(name = "processeddate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date processeddate;
    @Column(name = "photoavailable")
    private Boolean photoavailable;
    @Lob
    @Column(name = "photo")
    private byte[] photo;
    @Size(max = 30)
    @Column(name = "photoname")
    private String photoname;

    public Claims() {
    }

    public Claims(Integer tid) {
        this.tid = tid;
    }

    public Integer getTid() {
        return tid;
    }

    public void setTid(Integer tid) {
        this.tid = tid;
    }

    public String getClaimid() {
        return claimid;
    }

    public void setClaimid(String claimid) {
        this.claimid = claimid;
    }

    public Date getClaimdate() {
        return claimdate;
    }

    public void setClaimdate(Date claimdate) {
        this.claimdate = claimdate;
    }

    public String getClaimcontact() {
        return claimcontact;
    }

    public void setClaimcontact(String claimcontact) {
        this.claimcontact = claimcontact;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getStatusname() {
        return statusname;
    }

    public void setStatusname(String statusname) {
        this.statusname = statusname;
    }

    public String getProcessedby() {
        return processedby;
    }

    public void setProcessedby(String processedby) {
        this.processedby = processedby;
    }

    public Date getProcesseddate() {
        return processeddate;
    }

    public void setProcesseddate(Date processeddate) {
        this.processeddate = processeddate;
    }

    public Boolean getPhotoavailable() {
        return photoavailable;
    }

    public void setPhotoavailable(Boolean photoavailable) {
        this.photoavailable = photoavailable;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public String getPhotoname() {
        return photoname;
    }

    public void setPhotoname(String photoname) {
        this.photoname = photoname;
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
        if (!(object instanceof Claims)) {
            return false;
        }
        Claims other = (Claims) object;
        if ((this.tid == null && other.tid != null) || (this.tid != null && !this.tid.equals(other.tid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.panafrica.umash.model.Claims[ tid=" + tid + " ]";
    }
    
}
