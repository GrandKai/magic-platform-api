package com.magic.platform.api.front.catalog.service;

import com.magic.platform.api.business.catalog.mapper.dao.ContCatalogVOMapper;
import com.magic.platform.api.front.catalog.dto.CatalogDto;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: Administrator
 * @Description:
 * @Date: Created in 2019-07-31 22:22
 * @Modified By:
 */
@Service
public class CatalogService {

  @Autowired
  private ContCatalogVOMapper contCatalogVOMapper;

  /**
   * 手机-查询栏目列表接口
   * @return
   */
  public List<CatalogDto> selectCatalogList() {
    return contCatalogVOMapper.selectCatalogList();
  }
}
