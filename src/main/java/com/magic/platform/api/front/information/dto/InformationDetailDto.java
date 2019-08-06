package com.magic.platform.api.front.information.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: Administrator
 * @Description:
 * @Date: Created in 2019-08-01 15:15
 * @Modified By:
 */
@Data
@ApiModel("手机端资讯列表模型")
public class InformationDetailDto {
  private String id;
  @ApiModelProperty(value = "标题")
  private String title;
  @ApiModelProperty(value = "发布内容-富文本")
  private String content;

  @ApiModelProperty(value = "发布时间")
  private String releaseTime;
  @ApiModelProperty(value = "发布者")
  private String publisher;
  @ApiModelProperty(value = "点击数量")
  private Integer clickAmount;

  @ApiModelProperty(value = "有效期至")
  private String validityEndTime;

}
