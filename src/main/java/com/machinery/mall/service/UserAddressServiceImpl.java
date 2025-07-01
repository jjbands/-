package com.machinery.mall.service;

import com.machinery.mall.entity.UserAddress;
import com.machinery.mall.mapper.UserAddressMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 用户地址Service实现类
 * @author 你的名字
 * @version 1.0.0
 * @date: 2025/01/27
 */
@Service
public class UserAddressServiceImpl implements UserAddressService {

    private UserAddressMapper userAddressMapper;

    @Autowired
    public UserAddressServiceImpl(UserAddressMapper userAddressMapper) {
        this.userAddressMapper = userAddressMapper;
    }

    @Override
    @Transactional
    public boolean addAddress(UserAddress address) {
        // 设置默认状态
        address.setIsDel(0);
        
        // 如果设置为默认地址，先清除其他默认地址
        if (address.getDfault() != null && address.getDfault() == 1) {
            userAddressMapper.clearDefaultByUserId(address.getUserId());
        } else {
            // 如果是第一个地址，自动设为默认
            int count = userAddressMapper.countByUserId(address.getUserId());
            if (count == 0) {
                address.setDfault(1);
            } else {
                address.setDfault(0);
            }
        }
        
        return userAddressMapper.insert(address) > 0;
    }

    @Override
    public UserAddress getAddressById(Integer id) {
        return userAddressMapper.selectById(id);
    }

    @Override
    public List<UserAddress> getAddressesByUserId(Integer userId) {
        return userAddressMapper.selectByUserId(userId);
    }

    @Override
    public UserAddress getDefaultAddressByUserId(Integer userId) {
        return userAddressMapper.selectDefaultByUserId(userId);
    }

    @Override
    @Transactional
    public boolean updateAddress(UserAddress address) {
        // 如果设置为默认地址，先清除其他默认地址
        if (address.getDfault() != null && address.getDfault() == 1) {
            userAddressMapper.clearDefaultByUserId(address.getUserId());
        }
        
        return userAddressMapper.update(address) > 0;
    }

    @Override
    public boolean deleteAddress(Integer id) {
        return userAddressMapper.deleteById(id) > 0;
    }

    @Override
    @Transactional
    public boolean setDefaultAddress(Integer userId, Integer addressId) {
        // 先清除所有默认地址
        userAddressMapper.clearDefaultByUserId(userId);
        
        // 设置新的默认地址
        return userAddressMapper.setDefaultById(addressId) > 0;
    }

    @Override
    public int countAddressesByUserId(Integer userId) {
        return userAddressMapper.countByUserId(userId);
    }


    @Override
    public List<UserAddress> getDeletedAddressesByUserId(Integer userId) {
        return userAddressMapper.selectDeletedByUserId(userId);
    }

    @Override
    public boolean restoreAddress(Integer id) {
        return userAddressMapper.restoreById(id) > 0;
    }

    @Override
    public boolean permanentDeleteAddress(Integer id) {
        return userAddressMapper.permanentDeleteById(id) > 0;
    }

} 