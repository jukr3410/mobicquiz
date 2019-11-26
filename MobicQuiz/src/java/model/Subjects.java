/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Jn
 */
@Entity
@Table(name = "SUBJECTS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Subjects.findAll", query = "SELECT s FROM Subjects s")
    , @NamedQuery(name = "Subjects.findBySubjectno", query = "SELECT s FROM Subjects s WHERE s.subjectno = :subjectno")
    , @NamedQuery(name = "Subjects.findBySubject", query = "SELECT s FROM Subjects s WHERE s.subject = :subject")})
public class Subjects implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "SUBJECTNO")
    private String subjectno;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "SUBJECT")
    private String subject;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "subjectno")
    private List<Teachersubjects> teachersubjectsList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "subjectno")
    private List<Quizs> quizsList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "subjectno")
    private List<Studentsubjects> studentsubjectsList;

    public Subjects() {
    }

    public Subjects(String subjectno) {
        this.subjectno = subjectno;
    }

    public Subjects(String subjectno, String subject) {
        this.subjectno = subjectno;
        this.subject = subject;
    }

    public String getSubjectno() {
        return subjectno;
    }

    public void setSubjectno(String subjectno) {
        this.subjectno = subjectno;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    @XmlTransient
    public List<Teachersubjects> getTeachersubjectsList() {
        return teachersubjectsList;
    }

    public void setTeachersubjectsList(List<Teachersubjects> teachersubjectsList) {
        this.teachersubjectsList = teachersubjectsList;
    }

    @XmlTransient
    public List<Quizs> getQuizsList() {
        return quizsList;
    }

    public void setQuizsList(List<Quizs> quizsList) {
        this.quizsList = quizsList;
    }

    @XmlTransient
    public List<Studentsubjects> getStudentsubjectsList() {
        return studentsubjectsList;
    }

    public void setStudentsubjectsList(List<Studentsubjects> studentsubjectsList) {
        this.studentsubjectsList = studentsubjectsList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (subjectno != null ? subjectno.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Subjects)) {
            return false;
        }
        Subjects other = (Subjects) object;
        if ((this.subjectno == null && other.subjectno != null) || (this.subjectno != null && !this.subjectno.equals(other.subjectno))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Subjects[ subjectno=" + subjectno + " ]";
    }
    
}
