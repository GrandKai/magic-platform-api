package com.magic.platform.api.business.role.model;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleUsersModel {

    private List<String> userIds;
    private String roleId;

}
