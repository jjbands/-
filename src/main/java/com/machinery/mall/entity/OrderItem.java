package com.machinery.mall.entity;

import java.math.BigDecimal;
import java.util.Date;

public class OrderItem {
    private Integer id;
    private Integer uid;
    private Integer orderId;
    private Integer goodsId;
    private String goodsName;
    private String iconUrl;
    private BigDecimal price;
    private Integer quantity;
    private BigDecimal totalPrice;
    private Date created;
    private Date updated;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Integer getUid() { return uid; }
    public void setUid(Integer uid) { this.uid = uid; }
    public Integer getOrderId() { return orderId; }
    public void setOrderId(Integer orderId) { this.orderId = orderId; }
    public Integer getGoodsId() { return goodsId; }
    public void setGoodsId(Integer goodsId) { this.goodsId = goodsId; }
    public String getGoodsName() { return goodsName; }
    public void setGoodsName(String goodsName) { this.goodsName = goodsName; }
    public String getIconUrl() { return iconUrl; }
    public void setIconUrl(String iconUrl) { this.iconUrl = iconUrl; }
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
    public BigDecimal getTotalPrice() { return totalPrice; }
    public void setTotalPrice(BigDecimal totalPrice) { this.totalPrice = totalPrice; }
    public Date getCreated() { return created; }
    public void setCreated(Date created) { this.created = created; }
    public Date getUpdated() { return updated; }
    public void setUpdated(Date updated) { this.updated = updated; }
} 