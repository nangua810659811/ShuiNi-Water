/**
 * 
 */
package com.bupt.qrj.unifyum.dal.dataobject;

/**
 * @author renjun.qrj 2015年10月31日:下午5:52:08 com.bupt.qrj.dataobject.UserMeta
 *         unifyum-dal 用途: - 用户账户基本信息
 *
 */
public class searchDO {


	public String getFinish_time() {
		return finish_time;
	}

	public void setFinish_time(String finish_time) {
		this.finish_time = finish_time;
	}

	public String getMission_id() {
		return mission_id;
	}

	public void setMission_id(String mission_id) {
		this.mission_id = mission_id;
	}

	public String getWorker_name() {
		return worker_name;
	}

	public void setWorker_name(String worker_name) {
		this.worker_name = worker_name;
	}

	private String finish_time;
	private String mission_id;

	private String worker_name;



	private String mission_condition;

	public String getMission_condition() {
		return mission_condition;
	}

	public void setMission_condition(String mission_condition) {
		this.mission_condition = mission_condition;
	}
}
