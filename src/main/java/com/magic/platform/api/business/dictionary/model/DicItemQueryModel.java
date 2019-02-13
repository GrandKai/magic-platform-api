package com.magic.platform.api.business.dictionary.model;

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
@ApiModel("数据项目查询 Model")
public class DicItemQueryModel {

  @ApiModelProperty("数据类型id")
  private String typeId;
  @ApiModelProperty("数据类型编码")
  private String code;
  @ApiModelProperty("数据类型是否显示")
  private String typeIsShow;

  @ApiModelProperty("数据项目名称")
  private String name;
  @ApiModelProperty("数据条目是否显示")
  private String itemIsShow;
}
