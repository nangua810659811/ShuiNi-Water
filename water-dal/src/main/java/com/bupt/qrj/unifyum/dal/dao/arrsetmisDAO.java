/**
 * 
 */
package com.bupt.qrj.unifyum.dal.dao;

import com.bupt.qrj.unifyum.dal.dataobject.arrangeinsertDO;
import com.bupt.qrj.unifyum.dal.dataobject.arrfeedbackDO;
import com.bupt.qrj.unifyum.dal.dataobject.arrsetmisDO;
import com.bupt.qrj.unifyum.dal.dataobject.workerLoginDO;
import org.springframework.dao.DataAccessException;

/**
 * @author renjun.qrj 2015年10月31日:下午6:00:31 com.bupt.qrj.dao.impl.UserMetaDAO
 *         unifyum-dal 用途:
 *
 */
public interface arrsetmisDAO {


	public void insert(arrsetmisDO arrsetmisDO) throws DataAccessException;




	public arrfeedbackDO get(arrfeedbackDO arrfeedbackDO);

}