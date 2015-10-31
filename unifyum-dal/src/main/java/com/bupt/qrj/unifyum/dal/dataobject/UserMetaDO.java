/**
 * 
 */
package com.bupt.qrj.unifyum.dal.dataobject;

import java.util.Date;

/**
 * @author renjun.qrj 2015��10��31��:����5:52:08 com.bupt.qrj.dataobject.UserMeta
 *         unifyum-dal ��;: - �û��˻�������Ϣ
 *
 */
public class UserMetaDO {
	/** ���� */
	private Long id;

	private String userName;

	private String password;

	private String authToken;

	private Date loginTime;

	private Date gmtCreated;

	private Date gmtModified;

	private String memo;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

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

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the authToken
	 */
	public String getAuthToken() {
		return authToken;
	}

	/**
	 * @param authToken
	 *            the authToken to set
	 */
	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}

	/**
	 * @return the loginTime
	 */
	public Date getLoginTime() {
		return loginTime;
	}

	/**
	 * @param loginTime
	 *            the loginTime to set
	 */
	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	/**
	 * @return the gmtCreated
	 */
	public Date getGmtCreated() {
		return gmtCreated;
	}

	/**
	 * @param gmtCreated
	 *            the gmtCreated to set
	 */
	public void setGmtCreated(Date gmtCreated) {
		this.gmtCreated = gmtCreated;
	}

	/**
	 * @return the gmtModified
	 */
	public Date getGmtModified() {
		return gmtModified;
	}

	/**
	 * @param gmtModified
	 *            the gmtModified to set
	 */
	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}

	/**
	 * @return the memo
	 */
	public String getMemo() {
		return memo;
	}

	/**
	 * @param memo
	 *            the memo to set
	 */
	public void setMemo(String memo) {
		this.memo = memo;
	}

}
