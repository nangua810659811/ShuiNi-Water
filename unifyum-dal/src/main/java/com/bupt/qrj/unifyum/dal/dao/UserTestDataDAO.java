/**
 * 
 */
package com.bupt.qrj.unifyum.dal.dao;

import java.util.List;
import java.util.Map;

import com.bupt.qrj.unifyum.dal.dataobject.UserTestDataDO;

/**
 * @author renjun.qrj  2016��1��2��:����9:09:23
 * com.bupt.qrj.unifyum.dal.dao.UserTestDataDAO
 * unifyum-dal
 * ��;: 
 *
 */
public interface UserTestDataDAO {

    void insert(UserTestDataDO dataDO);

    /**
     * ��ȡ���µ�һ��
     * */
    UserTestDataDO get(String userName);

    /**
     * ��ȡȫ����,��userName ���ˣ����Դ�limit��start
     * */
    List<UserTestDataDO> list(Map params);
}
