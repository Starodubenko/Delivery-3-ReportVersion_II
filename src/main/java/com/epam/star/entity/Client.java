package com.epam.star.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@MappedEntityForAdmin("Client")
@Entity
@Table(name = "users")
@DiscriminatorColumn(name="dtype")
public class Client extends AbstractEntity {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "avatar_id", nullable = true)
    private AvatarImage avatar;
    @Column
    private String login;
    @Column
    private String password;
    @Column(nullable = true)
    private String firstName;
    @Column(nullable = true)
    private String lastName;
    @Column(nullable = true)
    private String middleName;
    @Column(nullable = true)
    private String address;
    @Column
    private String telephone;
    @Column(nullable = true)
    private String mobilephone;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="position_id")
    private Position position;
    @Column
    private BigDecimal virtualBalance;
    @Column(nullable = true)
    private String email;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="discount_id")
    private Discount discount;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<Order> orders;


    public Client() {
    }

    public Client(int id, String address, AvatarImage avatar, Discount discount, String firstName, String lastName, String login, String middleName, String mobilephone, String password, Position position, String telephone, BigDecimal virtualBalance) {
        super(id);
        this.address = address;
        this.avatar = avatar;
        this.discount = discount;
        this.firstName = firstName;
        this.lastName = lastName;
        this.login = login;
        this.middleName = middleName;
        this.mobilephone = mobilephone;
        this.password = password;
        this.position = position;
        this.telephone = telephone;
        this.virtualBalance = virtualBalance;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public AvatarImage getAvatar() {
        return avatar;
    }

    public void setAvatar(AvatarImage avatar) {
        this.avatar = avatar;
    }

    public String getMobilephone() {
        return mobilephone;
    }

    public void setMobilephone(String mobilephone) {
        this.mobilephone = mobilephone;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public BigDecimal getVirtualBalance() {
        return virtualBalance;
    }

    public void setVirtualBalance(BigDecimal virtualBalance) {
        this.virtualBalance = virtualBalance;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Discount getDiscount() {
        return discount;
    }

    public void setDiscount(Discount discount) {
        this.discount = discount;
    }
}
