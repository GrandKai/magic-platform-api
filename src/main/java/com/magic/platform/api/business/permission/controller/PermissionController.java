package com.magic.platform.api.business.permission.controller;

import com.magic.platform.api.business.operation.mapper.custom.entity.OperationVO;
import com.magic.platform.api.business.permission.model.PermissionModel;
import com.magic.platform.api.business.permission.model.PermissionQueryModel;
import com.magic.platform.api.business.permission.service.PermissionService;
import com.magic.platform.core.util.Objects;
import com.magic.platform.core.anotation.OpsLog;
import com.magic.platform.core.anotation.OpsLogType;
import com.magic.platform.core.model.RequestModel;
import com.magic.platform.core.model.ResponseModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: zyn
 * @Description:
 * @Date: Created in 2018-09-29 15:15
 * @Modified By:
 */
@Slf4j
@Api(tags = "访问权限URL相关操作")
@RestController
@RequestMapping("permission")
public class PermissionController {


  @Autowired
  private PermissionService permissionService;

  @PostMapping("list")
  @ApiOperation(value = "查询子操作列表")
  @OpsLog(value = "查询子操作列表", type = OpsLogType.SELECT)
  public ResponseModel list(@RequestBody RequestModel<PermissionQueryModel> requestModel) {
    PermissionQueryModel model = requestModel.getContent();
    List<OperationVO> list = permissionService.selectEntityList(model);
    return new ResponseModel<>("查询成功", list);
  }

  @PostMapping("add")
  @ApiOperation(value = "新增子操作")
  @OpsLog(value = "新增子操作", type = OpsLogType.ADD)
  public ResponseModel add(@RequestBody RequestModel<PermissionModel> requestModel) {
    PermissionModel param = requestModel.getContent();

    Objects.requireNonNull(param, "请求对象不能为空");
    Objects.requireNonNull(param.getParentId(), "操作id不能为空");

    OperationVO output = permissionService.addEntity(param);
    return new ResponseModel<>("新增子操作成功！", output);
  }

  @PostMapping("update")
  @ApiOperation(value = "修改子操作")
  @OpsLog(value = "修改子操作", type = OpsLogType.UPDATE)
  public ResponseModel update(@RequestBody RequestModel<PermissionModel> requestModel) {
    PermissionModel param = requestModel.getContent();

    Objects.requireNonNull(param.getId(), "子操作id不能为空");
    Objects.requireNonNull(param.getParentId(), "操作id不能为空");

    OperationVO output = permissionService.updateEntity(param);
    return new ResponseModel<>("修改子操作成功！", output);
  }

  @PostMapping("delete")
  @ApiOperation(value = "删除子操作")
  @OpsLog(value = "删除子操作", type = OpsLogType.DELETE)
  public ResponseModel delete(@RequestBody RequestModel<String> requestModel) {
    String id = requestModel.getContent();
    Objects.requireNonNull(id, "子操作id不能为空");

    permissionService.deleteEntity(id);
    return new ResponseModel("删除子操作成功！");
  }

}
