/**
 * 
 */
package com.bupt.qrj.unifyum.api.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author renjun.qrj  2015��12��30��:����7:17:57
 * com.bupt.qrj.unifyum.api.controller.UserImageController
 * unifyum-api
 * ��;: 
 *
 */
public interface UserImageController {

    public void upload(HttpServletRequest request, HttpServletResponse response);

    public void list(HttpServletRequest request, HttpServletResponse response);

    public void jqList(HttpServletRequest request, HttpServletResponse response);

    public void view(HttpServletRequest request, HttpServletResponse response);

    public void showImage(HttpServletRequest request, HttpServletResponse response);

    public void updateImage(HttpServletRequest request, HttpServletResponse response,
                            MultipartFile file);

}
