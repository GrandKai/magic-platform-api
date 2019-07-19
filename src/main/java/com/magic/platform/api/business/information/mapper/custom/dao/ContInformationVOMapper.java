package com.magic.platform.api.business.information.mapper.custom.dao;

import com.magic.platform.api.business.information.mapper.custom.entity.ContInformationVO;
import com.magic.platform.api.business.information.model.ContInformationQueryModel;
import com.magic.platform.entity.mapper.build.BaseMapper;
import java.util.List;

/**
 * @Author: Administrator
 * @Description:
 * @Date: Created in 2019-07-19 23:23
 * @Modified By:
 */
public interface ContInformationVOMapper extends BaseMapper<ContInformationVO> {

  List<ContInformationVO> selectEntityList(ContInformationQueryModel model);
}
