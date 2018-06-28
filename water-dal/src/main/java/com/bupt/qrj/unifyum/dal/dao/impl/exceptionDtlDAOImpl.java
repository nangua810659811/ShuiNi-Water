/**
 * 
 */
package com.bupt.qrj.unifyum.dal.dao.impl;

import com.bupt.qrj.unifyum.dal.dao.collectDAO;
import com.bupt.qrj.unifyum.dal.dao.exceptionDtlDAO;
import com.bupt.qrj.unifyum.dal.dataobject.collectDO;
import com.bupt.qrj.unifyum.dal.dataobject.exceptionDtlDO;
import com.bupt.qrj.unifyum.dal.dataobject.insertExceptionDO;
import org.springframework.dao.DataAccessException;
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
public class exceptionDtlDAOImpl extends SqlMapClientDaoSupport implements exceptionDtlDAO {
    
	 /** 日志 **/
/*    private static final Logger LOGGER = LoggerFactory.getLogger(seekwjsDAOImpl.class);*/
    public List<exceptionDtlDO> list(String ymmin, String ymmax,String status) {

        HashMap<String,Object> abbs = new HashMap<String,Object>();
        abbs.put("ymmin",ymmin);
        abbs.put("ymmax",ymmax);
        abbs.put("status",status);
        List<exceptionDtlDO> rets = this.getSqlMapClientTemplate().queryForList("select-exception-dtl",abbs);
       
        return rets;
    }
    /*    private static final Logger LOGGER = LoggerFactory.getLogger(seekwjsDAOImpl.class);*/
    public List<exceptionDtlDO> list1() {

        List<exceptionDtlDO> rets = this.getSqlMapClientTemplate().queryForList("select-exception-dtl1");

        return rets;
    }
    public void insert(insertExceptionDO insertExceptionDO) throws DataAccessException {


        this.getSqlMapClientTemplate().insert("exception-insert",
                insertExceptionDO);
    }
}
