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
import javax.persistence.Lob;
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
@Table(name = "clients")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Clients.findAll", query = "SELECT c FROM Clients c"),
    @NamedQuery(name = "Clients.findByRid", query = "SELECT c FROM Clients c WHERE c.rid = :rid"),
    @NamedQuery(name = "Clients.findByDocid", query = "SELECT c FROM Clients c WHERE c.docid = :docid"),
    @NamedQuery(name = "Clients.findByClientnames", query = "SELECT c FROM Clients c WHERE c.clientnames = :clientnames"),
    @NamedQuery(name = "Clients.findByContacts", query = "SELECT c FROM Clients c WHERE c.contacts = :contacts"),
    @NamedQuery(name = "Clients.findByAge", query = "SELECT c FROM Clients c WHERE c.age = :age"),
    @NamedQuery(name = "Clients.findByPin", query = "SELECT c FROM Clients c WHERE c.pin = :pin"),
    @NamedQuery(name = "Clients.findByEmail", query = "SELECT c FROM Clients c WHERE c.email = :email"),
    @NamedQuery(name = "Clients.findByStatusid", query = "SELECT c FROM Clients c WHERE c.statusid = :statusid"),
    @NamedQuery(name = "Clients.findByStatusname", query = "SELECT c FROM Clients c WHERE c.statusname = :statusname"),
    @NamedQuery(name = "Clients.findByRegistrationdate", query = "SELECT c FROM Clients c WHERE c.registrationdate = :registrationdate"),
    @NamedQuery(name = "Clients.findByDob", query = "SELECT c FROM Clients c WHERE c.dob = :dob"),
    @NamedQuery(name = "Clients.findByPremiumpayable", query = "SELECT c FROM Clients c WHERE c.premiumpayable = :premiumpayable"),
    @NamedQuery(name = "Clients.findByChannelid", query = "SELECT c FROM Clients c WHERE c.channelid = :channelid"),
    @NamedQuery(name = "Clients.findByChannelname", query = "SELECT c FROM Clients c WHERE c.channelname = :channelname"),
    @NamedQuery(name = "Clients.findByChildrenAvailable", query = "SELECT c FROM Clients c WHERE c.childrenAvailable = :childrenAvailable"),
    @NamedQuery(name = "Clients.findByParentAvailable", query = "SELECT c FROM Clients c WHERE c.parentAvailable = :parentAvailable"),
    @NamedQuery(name = "Clients.findByProductname", query = "SELECT c FROM Clients c WHERE c.productname = :productname"),
    @NamedQuery(name = "Clients.findByPaymentstatus", query = "SELECT c FROM Clients c WHERE c.paymentstatus = :paymentstatus"),
    @NamedQuery(name = "Clients.findByPaymentDescription", query = "SELECT c FROM Clients c WHERE c.paymentDescription = :paymentDescription"),
    @NamedQuery(name = "Clients.findByPaymentChannel", query = "SELECT c FROM Clients c WHERE c.paymentChannel = :paymentChannel"),
    @NamedQuery(name = "Clients.findByPaymentid", query = "SELECT c FROM Clients c WHERE c.paymentid = :paymentid"),
    @NamedQuery(name = "Clients.findByPolicystatus", query = "SELECT c FROM Clients c WHERE c.policystatus = :policystatus"),
    @NamedQuery(name = "Clients.findByPolicyid", query = "SELECT c FROM Clients c WHERE c.policyid = :policyid"),
    @NamedQuery(name = "Clients.findByPolicycomment", query = "SELECT c FROM Clients c WHERE c.policycomment = :policycomment"),
    @NamedQuery(name = "Clients.findByPolicyConfirmedBy", query = "SELECT c FROM Clients c WHERE c.policyConfirmedBy = :policyConfirmedBy"),
    @NamedQuery(name = "Clients.findByPolicyconfirmedDate", query = "SELECT c FROM Clients c WHERE c.policyconfirmedDate = :policyconfirmedDate"),
    @NamedQuery(name = "Clients.findBySpouseAvailable", query = "SELECT c FROM Clients c WHERE c.spouseAvailable = :spouseAvailable"),
    @NamedQuery(name = "Clients.findBySumassured", query = "SELECT c FROM Clients c WHERE c.sumassured = :sumassured"),
    @NamedQuery(name = "Clients.findByTEUploadDate", query = "SELECT c FROM Clients c WHERE c.tEUploadDate = :tEUploadDate"),
    @NamedQuery(name = "Clients.findByPaymentdate", query = "SELECT c FROM Clients c WHERE c.paymentdate = :paymentdate"),
    @NamedQuery(name = "Clients.findByAmountpaid", query = "SELECT c FROM Clients c WHERE c.amountpaid = :amountpaid")})
