package com.magic.platform.api.business.menu.mapper.custom.dao;

import com.magic.platform.api.business.menu.mapper.custom.entity.MenuVO;
import com.magic.platform.api.business.menu.model.GrantedMenuQueryModel;
import com.magic.platform.api.business.menu.model.MenuQueryModel;
import com.magic.platform.entity.mapper.build.BaseMapper;
import java.util.List;

/**
 * @Author: zyn
 * @Description:
 * @Date: Created in 2018-09-25 20:20
 * @Modified By:
 */
public interface MenuVOMapper extends BaseMapper<MenuVO> {

  /**
   * 根据 用户id，平台id 查询可操作菜单
   * @param model
   * @return
   */
  List<MenuVO> selectGrantedMuneListByUserIdAndPlatId(GrantedMenuQueryModel model);

  /**
   * 查询菜单列表
   * @param model
   * @return
   */
  List<MenuVO> selectEntityList(MenuQueryModel model);

}
