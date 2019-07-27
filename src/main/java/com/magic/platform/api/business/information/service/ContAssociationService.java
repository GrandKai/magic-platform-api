package com.magic.platform.api.business.information.service;

import com.magic.platform.api.business.information.mapper.custom.dao.ContAssociationVOMapper;
import com.magic.platform.api.business.information.mapper.custom.entity.ContInformationSimpleVO;
import com.magic.platform.api.business.information.model.ContAssociationQueryModel;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: Administrator
 * @Description:
 * @Date: Created in 2019-07-27 14:14
 * @Modified By:
 */
@Service
public class ContAssociationService {

  @Autowired
  private ContAssociationVOMapper contAssociationVOMapper;

  public List<ContInformationSimpleVO> selectContInformationSimpleVOList(ContAssociationQueryModel model) {
    return contAssociationVOMapper.selectContInformationSimpleVOList(model);
  }


}
