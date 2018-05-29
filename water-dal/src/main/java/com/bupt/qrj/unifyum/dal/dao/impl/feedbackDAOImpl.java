/**
 * 
 */
package com.bupt.qrj.unifyum.dal.dao.impl;

import com.bupt.qrj.unifyum.dal.dao.feedbackDAO;
import com.bupt.qrj.unifyum.dal.dao.locationDAO;
import com.bupt.qrj.unifyum.dal.dataobject.feedbackDO;
import com.bupt.qrj.unifyum.dal.dataobject.locationDO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import java.util.HashMap;
import java.util.List;

/**
 * @author renjun.qrj 2015年10月31日:下午6:00:56
 *         com.bupt.qrj.dao.impl.UserMetaDAOImpl unifyum-dal 用途:
 *
 */
@SuppressWarnings("deprecation")
public class feedbackDAOImpl extends SqlMapClientDaoSupport implements
		feedbackDAO {

	/** 日志 **/
	private static final Logger LOGGER = LoggerFactory
			.getLogger(feedbackDAOImpl.class);



	public void updatedata(feedbackDO feedbackDO) throws DataAccessException {

        this.getSqlMapClientTemplate().update("feedback-UPDATE-data",
				feedbackDO);

    }

	public void updatepic(feedbackDO feedbackDO) throws DataAccessException {

		this.getSqlMapClientTemplate().update("feedback-UPDATE-pic",
				feedbackDO);

	}

	public void insertdata(feedbackDO feedbackDO) throws DataAccessException {


		this.getSqlMapClientTemplate().insert("feedback-insert-data",
				feedbackDO);
	}
	public void insertpic(feedbackDO feedbackDO) throws DataAccessException {


		this.getSqlMapClientTemplate().insert("feedback-insert-pic",
				feedbackDO);
	}
	public List<feedbackDO> seek(String mission_id,String event_id,String work_name) {

		HashMap<String,Object> abbys = new HashMap<String,Object>();

		abbys.put("mission_id",mission_id);
		abbys.put("event_id",event_id);
        abbys.put("work_name",work_name);
		List<feedbackDO> rets = this.getSqlMapClientTemplate().queryForList("feedback-seek", abbys);

		return rets;
	}
}
