package com.ljy.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ljy.domain.User;
import com.ljy.service.UsersService;

import java.sql.*;

public class LoginCL extends HttpServlet {

	/**
	 * The doGet method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset = utf-8");
		request.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		// receive username and password

		String id = request.getParameter("id");
		String password = request.getParameter("password");

		// Construct UsersService Object, complete validation
		UsersService usersService = new UsersService();
		User user = new User();
		user.setId(Integer.parseInt(id));
		user.setPwd(password);
		if (usersService.checkUser(user)) {
			//save user to session
			HttpSession session = request.getSession();
			session.setAttribute("loginuser", user);
			
			request.getRequestDispatcher("/MainFrame").forward(request,	response);
		} else {
			request.setAttribute("err", "User Id or Password is incorrect!");
			request.getRequestDispatcher("/Login").forward(request, response);
		}

		/*
		 * request.getSession().setAttribute("loginuser", username);
		 * 
		 * if("junyao".equals(username)&&"junyao".equals(password)){
		 * response.sendRedirect
		 * ("/UserManagement/MainFrame?uname="+username+"&pwd="+password);
		 * }else{ response.sendRedirect("/UserManagement/Login"); }
		 */
	}

	/**
	 * The doPost method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to
	 * post.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		this.doGet(request, response);
	}

}
