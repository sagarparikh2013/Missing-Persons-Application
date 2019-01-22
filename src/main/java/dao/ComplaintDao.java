package dao;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.AccessControlList;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.transfer.Download;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.Upload;

import vo.ComplaintVo;

public class ComplaintDao {
	
    public static String UPLOAD_DIRECTORY = "";
    public static String uploadPath="";
    public static String complaint_num = "";
    private static String profileName = "YOUR-PROFILE-NAME";
    private static String dbName = "DB-NAME";
    //CREATE TABLE `sagardb`.`complaints` ( `name` VARCHAR(100), `address` VARCHAR(100), `contact` VARCHAR(100), `S3key` VARCHAR(100) );
    private static String connectionString = "jdbc:mysql://YOUR-RDS-CONNECTION-STRING:3306/"+dbName;
    private static String bucketName = "missing-persons";

    private static String accessKey="YOUR-ACCESS-KEY";
	private static String secretKey="YOUR-SECRET-KEY";
    private static Regions region = Regions.YOUR-REGION;

    


	public void insertComplaint(ComplaintVo cv){
		try{
			  
			Class.forName("com.mysql.jdbc.Driver");  
			Connection con=DriverManager.getConnection(connectionString,"YOUR-DB-USERNAME","YOUR-DB-PASSWORD");  
			Statement stmt=con.createStatement();  

			stmt.executeUpdate("INSERT into complaints(name,address,contact,S3key) VALUES('"+cv.getName()+"','"+cv.getAddress()+"','"+cv.getContact()+"','"+cv.getS3key()+"')");
			System.out.println("inserted into DB!");
			con.close();  
			}
		catch(Exception e){
			System.out.println(e);	}  
	}
	
	/*public void storeDetails(String[] details) throws IOException {
		String tf_name = uploadPath + File.separator + complaint_num +File.separator+ "details.txt";
        FileWriter text_file = new FileWriter(tf_name);
        text_file.write("Name: "+details[0]+"\r\nAddress: "+details[1]+"\r\nContact: "+details[2]);
        text_file.close();
        System.out.println("Details written onto disk!");
	}*/
	
	public void uploadObjectToS3(String image_name) {
		
		try {
			AWSCredentials awsCredentials=new BasicAWSCredentials(accessKey, secretKey);
			
			AmazonS3Client amazonS3Client = new AmazonS3Client(awsCredentials);
			amazonS3Client.setRegion(Region.getRegion(region));
			
			TransferManager manager = new TransferManager(amazonS3Client);

			File upload_image = new File(uploadPath+"/"+image_name);
			System.out.println(upload_image.getPath());
			String s3key = "Complaints_Data/"+image_name;
			Upload upload = manager.upload(bucketName, s3key, upload_image);
			upload.waitForCompletion();
			System.out.println("Objects uploaded to S3!");
		} catch (Exception e) {
 			e.printStackTrace();
		}
	}
	
	public String lookUpPeople(ComplaintVo lookUpCv) {
		String contact = null;
		try{  
			Class.forName("com.mysql.jdbc.Driver");  
			Connection con=DriverManager.getConnection(connectionString,"YOUR-DB-USERNAME","YOUR-DB-PASSWORD");  
			Statement stmt=con.createStatement();  

			ResultSet rs = stmt.executeQuery("SELECT * from complaints where name='"+lookUpCv.getName()+"' and contact='"+lookUpCv.getContact()+"'");
			while(rs.next()) {
				contact = rs.getString("contact");
			}
			con.close();
		System.out.println(contact+"  found!!");
			}
		catch(Exception e){
			System.out.println(e);	
		}
		return contact;
	}

	public ComplaintVo getComplaintDetails(String contact) {
		ComplaintVo complaintVo = new ComplaintVo();
		try{  
			Class.forName("com.mysql.jdbc.Driver");  
			Connection con=DriverManager.getConnection(connectionString,"YOUR-DB-USERNAME","YOUR-DB-PASSWORD");  
			Statement stmt=con.createStatement();  
			String query = "SELECT * from complaints where contact='"+contact+"'";
			System.out.println(query);
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()) {
				complaintVo.setName(rs.getString("name"));
				complaintVo.setAddress(rs.getString("address"));
				complaintVo.setContact(rs.getString("contact"));
				complaintVo.setS3key(rs.getString("S3key"));
				System.out.println("Getting Complaint Details...");
				System.out.println("Name : "+complaintVo.getName());
				System.out.println("Address : "+complaintVo.getAddress());
				System.out.println("Contact : "+complaintVo.getContact());
				System.out.println("S3Key : "+complaintVo.getS3key());
				
			}
			con.close();
		
			}
		catch(Exception e){
			System.out.println(e);	
		}
		return complaintVo;
		
	}

	
	public String downloadObjectFromS3(String s3key, String contact, String server_path) throws Exception {
		AWSCredentials awsCredentials=new BasicAWSCredentials(accessKey, secretKey);
		
		AmazonS3Client amazonS3Client = new AmazonS3Client(awsCredentials);
		amazonS3Client.setRegion(Region.getRegion(region));
		
		TransferManager manager = new TransferManager(amazonS3Client);
		
		File downloaded_folder = new File(server_path+"Downloaded_Images");
		if(!downloaded_folder.exists()) {
		downloaded_folder.mkdir();
		}
		
		File downloaded_image = new File(server_path+"Downloaded_Images"+File.separator+contact+".jpg");
		
			Download download = manager.download(bucketName, s3key, downloaded_image);
			download.waitForCompletion();
		    System.out.println("Object (Image) dowloaded from S3!:"+s3key);
			String a = "Downloaded_Images"+File.separator+contact+".jpg";
		    return a;
        
	}

	public void deleteImageFromServer(String server_path) {
		File image = new File(server_path);
		image.delete();
	}
	
	

}
