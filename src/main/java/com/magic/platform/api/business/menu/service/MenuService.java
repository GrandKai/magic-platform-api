package com.magic.platform.api.business.menu.service;

import com.magic.platform.api.business.menu.mapper.custom.dao.MenuVOMapper;
import com.magic.platform.api.business.menu.mapper.custom.entity.MenuVO;
import com.magic.platform.api.business.menu.model.MenuQueryModel;
import com.magic.platform.api.business.user.service.UserService;
import com.magic.platform.entity.mapper.build.dao.MenuMapper;
import com.magic.platform.entity.mapper.build.entity.Menu;
import com.magic.platform.core.util.Objects;
import com.magic.platform.util.UUIDUtils;
import java.util.Date;
import java.util.List;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author: zyn
 * @Description:
 * @Date: Created in 2018-09-22 18:18
 * @Modified By:
 */
@Service
public class MenuService {

  @Autowired
  private MenuMapper menuMapper;

  @Autowired
  private MenuVOMapper menuVOMapper;

  @Autowired
  private UserService userService;

  @Transactional(rollbackFor = Exception.class)
  public Menu addEntity(Menu param) {
    Menu entity = new Menu();

    BeanUtils.copyProperties(param, entity);

    String id = UUIDUtils.uuid();

    entity.setId(id);
    entity.setCreateTime(new Date());
    entity.setUpdateTime(new Date());
    entity.setIsDeleted("0");

    menuMapper.insert(entity);

    return menuMapper.selectByPrimaryKey(id);
  }

  @Transactional(rollbackFor = Exception.class)
  public Menu updateEntity(Menu param) {

    Menu entity = new Menu();
    BeanUtils.copyProperties(param, entity);
    entity.setUpdateTime(new Date());

    menuMapper.updateByPrimaryKeySelective(entity);

    return menuMapper.selectByPrimaryKey(param.getId());
  }

  public List<MenuVO> selectEntityList(MenuQueryModel model) {

    Objects.requireNonNull(model.getPlatId(),"系统id不能为空");
    return menuVOMapper.selectEntityList(model);
  }

  /**
   * 删除菜单
   */
  @Transactional(rollbackFor = Exception.class)
  public void deleteEntity(String id) {

    Menu param = menuMapper.selectByPrimaryKey(id);
    param.setIsDeleted("1");
    param.setUpdateTime(new Date());

    menuMapper.updateByPrimaryKeySelective(param);
  }
}
