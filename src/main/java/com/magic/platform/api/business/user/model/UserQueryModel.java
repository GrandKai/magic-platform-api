package com.magic.platform.api.business.user.model;

import java.util.Date;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserQueryModel {

    /**
     * 树层级，0表示根节点
     */
    private String level;

    /**
     * 账号状态
     */
    private String isEnabled;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 角色id
     */
    private String roleId;

    /**
     * 密码
     */
    private String password;

    private Date startTime;
    private Date endTime;

    private String userName;
    private String nickName;

    /**
     * 父组织机构id（该字段主要为了查询所有组织机构下的id集合）
     */
    private String organizationId;

    /**
     * 组织机构下的所有 children id 集合
     */
    private List<String> list;
}
