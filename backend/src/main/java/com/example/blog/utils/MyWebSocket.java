package com.example.blog.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint(value = "/websocket/{userid}")
@Component
public class MyWebSocket {
    private static final Logger log = LoggerFactory.getLogger(MyWebSocket.class);
    public static ConcurrentHashMap<String,MyWebSocket> webSocketMap = new ConcurrentHashMap<>();
    private Session session;
    private String userid;

    @OnOpen
    public void onOpen(Session session, @PathParam("userid")String userid){
        this.session = session;
        this.userid = userid;
        webSocketMap.put(userid,this);
        log.info("New WebSocket Connection--userid: {}; Total Online Count: {}",userid,webSocketMap.size());
    }

    public void sendMessage(String msg){
        try{
            this.session.getBasicRemote().sendText(msg+"");
        }catch (Exception e){
            log.info("error: {}",e.getMessage());
        }
    }

    public void sendNotification(String userid,int msg){
        if(webSocketMap.containsKey(userid)){
            webSocketMap.get(userid).sendMessage(msg+"");
        }else{
            log.error("user {} is not online",userid);
        }
    }

    @OnMessage
    public void onMessage(String message,Session session){
        if(message.equals("test")){
            this.sendMessage("success");
        }
    }

    @OnClose
    public void onClose(@PathParam("userid")String userid){
        webSocketMap.remove(userid);
        log.info("WebSocket Disconnected, userid: {}; Total Online Count: {}",userid,webSocketMap.size());
    }

    @OnError
    public void onError(Session session, Throwable error){
        log.error(error.getMessage());
    }

}
