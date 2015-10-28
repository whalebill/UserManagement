package com.ljy.view;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ljy.domain.User;

public class AddUserView extends HttpServlet {

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
		
		out.println("<img src='imgs/love.png' width = '150' height = '100'> Welcome,  <a href = '/UserManagement/MainFrame'>Return to My Account</a>  <a href = '/UserManagement/Login'>Quit</a><hr/><br/>");
		out.println("<h3>Add New User</h3><br/>");
		//Display user
		out.println("<form action = '/UserManagement/UserClServlet?type=add' method = 'post'>");
		out.println("<table border = '1' bordercolor = 'pink' cellspacing = '0' cellpadding = '15' width = '500'>");
//		out.println("<tr><td>ID</td><td><input type = 'text' readonly name = 'id'></td></tr>");
		out.println("<tr><td>Username</td><td><input type = 'text' name = 'username'></td></tr>");
		out.println("<tr><td>Password</td><td><input type = 'text' name = 'password'></td></tr>");
		out.println("<tr><td>Email</td><td><input type = 'text' name = 'email'></td></tr>");
		out.println("<tr><td>Tel</td><td><input type = 'text' name = 'tel'></td></tr>");
		out.println("<tr><td>Grade</td><td><input type = 'text' name = 'grade'></td></tr>");
		out.println("<tr><td><input type = 'submit' value = 'Submit'></td><td><input type = 'reset' value = 'Reset'></td></tr>");
		out.println("</table>");
		out.println("</form>");
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
