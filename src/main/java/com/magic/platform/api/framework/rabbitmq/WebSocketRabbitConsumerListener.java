package com.magic.platform.api.framework.rabbitmq;

import com.alibaba.fastjson.JSON;
import com.magic.platform.api.framework.websocket.model.WebSocketModel;
import com.magic.platform.api.framework.websocket.service.WebSocketService;
import com.magic.platform.framework.config.rabbitmq.RabbitConfiguration;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

/**
 * @Author: Administrator
 * @Description:
 * @Date: Created in 2019-08-14 15:15
 * @Modified By:
 */
@Slf4j
@EnableRabbit
@Component
@RabbitListener(containerFactory = RabbitConfiguration.RABBIT_LISTENER_CONTAINER_FACTORY, queues = "${rabbit.queueName}")
public class WebSocketRabbitConsumerListener {

  @Autowired
  private WebSocketService webSocketService;

  @RabbitHandler
  public void onMessage(@Payload String message, @Header(AmqpHeaders.DELIVERY_TAG) Long deliveryTag, Channel channel) throws Exception {

    log.info("消费者接收到的信息1：{}", message);

    WebSocketModel model = JSON.parseObject(message, WebSocketModel.class);
    try {
      webSocketService.sendMessage(model);
    } catch (Exception e) {
      log.error("MQ收到消息，向客户端发送消息异常：", e);
      channel.basicReject(deliveryTag, true);
    }
    channel.basicAck(deliveryTag, false);
  }
}
