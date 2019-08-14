package com.magic.platform.api.framework.websocket.service;

import com.magic.platform.api.framework.websocket.config.WebSocketServer;
import com.magic.platform.api.framework.websocket.model.WebSocketModel;
import com.magic.platform.core.exception.CustomException;
import com.magic.platform.core.util.Objects;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @Author: Administrator
 * @Description:
 * @Date: Created in 2019-08-13 13:13
 * @Modified By:
 */
@Slf4j
@Service
public class WebSocketService {


  public void sendMessage(WebSocketModel model) {

    Objects.requireNonNull(model.getMessage(), "要发送的消息不能为空！");
    Objects.requireNonNull(model.getToUserName(), "要发送的用户不能为空！");

    WebSocketServer socketServer = WebSocketServer.webSockets.get(model.getToUserName());

    try {
      if (socketServer == null) {
        throw new CustomException("未获取到socket链接，无法发送消息！");
      }
      socketServer.sendMessage(model.getMessage());
    } catch (IOException e) {
      log.error("发送消息失败:", e);
      throw new CustomException("发送消息失败！");
    }
  }
}
