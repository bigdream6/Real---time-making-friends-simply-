package cn.geng.controller;

import cn.geng.service.RoomService;
import cn.geng.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: geng
 * @Date: 2021/6/29 10:52
 */
@RestController
@CrossOrigin
public class RoomController {
    @Autowired
    private RoomService roomService;

    @RequestMapping("/room_id")
    public Object getRoomId(){
        try{
            synchronized (this.roomService){
                String rId = roomService.getRoomId();
                if(rId != null){
                    return ResultUtil.success(rId);
                }
                return ResultUtil.fail();
            }
        } catch (Exception e){
            e.printStackTrace();
            return ResultUtil.fail();
        }

    }
}
