package com.epam.star.entity;

import javax.persistence.*;
import java.util.List;

@MappedEntityForAdmin("Status")
@Entity
public class Status extends AbstractEntity {

    @Column
    private String statusName;
    @OneToMany(mappedBy = "status", fetch = FetchType.EAGER)
    private List<Order> orders;

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Status)) return false;

        Status status = (Status) o;

        if (statusName != null) {
            if (!statusName.equals(status.statusName)) return false;
        } else {
            if (status.statusName != null) return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return statusName != null ? statusName.hashCode() : 0;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }
}
