package com.magic.platform.api.framework.exception;

import javafx.util.Pair;

/**
 * @Author: zyn
 * @Description:
 * @Date: Created in 2018-09-21 13:13
 * @Modified By:
 */
public interface BusinessExceptionCode {
  Pair<Integer, String> SYSTEM_ALREADY_EXIST = new Pair<>(4001, "系统已存在！");

}
