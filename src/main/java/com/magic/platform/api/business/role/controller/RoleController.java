package com.magic.platform.api.business.role.controller;

import com.github.pagehelper.PageInfo;
import com.magic.platform.api.business.role.mapper.custom.entity.RoleVO;
import com.magic.platform.api.business.role.model.RoleAuthorityModel;
import com.magic.platform.api.business.role.model.RoleModel;
import com.magic.platform.api.business.role.model.RoleQueryModel;
import com.magic.platform.api.business.role.service.RoleService;
import com.magic.platform.core.anotation.OpsLog;
import com.magic.platform.core.anotation.OpsLogType;
import com.magic.platform.core.model.RequestModel;
import com.magic.platform.core.model.ResponseModel;
import com.magic.platform.core.util.Objects;
import com.magic.platform.entity.mapper.build.entity.Role;
import com.magic.platform.entity.mapper.build.entity.RoleAuthority;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: GrandKai
 * @create: 2018-10-02 09:37
 */
@Api(tags = "角色相关操作")
@RestController
@RequestMapping("role")
public class RoleController {

  @Autowired
  private RoleService roleService;

  @PostMapping
  @ApiOperation(value = "获取【角色】分页信息")
  @OpsLog(value = "获取【角色】分页信息", type = OpsLogType.SELECT)
  public ResponseModel page(@RequestBody RequestModel<RoleQueryModel> requestModel) {
    PageInfo pageInfo = roleService.getEntityPage(requestModel);
    return new ResponseModel<>(pageInfo);
  }

  @PostMapping("list")
  @ApiOperation(value = "查询角色列表")
  @OpsLog(value = "查询角色列表", type = OpsLogType.SELECT)
  public ResponseModel list(@RequestBody RequestModel<RoleQueryModel> requestModel) {
    List<RoleVO> list = roleService.selectEntityList(requestModel.getContent());

    return new ResponseModel(list);
  }

  @PostMapping("authority/list")
  @ApiOperation(value = "查询角色对应的权限列表")
  @OpsLog(value = "查询角色对应的权限列表", type = OpsLogType.SELECT)
  public ResponseModel selectAuthorityList(@RequestBody RequestModel<String> requestModel) {

    String id = requestModel.getContent();
    Objects.requireNonNull(id, "角色id不能为空");
    List<RoleAuthority> list = roleService.selectRoleAuthorityList(id);

    return new ResponseModel<>("查询角色权限列表成功", list);
  }

  @PostMapping("add")
  @ApiOperation(value = "新增角色")
  @OpsLog(value = "新增角色", type = OpsLogType.ADD)
  public ResponseModel add(@RequestBody RequestModel<RoleModel> requestModel) {
    RoleModel param = requestModel.getContent();
    Objects.requireNonNull(param, "请求对象不能为空");

    Objects.requireNonNull(param.getPlatId(), "系统id不能为空");

    roleService.addEntity(param);
    return new ResponseModel("新增角色成功！");
  }

  @PostMapping("authority/add")
  @ApiOperation(value = "新增角色权限")
  @OpsLog(value = "新增角色权限", type = {OpsLogType.DELETE, OpsLogType.ADD})
  public ResponseModel addRoleAuthority(@RequestBody RequestModel<RoleAuthorityModel> requestModel) {
    RoleAuthorityModel param = requestModel.getContent();
    Objects.requireNonNull(param, "请求对象不能为空");

    Objects.requireNonNull(param.getRoleId(), "角色id不能为空");

    roleService.addRoleAuthority(param);
    return new ResponseModel("角色权限设置成功！");
  }

  @PostMapping("update")
  @ApiOperation(value = "修改角色")
  @OpsLog(value = "修改角色", type = OpsLogType.UPDATE)
  public ResponseModel update(@RequestBody RequestModel<Role> requestModel) {
    Role param = requestModel.getContent();

    Objects.requireNonNull(param, "请求对象不能为空");
    Objects.requireNonNull(param.getId(), "角色id不能为空");

    roleService.updateEntity(param);
    return new ResponseModel("修改角色成功！");
  }

  @PostMapping("delete")
  @ApiOperation(value = "删除角色")
  @OpsLog(value = "删除角色", type = OpsLogType.DELETE)
  public ResponseModel delete(@RequestBody RequestModel<String> requestModel) {
    String id = requestModel.getContent();
    Objects.requireNonNull(id, "角色id不能为空");
    roleService.deleteEntity(id);

    return new ResponseModel();
  }

  @PostMapping("check/exist")
  @ApiOperation(value = "检测角色是否存在")
  @OpsLog(value = "检测角色是否存在", type = OpsLogType.CHECK)
  public ResponseModel checkExist(@RequestBody RequestModel<String> requestModel) {
    // 如果修改角色名 判断角色是否存在
    String name = requestModel.getContent();
    Objects.requireNonNull(name, "角色名称不能为空");
    roleService.checkExist(name);
    return new ResponseModel();
  }

  @PostMapping("check/update/exist")
  @ApiOperation(value = "检测【修改】角色是否存在")
  @OpsLog(value = "检测【修改】角色是否存在", type = OpsLogType.CHECK)
  public ResponseModel checkUpdateExist(@RequestBody RequestModel<RoleVO> requestModel) {
    // 如果修改角色名 判断角色是否存在
    RoleVO model = requestModel.getContent();
    Objects.requireNonNull(model, "请求对象不能为空");
    Objects.requireNonNull(model.getId(), "角色id不能为空");
    Objects.requireNonNull(model.getName(), "角色名称不能为空");
    roleService.checkUpdateExist(model);
    return new ResponseModel();
  }
}
