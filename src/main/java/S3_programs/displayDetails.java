package S3_programs;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;

import dao.ComplaintDao;
import vo.ComplaintVo;

/**
 * 
 * Servlet implementation class displayDetails
 */
public class displayDetails extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public displayDetails() {
        super();
        // TODO Auto-generated constructor stub
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    		String con = request.getParameter("con");
    		System.out.println("Inside displayDetails Servlet!");
    		PrintWriter out = response.getWriter();
    		String server_path = getServletContext().getRealPath("");
    		ComplaintDao complaintDao = new ComplaintDao();
    		System.out.println("Before getting Complaint Details!");
    		ComplaintVo cv1 = complaintDao.getComplaintDetails(con);
    		System.out.println("After getting Complaint Details!");

    		String path = null;
			try {
				System.out.println("before attempting path");
				path = complaintDao.downloadObjectFromS3(cv1.getS3key(),cv1.getContact(),server_path);
				
				
			} catch (Exception e) {
				e.printStackTrace();
			} 
    		
    		
    	
			if(path==null) {
					out.println("<html>"
				+ "<link rel=\"stylesheet\" type=\"text/css\" href=\"1.css\">"
				+ "<body>"
				+ "<div align='center'>-------------Inside doPost of Display Details Page!----------<br>"
				+ "<h2>Error in getting details!</h2><br>"
				+"<a href='search.jsp'>Please try again</a>"
			
				+ "</div>"
				+ "</body>" + 
				"</html>");
			}
			else {
    		HttpSession session = request.getSession();
    		session.setAttribute("path", path);
    		session.setAttribute("cv1", cv1);
    		response.sendRedirect("display.jsp");
			}
		}

}
