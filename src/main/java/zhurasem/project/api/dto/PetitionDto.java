package zhurasem.project.api.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PetitionDto {
    public Long pid = 0L;
    public String title;
    public String text;
    public int goal;
    public Date dateFrom;
    public String authorPetitionId = "";
    public List<Long> commentsIds = new ArrayList<>();
    public List<String> signedUsersIds = new ArrayList<>();

    public PetitionDto() {}

    public PetitionDto(Long pid, String title, String text, int goal, Date dateFrom, String authorPetitionId, List<Long> commentsIds, List<String> signedUsersIds) {
        this.pid = pid;
        this.title = title;
        this.text = text;
        this.goal = goal;
        this.dateFrom = dateFrom;
        this.authorPetitionId = authorPetitionId;
        this.commentsIds = commentsIds;
        this.signedUsersIds = signedUsersIds;
    }

    public PetitionDto(Long pid, String title, String text, int goal, Date dateFrom, List<Long> commentsIds, List<String> signedUsersIds) {
        this.pid = pid;
        this.title = title;
        this.text = text;
        this.goal = goal;
        this.dateFrom = dateFrom;
        this.commentsIds = commentsIds;
        this.signedUsersIds = signedUsersIds;
    }

    public PetitionDto(String title, String text, int goal, Date dateFrom, String authorPetitionId, List<Long> commentsIds, List<String> signedUsersIds) {
        this.title = title;
        this.text = text;
        this.goal = goal;
        this.dateFrom = dateFrom;
        this.authorPetitionId = authorPetitionId;
        this.commentsIds = commentsIds;
        this.signedUsersIds = signedUsersIds;
    }

    public PetitionDto(String title, String text, int goal, Date dateFrom, List<Long> commentsIds, List<String> signedUsersIds) {
        this.title = title;
        this.text = text;
        this.goal = goal;
        this.dateFrom = dateFrom;
        this.commentsIds = commentsIds;
        this.signedUsersIds = signedUsersIds;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getGoal() {
        return goal;
    }

    public void setGoal(int goal) {
        this.goal = goal;
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    public String getAuthorPetitionId() {
        return authorPetitionId;
    }

    public void setAuthorPetitionId(String authorPetitionId) {
        this.authorPetitionId = authorPetitionId;
    }

    public List<Long> getCommentsIds() {
        return commentsIds;
    }

    public void setCommentsIds(List<Long> commentsIds) {
        this.commentsIds = commentsIds;
    }

    public List<String> getSignedUsersIds() {
        return signedUsersIds;
    }

    public void setSignedUsersIds(List<String> signedUsersIds) {
        this.signedUsersIds = signedUsersIds;
    }
}
