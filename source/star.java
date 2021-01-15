
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

@WebServlet("/star")
public class star extends HttpServlet {
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
				  
				  
				 
	              // Declare our statement
	              String star_id = request.getParameter("star_id");
	              
	       

	              
	              Statement m_statement = dbcon.createStatement();
	              String movie_query = "SELECT * FROM stars_in_movies s, movies m, stars t where s.movie_id = m.id AND s.star_id = t.id AND t.id = "+star_id+";";
	              ResultSet rs_movie = m_statement.executeQuery(movie_query);
	              
	              String movie = "";
	              while(rs_movie.next()){
	            	  movie += "<form method=\"get\" action= \"singleMovie\">"
	            	  		+ "<input type=\"hidden\" name=\"id\" value="+ rs_movie.getString("movie_id") +">"
	            	  		+ "<button type=\"submit\" class=\"btn btn-link\" role=\"link\" name=\"movie_title\">" 
	            	  		+ rs_movie.getString("title") + "</button></form>";
	            	  
	              }
	              
	              
	              
	              Statement statement = dbcon.createStatement();
	              String query = "select * from stars where id = "+star_id+ ";"; 
	              ResultSet rs = statement.executeQuery(query);
	           
	              
	 

	              
	              String item = "";
	              String info ="";
	              
	              while (rs.next())
	              {
	            	  item =  "<img src=" + rs.getString("photo_url") 
	            			  + " onerror=\"this.src='https://s3.amazonaws.com/images.seroundtable.com/t-google-404-1299071983.jpg'\""
	            			  + " class=\"img\" width=\"100\" height=\"150\">";
	            	  
	            	  info ="<table class=\"table table-striped\">" 
			            	  +"<tr><th>Name</th><td>"+rs.getString("first_name")+" "+ rs.getString("last_name")+"</td></tr>"
			            	  +"<tr><th>Date of Birth</th><td>"+rs.getString("dob")+"</td></tr>"
			            	  +"<tr><th>Star Id</th><td>"+rs.getString("id")+"</td></tr>"
			            	  +"<tr><th>Movies</th><td>"+movie+"</td></tr>"
			            	  +"</table>";
	              }
	       

	              request.setAttribute("item",item);	
	              request.setAttribute("info", info);
            	  request.getRequestDispatcher("singlepage.jsp").forward(request, response);
 
            	  
            	  
            	  
	              rs.close();
	              rs_movie.close();
	              m_statement.close();
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
