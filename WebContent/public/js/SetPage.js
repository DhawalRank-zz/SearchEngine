/**
 * Set Page in Pagination
 */
	var currPage = 0;
	var searchResultsDiv = document.getElementById('searchResultsDiv');
	var inputElementSearchQuery = document.getElementById("searchQuery");
	function init(){
		var firstElementId = document.getElementById("searchResultA0");
		if(firstElementId){
			firstElementId.setAttribute("class", "pagination-link is-current");
			setPage(0);
		}
	}
	function setPage(destPage){
		var destAId = document.getElementById("searchResultA" + destPage);
		var currAId = document.getElementById("searchResultA" + currPage);
		if(destAId){
			currAId.setAttribute("class", "pagination-link");
			destAId.setAttribute("class", "pagination-link is-current");
			searchResultsDiv.innerHTML = "";
			var startIndex = (maxHitsPerPage * destPage);
			var endIndex = ( links.length >= 10) ? (startIndex + (maxHitsPerPage - 1)) :  ( links.length - 1 );
			for (var i = startIndex; i <= endIndex; i++){
				if(links[i] !== undefined){
					var title = links[i].replace(/WebPages\//, "").replace(/\.[^/.]+$/, "");
					searchResultsDiv.innerHTML  = searchResultsDiv.innerHTML + 
						"<a href='" + links[i] + "'style='color: blue;'>" + title + "</a><br/>" + contents[i] + "<br/><br/><br/>";
						currPage = destPage;
				}
			}
		}	
	}