/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Jn
 */
@Entity
@Table(name = "STUDENTSUBJECTS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Studentsubjects.findAll", query = "SELECT s FROM Studentsubjects s")
    , @NamedQuery(name = "Studentsubjects.findByStudentsubjectno", query = "SELECT s FROM Studentsubjects s WHERE s.studentsubjectno = :studentsubjectno")})
public class Studentsubjects implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "STUDENTSUBJECTNO")
    private String studentsubjectno;
    @JoinColumn(name = "STUDENTNO", referencedColumnName = "STUDENTNO")
    @ManyToOne(optional = false)
    private Students studentno;
    @JoinColumn(name = "SUBJECTNO", referencedColumnName = "SUBJECTNO")
    @ManyToOne(optional = false)
    private Subjects subjectno;

    public Studentsubjects() {
    }

    public Studentsubjects(String studentsubjectno) {
        this.studentsubjectno = studentsubjectno;
    }

    public String getStudentsubjectno() {
        return studentsubjectno;
    }

    public void setStudentsubjectno(String studentsubjectno) {
        this.studentsubjectno = studentsubjectno;
    }

    public Students getStudentno() {
        return studentno;
    }

    public void setStudentno(Students studentno) {
        this.studentno = studentno;
    }

    public Subjects getSubjectno() {
        return subjectno;
    }

    public void setSubjectno(Subjects subjectno) {
        this.subjectno = subjectno;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (studentsubjectno != null ? studentsubjectno.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Studentsubjects)) {
            return false;
        }
        Studentsubjects other = (Studentsubjects) object;
        if ((this.studentsubjectno == null && other.studentsubjectno != null) || (this.studentsubjectno != null && !this.studentsubjectno.equals(other.studentsubjectno))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Studentsubjects[ studentsubjectno=" + studentsubjectno + " ]";
    }
    
}
