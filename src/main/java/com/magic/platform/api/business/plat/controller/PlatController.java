package com.magic.platform.api.business.plat.controller;

import com.github.pagehelper.PageInfo;
import com.magic.platform.api.business.plat.mapper.custom.entity.PlatRoleVO;
import com.magic.platform.api.business.plat.mapper.custom.entity.PlatVO;
import com.magic.platform.api.business.plat.model.PlatQueryModel;
import com.magic.platform.api.business.plat.service.PlatService;
import com.magic.platform.core.anotation.OpsLog;
import com.magic.platform.core.anotation.OpsLogType;
import com.magic.platform.core.model.RequestModel;
import com.magic.platform.core.model.ResponseModel;
import com.magic.platform.core.util.Objects;
import com.magic.platform.entity.mapper.build.entity.Plat;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: zyn
 * @Description:
 * @Date: Created in 2018-09-20 18:18
 * @Modified By:
 */
@Slf4j
@Api(tags = "系统信息相关操作")
@RestController
@RequestMapping("plat")
public class PlatController {

  @Autowired
  private PlatService platService;

  @PostMapping
  @ApiOperation(value = "获取【系统】分页信息")
  @OpsLog(value = "获取【系统】分页信息", type = OpsLogType.SELECT)
  public ResponseModel page(@RequestBody RequestModel<Void> requestModel) {
    PageInfo pageInfo = platService.selectEntityPage(requestModel);
    return new ResponseModel<>(pageInfo);
  }

  @PostMapping("list")
  @ApiOperation(value = "查询系统列表")
  @OpsLog(value = "查询系统列表", type = OpsLogType.SELECT)
  public ResponseModel list(@RequestBody RequestModel<Void> requestModel) {
    List<PlatVO> list = platService.selectEntityList();
    return new ResponseModel<>(list);
  }

  @PostMapping("stop")
  @ApiOperation(value = "系统启停")
  @OpsLog(value = "系统启停", type = OpsLogType.STOP)
  public ResponseModel stopSystem(@RequestBody RequestModel<PlatQueryModel> requestModel) {
    PlatQueryModel model = requestModel.getContent();

    Objects.requireNonNull(model, "入参不能为空");
    Objects.requireNonNull(model.getId(), "系统id不能为空");
    Objects.requireNonNull(model.getIsEnabled(), "停(启)用标识不能为空");

    ResponseModel responseModel = new ResponseModel();
    if (StringUtils.equals("1", model.getIsEnabled())) {
      responseModel.setMessage("启用成功！");
    } else {
      responseModel.setMessage("停用成功！");
    }

    platService.stopSystem(model);
    return responseModel;
  }

  @PostMapping("add")
  @ApiOperation(value = "新增系统")
  @OpsLog(value = "新增系统", type = OpsLogType.ADD)
  public ResponseModel add(@RequestBody RequestModel<Plat> requestModel) {

    platService.addEntity(requestModel.getContent());
    return new ResponseModel("新增系统成功！");
  }

  @PostMapping("update")
  @ApiOperation(value = "修改系统")
  @OpsLog(value = "修改系统", type = OpsLogType.UPDATE)
  public ResponseModel update(@RequestBody RequestModel<Plat> requestModel) {
    Plat param = requestModel.getContent();
    Objects.requireNonNull(param, "请求对象不能为空");
    Objects.requireNonNull(param.getId(), "系统id不能为空");
    platService.updateEntity(param);
    return new ResponseModel("修改系统成功！");
  }

  @PostMapping("delete")
  @ApiOperation(value = "删除系统")
  @OpsLog(value = "删除系统", type = OpsLogType.DELETE)
  public ResponseModel delete(@RequestBody RequestModel<String> requestModel) {
    String id = requestModel.getContent();
    Objects.requireNonNull(id, "系统id不能为空");
    platService.deleteEntity(id);

    return new ResponseModel();
  }

  @PostMapping("check/exist")
  @ApiOperation(value = "检测系统是否存在")
  @OpsLog(value = "检测系统是否存在", type = OpsLogType.CHECK)
  public ResponseModel checkExist(@RequestBody RequestModel<String> requestModel) {
    // 如果修改系统名 判断系统是否存在
    String name = requestModel.getContent();
    Objects.requireNonNull(name, "系统名称不能为空");
    platService.checkExist(name);
    return new ResponseModel();
  }

  @PostMapping("check/update/exist")
  @ApiOperation(value = "检测【修改】系统是否存在")
  @OpsLog(value = "检测【修改】系统是否存在", type = OpsLogType.CHECK)
  public ResponseModel checkUpdateExist(@RequestBody RequestModel<PlatVO> requestModel) {
    // 如果修改系统名 判断系统是否存在
    PlatVO model = requestModel.getContent();
    Objects.requireNonNull(model, "请求对象不能为空");
    Objects.requireNonNull(model.getId(), "系统id不能为空");
    Objects.requireNonNull(model.getName(), "系统名称不能为空");
    platService.checkUpdateExist(model);
    return new ResponseModel();
  }


  @PostMapping("role/list")
  @ApiOperation(value = "查询所有平台所有角色")
  @OpsLog(value = "查询所有平台所有角色", type = OpsLogType.SELECT)
  public ResponseModel selectAllPlatsAndRoles(@RequestBody RequestModel<Void> requestModel) {

    List<PlatRoleVO> list = platService.selectAllPlatsAndRoles();
    return new ResponseModel<>("查询所有平台所有角色", list);

  }
}
