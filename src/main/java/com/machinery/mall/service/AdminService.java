package com.machinery.mall.service;

import com.machinery.mall.entity.User;
import java.util.List;

public interface AdminService {
    List<User> getAllUsers();
    List<User> getDeletedUsers();
    List<User> searchUsers(String keyword);
    List<User> searchDeletedUsers(String keyword);
    int updateUser(User user);
    int softDeleteUser(int id);
    int restoreUser(int id);
    int forceDeleteUser(int id);
}