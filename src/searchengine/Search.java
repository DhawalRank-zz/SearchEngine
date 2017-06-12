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
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		long beforeUsedMem = Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
		String searchQuery = request.getParameter("searchQuery");
	    long startTime = System.currentTimeMillis();
		List<String> searchResults = new ArrayList<String>();
		try {
			searchResults = IndexandSearch.run(searchQuery);
			request.setAttribute("searchResults", searchResults);

		} catch (Exception e) {
			// TODO Auto-generated catch block
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
