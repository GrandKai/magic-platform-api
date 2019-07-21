package com.magic.platform.api.business.dictionary.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.magic.platform.api.business.dictionary.mapper.custom.dao.DictionaryItemVOMapper;
import com.magic.platform.api.business.dictionary.mapper.custom.entity.DictionaryItemVO;
import com.magic.platform.api.business.dictionary.model.DicItemQueryModel;
import com.magic.platform.api.business.dictionary.model.DicTypeQueryModel;
import com.magic.platform.core.exception.CustomException;
import com.magic.platform.core.model.RequestModel;
import com.magic.platform.entity.mapper.build.dao.DictionaryItemMapper;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.magic.platform.entity.mapper.build.entity.DictionaryItem;
import com.magic.platform.entity.mapper.build.entity.DictionaryType;
import com.magic.platform.util.UUIDUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author: zyn
 * @Description:
 * @Date: Created in 2018-09-30 09:09
 * @Modified By:
 */
@Service
public class DictionaryItemService {

  @Autowired
  private DictionaryItemMapper dictionaryItemMapper;
  @Autowired
  private DictionaryItemVOMapper dictionaryItemVOMapper;

  /**
   * 数据字典条目分页查询
   * @param requestModel
   * @return
   */
  public PageInfo selectEntityPage(RequestModel<DicItemQueryModel> requestModel) {

    DicItemQueryModel model = requestModel.getContent();

    int pageNum = requestModel.getPage().getPageNum() - 1;
    int pageSize = requestModel.getPage().getPageSize();

    Map<String, Object> param = new HashMap<>();
    param.put("name", model.getName());
    param.put("typeId", model.getTypeId());

    PageHelper.offsetPage(pageNum * pageSize, pageSize, true);

    List<DictionaryItemVO> list = dictionaryItemVOMapper.selectEntityList(param);
    PageInfo pageInfo = new PageInfo<>(list);

    return pageInfo;
  }

  public List<DictionaryItemVO> selectEntityList(String code) {

    Map<String, Object> param = new HashMap<>();
    param.put("code", code);

    param.put("itemIsShow", "1");
    param.put("typeIsShow", "1");

    return dictionaryItemVOMapper.selectEntityList(param);

  }

    @Transactional(rollbackFor = Exception.class)
    public void addEntity(DictionaryItem param) {

        DictionaryItem entity = new DictionaryItem();

        BeanUtils.copyProperties(param, entity);
        entity.setId(UUIDUtils.uuid());
        entity.setCreateTime(new Date());
        entity.setUpdateTime(new Date());
        entity.setIsDeleted("0");
        entity.setIsShow("1");

        dictionaryItemMapper.insert(entity);
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateEntity(DictionaryItem param) {

        DictionaryItem entity = new DictionaryItem();

        BeanUtils.copyProperties(param, entity);
        entity.setUpdateTime(new Date());

        dictionaryItemMapper.updateByPrimaryKeySelective(entity);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteEntity(String id) {
        DictionaryItem param = new DictionaryItem();
        param.setId(id);
        param.setIsDeleted("1");

        dictionaryItemMapper.updateByPrimaryKeySelective(param);
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateShowStatus(DicTypeQueryModel model) {

        DictionaryItem param = new DictionaryItem();
        param.setIsShow(model.getIsShow());
        param.setId(model.getId());

        dictionaryItemMapper.updateByPrimaryKeySelective(param);
    }

    /**
     * 检测数据项目是否存在
     * @param code
     */
    public void checkExist(String code) {

        DictionaryItem param = new DictionaryItem();
        param.setCode(code);

        int count = dictionaryItemMapper.selectCount(param);
        if (0 < count) {
            throw new CustomException("数据项目编码已经存在，请修改后再提交");
        }
    }
}
