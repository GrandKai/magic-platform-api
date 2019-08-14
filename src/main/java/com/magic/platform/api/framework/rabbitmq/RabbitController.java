package com.magic.platform.api.framework.rabbitmq;

import com.alibaba.fastjson.JSON;
import com.magic.platform.api.framework.websocket.model.WebSocketModel;
import com.magic.platform.core.model.ResponseModel;
import com.magic.platform.framework.config.rabbitmq.RabbitProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Administrator
 * @Description:
 * @Date: Created in 2019-08-14 17:17
 * @Modified By:
 */
@RestController
@RequestMapping("rabbit")
public class RabbitController {

  @Autowired
  private RabbitProperties rabbitProperties;
  @Autowired
  private RabbitTemplate rabbitTemplate;

  @GetMapping("send")
  public ResponseModel send(String message, String toUserName) {

    WebSocketModel model = new WebSocketModel();
    model.setMessage(message);
    model.setToUserName(toUserName);
    String content = JSON.toJSONString(model);

    rabbitTemplate.convertAndSend(rabbitProperties.getExchangeName(), rabbitProperties.getRoutingKey(), content);

    return new ResponseModel("发送成功");
  }
}
