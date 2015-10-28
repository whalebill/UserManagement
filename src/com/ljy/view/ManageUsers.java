package com.ljy.view;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ljy.domain.User;
import com.ljy.service.UsersService;

public class ManageUsers extends HttpServlet {

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
		out.println("<script type = 'text/javascript' language = 'javascript'>");
		out.println("function gotoPageNow(){ "
						+ "var pageNow = document.getElementById('pageNow');"
						+ "window.open('/UserManagement/ManageUsers?pageNow='+pageNow.value,'_self');"
						+ "}"+
						"function confirmOper(){ return window.confirm('Do you confirm to delete this user?')}");
		out.println("</script>");
		out.println("<img src='imgs/love.png' width = '150' height = '100'> Welcome,  <a href = '/UserManagement/MainFrame'>Return to My Account</a>  <a href = '/UserManagement/Login'>Quit</a><hr/>");
		out.println("<h3>Manage Users</h3>");

		// page division variables
		int pageNow = 1; // Init current page
		String sPageNow = request.getParameter("pageNow");
		if (sPageNow != null)
			pageNow = Integer.parseInt(sPageNow);
		int pageSize = 4; // Number of rows in one page
		UsersService usersService = new UsersService();
		ArrayList<User> al = usersService.getUsersByPage(pageNow, pageSize);
		int pageCount = usersService.getPageCount(pageSize);
		out.println("<table border = '1' bordercolor = 'pink' cellspacing = '0' cellpadding = '15' width = '500'>");
		out.println("<tr><th>Id</th><th>Username</th><th>Email</th><th>Grade</th><th>Update User</th><th>Delete User</th></tr>");
		for (User u : al) {
			out.println("<tr><td>" + u.getId() + "</td><td>" + u.getName()
					+ "</td><td>" + u.getEmail() + "</td><td>" + u.getGrade()
					+ "</td><td><a href = '/UserManagement/UserClServlet?type=gotoUpdView&id="+u.getId()+"'>Update</a></td>"+
					"<td><a onClick='return confirmOper()' href = '/UserManagement/UserClServlet?type=del&id="+u.getId()+"'>Delete</a></td></tr>");
		}
		out.println("</table><br/>");
		// previous
		if (pageNow != 1)
			out.println("<a href = '/UserManagement/ManageUsers?pageNow="
					+ (pageNow - 1) + "'>Previous</a>");
		// show page division
		for (int i = 1; i <= pageCount; i++) {
			out.println("<a href = '/UserManagement/ManageUsers?pageNow=" + i
					+ "'><" + i + "></a>");
		}
		// next
		if (pageNow != pageCount)
			out.println("<a href = '/UserManagement/ManageUsers?pageNow="
					+ (pageNow + 1) + "'>Next</a>");
		// Page division info
		out.println("&nbsp;&nbsp;&nbsp;Current: " + pageNow + "/Total: "
				+ pageCount + "<br/>");
		out.println("Page:<input type = 'text' id = 'pageNow' name = 'pageNow'/><input type = 'button' onClick = 'gotoPageNow()' value = 'Go'>");
		out.println("<hr/><img src = 'imgs/love3.png' width = '200' height = '100'>");
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
