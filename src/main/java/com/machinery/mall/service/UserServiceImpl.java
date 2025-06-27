package com.machinery.mall.service;

/**
 * @author 你的名字
 * @version 1.0.0
 * @date: 2025/06/25  09:15
 */
import com.machinery.mall.entity.User;
import com.machinery.mall.mapper.UserMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

@Service
public class UserServiceImpl implements UserService {


    private UserMapper userMapper;
    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;}
    @Override
    public int register(User user) {
        // 检查用户名是否已存在
        if (userMapper.countByAccount(user.getAccount()) > 0) {
            return -1; // 用户名已存在
        }

        // 检查邮箱是否已注册
        if (userMapper.countByEmail(user.getEmail()) > 0) {
            return -2; // 邮箱已注册
        }

        // 检查手机号是否已使用
        if (userMapper.countByPhone(user.getPhone()) > 0) {
            return -3; // 手机号已使用
        }

        // 密码加密
        user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
        user.setRole(1); // 默认普通用户
        user.setDel(0); // 未删除

        return userMapper.insert(user);
    }

    @Override
    public User login(String loginKey, String password) {
        // 密码加密
        String md5Password = DigestUtils.md5DigestAsHex(password.getBytes());

        // 先尝试用用户名登录
        User user = userMapper.selectByAccountAndPassword(loginKey, md5Password);

        // 如果用户名登录失败，尝试用手机号登录
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
        // 先尝试用用户名验证
        int count = userMapper.checkAnswerByAccount(loginKey, question, answer);

        // 如果用户名验证失败，尝试用手机号验证
        if (count == 0) {
            count = userMapper.checkAnswerByPhone(loginKey, question, answer);
        }

        return count > 0;
    }

    @Override
    public boolean resetPassword(String loginKey, String newPassword) {
        String md5Password = DigestUtils.md5DigestAsHex(newPassword.getBytes());

        // 先尝试用用户名更新密码
        int result = userMapper.updatePasswordByAccount(loginKey, md5Password);

        // 如果用户名更新失败，尝试用手机号更新
        if (result == 0) {
            result = userMapper.updatePasswordByPhone(loginKey, md5Password);
        }

        return result > 0;
    }
}