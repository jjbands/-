package com.machinery.mall.mapper;

import com.machinery.mall.entity.UserAddress;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户地址Mapper接口
 * @author 你的名字
 * @version 1.0.0
 * @date: 2025/01/27
 */
@Mapper
public interface UserAddressMapper {
    
    /**
     * 插入新地址
     */
    int insert(UserAddress address);
    
    /**
     * 根据ID查询地址
     */
    UserAddress selectById(@Param("id") Integer id);
    
    /**
     * 根据用户ID查询所有地址
     */
    List<UserAddress> selectByUserId(@Param("userId") Integer userId);
    
    /**
     * 根据用户ID查询默认地址
     */
    UserAddress selectDefaultByUserId(@Param("userId") Integer userId);
    
    /**
     * 更新地址信息
     */
    int update(UserAddress address);
    
    /**
     * 删除地址（软删除）
     */
    int deleteById(@Param("id") Integer id);
    
    /**
     * 将用户的所有地址设为非默认
     */
    int clearDefaultByUserId(@Param("userId") Integer userId);
    
    /**
     * 设置指定地址为默认地址
     */
    int setDefaultById(@Param("id") Integer id);
    
    /**
     * 统计用户的地址数量
     */
    int countByUserId(@Param("userId") Integer userId);

    
    /**
     * 查询已删除地址
     */
    List<UserAddress> selectDeletedByUserId(@Param("userId") Integer userId);
    
    /**
     * 恢复地址
     */
    int restoreById(@Param("id") Integer id);
    
    /**
     * 物理删除
     */
    int permanentDeleteById(@Param("id") Integer id);

} 