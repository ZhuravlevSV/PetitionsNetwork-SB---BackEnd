package zhurasem.project.api.dto;

import java.util.Date;

public class CommentDto {
    public Long cid = 0L;
    public String text;
    public Date dateFrom;
    public String authorCommentId = "";
    public Long petitionCommentId = 0L;

    public CommentDto() {}

    public CommentDto(Long cid, String text, Date dateFrom, String authorCommentId, Long petitionCommentId) {
        this.cid = cid;
        this.text = text;
        this.dateFrom = dateFrom;
        this.authorCommentId = authorCommentId;
        this.petitionCommentId = petitionCommentId;
    }

    public CommentDto(String text, Date dateFrom, String authorCommentId, Long petitionCommentId) {
        this.text = text;
        this.dateFrom = dateFrom;
        this.authorCommentId = authorCommentId;
        this.petitionCommentId = petitionCommentId;
    }

    public CommentDto(Long cid, String text, Date dateFrom, Long petitionCommentId) {
        this.cid = cid;
        this.text = text;
        this.dateFrom = dateFrom;
        this.petitionCommentId = petitionCommentId;
    }

    public CommentDto(Long cid, String text, Date dateFrom, String authorCommentId) {
        this.cid = cid;
        this.text = text;
        this.dateFrom = dateFrom;
        this.authorCommentId = authorCommentId;
    }

    public CommentDto(Long cid, String text, Date dateFrom) {
        this.cid = cid;
        this.text = text;
        this.dateFrom = dateFrom;
    }

    public CommentDto(String text, Date dateFrom) {
        this.text = text;
        this.dateFrom = dateFrom;
    }

    public Long getCid() {
        return cid;
    }

    public void setCid(Long cid) {
        this.cid = cid;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    public String getAuthorCommentId() {
        return authorCommentId;
    }

    public void setAuthorCommentId(String authorCommentId) {
        this.authorCommentId = authorCommentId;
    }

    public Long getPetitionCommentId() {
        return petitionCommentId;
    }

    public void setPetitionCommentId(Long petitionCommentId) {
        this.petitionCommentId = petitionCommentId;
    }
}
