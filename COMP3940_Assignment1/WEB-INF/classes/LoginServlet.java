import javax.servlet.http.*;
import javax.servlet.*;
import java.io.*;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<html>\n" + "<head><title>" + "Login" + "</title></head>\n" + "<body>\n"
				+ "<h1 align=\"center\">" + "Login" + "</h1>\n" + "<form action=\"login\" method=\"POST\">\n"
				+ "Username: <input type=\"text\" required name=\"user_id\">\n" + "<br />\n"
				+ "Password: <input type=\"password\" required name=\"password\" />\n" + "<br />\n"
				+ "<input type=\"submit\" value=\"Sign in\" name=\"signup\" id=\"signup\"/>\n" + "<a href=\"register\">Create an account</a>" + "</form>\n"
				+ "</form>\n" + "</body>\n</html\n");		
	}
		
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html");
		String title = "Logged in as: ";
		String username = request.getParameter("user_id");
		String password = request.getParameter("password");

		try {		
			//Connection to DB
			Class.forName("com.mysql.cj.jdbc.Driver");	
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/youtube", "root", "oracle1");
			PreparedStatement check = con.prepareStatement("SELECT * FROM users WHERE uname ='" + username + "' and upass ='" + password + "'" );
			ResultSet rs = check.executeQuery();
			
			if (rs.next()) {
				//If login credential is found then set session user_id and redirect to main
				HttpSession session = request.getSession(true);
				session.setAttribute("USER_ID", username);
				response.setStatus(302);
				response.sendRedirect("main");	
			} else {
				//Alerts if login credential is wrong
				PrintWriter out = response.getWriter();
				response.setContentType("text/html");  
				out.println("<script type=\"text/javascript\">");  
				out.println("alert('Wrong Credentials!');");  
				out.println("window.location.href='login';");
				out.println("</script>");	
			} 
		} catch (Exception e) {
			System.out.println(e);
		} 
	}
}
