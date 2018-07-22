/**
 * 
 */
package com.bupt.qrj.unifyum.dal.dao;


import com.bupt.qrj.unifyum.dal.dataobject.apkinfoDO;
import com.bupt.qrj.unifyum.dal.dataobject.picinfoDO;
import com.bupt.qrj.unifyum.dal.dataobject.picinfoexceptionDO;

/**
 * @author renjun.qrj 2015年10月31日:下午6:00:31 com.bupt.qrj.dao.impl.UserMetaDAO
 *         unifyum-dal 用途:
 *
 */
public interface findpicDAO {




	public picinfoDO get(picinfoDO picinfo);

	public picinfoDO getnewest(picinfoDO picinfo);

	public picinfoexceptionDO getexception(picinfoexceptionDO picinfoexceptionDO);
	public apkinfoDO getApk(apkinfoDO apkinfoDO);
}
