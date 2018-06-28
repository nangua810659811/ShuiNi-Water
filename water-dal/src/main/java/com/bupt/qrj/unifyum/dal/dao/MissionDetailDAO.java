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
public interface MissionDetailDAO {



    public List<String> list(String mission_id);

	public String get(String mission_id, String column_key) throws DataAccessException;

	public String getname(String mission_id) throws DataAccessException;

    public void update(MissionReturnDO missionReturnDO) throws DataAccessException;

    public List<MissionReturnDO> Return_list(String level_one, String level_two);

    public String get_event(String mission_id) throws DataAccessException;

    public List<missionJsonDO> Event_Detail(String mission_id, String event_id);

    public MissionReturnDO get_first() throws DataAccessException;

    public List<EventDetail1DO> Event_Detail1(String mission_id);
}
