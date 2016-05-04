/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panafrica.umash.model;

import com.panafrica.umash.dbListener.NewClient;
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
@EntityListeners({NewClient.class})
@Table(name = "ussdtemp")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Ussdtemp.findAll", query = "SELECT u FROM Ussdtemp u"),
    @NamedQuery(name = "Ussdtemp.findByTid", query = "SELECT u FROM Ussdtemp u WHERE u.tid = :tid"),
    @NamedQuery(name = "Ussdtemp.findByDocid", query = "SELECT u FROM Ussdtemp u WHERE u.docid = :docid"),
    @NamedQuery(name = "Ussdtemp.findByContact", query = "SELECT u FROM Ussdtemp u WHERE u.contact = :contact"),
    @NamedQuery(name = "Ussdtemp.findByDateofbirth", query = "SELECT u FROM Ussdtemp u WHERE u.dateofbirth = :dateofbirth"),
    @NamedQuery(name = "Ussdtemp.findByProductname", query = "SELECT u FROM Ussdtemp u WHERE u.productname = :productname"),
    @NamedQuery(name = "Ussdtemp.findByEntrydate", query = "SELECT u FROM Ussdtemp u WHERE u.entrydate = :entrydate"),
    @NamedQuery(name = "Ussdtemp.findByStatusid", query = "SELECT u FROM Ussdtemp u WHERE u.statusid = :statusid"),
    @NamedQuery(name = "Ussdtemp.findByStatusname", query = "SELECT u FROM Ussdtemp u WHERE u.statusname = :statusname")})
public class Ussdtemp implements Serializable {

    @Column(name = "premiumpayable")
    private Integer premiumpayable;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "tid")
    private Integer tid;
    @Size(max = 30)
    @Column(name = "docid")
    private String docid;
    @Size(max = 30)
    @Column(name = "contact")
    private String contact;
    @Size(max = 20)
    @Column(name = "dateofbirth")
    private String dateofbirth;
    @Size(max = 20)
    @Column(name = "productname")
    private String productname;
    @Column(name = "entrydate")
    @Temporal(TemporalType.DATE)
    private Date entrydate;
    @Column(name = "statusid")
    private Integer statusid;
    @Size(max = 30)
    @Column(name = "statusname")
    private String statusname;

    public Ussdtemp() {
    }

    public Ussdtemp(Integer tid) {
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

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getDateofbirth() {
        return dateofbirth;
    }

    public void setDateofbirth(String dateofbirth) {
        this.dateofbirth = dateofbirth;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public Date getEntrydate() {
        return entrydate;
    }

    public void setEntrydate(Date entrydate) {
        this.entrydate = entrydate;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tid != null ? tid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ussdtemp)) {
            return false;
        }
        Ussdtemp other = (Ussdtemp) object;
        if ((this.tid == null && other.tid != null) || (this.tid != null && !this.tid.equals(other.tid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.panafrica.umash.model.Ussdtemp[ tid=" + tid + " ]";
    }

    public Integer getPremiumpayable() {
        return premiumpayable;
    }

    public void setPremiumpayable(Integer premiumpayable) {
        this.premiumpayable = premiumpayable;
    }
    
}
