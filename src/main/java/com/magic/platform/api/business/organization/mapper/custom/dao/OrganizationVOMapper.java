package com.magic.platform.api.business.organization.mapper.custom.dao;

import com.magic.platform.api.business.organization.mapper.custom.entity.OrganizationVO;
import com.magic.platform.api.business.organization.model.OrganizationQueryModel;
import com.magic.platform.entity.mapper.build.BaseMapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * @author: GrandKai
 * @create: 2018-10-07 22:58
 */
public interface OrganizationVOMapper extends BaseMapper<OrganizationVO> {
    List<OrganizationVO> selectEntityList(OrganizationQueryModel model);

    /**
     * 根据父节点查询所有子节点信息
     * @param parentId  父节点id
     * @param containParent 是否包含父节点本身（1：包含，0：不包含）
     * @return
     */
    String selectOrganizationChildren(@Param("parentId") String parentId, @Param("containParent") String containParent);
    String selectContCatalogChildren(@Param("parentId") String parentId, @Param("containParent") String containParent);
}

