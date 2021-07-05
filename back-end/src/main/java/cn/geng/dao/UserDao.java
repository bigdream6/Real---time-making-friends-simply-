package cn.geng.dao;

import cn.geng.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Author: geng
 * @Date: 2021/6/30 14:38
 */
@Component
@Mapper
public interface UserDao {
    Map<String,String> getUser(@Param("name") String name, @Param("password") String password);
    Integer saveUser(User user);
    String getUserName(Integer uId);
}
