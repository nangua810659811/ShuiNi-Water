package com.bupt.qrj.unifyum.api.controller.impl;

import com.bupt.qrj.unifyum.dal.dao.impl.findpicDAOImpl;

import com.bupt.qrj.unifyum.dal.dataobject.picinfoDO;
import com.bupt.qrj.unifyum.dal.dataobject.picinfoexceptionDO;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;

@Controller
@RequestMapping("/imageDownload.req")
public class Filedownload extends HttpServlet{

    public static ApplicationContext getContext() {
        ApplicationContext context = new ClassPathXmlApplicationContext(
                new String[] { "META-INF/spring/unifyum-dal-dao.xml", "META-INF/spring/unifyum-dal-db.xml" });
        return context;
    }

    ApplicationContext context = getContext();

		@RequestMapping(method = { RequestMethod.GET }, params = "action=filedownload")
		public void doGet(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {

            findpicDAOImpl findpicDAO = (findpicDAOImpl) context.getBean("findpicDAO");
            String phone = request.getParameter("phone");
            String mission_id = request.getParameter("mission_id");
            String event_id = request.getParameter("event_id");
            String work_name = request.getParameter("work_name");//header


//			String patha = "";

//            if(type.equals("header")){
//                System.out.println("header1");
//                headpicDO headpicDO = new headpicDO();
//                headpicDO.setPhone(phone);
//
//                headpicDO user = findpicDAO.getheader(headpicDO);
//                patha = user.getHead_pic();
//                System.out.println(patha+" header_pic");
//            }else{
                System.out.println("no header 2");
                picinfoDO picinfoDO = new picinfoDO();
                picinfoDO.setMission_id(mission_id);
                picinfoDO.setEvent_id(event_id);
                picinfoDO.setWork_name(work_name);

                picinfoDO user = findpicDAO.get(picinfoDO);
                String patha = user.getPic();
                System.out.println(patha+" mission_pic");
//            }

            //String path = "D:\\Program Files\\Apache Software Foundation\\apache-tomcat-7.0.73\\webapps\\water" +patha;
            String path = "C:\\apache-tomcat-8.5.23\\webapps\\water" +patha;

            /*String pathb = "F:\\upload\\abc.jpg";*/
            //witcher3

            String[] ary = patha.split("\\\\");
            String fileName = ary[3];
            System.out.println(fileName);
            System.out.println(path);


			File file = new File(path);

			if(!file.exists()){
				request.setAttribute("message", "您要下载的资源已被删除！！");
				request.getRequestDispatcher("/message.jsp").forward(request, response);
				return;
			}

			String realname = fileName.substring(fileName.indexOf("_")+1);

			response.setHeader("content-disposition", "filename=" + URLEncoder.encode(realname, "UTF-8"));

			FileInputStream in = new FileInputStream(path);

			OutputStream out = response.getOutputStream();

			byte buffer[] = new byte[1024];
			int len = 0;

			while((len=in.read(buffer))>0){

				out.write(buffer, 0, len);
			}

			in.close();

			out.close();
		}

	@RequestMapping(method = { RequestMethod.GET }, params = "action=exceptiondownload")
	public void doGetexception(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		findpicDAOImpl findpicDAO = (findpicDAOImpl) context.getBean("findpicDAO");

		String id = request.getParameter("id");


		System.out.println("no header 2");
		picinfoexceptionDO picinfoexceptionDO = new picinfoexceptionDO();
		picinfoexceptionDO.setId(id);
		picinfoexceptionDO user = findpicDAO.getexception(picinfoexceptionDO);
		String patha = user.getPic();

//            }

		//String path = "D:\\Program Files\\Apache Software Foundation\\apache-tomcat-7.0.73\\webapps\\water" +patha;
		String path = "C:\\apache-tomcat-8.5.23\\webapps\\water" +patha;

            /*String pathb = "F:\\upload\\abc.jpg";*/
		//witcher3!

		String[] ary = patha.split("\\\\");
		String fileName = ary[3];
		System.out.println(fileName);
		System.out.println(path);


		File file = new File(path);

		if(!file.exists()){
			request.setAttribute("message", "您要下载的资源已被删除！！");
			request.getRequestDispatcher("/message.jsp").forward(request, response);
			return;
		}

		String realname = fileName.substring(fileName.indexOf("_")+1);

		response.setHeader("content-disposition", "filename=" + URLEncoder.encode(realname, "UTF-8"));

		FileInputStream in = new FileInputStream(path);

		OutputStream out = response.getOutputStream();

		byte buffer[] = new byte[1024];
		int len = 0;

		while((len=in.read(buffer))>0){

			out.write(buffer, 0, len);
		}

		in.close();

		out.close();
	}

		public void doPost(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {
			doGet(request, response);
		}


	}




