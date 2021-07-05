package cn.geng.controller;

import cn.geng.entity.User;
import cn.geng.service.UserService;
import cn.geng.util.AliOSSUtil;
import cn.geng.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;

/**
 * @Author: geng
 * @Date: 2021/6/30 14:52
 */
@RestController
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public Object register(User user) throws IOException {

         user.setImgUrl(AliOSSUtil.upload(user.getImg()));
         Boolean registed = userService.register(user);
         if(registed){
             return ResultUtil.success();
         }else {
             return ResultUtil.fail();
         }
    }
    @PostMapping("/login")
    public Object login(String name, String password){
        try{
            Map<String,String> isSuccessed = userService.login(name,password);
            if(isSuccessed == null){
                return ResultUtil.fail("201","无此用户");
            }
            if(isSuccessed.size() == 2){
                return ResultUtil.success(isSuccessed);
            }
        } catch (Exception e){
            e.printStackTrace();
            return ResultUtil.fail();
        }
       return ResultUtil.fail();

    }
}
