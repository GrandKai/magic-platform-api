package com.magic.platform.api.front.information.controller;

import com.github.pagehelper.PageInfo;
import com.magic.platform.api.business.information.model.ContInformationQueryModel;
import com.magic.platform.api.front.information.dto.InformationDetailDto;
import com.magic.platform.api.front.information.service.InformationService;
import com.magic.platform.core.anotation.OpsLog;
import com.magic.platform.core.anotation.OpsLogType;
import com.magic.platform.core.model.RequestModel;
import com.magic.platform.core.model.ResponseModel;
import io.swagger.annotations.ApiOperation;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Administrator
 * @Description:
 * @Date: Created in 2019-08-01 14:14
 * @Modified By:
 */
@RestController
@RequestMapping("front/information")
public class InformationController {

  @Autowired
  private InformationService informationService;

  @PostMapping("list")
  @OpsLog(value = "手机-根据栏目id查询资讯列表", type = OpsLogType.SELECT)
  @ApiOperation(value = "手机-根据栏目id查询资讯列表")
  public ResponseModel list(@RequestBody RequestModel<ContInformationQueryModel> requestModel) {
    ContInformationQueryModel model = requestModel.getContent();
    Objects.requireNonNull(model, "请求参数不能为空");

    PageInfo pageInfo = informationService.selectInformationPage(requestModel);
    return new ResponseModel<>("查询资讯列表成功", pageInfo);
  }

  @PostMapping("getOne")
  @OpsLog(value = "手机端-查询资讯详情", type = OpsLogType.SELECT)
  @ApiOperation(value = "手机端-查询资讯详情")
  public ResponseModel getOne(@RequestBody RequestModel<String> requestModel) {
    String id = requestModel.getContent();
    Objects.requireNonNull(id, "资讯id不能为空");

    InformationDetailDto entity = informationService.selectInformationById(id);
    return new ResponseModel<>("查询资讯列表成功", entity);
  }
}
