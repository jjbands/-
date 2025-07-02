package com.machinery.mall.service;

/**
 * @author 你的名字
 * @version 1.0.0
 * @date: 2025/06/25  09:15
 */
import com.machinery.mall.entity.User;

public interface UserService {
    int register(User user);
    User login(String account, String password);

    String getQuestionByAccount(String account);

    String getQuestionByPhone(String phone);

    boolean checkAnswer(String account, String question, String asw);
    boolean resetPassword(String account, String newPassword);
    User getUserByAccount(String account);
    boolean updateUserProfile(User user);
}

