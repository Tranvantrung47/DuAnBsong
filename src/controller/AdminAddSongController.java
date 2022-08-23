package controller;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import model.bean.Category;
import model.bean.Song;
import model.dao.CategoryDAO;
import model.dao.SongDAO;
import util.AuthUtil;
import util.FileUtil;
@MultipartConfig

@WebServlet("/AdminAddNewsController")
public class AdminAddSongController extends HttpServlet {
	private static final long serialVersionUID = 1L;
		private SongDAO songDAO;
		private CategoryDAO categoryDAO;
    public AdminAddSongController() {
        super();
        songDAO = new SongDAO(); 
        categoryDAO = new CategoryDAO();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//check login
		if(!AuthUtil.checkLogin(request, response)) {
			response.sendRedirect(request.getContextPath() + "/login");
			return;
		}
		
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		
		//get category list
		
		ArrayList<Category> categories = categoryDAO.getItems();
		request.setAttribute("categories", categories);
		
		
		RequestDispatcher rd = request.getRequestDispatcher("/admin/song/add.jsp");
		rd.forward(request, response);
		return;
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//check login
		if(!AuthUtil.checkLogin(request, response)) {
			response.sendRedirect(request.getContextPath() + "/login");
			return;
		}
		
		request.setCharacterEncoding("UTF-8");
		
		//get data
		int catId = 0;
		
		try {
			catId = Integer.parseInt(request.getParameter("category"));
		} catch (NumberFormatException e) {
			response.sendRedirect(request.getContextPath() + "/admin/songs?err=1");
			return;
			
		}
		
		
		String name = request.getParameter("name");
		String previewText = request.getParameter("preview");
		String detailText = request.getParameter("detail");
		
		
		Part filePart = request.getPart("picture");
		
		// có thể có hoặc không datecreat,nếu dateCreat null thì csdl tự lấy ngày giờ hiện tại
		//Timestamp dateCreat = new Timestamp(catId);
		
		//tạo thư mục lưu ảnh
		final String dirPathname = request.getServletContext().getRealPath("/files");
		File dirFile = new File(dirPathname);
		if(!dirFile.exists()) {
			dirFile.mkdirs();
		}
		
		// lấy tên file tuef part
		String fileName = FileUtil.getName(filePart);
		
		// đổi tên file
		String picture = FileUtil.rename(fileName);
		
		// đường đẫn file
		String filePathName = dirPathname + File.separator + picture;
		
		Category category = new Category(catId, null);
		Timestamp dateCreat = null;
		Song item = new Song(0, name, previewText, detailText, dateCreat, picture, 0, category);
		
		if(songDAO.addItem(item) > 0 ) {
			//thành công
			
			
			//ghi file
			if(!fileName.isEmpty()) {
				filePart.write(filePathName);
			}
			response.sendRedirect(request.getContextPath() + "/admin/songs?msg=1");
			return;
			
		}else {
			//thất bại
			
			//get category list
			
			ArrayList<Category> categories = categoryDAO.getItems();
			request.setAttribute("categories", categories);
			
			RequestDispatcher rd = request.getRequestDispatcher("/admin/song/add.jsp?err=1");
			rd.forward(request, response);
			return;
			
		}
	
	}

}
