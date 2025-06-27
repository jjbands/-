package com.machinery.mall.mapper;

import com.machinery.mall.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
@Mapper
public interface UserMapper {
    int insert(User user);
    User selectByAccountAndPassword(@Param("account") String account, @Param("password") String password);
    User selectByPhoneAndPassword(@Param("phone") String phone, @Param("password") String password);
    int countByAccount(@Param("account") String account);
    int countByEmail(@Param("email") String email);
    int countByPhone(@Param("phone") String phone);
    String selectQuestionByAccount(@Param("account") String account);
    int checkAnswerByAccount(@Param("account") String account, @Param("question") String question, @Param("asw") String asw);
    int updatePasswordByAccount(@Param("account") String account, @Param("password") String password);
    String selectQuestionByPhone(@Param("phone") String phone);
    int checkAnswerByPhone(@Param("phone") String phone,
                           @Param("question") String question,
                           @Param("answer") String answer);

    int updatePasswordByPhone(@Param("phone") String phone,
                              @Param("password") String password);
}