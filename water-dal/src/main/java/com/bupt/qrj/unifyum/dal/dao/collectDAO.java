/**
 * 
 */
package com.bupt.qrj.unifyum.dal.dao;

import com.bupt.qrj.unifyum.dal.dataobject.checkInfoDO;
import com.bupt.qrj.unifyum.dal.dataobject.collectDO;

import java.util.List;

/**
 * @author renjun.qrj 2015年10月31日:下午6:00:31 com.bupt.qrj.dao.impl.UserMetaDAO
 *         unifyum-dal 用途:
 *
 */
public interface collectDAO {

	

	
	public List<collectDO> list(String ymmin,String ymmax);


}