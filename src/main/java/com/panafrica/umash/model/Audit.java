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
@Table(name = "audit")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Audit.findAll", query = "SELECT a FROM Audit a"),
    @NamedQuery(name = "Audit.findByTid", query = "SELECT a FROM Audit a WHERE a.tid = :tid"),
    @NamedQuery(name = "Audit.findByUsername", query = "SELECT a FROM Audit a WHERE a.username = :username"),
    @NamedQuery(name = "Audit.findByActiondone", query = "SELECT a FROM Audit a WHERE a.actiondone = :actiondone"),
    @NamedQuery(name = "Audit.findByActiondate", query = "SELECT a FROM Audit a WHERE a.actiondate = :actiondate")})
public class Audit implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "tid")
    private Integer tid;
    @Size(max = 50)
    @Column(name = "username")
    private String username;
    @Size(max = 100)
    @Column(name = "actiondone")
    private String actiondone;
    @Column(name = "actiondate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date actiondate;

    public Audit() {
    }

    public Audit(Integer tid) {
        this.tid = tid;
    }

    public Integer getTid() {
        return tid;
    }

    public void setTid(Integer tid) {
        this.tid = tid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getActiondone() {
        return actiondone;
    }

    public void setActiondone(String actiondone) {
        this.actiondone = actiondone;
    }

    public Date getActiondate() {
        return actiondate;
    }

    public void setActiondate(Date actiondate) {
        this.actiondate = actiondate;
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
        if (!(object instanceof Audit)) {
            return false;
        }
        Audit other = (Audit) object;
        if ((this.tid == null && other.tid != null) || (this.tid != null && !this.tid.equals(other.tid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.panafrica.umash.model.Audit[ tid=" + tid + " ]";
    }
    
}
