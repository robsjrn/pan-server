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
@Table(name = "officelocation")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Officelocation.findAll", query = "SELECT o FROM Officelocation o"),
    @NamedQuery(name = "Officelocation.findByTid", query = "SELECT o FROM Officelocation o WHERE o.tid = :tid"),
    @NamedQuery(name = "Officelocation.findByDescription", query = "SELECT o FROM Officelocation o WHERE o.description = :description"),
    @NamedQuery(name = "Officelocation.findByLng", query = "SELECT o FROM Officelocation o WHERE o.lng = :lng"),
    @NamedQuery(name = "Officelocation.findByLat", query = "SELECT o FROM Officelocation o WHERE o.lat = :lat")})
public class Officelocation implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "tid")
    private Integer tid;
    @Size(max = 50)
    @Column(name = "description")
    private String description;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "lng")
    private Float lng;
    @Column(name = "lat")
    private Float lat;

    public Officelocation() {
    }

    public Officelocation(Integer tid) {
        this.tid = tid;
    }

    public Integer getTid() {
        return tid;
    }

    public void setTid(Integer tid) {
        this.tid = tid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Float getLng() {
        return lng;
    }

    public void setLng(Float lng) {
        this.lng = lng;
    }

    public Float getLat() {
        return lat;
    }

    public void setLat(Float lat) {
        this.lat = lat;
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
        if (!(object instanceof Officelocation)) {
            return false;
        }
        Officelocation other = (Officelocation) object;
        if ((this.tid == null && other.tid != null) || (this.tid != null && !this.tid.equals(other.tid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.panafrica.umash.model.Officelocation[ tid=" + tid + " ]";
    }
    
}
