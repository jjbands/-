package com.machinery.mall.entity;

/**
 * @author 你的名字
 * @version 1.0.0
 * @date: 2025/06/26  14:56
 */
import java.math.BigDecimal;
import java.util.Date;

public class Products {
    private Integer id;
    private String name;
    private Integer productId;
    private Integer partsId;
    private String iconUrl;
    private String subImages;
    private String detail;
    private String specParam;
    private BigDecimal price;
    private Integer stock;
    private Integer status;
    private Integer isHot;
    private Date created;
    private Date updated;


    // getter/setter


    public Products(Integer id, String name, Integer partsId, Integer productId, String iconUrl, String subImages, String detail, String specParam, BigDecimal price, Integer stock, Integer status, Integer isHot, Date created, Date updated) {
        this.id = id;
        this.name = name;
        this.partsId = partsId;
        this.productId = productId;
        this.iconUrl = iconUrl;
        this.subImages = subImages;
        this.detail = detail;
        this.specParam = specParam;
        this.price = price;
        this.stock = stock;
        this.status = status;
        this.isHot = isHot;
        this.created = created;
        this.updated = updated;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getPartsId() {
        return partsId;
    }

    public void setPartsId(Integer partsId) {
        this.partsId = partsId;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getSubImages() {
        return subImages;
    }

    public void setSubImages(String subImages) {
        this.subImages = subImages;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getSpecParam() {
        return specParam;
    }

    public void setSpecParam(String specParam) {
        this.specParam = specParam;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getIsHot() {
        return isHot;
    }

    public void setIsHot(Integer isHot) {
        this.isHot = isHot;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }
}