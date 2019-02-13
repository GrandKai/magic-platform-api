package com.magic.platform.api.business.operation.mapper.custom.entity;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: zyn
 * @Description:
 * @Date: Created in 2018-09-29 09:09
 * @Modified By:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OperationVO {

  private String id;
  private String name;
  private String type;
  private String url;

  private String parentId;
  private Date createTime;
  private Date updateTime;
  private Short sortNumber;

}
