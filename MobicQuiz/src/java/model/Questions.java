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
@Table(name = "QUESTIONS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Questions.findAll", query = "SELECT q FROM Questions q")
    , @NamedQuery(name = "Questions.findByQuestionno", query = "SELECT q FROM Questions q WHERE q.questionno = :questionno")
    , @NamedQuery(name = "Questions.findByQuestion", query = "SELECT q FROM Questions q WHERE q.question = :question")
    , @NamedQuery(name = "Questions.findByAns1", query = "SELECT q FROM Questions q WHERE q.ans1 = :ans1")
    , @NamedQuery(name = "Questions.findByAns2", query = "SELECT q FROM Questions q WHERE q.ans2 = :ans2")
    , @NamedQuery(name = "Questions.findByAns3", query = "SELECT q FROM Questions q WHERE q.ans3 = :ans3")
    , @NamedQuery(name = "Questions.findByAns4", query = "SELECT q FROM Questions q WHERE q.ans4 = :ans4")
    , @NamedQuery(name = "Questions.findByCorrectans", query = "SELECT q FROM Questions q WHERE q.correctans = :correctans")})
public class Questions implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "QUESTIONNO")
    private Integer questionno;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "QUESTION")
    private String question;
    @Size(max = 45)
    @Column(name = "ANS1")
    private String ans1;
    @Size(max = 45)
    @Column(name = "ANS2")
    private String ans2;
    @Size(max = 45)
    @Column(name = "ANS3")
    private String ans3;
    @Size(max = 45)
    @Column(name = "ANS4")
    private String ans4;
    @Size(max = 45)
    @Column(name = "CORRECTANS")
    private String correctans;
    @JoinColumn(name = "QUIZNO", referencedColumnName = "QUIZNO")
    @ManyToOne(optional = false)
    private Quizs quizno;

    public Questions() {
    }

    public Questions(Integer questionno) {
        this.questionno = questionno;
    }

    public Questions(Integer questionno, String question) {
        this.questionno = questionno;
        this.question = question;
    }

    public Integer getQuestionno() {
        return questionno;
    }

    public void setQuestionno(Integer questionno) {
        this.questionno = questionno;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAns1() {
        return ans1;
    }

    public void setAns1(String ans1) {
        this.ans1 = ans1;
    }

    public String getAns2() {
        return ans2;
    }

    public void setAns2(String ans2) {
        this.ans2 = ans2;
    }

    public String getAns3() {
        return ans3;
    }

    public void setAns3(String ans3) {
        this.ans3 = ans3;
    }

    public String getAns4() {
        return ans4;
    }

    public void setAns4(String ans4) {
        this.ans4 = ans4;
    }

    public String getCorrectans() {
        return correctans;
    }

    public void setCorrectans(String correctans) {
        this.correctans = correctans;
    }

    public Quizs getQuizno() {
        return quizno;
    }

    public void setQuizno(Quizs quizno) {
        this.quizno = quizno;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (questionno != null ? questionno.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Questions)) {
            return false;
        }
        Questions other = (Questions) object;
        if ((this.questionno == null && other.questionno != null) || (this.questionno != null && !this.questionno.equals(other.questionno))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Questions[ questionno=" + questionno + " ]";
    }
    
}
