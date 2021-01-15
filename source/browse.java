
/* A servlet to display the contents of the MySQL movieDB database */

import java.io.*;
import java.net.*;
import java.sql.*;
import java.text.*;
import java.util.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.sql.DataSource;

@WebServlet("/browse")
public class browse extends HttpServlet {
	private Connection dbcon;
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
	        throws IOException, ServletException
	    {
		PrintWriter out = response.getWriter();

	        try
	           {
	        	  Context initCtx = new InitialContext();
		          if (initCtx == null) out.println ("initCtx is NULL");
				   
			      Context envCtx = (Context) initCtx.lookup("java:comp/env");
		          if (envCtx == null) out.println ("envCtx is NULL");
					
			       // Look up our data source
			      DataSource ds = (DataSource) envCtx.lookup("jdbc/FabFlix");
			      if (ds == null)
					 out.println ("ds is null.");
			      
			      Connection dbcon = ds.getConnection();
				  if (dbcon == null)
			         out.println ("dbcon is null.");
	              
				  
				  String letter = request.getParameter("letter");
				  String genre = request.getParameter("genre");

				  
				  if(letter != null || genre != null){
					  request.setAttribute("letter", letter);
					  request.setAttribute("genre", genre);
			          request.getRequestDispatcher("displayMovieB").forward(request, response);
				  }
					  
 
	              Statement statement = dbcon.createStatement(); 
	              String query = "select name from genres order by name;"; 
	              ResultSet rs = statement.executeQuery(query);
	    
	              ArrayList<String> genre_list = new ArrayList<String>();
	              while(rs.next()){
	            	  String s = "<input type=\"radio\" id=\"genre_label\" name=\"genre\" value=\""+rs.getString("name")
	            			  +"\">&nbsp;&nbsp;<label for=\"genre_label\">"+rs.getString("name")+"</label>";
 
	            	  genre_list.add(s);  
	              }
	              
	              

	             request.setAttribute("genre_list", genre_list);
	             request.getRequestDispatcher("browse.jsp").forward(request, response);

 
	              rs.close();
	              statement.close();
	              dbcon.close();
	            }
	        catch (SQLException ex) {
	              while (ex != null) {
	                    out.println ("SQL Exception:  " + ex.getMessage ());
	                    ex = ex.getNextException ();
	                }  // end while
	            }  // end catch SQLException

	        catch(java.lang.Exception ex)
	            {
	                out.println("<HTML>" +
	                            "<HEAD><TITLE>" +
	                            "MovieDB: Error" +
	                            "</TITLE></HEAD>\n<BODY>" +
	                            "<P>SQL error in doGet: " +
	                            ex.getMessage() + "</P></BODY></HTML>");
	                return;
	            }
	         out.close();
	    }

	    public void doPost(HttpServletRequest request, HttpServletResponse response)
	        throws IOException, ServletException
	    {
		doGet(request, response);
	    }
}
