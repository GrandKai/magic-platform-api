package com.magic.platform.api.business.permission.service;

import com.magic.platform.api.business.permission.mapper.custom.dao.PermissionVOMapper;
import com.magic.platform.api.business.operation.mapper.custom.entity.OperationVO;
import com.magic.platform.api.business.permission.model.PermissionModel;
import com.magic.platform.api.business.permission.model.PermissionQueryModel;
import com.magic.platform.entity.mapper.build.dao.PermissionMapper;
import com.magic.platform.entity.mapper.build.entity.Permission;
import com.magic.platform.util.UUIDUtils;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author: zyn
 * @Description:
 * @Date: Created in 2018-09-29 15:15
 * @Modified By:
 */
@Service
public class PermissionService {

  @Autowired
  private PermissionMapper permissionMapper;

  @Autowired
  private PermissionVOMapper permissionVOMapper;

  @Transactional(rollbackFor = Exception.class)
  public OperationVO addEntity(PermissionModel param) {

    Permission entity = new Permission();

    String id = UUIDUtils.uuid();
    entity.setId(id);
    entity.setName(param.getName());
    entity.setOperationId(param.getParentId());

    entity.setUrl(param.getUrl());
    entity.setCreateTime(new Date());
    entity.setUpdateTime(new Date());
    entity.setSortNumber(param.getSortNumber());

    permissionMapper.insert(entity);

    return permissionVOMapper.getOperationVO(id);
  }

  @Transactional(rollbackFor = Exception.class)
  public OperationVO updateEntity(PermissionModel param) {

    Permission entity = new Permission();

    entity.setId(param.getId());
    entity.setName(param.getName());

    entity.setUrl(param.getUrl());
    entity.setUpdateTime(new Date());
    entity.setSortNumber(param.getSortNumber());

    permissionMapper.updateByPrimaryKeySelective(entity);

    return permissionVOMapper.getOperationVO(param.getId());
  }

  @Transactional(rollbackFor = Exception.class)
  public void deleteEntity(String id) {
    permissionMapper.deleteByPrimaryKey(id);
  }

  public List<OperationVO> selectEntityList(PermissionQueryModel model) {
    return permissionVOMapper.selectEntityList(model);

  }

  /**
   * 获取所有授予的操作 url 信息
   * @param userName
   * @return
   */
  public List<String> selectAllGrantedPermissions(String userName) {
    return permissionVOMapper.selectAllGrantedPermissions(userName);
  }
}
