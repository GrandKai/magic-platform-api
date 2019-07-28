package com.magic.platform.api.business.information.service;

import com.magic.platform.api.business.information.mapper.custom.dao.ContAssociationVOMapper;
import com.magic.platform.api.business.information.mapper.custom.entity.ContInformationSimpleVO;
import com.magic.platform.api.business.information.model.ContAssociationQueryModel;
import com.magic.platform.api.business.label.mapper.custom.entity.ContLabelVO;
import com.magic.platform.api.util.Constant;
import com.magic.platform.core.model.ResponseModel;
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

  /**
   * 查询所有相关联的资讯信息
   * @param model
   * @return
   */
  public List<ContInformationSimpleVO> selectAssociationInformationSimpleVOList(ContAssociationQueryModel model) {
    return contAssociationVOMapper.selectAssociationInformationSimpleVOList(model);
  }

  /**
   * 查询所有相关联的标签信息
   * @param model
   * @return
   */
  public List<ContLabelVO> selectAssociationLabelList(ContAssociationQueryModel model) {
    return contAssociationVOMapper.selectAssociationLabelList(model);
  }


  public ResponseModel selectAssociationList(String sourceType, ContAssociationQueryModel model) {

    if (Constant.INFORMATION.equalsIgnoreCase(sourceType)) {
      List<ContInformationSimpleVO> list = selectAssociationInformationSimpleVOList (model);
      return new ResponseModel<>("查询成功！", list);
    }

    if (Constant.LABEL.equalsIgnoreCase(sourceType)) {
      List<ContLabelVO> list = selectAssociationLabelList(model);
      return new ResponseModel<>("查询成功！", list);
    }

    return new ResponseModel<>(-1, "查询异常！sourceType 类型有误");
  }
}
