package com.magic.platform.api.business.information.mapper.custom.entity;

import java.util.Date;
import lombok.Data;

/**
 * @Author: Administrator
 * @Description: 轻量的资讯信息
 * @Date: Created in 2019-07-20 00:00
 * @Modified By:
 */
@Data
public class ContInformationSimpleVO  {

  private String id;
  private String catalogId;
  private String catalogName;

  private String title;
  private String author;
  private Short topLevel;

  private Date topEndTime;
  private Date releaseTime;
  private String publisher;

  private Integer clickAmount;
  private Date createTime;
  private Date updateTime;
}
