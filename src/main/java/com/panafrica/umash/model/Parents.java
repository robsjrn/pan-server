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
@Table(name = "parents")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Parents.findAll", query = "SELECT p FROM Parents p"),
    @NamedQuery(name = "Parents.findByRid", query = "SELECT p FROM Parents p WHERE p.rid = :rid"),
    @NamedQuery(name = "Parents.findByParentNames", query = "SELECT p FROM Parents p WHERE p.parentNames = :parentNames"),
    @NamedQuery(name = "Parents.findByParentId", query = "SELECT p FROM Parents p WHERE p.parentId = :parentId"),
    @NamedQuery(name = "Parents.findByParentDob", query = "SELECT p FROM Parents p WHERE p.parentDob = :parentDob"),
    @NamedQuery(name = "Parents.findByParentAge", query = "SELECT p FROM Parents p WHERE p.parentAge = :parentAge"),
    @NamedQuery(name = "Parents.findByClientid", query = "SELECT p FROM Parents p WHERE p.clientid = :clientid"),
    @NamedQuery(name = "Parents.findByProductid", query = "SELECT p FROM Parents p WHERE p.productid = :productid")})
public class Parents implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "rid")
    private Integer rid;
    @Size(max = 30)
    @Column(name = "parent_names")
    private String parentNames;
    @Size(max = 30)
    @Column(name = "parent_id")
    private String parentId;
    @Size(max = 30)
    @Column(name = "parent_dob")
    private String parentDob;
    @Column(name = "parent_age")
    private Integer parentAge;
    @Size(max = 30)
    @Column(name = "clientid")
    private String clientid;
    @Column(name = "productid")
    private Integer productid;

    public Parents() {
    }

    public Parents(Integer rid) {
        this.rid = rid;
    }

    public Integer getRid() {
        return rid;
    }

    public void setRid(Integer rid) {
        this.rid = rid;
    }

    public String getParentNames() {
        return parentNames;
    }

    public void setParentNames(String parentNames) {
        this.parentNames = parentNames;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getParentDob() {
        return parentDob;
    }

    public void setParentDob(String parentDob) {
        this.parentDob = parentDob;
    }

    public Integer getParentAge() {
        return parentAge;
    }

    public void setParentAge(Integer parentAge) {
        this.parentAge = parentAge;
    }

    public String getClientid() {
        return clientid;
    }

    public void setClientid(String clientid) {
        this.clientid = clientid;
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
        hash += (rid != null ? rid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Parents)) {
            return false;
        }
        Parents other = (Parents) object;
        if ((this.rid == null && other.rid != null) || (this.rid != null && !this.rid.equals(other.rid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.panafrica.umash.model.Parents[ rid=" + rid + " ]";
    }
    
}
