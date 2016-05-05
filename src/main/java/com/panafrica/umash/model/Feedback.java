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
@Table(name = "feedback")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Feedback.findAll", query = "SELECT f FROM Feedback f"),
    @NamedQuery(name = "Feedback.findByTid", query = "SELECT f FROM Feedback f WHERE f.tid = :tid"),
    @NamedQuery(name = "Feedback.findByContacts", query = "SELECT f FROM Feedback f WHERE f.contacts = :contacts"),
    @NamedQuery(name = "Feedback.findByComments", query = "SELECT f FROM Feedback f WHERE f.comments = :comments"),
    @NamedQuery(name = "Feedback.findByFeedbackdate", query = "SELECT f FROM Feedback f WHERE f.feedbackdate = :feedbackdate"),
    @NamedQuery(name = "Feedback.findByReplied", query = "SELECT f FROM Feedback f WHERE f.replied = :replied"),
    @NamedQuery(name = "Feedback.findByRepliedby", query = "SELECT f FROM Feedback f WHERE f.repliedby = :repliedby"),
    @NamedQuery(name = "Feedback.findByReplyComments", query = "SELECT f FROM Feedback f WHERE f.replyComments = :replyComments"),
    @NamedQuery(name = "Feedback.findByReplyto", query = "SELECT f FROM Feedback f WHERE f.replyto = :replyto"),
    @NamedQuery(name = "Feedback.findByReplydate", query = "SELECT f FROM Feedback f WHERE f.replydate = :replydate")})
public class Feedback implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "tid")
    private Integer tid;
    @Size(max = 50)
    @Column(name = "contacts")
    private String contacts;
    @Size(max = 200)
    @Column(name = "comments")
    private String comments;
    @Column(name = "feedbackdate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date feedbackdate;
    @Column(name = "replied")
    private Boolean replied;
    @Size(max = 50)
    @Column(name = "repliedby")
    private String repliedby;
    @Size(max = 200)
    @Column(name = "replyComments")
    private String replyComments;
    @Size(max = 50)
    @Column(name = "replyto")
    private String replyto;
    @Column(name = "replydate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date replydate;

    public Feedback() {
    }

    public Feedback(Integer tid) {
        this.tid = tid;
    }

    public Integer getTid() {
        return tid;
    }

    public void setTid(Integer tid) {
        this.tid = tid;
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Date getFeedbackdate() {
        return feedbackdate;
    }

    public void setFeedbackdate(Date feedbackdate) {
        this.feedbackdate = feedbackdate;
    }

    public Boolean getReplied() {
        return replied;
    }

    public void setReplied(Boolean replied) {
        this.replied = replied;
    }

    public String getRepliedby() {
        return repliedby;
    }

    public void setRepliedby(String repliedby) {
        this.repliedby = repliedby;
    }

    public String getReplyComments() {
        return replyComments;
    }

    public void setReplyComments(String replyComments) {
        this.replyComments = replyComments;
    }

    public String getReplyto() {
        return replyto;
    }

    public void setReplyto(String replyto) {
        this.replyto = replyto;
    }

    public Date getReplydate() {
        return replydate;
    }

    public void setReplydate(Date replydate) {
        this.replydate = replydate;
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
        if (!(object instanceof Feedback)) {
            return false;
        }
        Feedback other = (Feedback) object;
        if ((this.tid == null && other.tid != null) || (this.tid != null && !this.tid.equals(other.tid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.panafrica.umash.model.Feedback[ tid=" + tid + " ]";
    }
    
}
