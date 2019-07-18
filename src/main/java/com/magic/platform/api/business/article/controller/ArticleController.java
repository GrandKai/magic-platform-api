package com.magic.platform.api.business.article.controller;

import com.magic.platform.api.business.article.model.FileUploadResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.io.File;
import java.io.IOException;
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
  public FileUploadResult uploadEditorImage(@RequestParam("upload") MultipartFile upload) {

    FileUploadResult result = new FileUploadResult();

    try {
      String fileName = this.upload(upload);

      String fileLocation = "http://localhost:8088/images/" + fileName;

      result.setUploaded(1);
      result.setFileName(fileName);
      result.setUrl(fileLocation);

    } catch (Exception e) {
      log.error("上传图片异常：", e);
      result.setUploaded(0);
      result.setError(new FileUploadResult.Error(e.getMessage()));
    }

    return result;
  }

  /**
   *
   * upload 即图片文件可直接转换成字节数组存数据库或其他云空间
   * 处理图片，返回一个图片的URL
   * @param file
   * @return
   * @throws IOException
   */
  private String upload(MultipartFile file) throws IOException {
    String fileName = System.currentTimeMillis() + file.getOriginalFilename();
    //拿到输出流，同时重命名上传的文件
    String filePath = "f:/upload/images/" + fileName;

    File localFile = new File(filePath);
    file.transferTo(localFile);

    return fileName;
  }
}
