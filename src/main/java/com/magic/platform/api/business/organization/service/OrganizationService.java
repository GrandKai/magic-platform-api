package com.magic.platform.api.business.organization.service;

import com.magic.platform.api.business.organization.mapper.custom.dao.OrganizationVOMapper;
import com.magic.platform.api.business.organization.mapper.custom.entity.OrganizationVO;
import com.magic.platform.api.business.organization.model.OrganizationQueryModel;
import com.magic.platform.core.exception.CustomException;
import com.magic.platform.entity.mapper.build.dao.OrganizationMapper;
import com.magic.platform.entity.mapper.build.entity.Organization;
import com.magic.platform.util.UUIDUtils;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author: GrandKai
 * @create: 2018-10-07 22:52
 */
@Service
public class OrganizationService {

  @Autowired
  private OrganizationMapper organizationMapper;
  @Autowired
  private OrganizationVOMapper organizationVOMapper;


  @Transactional(rollbackFor = Exception.class)
  public Organization addEntity(Organization param) {

    Organization entity = new Organization();

    BeanUtils.copyProperties(param, entity);

    String id = UUIDUtils.uuid();

    entity.setId(id);
    entity.setCreateTime(new Date());
    entity.setUpdateTime(new Date());
    entity.setIsDeleted("0");

    organizationMapper.insert(entity);

    return organizationMapper.selectByPrimaryKey(id);
  }

  @Transactional(rollbackFor = Exception.class)
  public Organization updateEntity(Organization param) {

    Organization entity = new Organization();
    BeanUtils.copyProperties(param, entity);
    entity.setUpdateTime(new Date());

    organizationMapper.updateByPrimaryKeySelective(entity);

    return organizationMapper.selectByPrimaryKey(param.getId());
  }

  @Transactional(rollbackFor = Exception.class)
  public void deleteEntity(String id) {

    Organization param = organizationMapper.selectByPrimaryKey(id);
    param.setIsDeleted("1");
    param.setUpdateTime(new Date());

    organizationMapper.updateByPrimaryKeySelective(param);
  }

  public List<OrganizationVO> selectEntityList(OrganizationQueryModel model) {

//        Objects.requireNonNull(model.getPlatId(),"系统id不能为空");
    return organizationVOMapper.selectEntityList(model);

  }

  public void checkEntityStatus(String id) {

    Organization param = new Organization();
    param.setParentId(id);
    param.setIsDeleted("0");

    int cout = organizationMapper.selectCount(param);
    if (0 < cout) {
      throw new CustomException("组织机构下有子组织，无法删除");
    }
  }

  private List<String> selectOrganizationChildren(String parentId, String containParent) {
    // 查询该组织机构下的所有员工信息
    if (!StringUtils.isEmpty(parentId)) {
      String ids = organizationVOMapper.selectOrganizationChildren(parentId, "1");
      if (!StringUtils.isEmpty(ids)) {
        return Arrays.asList(ids.split(","));
      }
    }
    return Collections.emptyList();
  }

  private List<String> selectContCatalogChildren(String parentId, String containParent) {
    // 查询该组织机构下的所有员工信息
    if (!StringUtils.isEmpty(parentId)) {
      String ids = organizationVOMapper.selectContCatalogChildren(parentId, "1");
      if (!StringUtils.isEmpty(ids)) {
        return Arrays.asList(ids.split(","));
      }
    }
    return Collections.emptyList();
  }

  /**
   * 查询父元素下的所有子节点（包含父节点）- 组织机构表
   * @param parentId
   * @return
   */
  public List<String> selectOrganizationChildrenContainParent(String parentId) {
    return selectOrganizationChildren(parentId, "1");
  }

  /**
   * 查询父元素下的所有子节点（不包含父节点 - 组织机构表
   * @param parentId
   * @return
   */
  public List<String> selectOrganizationChildrenNotContainParent(String parentId) {
    return selectOrganizationChildren(parentId, "0");
  }

  /**
   * 查询父元素下的所有子节点（包含父节点） - 栏目表
   * @param parentId
   * @return
   */
  public List<String> selectContentCatalogChildrenContainParent(String parentId) {
    return selectContCatalogChildren(parentId, "1");
  }

  /**
   * 查询父元素下的所有子节点（不包含父节点 - 栏目表
   * @param parentId
   * @return
   */
  public List<String> selectContentCatalogChildrenNotContainParent(String parentId) {
    return selectContCatalogChildren(parentId,"0");
  }
}
