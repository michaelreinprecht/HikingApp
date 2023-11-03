package models;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "MyHike.month")
public class Month {
    private Integer monthId;
    private String monthName;
    private List<Hike> hikes;

    @Id
    @Column(name = "month_id")
    public Integer getMonthId() {
        return monthId;
    }
    public void setMonthId(Integer monthId) {
        this.monthId = monthId;
    }

    @Column(name = "month_name", length = 20)
    public String getMonthName() {
        return monthName;
    }
    public void setMonthName(String monthName) {
        this.monthName = monthName;
    }

    @ManyToMany(mappedBy = "recommendedMonths")
    public List<Hike> getHikes() {
        return hikes;
    }
    public void setHikes(List<Hike> hikes) {
        this.hikes = hikes;
    }
}