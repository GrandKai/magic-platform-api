package com.magic.platform.api.business.article.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Data;

/**
 * @Author: zyn
 * @Description:
 * @Date: Created in 2018-11-14 15:15
 * @Modified By:
 */
@Data
@JsonInclude(Include.NON_NULL)
public class FileUploadResult {

  private Integer uploaded;
  private String fileName;
  private String url;

  private Error error;

  @Data
  public static class Error {

    public Error(String message) {
      this.message = message;
    }

    private String message;
  }

}

