package cn.geng.service;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

/**
 * @Author: geng
 * @Date: 2021/6/30 9:47
 */
public interface Record {

    Integer deleteRecord(Integer from , Integer to);
    Integer saveRecord(Integer from,Integer to, String msg);
    Map<String,Long> getRecordCount(Integer send, Integer revicer);
    List<Map<String,Object>> getRecord(Integer sender,Integer revicer);
    LinkedHashSet<Map<String,Object>> knowChat(Integer uId);
}
