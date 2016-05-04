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
@Table(name = "mpesatransactions")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Mpesatransactions.findAll", query = "SELECT m FROM Mpesatransactions m"),
    @NamedQuery(name = "Mpesatransactions.findByMpesarid", query = "SELECT m FROM Mpesatransactions m WHERE m.mpesarid = :mpesarid"),
    @NamedQuery(name = "Mpesatransactions.findByMpesacontact", query = "SELECT m FROM Mpesatransactions m WHERE m.mpesacontact = :mpesacontact"),
    @NamedQuery(name = "Mpesatransactions.findByMpesaamount", query = "SELECT m FROM Mpesatransactions m WHERE m.mpesaamount = :mpesaamount"),
    @NamedQuery(name = "Mpesatransactions.findByMpesatransactionid", query = "SELECT m FROM Mpesatransactions m WHERE m.mpesatransactionid = :mpesatransactionid"),
    @NamedQuery(name = "Mpesatransactions.findByMpesastatus", query = "SELECT m FROM Mpesatransactions m WHERE m.mpesastatus = :mpesastatus"),
    @NamedQuery(name = "Mpesatransactions.findByMpesadescription", query = "SELECT m FROM Mpesatransactions m WHERE m.mpesadescription = :mpesadescription"),
    @NamedQuery(name = "Mpesatransactions.findByTransactiondate", query = "SELECT m FROM Mpesatransactions m WHERE m.transactiondate = :transactiondate"),
    @NamedQuery(name = "Mpesatransactions.findByUmashTrxnID", query = "SELECT m FROM Mpesatransactions m WHERE m.umashTrxnID = :umashTrxnID"),
    @NamedQuery(name = "Mpesatransactions.findByMpesatrandate", query = "SELECT m FROM Mpesatransactions m WHERE m.mpesatrandate = :mpesatrandate"),
    @NamedQuery(name = "Mpesatransactions.findByMpesatransactionstatus", query = "SELECT m FROM Mpesatransactions m WHERE m.mpesatransactionstatus = :mpesatransactionstatus"),
    @NamedQuery(name = "Mpesatransactions.findByMpesareturncode", query = "SELECT m FROM Mpesatransactions m WHERE m.mpesareturncode = :mpesareturncode"),
    @NamedQuery(name = "Mpesatransactions.findByTranStatus", query = "SELECT m FROM Mpesatransactions m WHERE m.tranStatus = :tranStatus"),
    @NamedQuery(name = "Mpesatransactions.findByTranStatusDesc", query = "SELECT m FROM Mpesatransactions m WHERE m.tranStatusDesc = :tranStatusDesc")})
public class Mpesatransactions implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "mpesarid")
    private Integer mpesarid;
    @Size(max = 30)
    @Column(name = "mpesacontact")
    private String mpesacontact;
    @Column(name = "mpesaamount")
    private Integer mpesaamount;
    @Size(max = 30)
    @Column(name = "mpesatransactionid")
    private String mpesatransactionid;
    @Column(name = "mpesastatus")
    private Integer mpesastatus;
    @Size(max = 50)
    @Column(name = "mpesadescription")
    private String mpesadescription;
    @Column(name = "transactiondate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date transactiondate;
    @Size(max = 30)
    @Column(name = "UmashTrxnID")
    private String umashTrxnID;
    @Size(max = 20)
    @Column(name = "mpesatrandate")
    private String mpesatrandate;
    @Size(max = 20)
    @Column(name = "mpesatransactionstatus")
    private String mpesatransactionstatus;
    @Size(max = 20)
    @Column(name = "mpesareturncode")
    private String mpesareturncode;
    @Column(name = "tran_status")
    private Integer tranStatus;
    @Size(max = 20)
    @Column(name = "tran_status_desc")
    private String tranStatusDesc;

    public Mpesatransactions() {
    }

    public Mpesatransactions(Integer mpesarid) {
        this.mpesarid = mpesarid;
    }

    public Integer getMpesarid() {
        return mpesarid;
    }

    public void setMpesarid(Integer mpesarid) {
        this.mpesarid = mpesarid;
    }

    public String getMpesacontact() {
        return mpesacontact;
    }

    public void setMpesacontact(String mpesacontact) {
        this.mpesacontact = mpesacontact;
    }

    public Integer getMpesaamount() {
        return mpesaamount;
    }

    public void setMpesaamount(Integer mpesaamount) {
        this.mpesaamount = mpesaamount;
    }

    public String getMpesatransactionid() {
        return mpesatransactionid;
    }

    public void setMpesatransactionid(String mpesatransactionid) {
        this.mpesatransactionid = mpesatransactionid;
    }

    public Integer getMpesastatus() {
        return mpesastatus;
    }

    public void setMpesastatus(Integer mpesastatus) {
        this.mpesastatus = mpesastatus;
    }

    public String getMpesadescription() {
        return mpesadescription;
    }

    public void setMpesadescription(String mpesadescription) {
        this.mpesadescription = mpesadescription;
    }

    public Date getTransactiondate() {
        return transactiondate;
    }

    public void setTransactiondate(Date transactiondate) {
        this.transactiondate = transactiondate;
    }

    public String getUmashTrxnID() {
        return umashTrxnID;
    }

    public void setUmashTrxnID(String umashTrxnID) {
        this.umashTrxnID = umashTrxnID;
    }

    public String getMpesatrandate() {
        return mpesatrandate;
    }

    public void setMpesatrandate(String mpesatrandate) {
        this.mpesatrandate = mpesatrandate;
    }

    public String getMpesatransactionstatus() {
        return mpesatransactionstatus;
    }

    public void setMpesatransactionstatus(String mpesatransactionstatus) {
        this.mpesatransactionstatus = mpesatransactionstatus;
    }

    public String getMpesareturncode() {
        return mpesareturncode;
    }

    public void setMpesareturncode(String mpesareturncode) {
        this.mpesareturncode = mpesareturncode;
    }

    public Integer getTranStatus() {
        return tranStatus;
    }

    public void setTranStatus(Integer tranStatus) {
        this.tranStatus = tranStatus;
    }

    public String getTranStatusDesc() {
        return tranStatusDesc;
    }

    public void setTranStatusDesc(String tranStatusDesc) {
        this.tranStatusDesc = tranStatusDesc;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (mpesarid != null ? mpesarid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Mpesatransactions)) {
            return false;
        }
        Mpesatransactions other = (Mpesatransactions) object;
        if ((this.mpesarid == null && other.mpesarid != null) || (this.mpesarid != null && !this.mpesarid.equals(other.mpesarid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.panafrica.umash.model.Mpesatransactions[ mpesarid=" + mpesarid + " ]";
    }
    
}
