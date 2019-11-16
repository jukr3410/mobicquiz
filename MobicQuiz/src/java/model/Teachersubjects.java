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
 * @author Student
 */
@Entity
@Table(name = "TEACHERSUBJECTS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Teachersubjects.findAll", query = "SELECT t FROM Teachersubjects t")
    , @NamedQuery(name = "Teachersubjects.findByTeachersubjectno", query = "SELECT t FROM Teachersubjects t WHERE t.teachersubjectno = :teachersubjectno")})
public class Teachersubjects implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "TEACHERSUBJECTNO")
    private String teachersubjectno;
    @JoinColumn(name = "SUBJECTNO", referencedColumnName = "SUBJECTNO")
    @ManyToOne(optional = false)
    private Subjects subjectno;
    @JoinColumn(name = "TEACHERNO", referencedColumnName = "TEACHERNO")
    @ManyToOne(optional = false)
    private Teachers teacherno;

    public Teachersubjects() {
    }

    public Teachersubjects(String teachersubjectno) {
        this.teachersubjectno = teachersubjectno;
    }

    public String getTeachersubjectno() {
        return teachersubjectno;
    }

    public void setTeachersubjectno(String teachersubjectno) {
        this.teachersubjectno = teachersubjectno;
    }

    public Subjects getSubjectno() {
        return subjectno;
    }

    public void setSubjectno(Subjects subjectno) {
        this.subjectno = subjectno;
    }

    public Teachers getTeacherno() {
        return teacherno;
    }

    public void setTeacherno(Teachers teacherno) {
        this.teacherno = teacherno;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (teachersubjectno != null ? teachersubjectno.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Teachersubjects)) {
            return false;
        }
        Teachersubjects other = (Teachersubjects) object;
        if ((this.teachersubjectno == null && other.teachersubjectno != null) || (this.teachersubjectno != null && !this.teachersubjectno.equals(other.teachersubjectno))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Teachersubjects[ teachersubjectno=" + teachersubjectno + " ]";
    }
    
}
