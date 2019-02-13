package com.magic.platform.api.business.dictionary.mapper.custom.dao;

import com.magic.platform.api.business.dictionary.mapper.custom.entity.DictionaryItemVO;
import com.magic.platform.entity.mapper.build.BaseMapper;
import java.util.List;
import java.util.Map;

/**
 * @Author: zyn
 * @Description:
 * @Date: Created in 2018-09-30 09:09
 * @Modified By:
 */
public interface DictionaryItemVOMapper extends BaseMapper<DictionaryItemVO> {

  List<DictionaryItemVO> selectEntityList(Map<String, Object> param);
}
