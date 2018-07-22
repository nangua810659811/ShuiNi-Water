/**
 * 
 */
package com.bupt.qrj.unifyum.dal.dao.impl;

import com.bupt.qrj.unifyum.dal.dao.arrangepermisDAO;
import com.bupt.qrj.unifyum.dal.dataobject.arrangepermisDO;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import java.util.HashMap;
import java.util.List;


/**
 * @author renjun.qrj  2016年4月4日:下午11:07:26
 * com.bupt.qrj.unifyum.dal.dao.impl.SkinTestDataDAOImpl
 * unifyum-dal
 * 用途: 
 *
 */
public class arrangepermisDAOImpl extends SqlMapClientDaoSupport implements arrangepermisDAO {
    
	 /** 日志 **/
/*    private static final Logger LOGGER = LoggerFactory.getLogger(seekwjsDAOImpl.class);*/

    public List<arrangepermisDO> listarrangepermis(String worker_name, String timemmin, String timemmax,String set_start_time_code,String factory_id) {

        HashMap<String,Object> abbys = new HashMap<String,Object>();
        abbys.put("worker_name",worker_name);
        abbys.put("timemin",timemmin);
        abbys.put("timemax",timemmax);
        abbys.put("set_start_time_code",set_start_time_code);
        abbys.put("factory_id",factory_id);
//        System.out.println(timemmin+timemmax);
        List<arrangepermisDO> rets = this.getSqlMapClientTemplate().queryForList("arrange-per-mis", abbys);
        /*System.out.println(rets);*/
        return rets;
    }
}
