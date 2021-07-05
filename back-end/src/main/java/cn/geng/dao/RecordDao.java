package cn.geng.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import javax.websocket.server.PathParam;
import java.util.List;
import java.util.Map;

/**
 * @Author: geng
 * @Date: 2021/6/29 15:02
 */
@Mapper
@Component
public interface RecordDao {
    Integer saveRecord(@Param("from") Integer from, @Param("to") Integer to , @Param("msg") String msg);
    Map<String,Long> getRecordCount(@Param("from") Integer from, @Param("to") Integer to);
    Integer delRecord(@Param("from") Integer from, @Param("to") Integer to);
    List<Map<String,Object>> getRecord(@Param("sender") Integer sender, @Param("revicer") Integer revicer);
    List<Map<String,Object>> knowChat(Integer uId);
}
