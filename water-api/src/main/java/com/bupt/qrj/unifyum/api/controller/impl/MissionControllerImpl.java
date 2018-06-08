/**
 * 
 */
package com.bupt.qrj.unifyum.api.controller.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bupt.qrj.unifyum.api.controller.MissionController;
import com.bupt.qrj.unifyum.dal.dao.impl.*;
import com.bupt.qrj.unifyum.dal.dataobject.*;
import com.bupt.qrj.unifyum.util.SendMessage;
import com.bupt.qrj.unifyum.util.http.HttpOutUtil;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.Date;

/**
 * @author renjun.qrj 2015年10月31日:下午8:43:02
 *         com.bupt.qrj.unifyum.api.controller.impl.UserMetaControllerImpl
 *         unifyum-api 用途:
 *
 */
@Controller
@RequestMapping("/mission.req")
public class MissionControllerImpl implements MissionController {

	/** 日志 **/
    private static final Logger LOGGER = LoggerFactory.getLogger(MissionControllerImpl.class);
    
    public static ApplicationContext getContext() {
		//获得Spring中定义的Bean实例，两个以上加 new String[]
		ApplicationContext context = new ClassPathXmlApplicationContext(
				new String[] { "META-INF/spring/unifyum-dal-dao.xml", "META-INF/spring/unifyum-dal-db.xml" });
		return context;
	}
    ApplicationContext context = getContext();

//, produces = "application/json; charset=utf-8"
    @RequestMapping(method = { RequestMethod.POST }, params = "action=MissionReturn")
    public void MissionReturn(HttpServletRequest request, HttpServletResponse response) {
    	
//    	ApplicationContext context=getContext();
    	MissionReturnDAOImpl MissionReturnDAO=(MissionReturnDAOImpl) context.getBean("MissionReturnDAO");

        JSONObject result = new JSONObject();
        result.put("result","10001");
        System.out.println("11");

        try {

        	String MissionId = request.getParameter("MissionId");

            if (MissionId==null||MissionId.isEmpty()) {
                result.put("errMsg","输入参数有误");
            } else {
                List<String> missionReturn = MissionReturnDAO.list(MissionId);
                if(missionReturn.isEmpty()){
                    result.put("errMsg","请联系重新设置页面");
                }else{
                    for(String mr : missionReturn){

                        String temp =  MissionReturnDAO.get(MissionId,mr);
                        MissionReturnDO missionReturnDO = new MissionReturnDO();
                        missionReturnDO.setColumn_key(mr);
                        missionReturnDO.setNote_content(temp);
                        MissionReturnDAO.update(missionReturnDO);
                    }

                    ArrayList<JSONObject> eventData = new ArrayList<JSONObject>();
                    System.out.println("1111");
                    String event_id = MissionReturnDAO.get_event(MissionId);
                    List<EventInfoDO> eventInfo = MissionReturnDAO.Event_list(event_id);
                    System.out.println(event_id);
                    for(EventInfoDO EventInfoDO : eventInfo){
                        JSONObject data1 = new JSONObject();
                        data1.put("event_ID",EventInfoDO.getEvent_id());
                        data1.put("event_name",EventInfoDO.getEvent_name());
                        data1.put("font_color",EventInfoDO.getFont_color());
                        data1.put("font_size",Integer.valueOf(EventInfoDO.getFont_size()));
                        data1.put("workshop",EventInfoDO.getWorkshop());
                        data1.put("check_point",EventInfoDO.getCheck_point());
//                        data1.put("work",JSONObject.toJSONString(EventInfoDO.getAdditions()));

                        data1.put("work",JSONArray.parse(EventInfoDO.getAdditions()));
//                        System.out.println(EventInfoDO.getAdditions());
                        eventData.add(data1);
                    }





                    ArrayList<JSONObject> noteData = new ArrayList<JSONObject>();
                    List<MissionReturnDO> noteInfo =MissionReturnDAO.Return_list("2","0");
                    for(MissionReturnDO MissionReturnDO : noteInfo){
                        JSONObject data2 = new JSONObject();
                        data2.put("note_name",MissionReturnDO.getNote_name());
                        data2.put("note_content",MissionReturnDO.getNote_content());
                        data2.put("font_color",MissionReturnDO.getFont_color());
                        data2.put("font_size",MissionReturnDO.getFont_size());
                        noteData.add(data2);
                    }

                    MissionReturnDO task_info =MissionReturnDAO.get_first();
                    JSONObject data = new JSONObject();
                    data.put("task_ID",MissionId);
                    data.put("font_size",task_info.getFont_size());
                    data.put("font_color",task_info.getFont_color());
                    data.put("note",noteInfo);
                    data.put("event",eventData);

                    result.put("data",data);
                    result.put("essMsg","10000");
                    result.put("result","10000");
                }
            }
        } catch (Exception e) {
            result.put("essMsg", e.getMessage());
            LOGGER.warn("exception when login: " + e.getMessage());
        }
        // 输出结果
        HttpOutUtil.outData(response, JSONObject.toJSONString(result));
    }


