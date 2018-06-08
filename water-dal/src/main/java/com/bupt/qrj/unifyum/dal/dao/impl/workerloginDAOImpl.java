/**
 * 
 */
package com.bupt.qrj.unifyum.dal.dao.impl;

import com.bupt.qrj.unifyum.dal.dao.workerloginDAO;

import com.bupt.qrj.unifyum.dal.dataobject.workerLoginDO;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;


/**
 * @author renjun.qrj  2016年4月4日:下午11:07:26
 * com.bupt.qrj.unifyum.dal.dao.impl.SkinTestDataDAOImpl
 * unifyum-dal
 * 用途: 
 *
 */
public class workerloginDAOImpl extends SqlMapClientDaoSupport implements workerloginDAO {
    


    public workerLoginDO get(workerLoginDO workerlogin) throws DataAccessException {

        if (workerlogin.getPhone() == null)
            return null;
        //System.out.println("1");
        workerLoginDO resultDO = (workerLoginDO) this.getSqlMapClientTemplate()
                .queryForObject("WORKER-LOGIN", workerlogin);
        //System.out.println("2");
        return resultDO;
    }
    public void update(workerLoginDO workerlogin) throws DataAccessException {


        this.getSqlMapClientTemplate().update("update-login-random",
                workerlogin);


    }
}
