package com.epam.star.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

@MappedEntityForAdmin("Position")
@Entity
public class Position extends AbstractEntity {

    @Column
    private String positionName;
    @OneToOne(mappedBy = "position")
    private Client user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Position)) return false;

        Position position = (Position) o;

        if (positionName != null) {
            if (!(position.positionName.equals(positionName)))
                return false;
        } else {
            if (!(position.positionName == null))
                return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        if (positionName != null) return positionName.hashCode();
        else return 0;
    }

    public Client getUser() {
        return user;
    }

    public void setUser(Client user) {
        this.user = user;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }
}
