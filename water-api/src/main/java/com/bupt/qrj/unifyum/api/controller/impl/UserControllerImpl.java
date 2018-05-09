/**
 * 
 */
package com.bupt.qrj.unifyum.api.controller.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bupt.qrj.unifyum.dal.dao.impl.UserDAOImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bupt.qrj.unifyum.api.controller.UserController;
import com.bupt.qrj.unifyum.dal.dataobject.UserDO;
import com.bupt.qrj.unifyum.util.http.HttpOutUtil;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author renjun.qrj 2015年10月31日:下午8:43:02
 *         com.bupt.qrj.unifyum.api.controller.impl.UserMetaControllerImpl
 *         unifyum-api 用途:
 *
 */
@Controller
@RequestMapping("/user.req")
public class UserControllerImpl implements UserController {


	/** 日志 **/
    private static final Logger LOGGER = LoggerFactory.getLogger(UserControllerImpl.class);
    
    public static ApplicationContext getContext() {
		//获得Spring中定义的Bean实例，两个以上加 new String[]
		ApplicationContext context = new ClassPathXmlApplicationContext(
				new String[] { "META-INF/spring/unifyum-dal-dao.xml", "META-INF/spring/unifyum-dal-db.xml" });
		return context;
	}
    @Resource
    private OkHttpClient httpClient;

    @RequestMapping(method = { RequestMethod.POST }, params = "action=login")
    public void login(HttpServletRequest request, HttpServletResponse response) {
    	ApplicationContext context=getContext();
    	UserDAOImpl userDAO=(UserDAOImpl) context.getBean("UserDAO");
        JSONObject result = new JSONObject();
        result.put("result",10001);
        try {
        	String body = request.getParameter("login");
        	System.out.println("login="+body);
        	Map map = JSON.parseObject(body, Map.class);
        	System.out.println(map.get("mobileNo"));
        	String userName = (String) map.get("mobileNo");
        	String password = (String) map.get("password");
            if (userName == null || userName.isEmpty() || password == null || password.isEmpty()) {
                result.put("errMsg","输入参数有误");
            } else {
                UserDO userDO = new UserDO();
                userDO.setUserName(userName);
                UserDO user = userDAO.get(userDO);
                if (user == null) {
                    result.put("errMsg","用户名输入错误");
                } else {
                    if (password.equals(user.getPassword())) {
                    	userDO.setPassword(password);
                    	result.put("authToken",userName);
                    	result.put("errMsg","登录成功");
                        result.put("result",10000);
                    }else{
                    	result.put("errMsg", "密码错误");
                    }
                }
            }
        } catch (Exception e) {
            result.put("essMsg", e.getMessage());
            LOGGER.warn("exception when login: " + e.getMessage());
        }
        HttpOutUtil.outData(response, JSONObject.toJSONString(result));
    }




    @RequestMapping(method = { RequestMethod.POST }, params = "action=send")
    public void send(HttpServletRequest request, HttpServletResponse response) {

        ApplicationContext context=getContext();
        JSONObject result1 = new JSONObject();
        result1.put("success", "hh");


        int id = 12;


        JSONObject data = new JSONObject();
        data.put("id",id);
        data.put("type","T");


        LOGGER.info("datatoPeopleSoft,id:{},request:{}",id,data.toJSONString());
        System.out.println(data.toString());
        System.out.println(data.toJSONString());
        FormBody.Builder bodyBuilder = new FormBody.Builder();
        bodyBuilder.add("MissionId","2");

        Request request1 = new Request.Builder()
                .url("http://123.206.16.157:8080/water/mission.req?action=missiondetail")
                .header("Content-Type","application/x-www-form-urlencoded;charset=UTF-8")
                .post(bodyBuilder.build())
                .build();

        JSONObject result;
        OkHttpClient client  = new OkHttpClient.Builder()
        .connectTimeout(10, TimeUnit.MILLISECONDS)
                .readTimeout(20,TimeUnit.MILLISECONDS)
                .build();

        int i = 0;

        while(i<=3){
            try(Response response1 = client.newCall(request1).execute()){

                if(response1 == null||response1.body()==null){
                    System.out.println("null");
                }else{

                    System.out.println("----------");
                    result = JSONObject.parseObject(response1.body().string());
                    System.out.println(result.toString());
                    break;
                }


            } catch (IOException e) {
                LOGGER.warn("exception when login: " + e.getMessage());
                System.out.println("failure");
                System.out.println(i);
            }finally {
                i++;
                System.out.println("test"+i);
            }
        }


        HttpOutUtil.outData(response, JSONObject.toJSONString(result1));
    }

    @RequestMapping(method = { RequestMethod.POST }, params = "action=sending")
    public void sending(HttpServletRequest request, HttpServletResponse response) {

        ApplicationContext context=getContext();
        JSONObject result1 = new JSONObject();
        result1.put("success", "hh");

        try{

            BufferedReader br = request.getReader();

            String str, wholeStr = "";
            while((str = br.readLine()) != null){
                wholeStr += str;
            }
            System.out.println(wholeStr);




        }catch (IOException e){
            result1.put("essMsg", e.getMessage());
        }



        HttpOutUtil.outData(response, JSONObject.toJSONString(result1));
    }

}
