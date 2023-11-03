package models;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "MyHike.recommended")
public class Recommended implements Serializable {

    @Id
    @Column(name = "recommended_id")
    private int recommendedId;

    @ManyToOne
    @JoinColumn(name = "month_id")
    private Month month;

    @ManyToOne
    @JoinColumn(name = "hike_id")
    private Hike hike;

    // Constructors, getters, and setters

    public void setMonth(Month month) {
        this.month = month;
    }

    public void setHike(Hike hike) {
        this.hike = hike;
    }
}



