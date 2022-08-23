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

import model.bean.Category;
import model.bean.Song;
import model.dao.SongDAO;

@WebServlet("/PublicDetailController")
public class PublicDetailController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private SongDAO songDao;
    public PublicDetailController() {
        super();
        songDao = new SongDAO();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int id = 0;
		try {
			id = Integer.parseInt(request.getParameter("id")); 
		} catch (NumberFormatException e) {
			response.sendRedirect(request.getContextPath() + "/404");
			return;
		}
		
		Song song = songDao.getItem(id);
		if(song == null) {
			response.sendRedirect(request.getContextPath() + "/404");
			return;
		}
		
		
		// tăng view
		
		HttpSession session =request.getSession();
		String hasVisited = (String) session.getAttribute("hasVisited: " + id);
		if(hasVisited == null) {
			session.setAttribute("hasVisited: " + id ,"yes");
			session.setMaxInactiveInterval(3600);
			//increase page view
			songDao.increaseView(id);
		}
		ArrayList<Song> relatedSongs = songDao.getRalatedItems(song,5);
		
		
		request.setAttribute("song", song);
		request.setAttribute("relatedSongs", relatedSongs);
		RequestDispatcher rd = request.getRequestDispatcher("/public/detail.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}
