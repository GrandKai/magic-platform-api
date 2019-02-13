package com.magic.platform.api.business.dictionary.mapper.custom.dao;

import com.magic.platform.api.business.dictionary.mapper.custom.entity.DictionaryTypeVO;
import com.magic.platform.entity.mapper.build.BaseMapper;
import com.magic.platform.entity.mapper.build.entity.DictionaryType;
import java.util.List;
import java.util.Map;

/**
 * @Author: zyn
 * @Description:
 * @Date: Created in 2018-09-30 09:09
 * @Modified By:
 */
public interface DictionaryTypeVOMapper extends BaseMapper<DictionaryTypeVO> {

  List<DictionaryType> selectEntityList(Map<String, Object> param);
}