public class Clients implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "rid")
    private Integer rid;
    @Size(max = 30)
    @Column(name = "docid")
    private String docid;
    @Size(max = 30)
    @Column(name = "Clientnames")
    private String clientnames;
    @Size(max = 30)
    @Column(name = "contacts")
    private String contacts;
    @Column(name = "age")
    private Integer age;
    @Size(max = 20)
    @Column(name = "pin")
    private String pin;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 20)
    @Column(name = "email")
    private String email;
    @Column(name = "statusid")
    private Integer statusid;
    @Size(max = 60)
    @Column(name = "statusname")
    private String statusname;
    @Column(name = "registrationdate")
    @Temporal(TemporalType.DATE)
    private Date registrationdate;
    @Lob
    @Column(name = "photo")
    private byte[] photo;
    @Size(max = 30)
    @Column(name = "dob")
    private String dob;
    @Column(name = "premiumpayable")
    private Integer premiumpayable;
    @Column(name = "channelid")
    private Integer channelid;
    @Size(max = 20)
    @Column(name = "channelname")
    private String channelname;
    @Column(name = "childrenAvailable")
    private Integer childrenAvailable;
    @Column(name = "parentAvailable")
    private Integer parentAvailable;
    @Size(max = 10)
    @Column(name = "productname")
    private String productname;
    @Column(name = "paymentstatus")
    private Integer paymentstatus;
    @Size(max = 100)
    @Column(name = "paymentDescription")
    private String paymentDescription;
    @Size(max = 40)
    @Column(name = "paymentChannel")
    private String paymentChannel;
    @Size(max = 50)
    @Column(name = "paymentid")
    private String paymentid;
    @Column(name = "policystatus")
    private Integer policystatus;
    @Size(max = 50)
    @Column(name = "policyid")
    private String policyid;
    @Size(max = 60)
    @Column(name = "policycomment")
    private String policycomment;
    @Size(max = 30)
    @Column(name = "policyConfirmedBy")
    private String policyConfirmedBy;
    @Column(name = "policyconfirmedDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date policyconfirmedDate;
    @Column(name = "spouseAvailable")
    private Integer spouseAvailable;
    @Column(name = "sumassured")
    private Integer sumassured;
    @Column(name = "TEUploadDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date tEUploadDate;
    @Column(name = "paymentdate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date paymentdate;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "amountpaid")
    private Float amountpaid;

    public Clients() {
    }

    public Clients(Integer rid) {
        this.rid = rid;
    }

    public Integer getRid() {
        return rid;
    }

    public void setRid(Integer rid) {
        this.rid = rid;
    }

    public String getDocid() {
        return docid;
    }

    public void setDocid(String docid) {
        this.docid = docid;
    }

    public String getClientnames() {
        return clientnames;
    }

    public void setClientnames(String clientnames) {
        this.clientnames = clientnames;
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public Date getRegistrationdate() {
        return registrationdate;
    }

    public void setRegistrationdate(Date registrationdate) {
        this.registrationdate = registrationdate;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public Integer getPremiumpayable() {
        return premiumpayable;
    }

    public void setPremiumpayable(Integer premiumpayable) {
        this.premiumpayable = premiumpayable;
    }

    public Integer getChannelid() {
        return channelid;
    }

    public void setChannelid(Integer channelid) {
        this.channelid = channelid;
    }

    public String getChannelname() {
        return channelname;
    }

    public void setChannelname(String channelname) {
        this.channelname = channelname;
    }

    public Integer getChildrenAvailable() {
        return childrenAvailable;
    }

    public void setChildrenAvailable(Integer childrenAvailable) {
        this.childrenAvailable = childrenAvailable;
    }

    public Integer getParentAvailable() {
        return parentAvailable;
    }

    public void setParentAvailable(Integer parentAvailable) {
        this.parentAvailable = parentAvailable;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public Integer getPaymentstatus() {
        return paymentstatus;
    }

    public void setPaymentstatus(Integer paymentstatus) {
        this.paymentstatus = paymentstatus;
    }

    public String getPaymentDescription() {
        return paymentDescription;
    }

    public void setPaymentDescription(String paymentDescription) {
        this.paymentDescription = paymentDescription;
    }

    public String getPaymentChannel() {
        return paymentChannel;
    }

    public void setPaymentChannel(String paymentChannel) {
        this.paymentChannel = paymentChannel;
    }

    public String getPaymentid() {
        return paymentid;
    }

    public void setPaymentid(String paymentid) {
        this.paymentid = paymentid;
    }

    public Integer getPolicystatus() {
        return policystatus;
    }

    public void setPolicystatus(Integer policystatus) {
        this.policystatus = policystatus;
    }

    public String getPolicyid() {
        return policyid;
    }

    public void setPolicyid(String policyid) {
        this.policyid = policyid;
    }

    public String getPolicycomment() {
        return policycomment;
    }

    public void setPolicycomment(String policycomment) {
        this.policycomment = policycomment;
    }

    public String getPolicyConfirmedBy() {
        return policyConfirmedBy;
    }

    public void setPolicyConfirmedBy(String policyConfirmedBy) {
        this.policyConfirmedBy = policyConfirmedBy;
    }

    public Date getPolicyconfirmedDate() {
        return policyconfirmedDate;
    }

    public void setPolicyconfirmedDate(Date policyconfirmedDate) {
        this.policyconfirmedDate = policyconfirmedDate;
    }

    public Integer getSpouseAvailable() {
        return spouseAvailable;
    }

    public void setSpouseAvailable(Integer spouseAvailable) {
        this.spouseAvailable = spouseAvailable;
    }

    public Integer getSumassured() {
        return sumassured;
    }

    public void setSumassured(Integer sumassured) {
        this.sumassured = sumassured;
    }

    public Date getTEUploadDate() {
        return tEUploadDate;
    }

    public void setTEUploadDate(Date tEUploadDate) {
        this.tEUploadDate = tEUploadDate;
    }

    public Date getPaymentdate() {
        return paymentdate;
    }

    public void setPaymentdate(Date paymentdate) {
        this.paymentdate = paymentdate;
    }

    public Float getAmountpaid() {
        return amountpaid;
    }

    public void setAmountpaid(Float amountpaid) {
        this.amountpaid = amountpaid;
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
        if (!(object instanceof Clients)) {
            return false;
        }
        Clients other = (Clients) object;
        if ((this.rid == null && other.rid != null) || (this.rid != null && !this.rid.equals(other.rid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.panafrica.umash.model.Clients[ rid=" + rid + " ]";
    }
    
}
