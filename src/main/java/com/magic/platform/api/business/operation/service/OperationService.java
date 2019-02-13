package com.magic.platform.api.business.operation.service;

import com.magic.platform.api.business.operation.mapper.custom.dao.OperationVOMapper;
import com.magic.platform.api.business.operation.mapper.custom.entity.GrantedOperation;
import com.magic.platform.api.business.operation.mapper.custom.entity.OperationVO;
import com.magic.platform.api.business.operation.model.GrantedOperationQueryModel;
import com.magic.platform.api.business.operation.model.OperationModel;
import com.magic.platform.api.business.operation.model.OperationQueryModel;
import com.magic.platform.entity.mapper.build.dao.OperationMapper;
import com.magic.platform.entity.mapper.build.entity.Operation;
import com.magic.platform.util.UUIDUtils;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author: zyn
 * @Description:
 * @Date: Created in 2018-09-29 09:09
 * @Modified By:
 */
@Service
public class OperationService {

  @Autowired
  private OperationVOMapper operationVOMapper;

  @Autowired
  private OperationMapper operationMapper;

  public List<OperationVO> selectEntityList(OperationQueryModel model) {

    return operationVOMapper.selectEntityList(model);
  }

  @Transactional(rollbackFor = Exception.class)
  public OperationVO addEntity(OperationModel param) {
    Operation entity = new Operation();

    String id = UUIDUtils.uuid();
    entity.setId(id);
    entity.setCreateTime(new Date());
    entity.setUpdateTime(new Date());

    entity.setName(param.getName());
    entity.setMenuId(param.getParentId());
    entity.setType(param.getCode());
    entity.setSortNumber(param.getSortNumber());

    operationMapper.insert(entity);

    return operationVOMapper.getOperationVO(id);
  }

  @Transactional(rollbackFor = Exception.class)
  public OperationVO updateEntity(OperationModel param) {
    Operation entity = new Operation();

    entity.setId(param.getId());
    entity.setUpdateTime(new Date());
    entity.setName(param.getName());
    entity.setType(param.getCode());
    entity.setSortNumber(param.getSortNumber());

    operationMapper.updateByPrimaryKeySelective(entity);

    return operationVOMapper.getOperationVO(param.getId());

  }

  @Transactional(rollbackFor = Exception.class)
  public void deleteEntity(String id) {

    // TODO: 检验如果包含子操作则不可以删除
    operationMapper.deleteByPrimaryKey(id);
  }

  /**
   * 根据菜单查询操作权限（按钮级别）
   * @param model
   * @return
   */
  public List<GrantedOperation> selectGrantedOperationList(GrantedOperationQueryModel model) {
    return operationVOMapper.selectGrantedOperationList(model);
  }
}
