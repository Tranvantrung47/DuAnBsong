package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.bean.Category;
import model.dao.CategoryDAO;
import util.AuthUtil;

/**
 * Servlet implementation class AdminEditCatController
 */
@WebServlet("/AdminEditCatController")
public class AdminEditCatController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private CategoryDAO categoryDAO;

	public AdminEditCatController() {
		super();
		categoryDAO = new CategoryDAO();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
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
			response.sendRedirect(request.getContextPath() + "/admin/cats?err=1");
			return;
		}
		Category category = categoryDAO.getItem(id);

		if (category != null) {
			// dữ liệu đúng

			request.setAttribute("category", category);
			RequestDispatcher rd = request.getRequestDispatcher("/admin/cat/edit.jsp");
			rd.forward(request, response);

		} else {
			// dữ liệu không tồn tại
			response.sendRedirect(request.getContextPath() + "/admin/cats?err=1");
			return;

		}

		RequestDispatcher rd = request.getRequestDispatcher("/admin/cat/edit.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//check login
		if(!AuthUtil.checkLogin(request, response)) {
			response.sendRedirect(request.getContextPath() + "/login");
			return;
		}
		
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		
		//get category id
		int id = 0;

		try {
			id = Integer.parseInt(request.getParameter("id"));

		} catch (NumberFormatException e) {
			response.sendRedirect(request.getContextPath() + "/admin/cats?err=1");
			return;
		}

		String name = request.getParameter("name");

		Category item = new Category(id, name);
		if (categoryDAO.editItem(item) > 0) {
			// thành công
			response.sendRedirect(request.getContextPath() + "/admin/cats?msg=2");
			return;

		} else {
			// thất bại
			RequestDispatcher rd = request.getRequestDispatcher("/admin/cat/edit.jsp?err=1");
			rd.forward(request, response);
			return;
		}
	}

}
