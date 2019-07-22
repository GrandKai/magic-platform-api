package com.magic.platform.api.business.information.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.magic.platform.api.business.information.mapper.custom.dao.ContInformationVOMapper;
import com.magic.platform.api.business.information.mapper.custom.entity.ContInformationVO;
import com.magic.platform.api.business.information.model.ContInformationQueryModel;
import com.magic.platform.api.business.organization.service.OrganizationService;
import com.magic.platform.core.constant.Constant;
import com.magic.platform.core.model.RequestModel;
import com.magic.platform.entity.mapper.build.dao.ContInformationMapper;
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
  private OrganizationService organizationService;

  public ContInformation addEntity(ContInformation param) {
    ContInformation entity = new ContInformation();

    BeanUtils.copyProperties(param, entity);

    String id = UUIDUtils.uuid();

    entity.setId(id);
    entity.setCreateTime(new Date());
    entity.setUpdateTime(new Date());
    entity.setIsDeleted("0");

    contInformationMapper.insert(entity);

    return contInformationMapper.selectByPrimaryKey(id);
  }

  public ContInformation updateEntity(ContInformation param) {

    ContInformation entity = new ContInformation();
    BeanUtils.copyProperties(param, entity);
    entity.setUpdateTime(new Date());

    contInformationMapper.updateByPrimaryKeySelective(entity);

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

    List<ContInformationVO> list = contInformationVOMapper.selectEntityList(model);
    PageInfo pageInfo = new PageInfo(list);

    return pageInfo;
  }
}