    /*
    *1.3 1.3	查询未接收任务单
    *
    * */
    @RequestMapping(method = { RequestMethod.POST }, params = "action=seekwjs")
    public void seekwjs(HttpServletRequest request, HttpServletResponse response) {

//        ApplicationContext context = getContext();
        SeekWjsDAOImpl SeekWjsDAO = (SeekWjsDAOImpl) context.getBean("SeekWjsDAO");

//        JSONObject result = new JSONObject();


        JSONObject data = new JSONObject();
        data.put("result", "10001");
        try {

            String phone = request.getParameter("phone");



            if (phone == null || phone.isEmpty() ) {
                data.put("errMsg", "失败");
                data.put("result","10001");
            } else {
                // 获取用户的数据

                List<SeekWjsDO> seekwjs = SeekWjsDAO.list(phone);
                if (seekwjs.isEmpty()) {
                    data.put("result", 10002);
                    data.put("errMsg", "没有数据");
                    System.out.println("数据库为空");
                } else {
                    data.put("result", "10000");
                    data.put("errMsg", "成功");
                    System.out.println("seek-wjs-ok");
                    ArrayList<JSONObject> seekwjsdata = new ArrayList<JSONObject>();

                    for (SeekWjsDO seekwjsDO : seekwjs){
                        JSONObject data1 = new JSONObject();
                        data1.put("taskID", seekwjsDO.getMission_id());
                        String temp = seekwjsDO.getMission_condition();
                        String status = "";
                        if(temp.equals("1"))
                            status = "未接收";
                        else if(temp.equals("3"))
                            status = "接收";
                        else if(temp.equals("4"))
                            status = "执行中";
                        else
                            continue;
                        data1.put("status", status);
                        String describe = "";
                        if(seekwjsDO.getMission_addition().equals("0"))
                            describe = "暂无信息";
                        data1.put("describe", describe);


                        seekwjsdata.add(data1);
                    }
                    data.put("orders", seekwjsdata);
                }

            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
//        data.put("result", result);
        // 输出结果
        HttpOutUtil.outData(response, JSONObject.toJSONString(data));
    }

    @RequestMapping(method = { RequestMethod.POST }, params = "action=mapinfo")
    public void mapinfo(HttpServletRequest request, HttpServletResponse response) {

        //ApplicationContext context = getContext();
        MapInfoDAOImpl MapInfoDAO = (MapInfoDAOImpl) context.getBean("MapInfoDAO");

        JSONObject result = new JSONObject();
        result.put("result", "10001");

        JSONObject data = new JSONObject();
        try {

            String worker_phone = request.getParameter("phone");



            if (worker_phone == null || worker_phone.isEmpty()) {
                result.put("errMsg", "失败");
                result.put("result","10001");
            } else {
                // 获取用户的数据

                String worker_team = MapInfoDAO.get_team(worker_phone);
                if (worker_team.isEmpty()) {
                    result.put("result", 10002);
                    result.put("errMsg", "没有数据");
                    System.out.println("数据库为空");
                } else {
                    result.put("result", "10000");
                    result.put("errMsg", "成功");
                    List<MapHumanInfoDO> mapHumanInfoDO = MapInfoDAO.list(worker_team);

                    ArrayList<JSONObject> Humandata = new ArrayList<JSONObject>();
                    for (MapHumanInfoDO mapHumanInfo : mapHumanInfoDO){
                        JSONObject data1 = new JSONObject();
                        data1.put("friend_phone",mapHumanInfo.getPhone());
                        data1.put("friend_name",mapHumanInfo.getName());
                        data1.put("friend_longitude",mapHumanInfo.getLongitude());
                        data1.put("friend_latitude",mapHumanInfo.getLatitude());
                        Humandata.add(data1);
                    }
                    data.put("human_data", Humandata);

                    List<MapEventInfoDO> mapEventInfoDO = MapInfoDAO.Event_list();
                    ArrayList<JSONObject> Eventdata = new ArrayList<JSONObject>();

                    for (MapEventInfoDO mapEventInfo : mapEventInfoDO){
                        JSONObject data2 = new JSONObject();
                        data2.put("event_id", mapEventInfo.getEvent_id());
                        data2.put("event_name", mapEventInfo.getEvent_name());
                        data2.put("event_longitude", mapEventInfo.getLongitude());
                        data2.put("event_latitude", mapEventInfo.getLatitude());
                        Eventdata.add(data2);
                    }
                    data.put("event_data", Eventdata);
                }
                result.put("result","10000");
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        data.put("result", result);
        // 输出结果
        HttpOutUtil.outData(response, JSONObject.toJSONString(data));
    }


    @RequestMapping(method = { RequestMethod.POST }, params = "action=setMission")
    public void setMission(HttpServletRequest request, HttpServletResponse response) {

//    	ApplicationContext context=getContext();
        setMissionDAOImpl setMissionDAO=(setMissionDAOImpl) context.getBean("setMissionDAO");

        JSONObject result = new JSONObject();
        result.put("result","10001");


        try {

            String id = request.getParameter("taskname");

            if (id==null||id.isEmpty()) {
                result.put("errMsg","输入参数有误");
            } else {
                List<String> missionReturn = setMissionDAO.list0(id);

                for(String mr : missionReturn){

                    String temp =  setMissionDAO.get0(id,mr);
                    setMissionDO setMissionDO = new setMissionDO();
                    setMissionDO.setValue(mr);
                    setMissionDO.setContent(temp);
                    setMissionDAO.update(setMissionDO);
                }

                //formatinspection
                //addedittext
                String type = "1";
                JSONObject formatinspection = new JSONObject();
                List<setMissionDO> inedit = setMissionDAO.List(type);
                ArrayList<JSONObject> formatinspectionedit = new ArrayList<JSONObject>();
                if(inedit.isEmpty()){
                    result.put("errMsg","请联系重新设置页面");
                }else{
                    for(setMissionDO ie : inedit){
                        JSONObject data1 = new JSONObject();
                        data1.put("edittext_name",ie.getName());
                        data1.put("edittext_content",ie.getContent());
                        data1.put("font_color",ie.getFont_color());
                        data1.put("font_size",ie.getFont_size());
                        formatinspectionedit.add(data1);
                    }
                    formatinspection.put("addedittext",formatinspectionedit);
                    //addspinner
                    type = "2";
                    System.out.println(type);
                    List<setMissionDO> inspinner = setMissionDAO.List(type);
                    ArrayList<JSONObject> formatinspectionspinner = new ArrayList<JSONObject>();
                    for(setMissionDO is : inspinner){
                        JSONObject data2 = new JSONObject();
                        data2.put("spinner_name",is.getName());
                        data2.put("spinner_content",is.getContent());
                        data2.put("spinner_array",is.getArray());
                        data2.put("font_color",is.getFont_color());
                        data2.put("font_size",is.getFont_size());
                        formatinspectionspinner.add(data2);
                    }
                    formatinspection.put("addspinner",formatinspectionspinner);
                    //addbutton
                    ArrayList<JSONObject> formatinspectionbutton= new ArrayList<JSONObject>();

                    type = "2";

                    List<materialDO> inbutton00 = setMissionDAO.list1(type);
                    ArrayList<JSONObject> formatinspectionbuttoncontent1 = new ArrayList<JSONObject>();
                    for(materialDO ib1 : inbutton00){
                        JSONObject data3 = new JSONObject();
                        data3.put("content_name",ib1.getName());
                        data3.put("content_isselect","false");
                        formatinspectionbuttoncontent1.add(data3);
                    }
                    System.out.println(type);
                    type = "7";
                    String array = "1";
                    setMissionDO inbutton0 = setMissionDAO.get(type,array);
                    JSONObject databutton0 = new JSONObject();
                    databutton0.put("button_name","工具列表");
                    databutton0.put("button_content","选择工具");
                    databutton0.put("needtextview","true");
                    databutton0.put("needshownum","false");
                    databutton0.put("content",formatinspectionbuttoncontent1);
                    databutton0.put("font_color",inbutton0.getFont_color());
                    databutton0.put("font_size",inbutton0.getFont_size());
                    formatinspectionbutton.add(databutton0);
                    System.out.println(type);
                    type = "1";
                    List<materialDO> inbutton11 = setMissionDAO.list1(type);
                    ArrayList<JSONObject> formatinspectionbuttoncontent2 = new ArrayList<JSONObject>();
                    for(materialDO ib1 : inbutton11){
                        JSONObject data3 = new JSONObject();
                        data3.put("content_name",ib1.getName());
                        data3.put("content_isselect","0");
                        formatinspectionbuttoncontent2.add(data3);
                    }
                    System.out.println(type);
                    type = "7";
                    array = "2";
                    setMissionDO inbutton1 = setMissionDAO.get(type,array);
                    JSONObject databutton1 = new JSONObject();
                    databutton1.put("button_name","配件列表");
                    databutton1.put("button_content","选择配件");
                    databutton1.put("needtextview","true");
                    databutton1.put("needshownum","true");
                    databutton1.put("content",formatinspectionbuttoncontent2);
                    databutton1.put("font_color",inbutton1.getFont_color());
                    databutton1.put("font_size",inbutton1.getFont_size());
                    formatinspectionbutton.add(databutton1);
                    System.out.println(type);
                    List<EventInfoDO> inbutton22 = setMissionDAO.Event_list();
                    ArrayList<JSONObject> formatinspectionbuttoncontent3 = new ArrayList<JSONObject>();
                    for(EventInfoDO ib2 : inbutton22){
                        JSONObject data4 = new JSONObject();
                        data4.put("content_name",ib2.getEvent_name());
                        data4.put("content_note",ib2.getAdditions());
                        data4.put("content_id",ib2.getEvent_id());
                        data4.put("content_isselect","false");
                        formatinspectionbuttoncontent3.add(data4);
                    }
                    System.out.println(type);
                    type = "7";
                    array = "3";
                    setMissionDO inbutton2 = setMissionDAO.get(type,array);
                    JSONObject databutton2 = new JSONObject();
                    databutton2.put("button_name","任务事件");
                    databutton2.put("button_content","选择事件");
                    databutton2.put("needtextview","false");
                    databutton2.put("needshownum","false");
                    databutton2.put("content",formatinspectionbuttoncontent3);
                    databutton2.put("font_color",inbutton2.getFont_color());
                    databutton2.put("font_size",inbutton2.getFont_size());
                    formatinspectionbutton.add(databutton2);
                    System.out.println(type);
                    formatinspection.put("addbutton",formatinspectionbutton);


                    ArrayList<JSONObject> inspectioncheck= new ArrayList<JSONObject>();
                    type = "3";
                    List<setMissionDO> incheck = setMissionDAO.List(type);
                    for(setMissionDO ic : incheck){
                        JSONObject data5 = new JSONObject();
                        data5.put("content_name",ic.getName());
                        data5.put("content_isselect","false");
                        data5.put("font_color",ic.getFont_color());
                        data5.put("font_size",ic.getFont_size());
                        inspectioncheck.add(data5);
                    }
                    formatinspection.put("addcheck",inspectioncheck);
                    System.out.println(type);

//
//                    //formatrepair
//                    type = "4";
//                    JSONObject formatrepair = new JSONObject();
//                    List<setMissionDO> reedit = setMissionDAO.List(type);
//                    ArrayList<JSONObject> formatrepairedit = new ArrayList<JSONObject>();
//                    System.out.println(type);
//                    for(setMissionDO ie : reedit){
//                        JSONObject data9 = new JSONObject();
//                        data9.put("edittext_name",ie.getName());
//                        data9.put("edittext_content",ie.getContent());
//                        data9.put("font_color",ie.getFont_color());
//                        data9.put("font_size",ie.getFont_size());
//                        formatrepairedit.add(data9);
//                    }
//                    formatrepair.put("addedittext",formatrepairedit);
//                    //addspinner
//                    System.out.println(type);
//                    type = "5";
//                    List<setMissionDO> respinner = setMissionDAO.List(type);
//                    ArrayList<JSONObject> formatrepairspinner = new ArrayList<JSONObject>();
//                    for(setMissionDO rs : respinner){
//                        JSONObject data8 = new JSONObject();
//                        data8.put("spinner_name",rs.getName());
//                        data8.put("spinner_content",rs.getContent());
//                        data8.put("spinner_array",rs.getArray());
//                        data8.put("font_color",rs.getFont_color());
//                        data8.put("font_size",rs.getFont_size());
//                        formatrepairspinner.add(data8);
//                    }
//                    formatinspection.put("addspinner",formatrepairspinner);
//                    //addbutton
//                    ArrayList<JSONObject> formatrepairbutton= new ArrayList<JSONObject>();
//                    System.out.println(type);
//                    type = "2";
//
//                    List<materialDO> rebutton00 = setMissionDAO.list1(type);
//                    ArrayList<JSONObject> formatrepairbuttoncontent1 = new ArrayList<JSONObject>();
//                    for(materialDO rb1 : rebutton00){
//                        JSONObject data3 = new JSONObject();
//                        data3.put("content_name",rb1.getName());
//                        data3.put("content_isselect","false");
//                        formatrepairbuttoncontent1.add(data3);
//                    }
//                    System.out.println(type);
//                    type = "8";
//                    array = "1";
//                    setMissionDO rebutton0 = setMissionDAO.get(type,array);
//                    JSONObject databutton9 = new JSONObject();
//                    databutton9.put("button_name","工具列表");
//                    databutton9.put("button_content","选择工具");
//                    databutton9.put("content",formatrepairbuttoncontent1);
//                    databutton9.put("font_color",rebutton0.getFont_color());
//                    databutton9.put("font_size",rebutton0.getFont_size());
//                    formatrepairbutton.add(databutton9);
//                    System.out.println(type);
//                    type = "1";
//                    List<materialDO> rebutton11 = setMissionDAO.list1(type);
//                    ArrayList<JSONObject> formatrepairbuttoncontent2 = new ArrayList<JSONObject>();
//                    for(materialDO rb1 : rebutton11){
//                        JSONObject data7 = new JSONObject();
//                        data7.put("content_name",rb1.getName());
//                        data7.put("content_isselect","0");
//                        formatrepairbuttoncontent2.add(data7);
//                    }
//                    System.out.println(type);
//                    type = "8";
//                    array = "2";
//                    setMissionDO rebutton1 = setMissionDAO.get(type,array);
//                    JSONObject databutton6 = new JSONObject();
//                    databutton6.put("button_name","配件列表");
//                    databutton6.put("button_content","选择配件");
//                    databutton6.put("content",formatrepairbuttoncontent2);
//                    databutton6.put("font_color",rebutton1.getFont_color());
//                    databutton6.put("font_size",rebutton1.getFont_size());
//                    formatrepairbutton.add(databutton6);
////                    System.out.println(type);
//                    List<EventInfoDO> rebutton22 = setMissionDAO.Event_list();
//                    ArrayList<JSONObject> formatrepairbuttoncontent3 = new ArrayList<JSONObject>();
//                    for(EventInfoDO ib2 : rebutton22){
//                        JSONObject data4 = new JSONObject();
//                        data4.put("content_name",ib2.getEvent_name());
//                        data4.put("content_note",ib2.getAdditions());
//                        data4.put("content_isselect","false");
//                        formatrepairbuttoncontent3.add(data4);
//                    }
//                    System.out.println(type);
//                    type = "8";
//                    array = "3";
//                    setMissionDO rebutton2 = setMissionDAO.get(type,array);
//                    JSONObject databutton8 = new JSONObject();
//                    databutton8.put("button_name","任务事件");
//                    databutton8.put("button_content","选择事件");
//                    databutton8.put("content",formatrepairbuttoncontent3);
//                    databutton8.put("font_color",rebutton2.getFont_color());
//                    databutton8.put("font_size",rebutton2.getFont_size());
//                    formatrepairbutton.add(databutton8);
////                    System.out.println(type);
//                    formatrepair.put("addbutton",formatrepairbutton);
//
//
//                    ArrayList<JSONObject> repaircheck= new ArrayList<JSONObject>();
//                    type = "6";
//                    List<setMissionDO> recheck = setMissionDAO.List(type);
//                    for(setMissionDO rc : recheck){
//                        JSONObject data5 = new JSONObject();
//                        data5.put("content_name",rc.getName());
//                        data5.put("content_isselect","false");
//                        data5.put("font_color",rc.getFont_color());
//                        data5.put("font_size",rc.getFont_size());
//                        repaircheck.add(data5);
//                    }
//                    formatrepair.put("addcheck",repaircheck);
//                    System.out.println(type);
//                    ArrayList<JSONObject> tasklist = new ArrayList<JSONObject>();
//                    List<setMissioninfoDO> missioninfo =setMissionDAO.Mission_list();
//                    for(setMissioninfoDO mi : missioninfo){
//                        JSONObject data10 = new JSONObject();
//                        data10.put("task_name",mi.getMission());
//                        data10.put("task_num",mi.getMission_id());
//                        tasklist.add(data10);
//                    }
                    JSONObject data = new JSONObject();
//                    data.put("tasklist",tasklist);
//                    data.put("tasknature","巡检任务;维修任务");
                    data.put("format",formatinspection);
//                    data.put("formatrepair",formatrepair);
//                    System.out.println(type);
                    result.put("data",data);
                    result.put("essMsg","10000");
                    result.put("result","10000");
                }
            }
        } catch (Exception e) {
            result.put("essMsg", e.getMessage());
            LOGGER.warn("exception when login: " + e.getMessage());
        }
        // 输出结果
        HttpOutUtil.outData(response, JSONObject.toJSONString(result));
    }


    	/*
	* 登录
	*
	* */
	@RequestMapping(method = { RequestMethod.POST }, params = "action=login")
	public void login(HttpServletRequest request, HttpServletResponse response) {

		//ApplicationContext context = getContext();
		workerloginDAOImpl workerloginDAO = (workerloginDAOImpl) context.getBean("workerloginDAO");

		JSONObject result = new JSONObject();
		result.put("result", "10001");


		try {

			String phone = request.getParameter("phone");
			String password = request.getParameter("password");
            String code = request.getParameter("code");

            //System.out.println(phone);
            //System.out.println(password);
            // 先是参数检查
			if (phone == null || phone.isEmpty() || password == null || password.isEmpty()) {
				result.put("errMsg", "输入参数有误");
				result.put("result","10004");
			} else {

                    // second time and so on
                    workerLoginDO workerloginDO = new workerLoginDO();
                    workerloginDO.setPhone(phone);

                    workerLoginDO user = workerloginDAO.get(workerloginDO);

                    if (user == null) {
                        result.put("errMsg", "用户名输入错误");
                        result.put("result","10002");
                    } else {
                        // 检查用户密码是否正确
                        if (password.equals(user.getPassword())&&code.equals(user.getRandom())) {
                            result.put("result","10000");
                            result.put("errMsg", "登录成功");

                        } else {
                            result.put("errMsg", "密码或验证码错误");
                            result.put("result","10003");
                        }
                    }
			}
		} catch (Exception e) {
			result.put("essMsg", e.getMessage());
		}
		HttpOutUtil.outData(response, JSONObject.toJSONString(result));
	}



/*
* send Message
* */
    @RequestMapping(method = { RequestMethod.POST }, params = "action=sendMessage")
    public void sendMessage(HttpServletRequest request, HttpServletResponse response) {

        workerloginDAOImpl workerloginDAO = (workerloginDAOImpl) context.getBean("workerloginDAO");

        JSONObject result = new JSONObject();
        result.put("result", "10001");

        try {

            String phone = request.getParameter("phone");

            // 先是参数检查
            String random = Integer.toString((int)((Math.random()*9+1)*1000));

            if (phone == null || phone.isEmpty()) {
                result.put("errMsg", "输入参数有误");
                result.put("result","10004");
            } else {
                workerLoginDO workerloginDO = new workerLoginDO();
                workerloginDO.setPhone(phone);
                workerloginDO.setRandom(random);
                workerloginDAO.update(workerloginDO);

                SendMessage.sendMessage(phone, "您的随机验证码为：" + random);
                result.put("errMsg", "chenggong");
                result.put("result", "10000");
            }
        } catch (Exception e) {
            result.put("essMsg", e.getMessage());
        }
        HttpOutUtil.outData(response, JSONObject.toJSONString(result));
    }


    /*
*审核查询
*
* */
    @RequestMapping(method = { RequestMethod.POST }, params = "action=checkInfo")
    public void checkInfo(HttpServletRequest request, HttpServletResponse response) {

        //ApplicationContext context = getContext();
        checkInfoDAOImpl checkInfoDAO = (checkInfoDAOImpl) context.getBean("checkInfoDAO");

        JSONObject result = new JSONObject();
        result.put("result", "10001");
        int i=0;
        JSONObject data = new JSONObject();
        try {

            String phone = request.getParameter("phone");



            if (phone == null || phone.isEmpty() ) {
                result.put("errMsg", "失败");
                result.put("result","10001");
            } else {
                // 获取用户的数据

                List<checkInfoDO> readytodo = checkInfoDAO.list();
                if (readytodo.isEmpty()) {
                    result.put("result", 10002);
                    result.put("errMsg", "没有数据");
                    System.out.println("数据库为空");
                } else {
                    result.put("result", "10000");
                    result.put("errMsg", "成功");
                    System.out.println("checkInfo");
                    ArrayList<JSONObject> readytododata = new ArrayList<JSONObject>();

                    for (checkInfoDO checkInfoDO : readytodo){
                        JSONObject data1 = new JSONObject();
                        data1.put("finish_time", checkInfoDO.getFinish_time());
                        data1.put("mission_id", checkInfoDO.getMission_id());
                        data1.put("worker", checkInfoDO.getWorker_name());
                        i++;

                        readytododata.add(data1);
                    }
                    data.put("data", readytododata);
                }

            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        data.put("number",i);
        data.put("result", result);
        // 输出结果
        HttpOutUtil.outData(response, JSONObject.toJSONString(data));
    }


    /*
    *1.6 位置上传
    *
    * */
    @RequestMapping(method = { RequestMethod.POST }, params = "action=location")
    public void location(HttpServletRequest request, HttpServletResponse response) {

        //ApplicationContext context = getContext();
        locationDAOImpl locationDAO = (locationDAOImpl) context.getBean("locationDAO");

        JSONObject result = new JSONObject();
        result.put("result", "10001");


        try {

            String worker_phone = request.getParameter("phone");
            String longitude = request.getParameter("longitude");
            String latitude = request.getParameter("latitude");
            String time = request.getParameter("time");
            String mission_id = request.getParameter("mission_id");


            if (worker_phone == null || worker_phone.isEmpty() ||longitude == null || longitude.isEmpty() ||latitude == null || latitude.isEmpty()
                    ||time == null || time.isEmpty()) {
                result.put("errMsg", "输入参数有误");
                result.put("result","10001");
            } else {

                // get worker name
//                locationnameDO locationnameDO = new locationnameDO();
//                locationnameDO.setPhone(worker_phone);
//                locationnameDO usr = locationDAO.getname(locationnameDO);
//                String worker_name = usr.getName();
//                System.out.println(worker_name);
                // find isn't it have?
                locationnameDO find = new locationnameDO();
                find.setPhone(worker_phone);
                locationnameDO id = locationDAO.get(find);

                locationinsertDO li = new locationinsertDO();
                li.setId(id.getId());
                li.setLatitude(latitude);
                li.setLongitude(longitude);
                li.setMission_id(mission_id);
                locationDAO.insert(li);
                    //have,update
                    locationDO locationDO = new locationDO();
                    locationDO.setPhone(worker_phone);
                    locationDO.setLatitude(latitude);
                    locationDO.setLongitude(longitude);
                    locationDO.setTime(time);
                    locationDAO.update(locationDO);
                    System.out.println("newest-location-update-ok");
                    result.put("errMsg", "保存成功！");
                    result.put("result","10000");

            }
        } catch (Exception e) {
            result.put("essMsg", e.getMessage());

        }
        // 输出结果
        HttpOutUtil.outData(response, JSONObject.toJSONString(result));
    }

    //change mission condition
    @RequestMapping(method = { RequestMethod.POST }, params = "action=condition")
    public void condition(HttpServletRequest request, HttpServletResponse response) {

        //ApplicationContext context = getContext();
        conditionDAOImpl conditionDAO = (conditionDAOImpl) context.getBean("conditionDAO");

        JSONObject result = new JSONObject();
        result.put("result", "10001");


        try {

            String mission_id = request.getParameter("mission_id");
            String condition = request.getParameter("condition");



            if (mission_id == null || mission_id.isEmpty() ||condition == null || condition.isEmpty() ) {
                result.put("errMsg", "输入参数有误");
                result.put("result","10001");
            } else {

                //have,update
                misconditionDO misconditionDO = new misconditionDO();
                misconditionDO.setMission_id(mission_id);
                misconditionDO.setMission_condition(condition);
                conditionDAO.update(misconditionDO);
                System.out.println("newest-condition-update-ok");
                result.put("errMsg", "保存成功！");
                result.put("result","10000");


            }
        } catch (Exception e) {
            result.put("essMsg", e.getMessage());

        }
        // 输出结果
        HttpOutUtil.outData(response, JSONObject.toJSONString(result));
    }


    @RequestMapping(method = { RequestMethod.POST }, params = "action=search")
    public void search(HttpServletRequest request, HttpServletResponse response) {

        //ApplicationContext context = getContext();
        searchDAOImpl searchDAO = (searchDAOImpl) context.getBean("searchDAO");

        JSONObject result = new JSONObject();
        result.put("result", "10001");

        try {

            String time1 = request.getParameter("time1");
            String time2 = request.getParameter("time2");
            String worker = request.getParameter("worker");
            String mission_name = request.getParameter("mission_name");
            String mission_condition = request.getParameter("mission_condition");
            String mission_type = request.getParameter("mission_type");


            String timemin = time1 +" 00:00:00";
            String timemax = time2 +" 00:00:00";
            if(worker==null||worker.isEmpty())
                worker="";
            if(mission_condition==null||mission_condition.isEmpty())
                mission_condition="";
            if(mission_name==null||mission_name.isEmpty())
                mission_name="";
            if (mission_type==null||mission_type.isEmpty())
                mission_type="";


            List<searchDO> search = searchDAO.list(worker,mission_name,mission_type,mission_condition,timemin,timemax);
            if (search.isEmpty()) {
                result.put("result", 10002);
                result.put("errMsg", "没有数据");
                System.out.println("数据库为空");
            } else {
                result.put("result", "10000");
                result.put("errMsg", "成功");
                System.out.println("search-to-do");
                ArrayList<JSONObject> searchdata = new ArrayList<JSONObject>();

                for (searchDO searchDO : search){
                    JSONObject data1 = new JSONObject();
                    data1.put("mission_id", searchDO.getMission_id());
                    data1.put("worker", searchDO.getWorker_name());
                    data1.put("finish_time",searchDO.getFinish_time());

                    data1.put("mission_condition",searchDO.getMission_condition());

                    searchdata.add(data1);
                }
                result.put("data", searchdata);
            }


        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        // 输出结果
        HttpOutUtil.outData(response, JSONObject.toJSONString(result));
    }




    @RequestMapping(method = { RequestMethod.POST }, params = "action=poyuntasklist")
    public void poyuntasklist(HttpServletRequest request, HttpServletResponse response) {

        //ApplicationContext context = getContext();
//        searchDAOImpl searchDAO = (searchDAOImpl) context.getBean("searchDAO");

        JSONObject result = new JSONObject();
        result.put("result", "10001");
        JSONObject data = new JSONObject();
        try {





            result.put("result", "10000");
            result.put("errMsg", "成功");

            String search = "\"tasklist\":[\n" +
                    "\t\t{\n" +
                    "\t\t\t\"task_name\":\"巡检任务\",\n" +
                    "\t\t\t\"task_num\":\"1\"\n" +
                    "\t\t},\n" +
                    "\t\t{\n" +
                    "\t\t\t\"task_name\":\"巡检任务1\",\n" +
                    "\t\t\t\"task_num\":\"2\"\n" +
                    "\t\t},\n" +
                    "\t\t{\n" +
                    "\t\t\t\"task_name\":\"巡检任务2\",\n" +
                    "\t\t\t\"task_num\":\"3\"\n" +
                    "\t\t},\n" +
                    "\t\t{\n" +
                    "\t\t\t\"task_name\":\"巡检任务3\",\n" +
                    "\t\t\t\"task_num\":\"4\"\n" +
                    "\t\t}\n" +
                    "\t],\n" +
                    "\t\"tasknature\":[\n" +
                    "\t\t\"巡检任务\",\n" +
                    "\t\t\"维修任务\"\n" +
                    "\t]";
            JSONObject ss = new JSONObject();
            JSONArray tasklist = new JSONArray();
            JSONObject t1 = new JSONObject();
            t1.put("task_name","巡检任务");
            t1.put("task_num","1");
            tasklist.add(t1);
            JSONObject t2 = new JSONObject();
            t2.put("task_name","巡检任务1");
            t2.put("task_num","2");
            tasklist.add(t2);
            JSONObject t3 = new JSONObject();
            t3.put("task_name","巡检任务2");
            t3.put("task_num","3");
            tasklist.add(t3);
            JSONObject t4 = new JSONObject();
            t4.put("task_name","巡检任务3");
            t4.put("task_num","4");
            tasklist.add(t4);

            JSONArray tasknature = new JSONArray();
            tasknature.add("巡检任务");
            tasknature.add("维修任务");
            //ss = .JSONObject.toJSONString(search)
            data.put("tasklist", tasklist);
            data.put("tasknature", tasknature);




        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        data.put("result", result);
        // 输出结果
        HttpOutUtil.outData(response, JSONObject.toJSONString(data));
    }




    @RequestMapping(method = { RequestMethod.POST }, params = "action=poyunupload")
    public void poyunupload(HttpServletRequest request, HttpServletResponse response) {

        //ApplicationContext context = getContext();
//        searchDAOImpl searchDAO = (searchDAOImpl) context.getBean("searchDAO");

        JSONObject result = new JSONObject();
        result.put("result", "10001");
        JSONObject data = new JSONObject();
        try {





            result.put("result", "10000");
            result.put("errMsg", "成功");


            JSONArray edit = new JSONArray();
            JSONObject e1 = new JSONObject();
            e1.put("name","任务序号");
            e1.put("content","4");
            edit.add(e1);
            JSONObject e2 = new JSONObject();
            e2.put("name","任务名称");
            e2.put("content","巡检任务");
            edit.add(e2);
            JSONObject e3 = new JSONObject();
            e3.put("name","任务描述");
            e3.put("content","巡查目的地");
            edit.add(e3);
            JSONObject e4 = new JSONObject();
            e4.put("name","所涵盖地点");
            e4.put("content","车间一车间二");
            edit.add(e4);
            JSONArray spinner = new JSONArray();
            JSONObject s1 = new JSONObject();
            s1.put("name","任务级别");
            s1.put("scontent","班组");
            spinner.add(s1);
            JSONObject s3 = new JSONObject();
            s3.put("name","身份验证");
            s3.put("content","指纹验证");
            spinner.add(s3);
            JSONObject s2 = new JSONObject();
            s2.put("spinner_name","任务来源");
            s2.put("content","系统原因");
            spinner.add(s2);

            JSONArray parets = new JSONArray();
            JSONObject p1 = new JSONObject();
            p1.put("name","配件1");
            p1.put("content","1");
            parets.add(p1);
            JSONObject p2 = new JSONObject();
            p2.put("name","配件2");
            p2.put("content","2");
            parets.add(p2);
            JSONObject p3 = new JSONObject();
            p3.put("name","配件3");
            p3.put("content","3");
            parets.add(p3);
            JSONObject p4 = new JSONObject();
            p4.put("name","配件4");
            p4.put("content","4");
            parets.add(p4);
            JSONObject p5 = new JSONObject();
            p5.put("name","配件5");
            p5.put("content","5");
            parets.add(p5);

            JSONArray event = new JSONArray();
            JSONObject ee1 = new JSONObject();
            ee1.put("name","事件1");
            ee1.put("content","1");
            event.add(ee1);
            JSONObject ee2 = new JSONObject();
            ee2.put("name","事件2");
            ee2.put("content","2");
            event.add(ee2);
            JSONArray tool = new JSONArray();
            JSONObject t1 = new JSONObject();
            t1.put("name","工具1");
            t1.put("content","true");
            tool.add(t1);
            JSONObject t2 = new JSONObject();
            t2.put("name","工具2");
            t2.put("content","true");
            tool.add(t2);


            data.put("edit", edit);
            data.put("spinner", spinner);data.put("parets", parets);
            data.put("event", event);data.put("tool", tool);


        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        data.put("result", result);
        // 输出结果
        HttpOutUtil.outData(response, JSONObject.toJSONString(data));
    }

    @RequestMapping(method = { RequestMethod.POST }, params = "action=arrangeformat")
    public void poyunformat(HttpServletRequest request, HttpServletResponse response) {

        //ApplicationContext context = getContext();
//        searchDAOImpl searchDAO = (searchDAOImpl) context.getBean("searchDAO");

        JSONObject result = new JSONObject();
        result.put("result", "10001");
        JSONObject data = new JSONObject();
        try {





            result.put("result", "10000");
            result.put("errMsg", "成功");




            JSONArray format = new JSONArray();
            JSONObject f1 = new JSONObject();
            f1.put("edittext_name","执行人");
            f1.put("edittext_content","张三");
            f1.put("font_color","#080808");
            f1.put("font_size",20);
            format.add(f1);
            JSONObject f2 = new JSONObject();
            f2.put("edittext_name","所在班组");
            f2.put("edittext_content","班组一");
            f2.put("font_color","#080808");
            f2.put("font_size",20);
            format.add(f2);
            JSONObject f3 = new JSONObject();
            f3.put("edittext_name","任务序号");
            f3.put("edittext_content","4");
            f3.put("font_color","#080808");
            f3.put("font_size",20);
            format.add(f3);
            JSONObject f4 = new JSONObject();
            f4.put("edittext_name","任务名称");
            f4.put("edittext_content","巡检任务");
            f4.put("font_color","#080808");
            f4.put("font_size",20);
            format.add(f4);
            JSONObject f5 = new JSONObject();
            f5.put("edittext_name","任务描述");
            f5.put("edittext_content","按要求巡查目的地");
            f5.put("font_color","#080808");
            f5.put("font_size",20);
            format.add(f5);
            JSONObject f6 = new JSONObject();
            f6.put("edittext_name","所涵盖地点");
            f6.put("edittext_content","车间一车间二");
            f6.put("font_color","#080808");
            f6.put("font_size",20);
            format.add(f6);
            JSONObject f7 = new JSONObject();
            f7.put("edittext_name","任务级别");
            f7.put("edittext_content","班组");
            f7.put("font_color","#080808");
            f7.put("font_size",20);
            format.add(f7);
            JSONObject f8 = new JSONObject();
            f8.put("edittext_name","任务来源");
            f8.put("edittext_content","系统原因");
            f8.put("font_color","#080808");
            f8.put("font_size",20);
            format.add(f8);
            JSONObject f9 = new JSONObject();
            f9.put("edittext_name","身份验证");
            f9.put("edittext_content","指纹验证");
            f9.put("font_color","#080808");
            f9.put("font_size",20);
            format.add(f9);
            JSONObject f10 = new JSONObject();
            f10.put("edittext_name","工具");
            f10.put("edittext_content","工具1,工具3,工具4,工具6,工具9,工具10");
            f10.put("font_color","#080808");
            f10.put("font_size",20);
            format.add(f10);
            JSONObject f11 = new JSONObject();
            f11.put("edittext_name","配件");
            f11.put("edittext_content","配件1(2),配件3(1),配件4(8),配件5(4),配件6(5)");
            f11.put("font_color","#080808");
            f11.put("font_size",20);
            format.add(f11);
            JSONObject f12 = new JSONObject();
            f12.put("edittext_name","事件");
            f12.put("edittext_content","事件1,事件3,事件4,事件5");
            f12.put("font_color","#080808");
            f12.put("font_size",20);
            format.add(f12);
            JSONObject f13 = new JSONObject();
            f13.put("edittext_name","审核结果");
            f13.put("edittext_content","通过");
            f13.put("font_color","#080808");
            f13.put("font_size",20);
            format.add(f13);
            JSONObject f14 = new JSONObject();
            f14.put("edittext_name","审核未通过原因");
            f14.put("edittext_content","无");
            f14.put("font_color","#080808");
            f14.put("font_size",20);
            format.add(f14);



            data.put("format", format);




        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        data.put("result", result);
        // 输出结果
        HttpOutUtil.outData(response, JSONObject.toJSONString(data));
    }

    @RequestMapping(method = { RequestMethod.POST }, params = "action=poyunformat")
    public void arrangeformat(HttpServletRequest request, HttpServletResponse response) {

        //ApplicationContext context = getContext();
//        searchDAOImpl searchDAO = (searchDAOImpl) context.getBean("searchDAO");

        JSONObject result = new JSONObject();
        result.put("result", "10001");
        JSONObject data = new JSONObject();
        try {





            result.put("result", "10000");
            result.put("errMsg", "成功");



            JSONObject format = new JSONObject();
            JSONArray addedittext = new JSONArray();
            JSONObject f1 = new JSONObject();
            f1.put("edittext_name","任务序号");
            f1.put("edittext_content","");
            f1.put("font_color","#080808");
            f1.put("font_size",20);
            addedittext.add(f1);
            JSONObject f2 = new JSONObject();
            f2.put("edittext_name","任务名称");
            f2.put("edittext_content","");
            f2.put("font_color","#080808");
            f2.put("font_size",20);
            addedittext.add(f2);
            JSONObject f3 = new JSONObject();
            f3.put("edittext_name","任务描述");
            f3.put("edittext_content","");
            f3.put("font_color","#080808");
            f3.put("font_size",20);
            addedittext.add(f3);
            JSONObject f4 = new JSONObject();
            f4.put("edittext_name","所涵盖地点");
            f4.put("edittext_content","");
            f4.put("font_color","#080808");
            f4.put("font_size",20);
            addedittext.add(f4);
            format.put("addedittext",addedittext);

            JSONArray addspinner = new JSONArray();
            JSONObject a1 = new JSONObject();
            a1.put("spinner_name","任务级别");
            a1.put("spinner_content","无");
            a1.put("spinner_array","无;班组;车间;公司");
            a1.put("font_color","#080808");
            a1.put("font_size",20);
            addspinner.add(a1);
            JSONObject a2 = new JSONObject();
            a2.put("spinner_name","任务来源");
            a2.put("spinner_content","无");
            a2.put("spinner_array","无;系统原因;运行种异常情况;运转时间达到定检周期");
            a2.put("font_color","#080808");
            a2.put("font_size",20);
            addspinner.add(a2);
            JSONObject a3 = new JSONObject();
            a3.put("spinner_name","身份验证");
            a3.put("spinner_content","无");
            a3.put("spinner_array","无;指纹验证;面部识别");
            a3.put("font_color","#080808");
            a3.put("font_size",20);
            addspinner.add(a3);
            format.put("addspinner",addspinner);

            JSONArray addbutton = new JSONArray();
            JSONObject b1 = new JSONObject();
            b1.put("button_name","工具列表");
            b1.put("button_content","选择工具");
            b1.put("needtextview","true");
            b1.put("needshownum","false");
            b1.put("font_color","#080808");
            b1.put("font_size",20);
            JSONArray content = new JSONArray();
            JSONObject c1 = new JSONObject();
            c1.put("content_name","工具1");
            c1.put("content_isselect","false");
            content.add(c1);
            JSONObject c2 = new JSONObject();
            c2.put("content_name","工具2");
            c2.put("content_isselect","false");
            content.add(c2);
            JSONObject c3 = new JSONObject();
            c3.put("content_name","工具3");
            c3.put("content_isselect","false");
            content.add(c3);
            JSONObject c4 = new JSONObject();
            c4.put("content_name","工具4");
            c4.put("content_isselect","false");
            content.add(c4);
            JSONObject c5 = new JSONObject();
            c5.put("content_name","工具5");
            c5.put("content_isselect","false");
            content.add(c5);
            JSONObject c6 = new JSONObject();
            c6.put("content_name","工具6");
            c6.put("content_isselect","false");
            content.add(c6);
            b1.put("content",content);
            addbutton.add(b1);

            JSONObject b2 = new JSONObject();
            b2.put("button_name","配件列表");
            b2.put("button_content","选择配件");
            b2.put("needtextview","true");
            b2.put("needshownum","true");
            b2.put("font_color","#080808");
            b2.put("font_size",20);
            JSONArray content1 = new JSONArray();
            JSONObject c11 = new JSONObject();
            c11.put("content_name","配件1");
            c11.put("content_isselect",10);
            content1.add(c11);
            JSONObject c21 = new JSONObject();
            c21.put("content_name","配件2");
            c21.put("content_isselect",5);
            content1.add(c21);
            JSONObject c31 = new JSONObject();
            c31.put("content_name","配件3");
            c31.put("content_isselect",5);
            content1.add(c31);
            JSONObject c41 = new JSONObject();
            c41.put("content_name","配件4");
            c41.put("content_isselect",5);
            content1.add(c41);
            JSONObject c51 = new JSONObject();
            c51.put("content_name","配件5");
            c51.put("content_isselect",5);
            content1.add(c51);
            JSONObject c61 = new JSONObject();
            c61.put("content_name","配件6");
            c61.put("content_isselect",10);
            content1.add(c61);
            b2.put("content",content1);
            addbutton.add(b2);

            JSONObject b21 = new JSONObject();
            b21.put("button_name","任务事件");
            b21.put("button_content","选择事件");
            b21.put("needtextview","false");
            b21.put("needshownum","false");
            b21.put("font_color","#080808");
            b21.put("font_size",20);
            JSONArray content11 = new JSONArray();
            JSONObject c111 = new JSONObject();
            c111.put("content_name","事件1");
            c111.put("content_note","温度;仪表");
            c111.put("content_isselect","false");
            content11.add(c111);
            JSONObject c211 = new JSONObject();
            c211.put("content_name","事件2");
            c211.put("content_note","温度;仪表");
            c211.put("content_isselect","false");
            content11.add(c211);
            JSONObject c311 = new JSONObject();
            c311.put("content_name","事件3");
            c311.put("content_note","温度;仪表");
            c311.put("content_isselect","false");
            content11.add(c311);
            JSONObject c411 = new JSONObject();
            c411.put("content_name","事件4");
            c411.put("content_note","温度;仪表");
            c411.put("content_isselect","false");
            content11.add(c411);
            JSONObject c511 = new JSONObject();
            c511.put("content_name","事件5");
            c511.put("content_note","温度;仪表");
            c511.put("content_isselect","false");
            content11.add(c511);
            JSONObject c611 = new JSONObject();
            c611.put("content_name","事件6");
            c611.put("content_note","温度;仪表");
            c611.put("content_isselect","false");
            content11.add(c611);
            b21.put("content",content11);
            addbutton.add(b21);
            format.put("addbutton",addbutton);
            data.put("format", format);




        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        data.put("result", result);
        // 输出结果
        HttpOutUtil.outData(response, JSONObject.toJSONString(data));
    }



    @RequestMapping(method = { RequestMethod.POST }, params = "action=createformat")
    public void createformat(HttpServletRequest request, HttpServletResponse response) {


        setMissionDAOImpl setMissionDAO=(setMissionDAOImpl) context.getBean("setMissionDAO");

        JSONObject result = new JSONObject();
        result.put("result", "10001");
        JSONObject data = new JSONObject();
        try {

            result.put("result", "10000");
            result.put("errMsg", "成功");

            JSONObject format = new JSONObject();
            JSONArray addedittext = new JSONArray();
            JSONObject f1 = new JSONObject();
            f1.put("edittext_name","任务序号");
            f1.put("edittext_content","");
            f1.put("font_color","#080808");
            f1.put("font_size",20);
            addedittext.add(f1);
            JSONObject f2 = new JSONObject();
            f2.put("edittext_name","任务名称");
            f2.put("edittext_content","");
            f2.put("font_color","#080808");
            f2.put("font_size",20);
            addedittext.add(f2);
            JSONObject f3 = new JSONObject();
            f3.put("edittext_name","任务描述");
            f3.put("edittext_content","");
            f3.put("font_color","#080808");
            f3.put("font_size",20);
            addedittext.add(f3);
            JSONObject f4 = new JSONObject();
            f4.put("edittext_name","所涵盖地点");
            f4.put("edittext_content","");
            f4.put("font_color","#080808");
            f4.put("font_size",20);
            addedittext.add(f4);
            format.put("addedittext",addedittext);

            JSONArray addspinner = new JSONArray();
            JSONObject a1 = new JSONObject();
            a1.put("spinner_name","任务级别");
            a1.put("spinner_content","无");
            a1.put("spinner_array","无;班组;车间;公司");
            a1.put("font_color","#080808");
            a1.put("font_size",20);
            addspinner.add(a1);
            JSONObject a2 = new JSONObject();
            a2.put("spinner_name","任务来源");
            a2.put("spinner_content","无");
            a2.put("spinner_array","无;系统原因;运行种异常情况;运转时间达到定检周期");
            a2.put("font_color","#080808");
            a2.put("font_size",20);
            addspinner.add(a2);
            JSONObject a3 = new JSONObject();
            a3.put("spinner_name","身份验证");
            a3.put("spinner_content","无");
            a3.put("spinner_array","无;指纹验证;面部识别");
            a3.put("font_color","#080808");
            a3.put("font_size",20);
            addspinner.add(a3);
            format.put("addspinner",addspinner);

            ArrayList<JSONObject> formatinspectionbutton= new ArrayList<JSONObject>();

            String type = "2";

            List<materialDO> inbutton00 = setMissionDAO.list1(type);
            ArrayList<JSONObject> formatinspectionbuttoncontent1 = new ArrayList<JSONObject>();
            for(materialDO ib1 : inbutton00){
                JSONObject data3 = new JSONObject();
                data3.put("content_name",ib1.getName());
                data3.put("content_isselect","false");
                formatinspectionbuttoncontent1.add(data3);
            }
              type = "7";
            String array = "1";
            setMissionDO inbutton0 = setMissionDAO.get(type,array);
            JSONObject databutton0 = new JSONObject();
            databutton0.put("button_name","工具列表");
            databutton0.put("button_content","选择工具");
            databutton0.put("needtextview","true");
            databutton0.put("needshownum","false");
            databutton0.put("content",formatinspectionbuttoncontent1);
            databutton0.put("font_color",inbutton0.getFont_color());
            databutton0.put("font_size",inbutton0.getFont_size());
            formatinspectionbutton.add(databutton0);
            System.out.println(type);
            type = "1";
            List<materialDO> inbutton11 = setMissionDAO.list1(type);
            ArrayList<JSONObject> formatinspectionbuttoncontent2 = new ArrayList<JSONObject>();
            for(materialDO ib1 : inbutton11){
                JSONObject data3 = new JSONObject();
                data3.put("content_name",ib1.getName());
                data3.put("content_isselect","0");
                formatinspectionbuttoncontent2.add(data3);
            }
            System.out.println(type);
            type = "7";
            array = "2";
            setMissionDO inbutton1 = setMissionDAO.get(type,array);
            JSONObject databutton1 = new JSONObject();
            databutton1.put("button_name","配件列表");
            databutton1.put("button_content","选择配件");
            databutton1.put("needtextview","true");
            databutton1.put("needshownum","true");
            databutton1.put("content",formatinspectionbuttoncontent2);
            databutton1.put("font_color",inbutton1.getFont_color());
            databutton1.put("font_size",inbutton1.getFont_size());
            formatinspectionbutton.add(databutton1);
            System.out.println(type);
            List<EventInfoDO> inbutton22 = setMissionDAO.Event_list();
            ArrayList<JSONObject> formatinspectionbuttoncontent3 = new ArrayList<JSONObject>();
            for(EventInfoDO ib2 : inbutton22){
                JSONObject data4 = new JSONObject();
                data4.put("content_name",ib2.getEvent_name());
//                data4.put("content_note",ib2.getAdditions());
                data4.put("content_isselect","false");
                formatinspectionbuttoncontent3.add(data4);
            }
            System.out.println(type);
            type = "7";
            array = "3";
            setMissionDO inbutton2 = setMissionDAO.get(type,array);
            JSONObject databutton2 = new JSONObject();
            databutton2.put("button_name","任务事件");
            databutton2.put("button_content","选择事件");
            databutton2.put("needtextview","false");
            databutton2.put("needshownum","false");
            databutton2.put("content",formatinspectionbuttoncontent3);
            databutton2.put("font_color",inbutton2.getFont_color());
            databutton2.put("font_size",inbutton2.getFont_size());
            formatinspectionbutton.add(databutton2);
            System.out.println(type);




            format.put("addbutton",formatinspectionbutton);
            data.put("format", format);





        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        data.put("result", result);
        // 输出结果
        HttpOutUtil.outData(response, JSONObject.toJSONString(data));
    }

    @RequestMapping(method = { RequestMethod.POST }, params = "action=mission_feedback", produces = "application/json; charset=utf-8")
    public void mission_feedback(HttpServletRequest request, HttpServletResponse response) {

        //ApplicationContext context = getContext();
        feedbackDAOImpl feedbackDAO = (feedbackDAOImpl) context.getBean("feedbackDAO");

        JSONObject result = new JSONObject();
        result.put("result", "10001");


        try {

            String mission_id  = request.getParameter("MissionId");
            String event_id = request.getParameter("EventId");
            String work_name = request.getParameter("WorkId");
            String data = request.getParameter("Data");
            String pic = request.getParameter("Pic");
            String time = request.getParameter("Time");
            String type = request.getParameter("type");
            if (mission_id == null || mission_id.isEmpty() ||event_id == null || event_id.isEmpty() ||work_name == null || work_name.isEmpty()
                    ||time == null || time.isEmpty()) {
                result.put("errMsg", "输入参数有误");
                result.put("result","10001");
            } else {
                List<feedbackDO> feedback = feedbackDAO.seek(mission_id, event_id, work_name);
                System.out.println(feedback);
                if (feedback.isEmpty()) {
                    if ("1".equals(type)){
                        feedbackDO fddata = new feedbackDO();
                        fddata.setMission_id(mission_id);
                        fddata.setWork_name(work_name);
                        fddata.setEvent_id(event_id);
                        fddata.setData(data);
                        fddata.setTime(time);
                        feedbackDAO.insertdata(fddata);
                    }else{
                        pic = pic+".jpg";
                        feedbackDO fdpic = new feedbackDO();
                        fdpic.setMission_id(mission_id);
                        fdpic.setWork_name(work_name);
                        fdpic.setEvent_id(event_id);
                        fdpic.setPic(pic);
                        fdpic.setTime(time);
                        feedbackDAO.insertpic(fdpic);
                    }
                } else {
                    if ("1".equals(type)){
                        feedbackDO fddata = new feedbackDO();
                        fddata.setMission_id(mission_id);
                        fddata.setWork_name(work_name);
                        fddata.setEvent_id(event_id);
                        fddata.setData(data);
                        fddata.setTime(time);
                        feedbackDAO.updatedata(fddata);
                    }else{
                        pic = pic+".jpg";
                        feedbackDO fdpic = new feedbackDO();
                        fdpic.setMission_id(mission_id);
                        fdpic.setWork_name(work_name);
                        fdpic.setEvent_id(event_id);
                        fdpic.setPic(pic);
                        fdpic.setTime(time);
                        feedbackDAO.updatepic(fdpic);
                    }




                }System.out.println("ok");
                result.put("errMsg", "保存成功！");
                result.put("result", "10000");
            }
        } catch (Exception e) {
            result.put("essMsg", e.getMessage());

        }
        // 输出结果
        HttpOutUtil.outData(response, JSONObject.toJSONString(result));
    }


    @RequestMapping(method = { RequestMethod.POST }, params = "action=missiondetail")
    public void MissionDetail(HttpServletRequest request, HttpServletResponse response) {

//    	ApplicationContext context=getContext();
        MissionDetailDAOImpl MissionDetailDAO=(MissionDetailDAOImpl) context.getBean("MissionDetailDAO");

        JSONObject result = new JSONObject();
        result.put("result","10001");
//        System.out.println("11");

        try {

            String MissionId = request.getParameter("MissionId");

            if (MissionId==null||MissionId.isEmpty()) {
                result.put("errMsg","输入参数有误");
            } else {
                List<String> missionDetail = MissionDetailDAO.list(MissionId);
                if(missionDetail.isEmpty()){
                    result.put("errMsg","请联系重新设置页面");
                }else{
                    for(String mr : missionDetail){

                        String temp =  MissionDetailDAO.get(MissionId,mr);
                        MissionReturnDO missionReturnDO = new MissionReturnDO();
                        missionReturnDO.setColumn_key(mr);
                        missionReturnDO.setNote_content(temp);
                        MissionDetailDAO.update(missionReturnDO);
                    }

                    ArrayList<JSONObject> eventData = new ArrayList<JSONObject>();

                    String event_id = MissionDetailDAO.get_event(MissionId);
                    List<missionJsonDO> eventInfo = MissionDetailDAO.Event_Detail(MissionId,event_id);
                    System.out.println(event_id);
                    for(missionJsonDO EventInfoDO : eventInfo){
                        JSONObject jsonObject = JSONObject.parseObject(EventInfoDO.getBig_json());
                        eventData.add(jsonObject);
                    }

                    ArrayList<JSONObject> noteData = new ArrayList<JSONObject>();
                    List<MissionReturnDO> noteInfo =MissionDetailDAO.Return_list("2","0");
                    for(MissionReturnDO MissionReturnDO : noteInfo){
                        JSONObject data2 = new JSONObject();
                        data2.put("font_size",MissionReturnDO.getFont_size());
                        data2.put("note_name",MissionReturnDO.getNote_name());
                        data2.put("note_content",MissionReturnDO.getNote_content());
                        data2.put("font_color",MissionReturnDO.getFont_color());

                        noteData.add(data2);
                    }
                    ArrayList<JSONObject> auditorData = new ArrayList<JSONObject>();
                    List<EventDetail1DO> auditorInfo = MissionDetailDAO.Event_Detail1(MissionId);

                    for(EventDetail1DO EventDetail1DO : auditorInfo){
                        JSONObject data1 = new JSONObject();
                        data1.put("auditor",EventDetail1DO.getAuditor());
                        data1.put("auditor_opinion",EventDetail1DO.getAuditor_opinion());
                        data1.put("auditor_time",EventDetail1DO.getAuditor_time());

                        auditorData.add(data1);
                    }

                    MissionReturnDO task_info =MissionDetailDAO.get_first();
                    JSONObject data = new JSONObject();
                    data.put("task_ID",MissionId);
                    data.put("font_size",task_info.getFont_size());
                    data.put("font_color",task_info.getFont_color());
                    data.put("note",noteInfo);
                    data.put("event",eventData);
                    data.put("auditor",auditorData);
                    result.put("data",data);
                    result.put("essMsg","10000");
                    result.put("result","10000");
                }
            }
        } catch (Exception e) {
            result.put("essMsg", e.getMessage());
            LOGGER.warn("exception when login: " + e.getMessage());
        }
        // 输出结果
        HttpOutUtil.outData(response, JSONObject.toJSONString(result));
    }

    @RequestMapping(method = { RequestMethod.POST }, params = "action=exception")
    public void exception(HttpServletRequest request, HttpServletResponse response) {

//    	ApplicationContext context=getContext();
        collectDAOImpl collectDAO=(collectDAOImpl) context.getBean("collectDAO");

        JSONObject result = new JSONObject();
        result.put("result","10001");
//        System.out.println("11");

        try {

//            if (time1==null||time1.isEmpty()||time2==null||time2.isEmpty()) {
                String time2 = new SimpleDateFormat("yyyy-MM").format(new Date());

                SimpleDateFormat ym=new SimpleDateFormat("yyyy-MM");
                Calendar ymmax = Calendar.getInstance();
                ymmax.setTime(ym.parse(time2));
                ymmax.add(Calendar.MONTH,-5);
                Date dt1 = ymmax.getTime();
                String time1 = ym.format(dt1);

//            } else {
                List<collectDO> collect = collectDAO.list(time1,time2);
                if(collect.isEmpty()){
                    result.put("errMsg","empty");
                }else{


                    ArrayList<JSONObject> exception = new ArrayList<JSONObject>();

                    for(collectDO Collect : collect){

                        JSONObject data1 = new JSONObject();
                        data1.put("time",Collect.getTime());
                        data1.put("count",Collect.getAlarm()+Collect.getHidden_danger()+Collect.getQuestion());
                        data1.put("question",Collect.getQuestion());
                        data1.put("hidden_danger",Collect.getHidden_danger());
                        data1.put("alarm",Collect.getAlarm());
                        exception.add(data1);
                    }


                    result.put("data",exception);
                    result.put("essMsg","10000");
                    result.put("result","10000");
//                }
            }
        } catch (Exception e) {
            result.put("essMsg", e.getMessage());
            LOGGER.warn("exception when login: " + e.getMessage());
        }
        // 输出结果
        HttpOutUtil.outData(response, JSONObject.toJSONString(result));
    }
//, produces = "application/json; charset=utf-8"
    @RequestMapping(method = { RequestMethod.POST }, params = "action=missionJson")
    public void missionFeedback(HttpServletRequest request, HttpServletResponse response) {

        //ApplicationContext context = getContext();
        missionJsonDAOImpl missionJsonDAO = (missionJsonDAOImpl) context.getBean("missionJsonDAO");

        JSONObject result = new JSONObject();
        result.put("result", "10001");


        try {

            String mission_id = request.getParameter("mission_id");
            String event_id = request.getParameter("event_id");
            String big_json = request.getParameter("big_json");



            if (mission_id == null || mission_id.isEmpty() ||event_id == null || event_id.isEmpty()||big_json == null || big_json.isEmpty() ) {
                result.put("errMsg", "输入参数有误");
                result.put("result","10001");
            } else {


                missionJsonDO missionJsonDO = new missionJsonDO();
                missionJsonDO.setMission_id(mission_id);
                missionJsonDO.setEvent_id(event_id);
                missionJsonDO.setBig_json(big_json);
                missionJsonDAO.insert(missionJsonDO);
                System.out.println("insert-ok\\\"1\\\"||\\\"2\\\"");
                result.put("errMsg", "保存成功！");
                result.put("result","10000");


            }
        } catch (Exception e) {
            result.put("essMsg", e.getMessage());

        }
        // 输出结果
        HttpOutUtil.outData(response, JSONObject.toJSONString(result));
    }

    //, produces = "application/json; charset=utf-8"
    @RequestMapping(method = { RequestMethod.POST }, params = "action=missionTime")
    public void missionTime(HttpServletRequest request, HttpServletResponse response) {

        //ApplicationContext context = getContext();
        missionJsonDAOImpl missionJsonDAO = (missionJsonDAOImpl) context.getBean("missionJsonDAO");

        JSONObject result = new JSONObject();
        result.put("result", "10001");


        try {

            String time = request.getParameter("time");
            String mission_id = request.getParameter("mission_id");
            String type = request.getParameter("type");


            if (mission_id == null || mission_id.isEmpty() ) {
                result.put("errMsg", "输入参数有误");
                result.put("result","10001");
            } else {


                if("1".equals(type)){
                    missionTimeDO missionTimeDO = new missionTimeDO();
                    missionTimeDO.setMission_id(mission_id);
                    missionTimeDO.setTime(time);
                    missionTimeDO.setType(type);
                    missionJsonDAO.insert1(missionTimeDO);
                }else{
                    missionTimeDO missionTimeDO = new missionTimeDO();
                    missionTimeDO.setMission_id(mission_id);
                    missionTimeDO.setTime(time);
                    missionTimeDO.setType(type);
                    missionJsonDAO.insert2(missionTimeDO);
                }

                System.out.println("insert-ok\\\"1\\\"||\\\"2\\\"");
                result.put("errMsg", "保存成功！");
                result.put("result","10000");


            }
        } catch (Exception e) {
            result.put("essMsg", e.getMessage());

        }
        // 输出结果
        HttpOutUtil.outData(response, JSONObject.toJSONString(result));
    }

    @RequestMapping(method = { RequestMethod.POST }, params = "action=exceptionDetail")
    public void exceptionDetail(HttpServletRequest request, HttpServletResponse response) {

//    	ApplicationContext context=getContext();
        exceptionDtlDAOImpl exceptionDtlDAO=(exceptionDtlDAOImpl) context.getBean("exceptionDtlDAO");

        JSONObject result = new JSONObject();
        result.put("result","10001");

        try {

            String time1 = request.getParameter("time1");
            String time2 = request.getParameter("time2");
            String status = request.getParameter("abnormal_status");
            if (time1==null||time1.isEmpty()||time2==null||time2.isEmpty()) {
//            time2 = new SimpleDateFormat("yyyy-MM").format(new Date());
                result.put("errMsg", "输入参数有误");
                result.put("result","10001");
//            SimpleDateFormat ym=new SimpleDateFormat("yyyy-MM");
//            Calendar ymmax = Calendar.getInstance();
//            ymmax.setTime(ym.parse(time2));
//            ymmax.add(Calendar.MONTH,-5);
//            Date dt1 = ymmax.getTime();
//            time1 = ym.format(dt1);
//            System.out.println(time1+","+time2);
            } else {
            List<exceptionDtlDO> exDtl = exceptionDtlDAO.list(time1,time2,status);
            if(exDtl.isEmpty()){
                result.put("errMsg","empty");
            }else{
                ArrayList<JSONObject> exception = new ArrayList<JSONObject>();
                for(exceptionDtlDO exceptionDtl : exDtl){
                    JSONObject data1 = new JSONObject();
                    data1.put("abnormal_id",exceptionDtl.getId());
                    data1.put("abnormal_time",exceptionDtl.getReport_time());
                    data1.put("abnormal_person",exceptionDtl.getReport_worker());
                    data1.put("workshop",exceptionDtl.getWorkshop());
                    data1.put("abnormal_check_point",exceptionDtl.getCheckpoint());
                    data1.put("abnormal_description",exceptionDtl.getDescription());
                    data1.put("abnormal_level",exceptionDtl.getLevel());
                    exception.add(data1);
                }


                result.put("data",exception);
                result.put("essMsg","10000");
                result.put("result","10000");
                }
            }
        } catch (Exception e) {
            result.put("essMsg", e.getMessage());
            LOGGER.warn("exception when login: " + e.getMessage());
        }
        // 输出结果
        HttpOutUtil.outData(response, JSONObject.toJSONString(result));
    }


    @RequestMapping(method = { RequestMethod.POST }, params = "action=addauditor")
    public void addauditor(HttpServletRequest request, HttpServletResponse response) {

        //ApplicationContext context = getContext();
        addauditorDAOImpl addauditorDAO = (addauditorDAOImpl) context.getBean("addauditorDAO");

        JSONObject result = new JSONObject();
        result.put("result", "10001");


        try {

            String mission_id = request.getParameter("mission_id");
            String auditor_opinion = request.getParameter("audit_opinion");
            String auditor = request.getParameter("auditor");
            String auditor_time = request.getParameter("audit_time");


            if (mission_id == null || mission_id.isEmpty() ||auditor == null || auditor.isEmpty()||auditor_time == null || auditor_time.isEmpty() ) {
                result.put("errMsg", "输入参数有误");
                result.put("result","10001");
            } else {


                addauditorDO addauditorDO = new addauditorDO();
                addauditorDO.setMission_id(mission_id);
                addauditorDO.setAuditor(auditor);
                addauditorDO.setAuditor_opinion(auditor_opinion);
                addauditorDO.setAuditor_time(auditor_time);
                addauditorDAO.update(addauditorDO);
                System.out.println("update-ok");
                result.put("errMsg", "保存成功！");
                result.put("result","10000");


            }
        } catch (Exception e) {
            result.put("essMsg", e.getMessage());

        }
        // 输出结果
        HttpOutUtil.outData(response, JSONObject.toJSONString(result));
    }



    @RequestMapping(method = { RequestMethod.POST }, params = "action=collect1")
    public void collect1(HttpServletRequest request, HttpServletResponse response) {

//    	ApplicationContext context=getContext();
        collectDAOImpl collectDAO=(collectDAOImpl) context.getBean("collectDAO");

        JSONObject result = new JSONObject();
        result.put("result","10001");
//        System.out.println("11");

        try {


            String workshop = request.getParameter("workshop");

//            if (time1==null||time1.isEmpty()||time2==null||time2.isEmpty()) {
            String time2 = new SimpleDateFormat("yyyy-MM").format(new Date());

            SimpleDateFormat ym=new SimpleDateFormat("yyyy-MM");
            Calendar ymmax = Calendar.getInstance();
            ymmax.setTime(ym.parse(time2));
            ymmax.add(Calendar.MONTH,-5);
            Date dt1 = ymmax.getTime();
            String time1 = ym.format(dt1);
            System.out.println(time1+","+time2);
//            } else {
            List<collect1DO> collect = collectDAO.list1(time1,time2,workshop);
            if(collect.isEmpty()){
                result.put("errMsg","empty");
            }else{


                ArrayList<JSONObject> exception = new ArrayList<JSONObject>();
                for(collect1DO Collect : collect){
                    JSONObject data1 = new JSONObject();
                    data1.put("time",Collect.getTime());
                    data1.put("count",Collect.getDaily()+Collect.getRepair()+Collect.getTemp());
                    data1.put("daily",Collect.getDaily());
                    data1.put("repair",Collect.getRepair());
                    data1.put("temp",Collect.getTemp());
                    exception.add(data1);
                }


                result.put("data",exception);
                result.put("essMsg","10000");
                result.put("result","10000");
//                }
            }
        } catch (Exception e) {
            result.put("essMsg", e.getMessage());
            LOGGER.warn("exception when login: " + e.getMessage());
        }
        // 输出结果
        HttpOutUtil.outData(response, JSONObject.toJSONString(result));
    }


    @RequestMapping(method = { RequestMethod.POST }, params = "action=collect2")
    public void collect2(HttpServletRequest request, HttpServletResponse response) {

//    	ApplicationContext context=getContext();
        collectDAOImpl collectDAO=(collectDAOImpl) context.getBean("collectDAO");

        JSONObject result = new JSONObject();
        result.put("result","10001");
//        System.out.println("11");

        try {



//            if (time1==null||time1.isEmpty()||time2==null||time2.isEmpty()) {
            String time2 = new SimpleDateFormat("yyyy-MM").format(new Date());

            SimpleDateFormat ym=new SimpleDateFormat("yyyy-MM");
            Calendar ymmax = Calendar.getInstance();
            ymmax.setTime(ym.parse(time2));
            ymmax.add(Calendar.MONTH,-5);
            Date dt1 = ymmax.getTime();
            String time1 = ym.format(dt1);

//            } else {
            List<collect2DO> collect = collectDAO.list2(time1,time2);
            if(collect.isEmpty()){
                result.put("errMsg","empty");
            }else{


                ArrayList<JSONObject> exception = new ArrayList<JSONObject>();
                for(collect2DO Collect : collect){
                    JSONObject data1 = new JSONObject();
                    data1.put("time",Collect.getTime());

                    data1.put("daily",Collect.getDaily());
                    data1.put("repair",Collect.getRepair());
                    data1.put("temp",Collect.getTemp());
                    data1.put("count",Collect.getDaily()+Collect.getRepair()+Collect.getTemp());
                    exception.add(data1);
                }


                result.put("data",exception);
                result.put("essMsg","10000");
                result.put("result","10000");
//                }
            }
        } catch (Exception e) {
            result.put("essMsg", e.getMessage());
            LOGGER.warn("exception when login: " + e.getMessage());
        }
        // 输出结果
        HttpOutUtil.outData(response, JSONObject.toJSONString(result));
    }



    @RequestMapping(method = { RequestMethod.POST }, params = "action=insertException")
    public void insertException(HttpServletRequest request, HttpServletResponse response) {

        //ApplicationContext context = getContext();
        exceptionDtlDAOImpl insertExceptionDAO = (exceptionDtlDAOImpl) context.getBean("exceptionDtlDAO");

        JSONObject result = new JSONObject();
        result.put("result", "10001");


        try {

            String checkpoint = request.getParameter("abnormal_check_point");
            String description = request.getParameter("abnormal_description");
            String level = request.getParameter("abnormal_level");
            String report_worker = request.getParameter("abnormal_person");
            String report_time = request.getParameter("abnormal_time");
            String workshop = request.getParameter("workshop");
            String pic = request.getParameter("pic");

            if (checkpoint == null || checkpoint.isEmpty() ||workshop == null || workshop.isEmpty()||report_worker == null || report_worker.isEmpty() ) {
                result.put("errMsg", "输入参数有误");
                result.put("result","10001");
            } else {
                String rt = report_time.replace('_',' ');

                insertExceptionDO insertExceptionDO = new insertExceptionDO();
                insertExceptionDO.setCheckpoint(checkpoint);
                insertExceptionDO.setDescription(description);
                insertExceptionDO.setLevel(level);
                insertExceptionDO.setReport_worker(report_worker);
                insertExceptionDO.setReport_time(rt);
                insertExceptionDO.setWorkshop(workshop);
                insertExceptionDO.setPic(pic+".jpg");
                insertExceptionDAO.insert(insertExceptionDO);
                System.out.println("insert-ok\\\"1\\\"||\\\"2\\\"");
                result.put("errMsg", "保存成功！");
                result.put("result","10000");


            }
        } catch (Exception e) {
            result.put("essMsg", e.getMessage());

        }
        // 输出结果
        HttpOutUtil.outData(response, JSONObject.toJSONString(result));
    }


    @RequestMapping(method = { RequestMethod.POST }, params = "action=searchtest")
    public void searchtest(HttpServletRequest request, HttpServletResponse response) {

        //ApplicationContext context = getContext();
        searchDAOImpl searchDAO = (searchDAOImpl) context.getBean("searchDAO");

        JSONObject result = new JSONObject();
        result.put("result", "10001");

        try {

            List<searchtestDO> search = searchDAO.list_test();

            int i = 1;
                for (searchtestDO searchDO : search){

                    if(searchDO.getTime().after(Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()))){
                        continue;
                    }
                        System.out.println("add to list"+searchDO.getTime());

                }




        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        // 输出结果
        HttpOutUtil.outData(response, JSONObject.toJSONString(result));
    }
//    @RequestMapping(method = { RequestMethod.POST }, params = "action=send")
//    public void send(HttpServletRequest request, HttpServletResponse response) {
//
//
//        JSONObject result1 = new JSONObject();
//        result1.put("success", "hh");
//        try{
//
//
//            Map<String,String> test = new HashMap<>();
//            test.put("MissionId","2");
//            test.put("MissionId1","21");
//            FormBody.Builder bodyBuilder = new FormBody.Builder();
//            test.forEach(bodyBuilder::add);
//            Request request1 = new Request.Builder()
//                    .url("http://123.206.16.157:8080/water/mission.req?action=missiondetail")
//                    .header("Content-Type","application/x-www-form-urlencoded;charset=UTF-8")
//                    .post(bodyBuilder.build())
//                    .build();
//
//            JSONObject result;
//            try(Response response1 = OkHttpClient.newCall(request1).execute()){
//                if(response1 == null||response1.body()==null){
//                    System.out.println("null");
//                }else{
//
//                    System.out.println("----------");
//                    result = JSONObject.parseObject(response1.body().string());
//                    System.out.println(result.toString());
//                }
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//
//        HttpOutUtil.outData(response, JSONObject.toJSONString(result1));
//    }

}
