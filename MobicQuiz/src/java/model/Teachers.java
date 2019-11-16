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
 * @author Student
 */
@Entity
@Table(name = "TEACHERS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Teachers.findAll", query = "SELECT t FROM Teachers t")
    , @NamedQuery(name = "Teachers.findByTeacherno", query = "SELECT t FROM Teachers t WHERE t.teacherno = :teacherno")
    , @NamedQuery(name = "Teachers.findByName", query = "SELECT t FROM Teachers t WHERE t.name = :name")
    , @NamedQuery(name = "Teachers.findByEmail", query = "SELECT t FROM Teachers t WHERE t.email = :email")
    , @NamedQuery(name = "Teachers.findByPassword", query = "SELECT t FROM Teachers t WHERE t.password = :password")
    , @NamedQuery(name = "Teachers.findByActivatekey", query = "SELECT t FROM Teachers t WHERE t.activatekey = :activatekey")
    , @NamedQuery(name = "Teachers.findByActivated", query = "SELECT t FROM Teachers t WHERE t.activated = :activated")})
public class Teachers implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "TEACHERNO")
    private String teacherno;
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "teacherno")
    private List<Teachersubjects> teachersubjectsList;

    public Teachers() {
    }

    public Teachers(String teacherno) {
        this.teacherno = teacherno;
    }

    public Teachers(String teacherno, String name, String email, String password) {
        this.teacherno = teacherno;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public Teachers(String teacherno, String name, String email, String password, String activatekey) {
        this.teacherno = teacherno;
        this.name = name;
        this.email = email;
        this.password = password;
        this.activatekey = activatekey;
    }
    
    

    public String getTeacherno() {
        return teacherno;
    }

    public void setTeacherno(String teacherno) {
        this.teacherno = teacherno;
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
    public List<Teachersubjects> getTeachersubjectsList() {
        return teachersubjectsList;
    }

    public void setTeachersubjectsList(List<Teachersubjects> teachersubjectsList) {
        this.teachersubjectsList = teachersubjectsList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (teacherno != null ? teacherno.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Teachers)) {
            return false;
        }
        Teachers other = (Teachers) object;
        if ((this.teacherno == null && other.teacherno != null) || (this.teacherno != null && !this.teacherno.equals(other.teacherno))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Teachers[ teacherno=" + teacherno + " ]";
    }
    
}
