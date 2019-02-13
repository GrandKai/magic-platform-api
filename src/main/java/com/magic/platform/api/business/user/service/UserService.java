package com.magic.platform.api.business.user.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.magic.platform.api.business.user.dto.UserDto;
import com.magic.platform.api.business.user.mapper.custom.dao.UserVOMapper;
import com.magic.platform.api.business.user.mapper.custom.entity.UserVO;
import com.magic.platform.api.business.user.model.UserQueryModel;
import com.magic.platform.core.exception.CustomException;
import com.magic.platform.core.exception.ExceptionEnum;
import com.magic.platform.core.jwt.Token;
import com.magic.platform.core.model.RequestModel;
import com.magic.platform.core.util.Objects;
import com.magic.platform.encrypt.enumeration.DigestMessageType;
import com.magic.platform.entity.mapper.build.dao.UserMapper;
import com.magic.platform.entity.mapper.build.dao.UserRoleMapper;
import com.magic.platform.entity.mapper.build.entity.User;
import com.magic.platform.entity.mapper.build.entity.UserRole;
import com.magic.platform.util.DigestMessageUtil;
import com.magic.platform.util.UUIDUtils;
import io.jsonwebtoken.Claims;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

/**
 * @author: GrandKai
 * @create: 2018-08-29 9:46 PM
 */
@Slf4j
@Service
public class UserService {

  @Autowired
  private UserMapper userMapper;

  @Autowired
  private UserVOMapper userVOMapper;

  @Autowired
  private UserRoleMapper userRoleMapper;

  /**
   * 添加账户信息
   */
  public void addUser(UserQueryModel model) {

    Date date = new Date();

    User user = new User();
    user.setId(UUIDUtils.uuid());
    user.setName(model.getUserName());
    user.setNickName(model.getNickName());
    try {
      user.setPassword(DigestMessageUtil.getInstance().encodeWithBASE64(model.getPassword(), DigestMessageType.MD5));
    } catch (Exception e) {
      throw ExceptionEnum.ENCRYPTION_PASSWORD_ERROR.getException();
    }
    user.setIsEnabled("1");
    user.setIsDeleted("0");
    user.setCreateTime(date);
    user.setUpdateTime(date);

    userMapper.insert(user);
  }

  /**
   * 修改用户信息
   * @param model
   */
  public void updateUser(UserQueryModel model) {
    User user = new User();
    user.setId(model.getUserId());
    user.setName(model.getUserName());
    user.setUpdateTime(new Date());

    userMapper.updateByPrimaryKeySelective(user);
  }

  /**
   * 根据用户账户名称查看是否存在
   *
   * @param userName 用户账户名
   */
  public void checkExist(String userName) {

    User param = new User();
    param.setName(userName);

    int count = userMapper.selectCount(param);

    if (0 < count) {
      throw new CustomException("该用户账户已经存在");
    }
  }

  /**
   * 检测【修改用户账户】是否存在
   * @param model
   */
  public void checkUpdateExist(UserQueryModel model) {

    Example example = new Example(User.class);
    Example.Criteria criteria = example.createCriteria();

    criteria.andEqualTo("name", model.getUserName());
    criteria.andNotEqualTo("id", model.getUserId());

    int existCount = userMapper.selectCountByExample(example);
    if (0 < existCount) {
      throw new CustomException("该用户账户已经存在");
    }
  }

  /**
   * 停用用户
   */
  public void stopUser(UserQueryModel model) {

    User user = new User();
    user.setId(model.getUserId());
    user.setIsEnabled(model.getIsEnabled());
    user.setUpdateTime(new Date());

    userMapper.updateByPrimaryKeySelective(user);
  }

  /**
   * 删除用户
   */
  public void deleteUser(String id) {

    User user = new User();
    user.setId(id);
    user.setIsDeleted("1");
    user.setIsEnabled("0");
    user.setUpdateTime(new Date());

    userMapper.updateByPrimaryKeySelective(user);
  }

  /**
   * 重置密码
   *
   * @param model
   * @throws Exception
   */
  public void resetUserPassword(UserQueryModel model) {

    User user = new User();
    user.setId(model.getUserId());
    user.setUpdateTime(new Date());

    try {
      user.setPassword(DigestMessageUtil.getInstance().encodeWithBASE64(model.getPassword(), DigestMessageType.MD5));
    } catch (Exception e) {
      throw ExceptionEnum.ENCRYPTION_PASSWORD_ERROR.getException();
    }

    userMapper.updateByPrimaryKeySelective(user);
  }

  public PageInfo getEntityPage(RequestModel<UserQueryModel> requestModel) {

    UserQueryModel model = requestModel.getContent();

    int pageNum = requestModel.getPage().getPageNum() - 1;
    int pageSize = requestModel.getPage().getPageSize();

    Map<String, Object> param = new HashMap<>();
    param.put("name", model.getUserName());
    param.put("isEnabled", model.getIsEnabled());
    param.put("startTime", model.getStartTime());
    param.put("endTime", model.getEndTime());

    PageHelper.offsetPage(pageNum * pageSize, pageSize, true);

    List<UserVO> list = userVOMapper.selectEntityList(param);
    PageInfo pageInfo = new PageInfo(list);

    return pageInfo;
  }

  public UserDto getUserDtoByAccessToken(String accessToken) {

    Claims claims = Token.parseAccessToken2Claims(accessToken);

    String userName = claims.getSubject();

    Objects.requireNonNull(userName, "用户名不能为空");

    User param = new User();
    param.setName(userName);

    User entity = userMapper.selectOne(param);

    if (entity == null) {
      throw ExceptionEnum.USERNAME_NOT_FOUND.getException();
    }

    UserDto dto = new UserDto();

    BeanUtils.copyProperties(entity, dto);
    return dto;

  }

  /**
   * 保存用户的角色信息
   *
   * @param userId 用户id
   * @param roleList 角色信息
   */
  public void addUserRoles(String userId, List<String> roleList) {

    // 删除用户的全部角色信息
    UserRole userRole = new UserRole();
    userRole.setUserId(userId);
    userRoleMapper.delete(userRole);

    // 添加全部角色信息
    if (roleList != null && 0 < roleList.size()) {
      userVOMapper.addUserRole(userId, roleList);
    }
  }


  /**
   * 获取用户的全部角色
   *
   * @param userId 用户id
   */
  public List<UserRole> selectUserRoles(String userId) {

    UserRole userRole = new UserRole();
    userRole.setUserId(userId);

    return userRoleMapper.select(userRole);
  }

}
