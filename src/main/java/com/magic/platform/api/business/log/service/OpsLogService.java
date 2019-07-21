package com.magic.platform.api.business.log.service;

import com.github.pagehelper.PageInfo;
import com.magic.platform.api.business.log.model.OpsLogQueryModel;
import com.magic.platform.core.model.MongoPageResult;
import com.magic.platform.core.model.RequestModel;
import com.magic.platform.core.mongo.entity.OpsLog;
import com.magic.platform.framework.util.MongoPageHelper;
import java.util.Date;
import java.util.TimeZone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @Author: zyn
 * @Description:
 * @Date: Created in 2018-10-16 10:10
 * @Modified By:
 */
@Service
public class OpsLogService {

  @Autowired
  private MongoPageHelper mongoPageHelper;

  public PageInfo selectEntityPage(RequestModel<OpsLogQueryModel> requestModel) {

    OpsLogQueryModel model = requestModel.getContent();

    int pageNum = requestModel.getPage().getPageNum();
    int pageSize = requestModel.getPage().getPageSize();

    Sort sort = new Sort(Direction.DESC, "create_time");

    Query query = new Query();
    query.with(sort);

    // 平台id
    if (!StringUtils.isEmpty(model.getPlatId())) {
      query.addCriteria(Criteria.where("plat_id").is(model.getPlatId()));
    }

    // ip 地址
    if (!StringUtils.isEmpty(model.getIp())) {
      String pattern = ".*" + model.getIp() + ".*";
      query.addCriteria(Criteria.where("ip").regex(pattern, "i"));
    }

    // 操作账号
    if (!StringUtils.isEmpty(model.getUserName())) {
      String pattern = ".*" + model.getUserName() + ".*";
      query.addCriteria(Criteria.where("user_name").regex(pattern, "i"));
    }

    // 操作人
    if (!StringUtils.isEmpty(model.getRealName())) {
      String pattern = ".*" + model.getRealName() + ".*";
      query.addCriteria(Criteria.where("real_name").regex(pattern, "i"));
    }

    // 操作类型
    if (!StringUtils.isEmpty(model.getOpsType())) {
      query.addCriteria(Criteria.where("ops_type").is(model.getOpsType()));
    }


    if (!StringUtils.isEmpty(model.getBeginTime())) {
      query.addCriteria(Criteria.where("create_time").gte(model.getBeginTime()));
    }

    if (!StringUtils.isEmpty(model.getEndTime())) {
      query.addCriteria(Criteria.where("create_time").lte(model.getEndTime()));
    }


    MongoPageResult<OpsLog> result = mongoPageHelper.pageQuery(query, OpsLog.class, pageNum, pageSize);

    PageInfo pageInfo = new PageInfo<>();
    pageInfo.setTotal(result.getTotal());
    pageInfo.setList(result.getList());
    pageInfo.setSize(result.getList().size());
    pageInfo.setPageSize(result.getPageSize());
    pageInfo.setPageNum(result.getPageNum());
    pageInfo.setPages(result.getPages());

    return pageInfo;

  }

  public static void main(String[] args) {
    Date data = new Date();
    System.out.println("default date:"+ data.toString());

    TimeZone.setDefault(TimeZone.getTimeZone("GMT+8:00"));
    System.out.println("GMT+8:00 date:"+ data.toString());

    TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    System.out.println("UTC date:"+ data.toString());
  }
}
