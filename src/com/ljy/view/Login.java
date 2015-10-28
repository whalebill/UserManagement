package com.ljy.view;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Login extends HttpServlet {

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
		
		out.println("<img src='imgs/love.png' width = '150' height = '100'><hr/>");
		out.println("<h1>User Login</h1>");
		
		
		//action  /web name/Servlet URL		
		out.println("<form action = '/UserManagement/LoginCL' method = 'post'>");
		out.println("User Id: <input type ='text' name = 'id'/><br/>");
		out.println("Password: <input type = 'password' name = 'password'/><br/>");
		out.println("<input type = 'submit' value='Login'/><br/>");
		out.println("</form>");
		String errInfo = (String)request.getAttribute("err");
		if(errInfo != null) out.println("<font color = 'red'>"+errInfo+"</font>");
		out.println("<hr/><img src = 'imgs/love3.png' width = '200' height = '100'>");
		
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
