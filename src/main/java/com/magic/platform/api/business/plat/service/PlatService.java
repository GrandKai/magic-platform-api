package com.magic.platform.api.business.plat.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.magic.platform.api.business.plat.mapper.custom.dao.PlatVOMapper;
import com.magic.platform.api.business.plat.mapper.custom.entity.PlatRoleVO;
import com.magic.platform.api.business.plat.mapper.custom.entity.PlatVO;
import com.magic.platform.api.business.plat.model.PlatQueryModel;
import com.magic.platform.api.business.user.dto.UserDto;
import com.magic.platform.api.business.user.service.UserService;
import com.magic.platform.api.exception.BusinessExceptionEnum;
import com.magic.platform.core.model.RequestModel;
import com.magic.platform.entity.mapper.build.dao.PlatMapper;
import com.magic.platform.entity.mapper.build.entity.Plat;
import com.magic.platform.util.UUIDUtils;
import java.util.Date;
import java.util.List;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Service
public class PlatService {

  @Autowired
  private PlatVOMapper platVOMapper;

  @Autowired
  private PlatMapper platMapper;

  @Autowired
  private UserService userService;

  /***
   * 获取全部系统的信息（分页）
   * @return
   */
  public PageInfo selectEntityPage(RequestModel<Void> requestModel) {

    int pageNum = requestModel.getPage().getPageNum() - 1;
    int pageSize = requestModel.getPage().getPageSize();

    PageHelper.offsetPage(pageNum * pageSize, pageSize, true);

    List<PlatVO> list = platVOMapper.selectEntityList();
    PageInfo page = new PageInfo(list);
    return page;
  }

  /**
   * 获取所有平台信息
   * @return
   */
  public List<PlatVO> selectEntityList() {
    List<PlatVO> list = platVOMapper.selectEntityList();
    return list;
  }

  /**
   * 系统启停
   */
  @Transactional(rollbackFor = Exception.class)
  public void stopSystem(PlatQueryModel model) {
    Plat param = new Plat();
    param.setId(model.getId());
    param.setIsEnabled(model.getIsEnabled());
    param.setUpdateTime(new Date());

    platMapper.updateByPrimaryKeySelective(param);
  }

  /**
   * 删除系统
   */
  @Transactional(rollbackFor = Exception.class)
  public void deleteEntity(String id) {

    Plat param = platMapper.selectByPrimaryKey(id);
    param.setIsDeleted("1");
    param.setUpdateTime(new Date());

    platMapper.updateByPrimaryKeySelective(param);

  }

  /**
   * 根据系统名称检测系统是否已经存在
   * @param name
   */
  public void checkExist(String name) {
    Plat param = new Plat();
    param.setName(name);

    // 存在性只判断未删除的系统
    Plat entity = platMapper.selectOne(param);
    if (entity != null) {
      throw BusinessExceptionEnum.SYSTEM_ALREADY_EXIST.getException();
    }
  }

  /**
   * 根据【1.系统id,系统名称】检测系统是否已经存在
   * @param model
   */
  public void checkUpdateExist(PlatVO model) {

    Example example = new Example(Plat.class);
    Criteria criteria = example.createCriteria();

    criteria.andEqualTo("name", model.getName());
    criteria.andNotEqualTo("id", model.getId());

    int existCount = platMapper.selectCountByExample(example);
    if (0 < existCount) {
      throw BusinessExceptionEnum.SYSTEM_ALREADY_EXIST.getException();
    }
  }

  /**
   * 新增系统
   * @param param
   */
  @Transactional(rollbackFor = Exception.class)
  public void addEntity(Plat param) {
    Plat entity = new Plat();

    BeanUtils.copyProperties(param, entity);

    entity.setId(UUIDUtils.uuid());
    entity.setCreateTime(new Date());
    entity.setUpdateTime(new Date());
    entity.setIsDeleted("0");
    entity.setIsEnabled("0");

    platMapper.insert(entity);
  }

  /**
   * 修改系统
   * @param param
   */
  @Transactional(rollbackFor = Exception.class)
  public void updateEntity(Plat param) {

    Plat entity = new Plat();
    BeanUtils.copyProperties(param, entity);
    entity.setUpdateTime(new Date());

    platMapper.updateByPrimaryKeySelective(entity);

  }

  /**
   * 根据 accessToken 获取授权平台列表
   * @param accessToken
   * @return
   */
  public List<PlatVO> selectGrantedPlatListByAccessToken(String accessToken) {

    UserDto dto = userService.getUserDtoByAccessToken(accessToken);

    return platVOMapper.selectGrantedPlatListByUserId(dto.getId());
  }

  /**
   * 查询所有平台所有角色
   */
  public List<PlatRoleVO> selectAllPlatsAndRoles() {
    return this.platVOMapper.selectAllPlatsAndRoles();
  }
}
