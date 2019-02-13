package com.magic.platform.api.business.operation.mapper.custom.dao;

import com.magic.platform.api.business.operation.mapper.custom.entity.GrantedOperation;
import com.magic.platform.api.business.operation.mapper.custom.entity.OperationVO;
import com.magic.platform.api.business.operation.model.GrantedOperationQueryModel;
import com.magic.platform.api.business.operation.model.OperationQueryModel;
import com.magic.platform.entity.mapper.build.BaseMapper;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * @Author: zyn
 * @Description:
 * @Date: Created in 2018-09-29 09:09
 * @Modified By:
 */
public interface OperationVOMapper extends BaseMapper<OperationVO> {

  /**
   * 根据平台 id 获取所有操作相关信息
   */
  List<OperationVO> selectEntityList(OperationQueryModel model);

  OperationVO getOperationVO(@Param(value = "id") String id);

  List<GrantedOperation> selectGrantedOperationList(GrantedOperationQueryModel model);
}
