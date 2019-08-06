package com.magic.platform.api.front.information.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.magic.platform.api.business.information.mapper.custom.dao.ContInformationVOMapper;
import com.magic.platform.api.business.information.model.ContInformationQueryModel;
import com.magic.platform.api.business.organization.service.OrganizationService;
import com.magic.platform.api.front.information.dto.InformationDetailDto;
import com.magic.platform.api.front.information.dto.InformationDto;
import com.magic.platform.core.model.RequestModel;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: Administrator
 * @Description:
 * @Date: Created in 2019-08-01 15:15
 * @Modified By:
 */
@Slf4j
@Service
public class InformationService {

  @Autowired
  private ContInformationVOMapper contInformationVOMapper;
  @Autowired
  private OrganizationService organizationService;

  /**
   * 手机-根据栏目id查询资讯列表
   * @return
   */
  public PageInfo selectInformationPage(RequestModel<ContInformationQueryModel> requestModel) {
    ContInformationQueryModel model = requestModel.getContent();

    int pageNum = requestModel.getPage().getPageNum() - 1;
    int pageSize = requestModel.getPage().getPageSize();

    List<String> catalogs = organizationService.selectContentCatalogChildrenContainParent(model.getContCatalogId());
    model.setList(catalogs);

    PageHelper.offsetPage(pageNum * pageSize, pageSize, true);
    List<InformationDto> list = contInformationVOMapper.selectInformationList(model);
    PageInfo pageInfo = new PageInfo<>(list);

    return pageInfo;
  }


  /**
   * 手机端-查询资讯详情
   * @param id
   * @return
   */
  public InformationDetailDto selectInformationById(String id) {
    return contInformationVOMapper.selectInformationById(id);
  }
}
