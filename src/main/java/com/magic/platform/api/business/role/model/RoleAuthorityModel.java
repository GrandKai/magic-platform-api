package com.magic.platform.api.business.role.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author: GrandKai
 * @create: 2018-10-04 10:56
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("角色权限model")
public class RoleAuthorityModel {
    @ApiModelProperty("角色id")
    private String roleId;
    @ApiModelProperty("角色对应的权限列表")
    private List<String> authorities;
}
