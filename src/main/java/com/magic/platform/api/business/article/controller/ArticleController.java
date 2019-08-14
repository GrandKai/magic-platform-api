package com.magic.platform.api.business.article.controller;

import com.magic.platform.api.business.article.model.FileUploadResult;
import com.magic.platform.api.framework.util.FileUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author: zyn
 * @Description:
 * @Date: Created in 2018-11-14 13:13
 * @Modified By:
 */
@Slf4j
@Api(tags = "文章操作相关")
@RestController
@RequestMapping("article")
public class ArticleController {


  @ApiOperation(value = "富文本上传图片", notes = "<br/>富文本上传图片", httpMethod = "POST")
  @PostMapping(value = "upload")
  public FileUploadResult uploadEditorImage(@RequestParam("upload") MultipartFile file) {

    FileUploadResult result = FileUtils.upload(file);
    return result;
  }
}
