package cn.geng.service;

import cn.geng.entity.User;

import java.util.Map;

/**
 * @Author: geng
 * @Date: 2021/6/30 14:48
 */
public interface UserService {
    Map<String,String> login(String name, String password);
    Boolean register(User user);
}
