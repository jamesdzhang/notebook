package com.nb.james.mybatis.dao;


import com.nb.james.mybatis.vo.CdsOrderVo;

import java.util.List;
import java.util.Map;

/**
 * Created by zhangyaping on 2017/6/5.
 */
public interface ICdsOrderDao {

    List<CdsOrderVo> selectByParam(Map<String, Object> map);

    CdsOrderVo selectByOrderId(String orderId);

    CdsOrderVo selectBySubOrderId(String subOrderId);

    Integer insertRecord(CdsOrderVo cdsOrder);

    Integer updateStatus(CdsOrderVo cdsOrder);

    Integer deleteByArchiveTime(Map<String, Object> map);
}
