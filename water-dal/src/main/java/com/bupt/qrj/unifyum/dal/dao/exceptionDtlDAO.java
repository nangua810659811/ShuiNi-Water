/**
 * 
 */
package com.bupt.qrj.unifyum.dal.dao;

import com.bupt.qrj.unifyum.dal.dataobject.collectDO;
import com.bupt.qrj.unifyum.dal.dataobject.exceptionDtlDO;

import java.util.List;

/**
 * @author renjun.qrj 2015年10月31日:下午6:00:31 com.bupt.qrj.dao.impl.UserMetaDAO
 *         unifyum-dal 用途:
 *
 */
public interface exceptionDtlDAO {




	public List<exceptionDtlDO> list(String ymmin, String ymmax,String status);


}
