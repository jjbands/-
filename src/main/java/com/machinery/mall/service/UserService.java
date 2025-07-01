package com.machinery.mall.service;


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

