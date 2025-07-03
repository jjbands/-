package com.machinery.mall.entity;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class Order {
    private Integer id;
    private Long orderNo;
    private Integer uid;
    private Integer addrId;
    private BigDecimal amount;
    private Integer type;
    private Integer freight;
    private Integer status;
    private Date paymentTime;
    private Date deliveryTime;
    private Date finishTime;
    private Date closeTime;
    private Date created;
    private Date updated;
    private List<OrderItem> items;
    // 添加一个临时字段用于前端显示
    private String address;

    // getter 和 setter
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Long getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Long orderNo) {
        this.orderNo = orderNo;
    }
    public Integer getUid() { return uid; }
    public void setUid(Integer uid) { this.uid = uid; }
    public Integer getAddrId() { return addrId; }
    public void setAddrId(Integer addrId) { this.addrId = addrId; }
    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    public Integer getType() { return type; }
    public void setType(Integer type) { this.type = type; }
    public Integer getFreight() { return freight; }
    public void setFreight(Integer freight) { this.freight = freight; }
    public Integer getStatus() { return status; }
    public void setStatus(Integer status) { this.status = status; }
    public Date getPaymentTime() { return paymentTime; }
    public void setPaymentTime(Date paymentTime) { this.paymentTime = paymentTime; }
    public Date getDeliveryTime() { return deliveryTime; }
    public void setDeliveryTime(Date deliveryTime) { this.deliveryTime = deliveryTime; }
    public Date getFinishTime() { return finishTime; }
    public void setFinishTime(Date finishTime) { this.finishTime = finishTime; }
    public Date getCloseTime() { return closeTime; }
    public void setCloseTime(Date closeTime) { this.closeTime = closeTime; }
    public Date getCreated() { return created; }
    public void setCreated(Date created) { this.created = created; }
    public Date getUpdated() { return updated; }
    public void setUpdated(Date updated) { this.updated = updated; }
} 