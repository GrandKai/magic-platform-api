package com.magic.platform.api.websocket.model;

import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 * @Author: Administrator
 * @Description:
 * @Date: Created in 2019-08-12 20:20
 * @Modified By:
 */
@Data
public class WebSocketModel {
  @NotNull(message = "要发送的用户不能为空！")
  private String toUser;
  @NotNull(message = "要发送的消息不能为空！")
  private String message;
}
