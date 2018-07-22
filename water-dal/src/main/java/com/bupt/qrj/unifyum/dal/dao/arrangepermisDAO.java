/**
 * 
 */
package com.bupt.qrj.unifyum.dal.dao;

import com.bupt.qrj.unifyum.dal.dataobject.arrangepermisDO;

import java.util.List;

/**
 * @author renjun.qrj 2015年10月31日:下午6:00:31 com.bupt.qrj.dao.impl.UserMetaDAO
 *         unifyum-dal 用途:
 *
 */
public interface arrangepermisDAO {




	public List<arrangepermisDO> listarrangepermis(String worker_name, String timemmin, String timemmax, String set_start_time_code,String factory_id);
}
