package com.ljy.view;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ljy.domain.User;

public class MainFrame extends HttpServlet {

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
		
		//retrieve session
		User u = (User) request.getSession().getAttribute("loginuser");
		if(u == null){
			//back to login
			request.setAttribute("err", "Please input username and password to login");
			request.getRequestDispatcher("/Login").forward(request, response);
			return;//must add a "return", or it will continue go through
		}
		
		response.setContentType("text/html;charset = utf-8");
		request.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		Cookie[] cookie = request.getCookies();
		boolean b = false;
		String now = null;
		if(cookie != null){
			for(Cookie c : cookie){
				if(c.getName().equals("time")){
					b = true;
					now = c.getValue();
					SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					now = simpleDateFormat.format(new java.util.Date());
					c.setValue(now);
					c.setMaxAge(3600*24*7);
					response.addCookie(c);				
					break;
				}
			}
		}
		if(!b){
			now = "This is your first time login.";
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Cookie myCookie = new Cookie("time", simpleDateFormat.format(new java.util.Date()));
			myCookie.setMaxAge(3600*24*7);
			response.addCookie(myCookie);
		}
		
		out.println("<img src='imgs/love.png' width = '150' height = '100'> Welcome,  <a href = '/UserManagement/Login'>Quit</a><hr/>");
		
		out.println("<h3>My Account</h3>");
		out.println("<a href = '/UserManagement/ManageUsers'>Manage User</a></br>");
		out.println("<a href = '/UserManagement/UserClServlet?type=gotoAddUser'>Add User</a></br>");
		out.println("<a href = ''>Search User</a></br>");
		out.println("<a href = ''>Quit</a><br></br>");
		out.println("<a>"+now+"</a>");
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
