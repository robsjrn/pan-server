/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panafrica.umash.model;

import com.panafrica.umash.dbListener.NewClient;
import com.panafrica.umash.dbListener.NewPasswordReset;
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
@EntityListeners({NewPasswordReset.class})
@Table(name = "ussdpasswordreset")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Ussdpasswordreset.findAll", query = "SELECT u FROM Ussdpasswordreset u"),
    @NamedQuery(name = "Ussdpasswordreset.findByTid", query = "SELECT u FROM Ussdpasswordreset u WHERE u.tid = :tid"),
    @NamedQuery(name = "Ussdpasswordreset.findByRequestdate", query = "SELECT u FROM Ussdpasswordreset u WHERE u.requestdate = :requestdate"),
    @NamedQuery(name = "Ussdpasswordreset.findByContact", query = "SELECT u FROM Ussdpasswordreset u WHERE u.contact = :contact"),
    @NamedQuery(name = "Ussdpasswordreset.findByRequestdetails", query = "SELECT u FROM Ussdpasswordreset u WHERE u.requestdetails = :requestdetails")})
public class Ussdpasswordreset implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "tid")
    private Integer tid;
    @Column(name = "requestdate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date requestdate;
    @Size(max = 50)
    @Column(name = "contact")
    private String contact;
    @Size(max = 50)
    @Column(name = "requestdetails")
    private String requestdetails;

    public Ussdpasswordreset() {
    }

    public Ussdpasswordreset(Integer tid) {
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
        if (!(object instanceof Ussdpasswordreset)) {
            return false;
        }
        Ussdpasswordreset other = (Ussdpasswordreset) object;
        if ((this.tid == null && other.tid != null) || (this.tid != null && !this.tid.equals(other.tid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.panafrica.umash.model.Ussdpasswordreset[ tid=" + tid + " ]";
    }
    
}
