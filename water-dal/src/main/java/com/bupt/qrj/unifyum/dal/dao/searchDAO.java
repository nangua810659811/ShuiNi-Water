/**
 * 
 */
package com.bupt.qrj.unifyum.dal.dao;


import com.bupt.qrj.unifyum.dal.dataobject.searchDO;

import java.util.List;

/**
 * @author renjun.qrj 2015年10月31日:下午6:00:31 com.bupt.qrj.dao.impl.UserMetaDAO
 *         unifyum-dal 用途:
 *
 */
public interface searchDAO {

	

	
	public List<searchDO> list(String worker_name,String mission_name,String mission_type,String mission_condition,String timemin,String timemax);


}
