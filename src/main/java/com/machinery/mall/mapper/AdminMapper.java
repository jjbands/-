package com.machinery.mall.mapper;

import com.machinery.mall.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AdminMapper {
    List<User> getAllUsers();
    int updateUser(User user);
    int deleteUser(@Param("id") int id);
    List<User> searchUsers(@Param("keyword") String keyword);
}