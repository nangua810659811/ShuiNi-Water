/**
 * 
 */
package com.bupt.qrj.unifyum.dal.dao.impl;

import com.bupt.qrj.unifyum.dal.dao.arrangeinsertDAO;
import com.bupt.qrj.unifyum.dal.dao.arrsetmisDAO;
import com.bupt.qrj.unifyum.dal.dataobject.arrangeinsertDO;
import com.bupt.qrj.unifyum.dal.dataobject.arrfeedbackDO;
import com.bupt.qrj.unifyum.dal.dataobject.arrgetworkerDO;
import com.bupt.qrj.unifyum.dal.dataobject.arrsetmisDO;
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
public class arrsetmisDAOImpl extends SqlMapClientDaoSupport implements
		arrsetmisDAO {

	/** 日志 **/
	private static final Logger LOGGER = LoggerFactory
			.getLogger(arrsetmisDAOImpl.class);


	public void insert(arrsetmisDO arrsetmisDO) throws DataAccessException {


		this.getSqlMapClientTemplate().insert("arr-set-mis-insert",
				arrsetmisDO);



    }
	public void update(arrsetmisDO arrsetmisDO) throws DataAccessException {


		try{
			this.getSqlMapClientTemplate().update("arr-set-mis-insert-type-change",
					arrsetmisDO);
		}catch (Exception e){
			System.out.println("error");
		}

	}

	public arrfeedbackDO get(arrfeedbackDO arrfeedbackDO) throws DataAccessException {

		if (arrfeedbackDO.getMission() == null)
			return null;
		//System.out.println("1");
		arrfeedbackDO resultDO = (arrfeedbackDO) this.getSqlMapClientTemplate()
				.queryForObject("select-arrfeedback", arrfeedbackDO);
		//System.out.println("2");
		return resultDO;
	}
	public arrgetworkerDO getworker(arrgetworkerDO arrgetworkerDO) throws DataAccessException {


		arrgetworkerDO resultDO = (arrgetworkerDO) this.getSqlMapClientTemplate()
				.queryForObject("arr-set-mis-insert-get-worker", arrgetworkerDO);
		//System.out.println("2");
		return resultDO;
	}

}
