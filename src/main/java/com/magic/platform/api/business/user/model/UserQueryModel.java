package com.magic.platform.api.business.user.model;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserQueryModel {

    /**
     * 账号状态
     */
    private String isEnabled;

    /**
     * 账号主键
     */
    private String userId;

    /**
     * 密码
     */
    private String password;

    private Date startTime;
    private Date endTime;

    private String userName;
    private String nickName;

}
