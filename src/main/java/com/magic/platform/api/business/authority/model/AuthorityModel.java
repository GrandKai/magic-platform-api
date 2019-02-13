package com.magic.platform.api.business.authority.model;

import com.magic.platform.api.business.operation.mapper.custom.entity.OperationVO;
import com.magic.platform.entity.mapper.build.entity.Authority;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author: GrandKai
 * @create: 2018-10-03 08:11
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("权限添加model")
public class AuthorityModel extends Authority {

    @ApiModelProperty("权限对应的菜单操作url集合")
    private List<OperationVO> operations;
}
