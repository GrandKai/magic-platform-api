package com.magic.platform.api.business.role.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.magic.platform.api.business.role.mapper.custom.dao.RoleVOMapper;
import com.magic.platform.api.business.role.mapper.custom.entity.RoleVO;
import com.magic.platform.api.business.role.model.RoleAuthorityModel;
import com.magic.platform.api.business.role.model.RoleModel;
import com.magic.platform.api.business.role.model.RoleQueryModel;
import com.magic.platform.api.business.role.model.RoleUsersModel;
import com.magic.platform.api.business.user.mapper.custom.dao.UserVOMapper;
import com.magic.platform.core.exception.CustomException;
import com.magic.platform.core.model.RequestModel;
import com.magic.platform.entity.mapper.build.dao.RoleAuthorityMapper;
import com.magic.platform.entity.mapper.build.dao.RoleMapper;
import com.magic.platform.entity.mapper.build.dao.UserRoleMapper;
import com.magic.platform.entity.mapper.build.entity.Role;
import com.magic.platform.entity.mapper.build.entity.RoleAuthority;
import com.magic.platform.entity.mapper.build.entity.UserRole;
import com.magic.platform.util.UUIDUtils;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

/**
 * @author: GrandKai
 * @create: 2018-10-02 09:39
 */
@Service
public class RoleService {

  @Autowired
  private RoleVOMapper roleVOMapper;

  @Autowired
  private RoleMapper roleMapper;

  @Autowired
  private RoleAuthorityMapper roleAuthorityMapper;

  @Autowired
  private UserRoleMapper userRoleMapper;

  @Autowired
  public UserVOMapper userVOMapper;

  public PageInfo getEntityPage(RequestModel<RoleQueryModel> requestModel) {
    RoleQueryModel model = requestModel.getContent();

    int pageNum = requestModel.getPage().getPageNum() - 1;
    int pageSize = requestModel.getPage().getPageSize();

    Map<String, Object> param = new HashMap<>();
    param.put("name", model.getName());
    param.put("platId", model.getPlatId());

    PageHelper.offsetPage(pageNum * pageSize, pageSize, true);

    List<RoleVO> list = roleVOMapper.selectEntityList(param);
    PageInfo pageInfo = new PageInfo<>(list);

    return pageInfo;
  }

  public List<RoleVO> selectEntityList(RoleQueryModel model) {

    Map<String, Object> param = new HashMap<>();
    if (model != null) {
      param.put("name", model.getName());
      param.put("platId", model.getPlatId());
    }

    List<RoleVO> list = roleVOMapper.selectEntityList(param);
    return list;
  }

  public void addEntity(RoleModel param) {

    Role entity = new Role();

    String roleId = UUIDUtils.uuid();
    entity.setName(param.getName());
    entity.setDescription(param.getDescription());
    entity.setSortNumber(param.getSortNumber());
    entity.setPlatId(param.getPlatId());

    entity.setId(roleId);
    entity.setCreateTime(new Date());
    entity.setUpdateTime(new Date());

    roleMapper.insert(entity);

    // 设置角色权限
    RoleAuthority roleAuthority = null;
    List<String> authorities = param.getAuthorities();
    for (String authorityId : authorities) {
      roleAuthority = new RoleAuthority();
      roleAuthority.setRoleId(roleId);
      roleAuthority.setAuthorityId(authorityId);

      roleAuthorityMapper.insert(roleAuthority);
    }
  }

  public void updateEntity(Role param) {

    Role entity = new Role();

    entity.setId(param.getId());
    entity.setName(param.getName());
    entity.setDescription(param.getDescription());
    entity.setSortNumber(param.getSortNumber());
    entity.setUpdateTime(new Date());
    roleMapper.updateByPrimaryKeySelective(entity);
  }

  public void deleteEntity(String id) {
    // 校验删除 - 如果存在用户角色关联则不允许删除
    RoleAuthority param = new RoleAuthority();
    param.setRoleId(id);

    UserRole userRole = new UserRole();
    userRole.setRoleId(id);
    int count = userRoleMapper.selectCount(userRole);
    if (0 < count) {
      throw new CustomException("该角色存在用户关联，无法删除");
    }

    roleAuthorityMapper.delete(param);
    roleMapper.deleteByPrimaryKey(id);
  }

  public void checkExist(String name) {

    Role param = new Role();
    param.setName(name);

    int count = roleMapper.selectCount(param);

    if (0 < count) {
      throw new CustomException("角色【" + name + "】已经存存在，请重新命名");
    }
  }

  public void checkUpdateExist(RoleVO model) {

    Example example = new Example(Role.class);
    Example.Criteria criteria = example.createCriteria();

    criteria.andEqualTo("name", model.getName());
    criteria.andNotEqualTo("id", model.getId());

    int existCount = roleMapper.selectCountByExample(example);
    if (0 < existCount) {
      throw new CustomException("角色【" + model.getName() + "】已经存存在，请重新命名");
    }
  }

  /**
   * 根据角色id获取权限列表
   */
  public List<RoleAuthority> selectRoleAuthorityList(String id) {
    RoleAuthority param = new RoleAuthority();
    param.setRoleId(id);
    return roleAuthorityMapper.select(param);
  }

  /**
   * 添加角色权限关系
   */
  public void addRoleAuthority(RoleAuthorityModel model) {

    // 校验删除 - 如果存在用户角色关联则不允许删除
    RoleAuthority param = new RoleAuthority();
    param.setRoleId(model.getRoleId());

    // TODO: 去除外键后可以删除
//        UserRole userRole = new UserRole();
//        userRole.setRoleId(model.getRoleId());
//        int count = userRoleMapper.selectCount(userRole);
//        if (0 < count) {
//            throw new CustomException("该角色存在用户关联，无法删除");
//        }

    roleAuthorityMapper.delete(param);

    List<String> authorities = model.getAuthorities();

    RoleAuthority entity = null;

    List<RoleAuthority> list = new ArrayList<>();

    for (String authorityId : authorities) {
      entity = new RoleAuthority();
      entity.setRoleId(model.getRoleId());
      entity.setAuthorityId(authorityId);

      list.add(entity);
    }

    if (0 < list.size()) {
      roleAuthorityMapper.insertList(list);
    }
  }

  public void addRoleUsers(RoleUsersModel model) {
    roleVOMapper.insertRoleUserByBatch(model);
  }

  public void deleteRoleUsers(RoleUsersModel model) {
    roleVOMapper.deleteRoleUserByBatch(model);
  }
}
