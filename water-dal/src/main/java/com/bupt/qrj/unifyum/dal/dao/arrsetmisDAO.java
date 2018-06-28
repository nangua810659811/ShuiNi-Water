/**
 * 
 */
package com.bupt.qrj.unifyum.dal.dao;

import com.bupt.qrj.unifyum.dal.dataobject.*;
import org.springframework.dao.DataAccessException;

/**
 * @author renjun.qrj 2015年10月31日:下午6:00:31 com.bupt.qrj.dao.impl.UserMetaDAO
 *         unifyum-dal 用途:
 *
 */
public interface arrsetmisDAO {


	public void insert(arrsetmisDO arrsetmisDO) throws DataAccessException;


	public void update(arrsetmisDO arrsetmisDO) throws DataAccessException;

	public arrfeedbackDO get(arrfeedbackDO arrfeedbackDO);

	public arrgetworkerDO getworker(arrgetworkerDO arrgetworkerDO);
}
