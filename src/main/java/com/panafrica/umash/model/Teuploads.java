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
@Table(name = "teuploads")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Teuploads.findAll", query = "SELECT t FROM Teuploads t"),
    @NamedQuery(name = "Teuploads.findByTid", query = "SELECT t FROM Teuploads t WHERE t.tid = :tid"),
    @NamedQuery(name = "Teuploads.findByFilename", query = "SELECT t FROM Teuploads t WHERE t.filename = :filename"),
    @NamedQuery(name = "Teuploads.findByUploaddate", query = "SELECT t FROM Teuploads t WHERE t.uploaddate = :uploaddate"),
    @NamedQuery(name = "Teuploads.findByProcessedby", query = "SELECT t FROM Teuploads t WHERE t.processedby = :processedby")})
public class Teuploads implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "tid")
    private Integer tid;
    @Size(max = 100)
    @Column(name = "filename")
    private String filename;
    @Column(name = "uploaddate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date uploaddate;
    @Size(max = 50)
    @Column(name = "processedby")
    private String processedby;

    public Teuploads() {
    }

    public Teuploads(Integer tid) {
        this.tid = tid;
    }

    public Integer getTid() {
        return tid;
    }

    public void setTid(Integer tid) {
        this.tid = tid;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Date getUploaddate() {
        return uploaddate;
    }

    public void setUploaddate(Date uploaddate) {
        this.uploaddate = uploaddate;
    }

    public String getProcessedby() {
        return processedby;
    }

    public void setProcessedby(String processedby) {
        this.processedby = processedby;
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
        if (!(object instanceof Teuploads)) {
            return false;
        }
        Teuploads other = (Teuploads) object;
        if ((this.tid == null && other.tid != null) || (this.tid != null && !this.tid.equals(other.tid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.panafrica.umash.model.Teuploads[ tid=" + tid + " ]";
    }
    
}
