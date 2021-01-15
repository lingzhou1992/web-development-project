
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




@WebServlet("/window")
public class window extends HttpServlet {
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
			      
			      dbcon = ds.getConnection();
				  if (dbcon == null)
			         out.println ("dbcon is null.");
			
				    response.setContentType("text/xml");

				    
				    
				    
				    String input = request.getParameter("input");

					 

	              Statement t_statement = dbcon.createStatement();
	              String s = "select id from movies where title = '"+input+"';";
	              ResultSet rs_title = t_statement.executeQuery(s);
	              
	              String movie_id = "";
	              while(rs_title.next()){
	            	  movie_id = rs_title.getString("id");
	              }
	              System.out.println(movie_id);
	              
	              
	              Statement g_statement = dbcon.createStatement();
	              String genre_query = "SELECT * FROM genres_in_movies m, genres g where m.genre_id = g.id AND m.movie_id = "+movie_id+";";
	              ResultSet rs_genre = g_statement.executeQuery(genre_query);
	              
	              String genre = "";
	              while(rs_genre.next()){
	            	  genre += rs_genre.getString("name") +"&nbsp;&nbsp;&nbsp;";
	              }
	              
	              
	              Statement s_statement = dbcon.createStatement();
	              String star_query = "SELECT * FROM stars_in_movies s, movies m, stars t where s.movie_id = m.id AND s.star_id = t.id AND m.id = "+movie_id+";";
	              ResultSet rs_star = s_statement.executeQuery(star_query);
	              
	              String star = "";
	              while(rs_star.next()){
	            	  star += rs_star.getString("first_name") +" "+ rs_star.getString("last_name") +"&nbsp;&nbsp;&nbsp;";
	              }
	              
	              
	             
	              Statement statement = dbcon.createStatement();
	              String query = "select * from movies where id = "+movie_id+ ";"; 
	              ResultSet rs = statement.executeQuery(query);
	              
	              String item = "";
	              String info ="";
	              String m_title = "";
	              
	              while (rs.next())
	              {
	            	  
	            	  info =  "<div class=\"col_1\"><br/><br/>&nbsp;&nbsp;<img src=" + rs.getString("banner_url") 
	            			  + " onerror=\"this.src='https://s3.amazonaws.com/images.seroundtable.com/t-google-404-1299071983.jpg'\""
	            			  +" class=\"img\" width=\"100\" height=\"150\"/></div>"
	            	   		  +"<div class=\"col_2\"><table class=\"table\">" 
			            	  +"<tr><th>Title</th><td>"+rs.getString("title")+"</td></tr>"
			            	  +"<tr><th>Year</th><td>"+rs.getString("year")+"</td></tr>"
			            	  +"<tr><th>Director</th><td>"+rs.getString("director")+"</td></tr>"
			            	  +"<tr><th>Stars</th><td>"+star+"</td></tr>"
			            	  +"<tr><th>Genre</th><td>"+genre+"</td></tr>"
			            	  +"<tr><th>Price</th><td>$ 10.00</td></tr>"
			            	  +"</table></div>";
	              }
	              
	              out.println(info);
	              

	              rs.close();
	              statement.close();
	              dbcon.close();
	            }
	        catch (SQLException ex) {
	        	out.println("");
	        	System.out.println("error");
//	              while (ex != null) {
//	                    out.println ("SQL Exception:  " + ex.getMessage ());
//	                    ex = ex.getNextException ();
//	                }  
	            } 

	        catch(java.lang.Exception ex)
	            {
	                out.println("");
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
