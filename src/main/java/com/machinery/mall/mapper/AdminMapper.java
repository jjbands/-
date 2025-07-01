package com.machinery.mall.mapper;

import com.machinery.mall.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface AdminMapper {
    List<User> getAllUsers();
    List<User> getDeletedUsers();
    List<User> searchUsers(@Param("keyword") String keyword);
    List<User> searchDeletedUsers(@Param("keyword") String keyword);
    int updateUser(User user);
    int softDeleteUser(@Param("id") int id);
    int restoreUser(@Param("id") int id);
    int forceDeleteUser(@Param("id") int id);
}