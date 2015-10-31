/**
 * 
 */
package com.bupt.qrj.unifyum.api.result;

/**
 * @author renjun.qrj 2015��10��31��:����8:43:13
 *         com.bupt.qrj.unifyum.api.result.BaseResult unifyum-api ��;:
 *
 */
public class BaseResult {
	/** �Ƿ�����˺�̨���� */
	private boolean success;
	/** ������Ϣ���� */
	private String errMsg;
	/** ����ֵ���� */
	private String code;

	/**
	 * @return the success
	 */
	public boolean isSuccess() {
		return success;
	}

	/**
	 * @param success
	 *            the success to set
	 */
	public void setSuccess(boolean success) {
		this.success = success;
	}

	/**
	 * @return the errMsg
	 */
	public String getErrMsg() {
		return errMsg;
	}

	/**
	 * @param errMsg
	 *            the errMsg to set
	 */
	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code
	 *            the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

}
