package com.magic.platform.api.business.label.model;

import lombok.Data;

/**
 * @Author: Administrator
 * @Description:
 * @Date: Created in 2019-07-24 20:20
 * @Modified By:
 */
@Data
public class ContLabelQueryModel {
  private String id;
  private String name;
  private String isShow;


  private String groupId;
  private String groupName;
  private String labelGroupIsShow;
  private String labelIsShow;
}
