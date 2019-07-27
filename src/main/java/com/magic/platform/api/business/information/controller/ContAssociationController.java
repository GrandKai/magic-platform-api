package com.magic.platform.api.business.information.controller;

import com.magic.platform.api.business.information.mapper.custom.entity.ContInformationSimpleVO;
import com.magic.platform.api.business.information.model.ContAssociationQueryModel;
import com.magic.platform.api.business.information.service.ContAssociationService;
import com.magic.platform.core.anotation.OpsLog;
import com.magic.platform.core.anotation.OpsLogType;
import com.magic.platform.core.model.RequestModel;
import com.magic.platform.core.model.ResponseModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Administrator
 * @Description:
 * @Date: Created in 2019-07-27 14:14
 * @Modified By:
 */
@Slf4j
@Api(tags = "关联内容相关操作")
@RestController
@RequestMapping("association")
public class ContAssociationController {

  @Autowired
  private ContAssociationService contAssociationService;


  @PostMapping("list/{sourceType}")
  @ApiOperation(value = "获取关联资讯列表")
  @OpsLog(value = "获取关联资讯列表", type = OpsLogType.SELECT)
  public ResponseModel list(@RequestBody RequestModel<ContAssociationQueryModel> requestModel,
      @PathVariable(name = "sourceType", required = false) String sourceType) {
    ContAssociationQueryModel model = requestModel.getContent();
    model.setSourceType(sourceType);

    List<ContInformationSimpleVO> list = contAssociationService.selectContInformationSimpleVOList(model);
    return new ResponseModel<>("查询成功！", list);
  }
}
