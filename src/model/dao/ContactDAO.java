package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.mysql.cj.xdevapi.DbDoc;

import model.bean.Contact;
import util.DBConnectionUtil;
import util.DefineUtil;

public class ContactDAO {
	private Connection conn;
	private PreparedStatement pst;
	private Statement st;
	private ResultSet rs;
	
	
	public ArrayList<Contact> getItems() {
		ArrayList<Contact> items = new ArrayList<>();
		conn = DBConnectionUtil.getConnection();
		String query = "SELECT id,name,email,website,message FROM contacts ORDER BY id DESC";
		try {
			st = conn.createStatement();
			rs = st.executeQuery(query);
			
			while(rs.next()) {
				
				int id = rs.getInt("id");
				String name = rs.getString("name");
				String email = rs.getString("email");
				String website = rs.getString("website");
				String message = rs.getString("message");
				
				Contact item = new Contact(id, name, email, website, message);
				items.add(item);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			if(conn != null && st != null && rs != null) {
				try {
					rs.close();
					st.close();
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		
		
		return items;
	}


	public int delItem(int id) {
		int result = 0;
		conn = DBConnectionUtil.getConnection();
		String query = "DELETE FROM contacts WHERE id = ?";
		
		try {
			pst = conn.prepareStatement(query);
			pst.setInt(1, id);
			
			result = pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			if(pst != null && conn != null) {
				try {
					pst.close();
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		
		return result;
	}


	public int addItem(Contact item) {
		int result = 0;
		conn = DBConnectionUtil.getConnection();
		String query = "INSERT INTO  contacts(name,email,website,message) VALUES(?,?,?,?)";
		
		try {
			pst = conn.prepareStatement(query);
			pst.setString(1, item.getName());
			pst.setString(2, item.getEmail());
			pst.setString(3, item.getWebsite());
			pst.setString(4, item.getMessage());
			
			result = pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			if(pst != null && conn != null) {
				try {
					pst.close();
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		
		return result;
	}


	public int numberOfItems() {
		conn = DBConnectionUtil.getConnection();
		String query = "SELECT COUNT(*) AS count FROM contacts ";
		try {
			st = conn.createStatement();
			rs = st.executeQuery(query) ;
			
			if (rs.next()) {
				int count = rs.getInt("count");
				return count;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			if(conn != null && st != null && rs != null) {
				try {
					rs.close();
					st.close();
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		
		
		return 0;
	}


	public ArrayList<Contact> getItemsPagination(int offset) {
		ArrayList<Contact> items = new ArrayList<>();
		conn = DBConnectionUtil.getConnection();
		String query = "SELECT id,name,email,website,message FROM contacts ORDER BY id DESC LIMIT ?, ?";
		try {
			pst = conn.prepareStatement(query);
			pst.setInt(1, offset);
			pst.setInt(2, DefineUtil.NUMBER_PER_PAGE);
			rs = pst.executeQuery();
			
			while(rs.next()) {
				
				int id = rs.getInt("id");
				String name = rs.getString("name");
				String email = rs.getString("email");
				String website = rs.getString("website");
				String message = rs.getString("message");
				
				Contact item = new Contact(id, name, email, website, message);
				items.add(item);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			if(conn != null && pst != null && rs != null) {
				try {
					rs.close();
					pst.close();
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return items;
	}

}
