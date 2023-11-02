package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "MyHike.month")
public class Month {
    private Integer monthId;
    private String monthName;

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


}