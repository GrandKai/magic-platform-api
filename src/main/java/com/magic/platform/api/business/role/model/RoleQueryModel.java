package com.magic.platform.api.business.role.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: GrandKai
 * @create: 2018-10-02 09:41
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleQueryModel {
    private String name;
    private String platId;
    private String id;
    private String parentId;
}
