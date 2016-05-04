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
@Table(name = "apptokens")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Apptokens.findAll", query = "SELECT a FROM Apptokens a"),
    @NamedQuery(name = "Apptokens.findByTid", query = "SELECT a FROM Apptokens a WHERE a.tid = :tid"),
    @NamedQuery(name = "Apptokens.findByToken", query = "SELECT a FROM Apptokens a WHERE a.token = :token"),
    @NamedQuery(name = "Apptokens.findByTokendate", query = "SELECT a FROM Apptokens a WHERE a.tokendate = :tokendate"),
    @NamedQuery(name = "Apptokens.findByDevice", query = "SELECT a FROM Apptokens a WHERE a.device = :device")})
public class Apptokens implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "tid")
    private Integer tid;
    @Size(max = 300)
    @Column(name = "token")
    private String token;
    @Column(name = "tokendate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date tokendate;
    @Size(max = 50)
    @Column(name = "device")
    private String device;

    public Apptokens() {
    }

    public Apptokens(Integer tid) {
        this.tid = tid;
    }

    public Integer getTid() {
        return tid;
    }

    public void setTid(Integer tid) {
        this.tid = tid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getTokendate() {
        return tokendate;
    }

    public void setTokendate(Date tokendate) {
        this.tokendate = tokendate;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
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
        if (!(object instanceof Apptokens)) {
            return false;
        }
        Apptokens other = (Apptokens) object;
        if ((this.tid == null && other.tid != null) || (this.tid != null && !this.tid.equals(other.tid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.panafrica.umash.model.Apptokens[ tid=" + tid + " ]";
    }
    
}
