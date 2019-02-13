package com.magic.platform.api.business.authority.mapper.dao;

import com.magic.platform.api.business.authority.mapper.entity.AuthorityVO;
import com.magic.platform.entity.mapper.build.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author: GrandKai
 * @create: 2018-10-01 23:35
 */
public interface AuthorityVOMapper extends BaseMapper<AuthorityVO> {

    /**
     * 查询权限列表
     * @param param
     * @return
     */
    List<AuthorityVO> selectEntityList(Map<String, Object> param);

    /**
     * 根据权限 id 查询所有菜单和操作id
     * @param authorityId
     * @return
     */
    List<String> selectAllGrantedOperationIds(@Param("authorityId") String authorityId);
}
