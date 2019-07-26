package com.magic.platform.api.business.label.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.magic.platform.api.business.label.mapper.custom.dao.ContLabelVOMapper;
import com.magic.platform.api.business.label.mapper.custom.entity.ContLabelVO;
import com.magic.platform.api.business.label.model.ContLabelModel;
import com.magic.platform.api.business.label.model.ContLabelQueryModel;
import com.magic.platform.core.exception.CustomException;
import com.magic.platform.core.model.RequestModel;
import com.magic.platform.entity.mapper.build.dao.ContLabelMapper;
import com.magic.platform.entity.mapper.build.entity.ContLabel;
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
public class LabelService {

  @Autowired
  private ContLabelMapper contLabelMapper;

  @Autowired
  private ContLabelVOMapper contLabelVOMapper;

  public void addEntity(ContLabelModel param) {
//    List<ContLabel> list = new ArrayList<>();

    if (param.getNames() != null) {
      for (int i = 0; i < param.getNames().size(); i++) {
        ContLabel entity = new ContLabel();
        String id = UUIDUtils.uuid();

        entity.setId(id);
        entity.setName(param.getNames().get(i));
        entity.setGroupId(param.getGroupId());

        entity.setCreateTime(new Date());
        entity.setUpdateTime(new Date());
        entity.setIsDeleted("0");

        entity.setIsShow("1");
        entity.setSortNumber(param.getSortNumber());

//        list.add(entity);
        contLabelMapper.insert(entity);
      }

//      FIXME： 批量插入提示数据库 id 字段未设置值
//      contLabelMapper.insertList(list);
    }
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

  public void updateShowStatus(ContLabelQueryModel model) {

    ContLabel param = new ContLabel();
    param.setIsShow(model.getIsShow());
    param.setId(model.getId());

    contLabelMapper.updateByPrimaryKeySelective(param);
  }


  public void checkExist(ContLabelQueryModel model) {

    Example example = new Example(ContLabel.class);
    Criteria criteria = example.createCriteria();

    criteria.andEqualTo("name", model.getName());
    criteria.andEqualTo("groupId", model.getGroupId());

    if (StringUtils.isNotEmpty(model.getId())) {
      criteria.andNotEqualTo("id", model.getId());
    }

    int count = contLabelMapper.selectCountByExample(example);
    if (0 < count) {
      throw new CustomException("标签【" + model.getName() + "】在【" + model.getGroupName() + "】标签组中已存在，请修改后提交");
    }
  }

  public void checkEntityStatus(String id) {

//    ContLabel param = new ContLabel();
//    param.setGroupId(id);
//
//    int cout = contLabelMapper.selectCount(param);
//    if (0 < cout) {
//      throw new CustomException("标签组下含有标签,无法删除");
//    }
  }
}
