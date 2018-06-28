/**
 * 
 */
package com.bupt.qrj.unifyum.dal.dataobject;

/**
 * @author renjun.qrj 2015年10月31日:下午5:52:08 com.bupt.qrj.dataobject.UserMeta
 *         unifyum-dal 用途: - 用户账户基本信息
 *
 */
public class arrsetmisDO {

	public String getMission_description() {
		return mission_description;
	}

	public void setMission_description(String mission_description) {
		this.mission_description = mission_description;
	}

	public String getMission() {
		return mission;
	}

	public void setMission(String mission) {
		this.mission = mission;
	}

	public String getCover_fields() {
		return cover_fields;
	}

	public void setCover_fields(String cover_fields) {
		this.cover_fields = cover_fields;
	}

	public String getMission_level() {
		return mission_level;
	}

	public void setMission_level(String mission_level) {
		this.mission_level = mission_level;
	}

	public String getMission_source() {
		return mission_source;
	}

	public void setMission_source(String mission_source) {
		this.mission_source = mission_source;
	}



	public String getWork_instrument() {
		return work_instrument;
	}

	public void setWork_instrument(String work_instrument) {
		this.work_instrument = work_instrument;
	}



	private String mission_description;

	private String mission;
	private String cover_fields;
	private String mission_level;

	private String mission_source;

	public String getAuthen_method() {
		return authen_method;
	}

	public void setAuthen_method(String authen_method) {
		this.authen_method = authen_method;
	}

	public String getDetail_info() {
		return detail_info;
	}

	public void setDetail_info(String detail_info) {
		this.detail_info = detail_info;
	}

	private String authen_method;
	private String work_instrument;
	private String detail_info;

	public String getPeriod_start_time() {
		return period_start_time;
	}

	public void setPeriod_start_time(String period_start_time) {
		this.period_start_time = period_start_time;
	}

	public String getPeriod_end_time() {
		return period_end_time;
	}

	public void setPeriod_end_time(String period_end_time) {
		this.period_end_time = period_end_time;
	}

	public String getSet_start_time_code() {
		return set_start_time_code;
	}

	public void setSet_start_time_code(String set_start_time_code) {
		this.set_start_time_code = set_start_time_code;
	}

	private String period_start_time;
	private String period_end_time;
	private String set_start_time_code;

	public String getWorker_name() {
		return worker_name;
	}

	public void setWorker_name(String worker_name) {
		this.worker_name = worker_name;
	}

	private String worker_name;
	private String team;

	public String getTeam() {
		return team;
	}

	public void setTeam(String team) {
		this.team = team;
	}

	public String getWorker_phone() {
		return worker_phone;
	}

	public void setWorker_phone(String worker_phone) {
		this.worker_phone = worker_phone;
	}

	private String worker_phone;
}
