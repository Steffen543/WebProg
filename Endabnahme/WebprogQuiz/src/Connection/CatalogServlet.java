package Connection;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import de.fhwgt.quiz.application.Catalog;
import de.fhwgt.quiz.application.Quiz;
import de.fhwgt.quiz.loader.FilesystemLoader;
import de.fhwgt.quiz.loader.LoaderException;




@WebServlet(urlPatterns = "/CatalogServlet", loadOnStartup = 1)
public class CatalogServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	 Quiz quiz;
    
	public void init(ServletConfig servletConf)
	{
		try {
			super.init(servletConf);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		FilesystemLoader fsl = new FilesystemLoader("catalogs");
		quiz = Quiz.getInstance();
		quiz.initCatalogLoader(fsl);
		
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		
		Map<String, Catalog> catalogs = new HashMap<>();
		try {
			catalogs = quiz.getCatalogList();
		} catch (LoaderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		
		String xmlString = "<catalogs>";
		Iterator<Map.Entry<String, Catalog>> it = catalogs.entrySet().iterator();
        while(it.hasNext())
        {
        	Map.Entry<String, Catalog> pair = it.next();
        	String tmpString = "<catalog>" + "<name>" + pair.getValue().getName() + "</name>" + "</catalog>";
        	xmlString += tmpString;
        }
        xmlString += "</catalogs>";
		System.out.println(xmlString);
        
		response.setContentType("text/xml");
	    response.getWriter().write(xmlString);
	}
}
