package com.machinery.mall.entity;

/**
 * @author 你的名字
 * @version 1.0.0
 * @date: 2025/06/26  14:55
 */
import java.util.Date;
import java.util.List;

public class ProductCategory {
    private Integer id;
    private Integer parentId;
    private String name;
    private Integer sortOrder;
    private Integer status;
    private Integer level;
    private Date created;
    private Date updated;
    private List<ProductCategory> children;

    public ProductCategory(Integer id, Integer parentId, String name, Integer sortOrder, Integer status, Integer level, Date created, Date updated) {
        this.id = id;
        this.parentId = parentId;
        this.name = name;
        this.sortOrder = sortOrder;
        this.status = status;
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

    public List<ProductCategory> getChildren() {
        return children;
    }

    public void setChildren(List<ProductCategory> children) {
        this.children = children;
    }
}