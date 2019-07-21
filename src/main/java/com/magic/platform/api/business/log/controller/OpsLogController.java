package com.magic.platform.api.business.log.controller;

import com.github.pagehelper.PageInfo;
import com.magic.platform.api.business.log.model.OpsLogQueryModel;
import com.magic.platform.api.business.log.service.OpsLogService;
import com.magic.platform.core.anotation.OpsLog;
import com.magic.platform.core.anotation.OpsLogType;
import com.magic.platform.core.model.RequestModel;
import com.magic.platform.core.model.ResponseModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: zyn
 * @Description: 操作日志查询
 * @Date: Created in 2018-10-16 10:10
 * @Modified By:
 */
@Api(tags = "日志相关操作")
@RestController
@RequestMapping("opslog")
public class OpsLogController {

  @Autowired
  private OpsLogService opsLogService;

  @PostMapping
  @ApiOperation(value = "查询操作日志分页")
  @OpsLog(value = "查询操作日志分页", type = OpsLogType.SELECT)
  public ResponseModel page(@RequestBody RequestModel<OpsLogQueryModel> requestModel) {
    PageInfo pageInfo = opsLogService.selectEntityPage(requestModel);
    return new ResponseModel<>(pageInfo);
  }
}
