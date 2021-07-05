package cn.geng.controller;

import cn.geng.dao.RecordDao;
import cn.geng.service.Record;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: geng
 * @Date: 2021/7/1 19:03
 */
@CrossOrigin
@ServerEndpoint("/chat/{sender}/{receiver}")
@Component
public class ChatSocketServer {


    private static Record record;

    @Autowired
    public void setRoomService(Record record) {
        ChatSocketServer.record = record;
    }

    private static  Map<Session, Map<String,Integer>> sessionMap = new ConcurrentHashMap<>();
    @OnOpen
    public void onOpen(Session session, @PathParam("sender") Integer sender , @PathParam("receiver") Integer receiver){
//        sessionMap.put(uId,session);
        HashMap<String,Integer> userMap = new HashMap<>();
        Session goalSession = null;
        for(Map.Entry<Session,Map<String,Integer>> e : sessionMap.entrySet()){

            if(e.getValue().get("sender").equals(sender) && e.getValue().get("reveiver").equals(receiver)){
                goalSession = e.getKey();
            }
        }
        if(goalSession != null){
            sessionMap.remove(goalSession);
        }
        userMap.put("sender",sender);
        userMap.put("reveiver",receiver);
        sessionMap.put(session,userMap);
        System.out.println(sessionMap);
    }
    @OnClose
    public void onClose( Session session){
        System.out.println("close-------");
        sessionMap.remove(session);
    }
    @OnError
    public void onError(Throwable throwable) {
        throwable.printStackTrace();
    }

    @OnMessage
    public  void onMessage(Session session,String message){
        System.out.println(session);
        System.out.println(sessionMap);
        System.out.println(message);
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String,Object> result = new HashMap<>();
            Map<String, Integer> map = sessionMap.get(session);
            Integer sender = map.get("sender");
            Integer receiver = map.get("reveiver");
            record.saveRecord( sender,receiver,message);
            result.put("sender",sender);
            result.put("recevier",receiver);
            result.put("message",message);
            System.out.println(sender);
            System.out.println(receiver);
            a:for (Map.Entry<Session, Map<String, Integer>> e : sessionMap.entrySet()) {
                for (Map.Entry<String, Integer> s : e.getValue().entrySet()) {
                    if (s.getKey().equals("sender") && s.getValue().equals(receiver)) {
                        e.getKey().getAsyncRemote().sendText(objectMapper.writeValueAsString(result));
                        break a;

                    }
                }
            }
            session.getAsyncRemote().sendText(objectMapper.writeValueAsString(result));
        } catch (Exception e){
            e.printStackTrace();

        }

    }
}
