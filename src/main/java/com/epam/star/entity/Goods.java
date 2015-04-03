package com.epam.star.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@MappedEntityForAdmin("Goods")
@Entity
public class Goods extends AbstractEntity {

    @OneToMany(mappedBy = "goods", cascade = CascadeType.ALL,
                fetch = FetchType.EAGER)
    private Set<Image> images;
    @Column
    private String goodsName;
    @Column
    private BigDecimal price;
    @Column
    private BigDecimal countOnStoreHouse;
    @Column
    private boolean inCart;
    @OneToOne(mappedBy = "goods", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private OrderedGoods orderedGoods;

    public OrderedGoods getOrderedGoods() {
        return orderedGoods;
    }

    public void setOrderedGoods(OrderedGoods orderedGoods) {
        this.orderedGoods = orderedGoods;
    }

    public BigDecimal getCountOnStoreHouse() {
        return countOnStoreHouse;
    }

    public void setCountOnStoreHouse(BigDecimal countOnStoreHouse) {
        this.countOnStoreHouse = countOnStoreHouse;
    }

    public boolean isInCart() {
        return inCart;
    }

    public void setInCart(boolean inCart) {
        this.inCart = inCart;
    }

    public Set<Image> getImages() {
        return images;
    }

    public void setImages(Set<Image> images) {
        this.images = images;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getGoodsName() {

        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Goods)) return false;

        Goods goods = (Goods) o;

        if (inCart != goods.inCart) return false;
        if (goodsName != null ? !goodsName.equals(goods.goodsName) : goods.goodsName != null) return false;
        if (images != null ? !images.equals(goods.images) : goods.images != null) return false;
        if (price != null ? !price.equals(goods.price) : goods.price != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = images != null ? images.hashCode() : 0;
        result = 31 * result + (goodsName != null ? goodsName.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (inCart ? 1 : 0);
        return result;
    }
}
