package cn.geng.controller;

import cn.geng.service.Record;
import cn.geng.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: geng
 * @Date: 2021/7/2 9:09
 */
@RestController
@CrossOrigin
public class RecordController {

    @Autowired
    private Record record;

    @PostMapping("/record")
    public Object getRecord(Integer sender,Integer revicer){
        try{
            return ResultUtil.success( record.getRecord(sender, revicer) );
        } catch (Exception e){
            e.printStackTrace();
            return ResultUtil.fail();
        }
    }

    @GetMapping("/knowChat")
    public Object konwChat(Integer uId){
        return ResultUtil.success(record.knowChat(uId));
    }
}
