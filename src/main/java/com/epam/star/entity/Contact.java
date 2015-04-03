package com.epam.star.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

@MappedEntityForAdmin("Contact")
@Entity
public class Contact extends AbstractEntity {
    @Column
    private String owner;
    @Column
    private String telephone;
    @Column
    private String part;

    public String getPart() {
        return part;
    }

    public void setPart(String part) {
        this.part = part;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
}
