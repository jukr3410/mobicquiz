/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Student
 */
@Entity
@Table(name = "HISTORYS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Historys.findAll", query = "SELECT h FROM Historys h")
    , @NamedQuery(name = "Historys.findByHistoryno", query = "SELECT h FROM Historys h WHERE h.historyno = :historyno")
    , @NamedQuery(name = "Historys.findByScore", query = "SELECT h FROM Historys h WHERE h.score = :score")
    , @NamedQuery(name = "Historys.findByStudentno", query = "SELECT h FROM Historys h WHERE h.studentno.studentno = :studentno")
    , @NamedQuery(name = "Historys.findByTeacherno", query = "SELECT h FROM Historys h WHERE h.quizno.teacherno.teacherno = :teacherno")
    , @NamedQuery(name = "Historys.findByDate", query = "SELECT h FROM Historys h WHERE h.date = :date")})
public class Historys implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "HISTORYNO")
    private String historyno;
    @Basic(optional = false)
    @NotNull
    @Column(name = "SCORE")
    private int score;
    @Column(name = "DATE")
    @Temporal(TemporalType.DATE)
    private Date date;
    @JoinColumn(name = "QUIZNO", referencedColumnName = "QUIZNO")
    @ManyToOne(optional = false)
    private Quizs quizno;
    @JoinColumn(name = "STUDENTNO", referencedColumnName = "STUDENTNO")
    @ManyToOne(optional = false)
    private Students studentno;

    public Historys() {
    }

    public Historys(String historyno) {
        this.historyno = historyno;
    }

    public Historys(String historyno, int score) {
        this.historyno = historyno;
        this.score = score;
    }

    public Historys(String historyno, int score, Date date, Quizs quizno, Students studentno) {
        this.historyno = historyno;
        this.score = score;
        this.date = date;
        this.quizno = quizno;
        this.studentno = studentno;
    }

    public String getHistoryno() {
        return historyno;
    }

    public void setHistoryno(String historyno) {
        this.historyno = historyno;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Quizs getQuizno() {
        return quizno;
    }

    public void setQuizno(Quizs quizno) {
        this.quizno = quizno;
    }

    public Students getStudentno() {
        return studentno;
    }

    public void setStudentno(Students studentno) {
        this.studentno = studentno;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (historyno != null ? historyno.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Historys)) {
            return false;
        }
        Historys other = (Historys) object;
        if ((this.historyno == null && other.historyno != null) || (this.historyno != null && !this.historyno.equals(other.historyno))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Historys[ historyno=" + historyno + " ]";
    }

}
