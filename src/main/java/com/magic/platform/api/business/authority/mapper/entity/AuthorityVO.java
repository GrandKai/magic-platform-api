package com.magic.platform.api.business.authority.mapper.entity;

import com.magic.platform.entity.mapper.build.entity.Authority;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: GrandKai
 * @create: 2018-10-01 23:38
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorityVO extends Authority {
    private String platName;
}
