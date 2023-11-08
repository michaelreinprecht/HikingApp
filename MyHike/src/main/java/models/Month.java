package models;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "MyHike.month")
public class Month {
    private Integer monthId;
    private String monthName;
    private List<Recommended> recommendedList;

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

    @OneToMany(mappedBy = "month")
    public List<Recommended> getRecommendedList() {
        return recommendedList;
    }
    public void setRecommendedList(List<Recommended> recommendedList) {
        this.recommendedList = recommendedList;
    }


}