package cn.geng.service.impl;

import cn.geng.dao.UserDao;
import cn.geng.dao.UserInforDao;
import cn.geng.entity.User;
import cn.geng.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @Author: geng
 * @Date: 2021/6/30 14:49
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private UserInforDao userInforDao;

    @Override
    public Map<String,String> login(String name, String password) {
        return userDao.getUser(name, password);
    }

    @Override
    public Boolean register(User user) {
        try{
            Integer saved = userDao.saveUser(user);
            Integer inforSaved = userInforDao.saveInfor(user);
            if(saved == 1 && inforSaved == 1){
                return true;
            }else{
                return false;
            }
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }

    }
}
