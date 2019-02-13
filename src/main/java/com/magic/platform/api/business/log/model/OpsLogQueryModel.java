package com.magic.platform.api.business.log.model;

import java.util.Date;
import lombok.Data;

/**
 * @Author: zyn
 * @Description:
 * @Date: Created in 2018-10-16 10:10
 * @Modified By:
 */
@Data
public class OpsLogQueryModel {

  /**
   * 请求ip
   */
  private String ip;

  /**
   * 平台id
   */
  private String platId;

  /**
   * 操作账号
   */
  private String userName;

  /**
   * 操作人
   */
  private String realName;

  /**
   * 开始时间
   */
  private Date beginTime;

  /**
   * 结束时间
   */
  private Date endTime;

  /**
   * 操作类型
   */
  private String opsType;
}
