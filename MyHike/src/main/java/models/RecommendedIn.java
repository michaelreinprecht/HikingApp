/*package models;

import lombok.Getter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Entity
@IdClass(RecommendedInId.class)
@Table(name = "recommended_in")
public class RecommendedIn implements Serializable {

    @Id
    @ManyToOne
    @JoinColumn(name = "month_id")
    private Month month;

    @Id
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
*/


