package cn.geng.dao;

import cn.geng.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
 * @Author: geng
 * @Date: 2021/7/1 15:54
 */
@Mapper
@Component
public interface UserInforDao {

    Integer saveInfor(User user);
    String getImgUrl(Integer uId);
}
