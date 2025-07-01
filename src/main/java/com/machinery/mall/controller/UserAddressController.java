package com.machinery.mall.controller;

import com.machinery.mall.entity.User;
import com.machinery.mall.entity.UserAddress;
import com.machinery.mall.service.UserAddressService;
import com.machinery.mall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户地址管理Controller
 * @author 你的名字
 * @version 1.0.0
 * @date: 2025/01/27
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/address")
public class UserAddressController {

    private UserAddressService userAddressService;
    private UserService userService;

    @Autowired
    public UserAddressController(UserAddressService userAddressService, UserService userService) {
        this.userAddressService = userAddressService;
        this.userService = userService;
    }

    /**
     * 添加新地址
     */
    @PostMapping("/add")
    public Map<String, Object> addAddress(@RequestBody Map<String, Object> request) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            String account = (String) request.get("account");
            if (account == null || account.isEmpty()) {
                response.put("status", 1);
                response.put("msg", "账号参数缺失");
                return response;
            }

            // 根据账号获取用户ID
            User user = userService.getUserByAccount(account);
            if (user == null) {
                response.put("status", 2);
                response.put("msg", "用户不存在");
                return response;
            }

            UserAddress address = new UserAddress();
            address.setUserId(user.getId());
            address.setName((String) request.get("receiverName"));
            address.setPhone((String) request.get("receiverPhone"));
            address.setMobile((String) request.get("receiverPhone")); // 手机号同时设置到mobile字段
            address.setProvince((String) request.get("province"));
            address.setCity((String) request.get("city"));
            address.setDistrict((String) request.get("district"));
            address.setAddr((String) request.get("detailAddress"));
            address.setZip((String) request.get("postCode"));
            
            // 处理是否默认地址
            if (request.get("isDefault") != null) {
                address.setDfault(Integer.parseInt(request.get("isDefault").toString()));
            }

            boolean success = userAddressService.addAddress(address);
            
