package controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.bean.Song;
import model.dao.SongDAO;
import util.AuthUtil;
import util.DefineUtil;

@WebServlet("/AdminIndexSongController")
public class AdminIndexSongController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private SongDAO songDAO; 
	
    public AdminIndexSongController() {
        super();
        songDAO = new SongDAO();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//check login
		if(!AuthUtil.checkLogin(request, response)) {
			response.sendRedirect(request.getContextPath() + "/login");
			return;
		}
		
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		
		// lấy tổng số bài hát
		int numberOfItems = songDAO.numberOfItems();
		int numberOfPages = (int) Math.ceil((float) numberOfItems / DefineUtil.NUMBER_PER_PAGE);
		
		int currentPage = 1;
		try {
			currentPage  = Integer.parseInt(request.getParameter("page"));
		} catch (NumberFormatException e) {
		}
		
		if(currentPage > numberOfPages || currentPage < 1) {
			currentPage = 1;
		}
		int offset = (currentPage - 1) * DefineUtil.NUMBER_PER_PAGE;
		
		
		ArrayList<Song> songs = songDAO.getItemsPagination(offset);
		
		request.setAttribute("numberOfItems", numberOfItems);
		request.setAttribute("numberOfPages", numberOfPages);
		request.setAttribute("currentPage", currentPage);
		request.setAttribute("songs", songs);
		
		RequestDispatcher rd = request.getRequestDispatcher("/admin/song/index.jsp");
	rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	doGet(request, response);
	}

}
