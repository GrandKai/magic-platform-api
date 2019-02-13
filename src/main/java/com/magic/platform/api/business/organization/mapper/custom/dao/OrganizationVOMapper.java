package com.magic.platform.api.business.organization.mapper.custom.dao;

import com.magic.platform.api.business.organization.mapper.custom.entity.OrganizationVO;
import com.magic.platform.api.business.organization.model.OrganizationQueryModel;
import com.magic.platform.entity.mapper.build.BaseMapper;

import java.util.List;

/**
 * @author: GrandKai
 * @create: 2018-10-07 22:58
 */
public interface OrganizationVOMapper extends BaseMapper<OrganizationVO> {
    List<OrganizationVO> selectEntityList(OrganizationQueryModel model);
}
