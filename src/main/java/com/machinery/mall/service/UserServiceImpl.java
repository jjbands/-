package com.machinery.mall.service;

import com.machinery.mall.entity.User;
import com.machinery.mall.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

@Service
public class UserServiceImpl implements UserService {

    private UserMapper userMapper;
    @Autowired
    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;}
    @Override
    public int register(User user) {
        if (userMapper.countByAccount(user.getAccount()) > 0) {
            return -1;
        }

        if (userMapper.countByEmail(user.getEmail()) > 0) {
            return -2;
        }

        if (userMapper.countByPhone(user.getPhone()) > 0) {
            return -3;
        }

        user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
        user.setRole(1);
        user.setDel(0);

        return userMapper.insert(user);
    }

    @Override
    public User login(String loginKey, String password) {
        String md5Password = DigestUtils.md5DigestAsHex(password.getBytes());

        User user = userMapper.selectByAccountAndPassword(loginKey, md5Password);

        if (user == null) {
            user = userMapper.selectByPhoneAndPassword(loginKey, md5Password);
        }

        return user;
    }

    @Override
    public String getQuestionByAccount(String account) {
        return userMapper.selectQuestionByAccount(account);
    }

    @Override
    public String getQuestionByPhone(String phone) {
        return userMapper.selectQuestionByPhone(phone);
    }

    @Override
    public boolean checkAnswer(String loginKey, String question, String answer) {
        int count = userMapper.checkAnswerByAccount(loginKey, question, answer);

        if (count == 0) {
            count = userMapper.checkAnswerByPhone(loginKey, question, answer);
        }

        return count > 0;
    }

    @Override
    public boolean resetPassword(String loginKey, String newPassword) {
        String md5Password = DigestUtils.md5DigestAsHex(newPassword.getBytes());

        int result = userMapper.updatePasswordByAccount(loginKey, md5Password);

        if (result == 0) {
            result = userMapper.updatePasswordByPhone(loginKey, md5Password);
        }

        return result > 0;
    }
    @Override
    public User getUserByAccount(String account) {
        return userMapper.selectByAccount(account);
    }

    @Override
    public boolean updateUserProfile(User user) {
        return userMapper.updateProfile(user) > 0;
    }
}