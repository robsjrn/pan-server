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
@Table(name = "users")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Users.findAll", query = "SELECT u FROM Users u"),
    @NamedQuery(name = "Users.findByUsertid", query = "SELECT u FROM Users u WHERE u.usertid = :usertid"),
    @NamedQuery(name = "Users.findByUserid", query = "SELECT u FROM Users u WHERE u.userid = :userid"),
    @NamedQuery(name = "Users.findByUsername", query = "SELECT u FROM Users u WHERE u.username = :username"),
    @NamedQuery(name = "Users.findByPasssword", query = "SELECT u FROM Users u WHERE u.passsword = :passsword"),
    @NamedQuery(name = "Users.findByDepartment", query = "SELECT u FROM Users u WHERE u.department = :department"),
    @NamedQuery(name = "Users.findByOtherDetails", query = "SELECT u FROM Users u WHERE u.otherDetails = :otherDetails"),
    @NamedQuery(name = "Users.findByCreatedby", query = "SELECT u FROM Users u WHERE u.createdby = :createdby"),
    @NamedQuery(name = "Users.findByCreateddate", query = "SELECT u FROM Users u WHERE u.createddate = :createddate"),
    @NamedQuery(name = "Users.findByStatusid", query = "SELECT u FROM Users u WHERE u.statusid = :statusid"),
    @NamedQuery(name = "Users.findByStatusname", query = "SELECT u FROM Users u WHERE u.statusname = :statusname"),
    @NamedQuery(name = "Users.findByStatusdescription", query = "SELECT u FROM Users u WHERE u.statusdescription = :statusdescription"),
    @NamedQuery(name = "Users.findByEmail", query = "SELECT u FROM Users u WHERE u.email = :email"),
    @NamedQuery(name = "Users.findByNames", query = "SELECT u FROM Users u WHERE u.names = :names")})
public class Users implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "usertid")
    private Integer usertid;
    @Column(name = "userid")
    private Integer userid;
    @Size(max = 30)
    @Column(name = "username")
    private String username;
    @Size(max = 40)
    @Column(name = "passsword")
    private String passsword;
    @Size(max = 20)
    @Column(name = "department")
    private String department;
    @Size(max = 100)
    @Column(name = "OtherDetails")
    private String otherDetails;
    @Size(max = 50)
    @Column(name = "createdby")
    private String createdby;
    @Column(name = "createddate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createddate;
    @Column(name = "statusid")
    private Integer statusid;
    @Size(max = 50)
    @Column(name = "statusname")
    private String statusname;
    @Size(max = 100)
    @Column(name = "statusdescription")
    private String statusdescription;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 30)
    @Column(name = "email")
    private String email;
    @Size(max = 50)
    @Column(name = "names")
    private String names;

    public Users() {
    }

    public Users(Integer usertid) {
        this.usertid = usertid;
    }

    public Integer getUsertid() {
        return usertid;
    }

    public void setUsertid(Integer usertid) {
        this.usertid = usertid;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasssword() {
        return passsword;
    }

    public void setPasssword(String passsword) {
        this.passsword = passsword;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getOtherDetails() {
        return otherDetails;
    }

    public void setOtherDetails(String otherDetails) {
        this.otherDetails = otherDetails;
    }

    public String getCreatedby() {
        return createdby;
    }

    public void setCreatedby(String createdby) {
        this.createdby = createdby;
    }

    public Date getCreateddate() {
        return createddate;
    }

    public void setCreateddate(Date createddate) {
        this.createddate = createddate;
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

    public String getStatusdescription() {
        return statusdescription;
    }

    public void setStatusdescription(String statusdescription) {
        this.statusdescription = statusdescription;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (usertid != null ? usertid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Users)) {
            return false;
        }
        Users other = (Users) object;
        if ((this.usertid == null && other.usertid != null) || (this.usertid != null && !this.usertid.equals(other.usertid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.panafrica.umash.model.Users[ usertid=" + usertid + " ]";
    }
    
}
