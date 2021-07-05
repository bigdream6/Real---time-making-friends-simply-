package cn.geng.controller;

import cn.geng.service.Record;
import cn.geng.service.RoomService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: geng
 * @Date: 2021/6/29 9:50
 */
@CrossOrigin
@ServerEndpoint("/room/{openid}/{uId}")
@Component
public class WebSocketServer {
    /**
     * 存放所有在线的客户端
     */
//    private static Map<String, List<Session>> clients = new ConcurrentHashMap<>();
    private static Map<String, Map<Session,Integer>> clients = new ConcurrentHashMap<>();

    private static RoomService roomService;
    private static Record record;
    @Autowired
    public void setRoomService(RoomService roomService) {
        WebSocketServer.roomService = roomService;
    }

    @Autowired
    public void setRoomService(Record record) {
        WebSocketServer.record = record;
    }
    @OnOpen
    public void onOpen(Session session,@PathParam(value = "openid") String openid, @PathParam(value = "uId") Integer uId) {
        //将新用户存入在线的组
        if(clients.containsKey(openid)){
            if(clients.get(openid).size() >= 2){
                return;
            }
            clients.get(openid).put(session,uId);
        }else{
            HashMap<Session,Integer> binds = new HashMap<>();
            binds.put(session,uId);
            clients.put(openid, binds);
        }
        System.out.println(clients);
    }

    /**
     * 客户端关闭
     * @param session session
     */
    @OnClose
    public void onClose(Session session, @PathParam(value = "openid") String openid) throws JsonProcessingException {
        Session gaolSession = null;
        Integer from = null;
        Integer to = null;
        //将掉线的用户移除在线的组里
        System.out.println(clients);
        Map<Session,Integer> order = clients.get(openid);
        for (Map.Entry<Session, Integer> sessionIntegerEntry : order.entrySet()){
            if( !sessionIntegerEntry.getKey().equals(session)){
                gaolSession = sessionIntegerEntry.getKey();
                to = sessionIntegerEntry.getValue();
            }else{
                from = sessionIntegerEntry.getValue();
            }
        }
        if(gaolSession == null){
            clients.remove(openid);
            roomService.removeRoomId(openid);
        }else{
            System.out.println(gaolSession);
            Map<String,Long> result = record.getRecordCount(from,to);
            if((result.get("infor1") >= 1) && (result.get("infor2") >= 1)){
            }else{
                record.deleteRecord(from,to);
            }
            this.sendClose("close",from,gaolSession);
            clients.remove(openid);
            roomService.removeRoomId(openid);
//        clients.remove(session.getId());
            System.out.println(clients);
        }


    }

    /**
     * 发生错误
     * @param throwable e
     */
    @OnError
    public void onError(Throwable throwable) {
        throwable.printStackTrace();
    }

    /**
     * 收到客户端发来消息
     * @param message  消息对象
     */
    @OnMessage
    public void onMessage(String message,Session session) throws JsonProcessingException {
//        this.sendAll(message);
        Session gaolSession = null;
        Session fromSession = null;
        Integer from = null;
        Integer to = null;
        for(Map<Session, Integer> s : clients.values()){
            for (Map.Entry<Session, Integer> entry : s.entrySet()) {
                Session session1 = entry.getKey();
                if(session1.equals(session)){
                    from = entry.getValue();
                    fromSession = session1;
                }else{
                    gaolSession = entry.getKey();
                    to = entry.getValue();
                    if(from == null){
                        for (Map.Entry<Session, Integer> e : s.entrySet()) {
                            Session session2 = e.getKey();
                            if(session2.equals(session)){

                                from = entry.getValue();
                                fromSession = session2;
                            }
                        }
                    }
                    System.out.println(from);
                }

            }
        }

        if(gaolSession == null){
            this.sendClose("noUser",from,fromSession);
        }else{
            record.saveRecord(from,to,message);
            this.send(message,from,gaolSession);
            this.send(message,from,fromSession);
        }
    }
    /**
     * 点对点发送消息
     */
//    private  HashMap<String,Object> getOtherSession(Session session){
//
//    }
    private void send(String message,Integer uId, Session session) throws JsonProcessingException {
        HashMap<String,Object> context = new HashMap<>();
        context.put("message",message);
        context.put("from",uId);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValueAsString(context);
        session.getAsyncRemote().sendText(objectMapper.writeValueAsString(context));
    }
    private void sendClose(String closeMsg,Integer uId, Session session) throws JsonProcessingException {
        HashMap<String,Object> context = new HashMap<>();
        context.put("closeMsg",closeMsg);
        context.put("from",uId);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValueAsString(context);
        session.getAsyncRemote().sendText(objectMapper.writeValueAsString(context));
    }

    /**
     * 群发消息
     * @param message 消息内容
     */

//    private void sendAll(String message) {
//        System.out.println(clients);
//        System.out.println(message);
//        for (Map.Entry<String, Session> sessionEntry : clients.entrySet()) {
//            sessionEntry.getValue().getAsyncRemote().sendText(message);
//        }
//    }
}
