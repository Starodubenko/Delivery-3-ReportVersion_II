package com.epam.star.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "user_order")
public class Cart extends AbstractEntity{

    @OneToMany(mappedBy = "order", fetch = FetchType.EAGER)
    private Set<OrderedGoods> orderedGoods;

    public Set<OrderedGoods> getOrderedGoods() {
        return orderedGoods;
    }

    public void setOrderedGoods(Set<OrderedGoods> orderedGoods) {
        this.orderedGoods = orderedGoods;
    }

    public BigDecimal getTotalSum() {
        BigDecimal totalSum = new BigDecimal(0);

        for (OrderedGoods orderedGood : orderedGoods) {
            BigDecimal price = orderedGood.getGoods().getPrice();
            Integer count = orderedGood.getGoodsCount();
            BigDecimal cost = price.multiply(new BigDecimal(count));
            totalSum = totalSum.add(cost);
        }

        return totalSum;
    }

    public int getGoodsCount() {
        return orderedGoods.size();
    }

    public void addGoods(Goods goods) {
        this.orderedGoods.add(new OrderedGoods(goods, 1));
    }

    public void setGoodsCount(Goods goods, int count) {

        for (OrderedGoods orderedGood : orderedGoods) {
            if (orderedGood.getGoods().equals(goods)) orderedGood.setGoodsCount(count);
        }
    }

    public void removeGoods(Goods goods) {
        this.orderedGoods.remove(goods);
    }

    public BigDecimal getCostByGoodsId(int id) {

        for (OrderedGoods orderedGood : orderedGoods) {
            if (orderedGood.getGoods().getId() == id) {
                return orderedGood.getGoods().getPrice().multiply(new BigDecimal(orderedGood.getGoodsCount()));
            }
        }
        return null;
    }

    public List<Goods> getGoodsList() {

        List<Goods> goods = new ArrayList<>();
        for (OrderedGoods orderedGood : orderedGoods) {
            goods.add(orderedGood.getGoods());
        }
        return goods;
    }

    public void clear() {
        orderedGoods.clear();
    }
}
