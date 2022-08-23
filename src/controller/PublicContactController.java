package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.bean.Contact;
import model.dao.ContactDAO;

@WebServlet("/PublicContactController")
public class PublicContactController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ContactDAO contactDao; 
       
    public PublicContactController() {
        super();
        contactDao = new ContactDAO();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		RequestDispatcher rd = request.getRequestDispatcher("/public/contact.jsp");
		rd.forward(request, response);
	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String website = request.getParameter("website");
		String message = request.getParameter("message");
		
		Contact item = new Contact(0, name, email, website, message);
		if(contactDao.addItem(item) > 0) {
			//thêm thành công
			response.sendRedirect(request.getContextPath() + "/contact?msg=1");
		}else {
			// thêm thất bại
			RequestDispatcher rd = request.getRequestDispatcher("/public/contact.jsp?err=1");
			rd.forward(request, response);
			
		}
	
	
	}

}
