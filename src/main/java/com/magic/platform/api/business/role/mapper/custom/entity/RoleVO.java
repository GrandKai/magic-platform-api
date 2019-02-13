package com.magic.platform.api.business.role.mapper.custom.entity;

import com.magic.platform.entity.mapper.build.entity.Role;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: GrandKai
 * @create: 2018-10-02 09:43
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("角色查询VO")
public class RoleVO extends Role {

    @ApiModelProperty("平台名称")
    private String platName;
}
