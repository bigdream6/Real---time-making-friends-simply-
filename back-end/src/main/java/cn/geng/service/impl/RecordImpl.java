package cn.geng.service.impl;

import cn.geng.dao.RecordDao;
import cn.geng.dao.UserDao;
import cn.geng.dao.UserInforDao;
import cn.geng.service.Record;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @Author: geng
 * @Date: 2021/6/30 9:48
 */
@Service
public class RecordImpl implements Record {
    @Autowired
    private RecordDao recordDao;
    @Autowired
    private UserInforDao userInforDao;
    @Autowired
    private UserDao userDao;
    @Override
    public Integer deleteRecord(Integer from, Integer to) {
        return recordDao.delRecord(from,to);
    }

    @Override
    public Integer saveRecord(Integer from, Integer to, String msg) {
        System.out.println(from+""+to+""+msg);
        return recordDao.saveRecord(from,to,msg);
    }

    @Override
    public Map<String,Long> getRecordCount(Integer send, Integer revicer) {
        return recordDao.getRecordCount(send,revicer);
    }

    @Override
    public List<Map<String, Object>> getRecord(Integer sender,Integer revicer) {
        return recordDao.getRecord(sender, revicer);
    }

    @Override
    public LinkedHashSet<Map<String,Object>> knowChat(Integer uId) {
        List<Map<String, Object>> maps = recordDao.knowChat(uId);
        LinkedHashSet<Map<String,Object>> revicers = new LinkedHashSet<>();

        for ( Map<String, Object> s : maps){
            for(Map.Entry<String,Object> g : s.entrySet()){
                if(g.getValue().equals(uId)){
                    if(g.getKey().equals("revicer")){
                        Map<String, Object> recivers = new HashMap<>();
                        recivers.put("uId",s.get("send"));
                        revicers.add(recivers);
                    }else{
                        Map<String, Object> recivers = new HashMap<>();
                        recivers.put("uId",s.get("revicer"));
                        revicers.add(recivers);
                    }
                }
            }
        }
        for (Map<String, Object> i: revicers){
            List<Map<String, Object>> record = recordDao.getRecord(uId, (Integer) i.get("uId"));
            Map<String, Object> records = record.get(record.size()-1);
            String imgUrl = userInforDao.getImgUrl((Integer) i.get("uId"));
            i.put("records",records);
            i.put("img_url",imgUrl);
            i.put("name",userDao.getUserName( (Integer) i.get("uId")));
        }
        return revicers;
    }
}
