package com.magic.platform.api.websocket.controller;

import com.magic.platform.api.websocket.config.WebSocketServer;
import com.magic.platform.api.websocket.model.WebSocketModel;
import com.magic.platform.core.constant.Constant;
import com.magic.platform.core.model.RequestModel;
import com.magic.platform.core.model.ResponseModel;
import java.io.IOException;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Administrator
 * @Description:
 * @Date: Created in 2019-08-12 20:20
 * @Modified By:
 */
@Slf4j
@RestController
@RequestMapping("websocket")
public class WebSocketController {

  /**
   * 该类用来模拟测试从页面发送消息到页面
   * @param requestModel
   * @return
   */
  @PostMapping("send")
  public ResponseModel send(@Valid @RequestBody RequestModel<WebSocketModel> requestModel) {

    WebSocketModel model = requestModel.getContent();

    WebSocketServer socketServer = WebSocketServer.webSockets.get(model.getToUser());
    try {
      if (socketServer == null) {
        return new ResponseModel(Constant.EXCEPTION_CODE, "未获取到socket链接，无法发送消息！");
      }

      socketServer.sendMessage(model.getMessage());
    } catch (IOException e) {
      log.error("发送消息失败:", e);
      return new ResponseModel(Constant.EXCEPTION_CODE, "发送消息失败！");
    }

    return new ResponseModel("发送消息成功！");
  }
}
