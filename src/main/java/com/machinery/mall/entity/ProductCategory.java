package com.machinery.mall.entity;


import java.util.Date;

public class ProductCategory {
    private Integer id;
    private Integer parentId = 0; // 默认值为0
    private String name;
    private Integer sortOrder;
    private Integer status;
    private Integer level;
    private Date created;
    private Date updated;

    // 添加无参构造函数
    public ProductCategory() {
    }

    // 原有的全参构造函数
    public ProductCategory(Integer id, Integer parentId, String name, Integer sortOrder,
                           Integer status, Integer level, Date created, Date updated) {
        this.id = id;
        this.parentId = parentId != null ? parentId : 0;
        this.name = name;
        this.sortOrder = sortOrder != null ? sortOrder : 0;
        this.status = status != null ? status : 1;
        this.level = level;
        this.created = created;
        this.updated = updated;
    }


    // getter/setter

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
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