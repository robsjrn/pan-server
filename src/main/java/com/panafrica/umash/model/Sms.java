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
@Table(name = "sms")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Sms.findAll", query = "SELECT s FROM Sms s"),
    @NamedQuery(name = "Sms.findByTid", query = "SELECT s FROM Sms s WHERE s.tid = :tid"),
    @NamedQuery(name = "Sms.findByNumber", query = "SELECT s FROM Sms s WHERE s.number = :number"),
    @NamedQuery(name = "Sms.findByMsgText", query = "SELECT s FROM Sms s WHERE s.msgText = :msgText"),
    @NamedQuery(name = "Sms.findByStatusId", query = "SELECT s FROM Sms s WHERE s.statusId = :statusId"),
    @NamedQuery(name = "Sms.findByStatusDesc", query = "SELECT s FROM Sms s WHERE s.statusDesc = :statusDesc")})
public class Sms implements Serializable {

    @Column(name = "smsdate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date smsdate;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "tid")
    private Integer tid;
    @Size(max = 20)
    @Column(name = "number")
    private String number;
    @Size(max = 300)
    @Column(name = "msg_text")
    private String msgText;
    @Column(name = "status_id")
    private Integer statusId;
    @Size(max = 100)
    @Column(name = "status_desc")
    private String statusDesc;

    public Sms() {
    }

    public Sms(Integer tid) {
        this.tid = tid;
    }

    public Integer getTid() {
        return tid;
    }

    public void setTid(Integer tid) {
        this.tid = tid;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getMsgText() {
        return msgText;
    }

    public void setMsgText(String msgText) {
        this.msgText = msgText;
    }

    public Integer getStatusId() {
        return statusId;
    }

    public void setStatusId(Integer statusId) {
        this.statusId = statusId;
    }

    public String getStatusDesc() {
        return statusDesc;
    }

    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
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
        if (!(object instanceof Sms)) {
            return false;
        }
        Sms other = (Sms) object;
        if ((this.tid == null && other.tid != null) || (this.tid != null && !this.tid.equals(other.tid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.panafrica.umash.model.Sms[ tid=" + tid + " ]";
    }

    public Date getSmsdate() {
        return smsdate;
    }

    public void setSmsdate(Date smsdate) {
        this.smsdate = smsdate;
    }
    
}
