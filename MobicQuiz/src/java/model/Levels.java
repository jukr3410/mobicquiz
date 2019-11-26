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
@Table(name = "LEVELS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Levels.findAll", query = "SELECT l FROM Levels l")
    , @NamedQuery(name = "Levels.findByLevelno", query = "SELECT l FROM Levels l WHERE l.levelno = :levelno")
    , @NamedQuery(name = "Levels.findByLevel", query = "SELECT l FROM Levels l WHERE l.level = :level")})
public class Levels implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "LEVELNO")
    private String levelno;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "LEVEL")
    private String level;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "levelno")
    private List<Quizs> quizsList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "levelno")
    private List<Students> studentsList;

    public Levels() {
    }

    public Levels(String levelno) {
        this.levelno = levelno;
    }

    public Levels(String levelno, String level) {
        this.levelno = levelno;
        this.level = level;
    }

    public String getLevelno() {
        return levelno;
    }

    public void setLevelno(String levelno) {
        this.levelno = levelno;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    @XmlTransient
    public List<Quizs> getQuizsList() {
        return quizsList;
    }

    public void setQuizsList(List<Quizs> quizsList) {
        this.quizsList = quizsList;
    }

    @XmlTransient
    public List<Students> getStudentsList() {
        return studentsList;
    }

    public void setStudentsList(List<Students> studentsList) {
        this.studentsList = studentsList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (levelno != null ? levelno.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Levels)) {
            return false;
        }
        Levels other = (Levels) object;
        if ((this.levelno == null && other.levelno != null) || (this.levelno != null && !this.levelno.equals(other.levelno))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Levels[ levelno=" + levelno + " ]";
    }
    
}
