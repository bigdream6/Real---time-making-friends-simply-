package cn.geng.service.impl;

import cn.geng.dao.RoomDao;
import cn.geng.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * @Author: geng
 * @Date: 2021/6/29 10:41
 */
@Service
public class RoomServiceImpl implements RoomService {

    @Autowired
    private RoomDao roomDao;
    @Override
    public String getRoomId() {
        String s = roomDao.getRoomId();
        if(s == null){
            String rId =  UUID.randomUUID().toString();
            Integer result = roomDao.saveNewRoom(rId);
            if(result == 1){
                return rId;
            }
            return null;
        }
        roomDao.updateRoomStatus(s);
        return s;
    }

    @Override
    public Integer removeRoomId( String rId) {
        return roomDao.removeRoom(rId);
    }
}
