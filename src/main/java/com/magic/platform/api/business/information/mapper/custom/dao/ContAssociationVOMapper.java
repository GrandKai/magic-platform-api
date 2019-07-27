package com.magic.platform.api.business.information.mapper.custom.dao;

import com.magic.platform.api.business.information.mapper.custom.entity.ContAssociationVO;
import com.magic.platform.api.business.information.mapper.custom.entity.ContInformationSimpleVO;
import com.magic.platform.api.business.information.model.ContAssociationQueryModel;
import com.magic.platform.entity.mapper.build.BaseMapper;
import java.util.List;

/**
 * @Author: Administrator
 * @Description:
 * @Date: Created in 2019-07-27 14:14
 * @Modified By:
 */
public interface ContAssociationVOMapper extends BaseMapper<ContAssociationVO> {

  List<ContInformationSimpleVO> selectContInformationSimpleVOList(ContAssociationQueryModel model);

  int deleteContAssociations(ContAssociationQueryModel model);
}
