package com.magic.platform.api.business.authority.controller;

import com.github.pagehelper.PageInfo;
import com.magic.platform.api.business.authority.mapper.entity.AuthorityVO;
import com.magic.platform.api.business.authority.model.AuthorityModel;
import com.magic.platform.api.business.authority.model.AuthorityQueryModel;
import com.magic.platform.api.business.authority.service.AuthorityService;
import com.magic.platform.core.util.Objects;
import com.magic.platform.core.anotation.OpsLog;
import com.magic.platform.core.anotation.OpsLogType;
import com.magic.platform.core.model.RequestModel;
import com.magic.platform.core.model.ResponseModel;
import com.magic.platform.entity.mapper.build.entity.Authority;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author: GrandKai
 * @create: 2018-10-01 23:27
 */
@Api(tags = "权限相关操作")
@RestController
@RequestMapping("authority")
public class AuthorityController {
    @Autowired
    private AuthorityService authorityService;

    @PostMapping
    @ApiOperation(value = "权限分页查询")
    @OpsLog(value = "权限分页查询", type = OpsLogType.SELECT)
    public ResponseModel page(@RequestBody RequestModel<AuthorityQueryModel> requestModel) {
        PageInfo pageInfo = authorityService.getEntityPage(requestModel);
        return new ResponseModel<>(pageInfo);
    }

    @PostMapping("list")
    @ApiOperation(value = "查询权限列表")
    @OpsLog(value = "查询权限列表", type = OpsLogType.SELECT)
    public ResponseModel list(@RequestBody RequestModel<AuthorityQueryModel> requestModel) {
        AuthorityQueryModel model = requestModel.getContent();
        List<AuthorityVO> list = authorityService.selectEntityList(model);

        return new ResponseModel<>("查询权限列表成功", list);
    }

    @PostMapping("add")
    @ApiOperation(value = "新增权限")
    @OpsLog(value = "新增权限", type = OpsLogType.ADD)
    public ResponseModel add(@RequestBody RequestModel<AuthorityModel> requestModel) {
        AuthorityModel param = requestModel.getContent();
        Objects.requireNonNull(param, "请求对象不能为空");

        Objects.requireNonNull(param.getPlatId(), "系统id不能为空");

        authorityService.addEntity(param);
        return new ResponseModel("新增权限成功！");
    }

    @PostMapping("update")
    @ApiOperation(value = "修改权限信息")
    @OpsLog(value = "修改权限信息", type = OpsLogType.UPDATE)
    public ResponseModel update(@RequestBody RequestModel<Authority> requestModel) {
        Authority param = requestModel.getContent();

        Objects.requireNonNull(param, "请求对象不能为空");
        Objects.requireNonNull(param.getId(), "权限id不能为空");

        authorityService.updateEntity(param);
        return new ResponseModel("修改权限信息成功！");
    }

    @PostMapping("set")
    @ApiOperation(value = "修改权限设置")
    @OpsLog(value = "修改权限设置", type = {OpsLogType.DELETE, OpsLogType.ADD})
    public ResponseModel set(@RequestBody RequestModel<AuthorityModel> requestModel) {
        AuthorityModel param = requestModel.getContent();

        Objects.requireNonNull(param, "请求对象不能为空");
        Objects.requireNonNull(param.getId(), "权限id不能为空");

        authorityService.updateAuthority(param);
        return new ResponseModel("修改权限设置成功！");
    }

    @PostMapping("delete")
    @ApiOperation(value = "删除权限")
    @OpsLog(value = "删除权限", type = OpsLogType.DELETE)
    public ResponseModel delete(@RequestBody RequestModel<String> requestModel) {
        String id = requestModel.getContent();
        Objects.requireNonNull(id, "权限id不能为空");
        authorityService.deleteEntity(id);

        return new ResponseModel();
    }

    /**
     * 查询角色所有的操作集合（不查询菜单集合：防止菜单下所有的操作被选中，导致后添加的操作也被选中）
     * @param requestModel
     * @return
     */
    @PostMapping("granted/ids")
    @ApiOperation(value = "查询所有菜单id和操作id")
    @OpsLog(value = "查询所有菜单id和操作id", type = OpsLogType.SELECT)
    public ResponseModel selectAllGrantedOperationIds(@RequestBody RequestModel<String> requestModel) {
        String id = requestModel.getContent();
        Objects.requireNonNull(id, "权限id不能为空");
        List<String> list = authorityService.selectAllGrantedOperationIds(id);

        return new ResponseModel<>("查询权限菜单操作成功", list);
    }
}
