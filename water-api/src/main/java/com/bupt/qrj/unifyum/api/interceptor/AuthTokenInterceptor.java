/**
 * 
 */
package com.bupt.qrj.unifyum.api.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bupt.qrj.unifyum.dal.dao.UserMetaDAO;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.alibaba.fastjson.JSONObject;
import com.bupt.qrj.unifyum.api.result.usermeta.CheckLoginResult;
import com.bupt.qrj.unifyum.dal.dataobject.UserMetaDO;

/**
 * @author renjun.qrj 2015年12月29日:上午6:24:55
 *         com.bupt.qrj.unifyum.api.interceptor.LoginInterceptor unifyum-api 用途:
 *
 */
public class AuthTokenInterceptor extends HandlerInterceptorAdapter {
	private UserMetaDAO userMetaDAO;

	/**
	 * @return the userMetaDAO
	 */
	public UserMetaDAO getUserMetaDAO() {
		return userMetaDAO;
	}

	/**
	 * @param userMetaDAO
	 *            the userMetaDAO to set
	 */
	public void setUserMetaDAO(UserMetaDAO userMetaDAO) {
		this.userMetaDAO = userMetaDAO;
	}

	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse response, Object handler) throws Exception {
		CheckLoginResult result = new CheckLoginResult();
		result.setSuccess(false);

		String[] notCheckURL = new String[] {

				"/mission.req?action=MissionReturn","/arrange.req?action=arrangeseek","/mission.req?action=seekwjs","/mission.req?action=location",
				"/mission.req?action=mapinfo","/mission.req?action=setMission","/mission.req?action=login","/mission.req?action=checkInfo",
				"/mission.req?action=condition","/arrange.req?action=arrangeinsert","/arrange.req?action=arrangeseek","/arrange.req?action=arrangeseekall",
                "/arrange.req?action=MissionReturn","/arrange.req?action=arrangepermis","/mission.req?action=search","/mission.req?action=poyuntasklist",
                "/mission.req?action=poyunupload","/mission.req?action=poyunformat","/mission.req?action=createformat","/mission.req?action=arrangeformat",
                "/mission.req?action=mission_feedback","/imageDownload.req?action=filedownload","/imageUpload.req?action=upload","/mission.req?action=missiondetail",
                "/mission.req?action=exception","/mission.req?action=missionJson","/mission.req?action=exceptionDetail","/arrange.req?action=arrangelist",
				"/arrange.req?action=feedback","/mission.req?action=addauditor","/arrange.req?action=set_mission","/mission.req?action=collect2","/mission.req?action=collect1",
				"/mission.req?action=insertException","/user.req?action=send","/user.req?action=sending","/imageDownload.req?action=exceptiondownload",
				"/mission.req?action=searchtest","/imageDownload.req?action=filedownloadpost","/imageDownload.req?action=exceptiondownloadpost",
				"/arrange.req?action=arrsetmis","/mission.req?action=missionTime"

		};
		String cPath = req.getContextPath();
		String URI = req.getRequestURI();
		String testURL = URI.substring(cPath.length());
		String action = req.getParameter("action");
		testURL = testURL + "?action=" + action;

		for (String whiteList : notCheckURL) {
			if (testURL.equals(whiteList))
				return true;
		}

		String authToken = req.getParameter("authToken");
		try {
			if (authToken == null || authToken.isEmpty()) {
				result.setErrMsg("token 为空,不允许使用接口");
			} else {
				// 获取用户的数据
				UserMetaDO uMetaDO = new UserMetaDO();
				uMetaDO.setAuthToken(authToken);
				UserMetaDO userMeta = userMetaDAO.get(uMetaDO);
				if (userMeta == null) {
					result.setErrMsg("登录信息检查失败");
				} else {
					req.setAttribute("username", userMeta.getUserName());
					return true;
				}
			}
		} catch (Exception e) {
			result.setErrMsg("登录信息检查失败，" + e.getCause());
		}

		response.setContentType("application/json;charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache");
		response.getWriter().write(JSONObject.toJSONString(result));
		return false;
	}
}
