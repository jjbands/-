package com.machinery.mall.service;

import com.machinery.mall.entity.User;
import java.util.List;

public interface AdminService {
    List<User> getAllUsers();
    int updateUser(User user);
    int deleteUser(int id);
    List<User> searchUsers(String keyword);
}