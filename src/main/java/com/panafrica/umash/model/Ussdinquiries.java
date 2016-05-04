/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panafrica.umash.model;

import com.panafrica.umash.dbListener.NewInquiry;
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
@EntityListeners({NewInquiry.class})
@Table(name = "ussdinquiries")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Ussdinquiries.findAll", query = "SELECT u FROM Ussdinquiries u"),
    @NamedQuery(name = "Ussdinquiries.findByTid", query = "SELECT u FROM Ussdinquiries u WHERE u.tid = :tid"),
    @NamedQuery(name = "Ussdinquiries.findByInquirydate", query = "SELECT u FROM Ussdinquiries u WHERE u.inquirydate = :inquirydate"),
    @NamedQuery(name = "Ussdinquiries.findByInquirydetails", query = "SELECT u FROM Ussdinquiries u WHERE u.inquirydetails = :inquirydetails"),
    @NamedQuery(name = "Ussdinquiries.findByContact", query = "SELECT u FROM Ussdinquiries u WHERE u.contact = :contact")})
public class Ussdinquiries implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "tid")
    private Integer tid;
    @Column(name = "inquirydate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date inquirydate;
    @Size(max = 50)
    @Column(name = "inquirydetails")
    private String inquirydetails;
    @Size(max = 50)
    @Column(name = "contact")
    private String contact;

    public Ussdinquiries() {
    }

    public Ussdinquiries(Integer tid) {
        this.tid = tid;
    }

    public Integer getTid() {
        return tid;
    }

    public void setTid(Integer tid) {
        this.tid = tid;
    }

    public Date getInquirydate() {
        return inquirydate;
    }

    public void setInquirydate(Date inquirydate) {
        this.inquirydate = inquirydate;
    }

    public String getInquirydetails() {
        return inquirydetails;
    }

    public void setInquirydetails(String inquirydetails) {
        this.inquirydetails = inquirydetails;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
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
        if (!(object instanceof Ussdinquiries)) {
            return false;
        }
        Ussdinquiries other = (Ussdinquiries) object;
        if ((this.tid == null && other.tid != null) || (this.tid != null && !this.tid.equals(other.tid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.panafrica.umash.model.Ussdinquiries[ tid=" + tid + " ]";
    }
    
}
