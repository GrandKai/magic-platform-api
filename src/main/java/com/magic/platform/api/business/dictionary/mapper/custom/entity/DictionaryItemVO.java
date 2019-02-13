package com.magic.platform.api.business.dictionary.mapper.custom.entity;

import com.magic.platform.entity.mapper.build.entity.DictionaryItem;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel(value = "数据字典条目VO")
public class DictionaryItemVO extends DictionaryItem {

  @ApiModelProperty(name = "数据类型名称")
  private String dicName;
  @ApiModelProperty(name = "数据类型编码")
  private String dicCode;

}
