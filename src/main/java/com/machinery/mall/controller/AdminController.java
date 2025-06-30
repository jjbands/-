package com.machinery.mall.controller;

import com.machinery.mall.entity.User;
import com.machinery.mall.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin
public class AdminController {

    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/users")
    public Map<String, Object> getAllUsers() {
        Map<String, Object> response = new HashMap<>();
        try {
            List<User> users = adminService.getAllUsers();
            response.put("status", 0);
            response.put("data", users);
            response.put("msg", "获取成功");
        } catch (Exception e) {
            response.put("status", 1);
            response.put("msg", "获取失败: " + e.getMessage());
        }
        return response;
    }

    @PostMapping("/user")
    public Map<String, Object> updateUser(@RequestBody User user) {
        Map<String, Object> response = new HashMap<>();
        try {
            int result = adminService.updateUser(user);
            response.put("status", result > 0 ? 0 : 1);
            response.put("msg", result > 0 ? "更新成功" : "更新失败");
        } catch (Exception e) {
            response.put("status", 1);
            response.put("msg", "更新失败: " + e.getMessage());
        }
        return response;
    }

    @GetMapping("/users/search")
    public Map<String, Object> searchUsers(@RequestParam String keyword) {
        Map<String, Object> response = new HashMap<>();
        try {
            List<User> users = adminService.searchUsers(keyword);
            response.put("status", 0);
            response.put("data", users);
            response.put("msg", "搜索成功");
        } catch (Exception e) {
            response.put("status", 1);
            response.put("msg", "搜索失败: " + e.getMessage());
        }
        return response;
    }

    @DeleteMapping("/user/{id}")
    public Map<String, Object> deleteUser(@PathVariable int id) {
        Map<String, Object> response = new HashMap<>();
        try {
            int result = adminService.deleteUser(id);
            response.put("status", result > 0 ? 0 : 1);
            response.put("msg", result > 0 ? "删除成功" : "删除失败");
        } catch (Exception e) {
            response.put("status", 1);
            response.put("msg", "删除失败: " + e.getMessage());
        }
        return response;
    }
}