/**
 * 
 */
package com.bupt.qrj.unifyum.dal.dao;

import org.springframework.dao.DataAccessException;

import com.bupt.qrj.unifyum.dal.dataobject.UserMetaDO;

/**
 * @author renjun.qrj 2015��10��31��:����6:00:31 com.bupt.qrj.dao.impl.UserMetaDAO
 *         unifyum-dal ��;:
 *
 */
public interface UserMetaDAO {

	/**
	 * add 2015��10��31�� 2015��10��31��.����6:03:54
	 * 
	 * @param userMeta
	 * @return void - �����ݿ�������һ���û�����
	 */
	public void add(UserMetaDO userMeta) throws DataAccessException;

	/**
	 * update 2015��10��31�� 2015��10��31��.����6:04:00
	 * 
	 * @param userMeta
	 * @return void - ����һ���û����ݵ���Ϣ
	 */
	public void update(UserMetaDO userMeta) throws DataAccessException;

	/**
	 * get 2015��10��31�� 2015��10��31��.����6:04:07
	 * 
	 * @param UserMetaDO
	 * @return UserMetaDO - �����ݿ��л�ȡһ���û�������
	 */
	public UserMetaDO get(UserMetaDO userMeta) throws DataAccessException;

	/**
	 * delete 2015��10��31�� 2015��10��31��.����6:49:03
	 * 
	 * @param UserMetaDO
	 * @return: void - ɾ��һ������
	 */
	public void delete(UserMetaDO userMeta) throws DataAccessException;
}
