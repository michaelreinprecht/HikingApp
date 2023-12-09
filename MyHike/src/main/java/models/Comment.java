package models;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

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
}


