package com.magic.platform.api.business.role.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author: GrandKai
 * @create: 2018-10-02 19:29
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("角色添加model")
public class RoleModel {
    @ApiModelProperty("角色名称")
    private String name;
    @ApiModelProperty("角色描述")
    private String description;
    @ApiModelProperty("显示顺序")
    private Short sortNumber;
    @ApiModelProperty("所属系统")
    private String platId;

    @ApiModelProperty("功能权限集合")
    private List<String> authorities;
}
