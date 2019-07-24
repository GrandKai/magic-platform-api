package com.magic.platform.api.business.label.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.magic.platform.api.business.label.mapper.custom.dao.ContLabelVOMapper;
import com.magic.platform.api.business.label.mapper.custom.entity.ContLabelVO;
import com.magic.platform.api.business.label.model.ContLabelQueryModel;
import com.magic.platform.core.model.RequestModel;
import com.magic.platform.entity.mapper.build.dao.ContLabelMapper;
import com.magic.platform.entity.mapper.build.entity.ContLabel;
import com.magic.platform.util.UUIDUtils;
import java.util.Date;
import java.util.List;
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
public class LabelService {

  @Autowired
  private ContLabelMapper contLabelMapper;

  @Autowired
  private ContLabelVOMapper contLabelVOMapper;

  public ContLabel addEntity(ContLabel param) {
    ContLabel entity = new ContLabel();

    BeanUtils.copyProperties(param, entity);

    String id = UUIDUtils.uuid();

    entity.setId(id);
    entity.setCreateTime(new Date());
    entity.setUpdateTime(new Date());
    entity.setIsDeleted("0");

    contLabelMapper.insert(entity);

    return contLabelMapper.selectByPrimaryKey(id);
  }

  public ContLabel updateEntity(ContLabel param) {

    ContLabel entity = new ContLabel();
    BeanUtils.copyProperties(param, entity);
    entity.setUpdateTime(new Date());

    contLabelMapper.updateByPrimaryKeySelective(entity);

    return contLabelMapper.selectByPrimaryKey(param.getId());
  }

  public void deleteEntity(List<String> ids) {

    Example example = new Example(ContLabel.class);
    Criteria criteria = example.createCriteria();
    criteria.andIn("id", ids);

    ContLabel param = new ContLabel();
    param.setIsDeleted("1");
    param.setUpdateTime(new Date());
    contLabelMapper.updateByExampleSelective(param, example);

  }

  public List<ContLabelVO> selectEntityList(ContLabelQueryModel model) {
    return contLabelVOMapper.selectEntityList(model);
  }

  public PageInfo selectEntityPage(RequestModel<ContLabelQueryModel> requestModel) {

    ContLabelQueryModel model = requestModel.getContent();

    int pageNum = requestModel.getPage().getPageNum() - 1;
    int pageSize = requestModel.getPage().getPageSize();

    PageHelper.offsetPage(pageNum * pageSize, pageSize, true);

    List<ContLabelVO> list = contLabelVOMapper.selectEntityList(model);
    PageInfo pageInfo = new PageInfo(list);

    return pageInfo;
  }

  public ContLabel getEntity(String id) {
    return contLabelMapper.selectByPrimaryKey(id);
  }
}
