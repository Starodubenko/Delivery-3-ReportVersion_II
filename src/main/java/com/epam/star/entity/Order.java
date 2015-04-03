package com.epam.star.entity;


import javax.persistence.*;
import java.sql.Date;

@MappedEntityForAdmin("Order")
@Entity
public class Order extends Cart {

    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int number;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private Client user;
    @Column(nullable = true)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "period_id")
    private Period period;
    @Column(nullable = true)
    private Date deliveryDate;
    @Column(nullable = true)
    private Date orderDate;
    @Column(nullable = true)
    private String additionalInfo;
    @Column(nullable = true)
    private boolean paid;
    @Column(nullable = true)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "discount_id")
    private Discount discount;
    @Column(nullable = true)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "status_id")
    private Status status;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(java.util.Date deliveryDate) {
        this.deliveryDate = new Date(deliveryDate.getTime());
    }

    public Discount getDiscount() {
        return discount;
    }

    public void setDiscount(Discount discount) {
        this.discount = discount;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(java.util.Date orderDate) {
        this.orderDate = new Date(orderDate.getTime());
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public Period getPeriod() {
        return period;
    }

    public void setPeriod(Period periodId) {
        this.period = periodId;
    }

    public Client getUser() {
        return user;
    }

    public void setUser(Client userID) {
        this.user = userID;
    }
}
