package Servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import Model.UserModel;



public class LoginServlet extends HttpServlet  {

	private static final long serialVersionUID = 1L;


	public void init()
	{
		user = new ArrayList<UserModel>();
	}
	
	private ArrayList<UserModel> user;
	 
	 
	public void doPost(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException 
     {
		 	response.setContentType("text/html");     
			PrintWriter writer = response.getWriter();  

			RequestDispatcher dispacher = request.getRequestDispatcher("/html/index_top.html");
			dispacher.include(request, response);
			
			/* start checks */
			boolean userCanJoin = true;
			String username = request.getParameter("username");
			
			if(user.size() >= 4) {
				writer.println("<h3 style='color:#D9534F;'><i class='fa fa-info-circle' aria-hidden='true'></i> Es sind bereits genug Spieler registriert</h3>");
				userCanJoin = false;
			}
			else if (username == null) {
				writer.println("<h3 style='color:#D9534F;'><i class='fa fa-info-circle' aria-hidden='true'></i> Kein Username gefunden</h3>");
				userCanJoin = false;
			}
			else if (username.equals("")) {
				writer.println("<h3 style='color:#D9534F;'><i class='fa fa-info-circle' aria-hidden='true'></i> Username darf nicht leer sein</h3>");
				userCanJoin = false;
			}
			
			dispacher = request.getRequestDispatcher("/html/index_center.html");
			dispacher.include(request, response);
			
	
			if(userCanJoin) {
				UserModel newUser = new UserModel(username);
				user.add(newUser);
			}
			
			
			writer.println("<table class='score' id='playerTable'>");
			writer.println("<tr>");
			writer.println("<th>Player</th>");
			writer.println("<th>Score</th>");
			writer.println("</tr>");
			for(int i = 0; i < user.size(); i++)
			{
				UserModel currUser = user.get(i);
				
				writer.println("<tr>");
				writer.println("<td>" + currUser.getUsername() + "</td>");
				writer.println("<td>" + currUser.getPoints() + "</td>");
				writer.println("</tr>");
			}
			writer.println("</table>");
				

			dispacher = request.getRequestDispatcher("/html/index_bottom.html");      
			dispacher.include(request, response);          
     }
     
	
}
