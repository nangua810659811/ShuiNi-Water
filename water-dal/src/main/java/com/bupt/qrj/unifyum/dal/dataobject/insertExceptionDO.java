/**
 * 
 */
package com.bupt.qrj.unifyum.dal.dataobject;

/**
 * @author renjun.qrj 2015年10月31日:下午5:52:08 com.bupt.qrj.dataobject.UserMeta
 *         unifyum-dal 用途: - 用户账户基本信息
 *
 */
public class insertExceptionDO {


	public String getCheckpoint() {
		return checkpoint;
	}

	public void setCheckpoint(String checkpoint) {
		this.checkpoint = checkpoint;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getReport_time() {
		return report_time;
	}

	public void setReport_time(String report_time) {
		this.report_time = report_time;
	}

	public String getWorkshop() {
		return workshop;
	}

	public void setWorkshop(String workshop) {
		this.workshop = workshop;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getReport_worker() {
		return report_worker;
	}

	public void setReport_worker(String report_worker) {
		this.report_worker = report_worker;
	}

	private String checkpoint;



	private String description;


	private String report_time;

	private String workshop;


	private String level;

	private String report_worker;

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	private String pic;
}
