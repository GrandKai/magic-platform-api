package com.magic.platform.api.front.catalog.dto;

import lombok.Data;

/**
 * @Author: Administrator
 * @Description:
 * @Date: Created in 2019-07-31 22:22
 * @Modified By:
 */
@Data
public class CatalogDto {

  private String id;
  private String name;
  private String image;

  private String parentId;
  private String parentName;
}
