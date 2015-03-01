package com.epam.star.entity;

import java.sql.Time;

@MappedEntityForAdmin("Period")
public class Period extends AbstractEntity {

    private Time period;

    public Time getPeriod() {
        return period;
    }

    public void setPeriod(Time period) {
        this.period = period;
    }

}
