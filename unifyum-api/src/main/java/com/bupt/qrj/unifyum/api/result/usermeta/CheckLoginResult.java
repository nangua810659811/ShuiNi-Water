/**
 * 
 */
package com.bupt.qrj.unifyum.api.result.usermeta;

import com.bupt.qrj.unifyum.api.result.BaseResult;

/**
 * @author renjun.qrj 2015��11��8��:����7:36:05
 *         com.bupt.qrj.unifyum.api.result.usermeta.CheckLoginResult unifyum-api
 *         ��;:
 *
 */
public class CheckLoginResult extends BaseResult {
	private String userName;

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName
	 *            the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

}
