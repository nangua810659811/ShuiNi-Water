/**
 * 
 */
package com.bupt.qrj.unifyum.dal.dao;

import com.bupt.qrj.unifyum.dal.dataobject.arrangeListDO;
import com.bupt.qrj.unifyum.dal.dataobject.arrangeseekDO;
import com.bupt.qrj.unifyum.dal.dataobject.arrangeseekmisDO;
import com.bupt.qrj.unifyum.dal.dataobject.arrangesetmisDO;

import java.util.List;

/**
 * @author renjun.qrj 2015年10月31日:下午6:00:31 com.bupt.qrj.dao.impl.UserMetaDAO
 *         unifyum-dal 用途:
 *
 */
public interface arrangelistDAO {


	public List<arrangeListDO> listarrangeList(String date,String time,String factory_id);
	public List<arrangesetmisDO> setmis(String factory_id);
}
