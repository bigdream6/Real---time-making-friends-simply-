package cn.geng.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @Author: geng
 * @Date: 2021/6/29 10:25
 */
@Repository
@Mapper
public interface RoomDao {
    String getRoomId();
    Integer saveNewRoom(String roomId);
    Integer updateRoomStatus(String roomId);
    Integer removeRoom(String roomId);
}
