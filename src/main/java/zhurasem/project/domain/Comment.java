package zhurasem.project.domain;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.*;

@Entity
public class Comment implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cid;

    @Column(nullable = false)
    private String text;

    @Column(nullable = false)
    private Date dateFrom;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "author_id")
    private User authorComment;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "petition_id")
    private Petition petitionComment;

    // Methods:

    public Comment() {}

    public Comment(Long cid, String text, Date dateFrom) {
        this.cid = Objects.requireNonNull(cid);
        this.text = Objects.requireNonNull(text);
        this.dateFrom = Objects.requireNonNull(dateFrom);
    }

    public Comment(Long cid, String text, Date dateFrom, User authorComment, Petition petitionComment) {
        this.cid = Objects.requireNonNull(cid);
        this.text = Objects.requireNonNull(text);
        this.dateFrom = Objects.requireNonNull(dateFrom);
        this.authorComment = authorComment;
        this.petitionComment = petitionComment;
    }

    // Getters:

    public Long getCid() {
        return cid;
    }

    public String getText() {
        return text;
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public User getAuthorComment() {
        return authorComment;
    }

    public Petition getPetitionComment() {
        return petitionComment;
    }

    // Setters:

    public void setCid(Long cid) {
        this.cid = cid;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    public void setAuthorComment(User authorComment) {
        this.authorComment = authorComment;
    }

    public void setPetitionComment(Petition petitionComment) {
        this.petitionComment = petitionComment;
    }

    // Overrided methods:

    @Override
    public int hashCode() {
        return Objects.hash(cid);
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(obj == null) return false;
        if(getClass() != obj.getClass()) return false;
        Comment comment = (Comment) obj;
        return cid.equals(comment.cid);
    }

    @Override
    public String toString() {
        return "Comment{" +
                "cid=" + cid +
                ", text='" + text + '\'' +
                ", dateFrom=" + dateFrom +
                '}';
    }
}
