package com.magic.platform.api.websocket;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @Author: Administrator
 * @Description:
 * @Date: Created in 2019-08-09 17:17
 * @Modified By:
 */
@Slf4j
@Component
@ServerEndpoint("websocket/{userName}")
public class WebSocketServer {

  //新：使用map对象，便于根据userId来获取对应的WebSocket
  private static ConcurrentHashMap<String,WebSocketServer> webSockets = new ConcurrentHashMap<>();


  // 与某个客户端的连接会话，需要通过它来给客户端发送数据
  private Session session;

  // 接收sid
  private String userName = "";

  /**
   * 连接建立成功调用的方法
   */
  @OnOpen
  public void onOpen(Session session, @PathParam("userName") String userName) {
    this.session = session;
    this.userName = userName;
    webSockets.put(userName, this);
    log.info("有新窗口开始监听:" + userName + ",当前在线人数为" + getOnlineCount());
    try {
      this.sendMessage("连接成功");
    } catch (IOException e) {
      log.error("websocket IO异常");
    }
  }

  /**
   * 连接关闭调用的方法
   */
  @OnClose
  public void onClose() {
    webSockets.remove(this.userName);
    log.info("有一连接关闭！当前在线人数为" + getOnlineCount());
  }

  /**
   * 收到客户端消息后调用的方法
   *
   * @param message 客户端发送过来的消息
   */
  @OnMessage
  public void onMessage(String message, Session session) {
    log.info("收到来自窗口" + userName + "的信息:" + message);


  }

  /**
   *
   */
  @OnError
  public void onError(Session session, Throwable error) {
    log.error("发生错误");
    error.printStackTrace();
  }

  /**
   * 实现服务器主动推送
   */
  public void sendMessage(String message) throws IOException {
    this.session.getBasicRemote().sendText(message);
  }


  /**
   * 群发自定义消息
   */
  public static void sendMessageToAllClient(String message) throws IOException {

    Iterator<Entry<String, WebSocketServer>> entries =  webSockets.entrySet().iterator();
    while (entries.hasNext()) {
      Entry<String, WebSocketServer> entry = entries.next();
      String userName = entry.getKey();
      WebSocketServer item = entry.getValue();

      log.info("推送消息到窗口" + userName + "，推送内容:" + message);
      item.sendMessage(message);

    }

  }

  private long getOnlineCount() {
    return webSockets.size();
  }

}
