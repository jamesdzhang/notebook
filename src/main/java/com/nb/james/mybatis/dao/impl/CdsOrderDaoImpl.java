package com.nb.james.mybatis.dao.impl;

import com.nb.james.mybatis.dao.ICdsOrderDao;
import com.nb.james.mybatis.vo.CdsOrderVo;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangyaping on 2017/6/5.
 */
@Repository
public class CdsOrderDaoImpl implements ICdsOrderDao {

    private static String DEFAULT_NAMESPACE = CdsOrderVo.class.getName().concat(".");

    @Resource(name = "sqlSessionTemplateMaster")
    private SqlSessionTemplate sst;


    @Override
    public List<CdsOrderVo> selectByParam(Map<String, Object> map) {
        return this.sst.selectList(DEFAULT_NAMESPACE.concat("selectByParam"), map);
    }

    @Override
    public CdsOrderVo selectByOrderId(String orderId) {
        List<CdsOrderVo> list = this.sst.selectList(DEFAULT_NAMESPACE.concat("selectByOrderId"), orderId);
        return (list != null && list.size()>0)?list.get(0):null;
    }

    @Override
    public CdsOrderVo selectBySubOrderId(String subOrderId) {
        List<CdsOrderVo> list = this.sst.selectList(DEFAULT_NAMESPACE.concat("selectBySubOrderId"), subOrderId);
        return (list != null && list.size()>0)?list.get(0):null;
    }

    @Override
    public Integer insertRecord(CdsOrderVo cdsOrder) {
        return this.sst.insert(DEFAULT_NAMESPACE.concat("insertRecord"), cdsOrder);
    }

    @Override
    public Integer updateStatus(CdsOrderVo cdsOrder) {
        return this.sst.update(DEFAULT_NAMESPACE.concat("updateStatus"), cdsOrder);
    }

    @Override
    public Integer deleteByArchiveTime(Map<String, Object> map) {
        return this.sst.delete(DEFAULT_NAMESPACE.concat("deleteByArchiveTime"), map);
    }
}
