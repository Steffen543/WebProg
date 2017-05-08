package Task4;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class LogIn extends HttpServlet{

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        String name = request.getParameter("name");
        
        if(name.isEmpty())
        {
        	out.println("Username needed");
        	RequestDispatcher rs = request.getRequestDispatcher("..."); //.. durch unsere html Seite ersetzen
        	rs.include(request, response);
        }
        if(name.matches("[^A-Za-z0-9_]+"))
        {
        	out.println("Username not allowed");
        	RequestDispatcher rs = request.getRequestDispatcher("..."); //.. durch unsere html Seite ersetzen
        	rs.include(request, response);
        }
        else
        {
        	RequestDispatcher rs = request.getRequestDispatcher("Welcome " + name);
        	rs.forward(request, response);
        }
    }  
}
