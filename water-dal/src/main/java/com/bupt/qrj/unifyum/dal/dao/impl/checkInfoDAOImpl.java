/**
 * 
 */
package com.bupt.qrj.unifyum.dal.dao.impl;

import com.bupt.qrj.unifyum.dal.dao.checkInfoDAO;

import com.bupt.qrj.unifyum.dal.dataobject.checkInfoDO;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import java.util.List;


/**
 * @author renjun.qrj  2016年4月4日:下午11:07:26
 * com.bupt.qrj.unifyum.dal.dao.impl.SkinTestDataDAOImpl
 * unifyum-dal
 * 用途: 
 *
 */
public class checkInfoDAOImpl extends SqlMapClientDaoSupport implements checkInfoDAO {
    
	 /** 日志 **/
/*    private static final Logger LOGGER = LoggerFactory.getLogger(seekwjsDAOImpl.class);*/
    public List<checkInfoDO> list(String factory_id) {

/*        HashMap<String,Object> abbs = new HashMap<String,Object>();
        abbs.put("worker_phone",worker_phone);*/
        List<checkInfoDO> rets = this.getSqlMapClientTemplate().queryForList("select-finish-mission",factory_id);
       
        return rets;
    }

}
