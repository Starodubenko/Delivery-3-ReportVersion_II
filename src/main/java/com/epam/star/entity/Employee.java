package com.epam.star.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

@MappedEntityForAdmin("Employee")
@Entity
public class Employee extends Client {

    @Column(nullable = true)
    private String identityCard;
    @Column(nullable = true)
    private String workBook;
    @Column(nullable = true)
    private String RNN;
    @Column(nullable = true)
    private String SIK;

    public Employee(int id, String address, AvatarImage avatar, Discount discount, String firstName, String lastName, String login, String middleName, String mobilephone, String password, Position role, String telephone, BigDecimal virtualBalance, String identityCard, String RNN, String SIK, String workBook) {
        super(id, address, avatar, discount, firstName, lastName, login, middleName, mobilephone, password, role, telephone, virtualBalance);
        this.identityCard = identityCard;
        this.RNN = RNN;
        this.SIK = SIK;
        this.workBook = workBook;
    }

    public Employee() {

    }

    public String getSIK() {
        return SIK;
    }

    public void setSIK(String SIK) {
        this.SIK = SIK;
    }

    public String getRNN() {
        return RNN;
    }

    public void setRNN(String RNN) {
        this.RNN = RNN;
    }

    public String getWorkBook() {
        return workBook;
    }

    public void setWorkBook(String workBook) {
        this.workBook = workBook;
    }

    public String getIdentityCard() {
        return identityCard;
    }

    public void setIdentityCard(String identityCard) {
        this.identityCard = identityCard;
    }
}
