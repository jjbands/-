package com.machinery.mall.service;

import com.machinery.mall.entity.User;
import com.machinery.mall.mapper.AdminMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    private final AdminMapper adminMapper;

    @Autowired
    public AdminServiceImpl(AdminMapper adminMapper) {
        this.adminMapper = adminMapper;
    }

    @Override
    public List<User> getAllUsers() {
        return adminMapper.getAllUsers();
    }

    @Override

    public List<User> getDeletedUsers() {
        return adminMapper.getDeletedUsers();
    }

    @Override
    public List<User> searchUsers(String keyword) {
        return adminMapper.searchUsers(keyword);
    }

    @Override
    public List<User> searchDeletedUsers(String keyword) {
        return adminMapper.searchDeletedUsers(keyword);
    }

    @Override
    public int updateUser(User user) {
        return adminMapper.updateUser(user);
    }

    @Override
    public int deleteUser(int id) {
        return adminMapper.deleteUser(id);
    }
    @Override
    public List<User> searchUsers(String keyword) {
        return adminMapper.searchUsers(keyword);

    public int softDeleteUser(int id) {
        return adminMapper.softDeleteUser(id);
    }

    @Override
    public int restoreUser(int id) {
        return adminMapper.restoreUser(id);
    }

    @Override
    public int forceDeleteUser(int id) {
        return adminMapper.forceDeleteUser(id);

    }
}