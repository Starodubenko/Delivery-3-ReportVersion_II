package com.epam.star.entity;

import javax.persistence.*;

@MappedSuperclass
public abstract class AbstractEntity {

    public AbstractEntity() {}

    protected AbstractEntity(int id) {
        this.id = id;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private boolean deleted;

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AbstractEntity)) return false;

        AbstractEntity entity = (AbstractEntity) o;

        if (id != entity.id) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
