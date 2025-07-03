package com.machinery.mall.entity;

import java.util.Date;


public class UserAddress {
    private Integer id;
    private Integer userId;
    private String name;           // 收件人姓名
    private String phone;          // 收件人固定电话号码
    private String mobile;         // 收件人手机号码
    private String province;       // 省份
    private String city;           // 城市
    private String district;       // 区县
    private String addr;           // 详细地址
    private String zip;            // 邮政编码
    private Integer dfault;        // 是否默认地址 0-否 1-是
    private Integer isDel;         // 状态 0-正常 1-删除
    private Date created;
    private Date updated;

    // 构造函数
    public UserAddress() {}

    public UserAddress(Integer id, Integer userId, String name, String phone, String mobile,

                       String province, String city, String district, String addr, String zip,
                       Integer dfault, Integer isDel, Date created, Date updated) {

        this.id = id;
        this.userId = userId;
        this.name = name;
        this.phone = phone;
        this.mobile = mobile;
        this.province = province;
        this.city = city;
        this.district = district;
        this.addr = addr;
        this.zip = zip;
        this.dfault = dfault;
        this.isDel = isDel;
        this.created = created;
        this.updated = updated;
    }

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public Integer getDfault() {
        return dfault;
    }

    public void setDfault(Integer dfault) {
        this.dfault = dfault;
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
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

    @Override
    public String toString() {
        return "UserAddress{" +
                "id=" + id +
                ", userId=" + userId +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", mobile='" + mobile + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", district='" + district + '\'' +
                ", addr='" + addr + '\'' +
                ", zip='" + zip + '\'' +
                ", dfault=" + dfault +
                ", isDel=" + isDel +
                ", created=" + created +
                ", updated=" + updated +
                '}';
    }
} 