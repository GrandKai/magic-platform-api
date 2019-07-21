package com.magic.platform.api.business.role.mapper.custom.dao;

import com.magic.platform.api.business.role.mapper.custom.entity.RoleVO;
import com.magic.platform.api.business.role.model.RoleUsersModel;
import com.magic.platform.entity.mapper.build.BaseMapper;
import java.util.List;
import java.util.Map;

/**
 * @author: GrandKai
 * @create: 2018-10-02 09:43
 */
public interface RoleVOMapper extends BaseMapper<RoleVO> {

  List<RoleVO> selectEntityList(Map<String, Object> param);

  int insertRoleUserByBatch(RoleUsersModel model);

  int deleteRoleUserByBatch(RoleUsersModel model);
}
