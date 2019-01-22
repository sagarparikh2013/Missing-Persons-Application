package S3_programs;

import java.io.File;

import java.io.IOException;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;


import dao.ComplaintDao;
import vo.ComplaintVo;



public class uploadObject extends HttpServlet {
	private static final long serialVersionUID = 1L;
	// location to store file uploaded
    private static final String UPLOAD_DIRECTORY = "Complaints_Data";
 
    // upload settings
    private static final int MEMORY_THRESHOLD   = 1024 * 1024 * 3;  // 3MB
    private static final int MAX_FILE_SIZE      = 1024 * 1024 * 40; // 40MB
    private static final int MAX_REQUEST_SIZE   = 1024 * 1024 * 50; // 50MB
    
	private static String image_path ="";
    //public static String complaint_num = "1";

    public uploadObject() {
        super();
        // TODO Auto-generated constructor stub
    }

	@SuppressWarnings("deprecation")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String uploadPath = getServletContext().getRealPath("")+UPLOAD_DIRECTORY;
		String server_path = getServletContext().getRealPath("");
		System.out.println(uploadPath);
		response.setContentType("text/html");
		PrintWriter out= response.getWriter();
		String[] details = new String[3];
		
		out.println("<html>"
				+ "<link rel=\"stylesheet\" type=\"text/css\" href=\"1.css\">"
				+ "<body>"
				+ "");
			
			
		//________________________________Saving Uploaded Image____________________________________________________
			
			 // checks if the request actually contains upload file
	        if (!ServletFileUpload.isMultipartContent(request)) {
	            // if not, we stop here
	            
	            out.println("Error: Form must has enctype=multipart/form-data.");
	            out.flush();
	            return;
	        }
	 
	        // configures upload settings
	        DiskFileItemFactory factory = new DiskFileItemFactory();
	        // sets memory threshold - beyond which files are stored in disk
	        factory.setSizeThreshold(MEMORY_THRESHOLD);
	        // sets temporary location to store files
	        factory.setRepository(new File(System.getProperty("java.io.tmpdir")));
	 
	        ServletFileUpload upload = new ServletFileUpload(factory);
	         
	        // sets maximum size of upload file
	        upload.setFileSizeMax(MAX_FILE_SIZE);
	         
	        // sets maximum size of request (include file + form data)
	        upload.setSizeMax(MAX_REQUEST_SIZE);
	 
	        // creates the directory if it does not exist
	        
	       // File uploadDir = new File(uploadPath+"/"+complaint_num);
	        File uploadDir = new File(uploadPath);
	        if (!uploadDir.exists()) {
	            uploadDir.mkdirs();
	        }
	 
	        try {
	            // parses the request's content to extract file data
	            @SuppressWarnings("unchecked")
	            List<FileItem> formItems = upload.parseRequest(request);
	            int i=0;
	            if (formItems != null && formItems.size() > 0) {
	                // iterates over form's fields
	                for (FileItem item : formItems) {
	                    
	                	if(item.isFormField()){
	                		details[i] = item.getString();
	                		i++;
	                	}
	                    if (!item.isFormField()) {
	                    	// processes only fields that are not form fields
	                        String fileName = details[2]+".jpg";
	                        image_path = fileName;

	                        String filePath = uploadPath +"/"+ image_path;
	                        
	                        File storeFile = new File(filePath);
	                		
	                        // saves the file on disk
	                        item.write(storeFile);
	                    }
	                }
	            }
	        } catch (Exception ex) {
	          ex.printStackTrace();
	        }
	      
	      //________________________________Saving Uploaded Image____________________________________________________
	        ComplaintDao cd = new ComplaintDao();
	        cd.UPLOAD_DIRECTORY = UPLOAD_DIRECTORY;
	        cd.uploadPath= uploadPath;

	        //cd.storeDetails(details);
    		//out.println("<input type=\"text\" value=\"Details stored in disk\" />\r\n");

	        cd.uploadObjectToS3(image_path);

    		image_path = "Complaints_Data/"+image_path;

	        ComplaintVo cv = new ComplaintVo();
	        cv.setName(details[0]);
	        cv.setAddress(details[1]);
	        cv.setContact(details[2]);
	        cv.setS3key(image_path);
	        
	        cd.insertComplaint(cv);
	        
	        out.println("<form id=\"msform\">\r\n" + 
	        		"	        <fieldset>\r\n" + 
	        		"	            <h2 class=\"fs-title\">Stored Details</h2>\r\n");
    		out.println("Name:"+"<input type=\"text\" disabled value="+details[0]+" />\r\n");
    		out.println("Address:"+"<input type=\"text\" disabled value="+details[1]+" />\r\n");
    		out.println("Contact:"+"<input type=\"text\" disabled value="+details[2]+" />\r\n");
	        out.println("<a href='index.jsp'>Go back?</a>");
	        out.println("</fieldset></form>");
            out.println("</body></html>");
            server_path = server_path + image_path;
    		cd.deleteImageFromServer(server_path);
	       
	}
}
