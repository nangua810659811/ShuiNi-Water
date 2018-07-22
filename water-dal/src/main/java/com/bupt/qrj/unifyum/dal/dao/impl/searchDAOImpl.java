/**
 * 
 */
package com.bupt.qrj.unifyum.dal.dao.impl;


import com.bupt.qrj.unifyum.dal.dao.searchDAO;

import com.bupt.qrj.unifyum.dal.dataobject.searchDO;
import com.bupt.qrj.unifyum.dal.dataobject.searchtestDO;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import java.util.HashMap;
import java.util.List;


/**
 * @author renjun.qrj  2016年4月4日:下午11:07:26
 * com.bupt.qrj.unifyum.dal.dao.impl.SkinTestDataDAOImpl
 * unifyum-dal
 * 用途: 
 *
 */
public class searchDAOImpl extends SqlMapClientDaoSupport implements searchDAO {
    
	 /** 日志 **/
/*    private static final Logger LOGGER = LoggerFactory.getLogger(seekwjsDAOImpl.class);*/
    public List<searchDO> list(String worker_name,String mission_name,String mission_type,String mission_condition,String timemin,String timemax,String factory) {

        HashMap<String,Object> abbs = new HashMap<String,Object>();
        abbs.put("worker_name",worker_name);
        abbs.put("mission_name",mission_name);
        abbs.put("mission_type",mission_type);
        abbs.put("mission_condition",mission_condition);
        abbs.put("timemin",timemin);
        abbs.put("timemax",timemax);
        abbs.put("factory",factory);
        List<searchDO> rets = this.getSqlMapClientTemplate().queryForList("search-mission",abbs);
       
        return rets;
    }

    public List<searchtestDO> list_test() {

        List<searchtestDO> rets = this.getSqlMapClientTemplate().queryForList("search-test");

        return rets;
    }

}
