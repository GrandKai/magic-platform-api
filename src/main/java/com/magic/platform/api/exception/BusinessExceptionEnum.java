package com.magic.platform.api.exception;

import com.magic.platform.core.exception.CustomException;
import lombok.Getter;

/**
 * @Author: zyn
 * @Description:
 * @Date: Created in 2018-09-21 13:13
 * @Modified By:
 */
@Getter
public enum BusinessExceptionEnum {
  // 系统已存在
  SYSTEM_ALREADY_EXIST(new CustomException(BusinessExceptionCode.SYSTEM_ALREADY_EXIST.getKey(), BusinessExceptionCode.SYSTEM_ALREADY_EXIST.getValue())),
  ;
  private CustomException exception;

  BusinessExceptionEnum(CustomException exception) {
    this.exception = exception;
  }

}
