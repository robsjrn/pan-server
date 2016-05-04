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
@Table(name = "childrens")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Childrens.findAll", query = "SELECT c FROM Childrens c"),
    @NamedQuery(name = "Childrens.findByRid", query = "SELECT c FROM Childrens c WHERE c.rid = :rid"),
    @NamedQuery(name = "Childrens.findByChildName", query = "SELECT c FROM Childrens c WHERE c.childName = :childName"),
    @NamedQuery(name = "Childrens.findByChildAge", query = "SELECT c FROM Childrens c WHERE c.childAge = :childAge"),
    @NamedQuery(name = "Childrens.findByChildDob", query = "SELECT c FROM Childrens c WHERE c.childDob = :childDob"),
    @NamedQuery(name = "Childrens.findByClientid", query = "SELECT c FROM Childrens c WHERE c.clientid = :clientid"),
    @NamedQuery(name = "Childrens.findByProductid", query = "SELECT c FROM Childrens c WHERE c.productid = :productid")})
public class Childrens implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "rid")
    private Integer rid;
    @Size(max = 30)
    @Column(name = "child_name")
    private String childName;
    @Column(name = "child_age")
    private Integer childAge;
    @Size(max = 30)
    @Column(name = "child_dob")
    private String childDob;
    @Size(max = 30)
    @Column(name = "clientid")
    private String clientid;
    @Column(name = "productid")
    private Integer productid;

    public Childrens() {
    }

    public Childrens(Integer rid) {
        this.rid = rid;
    }

    public Integer getRid() {
        return rid;
    }

    public void setRid(Integer rid) {
        this.rid = rid;
    }

    public String getChildName() {
        return childName;
    }

    public void setChildName(String childName) {
        this.childName = childName;
    }

    public Integer getChildAge() {
        return childAge;
    }

    public void setChildAge(Integer childAge) {
        this.childAge = childAge;
    }

    public String getChildDob() {
        return childDob;
    }

    public void setChildDob(String childDob) {
        this.childDob = childDob;
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
        if (!(object instanceof Childrens)) {
            return false;
        }
        Childrens other = (Childrens) object;
        if ((this.rid == null && other.rid != null) || (this.rid != null && !this.rid.equals(other.rid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.panafrica.umash.model.Childrens[ rid=" + rid + " ]";
    }
    
}
