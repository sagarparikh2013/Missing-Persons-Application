package S3_programs;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ComplaintDao;
import vo.ComplaintVo;

/**
 * Servlet implementation class showComplaintDetails
 */
public class showComplaintDetails extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public showComplaintDetails() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String name = request.getParameter("name");
		String contact = request.getParameter("contact");
		System.out.println("name:"+name+" contact: "+contact);
		
		ComplaintVo lookUpCv = new ComplaintVo();
		lookUpCv.setName(name);
		lookUpCv.setContact(contact);
		
		ComplaintDao cd = new ComplaintDao();
		
		String con = cd.lookUpPeople(lookUpCv);
		System.out.println("con:"+con);
		if(con!=null) {
			System.out.println("search.jsp?display=display&con="+con+"&name="+name);
			response.sendRedirect("search.jsp?display=display&con="+con+"&name="+name);
		}
		else {
			response.sendRedirect("search.jsp?display=nodata");
		}
			
		
		
	}

}
