package searchengine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Search
 */
@WebServlet("/Search")
public class Search extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Search() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		long beforeUsedMem = Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
		String searchQuery = request.getParameter("searchQuery");
	    long startTime = System.currentTimeMillis();
		List<String> searchResults = new ArrayList<String>();
		List<String> searchResultsContents = new ArrayList<String>();

		try {
			IndexandSearch.run(searchQuery);
			searchResults = IndexandSearch.getSearchResults();
			searchResultsContents = IndexandSearch.getSearchResultsContents();
			request.setAttribute("searchResults", searchResults);
			request.setAttribute("searchResultsContents", searchResultsContents);

		} catch (Exception e) {
			e.printStackTrace();
		}
		long stopTime = System.currentTimeMillis();
	    double elapsedTime = (double) (stopTime - startTime) / 1000;
		request.setAttribute("totalTime", elapsedTime);
		long afterUsedMem = Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
		float actualMemUsed = (float) (afterUsedMem-beforeUsedMem) / (1024 * 1024);
		request.setAttribute("actualMemUsed", actualMemUsed);
		request.getRequestDispatcher("searchResult.jsp").forward(request, response);
	}
}
