<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>Document</title>
    <link rel="stylesheet" href="${ pageContext.servletContext.contextPath }/resources/css/reset.css">
    <link rel="stylesheet" href="${ pageContext.servletContext.contextPath }/resources/css/admin_main.css">
    <script src="https://code.jquery.com/jquery-3.5.1.js" ></script>
    <style>
        .system-user-head{
            font-size: 30px;
            padding: 15px;
            border-bottom: 2px solid rgb(119, 94, 238);
            color: rgb(119, 94, 238);

        }
        
        #userSearchForm input,
        #userSearchForm select,
        #userSearchForm label,
        #userSearchForm button
        {
        	height: 40px;
        	vertical-align: middle;
        	line-height: 40px;
        	padding: 3px;
        }
        
        .system-user-searchBar input[type=date]{
        
        	border: 1px solid black;
        	font-size: 14px;
        
        }
        
        .system-user-searchBar select{
        
        	border: 1px solid black;
        	font-size: 18px;
        
        }
        
        .system-user-searchBar input[type=search]{
        
        	border: 1px solid black;
        	font-size: 14px;
        
        }
        
        .system-user-list{
        
        	width: 1490px;
        	margin: 0 auto;
        
        }

        .system-user-searchBar{
            margin-top: 20px;
            margin-bottom: 20px;
            font-size: 20px;
           
        }

        .system-user-searchBar>select{
            font-size: 18px;
        }


        .system-user-searchBar > input[type="search"] {
            border: 2px solid rgb(119, 94, 238);
            width: 400px;
            line-height: 2;
        }

        .system-user-searchBar > input[type="button"] {
            font-size: 18px;
            padding: 3px;
            background-color: rgb(119, 94, 238);
        }
       
        

        .system-user-info{
             text-align: center; 
             line-height: 2;
        }

        

        .system-user-info tr{
            border-bottom: 1px solid  rgb(119, 94, 238) ;
            
            
       }

       .system-user-info td{
            padding-top: 8px;
            padding-bottom: 8px;
       }

        .first-tr {
            background: rgb(119, 94, 238);
            color: white;
            
        }

        input[type ="search"]{
            width: 300px;
            line-height: 2;
        }

        .system-user-info input[type="button"]{
           
            border: 1px solid rgb(119, 94, 238);
            border-radius: 5px ;
            color: rgb(47, 16, 201);
            padding: 3px;
        }

        
        .system-user-page{
            
            margin: 0 auto;
            
        }
        
        .system-user-searchBar button[type=submit]{
           
            background-color: rgb(119, 94, 238); 
			color: white;
			width: 50px;
			border-radius: 8px;
        }
        
        .pageButtons{
        
        	background-color: #fff;
			border: 1px solid black;
			width: 30px;
			height: 40px;
        
        }
    </style>
