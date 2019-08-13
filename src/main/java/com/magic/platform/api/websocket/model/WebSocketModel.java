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
  private String toUserName;
  private String message;
}
