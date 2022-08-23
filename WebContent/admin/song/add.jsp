<%@page import="model.bean.Category"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/templates/admin/inc/header.jsp" %>
<%@ include file="/templates/admin/inc/leftbar.jsp" %>
<div id="page-wrapper">
    <div id="page-inner">
        <div class="row">
            <div class="col-md-12">
                <h2>Thêm bài hát</h2>
            </div>
        </div>
        <!-- /. ROW  -->
        <%
        
        ArrayList<Category> categories = ( ArrayList<Category>) request.getAttribute("categories");
        String name = request.getParameter("name");
        String catId = request.getParameter("category");
        String previewText = request.getParameter("preview");
        String detailText = request.getParameter("detail");
        
        String err = request.getParameter("err");
        
        
        if ("1".equals(err)) {

			out.print("<span style=\"background:green ;color : red ; font-weight:bold; padding : 5px\">Có lỗi khi thêm</span>");
		}
        
        
        %>
        <hr />
        <div class="row">
            <div class="col-md-12">
                <!-- Form Elements -->
                <div class="panel panel-default">
                    <div class="panel-body">
                        <div class="row">
                            <div class="col-md-12">
                                <form action="" role="form" method="post" enctype="multipart/form-data" id="form">
                                    <div class="form-group">
                                        <label for="name">Tên bài hát</label>
                                        <input type="text" id="name" value="<%if (name != null) out.print(name); %> " name="name" class="form-control"  required="required"/>
                                    </div>
                                    <div class="form-group">
                                        <label for="category">Danh mục bài hát</label>
                                        <select id="category" name="category" class="form-control" required="required">
                                        <%
                                        
                                        if(categories != null && categories.size() > 0){
                                        	for(Category item : categories){
                                        
                                        %>
                                      
	                                       <option <%if (catId != null && catId.equals(String.valueOf(item.getId()))) out.print(" selected"); %>   value="<%=item.getId()%>"> <%=item.getName() %></option>
											<% } }%>
                                        </select>
                                    </div>
                                    <div class="form-group">
                                        <label for="picture">Hình ảnh</label>
                                        <input type="file" name="picture" />
                                    </div>
                                    <div class="form-group">
                                        <label for="preview">Mô tả</label>
                                        <textarea id="preview" class="form-control" rows="3" name="preview" required="required"><%if (previewText != null) out.print(previewText); %></textarea>
                                    </div>
                                    <div class="form-group">
                                        <label for="detail">Chi tiết</label>
                                        <textarea id="detail" class="form-control" id="detail" rows="5" name="detail"><%if (detailText != null) out.print(detailText); %> </textarea>
                                    </div>
                                    <button type="submit" name="submit" class="btn btn-success btn-md">Thêm</button>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- End Form Elements -->
            </div>
        </div>
        <!-- /. ROW  -->
    </div>
    <!-- /. PAGE INNER  -->
</div>
<script>
   $().ready(function() {
	   var validator = $("#form").validate({
		   errorPlacement: function(error,element){
			   $(element).closest("form").find("label[for='" + element.attr("id") + "']").append(error);
		   },
		   errorElement: "span",
		   messages: {
			   name : {
				   required: " (tên bài hát không được trống)",
			   },
		  	   category : {
			       required: " (chọn danh mục)",
		   	   },
		   	   preview : {
				   required: " (mô tả không được trống)",
			   }
			   
		   }
	   });
   });
</script>

<script>
    document.getElementById("song").classList.add('active-menu');
</script>
<!-- /. PAGE WRAPPER  -->
<%@ include file="/templates/admin/inc/footer.jsp" %>