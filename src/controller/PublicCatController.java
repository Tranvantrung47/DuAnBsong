package controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.bean.Category;
import model.bean.Song;
import model.dao.CategoryDAO;
import model.dao.SongDAO;
import util.DefineUtil;

@WebServlet("/PublicCatController")
public class PublicCatController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private CategoryDAO categoryDao;
	private SongDAO songDao;
       
    public PublicCatController() {
        super();
        categoryDao = new CategoryDAO();
        songDao = new SongDAO();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int id = 0;
		
		
		try {
			id = Integer.parseInt(request.getParameter("id"));
		} catch (Exception e) {
			response.sendRedirect(request.getContextPath() + "/404");
			return;
		}
		
		int currentPage = 1;
		try {
			currentPage = Integer.parseInt(request.getParameter("page"));
		} catch (Exception e) {
			 currentPage = 1;
			
		}
		
		Category category = categoryDao.getItem(id);
		if(category == null) {
			response.sendRedirect(request.getContextPath() + "/404");
			return;
		}
		
		int numberOfItems = songDao.numberOfItems(id);
		int numberOfPages = (int) Math.ceil((float) numberOfItems / DefineUtil.NUMBER_PER_PAGE);
		if(currentPage > numberOfPages || currentPage < 1) {
			currentPage = 1 ;
		}
		int offset = (currentPage - 1) * DefineUtil.NUMBER_PER_PAGE;
		
		
		
		ArrayList<Song> songs = songDao.getItemsByCategoryPagination(offset,id);
		
		request.setAttribute("numberOfPages", numberOfPages);
		request.setAttribute("currentPage", currentPage);
		request.setAttribute("category", category);
		request.setAttribute("songs", songs);
		
		
		RequestDispatcher rd = request.getRequestDispatcher("/public/cat.jsp");
		rd.forward(request, response);
	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	doGet(request, response);
	}

}
