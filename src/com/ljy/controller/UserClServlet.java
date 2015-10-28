package com.ljy.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ljy.domain.User;
import com.ljy.service.UsersService;

public class UserClServlet extends HttpServlet {

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset = utf-8");
		request.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		String id = request.getParameter("id");
		String type = request.getParameter("type");
		UsersService usersService = new UsersService();
		if(type.equals("del")){
			if(usersService.delUser(id)){
				request.setAttribute("info", "del");
				request.getRequestDispatcher("/Ok").forward(request, response);
			} else {
				request.setAttribute("info", "del");
				request.getRequestDispatcher("/Err").forward(request, response);		
			}
		}else if(type.equals("gotoUpdView")){
			User user = usersService.getUserById(id);
			request.setAttribute("userInfo", user);
			request.getRequestDispatcher("/UpdateUserView").forward(request, response);
		}else if(type.equals("update")){
			//Receive new info
			id = request.getParameter("id");
			String username = request.getParameter("username");
			String email = request.getParameter("email");
			String grade = request.getParameter("grade");
			String password = request.getParameter("password");
			String tel = request.getParameter("tel");
			
			User user = new User(Integer.parseInt(id), username, password, email, tel, Integer.parseInt(grade));
			if(usersService.updUser(user)){
				request.setAttribute("info", "update");
				request.getRequestDispatcher("/Ok").forward(request, response);
			}else{
				request.setAttribute("info", "update");
				request.getRequestDispatcher("/Err").forward(request, response);		
			}
		}else if(type.equals("gotoAddUser")){
			request.getRequestDispatcher("/AddUserView").forward(request, response);
		}else if(type.equals("add")){
			//receive user info
			String username = request.getParameter("username");
			String email = request.getParameter("email");
			String grade = request.getParameter("grade");
			String password = request.getParameter("password");
			String tel = request.getParameter("tel");
			
			//Construct user
			User user = new User();
			user.setEmail(email);
			user.setGrade(Integer.parseInt(grade));
			user.setName(username);
			user.setPwd(password);
			user.setTel(tel);
			
			if(usersService.addUser(user)){
				request.setAttribute("info", "add");
				request.getRequestDispatcher("/Ok").forward(request, response);
			}else{
				request.setAttribute("info", "add");
				request.getRequestDispatcher("/Err").forward(request, response);		
			}
		}
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		this.doGet(request, response);
	}

}
