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
@Table(name = "clientspoducts")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Clientspoducts.findAll", query = "SELECT c FROM Clientspoducts c"),
    @NamedQuery(name = "Clientspoducts.findByTid", query = "SELECT c FROM Clientspoducts c WHERE c.tid = :tid"),
    @NamedQuery(name = "Clientspoducts.findByClientid", query = "SELECT c FROM Clientspoducts c WHERE c.clientid = :clientid"),
    @NamedQuery(name = "Clientspoducts.findByProductname", query = "SELECT c FROM Clientspoducts c WHERE c.productname = :productname"),
    @NamedQuery(name = "Clientspoducts.findByPremiumpayable", query = "SELECT c FROM Clientspoducts c WHERE c.premiumpayable = :premiumpayable"),
    @NamedQuery(name = "Clientspoducts.findBySumassured", query = "SELECT c FROM Clientspoducts c WHERE c.sumassured = :sumassured"),
    @NamedQuery(name = "Clientspoducts.findByProductid", query = "SELECT c FROM Clientspoducts c WHERE c.productid = :productid")})
public class Clientspoducts implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "tid")
    private Integer tid;
    @Size(max = 50)
    @Column(name = "clientid")
    private String clientid;
    @Size(max = 30)
    @Column(name = "productname")
    private String productname;
    @Column(name = "premiumpayable")
    private Integer premiumpayable;
    @Column(name = "sumassured")
    private Integer sumassured;
    @Column(name = "productid")
    private Integer productid;

    public Clientspoducts() {
    }

    public Clientspoducts(Integer tid) {
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

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public Integer getPremiumpayable() {
        return premiumpayable;
    }

    public void setPremiumpayable(Integer premiumpayable) {
        this.premiumpayable = premiumpayable;
    }

    public Integer getSumassured() {
        return sumassured;
    }

    public void setSumassured(Integer sumassured) {
        this.sumassured = sumassured;
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
        if (!(object instanceof Clientspoducts)) {
            return false;
        }
        Clientspoducts other = (Clientspoducts) object;
        if ((this.tid == null && other.tid != null) || (this.tid != null && !this.tid.equals(other.tid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.panafrica.umash.model.Clientspoducts[ tid=" + tid + " ]";
    }
    
}
