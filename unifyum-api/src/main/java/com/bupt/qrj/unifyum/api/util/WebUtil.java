/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2014 All Rights Reserved.
 */
package com.bupt.qrj.unifyum.api.util;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bupt.qrj.unifyum.api.controller.impl.UserMetaControllerImpl;

/**
 * @author renjun.qrj 2015��10��31��:����9:22:40
 *         com.bupt.qrj.unifyum.api.util.WebUtil unifyum-api ��;:
 *
 */
public class WebUtil {

	/** ��־ **/
	private static final Logger LOGGER = LoggerFactory
			.getLogger(UserMetaControllerImpl.class);

	/**
	 * ��ͻ���������ݡ�
	 * 
	 * @param httpServletResponse
	 *            ��Ӧ����
	 * @param resJsonString
	 */
	public static void outData(HttpServletResponse httpServletResponse,
			String resJsonString) {
		PrintWriter writer = null;
		try {
			httpServletResponse.setContentType("application/json");
			httpServletResponse.setCharacterEncoding("utf-8");
			writer = httpServletResponse.getWriter();
			writer.print(resJsonString);
			writer.flush();
		} catch (Exception e) {
			LOGGER.error("[webUtils][outData][Message]", e.getMessage());
		} finally {
			if (writer != null) {
				writer.close();
			}
		}
	}

}
