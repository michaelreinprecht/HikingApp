package models;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@IdClass(RecommendedInId.class)
@Table(name = "recommended_in")
public class RecommendedIn {

    @Id
    @ManyToOne
    @JoinColumn(name = "month_id")
    private Month month;

    @Id
    @ManyToOne
    @JoinColumn(name = "hike_id")
    private Hike hike;

    // Constructors, getters, and setters

    public Month getMonth() {
        return month;
    }

    public void setMonth(Month month) {
        this.month = month;
    }

    public Hike getHike() {
        return hike;
    }

    public void setHike(Hike hike) {
        this.hike = hike;
    }
}



