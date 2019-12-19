/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

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
 * @author Jn
 */
@Entity
@Table(name = "STUDENTS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Students.findAll", query = "SELECT s FROM Students s")
    , @NamedQuery(name = "Students.findByStudentno", query = "SELECT s FROM Students s WHERE s.studentno = :studentno")
    , @NamedQuery(name = "Students.findByName", query = "SELECT s FROM Students s WHERE s.name = :name")
    , @NamedQuery(name = "Students.findByEmail", query = "SELECT s FROM Students s WHERE s.email = :email")
    , @NamedQuery(name = "Students.findByPassword", query = "SELECT s FROM Students s WHERE s.password = :password")
    , @NamedQuery(name = "Students.findByActivatekey", query = "SELECT s FROM Students s WHERE s.activatekey = :activatekey")
    , @NamedQuery(name = "Students.findByActivated", query = "SELECT s FROM Students s WHERE s.activated = :activated")})
public class Students implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "STUDENTNO")
    private String studentno;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "NAME")
    private String name;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "EMAIL")
    private String email;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "PASSWORD")
    private String password;
    @Size(max = 45)
    @Column(name = "ACTIVATEKEY")
    private String activatekey;
    @Size(max = 45)
    @Column(name = "ACTIVATED")
    private String activated;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "studentno")
    private List<Studentsubjects> studentsubjectsList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "studentno")
    private List<Historys> historysList;
    @JoinColumn(name = "LEVELNO", referencedColumnName = "LEVELNO")
    @ManyToOne(optional = false)
    private Levels levelno;

    public Students() {
    }

    public Students(String studentno) {
        this.studentno = studentno;
    }

    public Students(String studentno, String name, String email, String password) {
        this.studentno = studentno;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public String getStudentno() {
        return studentno;
    }

    public void setStudentno(String studentno) {
        this.studentno = studentno;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getActivatekey() {
        return activatekey;
    }

    public void setActivatekey(String activatekey) {
        this.activatekey = activatekey;
    }

    public String getActivated() {
        return activated;
    }

    public void setActivated(String activated) {
        this.activated = activated;
    }

    @XmlTransient
    public List<Studentsubjects> getStudentsubjectsList() {
        return studentsubjectsList;
    }

    public void setStudentsubjectsList(List<Studentsubjects> studentsubjectsList) {
        this.studentsubjectsList = studentsubjectsList;
    }

    @XmlTransient
    public List<Historys> getHistorysList() {
        return historysList;
    }

    public void setHistorysList(List<Historys> historysList) {
        this.historysList = historysList;
    }

    public Levels getLevelno() {
        return levelno;
    }

    public void setLevelno(Levels levelno) {
        this.levelno = levelno;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (studentno != null ? studentno.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Students)) {
            return false;
        }
        Students other = (Students) object;
        if ((this.studentno == null && other.studentno != null) || (this.studentno != null && !this.studentno.equals(other.studentno))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "servlet.Students[ studentno=" + studentno + " ]";
    }
    
}
