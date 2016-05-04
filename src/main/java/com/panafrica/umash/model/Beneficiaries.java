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
@Table(name = "beneficiaries")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Beneficiaries.findAll", query = "SELECT b FROM Beneficiaries b"),
    @NamedQuery(name = "Beneficiaries.findByRid", query = "SELECT b FROM Beneficiaries b WHERE b.rid = :rid"),
    @NamedQuery(name = "Beneficiaries.findByClientid", query = "SELECT b FROM Beneficiaries b WHERE b.clientid = :clientid"),
    @NamedQuery(name = "Beneficiaries.findByBeneficiaryId", query = "SELECT b FROM Beneficiaries b WHERE b.beneficiaryId = :beneficiaryId"),
    @NamedQuery(name = "Beneficiaries.findByBeneficiaryNames", query = "SELECT b FROM Beneficiaries b WHERE b.beneficiaryNames = :beneficiaryNames"),
    @NamedQuery(name = "Beneficiaries.findByBeneficiaryContacts", query = "SELECT b FROM Beneficiaries b WHERE b.beneficiaryContacts = :beneficiaryContacts"),
    @NamedQuery(name = "Beneficiaries.findByBeneficiaryAge", query = "SELECT b FROM Beneficiaries b WHERE b.beneficiaryAge = :beneficiaryAge"),
    @NamedQuery(name = "Beneficiaries.findByBeneficiaryDob", query = "SELECT b FROM Beneficiaries b WHERE b.beneficiaryDob = :beneficiaryDob"),
    @NamedQuery(name = "Beneficiaries.findByProductid", query = "SELECT b FROM Beneficiaries b WHERE b.productid = :productid")})
public class Beneficiaries implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "rid")
    private Integer rid;
    @Size(max = 30)
    @Column(name = "clientid")
    private String clientid;
    @Size(max = 30)
    @Column(name = "beneficiary_id")
    private String beneficiaryId;
    @Size(max = 30)
    @Column(name = "beneficiary_names")
    private String beneficiaryNames;
    @Size(max = 30)
    @Column(name = "beneficiary_contacts")
    private String beneficiaryContacts;
    @Column(name = "beneficiary_age")
    private Integer beneficiaryAge;
    @Size(max = 30)
    @Column(name = "beneficiary_dob")
    private String beneficiaryDob;
    @Column(name = "productid")
    private Integer productid;

    public Beneficiaries() {
    }

    public Beneficiaries(Integer rid) {
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

    public String getBeneficiaryId() {
        return beneficiaryId;
    }

    public void setBeneficiaryId(String beneficiaryId) {
        this.beneficiaryId = beneficiaryId;
    }

    public String getBeneficiaryNames() {
        return beneficiaryNames;
    }

    public void setBeneficiaryNames(String beneficiaryNames) {
        this.beneficiaryNames = beneficiaryNames;
    }

    public String getBeneficiaryContacts() {
        return beneficiaryContacts;
    }

    public void setBeneficiaryContacts(String beneficiaryContacts) {
        this.beneficiaryContacts = beneficiaryContacts;
    }

    public Integer getBeneficiaryAge() {
        return beneficiaryAge;
    }

    public void setBeneficiaryAge(Integer beneficiaryAge) {
        this.beneficiaryAge = beneficiaryAge;
    }

    public String getBeneficiaryDob() {
        return beneficiaryDob;
    }

    public void setBeneficiaryDob(String beneficiaryDob) {
        this.beneficiaryDob = beneficiaryDob;
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
        if (!(object instanceof Beneficiaries)) {
            return false;
        }
        Beneficiaries other = (Beneficiaries) object;
        if ((this.rid == null && other.rid != null) || (this.rid != null && !this.rid.equals(other.rid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.panafrica.umash.model.Beneficiaries[ rid=" + rid + " ]";
    }
    
}
