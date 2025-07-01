package com.machinery.mall.service;

import com.machinery.mall.entity.UserAddress;

import java.util.List;

/**
 * 用户地址Service接口
 * @author 你的名字
 * @version 1.0.0
 * @date: 2025/01/27
 */
public interface UserAddressService {
    
    /**
     * 添加新地址
     */
    boolean addAddress(UserAddress address);
    
    /**
     * 根据ID获取地址
     */
    UserAddress getAddressById(Integer id);
    
    /**
     * 根据用户ID获取所有地址
     */
    List<UserAddress> getAddressesByUserId(Integer userId);
    
    /**
     * 根据用户ID获取默认地址
     */
    UserAddress getDefaultAddressByUserId(Integer userId);
    
    /**
     * 更新地址信息
     */
    boolean updateAddress(UserAddress address);
    
    /**
     * 删除地址
     */
    boolean deleteAddress(Integer id);
    
    /**
     * 设置默认地址
     */
    boolean setDefaultAddress(Integer userId, Integer addressId);
    
    /**
     * 统计用户地址数量
     */
    int countAddressesByUserId(Integer userId);

    /**
     * 查询已删除地址
     */
    List<UserAddress> getDeletedAddressesByUserId(Integer userId);
    
    /**
     * 恢复地址
     */
    boolean restoreAddress(Integer id);
    
    /**
     * 物理删除
     */
    boolean permanentDeleteAddress(Integer id);

} 