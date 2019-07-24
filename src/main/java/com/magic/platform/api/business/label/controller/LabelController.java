package com.magic.platform.api.business.label.controller;

import com.github.pagehelper.PageInfo;
import com.magic.platform.api.business.label.model.ContLabelQueryModel;
import com.magic.platform.api.business.label.service.LabelService;
import com.magic.platform.core.anotation.OpsLog;
import com.magic.platform.core.anotation.OpsLogType;
import com.magic.platform.core.model.RequestModel;
import com.magic.platform.core.model.ResponseModel;
import com.magic.platform.core.util.Objects;
import com.magic.platform.entity.mapper.build.entity.ContLabel;
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
@Api(tags = "标签相关操作")
@RestController
@RequestMapping("label")
public class LabelController {

  @Autowired
  public LabelService labelService;

  @PostMapping
  @ApiOperation(value = "标签分页查询")
  @OpsLog(value = "标签分页查询", type = OpsLogType.SELECT)
  public ResponseModel page(@RequestBody RequestModel<ContLabelQueryModel> requestModel) {

    PageInfo pageInfo = labelService.selectEntityPage(requestModel);
    return new ResponseModel<>(pageInfo);
  }

  @PostMapping("get")
  @ApiOperation(value = "查询标签")
  @OpsLog(value = "查询标签", type = OpsLogType.SELECT)
  public ResponseModel get(@RequestBody RequestModel<String> requestModel) {
    String id = requestModel.getContent();

    Objects.requireNonNull(id, "标签id不能为空！");
    ContLabel entity = labelService.getEntity(id);
    return new ResponseModel<>("查询标签成功！", entity);
  }

  @PostMapping("add")
  @ApiOperation(value = "新增标签")
  @OpsLog(value = "新增标签", type = OpsLogType.ADD)
  public ResponseModel add(@RequestBody RequestModel<ContLabel> requestModel) {
    ContLabel param = requestModel.getContent();

    Objects.requireNonNull(param, "请求对象不能为空！");
    ContLabel entity = labelService.addEntity(param);
    return new ResponseModel<>("新增标签成功！", entity);
  }

  @PostMapping("update")
  @ApiOperation(value = "修改标签")
  @OpsLog(value = "修改标签", type = OpsLogType.UPDATE)
  public ResponseModel update(@RequestBody RequestModel<ContLabel> requestModel) {
    ContLabel param = requestModel.getContent();

    Objects.requireNonNull(param.getId(), "标签id不能为空！");
    ContLabel entity = labelService.updateEntity(param);
    return new ResponseModel<>("修改标签成功！", entity);
  }

  @PostMapping("delete")
  @ApiOperation(value = "删除标签")
  @OpsLog(value = "删除标签", type = OpsLogType.DELETE)
  public ResponseModel delete(@RequestBody RequestModel<List<String>> requestModel) {
    List<String> ids = requestModel.getContent();
    Objects.requireNonNull(ids, "标签id不能为空！");

    labelService.deleteEntity(ids);
    return new ResponseModel("删除标签成功！");
  }
}
