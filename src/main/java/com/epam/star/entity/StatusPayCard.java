package com.epam.star.entity;

import javax.persistence.*;
import java.util.List;

@MappedEntityForAdmin("StatusPayCard")
@Entity
@Table(name = "status_pay_card")
public class StatusPayCard extends AbstractEntity {

    @Column
    private String statusName;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "statusPayCard")
    private List<PayCard> payCards;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StatusPayCard)) return false;

        StatusPayCard that = (StatusPayCard) o;

        if (!statusName.equals(that.statusName)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return statusName.hashCode();
    }

    public String getStatusName() {

        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }
}
