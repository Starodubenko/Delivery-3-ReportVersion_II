package com.epam.star.entity;

import javax.persistence.*;

@MappedEntityForAdmin("Image")
@Entity
public class Image extends AbstractEntity {

    @Column
    private String filename;
    @Column
    private byte[] content;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "goods_id")
    private Goods goods;

    public Image() {
    }

    public Image(int id, String filename, byte[] content) {
        super(id);
        this.filename = filename;
        this.content = content;
    }

    public Image(String filename, byte[] content) {
        this.filename = filename;
        this.content = content;
    }

    public Goods getGoods() {
        return goods;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }
}
