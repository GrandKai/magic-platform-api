package com.magic.platform.api.business.permission.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: zyn
 * @Description:
 * @Date: Created in 2018-09-29 15:15
 * @Modified By:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PermissionModel {

  private String id;
  private String name;
  private String url;
  private String parentId;
  private Short sortNumber;
}
