package com.magic.platform.api.business.dictionary.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.magic.platform.api.business.dictionary.mapper.custom.dao.DictionaryTypeVOMapper;
import com.magic.platform.api.business.dictionary.mapper.custom.entity.DictionaryTypeVO;
import com.magic.platform.api.business.dictionary.model.DicTypeQueryModel;
import com.magic.platform.api.exception.BusinessExceptionEnum;
import com.magic.platform.core.exception.CustomException;
import com.magic.platform.core.model.RequestModel;
import com.magic.platform.entity.mapper.build.dao.DictionaryItemMapper;
import com.magic.platform.entity.mapper.build.dao.DictionaryTypeMapper;
import com.magic.platform.entity.mapper.build.entity.DictionaryItem;
import com.magic.platform.entity.mapper.build.entity.DictionaryType;
import com.magic.platform.entity.mapper.build.entity.Plat;
import com.magic.platform.util.UUIDUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: zyn
 * @Description:
 * @Date: Created in 2018-09-30 09:09
 * @Modified By:
 */
@Service
public class DictionaryTypeService {
    @Autowired
    private DictionaryTypeVOMapper dictionaryTypeVOMapper;

    @Autowired
    private DictionaryTypeMapper dictionaryTypeMapper;

    @Autowired
    private DictionaryItemMapper dictionaryItemMapper;

    public PageInfo selectEntityPage(RequestModel<DicTypeQueryModel> requestModel) {

        DicTypeQueryModel model = requestModel.getContent();

        int pageNum = requestModel.getPage().getPageNum() - 1;
        int pageSize = requestModel.getPage().getPageSize();

        Map<String, Object> param = new HashMap<>();
        param.put("name", model.getName());

        PageHelper.offsetPage(pageNum * pageSize, pageSize, true);


        List<DictionaryType> list = dictionaryTypeVOMapper.selectEntityList(param);
        PageInfo pageInfo = new PageInfo<>(list);

        return pageInfo;
    }

    public List<DictionaryType> selectEntityList(DicTypeQueryModel model) {

        Map<String, Object> param = new HashMap<>();
        param.put("name", model.getName());
        param.put("isShow", model.getIsShow());

        return dictionaryTypeVOMapper.selectEntityList(param);

    }

    @Transactional(rollbackFor = Exception.class)
    public void updateEntity(DictionaryType param) {

        DictionaryType entity = new DictionaryType();

        BeanUtils.copyProperties(param, entity);
        entity.setUpdateTime(new Date());

        dictionaryTypeMapper.updateByPrimaryKeySelective(entity);

    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteEntity(String id) {
        DictionaryType param = new DictionaryType();
        param.setId(id);
        param.setIsDeleted("1");

        dictionaryTypeMapper.updateByPrimaryKeySelective(param);
    }

    @Transactional(rollbackFor = Exception.class)
    public void addEntity(DictionaryType param) {

        DictionaryType entity = new DictionaryType();

        BeanUtils.copyProperties(param, entity);
        entity.setId(UUIDUtils.uuid());
        entity.setCreateTime(new Date());
        entity.setUpdateTime(new Date());
        entity.setIsDeleted("0");
        entity.setIsShow("1");

        dictionaryTypeMapper.insert(entity);
    }

    /**
     * 修改数据类型显示隐藏状态
     * @param model
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateShowStatus(DicTypeQueryModel model) {

        DictionaryType param = new DictionaryType();
        param.setIsShow(model.getIsShow());
        param.setId(model.getId());

        dictionaryTypeMapper.updateByPrimaryKeySelective(param);
    }

    /**
     * 删除前校验数据类型下是否存在数据项目
     * @param id
     */
    public void checkEntityStatus(String id) {

        DictionaryItem param = new DictionaryItem();
        param.setTypeId(id);

        int cout = dictionaryItemMapper.selectCount(param);
        if (0 < cout) {
            throw new CustomException("数据类型下有数据项目,无法删除");
        }
    }

    /**
     * 检测数据类型编码是否已经存在
     * @param code
     */
    public void checkExist(String code) {

        DictionaryType param = new DictionaryType();
        param.setCode(code);

        int count = dictionaryTypeMapper.selectCount(param);
        if (0 < count) {
            throw new CustomException("数据类型编码已经存在，请修改后再提交");
        }
    }
}
