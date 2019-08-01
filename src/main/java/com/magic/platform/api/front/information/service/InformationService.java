package com.magic.platform.api.front.information.service;

import com.magic.platform.api.business.information.mapper.custom.dao.ContInformationVOMapper;
import com.magic.platform.api.business.information.model.ContInformationQueryModel;
import com.magic.platform.api.front.information.dto.InformationDto;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: Administrator
 * @Description:
 * @Date: Created in 2019-08-01 15:15
 * @Modified By:
 */
@Service
public class InformationService {

  @Autowired
  private ContInformationVOMapper contInformationVOMapper;

  /**
   * 手机-根据栏目id查询资讯列表
   * @return
   */
  public List<InformationDto> selectInformationList(ContInformationQueryModel model) {

    return contInformationVOMapper.selectInformationList(model);
  }
}
