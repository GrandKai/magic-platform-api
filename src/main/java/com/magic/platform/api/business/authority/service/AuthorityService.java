package com.magic.platform.api.business.authority.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.magic.platform.api.business.authority.mapper.dao.AuthorityVOMapper;
import com.magic.platform.api.business.authority.mapper.entity.AuthorityVO;
import com.magic.platform.api.business.authority.model.AuthorityModel;
import com.magic.platform.api.business.authority.model.AuthorityQueryModel;
import com.magic.platform.api.business.operation.mapper.custom.entity.OperationVO;
import com.magic.platform.core.model.RequestModel;
import com.magic.platform.entity.mapper.build.dao.AuthorityMapper;
import com.magic.platform.entity.mapper.build.dao.AuthorityMenuMapper;
import com.magic.platform.entity.mapper.build.dao.AuthorityOperationMapper;
import com.magic.platform.entity.mapper.build.entity.Authority;
import com.magic.platform.entity.mapper.build.entity.AuthorityMenu;
import com.magic.platform.entity.mapper.build.entity.AuthorityOperation;
import com.magic.platform.util.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: GrandKai
 * @create: 2018-10-01 23:30
 */
@Service
public class AuthorityService {
    @Autowired
    private AuthorityMapper authorityMapper;
    @Autowired
    private AuthorityVOMapper authorityVOMapper;


    @Autowired
    private AuthorityMenuMapper authorityMenuMapper;

    @Autowired
    private AuthorityOperationMapper authorityOperationMapper;

    public PageInfo selectEntityPage(RequestModel<AuthorityQueryModel> requestModel) {

        AuthorityQueryModel model = requestModel.getContent();

        int pageNum = requestModel.getPage().getPageNum() - 1;
        int pageSize = requestModel.getPage().getPageSize();

        Map<String, Object> param = new HashMap<>();
        param.put("name", model.getName());
        param.put("platId", model.getPlatId());

        PageHelper.offsetPage(pageNum * pageSize, pageSize, true);

        List<AuthorityVO> list = authorityVOMapper.selectEntityList(param);
        PageInfo pageInfo = new PageInfo(list);

        return pageInfo;
    }

    public List<AuthorityVO> selectEntityList(AuthorityQueryModel model) {

        Map<String, Object> param = new HashMap<>();
        if (model != null) {
            param.put("name", model.getName());
            param.put("platId", model.getPlatId());
        }

        return authorityVOMapper.selectEntityList(param);
    }

    @Transactional(rollbackFor = Exception.class)
    public void addEntity(AuthorityModel param) {

        Authority entity = new Authority();

        String authorityId = UUIDUtils.uuid();

        entity.setId(authorityId);
        entity.setPlatId(param.getPlatId());
        entity.setName(param.getName());
        entity.setDescription(param.getDescription());

        entity.setSortNumber(param.getSortNumber());
        entity.setCreateTime(new Date());
        entity.setUpdateTime(new Date());
        authorityMapper.insert(entity);

        List<OperationVO> list = param.getOperations();

        AuthorityMenu authorityMenu = null;
        AuthorityOperation authorityOperation = null;
        // 向权限对应的菜单，操作中间表插入数据

        List<AuthorityMenu> authorityMenus = new ArrayList<>();
        List<AuthorityOperation> authorityOperations = new ArrayList<>();
        for (OperationVO vo : list) {
            if ("menu".equals(vo.getType())) {
                authorityMenu = new AuthorityMenu();
                authorityMenu.setAuthorityId(authorityId);
                authorityMenu.setMenuId(vo.getId());

                authorityMenus.add(authorityMenu);

            } else if ("operation".equals(vo.getType())) {
                authorityOperation = new AuthorityOperation();
                authorityOperation.setAuthorityId(authorityId);
                authorityOperation.setOperationId(vo.getId());

                authorityOperations.add(authorityOperation);
            }
        }

        // 向 authority_menu 表插入数据
        if (0 < authorityMenus.size()) {
            authorityMenuMapper.insertList(authorityMenus);
        }

        // 向 authority_operation 表插入数据
        if (0 < authorityOperations.size()) {
            authorityOperationMapper.insertList(authorityOperations);
        }

    }

    @Transactional(rollbackFor = Exception.class)
    public void updateEntity(Authority param) {

        Authority entity = new Authority();

        entity.setId(param.getId());
        entity.setName(param.getName());
        entity.setDescription(param.getDescription());
        entity.setSortNumber(param.getSortNumber());
        entity.setUpdateTime(new Date());
        authorityMapper.updateByPrimaryKeySelective(entity);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteEntity(String id) {

        // 1.删除权限菜单对应关系
        AuthorityMenu authorityMenu = new AuthorityMenu();
        authorityMenu.setAuthorityId(id);
        authorityMenuMapper.delete(authorityMenu);

        // 2.删除权限操作对应关系
        AuthorityOperation authorityOperation = new AuthorityOperation();
        authorityOperation.setAuthorityId(id);
        authorityOperationMapper.delete(authorityOperation);

        // 3.删除权限信息
        authorityMapper.deleteByPrimaryKey(id);
    }

    /**
     * 根据权限 id 查询所有菜单 id 和操作 id
     * @param authorityId
     * @return
     */
    public List<String> selectAllGrantedOperationIds(String authorityId) {
        return authorityVOMapper.selectAllGrantedOperationIds(authorityId);
    }

    /**
     * 修改权限设置信息【1.修改权限菜单对应关系，2.修改权限操作对应关系】
     * @param param
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateAuthority(AuthorityModel param) {
        String authorityId = param.getId();

        // 1. 先删除原表数据信息
        AuthorityMenu paramMenu = new AuthorityMenu();
        paramMenu.setAuthorityId(authorityId);
        authorityMenuMapper.delete(paramMenu);

        AuthorityOperation paramAuthority = new AuthorityOperation();
        paramAuthority.setAuthorityId(authorityId);
        authorityOperationMapper.delete(paramAuthority);

        // 2. 重新插入数据
        List<OperationVO> list = param.getOperations();

        AuthorityMenu authorityMenu = null;
        AuthorityOperation authorityOperation = null;
        // 向权限对应的菜单，操作中间表插入数据

        List<AuthorityMenu> authorityMenus = new ArrayList<>();
        List<AuthorityOperation> authorityOperations = new ArrayList<>();
        for (OperationVO vo : list) {
            if ("menu".equals(vo.getType())) {
                authorityMenu = new AuthorityMenu();
                authorityMenu.setAuthorityId(authorityId);
                authorityMenu.setMenuId(vo.getId());

                authorityMenus.add(authorityMenu);

            } else if ("operation".equals(vo.getType())) {
                authorityOperation = new AuthorityOperation();
                authorityOperation.setAuthorityId(authorityId);
                authorityOperation.setOperationId(vo.getId());

                authorityOperations.add(authorityOperation);
            }
        }

        // 向 authority_menu 表插入数据
        if (0 < authorityMenus.size()) {
            authorityMenuMapper.insertList(authorityMenus);
        }

        // 向 authority_operation 表插入数据
        if (0 < authorityOperations.size()) {
            authorityOperationMapper.insertList(authorityOperations);
        }

    }
}
