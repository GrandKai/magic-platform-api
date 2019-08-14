package com.magic.platform.api.business.information.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.magic.platform.api.business.information.mapper.custom.dao.ContAssociationVOMapper;
import com.magic.platform.api.business.information.mapper.custom.dao.ContInformationVOMapper;
import com.magic.platform.api.business.information.mapper.custom.entity.ContInformationSimpleVO;
import com.magic.platform.api.business.information.mapper.custom.entity.ContInformationVO;
import com.magic.platform.api.business.information.model.ContAssociationQueryModel;
import com.magic.platform.api.business.information.model.ContInformationModel;
import com.magic.platform.api.business.information.model.ContInformationQueryModel;
import com.magic.platform.api.business.organization.service.OrganizationService;
import com.magic.platform.core.constant.Constant;
import com.magic.platform.core.model.RequestModel;
import com.magic.platform.entity.mapper.build.dao.ContAssociationMapper;
import com.magic.platform.entity.mapper.build.dao.ContInformationMapper;
import com.magic.platform.entity.mapper.build.entity.ContAssociation;
import com.magic.platform.entity.mapper.build.entity.ContInformation;
import com.magic.platform.util.UUIDUtils;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

/**
 * @Author: Administrator
 * @Description:
 * @Date: Created in 2019-07-19 23:23
 * @Modified By:
 */
@Service
public class ContInformationService {

  @Autowired
  private ContInformationMapper contInformationMapper;
  @Autowired
  private ContInformationVOMapper contInformationVOMapper;

  @Autowired
  private ContAssociationMapper contAssociationMapper;
  @Autowired
  private ContAssociationVOMapper contAssociationVOMapper;

  @Autowired
  private OrganizationService organizationService;

  public ContInformation addEntity(ContInformationModel param) {
    ContInformation entity = new ContInformation();

    BeanUtils.copyProperties(param, entity);
    String informationId = UUIDUtils.uuid();

    entity.setId(informationId);
    entity.setCreateTime(new Date());
    entity.setUpdateTime(new Date());
    entity.setIsDeleted("0");

    contInformationMapper.insert(entity);

    this.insertListAssociation(informationId, param.getInformation());
    this.insertListAssociation(informationId, param.getLabels());

    return contInformationMapper.selectByPrimaryKey(informationId);
  }

  /**
   * 批量插入关联信息
   * @param informationId 资讯id
   * @param associations 关联内容列表
   */
  private void insertListAssociation(String informationId, List<ContAssociation> associations) {
    if (associations != null) {

      ContAssociation associationEntity = null;
      for (int i = 0; i < associations.size(); i++) {
        ContAssociation association = associations.get(i);

        associationEntity = new ContAssociation();

        associationEntity.setId(UUIDUtils.uuid());
        associationEntity.setCreateTime(new Date());
        associationEntity.setUpdateTime(new Date());

        associationEntity.setAssociationId(association.getAssociationId());


        associationEntity.setSortNumber((short) (i + 1));
        associationEntity.setSourceId(informationId);
        // TODO：取数据字典的值
        associationEntity.setSourceType(association.getSourceType());

        // 添加关联资讯
        contAssociationMapper.insert(associationEntity);

      }
    }
  }

  public ContInformation updateEntity(ContInformationModel param) {

    ContInformation entity = new ContInformation();
    BeanUtils.copyProperties(param, entity);
    entity.setUpdateTime(new Date());

    contInformationMapper.updateByPrimaryKeySelective(entity);

    String informationId = param.getId();

    // 删除旧资讯信息-添加新资讯信息
    ContAssociationQueryModel associationInformation = new ContAssociationQueryModel();
    associationInformation.setSourceType(com.magic.platform.api.framework.util.Constant.INFORMATION);
    associationInformation.setSourceId(informationId);

    contAssociationVOMapper.deleteContAssociations(associationInformation);
    this.insertListAssociation(informationId, param.getInformation());

    // 删除旧标签信息-添加新标签信息
    ContAssociationQueryModel associationLabel = new ContAssociationQueryModel();
    associationLabel.setSourceType(com.magic.platform.api.framework.util.Constant.LABEL);
    associationLabel.setSourceId(informationId);

    contAssociationVOMapper.deleteContAssociations(associationLabel);
    this.insertListAssociation(informationId, param.getLabels());

    return contInformationMapper.selectByPrimaryKey(param.getId());
  }

  public void deleteEntity(List<String> ids) {

    Example example = new Example(ContInformation.class);
    Criteria criteria = example.createCriteria();
    criteria.andIn("id", ids);

    ContInformation param = new ContInformation();
    param.setIsDeleted("1");
    param.setUpdateTime(new Date());
    contInformationMapper.updateByExampleSelective(param, example);

  }

  public List<ContInformationVO> selectEntityList(ContInformationQueryModel model) {
    return contInformationVOMapper.selectEntityList(model);
  }

  public PageInfo selectEntityPage(RequestModel<ContInformationQueryModel> requestModel) {
    return this.selectEntityPage(requestModel, false);
  }

  public PageInfo selectEntityPageSimple(RequestModel<ContInformationQueryModel> requestModel) {
    return this.selectEntityPage(requestModel, true);
  }

  /**
   *
   * @param requestModel
   * @param simple 是否是轻量级实体
   * @return
   */
  public PageInfo selectEntityPage(RequestModel<ContInformationQueryModel> requestModel, boolean simple) {

    ContInformationQueryModel model = requestModel.getContent();

    int pageNum = requestModel.getPage().getPageNum() - 1;
    int pageSize = requestModel.getPage().getPageSize();

    // 查询该栏目下的所有资讯
    if (!StringUtils.isEmpty(model.getContCatalogId())) {

      // 如果不是 0 查询的是具体某个结点
      if (!Constant.ZERO.equals(model.getLevel())) {
        List<String> list = organizationService.selectContentCatalogChildrenContainParent(model.getContCatalogId());
        model.setList(list);
      }
    }

    PageHelper.offsetPage(pageNum * pageSize, pageSize, true);

    if (simple) {
      List<ContInformationSimpleVO> list = contInformationVOMapper.selectSimpleEntityList(model);
      return new PageInfo<>(list);
    } else {
      List<ContInformationVO> list = contInformationVOMapper.selectEntityList(model);
      return new PageInfo<>(list);
    }

  }

  public ContInformation getEntity(String id) {
    return contInformationMapper.selectByPrimaryKey(id);
  }
}
