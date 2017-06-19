<jsp:include page="baseHeader.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
	<%@ page import="java.util.List" %>
	<div style="padding-left: 0.5%; padding-top: 0.7%">
	<form method="get" id="searchResultForm" action="Search">
		<div class="field has-addons" style="width: 57%;">
		  <p class="control is-expanded">
		    <input class="input" type="text" id="searchQuery" name="searchQuery" value="<%= request.getParameter("searchQuery") %>" placeholder="What are you looking for Homey!">
			  <p class="control">
				<a class="button" onclick="document.getElementById('searchResultForm').submit()">
   				<span class="icon">
					<i class="fa fa-search"></i>
				</span>
 				</a>
	  		  </p>
		  </p>
		</div>
	</form>
	<br/>
	<% 
		int maxHitsPerPage = 10; 
		List<String> searchResults = (List<String>)request.getAttribute("searchResults");
		List<String> searchResultsContents = (List<String>)request.getAttribute("searchResultsContents");

	%>
	<p class="title is-6">About <%= searchResults.size() %> results (<%= request.getAttribute("totalTime") %> seconds, <%= request.getAttribute("actualMemUsed") %>mb used.)</p>
	<div id="searchResultsDiv" style="width: 70%">
	
	</div>
	<br />
		<nav class="pagination">
	<ul class="pagination-list"  style="margin: auto;">
	<% 
		if(searchResults.size() > 0){
			int totalPages = ((int) Math.ceil(searchResults.size() / maxHitsPerPage)) + 1;
			//int totalPages = (totalPagesCalc > 0) ? totalPagesCalc : 1; 
			
	%>
		<li>
	      <a class="pagination-link" id="searchResultAFirst" onclick="setPage(0)">First </a>
	    </li>	
	<%
			for(int i = 0; i < totalPages; i++){
				
	%>
		<li>
	      <a class="pagination-link" id="searchResultA<%= i %>" onclick="setPage(<%= i %>)"><%= i + 1 %></a>
	    </li>	
	<%
			}
	%>
		<li>
	   		<a class="pagination-link" id="searchResultALast" onclick="setPage(<%= totalPages - 1 %>)">Last </a>
		</li>

	<%
		}
	%>
	
	</ul>
	</div>
	<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/SetPage.js"></script>
	<script type="text/javascript">
		var links = [<% for (int i = 0; i < searchResults.size(); i++) { %>"<%= searchResults.get(i) %>"<%= i + 1 < searchResults.size() ? ",":"" %><% } %>];
		var contents = [<% for (int i = 0; i < searchResultsContents.size(); i++) { %>"<%= searchResultsContents.get(i).toString() %>"<%= i + 1 < searchResultsContents.size() ? ",":"" %><% } %>];
		var maxHitsPerPage = <%= maxHitsPerPage %>;
		init();
	</script> 
<jsp:include page="baseFooter.jsp" />