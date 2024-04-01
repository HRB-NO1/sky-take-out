package com.sky.service;

import com.sky.dto.UserLoginDTO;
import com.sky.entity.User;

public interface UserService {

    /**
     * Wechat Login
     *
     * @param userLoginDTO User Login DTO
     * @return User
     */
    User wechatLogin(UserLoginDTO userLoginDTO);
}
