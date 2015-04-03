package com.epam.star.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

@MappedEntityForAdmin("Discount")
@Entity
public class Discount extends AbstractEntity {

    @Column
    private String name;
    @Column
    private int percentage;
    @OneToOne(mappedBy = "discount", cascade = CascadeType.ALL)
    private Client user;

    public Client getUser() {
        return user;
    }

    public void setUser(Client user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPercentage() {
        return percentage;
    }

    public void setPercentage(int percentage) {
        this.percentage = percentage;
    }
}
