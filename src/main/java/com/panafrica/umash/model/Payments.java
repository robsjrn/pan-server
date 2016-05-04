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
@Table(name = "payments")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Payments.findAll", query = "SELECT p FROM Payments p"),
    @NamedQuery(name = "Payments.findByRid", query = "SELECT p FROM Payments p WHERE p.rid = :rid"),
    @NamedQuery(name = "Payments.findByPaymentid", query = "SELECT p FROM Payments p WHERE p.paymentid = :paymentid"),
    @NamedQuery(name = "Payments.findByPaymentdate", query = "SELECT p FROM Payments p WHERE p.paymentdate = :paymentdate"),
    @NamedQuery(name = "Payments.findByPaymentamount", query = "SELECT p FROM Payments p WHERE p.paymentamount = :paymentamount"),
    @NamedQuery(name = "Payments.findByClientid", query = "SELECT p FROM Payments p WHERE p.clientid = :clientid"),
    @NamedQuery(name = "Payments.findByPaymentdescription", query = "SELECT p FROM Payments p WHERE p.paymentdescription = :paymentdescription"),
    @NamedQuery(name = "Payments.findBySystemdate", query = "SELECT p FROM Payments p WHERE p.systemdate = :systemdate")})
public class Payments implements Serializable {

    @Size(max = 30)
    @Column(name = "entryby")
    private String entryby;
    @Column(name = "entrydate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date entrydate;
    @Size(max = 50)
    @Column(name = "paymentchanel")
    private String paymentchanel;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "rid")
    private Integer rid;
    @Size(max = 30)
    @Column(name = "Paymentid")
    private String paymentid;
    @Column(name = "paymentdate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date paymentdate;
    @Column(name = "paymentamount")
    private Integer paymentamount;
    @Size(max = 30)
    @Column(name = "clientid")
    private String clientid;
    @Size(max = 50)
    @Column(name = "paymentdescription")
    private String paymentdescription;
    @Column(name = "systemdate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date systemdate;

    public Payments() {
    }

    public Payments(Integer rid) {
        this.rid = rid;
    }

    public Integer getRid() {
        return rid;
    }

    public void setRid(Integer rid) {
        this.rid = rid;
    }

    public String getPaymentid() {
        return paymentid;
    }

    public void setPaymentid(String paymentid) {
        this.paymentid = paymentid;
    }

    public Date getPaymentdate() {
        return paymentdate;
    }

    public void setPaymentdate(Date paymentdate) {
        this.paymentdate = paymentdate;
    }

    public Integer getPaymentamount() {
        return paymentamount;
    }

    public void setPaymentamount(Integer paymentamount) {
        this.paymentamount = paymentamount;
    }

    public String getClientid() {
        return clientid;
    }

    public void setClientid(String clientid) {
        this.clientid = clientid;
    }

    public String getPaymentdescription() {
        return paymentdescription;
    }

    public void setPaymentdescription(String paymentdescription) {
        this.paymentdescription = paymentdescription;
    }

    public Date getSystemdate() {
        return systemdate;
    }

    public void setSystemdate(Date systemdate) {
        this.systemdate = systemdate;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (rid != null ? rid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Payments)) {
            return false;
        }
        Payments other = (Payments) object;
        if ((this.rid == null && other.rid != null) || (this.rid != null && !this.rid.equals(other.rid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.panafrica.umash.model.Payments[ rid=" + rid + " ]";
    }

    public String getEntryby() {
        return entryby;
    }

    public void setEntryby(String entryby) {
        this.entryby = entryby;
    }

    public Date getEntrydate() {
        return entrydate;
    }

    public void setEntrydate(Date entrydate) {
        this.entrydate = entrydate;
    }

    public String getPaymentchanel() {
        return paymentchanel;
    }

    public void setPaymentchanel(String paymentchanel) {
        this.paymentchanel = paymentchanel;
    }
    
}
