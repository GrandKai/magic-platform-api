package com.magic.platform.api.business.information.controller;

import com.github.pagehelper.PageInfo;
import com.magic.platform.api.business.article.model.FileUploadResult;
import com.magic.platform.api.business.information.mapper.custom.entity.ContInformationVO;
import com.magic.platform.api.business.information.model.ContInformationQueryModel;
import com.magic.platform.api.business.information.service.ContInformationService;
import com.magic.platform.api.business.user.model.UserQueryModel;
import com.magic.platform.api.util.FileUtils;
import com.magic.platform.core.anotation.OpsLog;
import com.magic.platform.core.anotation.OpsLogType;
import com.magic.platform.core.model.RequestModel;
import com.magic.platform.core.model.ResponseModel;
import com.magic.platform.core.util.Objects;
import com.magic.platform.entity.mapper.build.entity.ContInformation;
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
 * @Date: Created in 2019-07-19 23:23
 * @Modified By:
 */
@Slf4j
@Api(tags = "文章相关操作")
@RestController
@RequestMapping("information")
public class ContInformationController {

  @Autowired
  public ContInformationService contInformationService;

  @PostMapping
  @ApiOperation(value = "账号分页查询")
  @OpsLog(value = "账号分页查询", type = OpsLogType.SELECT)
  public ResponseModel page(@RequestBody RequestModel<ContInformationQueryModel> requestModel) {

    PageInfo pageInfo = contInformationService.selectEntityPage(requestModel);
    return new ResponseModel<>(pageInfo);
  }

  @PostMapping("add")
  @ApiOperation(value = "新增文章")
  @OpsLog(value = "新增文章", type = OpsLogType.ADD)
  public ResponseModel add(@RequestBody RequestModel<ContInformation> requestModel) {
    ContInformation param = requestModel.getContent();

    Objects.requireNonNull(param, "请求对象不能为空");
    ContInformation entity = contInformationService.addEntity(param);
    return new ResponseModel<>("新增文章成功！", entity);
  }

  @PostMapping("update")
  @ApiOperation(value = "修改文章")
  @OpsLog(value = "修改文章", type = OpsLogType.UPDATE)
  public ResponseModel update(@RequestBody RequestModel<ContInformation> requestModel) {
    ContInformation param = requestModel.getContent();

    Objects.requireNonNull(param.getId(), "文章id不能为空");
    ContInformation entity = contInformationService.updateEntity(param);
    return new ResponseModel<>("修改文章成功！", entity);
  }

  @PostMapping("delete")
  @ApiOperation(value = "删除文章")
  @OpsLog(value = "删除文章", type = OpsLogType.DELETE)
  public ResponseModel delete(@RequestBody RequestModel<String> requestModel) {
    String id = requestModel.getContent();
    Objects.requireNonNull(id, "文章id不能为空");

    contInformationService.deleteEntity(id);
    return new ResponseModel("删除文章成功！");
  }

  @PostMapping("list")
  @ApiOperation(value = "获取文章列表")
  @OpsLog(value = "获取文章列表", type = OpsLogType.SELECT)
  public ResponseModel list(@RequestBody RequestModel<ContInformationQueryModel> requestModel) {
    ContInformationQueryModel model = requestModel.getContent();
    List<ContInformationVO> list = contInformationService.selectEntityList(model);
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

    return new ResponseModel<>("上传文件成功!", result);
  }


}