            if (success) {
                response.put("status", 0);
                response.put("msg", "地址添加成功");
                response.put("data", address.getId());
            } else {
                response.put("status", 3);
                response.put("msg", "地址添加失败");
            }
        } catch (Exception e) {
            response.put("status", 4);
            response.put("msg", "系统错误: " + e.getMessage());
        }
        
        return response;
    }

    /**
     * 获取用户所有地址
     */
    @PostMapping("/list")
    public Map<String, Object> getAddressList(@RequestBody Map<String, String> request) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            String account = request.get("account");
            if (account == null || account.isEmpty()) {
                response.put("status", 1);
                response.put("msg", "账号参数缺失");
                return response;
            }

            // 根据账号获取用户ID
            User user = userService.getUserByAccount(account);
            if (user == null) {
                response.put("status", 2);
                response.put("msg", "用户不存在");
                return response;
            }

            List<UserAddress> addresses = userAddressService.getAddressesByUserId(user.getId());
            
            response.put("status", 0);
            response.put("msg", "获取成功");
            response.put("data", addresses);
        } catch (Exception e) {
            response.put("status", 3);
            response.put("msg", "系统错误: " + e.getMessage());
        }
        
        return response;
    }

    /**
     * 获取默认地址
     */
    @PostMapping("/default")
    public Map<String, Object> getDefaultAddress(@RequestBody Map<String, String> request) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            String account = request.get("account");
            if (account == null || account.isEmpty()) {
                response.put("status", 1);
                response.put("msg", "账号参数缺失");
                return response;
            }

            // 根据账号获取用户ID
            User user = userService.getUserByAccount(account);
            if (user == null) {
                response.put("status", 2);
                response.put("msg", "用户不存在");
                return response;
            }

            UserAddress address = userAddressService.getDefaultAddressByUserId(user.getId());
            
            if (address != null) {
                response.put("status", 0);
                response.put("msg", "获取成功");
                response.put("data", address);
            } else {
                response.put("status", 3);
                response.put("msg", "暂无默认地址");
            }
        } catch (Exception e) {
            response.put("status", 4);
            response.put("msg", "系统错误: " + e.getMessage());
        }
        
        return response;
    }

    /**
     * 更新地址
     */
    @PostMapping("/update")
    public Map<String, Object> updateAddress(@RequestBody Map<String, Object> request) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            Integer addressId = Integer.parseInt(request.get("id").toString());
            if (addressId == null) {
                response.put("status", 1);
                response.put("msg", "地址ID参数缺失");
                return response;
            }

            UserAddress address = new UserAddress();
            address.setId(addressId);
            address.setName((String) request.get("receiverName"));
            address.setPhone((String) request.get("receiverPhone"));
            address.setMobile((String) request.get("receiverPhone")); // 手机号同时设置到mobile字段
            address.setProvince((String) request.get("province"));
            address.setCity((String) request.get("city"));
            address.setDistrict((String) request.get("district"));
            address.setAddr((String) request.get("detailAddress"));
            address.setZip((String) request.get("postCode"));
            
            // 处理是否默认地址
            if (request.get("isDefault") != null) {
                address.setDfault(Integer.parseInt(request.get("isDefault").toString()));
            }

            // 获取用户ID用于设置默认地址
            String account = (String) request.get("account");
            if (account != null && !account.isEmpty()) {
                User user = userService.getUserByAccount(account);
                if (user != null) {
                    address.setUserId(user.getId());
                }
            }

            boolean success = userAddressService.updateAddress(address);
            
            if (success) {
                response.put("status", 0);
                response.put("msg", "地址更新成功");
            } else {
                response.put("status", 2);
                response.put("msg", "地址更新失败");
            }
        } catch (Exception e) {
            response.put("status", 3);
            response.put("msg", "系统错误: " + e.getMessage());
        }
        
        return response;
    }

    /**
     * 删除地址
     */
    @PostMapping("/delete")
    public Map<String, Object> deleteAddress(@RequestBody Map<String, Object> request) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            Integer addressId = Integer.parseInt(request.get("id").toString());
            if (addressId == null) {
                response.put("status", 1);
                response.put("msg", "地址ID参数缺失");
                return response;
            }

            boolean success = userAddressService.deleteAddress(addressId);
            
            if (success) {
                response.put("status", 0);
                response.put("msg", "地址删除成功");
            } else {
                response.put("status", 2);
                response.put("msg", "地址删除失败");
            }
        } catch (Exception e) {
            response.put("status", 3);
            response.put("msg", "系统错误: " + e.getMessage());
        }
        
        return response;
    }

    /**
     * 设置默认地址
     */
    @PostMapping("/setDefault")
    public Map<String, Object> setDefaultAddress(@RequestBody Map<String, Object> request) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            String account = (String) request.get("account");
            Integer addressId = Integer.parseInt(request.get("addressId").toString());
            
            if (account == null || account.isEmpty()) {
                response.put("status", 1);
                response.put("msg", "账号参数缺失");
                return response;
            }
            
            if (addressId == null) {
                response.put("status", 2);
                response.put("msg", "地址ID参数缺失");
                return response;
            }

            // 根据账号获取用户ID
            User user = userService.getUserByAccount(account);
            if (user == null) {
                response.put("status", 3);
                response.put("msg", "用户不存在");
                return response;
            }

            boolean success = userAddressService.setDefaultAddress(user.getId(), addressId);
            
            if (success) {
                response.put("status", 0);
                response.put("msg", "默认地址设置成功");
            } else {
                response.put("status", 4);
                response.put("msg", "默认地址设置失败");
            }
        } catch (Exception e) {
            response.put("status", 5);
            response.put("msg", "系统错误: " + e.getMessage());
        }
        
        return response;
    }

    /**
     * 根据ID获取地址详情
     */
    @PostMapping("/detail")
    public Map<String, Object> getAddressDetail(@RequestBody Map<String, Object> request) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            Integer addressId = Integer.parseInt(request.get("id").toString());
            if (addressId == null) {
                response.put("status", 1);
                response.put("msg", "地址ID参数缺失");
                return response;
            }

            UserAddress address = userAddressService.getAddressById(addressId);
            
            if (address != null) {
                response.put("status", 0);
                response.put("msg", "获取成功");
                response.put("data", address);
            } else {
                response.put("status", 2);
                response.put("msg", "地址不存在");
            }
        } catch (Exception e) {
            response.put("status", 3);
            response.put("msg", "系统错误: " + e.getMessage());
        }
        
        return response;
    }
} 