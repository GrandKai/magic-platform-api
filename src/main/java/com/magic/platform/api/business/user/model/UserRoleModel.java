package com.magic.platform.api.business.user.model;

import java.util.List;
import lombok.Data;

/**
 * @Author: zyn
 * @Description:
 * @Date: Created in 2018-11-10 14:14
 * @Modified By:
 */
@Data
public class UserRoleModel {

  /**
   * 用户id
   */
  private String userId;

  /**
   * 用户的角色列表
   */
  private List<String> roleList;

}
