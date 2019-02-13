package com.magic.platform.api.business.permission.mapper.custom.dao;

import com.magic.platform.api.business.operation.mapper.custom.entity.OperationVO;
import com.magic.platform.api.business.permission.mapper.custom.entity.PermissionVO;
import com.magic.platform.api.business.permission.model.PermissionQueryModel;
import com.magic.platform.entity.mapper.build.BaseMapper;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * @Author: zyn
 * @Description:
 * @Date: Created in 2018-09-02 21:21
 * @Modified By:
 */
public interface PermissionVOMapper extends BaseMapper<PermissionVO> {

  /**
   * 获取所有授予的操作 url 信息
   * @param userName
   * @return
   */
  List<String> selectAllGrantedPermissions(@Param(value = "userName") String userName);

  OperationVO getOperationVO(@Param(value = "id") String id);

  List<OperationVO> selectEntityList(PermissionQueryModel model);
}
