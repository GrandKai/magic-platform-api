package com.magic.platform.api.business.catalog.mapper.dao;

import com.magic.platform.api.business.catalog.mapper.entity.ContCatalogVO;
import com.magic.platform.api.business.catalog.model.ContCatalogQueryModel;
import com.magic.platform.entity.mapper.build.BaseMapper;
import java.util.List;

/**
 * @Author: Administrator
 * @Description:
 * @Date: Created in 2019-07-18 15:15
 * @Modified By:
 */
public interface ContCatalogVOMapper extends BaseMapper<ContCatalogVO> {

  List<ContCatalogVO> selectEntityList(ContCatalogQueryModel model);
}
