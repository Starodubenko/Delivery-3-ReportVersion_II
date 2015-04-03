package com.epam.star.entity;

import javax.persistence.*;
import java.math.BigDecimal;

@MappedEntityForAdmin("PayCard")
@Entity
@Table(name = "pay_card")
public class PayCard extends AbstractEntity {

    @Column
    private String serialNumber;
    @Column
    private String secretNumber;
    @Column
    private BigDecimal balance;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "status_id")
    private StatusPayCard statusPayCard;

    public StatusPayCard getStatusPayCard() {
        return statusPayCard;
    }

    public void setStatusPayCard(StatusPayCard statusPayCard) {
        this.statusPayCard = statusPayCard;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getSecretNumber() {
        return secretNumber;
    }

    public void setSecretNumber(String secretNumber) {
        this.secretNumber = secretNumber;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }
}
