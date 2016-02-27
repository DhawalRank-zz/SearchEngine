package searchengine;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class DisplaySearchResult
 */
@WebServlet("/DisplaySearchResult")
public class DisplaySearchResult extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
	private static final String PARAM_SEARCH_QUERY = "query";
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
 
        out.println("<!DOCTYPE html>");
        out.println("<html>");
 
        out.println("<head>");
        out.println("<title>569 DisplaySearchResult</title>");
        out.println("</head>");
        out.println("<body>");
        try
        {
            if (!validateRequest (request))
            {
                out.println ("Invalid request");
                return;
            }
 
            String searchQuery = request.getParameter(PARAM_SEARCH_QUERY);
 
           // String directory = this.getServletContext().getRealPath("WEB-INF/classes/indexDir/uwindsor.ca/");
            IndexandSearch srchr = new IndexandSearch();
            String l = srchr.searchIndex(searchQuery);
            l = l.replaceAll("(\r\n|\n)", "<br />");
 
            out.println("<div>" + l + "</div>");
 
        } catch(Exception e){
            e.printStackTrace();
        }finally {
            out.println("</body>");
            out.println("</html>");
            out.close();
        }
 
    }
 
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */


 
    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override

 
    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */

    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
 
    public boolean validateRequest (HttpServletRequest req)
    {
        if (req.getParameter(PARAM_SEARCH_QUERY) == null)
        {
            return false;
        }
        return true;
    }
    public DisplaySearchResult() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
