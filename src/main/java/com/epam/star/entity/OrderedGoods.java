package com.epam.star.entity;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;

@Entity
@Table(name = "ordered_goods")
public class OrderedGoods extends AbstractEntity {

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "goods_id")
    private Goods goods;
    @Column
    private int goodsCount;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "order_id")
    private Order order;

    public OrderedGoods() {
    }

    public OrderedGoods(Goods goods, int goodsCount) {
        this.goods = goods;
        this.goodsCount = goodsCount;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Goods getGoods() {
        return goods;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }

    public int getGoodsCount() {
        return goodsCount;
    }

    public void setGoodsCount(int goodsCount) {
        this.goodsCount = goodsCount;
    }
}
