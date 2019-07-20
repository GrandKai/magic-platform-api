package com.magic.platform.api.business.user.mapper.custom.entity;

import java.util.Date;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @Author: zyn
 * @Description:
 * @Date: Created in 2018-09-20 10:10
 * @Modified By:
 */
@Setter
@Getter
@NoArgsConstructor
public class UserVO {

  private String userId;
  private String userName;
  private String nickName;
  private String password;

  private String isEnabled;
  private String isDeleted;

  private Date createTime;
  private Date updateTime;

  private String organizationId;
  private String organizationName;

  private String staffId;
  private String staffName;

  /**
   * 用户所有的角色
   */
  private List<String> roles;
}
