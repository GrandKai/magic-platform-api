package com.magic.platform.api.business.user.mapper.custom.dao;

import com.magic.platform.api.business.user.mapper.custom.entity.UserVO;
import com.magic.platform.entity.mapper.build.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Author: zyn
 * @Description:
 * @Date: Created in 2018-09-20 09:09
 * @Modified By:
 */
public interface UserVOMapper extends BaseMapper<UserVO> {

  void addUserRole(@Param("userId") String userId, @Param("list") List<String> roleList);

  List<UserVO> selectEntityList(Map<String, Object> param);
}
