package com.magic.platform.api.business.label.model;

import java.util.List;
import lombok.Data;

/**
 * @Author: Administrator
 * @Description:
 * @Date: Created in 2019-07-26 16:16
 * @Modified By:
 */
@Data
public class ContLabelModel {

  private String groupId;
  private List<String> names;

  private Short sortNumber;
}
