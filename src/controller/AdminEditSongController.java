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

@WebServlet("/AdminEditSongController")
public class AdminEditSongController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private SongDAO songDAO;
	private CategoryDAO categoryDAO;

	public AdminEditSongController() {
		super();
		songDAO = new SongDAO();
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
			response.sendRedirect(request.getContextPath() + "/admin/songs?err=1");
			return;

		}
		Song song = songDAO.getItem(id);

		if (song == null) {
			response.sendRedirect(request.getContextPath() + "/admin/songs?err=1");
			return;
		}

		// get category list

		ArrayList<Category> categories = categoryDAO.getItems();

		request.setAttribute("song", song);

		request.setAttribute("categories", categories);
		RequestDispatcher rd = request.getRequestDispatcher("/admin/song/edit.jsp");
		rd.forward(request, response);
		return;
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//check login
		if(!AuthUtil.checkLogin(request, response)) {
			response.sendRedirect(request.getContextPath() + "/login");
			return;
		}
		
		request.setCharacterEncoding("UTF-8");

		// get data
		int catId = 0;
		int id = 0;

		try {
			id = Integer.parseInt(request.getParameter("id"));
			catId = Integer.parseInt(request.getParameter("category"));
		} catch (NumberFormatException e) {
			response.sendRedirect(request.getContextPath() + "/admin/songs?err=1");
			return;

		}

		String name = request.getParameter("name");
		String previewText = request.getParameter("preview");
		String detailText = request.getParameter("detail");

		Part filePart = request.getPart("picture");

		//get dữ liệu cũ
		Song song = songDAO.getItem(id);
		if (song == null) {
			response.sendRedirect(request.getContextPath() + "/admin/songs?err=1");
			return;
		}
		
		// tạo thư mục lưu ảnh
		final String dirPathname = request.getServletContext().getRealPath("/files");
		File dirFile = new File(dirPathname);
		if (!dirFile.exists()) {
			dirFile.mkdirs();
		}

		// lấy tên file từ part
		String fileName = FileUtil.getName(filePart);

		
		
		// đổi tên file
		
		String picture = "";
		if(fileName.isEmpty()) {
			picture = song.getPicture();
		}else {
			picture = FileUtil.rename(fileName);
		}
		
		
		// đường đẫn file
		String filePathName = dirPathname + File.separator + picture;

		Category category = new Category(catId, null);
		Timestamp dateCreat = null;
		Song item = new Song(id, name, previewText, detailText, null, picture, 0, category);

		if (songDAO.editItem(item) > 0) {
			// thành công

			// ghi file
			if (!fileName.isEmpty()) {
				// xóa file cũ
				String oldFilePathName = dirPathname + File.separator + song.getPicture();
				File oldFile = new File(oldFilePathName);
				if(oldFile.exists()) {
					oldFile.delete();
				}
				
				//ghi file
				filePart.write(filePathName);
			}
			response.sendRedirect(request.getContextPath() + "/admin/songs?msg=2");
			return;

		} else {
			// thất bại

			// get category list

			ArrayList<Category> categories = categoryDAO.getItems();
			request.setAttribute("categories", categories);

			RequestDispatcher rd = request.getRequestDispatcher("/admin/song/edit.jsp?err=1");
			rd.forward(request, response);
			return;

		}

	}

}
