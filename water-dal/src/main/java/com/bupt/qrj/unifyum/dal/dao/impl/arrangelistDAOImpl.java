/**
 * 
 */
package com.bupt.qrj.unifyum.dal.dao.impl;

import com.bupt.qrj.unifyum.dal.dao.arrangelistDAO;
import com.bupt.qrj.unifyum.dal.dao.arrangeseekDAO;
import com.bupt.qrj.unifyum.dal.dataobject.arrangeListDO;
import com.bupt.qrj.unifyum.dal.dataobject.arrangeseekDO;
import com.bupt.qrj.unifyum.dal.dataobject.arrangeseekmisDO;
import com.bupt.qrj.unifyum.dal.dataobject.arrangesetmisDO;
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
public class arrangelistDAOImpl extends SqlMapClientDaoSupport implements arrangelistDAO {
    
	 /** 日志 **/
/*    private static final Logger LOGGER = LoggerFactory.getLogger(seekwjsDAOImpl.class);*/
    public List<arrangeListDO> listarrangeList(String date,String time) {

        HashMap<String,Object> abbys = new HashMap<String,Object>();
        abbys.put("date",date);
        abbys.put("set_time",time);

/*        System.out.println(timemmin+timemmax);*/
        List<arrangeListDO> rets = this.getSqlMapClientTemplate().queryForList("arrange-List", abbys);
        /*System.out.println(rets);*/
        return rets;
    }
    public List<arrangesetmisDO> setmis() {


        List<arrangesetmisDO> rets = this.getSqlMapClientTemplate().queryForList("arr-set-mis");

        return rets;
    }
}
