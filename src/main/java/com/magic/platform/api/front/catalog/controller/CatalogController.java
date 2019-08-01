package com.magic.platform.api.front.catalog.controller;

import com.magic.platform.api.front.catalog.dto.CatalogDto;
import com.magic.platform.api.front.catalog.service.CatalogService;
import com.magic.platform.core.anotation.OpsLog;
import com.magic.platform.core.anotation.OpsLogType;
import com.magic.platform.core.model.RequestModel;
import com.magic.platform.core.model.ResponseModel;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Administrator
 * @Description:
 * @Date: Created in 2019-07-31 22:22
 * @Modified By:
 */
@RestController
@RequestMapping("front/catalog")
public class CatalogController {

  @Autowired
  private CatalogService catalogService;

  @PostMapping("list")
  @OpsLog(value = "手机-查询栏目列表接口", type = OpsLogType.SELECT)
  @ApiOperation(value = "手机-查询栏目列表接口")
  public ResponseModel list(@RequestBody RequestModel requestModel) {

    List<CatalogDto> list = catalogService.selectCatalogList();
    return new ResponseModel<>("栏目列表查询成功", list);
  }
}
