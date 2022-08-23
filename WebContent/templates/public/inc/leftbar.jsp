<%@page import="util.StringUtil"%>
<%@page import="model.bean.Song"%>
<%@page import="model.dao.SongDAO"%>
<%@page import="model.dao.CategoryDAO"%>
<%@page import="model.bean.Category"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<div class="searchform">
  <form id="formsearch" name="formsearch" method="post" action="#">
    <span>
    <input name="editbox_search" class="editbox_search" id="editbox_search" maxlength="80" value="Tìm kiếm bài hát" type="text" />
    </span>
    <input name="button_search" src="<%=request.getContextPath()%>/templates/public/images/search.jpg" class="button_search" type="image" />
  </form>
</div>
<div class="clr"></div>
<div class="gadget">
  <h2 class="star">Danh mục bài hát</h2>
  <div class="clr"></div>
  <ul class="sb_menu">
  
  <%
  
  CategoryDAO categoryDAO = new CategoryDAO();
   ArrayList<Category> categories = categoryDAO.getItems();
   if(categories.size() > 0){
	   for(Category item : categories){
  %>
    <li><a href="<%=request.getContextPath()%>/<%=StringUtil.makeSlug(item.getName())%>-<%=item.getId()%>"><%=item.getName() %></a></li>
    
    <% }} %>
  </ul>
</div>

<div class="gadget">
  <h2 class="star"><span>Bài hát mới</span></h2>
  <div class="clr"></div>
  <ul class="ex_menu">
  <%
  
  SongDAO songDAO = new SongDAO();
   ArrayList<Song> recentSongs = songDAO.getItems(6);
   if(recentSongs.size() > 0){
	   for(Song item : recentSongs){
  %>
    <li><a href=""<%=request.getContextPath()%>/<%=StringUtil.makeSlug(item.getCategory().getName())%>/<%=StringUtil.makeSlug(item.getName())%>-<%=item.getId()%>.html""><%=item.getName() %></a><br />
     <%if (item.getPreviewText().length() > 50) out.print(item.getPreviewText().substring(0, 50) + "..."); else out.print(item.getPreviewText());%></li>
  <% }} %>
  </ul>
</div>