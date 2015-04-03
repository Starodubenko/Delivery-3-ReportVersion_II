package com.epam.star.entity;

import javax.persistence.*;

@Entity
public class AvatarImage extends Image {

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private Client user;

    public Client getUser() {
        return user;
    }

    public void setUser(Client user) {
        this.user = user;
    }
}
