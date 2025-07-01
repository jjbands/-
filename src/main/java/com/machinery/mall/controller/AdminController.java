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

@CrossOrigin(origins = "*")

public class AdminController {

    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }


    // 获取活跃用户

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


    // 获取已删除用户
    @GetMapping("/users/deleted")
    public Map<String, Object> getDeletedUsers() {
        Map<String, Object> response = new HashMap<>();
        try {
            List<User> users = adminService.getDeletedUsers();
            response.put("status", 0);
            response.put("data", users);
            response.put("msg", "获取成功");
        } catch (Exception e) {
            response.put("status", 1);
            response.put("msg", "获取失败: " + e.getMessage());

        }
        return response;
    }

    // 搜索活跃用户

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


    // 搜索已删除用户
    @GetMapping("/users/deleted/search")
    public Map<String, Object> searchDeletedUsers(@RequestParam String keyword) {
        Map<String, Object> response = new HashMap<>();
        try {
            List<User> users = adminService.searchDeletedUsers(keyword);
            response.put("status", 0);
            response.put("data", users);
            response.put("msg", "搜索成功");
        } catch (Exception e) {
            response.put("status", 1);
            response.put("msg", "搜索失败: " + e.getMessage());
        }
        return response;
    }

    // 更新用户信息
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

    // 假删除用户
    @DeleteMapping("/user/{id}")
    public Map<String, Object> softDeleteUser(@PathVariable int id) {
        Map<String, Object> response = new HashMap<>();
        try {
            int result = adminService.softDeleteUser(id);
            response.put("status", result > 0 ? 0 : 1);
            response.put("msg", result > 0 ? "删除成功" : "删除失败(可能尝试删除管理员)");

        } catch (Exception e) {
            response.put("status", 1);
            response.put("msg", "删除失败: " + e.getMessage());
        }
        return response;
    }



    // 恢复用户
    @PostMapping("/user/{id}/restore")
    public Map<String, Object> restoreUser(@PathVariable int id) {
        Map<String, Object> response = new HashMap<>();
        try {
            int result = adminService.restoreUser(id);
            response.put("status", result > 0 ? 0 : 1);
            response.put("msg", result > 0 ? "恢复成功" : "恢复失败");
        } catch (Exception e) {
            response.put("status", 1);
            response.put("msg", "恢复失败: " + e.getMessage());
        }
        return response;
    }

    // 强制删除用户
    @DeleteMapping("/user/{id}/force")
    public Map<String, Object> forceDeleteUser(@PathVariable int id) {
        Map<String, Object> response = new HashMap<>();
        try {
            int result = adminService.forceDeleteUser(id);
            response.put("status", result > 0 ? 0 : 1);
            response.put("msg", result > 0 ? "永久删除成功" : "永久删除失败");
        } catch (Exception e) {
            response.put("status", 1);
            response.put("msg", "永久删除失败: " + e.getMessage());
        }
        return response;
    }

}