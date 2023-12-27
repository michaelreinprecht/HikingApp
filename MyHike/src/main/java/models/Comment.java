package models;

import lombok.Getter;

import javax.persistence.*;
import java.io.Serializable;

@SuppressWarnings("unused")
@Getter
@Entity
@Table(name = "MyHike.comment")
public class Comment implements Serializable {
    @Id
    @Column(name = "comment_id")
    private String commentId;

    @Column(name = "comment_description")
    private String commentDescription;

    @ManyToOne
    @JoinColumn(name = "comment_hike")
    private Hike commentHike;

    @ManyToOne
    @JoinColumn(name = "comment_user")
    private User commentUser;

    // Constructors, getters, and setters
    public Comment() {}

    public Comment(String commentId, String commentDescription, Hike commentHike, User commentUser) {
        this.commentId = commentId;
        this.commentDescription = commentDescription;
        this.commentHike = commentHike;
        this.commentUser = commentUser;
    }

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public String getCommentDescription() {
        return commentDescription;
    }

    public void setCommentDescription(String commentDescription) {
        this.commentDescription = commentDescription;
    }

    public Hike getCommentHike() {
        return commentHike;
    }

    public void setCommentHike(Hike commentHike) {
        this.commentHike = commentHike;
    }

    public User getCommentUser() {
        return commentUser;
    }

    public void setCommentUser(User commentUser) {
        this.commentUser = commentUser;
    }
}


