package com.magic.platform.api.front.information.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import lombok.Data;

/**
 * @Author: Administrator
 * @Description:
 * @Date: Created in 2019-08-01 15:15
 * @Modified By:
 */
@Data
@ApiModel("手机端资讯列表模型")
public class InformationDto {
  private String id;
  @ApiModelProperty(value = "标题")
  private String title;
  @ApiModelProperty(value = "发布时间")
  private Date releaseTime;

  @ApiModelProperty(value = "发布者")
  private String publisher;
  @ApiModelProperty(value = "点击数量")
  private Integer clickAmount;
  @ApiModelProperty(value = "封面图片")
  private String coverImage;

}
