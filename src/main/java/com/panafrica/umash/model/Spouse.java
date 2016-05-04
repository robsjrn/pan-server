/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panafrica.umash.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author User
 */
@Entity
@Table(name = "spouse")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Spouse.findAll", query = "SELECT s FROM Spouse s"),
    @NamedQuery(name = "Spouse.findByTid", query = "SELECT s FROM Spouse s WHERE s.tid = :tid"),
    @NamedQuery(name = "Spouse.findByClientid", query = "SELECT s FROM Spouse s WHERE s.clientid = :clientid"),
    @NamedQuery(name = "Spouse.findBySpousename", query = "SELECT s FROM Spouse s WHERE s.spousename = :spousename"),
    @NamedQuery(name = "Spouse.findBySpouseid", query = "SELECT s FROM Spouse s WHERE s.spouseid = :spouseid"),
    @NamedQuery(name = "Spouse.findBySpousephone", query = "SELECT s FROM Spouse s WHERE s.spousephone = :spousephone"),
    @NamedQuery(name = "Spouse.findBySpousedob", query = "SELECT s FROM Spouse s WHERE s.spousedob = :spousedob"),
    @NamedQuery(name = "Spouse.findBySpouseage", query = "SELECT s FROM Spouse s WHERE s.spouseage = :spouseage"),
    @NamedQuery(name = "Spouse.findByProductid", query = "SELECT s FROM Spouse s WHERE s.productid = :productid")})
public class Spouse implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "tid")
    private Integer tid;
    @Size(max = 30)
    @Column(name = "clientid")
    private String clientid;
    @Size(max = 30)
    @Column(name = "spousename")
    private String spousename;
    @Size(max = 30)
    @Column(name = "spouseid")
    private String spouseid;
    @Size(max = 30)
    @Column(name = "spousephone")
    private String spousephone;
    @Size(max = 40)
    @Column(name = "spousedob")
    private String spousedob;
    @Column(name = "spouseage")
    private Integer spouseage;
    @Column(name = "productid")
    private Integer productid;

    public Spouse() {
    }

    public Spouse(Integer tid) {
        this.tid = tid;
    }

    public Integer getTid() {
        return tid;
    }

    public void setTid(Integer tid) {
        this.tid = tid;
    }

    public String getClientid() {
        return clientid;
    }

    public void setClientid(String clientid) {
        this.clientid = clientid;
    }

    public String getSpousename() {
        return spousename;
    }

    public void setSpousename(String spousename) {
        this.spousename = spousename;
    }

    public String getSpouseid() {
        return spouseid;
    }

    public void setSpouseid(String spouseid) {
        this.spouseid = spouseid;
    }

    public String getSpousephone() {
        return spousephone;
    }

    public void setSpousephone(String spousephone) {
        this.spousephone = spousephone;
    }

    public String getSpousedob() {
        return spousedob;
    }

    public void setSpousedob(String spousedob) {
        this.spousedob = spousedob;
    }

    public Integer getSpouseage() {
        return spouseage;
    }

    public void setSpouseage(Integer spouseage) {
        this.spouseage = spouseage;
    }

    public Integer getProductid() {
        return productid;
    }

    public void setProductid(Integer productid) {
        this.productid = productid;
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
        if (!(object instanceof Spouse)) {
            return false;
        }
        Spouse other = (Spouse) object;
        if ((this.tid == null && other.tid != null) || (this.tid != null && !this.tid.equals(other.tid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.panafrica.umash.model.Spouse[ tid=" + tid + " ]";
    }
    
}
