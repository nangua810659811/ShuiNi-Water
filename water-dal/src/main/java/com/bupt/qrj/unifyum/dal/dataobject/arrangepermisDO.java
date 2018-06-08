/**
 * 
 */
package com.bupt.qrj.unifyum.dal.dataobject;

/**
 * @author renjun.qrj 2015年10月31日:下午5:52:08 com.bupt.qrj.dataobject.UserMeta
 *
 *
 */
public class arrangepermisDO {


    public String getMission_condition() {
        return mission_condition;
    }

    public void setMission_condition(String mission_condition) {
        this.mission_condition = mission_condition;
    }


    private String period_start_time;
    private String mission_condition;

    private String worker_name;

    public String getWorker_name() {
        return worker_name;
    }

    public void setWorker_name(String worker_name) {
        this.worker_name = worker_name;
    }


    public String getPeriod_start_time() {
        return period_start_time;
    }

    public void setPeriod_start_time(String period_start_time) {
        this.period_start_time = period_start_time;
    }

    public String getMission_name() {
        return mission_name;
    }

    public void setMission_name(String mission_name) {
        this.mission_name = mission_name;
    }

    private String mission_name;

    public String getMission_id() {
        return mission_id;
    }

    public void setMission_id(String mission_id) {
        this.mission_id = mission_id;
    }

    private String mission_id;
}
