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
			console.log(2);
			currAId.setAttribute("class", "pagination-link");
			destAId.setAttribute("class", "pagination-link is-current");
			searchResultsDiv.innerHTML = "";
			var startIndex = (maxHitsPerPage * destPage);
			var endIndex = ( a.length >= 10) ? (startIndex + (maxHitsPerPage - 1)) :  ( a.length - 1 );
			for (var i = startIndex; i <= endIndex; i++){
				if(a[i] !== undefined){
					searchResultsDiv.innerHTML  = searchResultsDiv.innerHTML + 
						"<a href='" + a[i] + "'style='color: blue;'>" + a[i] + "</a><br/>";
						currPage = destPage;
				}
			}
		}	
	}