package com.magic.platform.api.business.operation.mapper.custom.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author: GrandKai
 * @create: 2018-10-03 16:28
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GrantedOperation {

    private String authorityId;
    private String operationId;
    private String operationName;

    private String menuId;
    private String menuName;
    private String type;

    private Date createTime;
    private Date updateTime;
    private Short sortNumber;
}
