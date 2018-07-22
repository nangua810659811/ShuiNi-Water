/**
 * 
 */
package com.bupt.qrj.unifyum.dal.dao;


import com.bupt.qrj.unifyum.dal.dataobject.workerLoginDO;

/**
 * @author renjun.qrj 2015年10月31日:下午6:00:31 com.bupt.qrj.dao.impl.UserMetaDAO
 *         unifyum-dal 用途:
 *
 */
public interface workerloginDAO {


	public workerLoginDO get(workerLoginDO workerloginDO);

	public void update(workerLoginDO workerloginDO);
	public String getVersion();

}
