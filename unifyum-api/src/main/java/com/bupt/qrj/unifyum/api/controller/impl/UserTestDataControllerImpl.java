/**
 * 
 */
package com.bupt.qrj.unifyum.api.controller.impl;

import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSONObject;
import com.bupt.qrj.unifyum.api.controller.UserTestDataController;
import com.bupt.qrj.unifyum.dal.dao.UserTestDataDAO;
import com.bupt.qrj.unifyum.dal.dataobject.UserTestDataDO;
import com.bupt.qrj.unifyum.util.http.HttpOutUtil;

/**
 * @author renjun.qrj  2016��1��2��:����9:48:33
 * com.bupt.qrj.unifyum.api.controller.impl.UserTestDataControllerImpl
 * unifyum-api
 * ��;: 
 *
 */
@Controller
@RequestMapping("/usertestdata.req")
public class UserTestDataControllerImpl implements UserTestDataController {

    private UserTestDataDAO     userTestDataDAO;

    /** ��־ **/
    private static final Logger LOGGER = LoggerFactory.getLogger(UserTestDataControllerImpl.class);

    /* (non-Javadoc)
     * @see com.bupt.qrj.unifyum.api.controller.UserTestDataController#updateTestData(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    @RequestMapping(method = { RequestMethod.POST }, params = "action=update")
    public void updateTestData(HttpServletRequest request, HttpServletResponse response) {
        JSONObject result = new JSONObject();
        result.put("success", false);
        String userName = (String) request.getAttribute("username");
        try {
            Map<String, String[]> paramsMap = request.getParameterMap();
            JSONObject testDataMap = new JSONObject();
            Iterator<String> paramKeyIter = paramsMap.keySet().iterator();
            //����param
            while (paramKeyIter.hasNext()) {
                String key = paramKeyIter.next();
                if (!key.equals("authToken") && !key.equals("action")) {
                    String value = String.valueOf(request.getParameter(key));
                    testDataMap.put(key, value);
                }
            }
            if (testDataMap.isEmpty()) {
                result.put("errMsg", "δ�����κβ�������");
            } else {
                UserTestDataDO testDataDO = new UserTestDataDO();
                testDataDO.setUserName(userName);
                String oldTestData = userTestDataDAO.get(userName);
                if (oldTestData == null || oldTestData.isEmpty()) {
                    Iterator<Map.Entry<String, Object>> testDIter = testDataMap.entrySet()
                        .iterator();
                    //����testData,Ϊ�յ�ֵ��Ҫȥ��~!!
                    while (testDIter.hasNext()) {
                        Map.Entry<String, Object> entry = testDIter.next();
                        String value = String.valueOf(entry.getValue());
                        if (value == null || value.isEmpty() || value.equals("\"\"")) {
                            testDIter.remove();
                        }
                    }
                    if (testDataMap.isEmpty()) {
                        result.put("errMsg", "����Ĳ���������");
                    } else {
                        testDataDO.setTestData(testDataMap.toJSONString());
                        userTestDataDAO.insert(testDataDO);
                        result.put("success", true);
                    }
                } else {
                    JSONObject oldTestDataMap = new JSONObject();
                    try {
                        oldTestDataMap = JSONObject.parseObject(oldTestData);
                    } catch (Exception e) {
                        LOGGER.warn("get old test data error: ", e);
                    }
                    oldTestDataMap.putAll(testDataMap);
                    Iterator<Map.Entry<String, Object>> testDIter = oldTestDataMap.entrySet()
                        .iterator();
                    //����testData,Ϊ�յ�ֵ��Ҫȥ��~!!
                    while (testDIter.hasNext()) {
                        Map.Entry<String, Object> entry = testDIter.next();
                        String value = String.valueOf(entry.getValue());

                        if (value == null || value.isEmpty() || value.equals("\"\"")) {
                            testDIter.remove();
                        }
                    }
                    if (oldTestDataMap.isEmpty()) {
                        testDataDO.setTestData("");
                    } else {
                        testDataDO.setTestData(oldTestDataMap.toJSONString());
                    }
                    userTestDataDAO.update(testDataDO);
                    result.put("success", true);
                }
            }
        } catch (Exception e) {
            LOGGER.warn("exp happened: ", e);
            result.put("errMsg", e);
        }
        // ������        
        HttpOutUtil.outData(response, JSONObject.toJSONString(result));
    }

    /* (non-Javadoc)
     * @see com.bupt.qrj.unifyum.api.controller.UserTestDataController#getTestData(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    @RequestMapping(method = { RequestMethod.POST }, params = "action=get")
    public void getTestData(HttpServletRequest request, HttpServletResponse response) {
        JSONObject result = new JSONObject();
        result.put("success", false);
        String userName = (String) request.getAttribute("username");
        try {
            String testDataStr = userTestDataDAO.get(userName);
            if (testDataStr == null || testDataStr.isEmpty()) {
                result.put("errMsg", "Ŀǰû���κβ�������");
            } else {
                //����һ���ܲ��ܽ���
                JSONObject testData = JSONObject.parseObject(testDataStr);
                result.put("data", testData);
                result.put("success", true);
            }
        } catch (Exception e) {
            result.put("errMsg", e);
        }
        // ������       
        HttpOutUtil.outData(response, result.toString());
    }

    /**
     * @return the userTestDataDAO
     */
    public UserTestDataDAO getUserTestDataDAO() {
        return userTestDataDAO;
    }

    /**
     * @param userTestDataDAO the userTestDataDAO to set
     */
    public void setUserTestDataDAO(UserTestDataDAO userTestDataDAO) {
        this.userTestDataDAO = userTestDataDAO;
    }

}
