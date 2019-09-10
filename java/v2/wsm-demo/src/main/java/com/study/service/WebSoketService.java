package com.study.service;

import org.springframework.stereotype.Service;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
@ServerEndpoint("/webSoket")
public class WebSoketService {

    /*WebSoket 客户端会话 通过session 向客户端发送数据*/
    private Session session;

    /*线程安全set 存放每个客户端处理消息的对象*/
    private static CopyOnWriteArrayList<WebSoketService> webSokets = new CopyOnWriteArrayList();

    /*websocket 连接建立成功后进行调用*/
    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        webSokets.add(this);
        System.err.println("websocket 有新的链接" + webSokets.size());
    }

    /*websocket 链接关闭调用的方法*/
    @OnClose
    public void onClose() {
        webSokets.remove(this);
        System.err.println("关闭了链接：" + this);
    }

    /*收到客户端消息后调用的方法*/
    @OnMessage
    public void onMessage(String message) throws IOException {
        for (WebSoketService webSoket : webSokets) {
            webSoket.session.getBasicRemote().sendText("发送消息：" + message);
        }
        System.err.println("发送了消息：" + this);
        System.err.println("发送了消息：" + message);
    }

    /*websocket 发生错误时进行调用*/
    @OnError
    public void onError(Session session, Throwable error) {
        error.printStackTrace();
        System.err.println("发生了错误：" + this);
    }

    public void sendMessage(String message) throws IOException {
        for (WebSoketService webSoket : webSokets) {
            webSoket.session.getBasicRemote().sendText(message);
        }
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

}
