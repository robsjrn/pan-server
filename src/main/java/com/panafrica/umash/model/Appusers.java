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
@Table(name = "appusers")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Appusers.findAll", query = "SELECT a FROM Appusers a"),
    @NamedQuery(name = "Appusers.findByRid", query = "SELECT a FROM Appusers a WHERE a.rid = :rid"),
    @NamedQuery(name = "Appusers.findByClientid", query = "SELECT a FROM Appusers a WHERE a.clientid = :clientid"),
    @NamedQuery(name = "Appusers.findByUsername", query = "SELECT a FROM Appusers a WHERE a.username = :username"),
    @NamedQuery(name = "Appusers.findByPassword", query = "SELECT a FROM Appusers a WHERE a.password = :password"),
    @NamedQuery(name = "Appusers.findByStatusid", query = "SELECT a FROM Appusers a WHERE a.statusid = :statusid"),
    @NamedQuery(name = "Appusers.findByStatusname", query = "SELECT a FROM Appusers a WHERE a.statusname = :statusname"),
    @NamedQuery(name = "Appusers.findByDateCreated", query = "SELECT a FROM Appusers a WHERE a.dateCreated = :dateCreated"),
    @NamedQuery(name = "Appusers.findByCreatedby", query = "SELECT a FROM Appusers a WHERE a.createdby = :createdby"),
    @NamedQuery(name = "Appusers.findByUserrole", query = "SELECT a FROM Appusers a WHERE a.userrole = :userrole"),
    @NamedQuery(name = "Appusers.findByPhonenumber", query = "SELECT a FROM Appusers a WHERE a.phonenumber = :phonenumber"),
    @NamedQuery(name = "Appusers.findByProgressstatusid", query = "SELECT a FROM Appusers a WHERE a.progressstatusid = :progressstatusid"),
    @NamedQuery(name = "Appusers.findByProgressstatusname", query = "SELECT a FROM Appusers a WHERE a.progressstatusname = :progressstatusname")})
public class Appusers implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "rid")
    private Integer rid;
    @Size(max = 20)
    @Column(name = "clientid")
    private String clientid;
    @Size(max = 30)
    @Column(name = "username")
    private String username;
    @Size(max = 100)
    @Column(name = "password")
    private String password;
    @Column(name = "statusid")
    private Integer statusid;
    @Size(max = 30)
    @Column(name = "statusname")
    private String statusname;
    @Column(name = "dateCreated")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreated;
    @Size(max = 20)
    @Column(name = "createdby")
    private String createdby;
    @Size(max = 20)
    @Column(name = "userrole")
    private String userrole;
    @Size(max = 30)
    @Column(name = "phonenumber")
    private String phonenumber;
    @Column(name = "progressstatusid")
    private Integer progressstatusid;
    @Size(max = 60)
    @Column(name = "progressstatusname")
    private String progressstatusname;

    public Appusers() {
    }

    public Appusers(Integer rid) {
        this.rid = rid;
    }

    public Integer getRid() {
        return rid;
    }

    public void setRid(Integer rid) {
        this.rid = rid;
    }

    public String getClientid() {
        return clientid;
    }

    public void setClientid(String clientid) {
        this.clientid = clientid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getCreatedby() {
        return createdby;
    }

    public void setCreatedby(String createdby) {
        this.createdby = createdby;
    }

    public String getUserrole() {
        return userrole;
    }

    public void setUserrole(String userrole) {
        this.userrole = userrole;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public Integer getProgressstatusid() {
        return progressstatusid;
    }

    public void setProgressstatusid(Integer progressstatusid) {
        this.progressstatusid = progressstatusid;
    }

    public String getProgressstatusname() {
        return progressstatusname;
    }

    public void setProgressstatusname(String progressstatusname) {
        this.progressstatusname = progressstatusname;
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
        if (!(object instanceof Appusers)) {
            return false;
        }
        Appusers other = (Appusers) object;
        if ((this.rid == null && other.rid != null) || (this.rid != null && !this.rid.equals(other.rid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.panafrica.umash.model.Appusers[ rid=" + rid + " ]";
    }
    
}
