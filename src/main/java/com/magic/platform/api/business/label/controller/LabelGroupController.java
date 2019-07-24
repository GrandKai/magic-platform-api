package com.magic.platform.api.business.label.controller;

import com.github.pagehelper.PageInfo;
import com.magic.platform.api.business.label.model.ContLabelGroupQueryModel;
import com.magic.platform.api.business.label.service.LabelGroupService;
import com.magic.platform.core.anotation.OpsLog;
import com.magic.platform.core.anotation.OpsLogType;
import com.magic.platform.core.model.RequestModel;
import com.magic.platform.core.model.ResponseModel;
import com.magic.platform.core.util.Objects;
import com.magic.platform.entity.mapper.build.entity.ContLabelGroup;
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
 * @Author: Administrator
 * @Description:
 * @Date: Created in 2019-07-24 20:20
 * @Modified By:
 */
@Slf4j
@Api(tags = "标签组相关操作")
@RestController
@RequestMapping("labelGroup")
public class LabelGroupController {

  @Autowired
  public LabelGroupService labelGroupService;

  @PostMapping
  @ApiOperation(value = "标签组分页查询")
  @OpsLog(value = "标签组分页查询", type = OpsLogType.SELECT)
  public ResponseModel page(@RequestBody RequestModel<ContLabelGroupQueryModel> requestModel) {

    PageInfo pageInfo = labelGroupService.selectEntityPage(requestModel);
    return new ResponseModel<>(pageInfo);
  }

  @PostMapping("get")
  @ApiOperation(value = "查询标签组")
  @OpsLog(value = "查询标签组", type = OpsLogType.SELECT)
  public ResponseModel get(@RequestBody RequestModel<String> requestModel) {
    String id = requestModel.getContent();

    Objects.requireNonNull(id, "标签组id不能为空！");
    ContLabelGroup entity = labelGroupService.getEntity(id);
    return new ResponseModel<>("查询标签组成功！", entity);
  }

  @PostMapping("add")
  @ApiOperation(value = "新增标签组")
  @OpsLog(value = "新增标签组", type = OpsLogType.ADD)
  public ResponseModel add(@RequestBody RequestModel<ContLabelGroup> requestModel) {
    ContLabelGroup param = requestModel.getContent();

    Objects.requireNonNull(param, "请求对象不能为空！");
    ContLabelGroup entity = labelGroupService.addEntity(param);
    return new ResponseModel<>("新增标签组成功！", entity);
  }

  @PostMapping("update")
  @ApiOperation(value = "修改标签组")
  @OpsLog(value = "修改标签组", type = OpsLogType.UPDATE)
  public ResponseModel update(@RequestBody RequestModel<ContLabelGroup> requestModel) {
    ContLabelGroup param = requestModel.getContent();

    Objects.requireNonNull(param.getId(), "标签组id不能为空！");
    ContLabelGroup entity = labelGroupService.updateEntity(param);
    return new ResponseModel<>("修改标签组成功！", entity);
  }

  @PostMapping("delete")
  @ApiOperation(value = "删除标签组")
  @OpsLog(value = "删除标签组", type = OpsLogType.DELETE)
  public ResponseModel delete(@RequestBody RequestModel<List<String>> requestModel) {
    List<String> ids = requestModel.getContent();
    Objects.requireNonNull(ids, "标签组id不能为空！");

    labelGroupService.deleteEntity(ids);
    return new ResponseModel("删除标签组成功！");
  }

}
