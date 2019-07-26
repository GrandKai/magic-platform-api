package com.magic.platform.api.business.label.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.magic.platform.api.business.label.mapper.custom.dao.ContLabelGroupVOMapper;
import com.magic.platform.api.business.label.mapper.custom.entity.ContLabelGroupVO;
import com.magic.platform.api.business.label.model.ContLabelGroupQueryModel;
import com.magic.platform.core.exception.CustomException;
import com.magic.platform.core.model.RequestModel;
import com.magic.platform.entity.mapper.build.dao.ContLabelGroupMapper;
import com.magic.platform.entity.mapper.build.dao.ContLabelMapper;
import com.magic.platform.entity.mapper.build.entity.ContLabel;
import com.magic.platform.entity.mapper.build.entity.ContLabelGroup;
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
 * @Date: Created in 2019-07-24 20:20
 * @Modified By:
 */
@Service
public class LabelGroupService {

  @Autowired
  private ContLabelGroupMapper contLabelGroupMapper;
  @Autowired
  private ContLabelMapper contLabelMapper;

  @Autowired
  private ContLabelGroupVOMapper contLabelGroupVOMapper;

  public ContLabelGroup addEntity(ContLabelGroup param) {
    ContLabelGroup entity = new ContLabelGroup();

    BeanUtils.copyProperties(param, entity);

    String id = UUIDUtils.uuid();

    entity.setId(id);
    entity.setCreateTime(new Date());
    entity.setUpdateTime(new Date());
    entity.setIsDeleted("0");
    entity.setIsShow("1");

    contLabelGroupMapper.insert(entity);

    return contLabelGroupMapper.selectByPrimaryKey(id);
  }

  public ContLabelGroup updateEntity(ContLabelGroup param) {

    ContLabelGroup entity = new ContLabelGroup();
    BeanUtils.copyProperties(param, entity);
    entity.setUpdateTime(new Date());

    contLabelGroupMapper.updateByPrimaryKeySelective(entity);

    return contLabelGroupMapper.selectByPrimaryKey(param.getId());
  }

  public void deleteEntity(List<String> ids) {

    Example example = new Example(ContLabelGroup.class);
    Criteria criteria = example.createCriteria();
    criteria.andIn("id", ids);

    ContLabelGroup param = new ContLabelGroup();
    param.setIsDeleted("1");
    param.setUpdateTime(new Date());
    contLabelGroupMapper.updateByExampleSelective(param, example);

  }

  public List<ContLabelGroupVO> selectEntityList(ContLabelGroupQueryModel model) {
    return contLabelGroupVOMapper.selectEntityList(model);
  }

  public PageInfo selectEntityPage(RequestModel<ContLabelGroupQueryModel> requestModel) {

    ContLabelGroupQueryModel model = requestModel.getContent();

    int pageNum = requestModel.getPage().getPageNum() - 1;
    int pageSize = requestModel.getPage().getPageSize();

    PageHelper.offsetPage(pageNum * pageSize, pageSize, true);

    List<ContLabelGroupVO> list = contLabelGroupVOMapper.selectEntityList(model);
    PageInfo pageInfo = new PageInfo(list);

    return pageInfo;
  }

  public ContLabelGroup getEntity(String id) {
    return contLabelGroupMapper.selectByPrimaryKey(id);
  }

  public void updateShowStatus(ContLabelGroupQueryModel model) {

    ContLabelGroup param = new ContLabelGroup();
    param.setIsShow(model.getIsShow());
    param.setId(model.getId());

    contLabelGroupMapper.updateByPrimaryKeySelective(param);
  }

  public void checkEntityStatus(String id) {

    ContLabel param = new ContLabel();
    param.setGroupId(id);

    int cout = contLabelMapper.selectCount(param);
    if (0 < cout) {
      throw new CustomException("标签组下含有标签");
    }
  }

  public void checkExist(ContLabelGroupQueryModel model) {

    Example example = new Example(ContLabelGroup.class);
    Criteria criteria = example.createCriteria();

    criteria.andEqualTo("name", model.getName());
    if (StringUtils.isNotEmpty(model.getId())) {
      criteria.andNotEqualTo("id", model.getId());
    }

    int count = contLabelGroupMapper.selectCountByExample(example);
    if (0 < count) {
      throw new CustomException("标签组名称已存在，请修改后提交");
    }
  }
}
