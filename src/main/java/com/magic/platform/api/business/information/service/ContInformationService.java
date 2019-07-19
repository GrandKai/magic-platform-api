package com.magic.platform.api.business.information.service;

import com.magic.platform.api.business.information.mapper.custom.dao.ContInformationVOMapper;
import com.magic.platform.api.business.information.mapper.custom.entity.ContInformationVO;
import com.magic.platform.api.business.information.model.ContInformationQueryModel;
import com.magic.platform.entity.mapper.build.dao.ContInformationMapper;
import com.magic.platform.entity.mapper.build.entity.ContInformation;
import com.magic.platform.util.UUIDUtils;
import java.util.Date;
import java.util.List;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: Administrator
 * @Description:
 * @Date: Created in 2019-07-19 23:23
 * @Modified By:
 */
@Service
public class ContInformationService {

  @Autowired
  private ContInformationMapper contInformationMapper;

  @Autowired
  private ContInformationVOMapper contInformationVOMapper;

  public ContInformation addEntity(ContInformation param) {
    ContInformation entity = new ContInformation();

    BeanUtils.copyProperties(param, entity);

    String id = UUIDUtils.uuid();

    entity.setId(id);
    entity.setCreateTime(new Date());
    entity.setUpdateTime(new Date());
    entity.setIsDeleted("0");

    contInformationMapper.insert(entity);

    return contInformationMapper.selectByPrimaryKey(id);
  }

  public ContInformation updateEntity(ContInformation param) {

    ContInformation entity = new ContInformation();
    BeanUtils.copyProperties(param, entity);
    entity.setUpdateTime(new Date());

    contInformationMapper.updateByPrimaryKeySelective(entity);

    return contInformationMapper.selectByPrimaryKey(param.getId());
  }

  public void deleteEntity(String id) {

    ContInformation param = contInformationMapper.selectByPrimaryKey(id);
    param.setIsDeleted("1");
    param.setUpdateTime(new Date());

    contInformationMapper.updateByPrimaryKeySelective(param);
  }

  public List<ContInformationVO> selectEntityList(ContInformationQueryModel model) {
    return contInformationVOMapper.selectEntityList(model);
  }
}
