/**
 * 
 */
package com.bupt.qrj.unifyum.dal.dao;

import java.util.List;

import com.bupt.qrj.unifyum.dal.dataobject.SkinTestDataDO;

/**
 * @author renjun.qrj  2016��4��4��:����11:06:21
 * com.bupt.qrj.unifyum.dal.dao.SkinTestDataDAO
 * unifyum-dal
 * ��;: 
 *
 */
public interface SkinTestDataDAO {
    void insert(SkinTestDataDO dataDO);

    /**
     * ��ȡȫ����,��userName ���ˣ����Դ�limit��start
     * */
    List<SkinTestDataDO> list(String userName);

    /**
     * �鿴���е��û���
     * */
    List<String> listUsers();

}
