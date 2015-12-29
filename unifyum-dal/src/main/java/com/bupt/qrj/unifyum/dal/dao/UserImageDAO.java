/**
 * 
 */
package com.bupt.qrj.unifyum.dal.dao;

import java.util.List;

import com.bupt.qrj.unifyum.dal.dataobject.UserImageDO;

/**
 * @author renjun.qrj  2015��12��29��:����7:31:43
 * com.bupt.qrj.unifyum.dal.dao.UserImageDAO
 * unifyum-dal
 * ��;: 
 *
 */
public interface UserImageDAO {

    public void addImage(UserImageDO image);

    public List<UserImageDO> queryImageByUser(String userName);

    public UserImageDO getImage(Long id);

}
