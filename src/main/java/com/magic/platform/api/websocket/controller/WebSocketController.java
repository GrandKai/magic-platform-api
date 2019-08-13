package com.magic.platform.api.websocket.controller;

import com.magic.platform.api.websocket.model.WebSocketModel;
import com.magic.platform.api.websocket.service.WebSocketService;
import com.magic.platform.core.model.RequestModel;
import com.magic.platform.core.model.ResponseModel;
import com.magic.platform.core.util.Objects;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

  @Autowired
  private WebSocketService webSocketService;

  /**
   * 该类用来模拟测试从页面发送消息到页面
   * @param requestModel
   * @return
   */
  @PostMapping("send")
  public ResponseModel send(@Valid @RequestBody RequestModel<WebSocketModel> requestModel) {

    WebSocketModel model = requestModel.getContent();
    webSocketService.sendMessage(model);

    return new ResponseModel("发送消息成功！");
  }
}
