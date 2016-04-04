/**
 * 
 */
package com.bupt.qrj.unifyum.api.controller.impl;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSONArray;
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

    private UserTestDataDAO           userTestDataDAO;

    /** ��־ **/
    private static final Logger       LOGGER      = LoggerFactory
                                                      .getLogger(UserTestDataControllerImpl.class);

    /**
     * jqgrid ��op �� sql ���� map
     * */
    private final Map<String, String> jqOpMapping = new HashMap<String, String>() {
                                                      {
                                                          //����
                                                          put("eq", " = ");
                                                          //������
                                                          put("ne", " != ");
                                                          //��ʼ��
                                                          put("bw", " like ");
                                                          //����ʼ��
                                                          put("bn", " not like ");
                                                          //������
                                                          put("ew", " like ");
                                                          //��������
                                                          put("en", " not like ");
                                                          //����
                                                          put("cn", " like ");
                                                          //������
                                                          put("nc", " not like ");
                                                          //���ڵ���
                                                          put("ge", " >= ");
                                                          //С�ڵ���
                                                          put("le", " <= ");
                                                      }
                                                  };

    private void processPageParams(HttpServletRequest request, HashMap<String, Object> paraMap,
                                   Integer count) {
        //��ǰ���ڵڼ�ҳ
        Long page = Long.valueOf(request.getParameter("page"));
        //ÿһҳ�еļ�¼����
        Long rows = Long.valueOf(request.getParameter("rows"));
        paraMap.put("page", page);
        paraMap.put("limit", (page - 1) * rows);
        paraMap.put("offset", rows);
        //����
        //�������
        String sortKey = request.getParameter("sidx");
        paraMap.put("sortKey", sortKey);
        //�����˳��
        String order = request.getParameter("sord");
        paraMap.put("order", order);
    }

    /* (non-Javadoc)
     * @see com.bupt.qrj.unifyum.api.controller.UserTestDataController#updateTestData(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    @RequestMapping(method = { RequestMethod.POST }, params = "action=update")
    public void updateTestData(HttpServletRequest request, HttpServletResponse response)
                                                                                        throws UnsupportedEncodingException {
        JSONObject result = new JSONObject();
        request.setCharacterEncoding("UTF-8");
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
                JSONObject oldTestDataMap = new JSONObject();

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
                    testDataDO.setTestData("".getBytes());
                } else {
                    testDataDO.setTestData(oldTestDataMap.toJSONString().getBytes("utf8"));
                }
                userTestDataDAO.insert(testDataDO);
                result.put("success", true);
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
            UserTestDataDO testDO = userTestDataDAO.get(userName);
            if (testDO == null || testDO.getTestData().length == 0) {
                result.put("errMsg", "Ŀǰû���κβ�������");
            } else {
                String testDataStr = new String(testDO.getTestData(), StandardCharsets.UTF_8);
                //����һ���ܲ��ܽ���
                JSONObject testDataJstr = JSONObject.parseObject(testDataStr);
                result.put("data", testDataJstr);
                result.put("success", true);
            }

        } catch (Exception e) {
            result.put("errMsg", e);
        }
        // ������       
        HttpOutUtil.outData(response, result.toString());
    }

    /* (non-Javadoc)
     * @see com.bupt.qrj.unifyum.api.controller.UserTestDataController#jqList(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    @RequestMapping(method = { RequestMethod.POST }, params = "action=jqList")
    public void jqList(HttpServletRequest request, HttpServletResponse response) {
        HashMap<String, Object> paraMap = new HashMap<String, Object>();
        String userName = (String) request.getAttribute("username");
        JSONObject result = new JSONObject();
        result.put("success", false);
        List<UserTestDataDO> testDOList = null;
        try {
            paraMap.put("userName", userName);
            testDOList = userTestDataDAO.list(paraMap);
            if (testDOList == null || testDOList.size() == 0) {
                result.put("errMsg", "û�д��û�������");
            } else {
                JSONArray jArray = new JSONArray();
                result.put("datas", jArray);
                for (UserTestDataDO testDO : testDOList) {
                    String testDataStr = new String(testDO.getTestData(), StandardCharsets.UTF_8);
                    //����һ���ܲ��ܽ���
                    JSONObject testDataJObj = JSONObject.parseObject(testDataStr);
                    jArray.add(testDataJObj);
                }
                result.put("result", true);
            }

        } catch (Exception e) {
            result.put("errMsg", e.getMessage());
        }
        HttpOutUtil.outData(response, JSONObject.toJSONString(result));

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
