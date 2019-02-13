package com.magic.platform.api.business.user.dto;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: GrandKai
 * @create: 2018-10-05 15:30
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private String id;

    /**
     * 用户账号（登录
     */
    private String name;

    /**
     * 用户昵称
     */
    private String nickName;
    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;
}
