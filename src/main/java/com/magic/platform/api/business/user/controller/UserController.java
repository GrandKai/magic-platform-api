package com.magic.platform.api.business.user.controller;

import com.github.pagehelper.PageInfo;
import com.magic.platform.api.business.user.model.UserQueryModel;
import com.magic.platform.api.business.user.model.UserRolesModel;
import com.magic.platform.api.business.user.service.UserService;
import com.magic.platform.api.util.ExcelUtil;
import com.magic.platform.core.anotation.OpsLog;
import com.magic.platform.core.anotation.OpsLogType;
import com.magic.platform.core.constant.Constant;
import com.magic.platform.core.exception.CustomException;
import com.magic.platform.core.model.RequestModel;
import com.magic.platform.core.model.ResponseModel;
import com.magic.platform.core.util.Objects;
import com.magic.platform.entity.mapper.build.entity.UserRole;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 默认根路由为【分页查询】页面
 * @author: GrandKai
 * @create: 2018-05-06 7:56 PM
 */
@Slf4j
@Api(tags = "用户相关信息")
@RestController
@RequestMapping("user")
public class UserController {

  @Autowired
  private UserService userService;

  @PostMapping
  @ApiOperation(value = "账号分页查询")
  @OpsLog(value = "账号分页查询", type = OpsLogType.SELECT)
  public ResponseModel page(@RequestBody RequestModel<UserQueryModel> requestModel) {

    PageInfo pageInfo = userService.selectEntityPage(requestModel);
    return new ResponseModel<>(pageInfo);
  }

  @PostMapping(value = "stop")
  @ApiOperation(value = "账号启停")
  @OpsLog(value = "账号启停", type = OpsLogType.STOP)
  public ResponseModel stopUser(@RequestBody RequestModel<UserQueryModel> requestModel) {
    UserQueryModel model = requestModel.getContent();

    Objects.requireNonNull(model, "入参不能为空");
    Objects.requireNonNull(model.getUserId(), "用户id不能为空");
    Objects.requireNonNull(model.getIsEnabled(), "启(停)用标识不能为空");

    ResponseModel responseModel = new ResponseModel();
    if (StringUtils.equals("1", model.getIsEnabled())) {
      responseModel.setMessage("启用成功！");
    } else {
      responseModel.setMessage("停用成功！");
    }

    userService.stopUser(model);
    return responseModel;
  }

  @PostMapping("check/exist")
  @ApiOperation(value = "查看用户账号是否重复")
  @OpsLog(value = "查看用户账号是否重复", type = OpsLogType.CHECK)
  public ResponseModel checkExist(@RequestBody RequestModel<String> requestModel) {

    String userName = requestModel.getContent();
    Objects.requireNonNull(userName, "用户账户不能为空");
    userService.checkExist(userName);

    return new ResponseModel("该用户账号可以使用");
  }

  @PostMapping("check/update/exist")
  @ApiOperation(value = "检测【修改】用户账号是否存在")
  @OpsLog(value = "检测【修改】用户账号是否存在", type = OpsLogType.CHECK)
  public ResponseModel checkUpdateExist(@RequestBody RequestModel<UserQueryModel> requestModel) {
    // 如果修改角色名 判断角色是否存在
    UserQueryModel model = requestModel.getContent();
    Objects.requireNonNull(model, "请求对象不能为空");
    Objects.requireNonNull(model.getUserId(), "用户id不能为空");
    Objects.requireNonNull(model.getUserName(), "用户名不能为空");
    userService.checkUpdateExist(model);
    return new ResponseModel();
  }

  @PostMapping("add")
  @ApiOperation(value = "添加用户账号信息")
  @OpsLog(value = "添加用户账号信息", type = OpsLogType.ADD)
  public ResponseModel add(@RequestBody RequestModel<UserQueryModel> requestModel) {

      UserQueryModel model = requestModel.getContent();

      Objects.requireNonNull(model.getUserName(), "用户账户不能为空");
      Objects.requireNonNull(model.getNickName(), "用户昵称不能为空");
      Objects.requireNonNull(model.getPassword(), "登录密码不能为空");

      userService.addUser(model);
      return new ResponseModel("账户添加成功");
  }

  @PostMapping(value = "/update")
  @ApiOperation(value = "修改账号")
  @OpsLog(value = "修改账号", type = OpsLogType.UPDATE)
  public ResponseModel updateUser(@RequestBody RequestModel<UserQueryModel> requestModel) {
    UserQueryModel model = requestModel.getContent();

    Objects.requireNonNull(model, "入参不能为空");
    Objects.requireNonNull(model.getUserId(), "用户id不能为空");
    Objects.requireNonNull(model.getUserName(), "用户名不能为空");

    userService.updateUser(model);
    return new ResponseModel("修改账号成功！");
  }

