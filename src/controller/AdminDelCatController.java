package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.dao.CategoryDAO;
import util.AuthUtil;

@WebServlet("/AdminDelCatController")
public class AdminDelCatController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private CategoryDAO categoryDAO;
       
    public AdminDelCatController() {
        super();
        categoryDAO = new CategoryDAO();
   
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//check login
		if(!AuthUtil.checkLogin(request, response)) {
			response.sendRedirect(request.getContextPath() + "/login");
			return;
		}
		
		
		int id = 0;
		try {
			id = Integer.parseInt(request.getParameter("id"));
		} catch (NumberFormatException e) {
			response.sendRedirect(request.getContextPath() + "/admin/cats?err=1");
			return;
		
		}
		
		if(categoryDAO.delItem(id) > 0 ) {
			//thành công
			response.sendRedirect(request.getContextPath() + "/admin/cats?msg=3");
			return;
		}else {
			//thất bại
			response.sendRedirect(request.getContextPath() + "/admin/cats?err=2");
			return;
		}
		
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
	}

}
