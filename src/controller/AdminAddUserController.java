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

@WebServlet("/AdminAddUserController")
public class AdminAddUserController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserDAO userDAO;
       
    public AdminAddUserController() {
        super();
        userDAO = new UserDAO();
  
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//check login
		if(!AuthUtil.checkLogin(request, response)) {
			response.sendRedirect(request.getContextPath() + "/login");
			return;
		}
		
		HttpSession session = request.getSession();
		User userLogin = (User) session.getAttribute("userLogin");
		
		if(!"admin".equals(userLogin.getUsername())) {
			//chỉ có admin mới được thêm người dùng
			response.sendRedirect(request.getContextPath() + "/admin/users?err=3");
			return;
		}
		
		
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");		
		
		RequestDispatcher rd = request.getRequestDispatcher("/admin/user/add.jsp");
		rd.forward(request, response);
	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//check login
		if(!AuthUtil.checkLogin(request, response)) {
			response.sendRedirect(request.getContextPath() + "/login");
			return;
		}
		
		HttpSession session = request.getSession();
		User userLogin = (User) session.getAttribute("userLogin");
		
		if(!"admin".equals(userLogin.getUsername())) {
			//chỉ có admin mới được thêm người dùng
			response.sendRedirect(request.getContextPath() + "/admin/users?err=4");
			return;
		}
		
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String fullname = request.getParameter("fullname");
		
		
		
		// kiểm tra trùng username
		if(userDAO.hasUser(username)) {
			RequestDispatcher rd = request.getRequestDispatcher("/admin/user/add.jsp?err=2");
			rd.forward(request, response);
			return;
		}
		
		// mã hóa password
		password = StringUtil.md5(password); // kí tự ngẫu nhiên
		
		
		
		User item = new User(0, username, password, fullname);
		if(userDAO.addItem(item) > 0) {
			//thành công
			
			response.sendRedirect(request.getContextPath() + "/admin/users?msg=1");
			return;
		}else {
			//thất bại
			RequestDispatcher rd = request.getRequestDispatcher("/admin/user/add.jsp?err=1");
			rd.forward(request, response);
			return;
		}
		
		
	
	}

}