  @PostMapping("delete")
  @ApiOperation(value = "逻辑删除用户信息")
  @OpsLog(value = "逻辑删除用户信息", type = OpsLogType.DELETE)
  public ResponseModel delete(@RequestBody RequestModel<String> requestModel){

    String userId = requestModel.getContent();
    Objects.requireNonNull(userId, "用户id不能为空");
    userService.deleteUser(userId);

    return new ResponseModel("删除用户成功");

  }

  @PostMapping(value = "reset/password")
  @OpsLog(value = "密码重置", type = OpsLogType.RESET)
  @ApiOperation(value = "密码重置")
  public ResponseModel resetUserPassword(@RequestBody RequestModel<UserQueryModel> requestModel) {
    UserQueryModel model = requestModel.getContent();

    Objects.requireNonNull(model, "入参不能为空");
    Objects.requireNonNull(model.getUserId(), "用户id不能为空");
    Objects.requireNonNull(model.getPassword(), "用户密码不能为空");

    userService.resetUserPassword(model);
    return new ResponseModel("密码重置成功！");
  }

  @PostMapping("add/roles")
  @ApiOperation("设置用户角色信息")
  @OpsLog(value = "设置用户角色信息", type = {OpsLogType.ADD, OpsLogType.UPDATE})
  public ResponseModel addUserRole(@RequestBody RequestModel<UserRolesModel> requestModel) {

    UserRolesModel model = requestModel.getContent();
    Objects.requireNonNull(model.getUserId(), "用户id不能为空");

    userService.addUserRoles(model);

    return new ResponseModel("用户角色信息设置成功！");
  }


  @PostMapping("select/roles")
  @ApiOperation(value = "根据用户id获取当前用户的角色信息")
  @OpsLog(value = "根据用户id获取当前用户的角色信息", type = OpsLogType.SELECT)
  public ResponseModel selectUser(@RequestBody RequestModel<String> requestModel) {

    String userId = requestModel.getContent();
    Objects.requireNonNull(userId, "用户id不能为空");

    List<UserRole> list = userService.selectUserRoles(userId);

    return new ResponseModel<>(list);

  }


  @PostMapping(value = "/page/right/set")
  @ApiOperation(value = "分页查询已分配账号的 staffInfo 列表")
  public ResponseModel selectRoleUsersRightPageSetByRoleId(@RequestBody RequestModel<UserQueryModel> requestModel) {
    PageInfo pageInfo = userService.selectRoleUsersRightPageSetByRoleId(requestModel);
    return new ResponseModel(pageInfo);

  }


  @PostMapping(value = "/page/left/unset")
  @ApiOperation(value = "分页查询未分配账号的 staffInfo 列表")
  public ResponseModel selectRoleUsersPageLeftUnset(@RequestBody RequestModel<UserQueryModel> requestModel) {
    PageInfo pageInfo = userService.selectRoleUsersLeftPageUnset(requestModel);
    return new ResponseModel(pageInfo);
  }
  @PostMapping("list/{poolCode}")
  @ApiOperation(value = "")
  public void list(@PathVariable(value = "poolCode") String poolCode,
      @RequestBody RequestModel<UserQueryModel> requestModel, HttpServletResponse response) {


    List<Map> list = this.userService.selectEntityListMap(requestModel);

    try {
      // 题头
      List<String[]> headNameList = this.userService.getExcelHeader(poolCode);

      String sheetName = this.userService.getExcelSheetName(poolCode);
      // 构造下拉数据
      List<Map<String, Object>> dropDownList = null;

      // 生成导入模板
      HSSFWorkbook wb = ExcelUtil.exportCustomExcel(sheetName, headNameList, list, dropDownList);

      // 设置导出格式为Excel
      response.setContentType("application/vnd.ms-excel");
      // 设置文件名并解决中文乱码

      String fileName = URLEncoder.encode(sheetName, "UTF-8");
      response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xls");
      response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");

      response.setHeader("Pragma", "No-cache");
      response.setHeader("Cache-Control", "No-cache");
      response.setDateHeader("Expires", 0);

      // 声明输出流
      OutputStream outputStream = response.getOutputStream();
      // 输出文件
      wb.write(outputStream);
      outputStream.flush();
      outputStream.close();
    } catch (Exception e) {
      log.error("导出数据异常:", e);
      throw new CustomException(Constant.EXCEPTION_CODE, "导出数据异常");
    }
  }
}
