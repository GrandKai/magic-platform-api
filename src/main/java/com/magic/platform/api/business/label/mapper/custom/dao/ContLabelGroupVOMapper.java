package com.magic.platform.api.business.label.mapper.custom.dao;

import com.magic.platform.api.business.label.mapper.custom.entity.ContLabelGroupVO;
import com.magic.platform.api.business.label.model.ContLabelGroupQueryModel;
import com.magic.platform.entity.mapper.build.BaseMapper;
import java.util.List;

/**
 * @Author: Administrator
 * @Description:
 * @Date: Created in 2019-07-24 20:20
 * @Modified By:
 */
public interface ContLabelGroupVOMapper extends BaseMapper<ContLabelGroupVO> {

  List<ContLabelGroupVO> selectEntityList(ContLabelGroupQueryModel model);
}
