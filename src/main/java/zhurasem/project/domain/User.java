package zhurasem.project.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.hibernate.sql.ast.tree.expression.Collation;

import java.io.Serializable;
import java.util.Collection;
import java.util.Objects;

@Entity
public class User implements Serializable {

    @Id
    private String username;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @OneToMany(mappedBy = "authorPetition")
    private Collection<Petition> myPetitions;

    @OneToMany(mappedBy = "authorComment")
    private Collection<Comment> myComments;

    @ManyToMany(mappedBy = "signedBy")
    @JsonIgnore
    private Collection<Petition> signedByMe;

    // Methods:

    public User() {}

    public User(String username, String email, String password) {
        this.username = Objects.requireNonNull(username);
        this.email = Objects.requireNonNull(email);
        this.password = Objects.requireNonNull(password);
    }

    public User(String username, String email, String password, Collection<Petition> myPetitions, Collection<Comment> myComments, Collection<Petition> signedByMe) {
        this.username = Objects.requireNonNull(username);
        this.email = Objects.requireNonNull(email);
        this.password = Objects.requireNonNull(password);
        this.myPetitions = myPetitions;
        this.myComments = myComments;
        this.signedByMe = signedByMe;
    }

    // Getters:

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Collection<Petition> getMyPetitions() {
        return myPetitions;
    }

    public Collection<Comment> getMyComments() {
        return myComments;
    }

    public Collection<Petition> getSignedByMe() {
        return signedByMe;
    }

    // Setters:

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // Overrided methods:

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(obj == null) return false;
        if(getClass() != obj.getClass()) return false;
        User user = (User) obj;
        return username.equals(user.username);
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

}
