/**
 * 
 */
package com.bupt.qrj.unifyum.dal.dao.impl;

import com.bupt.qrj.unifyum.dal.dao.MissionReturnArrDAO;
import com.bupt.qrj.unifyum.dal.dao.MissionReturnDAO;
import com.bupt.qrj.unifyum.dal.dataobject.EventInfoDO;
import com.bupt.qrj.unifyum.dal.dataobject.MissionReturnDO;
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
public class MissionReturnArrDAOImpl extends SqlMapClientDaoSupport implements
        MissionReturnArrDAO {

	/** 日志 **/
	private static final Logger LOGGER = LoggerFactory
			.getLogger(MissionReturnArrDAOImpl.class);



	public List<String> list(String mission_id) {

		LOGGER.debug("do the user list start");

		List<String> rets = this.getSqlMapClientTemplate().queryForList(
				"Mission-Return-Key",mission_id);
		LOGGER.debug("do the TestData user list over");
		if (rets == null || rets.size() == 0) {
			LOGGER.debug("do the user list return null");
			return null;
		}
		System.out.println(rets);
		return rets;
	}

	public String get(String mission_id, String column_key) throws DataAccessException {

		if (mission_id == null || column_key== null)
			return null;
        HashMap<String,Object> abc = new HashMap<String, Object>();
        abc.put("column_key",column_key);
        abc.put("mission_id",mission_id);

        String rets = (String) this.getSqlMapClientTemplate()
				.queryForObject("Mission-Value", abc);

        return rets;
	}

    public void update(MissionReturnDO missionReturnDO) throws DataAccessException {
        if (missionReturnDO == null) {
            throw new IllegalArgumentException(
                    "Can't insert a null data(mobilelbsMerchantSubcategoryDO) object into db.");
        }


        this.getSqlMapClientTemplate().update("Mission-Return-UPDATE",
                missionReturnDO);

    }


    public List<MissionReturnDO> Return_list(String level_one,String level_two) {

        LOGGER.debug("do the user list start");

        HashMap<String,Object> abb = new HashMap<String, Object>();
        abb.put("level_two",level_two);
        abb.put("level_one",level_one);
        List<MissionReturnDO> rets = this.getSqlMapClientTemplate().queryForList(
                "Mission-Return-List",abb);
        LOGGER.debug("do the TestData user list over");
        if (rets == null || rets.size() == 0) {
            LOGGER.debug("do the user list return null");
            return null;
        }

        return rets;
    }

    public String get_event(String mission_id) throws DataAccessException {

        String rets = (String) this.getSqlMapClientTemplate()
                .queryForObject("Mission-Event-List", mission_id);

        return rets;
    }

    public List<EventInfoDO> Event_list(String event_id) {

        LOGGER.debug("do the user list start");

        List<EventInfoDO> rets = this.getSqlMapClientTemplate().queryForList(
                "Mission-Event-Info",event_id);
        LOGGER.debug("do the TestData user list over");
        if (rets == null || rets.size() == 0) {
            LOGGER.debug("do the user list return null");
            return null;
        }

        return rets;
    }
    public MissionReturnDO get_first() throws DataAccessException {

         MissionReturnDO resultDO = (MissionReturnDO) this.getSqlMapClientTemplate()
                .queryForObject("Mission-Return-first");

        return resultDO;
    }
}
