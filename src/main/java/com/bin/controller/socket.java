package com.bin.controller;


import com.alibaba.fastjson.JSONObject;
import com.bin.model.dto.socket.SocketDto;
import org.springframework.stereotype.Component;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint("/connect")
@Component
public class socket {

    static Map<Integer,ArrayList<SocketDto>> board=new HashMap<>();
    static Map<Integer, ArrayList<SocketDto>> map=new HashMap<>();

    /**
     * 存放所有在线的客户端
     */
    private static Map<String, Session> clients = new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(Session session) throws IOException {
        //将新用户存入在线的组
        clients.put(session.getId(), session);
        sendBoard(session);
        sendNumber();
    }

    public void sendNumber() throws IOException {
        SocketDto dto = new SocketDto(1,"getNumber",1,0,clients.size()+"");
        for (Map.Entry<String, Session> sessionEntry : clients.entrySet()) {
            sessionEntry.getValue().getBasicRemote().sendText(JSONObject.toJSONString(dto));
        }
    }
    public void sendBoard(Session session) throws IOException {
        if(board.size()==0)
            return ;
        for(Map.Entry<Integer, ArrayList<SocketDto>> entry : board.entrySet()){
            List<SocketDto> lists = entry.getValue();
            for(SocketDto dto: lists){
                session.getBasicRemote().sendText(JSONObject.toJSONString(dto));
            }
        }
    }
    /**
     * 客户端关闭
     * @param session session
     */
    @OnClose
    public void onClose(Session session) {
        //将掉线的用户移除在线的组里
        clients.remove(session.getId());
        System.out.println("close");
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
    public void onMessage(String message, Session session) throws IOException {
        SocketDto dto = JSONObject.parseObject(message, SocketDto.class);
        if("clearBoard".equals(dto.getType())){
            board.clear();
            sendAll(message,session);
            return ;
        }
        if(map.containsKey(dto.getId())){
            ArrayList<SocketDto> list = map.get(dto.getId());
            list.add(dto);
            map.put(dto.getId(),list);
        }
        else{
            ArrayList<SocketDto> list = new ArrayList<>();
            list.add(dto);
            map.put(dto.getId(),list);
        }
        if(dto.getChunkIndex()+1 == dto.getTotalChunks() ){
            ArrayList<SocketDto> lists = map.get(dto.getId());
            if(lists.size() == dto.getTotalChunks()){
                if("add".equals(dto.getType())){
                    if(board.get(dto.getId())==null){
                        board.put(dto.getId(),lists);
                    }
                }else if("del".equals(dto.getType())){
                    if(board.get(dto.getId())!=null){
                        board.remove(dto.getId());
                    }
                }
            }
        }
        sendAll(message,session);
    }

    /**
     * 群发消息
     * @param message 消息内容
     */
    private void sendAll(String message, Session session) throws IOException {
        for (Map.Entry<String, Session> sessionEntry : clients.entrySet()) {
            if(session.getId()!=sessionEntry.getKey()){
                sessionEntry.getValue().getBasicRemote().sendText(message);
            }
        }
    }
}
