
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

@WebServlet("/singleMovie")
public class singleMovie extends HttpServlet {
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
	              String movie_id = request.getParameter("id");
	              
	              

	              Statement g_statement = dbcon.createStatement();
	              String genre_query = "SELECT * FROM genres_in_movies m, genres g where m.genre_id = g.id AND m.movie_id = "+movie_id+";";
	              ResultSet rs_genre = g_statement.executeQuery(genre_query);
	              
	              String genre = "";
	              while(rs_genre.next()){
	            	  genre +=  "<a href= browse?genre=" + rs_genre.getString("name") + "&letter=All>"+rs_genre.getString("name")+ "</a>&nbsp;&nbsp;&nbsp;";
	              }
	              
	              
	              Statement s_statement = dbcon.createStatement();
	              String star_query = "SELECT * FROM stars_in_movies s, movies m, stars t where s.movie_id = m.id AND s.star_id = t.id AND m.id = "+movie_id+";";
	              ResultSet rs_star = s_statement.executeQuery(star_query);
	              
	              String star = "";
	              while(rs_star.next()){
	            	  star += "<form method=\"get\" action= \"star\">"
	            	  		+ "<input type=\"hidden\" name=\"star_id\" value="+ rs_star.getString("star_id") +">"
	            	  		+ "<button type=\"submit\" class=\"btn btn-link\" role=\"link\" name=\"star_name\" value=\'"
	            	  		+ rs_star.getString("first_name") +" "+ rs_star.getString("last_name")+"'>" 
	            	  		+ rs_star.getString("first_name") +" "+ rs_star.getString("last_name")+"</button></form>";
	              }
	              
	              Statement statement = dbcon.createStatement();
	              String query = "select * from movies where id = "+movie_id+ ";"; 
	              ResultSet rs = statement.executeQuery(query);
	              
	              String item = "";
	              String info ="";
	              String m_title = "";
	              
	              while (rs.next())
	              {
	            	  
	            	  item =  "<img src=" + rs.getString("banner_url") 
	            			  + " onerror=\"this.src='https://s3.amazonaws.com/images.seroundtable.com/t-google-404-1299071983.jpg'\""
	            			  +" class=\"img\" width=\"100\" height=\"150\"/><br/><br/>"
	            			  + "<form method=\"post\" action=\"shoppingcart\" target=\"shoppingcart\">" 
	            			  +"<input type=\"hidden\" name=\"id\" value="+ rs.getString("id") +">"
	            			  + "<button  type=\"submit\" name=\"cart\" class=\"btn btn-default\">Add to Cart</button></form>";
	            	  
	            	  info ="<table class=\"table table-striped\">" 
			            	  +"<tr><th>Title</th><td>&nbsp;&nbsp;&nbsp;"+rs.getString("title")+"</td></tr>"
			            	  +"<tr><th>Year</th><td>&nbsp;&nbsp;&nbsp;"+rs.getString("year")+"</td></tr>"
			            	  +"<tr><th>Director</th><td>&nbsp;&nbsp;&nbsp;"+rs.getString("director")+"</td></tr>"
			            	  +"<tr><th>Movie Id</th><td>&nbsp;&nbsp;&nbsp;"+rs.getString("id")+"</td></tr>"
			            	  +"<tr><th>Stars</th><td>"+star+"</td></tr>"
			            	  +"<tr><th>Genre</th><td>&nbsp;&nbsp;&nbsp;"+genre+"</td></tr>"
			            	  +"<tr><th>Trailer</th><td>&nbsp;&nbsp;&nbsp;<a href='" +rs.getString("trailer_url")+"' >Click here!</a></td></tr>"
			            	  +"<tr><th>Price</th><td>&nbsp;&nbsp;&nbsp;$ 10.00</td></tr>"
			            	  +"</table>";
	              }
	       

	              
	              request.setAttribute("item",item);	
	              request.setAttribute("info", info);
            	  request.getRequestDispatcher("singlepage.jsp").forward(request, response);
 
            	  
            	  
            	  
	              rs.close();
	              rs_genre.close();
	              rs_star.close();
	              g_statement.close();
	              s_statement.close();
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
