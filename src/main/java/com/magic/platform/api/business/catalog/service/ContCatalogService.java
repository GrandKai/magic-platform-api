package com.magic.platform.api.business.catalog.service;

import com.magic.platform.api.business.catalog.mapper.dao.ContCatalogVOMapper;
import com.magic.platform.api.business.catalog.mapper.entity.ContCatalogVO;
import com.magic.platform.api.business.catalog.model.ContCatalogQueryModel;
import com.magic.platform.entity.mapper.build.dao.ContCatalogMapper;
import com.magic.platform.entity.mapper.build.entity.ContCatalog;
import com.magic.platform.util.UUIDUtils;
import java.util.Date;
import java.util.List;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: Administrator
 * @Description:
 * @Date: Created in 2019-07-18 15:15
 * @Modified By:
 */
@Service
public class ContCatalogService {

  @Autowired
  private ContCatalogMapper contCatalogMapper;

  @Autowired
  private ContCatalogVOMapper contCatalogVOMapper;

  public ContCatalog addEntity(ContCatalog param) {
    ContCatalog entity = new ContCatalog();

    BeanUtils.copyProperties(param, entity);

    String id = UUIDUtils.uuid();

    entity.setId(id);
    entity.setCreateTime(new Date());
    entity.setUpdateTime(new Date());
    entity.setIsDeleted("0");

    contCatalogMapper.insert(entity);

    return contCatalogMapper.selectByPrimaryKey(id);
  }

  public ContCatalog updateEntity(ContCatalog param) {

    ContCatalog entity = new ContCatalog();
    BeanUtils.copyProperties(param, entity);
    entity.setUpdateTime(new Date());

    contCatalogMapper.updateByPrimaryKeySelective(entity);

    return contCatalogMapper.selectByPrimaryKey(param.getId());
  }

  public void deleteEntity(String id) {

    ContCatalog param = contCatalogMapper.selectByPrimaryKey(id);
    param.setIsDeleted("1");
    param.setUpdateTime(new Date());

    contCatalogMapper.updateByPrimaryKeySelective(param);
  }

  public List<ContCatalogVO> selectEntityList(ContCatalogQueryModel model) {
    return contCatalogVOMapper.selectEntityList(model);
  }
}
