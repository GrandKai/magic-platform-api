package com.magic.platform.api.business.plat.mapper.custom.dao;

import com.magic.platform.api.business.plat.mapper.custom.entity.PlatRoleVO;
import com.magic.platform.api.business.plat.mapper.custom.entity.PlatVO;
import com.magic.platform.entity.mapper.build.BaseMapper;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * @Author: zyn
 * @Description:
 * @Date: Created in 2018-09-20 18:18
 * @Modified By:
 */
public interface PlatVOMapper extends BaseMapper<PlatVO> {

  /**
   * 查询所有平台列表
   * @return
   */
  List<PlatVO> selectEntityList();

  /**
   * 根据用户id获取授权平台列表
   * @param userId
   * @return
   */
  List<PlatVO> selectGrantedPlatListByUserId(@Param("userId") String userId);



  /**
   * 查询所有平台所有角色
   * @return
   */
  List<PlatRoleVO> selectAllPlatsAndRoles();

}
