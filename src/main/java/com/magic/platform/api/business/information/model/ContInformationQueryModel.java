package com.magic.platform.api.business.information.model;

import java.util.Date;
import java.util.List;
import lombok.Data;

/**
 * @Author: Administrator
 * @Description:
 * @Date: Created in 2019-07-19 23:23
 * @Modified By:
 */
@Data
public class ContInformationQueryModel {

  /**
   * 树层级，0表示根节点
   */
  private String level;

  /**
   * 资讯名称
   */
  private String name;

  /**
   * 栏目id（为了查询该栏目下的所有子栏目）
   */
  private String contCatalogId;

  private Date startTime;
  private Date endTime;

  /**
   *
   */
  private List<String> list;
}
