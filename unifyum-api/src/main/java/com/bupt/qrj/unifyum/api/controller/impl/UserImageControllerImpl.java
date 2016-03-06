/**
 * 
 */
package com.bupt.qrj.unifyum.api.controller.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import sun.misc.BASE64Decoder;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bupt.qrj.unifyum.api.controller.UserImageController;
import com.bupt.qrj.unifyum.dal.dao.UserImageDAO;
import com.bupt.qrj.unifyum.dal.dataobject.UserImageDO;
import com.bupt.qrj.unifyum.util.http.HttpOutUtil;

/**
 * @author renjun.qrj  2015��12��30��:����7:19:36
 * com.bupt.qrj.unifyum.api.controller.impl.UserImageControllerImpl
 * unifyum-api
 * ��;: 
 *
 */
@Controller
@RequestMapping("/imagemanagement.req")
public class UserImageControllerImpl implements UserImageController {

    private UserImageDAO              userImageDAO;

    private static JSONObject         solutionData = new JSONObject();

    private static JSONObject         adviceData   = new JSONObject();

    /**
     * jqgrid ��op �� sql ���� map
     * */
    private final Map<String, String> jqOpMapping  = new HashMap<String, String>() {
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

    {
        solutionData.put("productSuggest", "������ʹ��A��Ʒ");
        solutionData.put("foodSuggest", "����������߲�");
        solutionData.put("liveSuggest", "��������ʱ�Է����ú���Ϣ");

        adviceData.put("moistenAdvice", "����ļ��������ֳ��������õ�����ϰ��");
        adviceData.put("satinAdvice", "Ƥ����������");
        adviceData.put("bloodAdvice", "��Ѫ��ʢ����������");
        adviceData.put("colorAdvice", "ɫ����󣬿��Լ�������������Ϣ");
        adviceData.put("textureAdvice", "Ƥ�����������ʸ�");
    }

    /* (non-Javadoc)
     * @see com.bupt.qrj.unifyum.api.controller.UserImageController#upload(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    @RequestMapping(method = { RequestMethod.POST }, params = "action=upload")
    public void upload(HttpServletRequest request, HttpServletResponse response) {
        JSONObject result = new JSONObject();
        result.put("success", false);
        String userName = (String) request.getAttribute("username");
        try {
            String imageStr = request.getParameter("image");
            if (imageStr == null || imageStr.isEmpty()) {
                result.put("errMsg", "�����������");
            } else {
                Random rand = new Random();
                int moisten = rand.nextInt(10) + 1;
                int blood = rand.nextInt(10) + 1;
                int color = rand.nextInt(10) + 1;
                int texture = rand.nextInt(10) + 1;
                int satin = rand.nextInt(10) + 1;
                UserImageDO image = new UserImageDO();
                image.setUserName(userName);
                image.setImage(imageStr);
                image.setMoisten(String.valueOf(moisten));
                image.setBlood(String.valueOf(blood));
                image.setTexture(String.valueOf(texture));
                image.setSatin(String.valueOf(satin));
                image.setColor(String.valueOf(color));
                userImageDAO.addImage(image);
                JSONObject picData = new JSONObject();
                picData.put("moisten", String.valueOf(moisten));
                picData.put("blood", String.valueOf(blood));
                picData.put("color", String.valueOf(color));
                picData.put("texture", String.valueOf(texture));
                picData.put("satin", String.valueOf(satin));
                result.put("adviceData", adviceData);
                result.put("solutionData", solutionData);
                result.put("picData", picData);
                result.put("success", true);
            }
        } catch (Exception e) {
            result.put("errMsg", e);
        }
        //������
        HttpOutUtil.outData(response, JSONObject.toJSONString(result));
    }

    /* (non-Javadoc)
     * @see com.bupt.qrj.unifyum.api.controller.UserImageController#list(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    @RequestMapping(method = { RequestMethod.POST }, params = "action=list")
    public void list(HttpServletRequest request, HttpServletResponse response) {
        String userName = (String) request.getAttribute("username");
        JSONObject result = new JSONObject();
        result.put("success", false);
        try {
            List<UserImageDO> imgDOList = userImageDAO.queryImageByUser(userName);
            if (imgDOList == null || imgDOList.isEmpty()) {
                result.put("errMsg", "no data");
            } else {
                result.put("success", true);
                result.put("images", imgDOList);
            }
        } catch (Exception e) {
            result.put("errMsg", e);
        }
        //������
        HttpOutUtil.outData(response, JSONObject.toJSONString(result));
    }

    /* (non-Javadoc)
     * @see com.bupt.qrj.unifyum.api.controller.UserImageController#view(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    @RequestMapping(method = { RequestMethod.POST }, params = "action=view")
    public void view(HttpServletRequest request, HttpServletResponse response) {
        JSONObject result = new JSONObject();
        result.put("success", false);
        try {
            Long imageId = Long.parseLong(request.getParameter("imgId"));
            if (imageId == null) {
                result.put("errMsg", "�����������");
            } else {
                UserImageDO image = userImageDAO.getImage(imageId);
                if (image == null) {
                    result.put("errMsg", "û�в��ҵ�ͼ��");
                } else {
                    result.put("success", true);
                    result.put("image", image);
                }
            }
        } catch (Exception e) {
            result.put("errMsg", e);
        }
        //������
        HttpOutUtil.outData(response, JSONObject.toJSONString(result));
    }

    /* (non-Javadoc)
     * @see com.bupt.qrj.unifyum.api.controller.UserImageController#showImage(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    @SuppressWarnings("restriction")
    @RequestMapping(method = { RequestMethod.GET }, params = "action=showImg")
    public void showImage(HttpServletRequest request, HttpServletResponse response) {
        try {
            Long imageId = Long.parseLong(request.getParameter("id"));
            if (imageId != null) {
                UserImageDO image = userImageDAO.getImage(imageId);
                if (image != null) {
                    byte[] imageData = null;
                    BASE64Decoder decoder = new BASE64Decoder();
                    imageData = decoder.decodeBuffer(image.getImage());
                    if (imageData != null) {
                        response.setContentType("image/jpg");
                        ServletOutputStream outputStream = response.getOutputStream();
                        outputStream.write(imageData);
                        outputStream.close();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* (non-Javadoc)
     * @see com.bupt.qrj.unifyum.api.controller.UserImageController#jqList(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    @RequestMapping(method = { RequestMethod.POST, RequestMethod.GET }, params = "action=jqList")
    public void jqList(HttpServletRequest request, HttpServletResponse response) {
        HashMap<String, Object> paraMap = new HashMap<String, Object>();

        processFilterParams(request, paraMap);
        JSONObject result = null;
        Integer count = 0;
        List<UserImageDO> imageDOList = null;
        try {
            count = userImageDAO.count(paraMap);
            if (count > 0) {
                //����ҳ����Ϣ
                processPageParams(request, paraMap, count);
                imageDOList = userImageDAO.queryImageByProps(paraMap);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        result = buildJqResult(imageDOList, paraMap, count);
        HttpOutUtil.outData(response, JSONObject.toJSONString(result));
    }

    /**
     * processFilterParams 2016��3��6��
     * 2016��3��6��.����9:58:42
     * return: void 
     */
    private void processFilterParams(HttpServletRequest request, HashMap<String, Object> paraMap) {
        Map reqMap = request.getParameterMap();
        String searchSwitch = request.getParameter("_search");
        StringBuffer filterBuffer = new StringBuffer();
        if (searchSwitch.equalsIgnoreCase("true")) {

            String filters = request.getParameter("filters");
            JSONObject filterJSON = JSONObject.parseObject(filters);
            String groupOp = filterJSON.getString("groupOp");
            JSONArray rules = filterJSON.getJSONArray("rules");
            int iterCount = 1;
            for (Object rule : rules) {
                JSONObject ruleJSON = (JSONObject) rule;
                String keyName = ruleJSON.getString("field");
                String op = ruleJSON.getString("op");
                String sqlOp = jqOpMapping.get(op) == null ? " = " : jqOpMapping.get(op);
                String data = ruleJSON.getString("data");
                //��¼������ƴ��groupOp
                filterBuffer.append(" ");
                filterBuffer.append(keyName);
                filterBuffer.append(sqlOp);
                filterBuffer.append(prepareFilterData(data, op));
                filterBuffer.append(" ");
                if (iterCount != rules.size()) {
                    filterBuffer.append(groupOp);
                }
                iterCount++;
            }
            //������
            String filterStr = filterBuffer.toString();
            if (filterStr != null && !filterStr.isEmpty()) {
                paraMap.put("filterStr", filterBuffer.toString());
            }
        }
    }

    private String prepareFilterData(String data, String op) {
        if (op.equals("bw") || op.equals("bn")) {
            //��ʼ��
            data = data + "%";
        } else if (op.equals("ew") || op.equals("en")) {
            //������
            data = "%" + data;
        } else if (op.equals("cn") || op.equals("nc")) {
            //������
            data = "%" + data + "%";
        }
        //�ַ������͵Ĵ���
        data = "'" + data + "'";

        return data;
    }

    /**
     * processRequestParams 2016��3��6��
     * 2016��3��6��.����9:44:45
     * return: void 
     * @param count 
     */
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

    /**
     * buildJqResult 2016��3��6��
     * 2016��3��6��.����12:25:41
     * return: JSONObject 
     * @param count 
     * @param paraMap 
     */
    private JSONObject buildJqResult(List<UserImageDO> imageDOList,
                                     HashMap<String, Object> paraMap, Integer count) {
        JSONObject result = new JSONObject();
        JSONArray rows = new JSONArray();
        result.put("records", count);
        result.put("rows", rows);

        if (imageDOList == null || imageDOList.isEmpty()) {
            result.put("total", 0);
            result.put("page", 0);
        } else {
            Integer offset = ((Long) paraMap.get("offset")).intValue();
            int total = count / offset;
            if (count % offset != 0) {
                total++;
            }

            result.put("total", total);
            if (paraMap.containsKey("page")) {
                result.put("page", paraMap.get("page"));
            } else {
                result.put("page", 1);
            }
            //��ʼת��
            for (UserImageDO imageDO : imageDOList) {
                JSONObject data = new JSONObject();
                data.put("user_name", imageDO.getUserName());
                data.put("blood", imageDO.getBlood());
                data.put("moisten", imageDO.getMoisten());
                data.put("color", imageDO.getColor());
                data.put("texture", imageDO.getTexture());
                data.put("satin", imageDO.getSatin());
                data.put("record_time", imageDO.getRecordTime());
                data.put("id", imageDO.getId());
                rows.add(data);
            }
        }
        return result;
    }

    /**
     * @param userImageDAO the userImageDAO to set
     */
    public void setUserImageDAO(UserImageDAO userImageDAO) {
        this.userImageDAO = userImageDAO;
    }

}
