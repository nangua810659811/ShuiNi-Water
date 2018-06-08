/**
 * 
 */
package com.bupt.qrj.unifyum.dal.dao.impl;

import com.bupt.qrj.unifyum.dal.dao.arrangeinsertDAO;
import com.bupt.qrj.unifyum.dal.dao.arrfeedbackDAO;
import com.bupt.qrj.unifyum.dal.dataobject.arrangeinsertDO;
import com.bupt.qrj.unifyum.dal.dataobject.arrfeedbackDO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

/**
 * @author renjun.qrj 2015年10月31日:下午6:00:56
 *         com.bupt.qrj.dao.impl.UserMetaDAOImpl unifyum-dal 用途:
 *
 */
@SuppressWarnings("deprecation")
public class arrfeedbackDAOImpl extends SqlMapClientDaoSupport implements
		arrfeedbackDAO {

	/** 日志 **/
	private static final Logger LOGGER = LoggerFactory
			.getLogger(arrfeedbackDAOImpl.class);


	public void insert(arrfeedbackDO arrfeedbackDO) throws DataAccessException {


		this.getSqlMapClientTemplate().insert("arr-feedback-insert",
				arrfeedbackDO);
    }



	public void delete(String set_id) throws DataAccessException {


		this.getSqlMapClientTemplate().delete("set-mission-DELETE",
				set_id);
		LOGGER.debug("do the userMeta success");
	}



}
