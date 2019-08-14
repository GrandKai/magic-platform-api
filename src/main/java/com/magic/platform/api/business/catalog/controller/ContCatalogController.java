package com.magic.platform.api.business.catalog.controller;

import com.magic.platform.api.business.article.model.FileUploadResult;
import com.magic.platform.api.business.catalog.mapper.entity.ContCatalogVO;
import com.magic.platform.api.business.catalog.model.ContCatalogQueryModel;
import com.magic.platform.api.business.catalog.service.ContCatalogService;
import com.magic.platform.api.framework.util.FileUtils;
import com.magic.platform.core.anotation.OpsLog;
import com.magic.platform.core.anotation.OpsLogType;
import com.magic.platform.core.model.RequestModel;
import com.magic.platform.core.model.ResponseModel;
import com.magic.platform.core.util.Objects;
import com.magic.platform.entity.mapper.build.entity.ContCatalog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import javax.imageio.ImageIO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author: Administrator
 * @Description:
 * @Date: Created in 2019-07-18 15:15
 * @Modified By:
 */
@Slf4j
@Api(tags = "栏目相关操作")
@RestController
@RequestMapping("catalog")
public class ContCatalogController {

  @Autowired
  public ContCatalogService contCatalogService;

  @PostMapping("add")
  @ApiOperation(value = "新增栏目")
  @OpsLog(value = "新增栏目", type = OpsLogType.ADD)
  public ResponseModel add(@RequestBody RequestModel<ContCatalog> requestModel) {
    ContCatalog param = requestModel.getContent();

    Objects.requireNonNull(param, "请求对象不能为空");
    ContCatalog entity = contCatalogService.addEntity(param);
    return new ResponseModel<>("新增栏目成功！", entity);
  }

  @PostMapping("update")
  @ApiOperation(value = "修改栏目")
  @OpsLog(value = "修改栏目", type = OpsLogType.UPDATE)
  public ResponseModel update(@RequestBody RequestModel<ContCatalog> requestModel) {
    ContCatalog param = requestModel.getContent();

    Objects.requireNonNull(param.getId(), "栏目id不能为空");
    ContCatalog entity = contCatalogService.updateEntity(param);
    return new ResponseModel<>("修改栏目成功！", entity);
  }

  @PostMapping("delete")
  @ApiOperation(value = "删除栏目")
  @OpsLog(value = "删除栏目", type = OpsLogType.DELETE)
  public ResponseModel delete(@RequestBody RequestModel<String> requestModel) {
    String id = requestModel.getContent();
    Objects.requireNonNull(id, "栏目id不能为空");

    contCatalogService.deleteEntity(id);
    return new ResponseModel("删除栏目成功！");
  }

  @PostMapping("list")
  @ApiOperation(value = "获取栏目列表")
  @OpsLog(value = "获取栏目列表", type = OpsLogType.SELECT)
  public ResponseModel list(@RequestBody RequestModel<ContCatalogQueryModel> requestModel) {
    ContCatalogQueryModel model = requestModel.getContent();
    List<ContCatalogVO> list = contCatalogService.selectEntityList(model);
    return new ResponseModel<>("查询成功", list);
  }


  @ApiOperation(value = "上传图片", notes = "<br/>上传图片", httpMethod = "POST")
  @PostMapping(value = "upload")
  public ResponseModel upload(@RequestParam("file") MultipartFile file) {
    FileUploadResult result = new FileUploadResult();
    try {
      InputStream inputStream = file.getInputStream();
      BufferedImage sourceImg = ImageIO.read(inputStream);
      int imgWidth = sourceImg.getWidth();
      int imgHeight = sourceImg.getHeight();

      int width = 400, height = 400;

      if (imgWidth != width && imgHeight != height) {
//        return new ResponseModel(500, "请上传400宽400高的图片，仅限一张图片");
      }

      result = FileUtils.upload(file);

    } catch (IOException e) {
      log.error("上传图片异常：", e);
      result.setUploaded(0);
      result.setError(new FileUploadResult.Error(e.getMessage()));

    }

    return new ResponseModel<>("上传文件成功！", result);
  }

}
