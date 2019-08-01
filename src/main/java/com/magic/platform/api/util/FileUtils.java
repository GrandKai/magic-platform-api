package com.magic.platform.api.util;

import com.google.common.io.Files;
import com.magic.platform.api.business.article.model.FileUploadResult;
import com.magic.platform.util.UUIDUtils;
import java.io.File;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author: Administrator
 * @Description:
 * @Date: Created in 2019-07-19 22:22
 * @Modified By:
 */
@Slf4j
public class FileUtils {

  /**
   *
   * upload 即图片文件可直接转换成字节数组存数据库或其他云空间
   * 处理图片，返回一个图片的URL
   * @param file
   * @return
   * @throws IOException
   */
  private static String transferTo(MultipartFile file) throws IOException {
    String fileName = UUIDUtils.uuid() + "." + Files.getFileExtension(file.getOriginalFilename());
    //拿到输出流，同时重命名上传的文件
    String filePath = "f:/upload/images/" + fileName;

    File localFile = new File(filePath);
    file.transferTo(localFile);

    return fileName;
  }

  public static FileUploadResult upload(MultipartFile file) {

    FileUploadResult result = new FileUploadResult();
    try {
      String fileName = FileUtils.transferTo(file);
      String fileLocation = "http://172.27.4.71:9088/images/" + fileName;

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

}
