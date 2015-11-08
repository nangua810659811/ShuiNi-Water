/**
 * 
 */
package com.bupt.qrj.unifyum.api.generator;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.bupt.qrj.unifyum.uti.digest.MD5Encoder;

/**
 * @author renjun.qrj 2015��11��8��:����7:43:35
 *         com.bupt.qrj.unifyum.api.generator.UserAuthTokenGenerator unifyum-api
 *         ��;:
 *
 */
public class UserAuthTokenGenerator {

	public static String generate(String userName, Date loginTime) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateStr = sdf.format(loginTime);
		String toGeneratorStr = userName + "," + dateStr;
		return MD5Encoder.encode(toGeneratorStr);
	}

}
