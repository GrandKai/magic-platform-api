package com.magic.platform.api.business.organization.controller;

import com.magic.platform.api.business.organization.mapper.custom.entity.OrganizationVO;
import com.magic.platform.api.business.organization.model.OrganizationQueryModel;
import com.magic.platform.api.business.organization.service.OrganizationService;
import com.magic.platform.core.util.Objects;
import com.magic.platform.core.anotation.OpsLog;
import com.magic.platform.core.anotation.OpsLogType;
import com.magic.platform.core.model.RequestModel;
import com.magic.platform.core.model.ResponseModel;
import com.magic.platform.entity.mapper.build.entity.Organization;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: GrandKai
 * @create: 2018-10-07 22:48
 */
@Api(tags = "组织机构相关操作")
@RestController
@RequestMapping("org")
public class OrganizationController {

    @Autowired
    private OrganizationService organizationService;


    @PostMapping("add")
    @ApiOperation(value = "新增组织机构")
    @OpsLog(value = "新增组织机构", type = OpsLogType.ADD)
    public ResponseModel add(@RequestBody RequestModel<Organization> requestModel) {
        Organization param = requestModel.getContent();

        Objects.requireNonNull(param, "请求对象不能为空");

        Organization entity = organizationService.addEntity(param);
        return new ResponseModel<>("新增组织机构成功！", entity);
    }

    @PostMapping("update")
    @ApiOperation(value = "修改组织机构")
    @OpsLog(value = "修改组织机构", type = OpsLogType.UPDATE)
    public ResponseModel update(@RequestBody RequestModel<Organization> requestModel) {
        Organization param = requestModel.getContent();
        Objects.requireNonNull(param.getId(), "组织机构id不能为空");
//        Objects.requireNonNull(param.getPlatId(), "系统id不能为空");

        Organization entity = organizationService.updateEntity(param);
        return new ResponseModel<>("修改组织机构成功！", entity);
    }

    @PostMapping("delete")
    @ApiOperation(value = "删除组织机构")
    @OpsLog(value = "删除组织机构", type = OpsLogType.DELETE)
    public ResponseModel delete(@RequestBody RequestModel<String> requestModel) {
        String id = requestModel.getContent();
        Objects.requireNonNull(id, "组织机构id不能为空");

        organizationService.deleteEntity(id);
        return new ResponseModel("删除组织机构成功！");
    }

    @PostMapping("list")
    @ApiOperation(value = "查询组织机构列表")
    @OpsLog(value = "查询组织机构列表", type = OpsLogType.SELECT)
    public ResponseModel list(@RequestBody RequestModel<OrganizationQueryModel> requestModel) {
        OrganizationQueryModel model = requestModel.getContent();
        List<OrganizationVO> list = organizationService.selectEntityList(model);
        return new ResponseModel<>("查询成功", list);
    }


    @PostMapping(value = "check/status")
    @ApiOperation(value = "查询组织机构是否可以删除")
    @OpsLog(value = "查询组织机构是否可以删除", type = OpsLogType.SELECT)
    public ResponseModel checkEntityStatus(@RequestBody RequestModel<String> requestModel) {

        String id = requestModel.getContent();
        Objects.requireNonNull(id, "组织机构id不能为空");

        organizationService.checkEntityStatus(id);
        return new ResponseModel();
    }
}
