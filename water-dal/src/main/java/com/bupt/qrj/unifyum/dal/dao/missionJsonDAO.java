/**
 * 
 */
package com.bupt.qrj.unifyum.dal.dao;

import com.bupt.qrj.unifyum.dal.dataobject.arrangeinsertDO;
import com.bupt.qrj.unifyum.dal.dataobject.missionJsonDO;
import com.bupt.qrj.unifyum.dal.dataobject.missionTimeDO;
import org.springframework.dao.DataAccessException;

/**
 * @author renjun.qrj 2015年10月31日:下午6:00:31 com.bupt.qrj.dao.impl.UserMetaDAO
 *         unifyum-dal 用途:
 *
 */
public interface missionJsonDAO {


	public void insert(missionJsonDO missionJsonDO) throws DataAccessException;
	public void insert1(missionTimeDO missionTimeDO) throws DataAccessException;
	public void insert2(missionTimeDO missionTimeDO) throws DataAccessException;
}