</head>
<body>
    <header>
		<jsp:include page="../../common/corpMngHeader.jsp"/>
    </header>
	<div style="overflow:hidden; display: flex;">
    <aside>
		<jsp:include page="../../common/corpMngNavi.jsp"/>
    </aside>
    <section>
    	<div class="system-user-list">
        <div class="system-user-head">????????????</div>
        <br>
        <div class="system-user-searchBar" >
        	<form action="${ pageContext.servletContext.contextPath }/admin/userManage/list" id="userSearchForm" method="GET">
        	<label>????????? : </label>
        	<input type="date" id="searchWriteDateStart" name="searchWriteDateStart" value=<c:if test="${ search.searchWriteDateStart ne null }">"${ search.searchWriteDateStart }"</c:if>> ~
            <input type="date" id="searchWriteDateEnd" name="searchWriteDateEnd" value=<c:if test="${ search.searchWriteDateEnd ne null }">"${ search.searchWriteDateEnd }"</c:if>>
            <select name="largeSearchCondition" style="margin-left:470px;">
                <option value="all" <c:if test="${ search.largeSearchCondition eq 'all' }">selected</c:if>>??????</option>
                <option value="active" <c:if test="${ search.largeSearchCondition eq 'active' }">selected</c:if>>????????????</option>
                <option value="leave" <c:if test="${ search.largeSearchCondition eq 'leave' }">selected</c:if>>????????????</option>
            </select>
            <select name="smallSearchCondition">
                <option value="all" <c:if test="${ search.smallSearchCondition eq 'all' }">selected</c:if>>??????</option>
                <option value="id" <c:if test="${ search.smallSearchCondition eq 'id' }">selected</c:if>>?????????</option>
                <option value="name" <c:if test="${ search.smallSearchCondition eq 'name' }">selected</c:if>>??????</option>
                <option value="address" <c:if test="${ search.smallSearchCondition eq 'address' }">selected</c:if>>??????</option>
                <option value="contacts" <c:if test="${ search.smallSearchCondition eq 'contacts' }">selected</c:if>>????????????</option>
            </select>
            <input type="search" name="searchValue" value="${ search.searchValue }">
            <button type="submit" id="searchButton">??????</button>
            </form>
        </div>
    
        <br>
    
        <div class="system-user-info" >
            <table>
                <tr class="first-tr">
                    <th width=80px;>????????????</th>
                    <th width=160px;>?????????</th>
                    <th width=80px;>??????</th>
                    <th width=160px;>????????????</th>
                    <th width=300px;>??????</th>
                    <th width=160px;>????????????</th>
                    <th width=200px;>?????????</th>
                    <th width=160px;>?????????</th>
                    <th width=80px;>????????????</th>
                    <th width=200px;>??????</th>
                 </tr>
                 <c:forEach items="${ userList }" var="user">
                 <tr>
                     <td><c:out value="${ user.no }"/></td>
                     <td><c:out value="${ user.id }"/></td>
                     <td><c:out value="${ user.name }"/></td>
                     <td><c:out value="${ user.birth }"/></td>
                     <td><c:out value="${ user.address }"/></td>
                     <td><c:out value="${ user.contacts }"/></td>
                     <td><c:out value="${ user.email }"/></td>
                     <td><c:out value="${ user.registDate }"/></td>
                     <td><c:if test="${ user.leaveChk eq 'N' }">?????????</c:if><c:if test="${ user.leaveChk eq 'Y' }">??????</c:if></td>
                     <td><input type="button" class="purchaseListButton" value="????????????" style="margin-right: 5px;"></td>
                 </tr>
                 </c:forEach>
            </table>
        </div>
        </div>

        <br>

        <div align="center">
            <table>
                <tr class="system-user-page">
                    <c:forEach var="page" begin="${ search.pageInfo.startPage }" end="${ search.pageInfo.endPage }" step="1">
                    	<c:if test="${search.pageInfo.pageNo eq page }">
                    	<td>
							<button class="pageButtons" disabled>
								<c:out value="${ page }" />
							</button>
						</td>
						</c:if>
						<c:if test="${search.pageInfo.pageNo ne page }">
						<td>
							<button class="pageButtons" onclick="pageButtonAction(this.innerText);">
								<c:out value="${ page }" />
							</button>
						</td>
						</c:if>
                    </c:forEach>
                </tr>
            </table>
        </div>
    </section>
    </div>
    <footer>
		<jsp:include page="../../common/footer.jsp"/>
	</footer>
<script>
var link = "${ pageContext.servletContext.contextPath }/admin/userManage/list"

function pageButtonAction(text){
	location.href = link + "?currentPage=" + text + "&searchWriteDateStart="
	+ document.getElementsByName("searchWriteDateStart")[0].value
	+ "&searchWriteDateEnd=" + document.getElementsByName("searchWriteDateEnd")[0].value
	+ "&largeSearchCondition=" + document.getElementsByName("largeSearchCondition")[0].value
	+ "&smallSearchCondition=" + document.getElementsByName("smallSearchCondition")[0].value
	+ "&searchValue=" + document.getElementsByName("searchValue")[0].value;
};

$(".purchaseListButton").click(function(){
	
	var no = this.parentNode.parentNode.children[0].innerText;
	
	location.href = "${ pageContext.servletContext.contextPath }/admin/userManage/purchase/list?no=" + no; 
			
});

$("#searchButton").click(function(){
	var searchWriteDateStart = $("#searchWriteDateStart").val();
	var searchWriteDateEnd = $("#searchWriteDateEnd").val();
	if(searchWriteDateStart > searchWriteDateEnd){
		
		alert("???????????? ??????????????? ??? ??? ????????????.");
		return false;
		
	}
	
	$("#userSearchForm").submit();
	
});
</script>
</body>
</html>