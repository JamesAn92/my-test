import javax.servlet.http.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import java.io.*;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<html>\n" + "<head><title>" + "Sign up" + "</title></head>\n" + "<body>\n"
				+ "<h1 align=\"center\">" + "Sign up" + "</h1>\n" + "<form action=\"register\" method=\"POST\">\n"
				+ "Username: <input type=\"text\" required name=\"user_id\">\n" + "<br />\n"
				+ "Password: <input type=\"password\" required name=\"password\" />\n" + "<br />\n"
				+ "<input type=\"submit\" value=\"Sign up\" name=\"signup\" id=\"signup\"/>\n" + "<a href=\"login\">Already got an account?</a>" + "</form>\n"
				+ "</form>\n" + "</body>\n</html\n");
	}
		
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		String title = "Logged in as: ";
		String username = request.getParameter("user_id");
		String password = request.getParameter("password");

		try {
			//Connects to DB
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/youtube", "root", "oracle1");
			PreparedStatement create = con.prepareStatement("CREATE TABLE if not exists users(id int NOT NULL AUTO_INCREMENT, uname varchar(20), upass varchar(20), Primary key(id))");
			create.executeUpdate();
			
			PreparedStatement check = con.prepareStatement("SELECT * FROM users WHERE uname ='" + username + "'");
			ResultSet rs = check.executeQuery();
			if (rs.next()) {
				//Checking if ID exists and send Alert
				PrintWriter out = response.getWriter();
				response.setContentType("text/html");  
				out.println("<script type=\"text/javascript\">");  
				out.println("alert('That ID Already Exists!');");  
				out.println("window.location.href='register';");
				out.println("</script>");
			} else {
				//Insert user data into database
				PreparedStatement insert = con.prepareStatement("INSERT INTO users(uname, upass) values(?, ?) ");
				insert.setString(1, username);
				insert.setString(2, password);
				insert.executeUpdate();
			}	
		} catch (Exception e) {
			System.out.println(e);
			
		} finally {
			//Alerts Success registration
			PrintWriter out = response.getWriter();
			response.setContentType("text/html");  
			out.println("<script type=\"text/javascript\">");  
			out.println("alert('Successfully created account!');");  
			out.println("window.location.href='login';");
			out.println("</script>");	
		}		
		

	}
}
