package zhurasem.project.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.*;

@Entity
public class Petition implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pid;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String text;

    private int goal;

    @Column(nullable = false)
    private Date dateFrom;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "author_id")
    private User authorPetition;

    @OneToMany(mappedBy = "petitionComment")
    @JsonIgnore
    private Collection<Comment> comments;

    @ManyToMany
    @JoinTable(
            name = "signs",
            joinColumns = @JoinColumn(name = "is_signed"),
            inverseJoinColumns = @JoinColumn(name = "signs")
    )
    private final Collection<User> signedBy = new ArrayList<>();

    // Methods:

    public Petition() {}

    public Petition(Long pid, String title, String text, int goal, Date dateFrom) {
        this.pid = Objects.requireNonNull(pid);
        this.title = Objects.requireNonNull(title);
        this.text = Objects.requireNonNull(text);
        this.goal = goal;
        this.dateFrom = Objects.requireNonNull(dateFrom);
    }

    public Petition(Long pid, String title, String text, int goal, Date dateFrom, User authorPetition, Collection<Comment> comments) {
        this.pid = Objects.requireNonNull(pid);
        this.title = Objects.requireNonNull(title);
        this.text = Objects.requireNonNull(text);
        this.goal = goal;
        this.dateFrom = Objects.requireNonNull(dateFrom);
        this.authorPetition = authorPetition;
        this.comments = comments;
    }

    // Getters:

    public Long getPid() {
        return pid;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public int getGoal() {
        return goal;
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public User getAuthorPetition() {
        return authorPetition;
    }

    public Collection<Comment> getComments() {
        return comments;
    }

    public Collection<User> getSignedBy() {
        return signedBy;
    }

    // Setters:

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setGoal(int goal) {
        this.goal = goal;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    public void setAuthorPetition(User authorPetition) {
        this.authorPetition = authorPetition;
    }

    public void setComments(Collection<Comment> comments) {
        this.comments = comments;
    }

    // Overrided methods:

    @Override
    public int hashCode() {
        return Objects.hash(pid);
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(obj == null) return false;
        if(getClass() != obj.getClass()) return false;
        Petition petition = (Petition) obj;
        return pid.equals(petition.pid);
    }

    @Override
    public String toString() {
        return "Petition{" +
                "pid=" + pid +
                ", title='" + title + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
