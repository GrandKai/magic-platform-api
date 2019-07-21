package com.magic.platform.api.business.user.mapper.custom.dao;

import com.magic.platform.api.business.user.mapper.custom.entity.UserVO;
import com.magic.platform.api.business.user.model.UserQueryModel;
import com.magic.platform.api.business.user.model.UserRolesModel;
import com.magic.platform.entity.mapper.build.BaseMapper;
import java.util.List;

/**
 * @Author: zyn
 * @Description:
 * @Date: Created in 2018-09-20 09:09
 * @Modified By:
 */
public interface UserVOMapper extends BaseMapper<UserVO> {

  int insertUserRolesByBatch(UserRolesModel model);

  List<UserVO> selectEntityList(UserQueryModel model);

  /**
   * TODO: 可以优化，返回信息中不待会角色信息
   * @param model
   * @return
   */
  List<UserVO> selectRoleUsersRightPageSetByRoleId(UserQueryModel model);

  /**
   * TODO: 可以优化，返回信息中不待会角色信息
   * @param model
   * @return
   */
  List<UserVO> selectRoleUsersLeftPageUnset(UserQueryModel model);
}
