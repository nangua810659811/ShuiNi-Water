/**
 * 
 */
package com.bupt.qrj.unifyum.dal.dao;

import com.bupt.qrj.unifyum.dal.dataobject.arrangeinsertDO;
import com.bupt.qrj.unifyum.dal.dataobject.arrfeedbackDO;
import org.springframework.dao.DataAccessException;

/**
 * @author renjun.qrj 2015年10月31日:下午6:00:31 com.bupt.qrj.dao.impl.UserMetaDAO
 *         unifyum-dal 用途:
 *
 */
public interface arrfeedbackDAO {


	public void insert(arrfeedbackDO arrfeedbackDO) throws DataAccessException;

	public void delete(String set_id) throws DataAccessException;


}
