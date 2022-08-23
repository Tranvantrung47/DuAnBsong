package controller;

import java.io.File;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.bean.Song;
import model.dao.SongDAO;
import util.AuthUtil;

@WebServlet("/AdminDelSongController")
public class AdminDelSongController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       private SongDAO songDAO;
    public AdminDelSongController() {
        super();
        songDAO = new SongDAO();
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
	} catch (Exception e) {
		response.sendRedirect(request.getContextPath() + "/admin/songs?err=1");
		return;
	}
	
	//get bài hát với id hiện tại
	Song song = songDAO.getItem(id);
	if(song == null) {
		response.sendRedirect(request.getContextPath() + "/admin/songs?err=1");
		return;
	}
	if(songDAO.delItem(id) > 0) {
		//thành công
		
		// xóa luôn ảnh
		
		final String dirPathName = request.getServletContext().getRealPath("/files");
		String picture = song.getPicture();
		if(!picture.isEmpty()) {
		String filePathname = dirPathName + File.separator + picture;
		File file = new File(filePathname);
		if(file.exists()){
		file.delete();
		}
		}
		
		
		response.sendRedirect(request.getContextPath() + "/admin/songs?msg=3");
		return;
	}else {
		//thất bại
		response.sendRedirect(request.getContextPath() + "/admin/songs?err=2");
		return;
		
	}
	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
	
	}

}
