package com.machinery.mall.controller;

import com.machinery.mall.entity.User;
import com.machinery.mall.service.UserService;
import com.machinery.mall.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/user")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public Map<String, Object> register(@RequestBody User user) {
        Map<String, Object> response = new HashMap<>();
        int result = userService.register(user);

        if (result > 0) {
            response.put("status", 0);
            response.put("msg", "注册成功");
        } else if (result == -1) {
            response.put("status", 1);
            response.put("msg", "用户名已存在");
        } else if (result == -2) {
            response.put("status", 2);
            response.put("msg", "邮箱已注册");
        } else if (result == -3) {
            response.put("status", 3);
            response.put("msg", "手机号已使用");
        } else {
            response.put("status", 4);
            response.put("msg", "注册失败");
        }

        return response;
    }

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody Map<String, String> loginRequest) {
        Map<String, Object> response = new HashMap<>();
        String loginKey = loginRequest.get("loginKey");
        String password = loginRequest.get("password");

        User user = userService.login(loginKey, password);

        if (user != null) {
            response.put("status", 0);
            response.put("msg", "登录成功");
            response.put("data", user);
        } else {
            response.put("status", 1);
            response.put("msg", "用户名/手机号或密码错误");
        }

        return response;
    }

    @PostMapping("/security_question")
    public Map<String, Object> getSecurityQuestion(@RequestBody Map<String, String> request) {
        Map<String, Object> response = new HashMap<>();
        String loginKey = request.get("loginKey");

        String question = userService.getQuestionByAccount(loginKey);

        if (question == null) {
            question = userService.getQuestionByPhone(loginKey);
        }

        if (question != null) {
            response.put("status", 0);
            response.put("msg", "成功");
            response.put("question", question);
        } else {
            response.put("status", 1);
            response.put("msg", "用户不存在或未设置安全问题");
        }

        return response;
    }

    @PostMapping("/verify_answer")
    public Map<String, Object> verifyAnswer(@RequestBody Map<String, String> request) {
        Map<String, Object> response = new HashMap<>();
        String loginKey = request.get("loginKey");
        String question = request.get("question");
        String answer = request.get("answer");

        boolean valid = userService.checkAnswer(loginKey, question, answer);

        if (valid) {
            response.put("status", 0);
            response.put("msg", "验证成功");
        } else {
            response.put("status", 1);
            response.put("msg", "答案不正确");
        }

        return response;
    }

    @PostMapping("/reset_password")
    public Map<String, Object> resetPassword(@RequestBody Map<String, String> request) {
        Map<String, Object> response = new HashMap<>();
        String loginKey = request.get("loginKey");
        String newPassword = request.get("newPassword");

        boolean success = userService.resetPassword(loginKey, newPassword);

        if (success) {
            response.put("status", 0);
            response.put("msg", "密码重置成功");
        } else {
            response.put("status", 1);
            response.put("msg", "密码重置失败");
        }

        return response;
    }
}