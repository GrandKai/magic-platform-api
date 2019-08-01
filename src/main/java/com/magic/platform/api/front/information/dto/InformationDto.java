package com.magic.platform.api.front.information.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;
import lombok.Data;

/**
 * @Author: Administrator
 * @Description:
 * @Date: Created in 2019-08-01 15:15
 * @Modified By:
 */
@Data
public class InformationDto {
  private String id;
  @JsonProperty(value = "标题")
  private String title;
  @JsonProperty(value = "发布时间")
  private Date releaseTime;

  @JsonProperty(value = "发布者")
  private String publisher;
  @JsonProperty(value = "点击数量")
  private Integer clickAmount;
  @JsonProperty(value = "封面图片")
  private String coverImage;

}
