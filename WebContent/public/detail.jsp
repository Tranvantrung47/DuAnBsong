<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/templates/public/inc/header.jsp" %>
<div class="content_resize">
  <div class="mainbar">
  
  <%
    	Song song = (Song) request.getAttribute("song");
  		if(song != null){
    
   %>
    <div class="article">
      <h1><%=song.getName() %></h1>
      <div class="clr"></div>
      <p>Ngày đăng:<%=song.getDateCreat() %>  Lượt xem:<%=song.getCounter() %> </p>
      <div class="vnecontent">
        <%=song.getDetailText() %>
      </div>
    </div>
    <% } %>
    <div class="article">
      <h2>Bài viết liên quan</h2>
      <div class="clr"></div>
      
      <%
      
      	ArrayList<Song> relatedSongs = (ArrayList<Song>) request.getAttribute("relatedSongs");
      	if(relatedSongs != null && relatedSongs.size() > 0 ){
      		for(Song item : relatedSongs){
      %>
      <div class="comment"> <a href="<%=request.getContextPath()%>/<%=StringUtil.makeSlug(item.getCategory().getName())%>/<%=StringUtil.makeSlug(item.getName())%>-<%=item.getId()%>.html"><img src="<%=request.getContextPath()%>/files/<%=item.getPicture() %>" width="40" height="40" alt="" class="userpic" /></a>
        <h2><a href="<%=request.getContextPath()%>/<%=StringUtil.makeSlug(item.getCategory().getName())%>/<%=StringUtil.makeSlug(item.getName())%>-<%=item.getId()%>.html"><%=item.getName() %></a></h2>
        <p><%=item.getPreviewText() %></p>
      </div>
      <% }} %>
    </div>
  </div>
  <div class="sidebar">
  <%@ include file="/templates/public/inc/leftbar.jsp" %>
  </div>
  <div class="clr"></div>
</div>
<%@ include file="/templates/public/inc/footer.jsp" %>
