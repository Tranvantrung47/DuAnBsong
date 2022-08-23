package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.bean.User;
import model.dao.UserDAO;
import util.AuthUtil;
import util.StringUtil;

@WebServlet("/AdminEditUserController")
public class AdminEditUserController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserDAO userDAO;
       
    public AdminEditUserController() {
        super();
        userDAO = new UserDAO();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//check login
		if(!AuthUtil.checkLogin(request, response)) {
			response.sendRedirect(request.getContextPath() + "/login");
			return;
		}
		
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		
		int id = 0;
		try {
			id = Integer.parseInt(request.getParameter("id"));
		} catch (NumberFormatException e) {
			response.sendRedirect(request.getContextPath() + "/admin/users?err=2");
			return;
		
		}
		
		HttpSession session = request.getSession();
		User userLogin = (User) session.getAttribute("userLogin");
		
		if("admin".equals(userDAO.getItem(userLogin.getId()).getUsername()) || (id == userLogin.getId())) {
			User item = userDAO.getItem(id);
			if(item != null) {
				request.setAttribute("user", item);
				RequestDispatcher rd = request.getRequestDispatcher("/admin/user/edit.jsp");
				rd.forward(request, response);
			}else {
				response.sendRedirect(request.getContextPath() + "/admin/users?err=2");
				return;
			}
		}else {
			//không có quyền
			response.sendRedirect(request.getContextPath() + "/admin/users?err=5");
			return;
		}
		
		
	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//check login
		if(!AuthUtil.checkLogin(request, response)) {
			response.sendRedirect(request.getContextPath() + "/login");
			return;
		}
		
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		int id = 0;
		try {
			id = Integer.parseInt(request.getParameter("id"));
		} catch (NumberFormatException e) {
			response.sendRedirect(request.getContextPath() + "/admin/users?err=2");
			return;
		}
		HttpSession session = request.getSession();
		User userLogin = (User) session.getAttribute("userLogin");
		
		if("admin".equals(userDAO.getItem(userLogin.getId()).getUsername()) || (id == userLogin.getId())) {
			// lấy lại dữ liệu cũ
			User item = userDAO.getItem(id);
			
			if(item == null) {
				
				response.sendRedirect(request.getContextPath() + "/admin/users?err=2");
				return;
				
				
				}
			
			// get data
			//String username = request.getParameter("username"); không cần vì đã disable
			String password = request.getParameter("password");
			if("".equals(password)) {
				
				password = item.getPassword();
				
			}else {
				password = StringUtil.md5(password);
			}
			
			String fullname = request.getParameter("fullname");
			
			//dữ liệu mới
			
			item.setPassword(password);
			item.setFullname(fullname);
			
			
			if(userDAO.editItem(item) > 0) {
				//thành công
				response.sendRedirect(request.getContextPath() + "/admin/users?msg=2");
				return;
				
			}else {
				// thất bại
				RequestDispatcher rd = request.getRequestDispatcher("/admin/user/edit.jsp?err=1");
				rd.forward(request, response);
				return;
			}
		}else {
			//không có quyền
			response.sendRedirect(request.getContextPath() + "/admin/users?err=4");
			return;
		}
		
		
	}

}
