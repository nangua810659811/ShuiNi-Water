/**
 * 
 */
package com.bupt.qrj.unifyum.api.controller;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author renjun.qrj  2016��1��2��:����9:47:27
 * com.bupt.qrj.unifyum.api.controller.UserTestDataController
 * unifyum-api
 * ��;: 
 *
 */
public interface UserTestDataController {

    void updateTestData(HttpServletRequest request, HttpServletResponse response)
                                                                                 throws UnsupportedEncodingException;

    void getTestData(HttpServletRequest request, HttpServletResponse response);

    void jqList(HttpServletRequest request, HttpServletResponse response);

}
