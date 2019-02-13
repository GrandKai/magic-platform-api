package com.magic.platform.api.business.dictionary.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: zyn
 * @Description:
 * @Date: Created in 2018-09-30 09:09
 * @Modified By:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DicTypeQueryModel {

  private String id;
  private String isShow;

  private String name;
}
