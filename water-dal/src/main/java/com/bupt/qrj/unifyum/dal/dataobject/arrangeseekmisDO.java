/**
 * 
 */
package com.bupt.qrj.unifyum.dal.dataobject;

/**
 * @author renjun.qrj 2015年10月31日:下午5:52:08 com.bupt.qrj.dataobject.UserMeta
 *
 *
 */
public class arrangeseekmisDO {





    public String getMission_condition() {
        return mission_condition;
    }

    public void setMission_condition(String mission_condition) {
        this.mission_condition = mission_condition;
    }


    public String getPeriod_start_time() {
        return period_start_time;
    }

    public void setPeriod_start_time(String period_start_time) {
        this.period_start_time = period_start_time;
    }

    private String period_start_time;

    private String mission_condition;

    public String getSet_start_time_code() {
        return set_start_time_code;
    }

    public void setSet_start_time_code(String set_start_time_code) {
        this.set_start_time_code = set_start_time_code;
    }

    private String set_start_time_code;
    public String getMission() {
        return mission;
    }

    public void setMission(String mission) {
        this.mission = mission;
    }

    private String mission;

    public String getMission_id() {
        return mission_id;
    }

    public void setMission_id(String mission_id) {
        this.mission_id = mission_id;
    }

    private String mission_id;
}
