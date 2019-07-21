package com.magic.platform.api.business.dictionary.controller;

import com.github.pagehelper.PageInfo;
import com.magic.platform.api.business.dictionary.model.DicTypeQueryModel;
import com.magic.platform.api.business.dictionary.service.DictionaryTypeService;
import com.magic.platform.core.util.Objects;
import com.magic.platform.core.anotation.OpsLog;
import com.magic.platform.core.anotation.OpsLogType;
import com.magic.platform.core.model.RequestModel;
import com.magic.platform.core.model.ResponseModel;
import com.magic.platform.entity.mapper.build.entity.DictionaryType;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: zyn
 * @Description:
 * @Date: Created in 2018-09-30 09:09
 * @Modified By:
 */
@Slf4j
@Api(tags = "数据类型相关的接口")
@RestController
@RequestMapping("dictionary/type")
public class DictionaryTypeController {


    @Autowired
    private DictionaryTypeService dictionaryTypeService;

    @PostMapping
    @ApiOperation(value = "查询数据类型分页")
    @OpsLog(value = "查询数据类型分页", type = OpsLogType.SELECT)
    public ResponseModel page(@RequestBody RequestModel<DicTypeQueryModel> requestModel) {
        PageInfo pageInfo = dictionaryTypeService.selectEntityPage(requestModel);
        return new ResponseModel<>(pageInfo);
    }


    @PostMapping("list")
    @ApiOperation(value = "查询数据类型列表")
    @OpsLog(value = "查询数据类型列表", type = OpsLogType.SELECT)
    public ResponseModel list(@RequestBody RequestModel<DicTypeQueryModel> requestModel) {

        DicTypeQueryModel param = requestModel.getContent();
        Objects.requireNonNull(param, "请求对象不能为空");

        List<DictionaryType> list = dictionaryTypeService.selectEntityList(param);
        return new ResponseModel<>(list);
    }

    @PostMapping("add")
    @ApiOperation(value = "新增数据类型")
    @OpsLog(value = "新增数据类型", type = OpsLogType.ADD)
    public ResponseModel add(@RequestBody RequestModel<DictionaryType> requestModel) {

        dictionaryTypeService.addEntity(requestModel.getContent());
        return new ResponseModel("新增数据类型成功！");
    }

    @PostMapping("update")
    @ApiOperation(value = "修改数据类型")
    @OpsLog(value = "修改数据类型", type = OpsLogType.UPDATE)
    public ResponseModel update(@RequestBody RequestModel<DictionaryType> requestModel) {
        DictionaryType param = requestModel.getContent();
        Objects.requireNonNull(param, "请求对象不能为空");
        dictionaryTypeService.updateEntity(param);
        return new ResponseModel("修改数据类型成功！");
    }

    @PostMapping("delete")
    @ApiOperation(value = "删除数据类型")
    @OpsLog(value = "删除数据类型", type = OpsLogType.DELETE)
    public ResponseModel delete(@RequestBody RequestModel<String> requestModel) {
        String id = requestModel.getContent();
        Objects.requireNonNull(id, "数据类型id不能为空");
        dictionaryTypeService.deleteEntity(id);

        return new ResponseModel();
    }

    @PostMapping(value = "set")
    @ApiOperation(value = "显示/隐藏数据类型")
    @OpsLog(value = "显示/隐藏数据类型", type = OpsLogType.UPDATE)
    public ResponseModel updateShowStatus(@RequestBody RequestModel<DicTypeQueryModel> requestModel) {
        DicTypeQueryModel model = requestModel.getContent();

        Objects.requireNonNull(model, "入参不能为空");
        Objects.requireNonNull(model.getId(), "数据类型id不能为空");
        Objects.requireNonNull(model.getIsShow(), "数据类型状态不能为空");

        ResponseModel responseModel = new ResponseModel();
        if (StringUtils.equals("1", model.getIsShow())) {
            responseModel.setMessage("显示成功！");
        } else {
            responseModel.setMessage("隐藏成功！");
        }

        dictionaryTypeService.updateShowStatus(model);
        return responseModel;
    }

    @PostMapping(value = "check/status")
    @ApiOperation(value = "查询数据类型是否可以删除")
    @OpsLog(value = "查询数据类型是否可以删除", type = OpsLogType.SELECT)
    public ResponseModel checkEntityStatus(@RequestBody RequestModel<String> requestModel) {

        String id = requestModel.getContent();
        Objects.requireNonNull(id, "数据类型id不能为空");

        dictionaryTypeService.checkEntityStatus(id);
        return new ResponseModel();
    }

    @PostMapping("check/exist")
    @ApiOperation(value = "检测【数据类型编码】是否存在")
    @OpsLog(value = "检测【数据类型编码】是否存在", type = OpsLogType.CHECK)
    public ResponseModel checkExist(@RequestBody RequestModel<String> requestModel) {
        String code = requestModel.getContent();
        Objects.requireNonNull(code, "数据类型编码不能为空");
        dictionaryTypeService.checkExist(code);
        return new ResponseModel();
    }

}
