/**
 * 
 */
package com.bupt.qrj.unifyum.dal.dataobject;

/**
 * @author renjun.qrj 2015年10月31日:下午5:52:08 com.bupt.qrj.dataobject.UserMeta
 *         unifyum-dal 用途: - 用户账户基本信息
 *
 */
public class arrfeedbackDO {

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







	private String mission_description;

	private String mission;
	private String cover_fields;
	private String mission_level;

	private String mission_source;
	private String factory_id;

	public String getFactory_id() {
		return factory_id;
	}

	public void setFactory_id(String factory_id) {
		this.factory_id = factory_id;
	}

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
	private String task_addition;

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	private String item;
	public String getTask_addition() {
		return task_addition;
	}

	public void setTask_addition(String task_addition) {
		this.task_addition = task_addition;
	}

	private String detail_info;
	private String mission_type;

	public String getMission_type() {
		return mission_type;
	}

	public void setMission_type(String mission_type) {
		this.mission_type = mission_type;
	}

	public String getSet_time() {
		return set_time;
	}

	public void setSet_time(String set_time) {
		this.set_time = set_time;
	}

	private String set_time;
	private String set_id;

	public String getSet_id() {
		return set_id;
	}

	public void setSet_id(String set_id) {
		this.set_id = set_id;
	}
}
