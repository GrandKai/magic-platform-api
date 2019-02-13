package com.magic.platform.api.business.operation.controller;

import com.magic.platform.api.business.operation.mapper.custom.entity.GrantedOperation;
import com.magic.platform.api.business.operation.mapper.custom.entity.OperationVO;
import com.magic.platform.api.business.operation.model.GrantedOperationQueryModel;
import com.magic.platform.api.business.operation.model.OperationModel;
import com.magic.platform.api.business.operation.model.OperationQueryModel;
import com.magic.platform.api.business.operation.service.OperationService;
import com.magic.platform.core.util.Objects;
import com.magic.platform.core.anotation.OpsLog;
import com.magic.platform.core.anotation.OpsLogType;
import com.magic.platform.core.model.RequestModel;
import com.magic.platform.core.model.ResponseModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: zyn
 * @Description:
 * @Date: Created in 2018-09-29 09:09
 * @Modified By:
 */
@Slf4j
@Api(tags = "操作相关的接口")
@RestController
@RequestMapping("operation")
public class OperationController {

  @Autowired
  private OperationService operationService;

  @PostMapping("list")
  @ApiOperation(value = "查询操作列表")
  @OpsLog(value = "查询操作列表", type = OpsLogType.SELECT)
  public ResponseModel list(@RequestBody RequestModel<OperationQueryModel> requestModel) {
    OperationQueryModel model = requestModel.getContent();
    List<OperationVO> list = operationService.selectEntityList(model);
    return new ResponseModel<>("查询成功", list);
  }

  @PostMapping("add")
  @ApiOperation(value = "新增操作")
  @OpsLog(value = "新增操作", type = OpsLogType.ADD)
  public ResponseModel add(@RequestBody RequestModel<OperationModel> requestModel) {
    OperationModel param = requestModel.getContent();

    Objects.requireNonNull(param, "请求对象不能为空");
    Objects.requireNonNull(param.getParentId(), "菜单id不能为空");

    OperationVO output = operationService.addEntity(param);
    return new ResponseModel<>("新增操作成功！", output);
  }

  @PostMapping("update")
  @ApiOperation(value = "修改操作")
  @OpsLog(value = "修改操作", type = OpsLogType.UPDATE)
  public ResponseModel update(@RequestBody RequestModel<OperationModel> requestModel) {
    OperationModel param = requestModel.getContent();

    Objects.requireNonNull(param.getId(), "操作id不能为空");
    Objects.requireNonNull(param.getParentId(), "菜单id不能为空");

    OperationVO output = operationService.updateEntity(param);
    return new ResponseModel<>("修改操作成功！", output);
  }

  @PostMapping("delete")
  @ApiOperation(value = "删除操作")
  @OpsLog(value = "删除操作", type = OpsLogType.DELETE)
  public ResponseModel delete(@RequestBody RequestModel<String> requestModel) {
    String id = requestModel.getContent();
    Objects.requireNonNull(id, "操作id不能为空");

    operationService.deleteEntity(id);
    return new ResponseModel("删除操作成功！");
  }

  @PostMapping("granted/list")
  @ApiOperation(value = "根据菜单id获取授予的操作列表")
  @OpsLog(value = "根据菜单id获取授予的操作列表", type = OpsLogType.SELECT)
  public ResponseModel selectGrantedOperationList(@RequestBody RequestModel<GrantedOperationQueryModel> requestModel) {
    GrantedOperationQueryModel model = requestModel.getContent();

    Objects.requireNonNull(model, "请求对象不能为空");
    Objects.requireNonNull(model.getMenuId(), "菜单id不能为空");

    List<GrantedOperation> list = operationService.selectGrantedOperationList(model);
    return new ResponseModel<>("查询成功", list);
  }
}
