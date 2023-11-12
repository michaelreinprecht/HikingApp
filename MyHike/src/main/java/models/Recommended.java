package models;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "MyHike.recommended")
public class Recommended implements Serializable {

    @Setter
    @Id
    @Column(name = "recommended_id")
    private String recommendedId;

    @Getter
    @ManyToOne
    @JoinColumn(name = "month_id")
    private Month month;


    @ManyToOne
    @JoinColumn(name = "hike_id")
    private Hike hike;

    // Constructors, getters, and setters
    public Recommended() {}

    public Recommended(String recommendedId, Month month, Hike hike) {
        this.recommendedId = recommendedId;
        this.month = month;
        this.hike = hike;
    }

    public void setMonth(Month month) {
        this.month = month;
    }

    public void setHike(Hike hike) {
        this.hike = hike;
    }
}



