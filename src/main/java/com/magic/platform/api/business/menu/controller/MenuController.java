package com.magic.platform.api.business.menu.controller;

import com.magic.platform.api.business.menu.mapper.custom.entity.MenuVO;
import com.magic.platform.api.business.menu.model.MenuQueryModel;
import com.magic.platform.api.business.menu.service.MenuService;
import com.magic.platform.core.util.Objects;
import com.magic.platform.core.anotation.OpsLog;
import com.magic.platform.core.anotation.OpsLogType;
import com.magic.platform.core.model.RequestModel;
import com.magic.platform.core.model.ResponseModel;
import com.magic.platform.entity.mapper.build.entity.Menu;
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
 * @Date: Created in 2018-09-21 22:22
 * @Modified By:
 */
@Slf4j
@Api(tags = "菜单相关操作")
@RestController
@RequestMapping("menu")
public class MenuController {

  @Autowired
  public MenuService menuService;

  @PostMapping("add")
  @ApiOperation(value = "新增菜单")
  @OpsLog(value = "新增菜单", type = OpsLogType.ADD)
  public ResponseModel add(@RequestBody RequestModel<Menu> requestModel) {
    Menu param = requestModel.getContent();

    Objects.requireNonNull(param, "请求对象不能为空");
    Objects.requireNonNull(param.getPlatId(), "系统id不能为空");

    Menu menu = menuService.addEntity(param);
    return new ResponseModel<>("新增菜单成功！", menu);
  }

  @PostMapping("update")
  @ApiOperation(value = "修改菜单")
  @OpsLog(value = "修改菜单", type = OpsLogType.UPDATE)
  public ResponseModel update(@RequestBody RequestModel<Menu> requestModel) {
    Menu param = requestModel.getContent();
    Objects.requireNonNull(param.getId(), "菜单id不能为空");
    Objects.requireNonNull(param.getPlatId(), "系统id不能为空");

    Menu menu = menuService.updateEntity(param);
    return new ResponseModel<>("修改菜单成功！", menu);
  }

  @PostMapping("delete")
  @ApiOperation(value = "删除菜单")
  @OpsLog(value = "删除菜单", type = OpsLogType.DELETE)
  public ResponseModel delete(@RequestBody RequestModel<String> requestModel) {
    String id = requestModel.getContent();
    Objects.requireNonNull(id, "菜单id不能为空");

    menuService.deleteEntity(id);
    return new ResponseModel("删除菜单成功！");
  }

  @PostMapping("list")
  @ApiOperation(value = "根据平台id获取菜单列表")
  @OpsLog(value = "根据平台id获取菜单列表", type = OpsLogType.SELECT)
  public ResponseModel list(@RequestBody RequestModel<MenuQueryModel> requestModel) {
    MenuQueryModel model = requestModel.getContent();
    List<MenuVO> list = menuService.selectEntityList(model);
    return new ResponseModel<>("查询成功", list);
  }

}
