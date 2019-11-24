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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
 * @author Student
 */
@Entity
@Table(name = "QUIZS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Quizs.findAll", query = "SELECT q FROM Quizs q")
    , @NamedQuery(name = "Quizs.findByQuizno", query = "SELECT q FROM Quizs q WHERE q.quizno = :quizno")
    , @NamedQuery(name = "Quizs.findByTitle", query = "SELECT q FROM Quizs q WHERE q.title = :title")
    , @NamedQuery(name = "Quizs.findByTime", query = "SELECT q FROM Quizs q WHERE q.time = :time")

    , @NamedQuery(name = "Quizs.findByFullscore", query = "SELECT q FROM Quizs q WHERE q.fullscore = :fullscore")
    , @NamedQuery(name = "Quizs.findByLevelno", query = "SELECT q FROM Quizs q WHERE q.levelno.levelno = :levelno and q.status like 'on'")
    , @NamedQuery(name = "Quizs.findByStatus", query = "SELECT q FROM Quizs q WHERE q.status = :status")
    , @NamedQuery(name = "Quizs.findByTeacherno", query = "SELECT q FROM Quizs q WHERE q.teacherno.teacherno = :teacherno")})
public class Quizs implements Serializable {

    @JoinColumn(name = "TEACHERNO", referencedColumnName = "TEACHERNO")
    @ManyToOne
    private Teachers teacherno;

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "QUIZNO")
    private String quizno;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "TITLE")
    private String title;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TIME")
    private int time;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FULLSCORE")
    private int fullscore;
    @Size(max = 45)
    @Column(name = "STATUS")
    private String status;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "quizno")
    private List<Questions> questionsList;
    @JoinColumn(name = "LEVELNO", referencedColumnName = "LEVELNO")
    @ManyToOne(optional = false)
    private Levels levelno;
    @JoinColumn(name = "SUBJECTNO", referencedColumnName = "SUBJECTNO")
    @ManyToOne(optional = false)
    private Subjects subjectno;


    public Quizs() {
    }

    public Quizs(String quizno) {
        this.quizno = quizno;
    }

    public Quizs(String quizno, String title, int time, int fullscore) {
        this.quizno = quizno;
        this.title = title;
        this.time = time;
        this.fullscore = fullscore;
    }
    
        public Quizs(String title, int time, int fullscore, Levels levelno, Subjects subjectno,Teachers teacherno) {
        this.teacherno = teacherno;
        this.title = title;
        this.time = time;
        this.fullscore = fullscore;       
        this.levelno = levelno;
        this.subjectno = subjectno;
    }

    public Quizs(String quizno, String title, int time, int fullscore, Levels levelno, Subjects subjectno,Teachers teacherno) {
        this.teacherno = teacherno;
        this.quizno = quizno;
        this.title = title;
        this.time = time;
        this.fullscore = fullscore;       
        this.levelno = levelno;
        this.subjectno = subjectno;
    }
    
    

    public String getQuizno() {
        return quizno;
    }

    public void setQuizno(String quizno) {
        this.quizno = quizno;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getFullscore() {
        return fullscore;
    }

    public void setFullscore(int fullscore) {
        this.fullscore = fullscore;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @XmlTransient
    public List<Questions> getQuestionsList() {
        return questionsList;
    }

    public void setQuestionsList(List<Questions> questionsList) {
        this.questionsList = questionsList;
    }

    public Levels getLevelno() {
        return levelno;
    }

    public void setLevelno(Levels levelno) {
        this.levelno = levelno;
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
        hash += (quizno != null ? quizno.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Quizs)) {
            return false;
        }
        Quizs other = (Quizs) object;
        if ((this.quizno == null && other.quizno != null) || (this.quizno != null && !this.quizno.equals(other.quizno))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Quizs[ quizno=" + quizno + " ]";
    }

    public Teachers getTeacherno() {
        return teacherno;
    }

    public void setTeacherno(Teachers teacherno) {
        this.teacherno = teacherno;
    }

}
