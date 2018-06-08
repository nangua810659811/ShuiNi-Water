/**
 * 
 */
package com.bupt.qrj.unifyum.dal.dao;

import com.bupt.qrj.unifyum.dal.dataobject.*;
import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 * @author renjun.qrj 2015年10月31日:下午6:00:31 com.bupt.qrj.dao.impl.UserMetaDAO
 *         unifyum-dal 用途:
 *
 */
public interface setMissionDAO {

    public String get0(String mission_id,String column_key) throws DataAccessException;

    public List<String> list0(String mission_id);

    public void update(setMissionDO setMissionDO) throws DataAccessException;

    public List<setMissionDO> List(String type);

    public List<materialDO> list1(String type);

    public List<EventInfoDO> Event_list();

    public setMissionDO get(String type, String array) throws DataAccessException;

    public List<setMissioninfoDO> Mission_list();
}
