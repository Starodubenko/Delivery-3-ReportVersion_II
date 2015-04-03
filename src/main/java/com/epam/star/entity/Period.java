package com.epam.star.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.sql.Time;
import java.util.List;

@MappedEntityForAdmin("Period")
@Entity
public class Period extends AbstractEntity {

    @Column
    private Time period;
    @Column
    private String describe;
    @OneToMany(mappedBy = "period")
    private List<Order> orders;

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public Time getPeriod() {
        return period;
    }

    public void setPeriod(Time period) {
        this.period = period;
    }

}
