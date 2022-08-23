package controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.bean.Song;
import model.bean.User;
import model.dao.SongDAO;
import model.dao.UserDAO;
import util.AuthUtil;
import util.StringUtil;

public class AuthLoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserDAO  userDao;
       
    public AuthLoginController() {
        super();
        userDao = new UserDAO();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//check login
		if(AuthUtil.checkLogin(request, response)) {
			response.sendRedirect(request.getContextPath() + "/admin");
			return;
		}
		
		RequestDispatcher rd = request.getRequestDispatcher("/auth/login.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	request.setCharacterEncoding("UTF-8");
	HttpSession session = request.getSession();
	User userLogin =(User) session.getAttribute("userLogin");
	
	//chuyển hướng đến trang chủ admin khi đã login
	if(userLogin != null) {
		response.sendRedirect(request.getContextPath() + "/admin");
		return;
	}
	
	String username = request.getParameter("username");
	String password = request.getParameter("password");
	
	password = StringUtil.md5(password);
	
	
	
	// KIỂM TRA LOGIN WITH username & password
	User user = userDao.existUser(username,password);
	
	if(user != null) {
		//đăng nhập thành công
		session.setAttribute("userLogin", user);
		response.sendRedirect(request.getContextPath() + "/admin");
		return;
	}else {
		//đăng nhập thất bại
		response.sendRedirect(request.getContextPath() + "/login?err=1");
		return;
	}
	
	
	}

}
