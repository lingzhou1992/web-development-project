
/* A servlet to display the contents of the MySQL movieDB database */

import java.io.*;
import java.net.*;
import java.sql.*;
import java.text.*;
import java.util.*;
import java.util.Map.Entry;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.sql.DataSource;

@WebServlet("/displayMovieM")
public class displayMovieM extends HttpServlet {
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
	              
				  
				  
				  String sortbytitleA = request.getParameter("sortbytitleA");
				  String sortbytitleD = request.getParameter("sortbytitleD");
				  String sortbyyearA = request.getParameter("sortbyyearA");
				  String sortbyyearD = request.getParameter("sortbyyearD");
				  
				  
				  String sort = "";
				  if(sortbytitleA != null)
					  sort = "order by title";
				  else if(sortbytitleD != null)
					  sort = "order by title DESC";
				  else if(sortbyyearA != null)
					  sort = "order by year";
				  else if(sortbyyearD != null)
					  sort = "order by year DESC";
				  
				  String perpage5 = request.getParameter("perpage5");
				  String perpage10 = request.getParameter("perpage10");
				  String perpage15 = request.getParameter("perpage15");
				  String perpage20 = request.getParameter("perpage20");
				  String perpage25 = request.getParameter("perpage25");

				  String prev = request.getParameter("prev");
				  String next = request.getParameter("next");
				  
				  String o = request.getParameter("offset");
				  int offset;
				  if(o == null)
					  offset = 1;
				  else 
					  offset = Integer.parseInt(o);
				  
				  if(prev != null && offset > 1)
					  --offset;
				  else if(next != null)
					  ++offset;
				

				  String perpage = "";
				  int number = 0;
				  
				  
				  if(perpage10 != null){
					  perpage = " LIMIT 10 offset "+ (offset*10-10);
					  number = 10;
				  }else if(perpage15 != null){
					  perpage = " LIMIT 15 offset "+(offset*15-15);
					  number = 15;
				  }else if(perpage20 != null){
					  perpage = " LIMIT 20 offset "+(offset*20-20);
					  number = 20;
				  }else if(perpage25 != null){
					  perpage = " LIMIT 25 offset "+(offset*25-25);
					  number = 25;
				  }else if(perpage5 != null){
					  perpage = " LIMIT 5 offset "+ (offset*5-5);
					  number = 5;
				  }else if(prev != null || next != null){
					  String n = request.getParameter("number");
					  if(n == null){
						  perpage = " LIMIT 5 offset "+ (offset*5-5);
						  number = 5;
					  }else{
						  number = Integer.parseInt(n);
						  perpage = " LIMIT "+ n + " offset " + (offset*number-number);						  
					  }
				  }else if(sort.length() > 1){
					  String n = request.getParameter("number");
					  if(n == null){
						  perpage = " LIMIT 5 offset "+ (offset*5-5);
						  number = 5;
					  }else{
						  number = Integer.parseInt(n);
						  perpage = " LIMIT "+ n + " offset 0";						  
					  }
	           	  }else{
					  perpage = " LIMIT 5 offset "+ (offset*5-5);
					  number = 5;
	           	  }


	              String title = request.getParameter("title");
	              String toSearch = "";
	              String[] keywords = title.split("[^a-zA-Z0-9']+");
				 
				 
	              for(int i=0; i<keywords.length; ++i)
					toSearch += "+"+keywords[i]+"* ";
				 
	
	              Statement statement = dbcon.createStatement();
	              String query = "select * from movies where match(title) against ('"+toSearch+"' in boolean mode) OR title = '"+title+"';"; 
	              ResultSet rs_input = statement.executeQuery(query);
	              
	              ArrayList<String> id_list = new ArrayList<String>();
	              while(rs_input.next()){
    				  id_list.add(rs_input.getString("id"));
    				  out.println(rs_input.getString("id"));
	              }
	              
	              

	              int total_result = 0;
	              String notFound = "";
	              
	            	  

	   
	              if(id_list.isEmpty()){
            		  String disabledprev = "disabled";
            		  String disablednext = "disabled";
            		  String disabledpage = "disabled";
            	      request.setAttribute("disabledpage", disabledpage);
            	      request.setAttribute("disabledprev", disabledprev);
    	              request.setAttribute("disablednext", disablednext);
	            	  notFound = "Sorry, cannot find any record!";
	            	  request.setAttribute("notFound",notFound);
	            	  request.getRequestDispatcher("displayMovieM.jsp").forward(request, response);
	              }
	              total_result = id_list.size();
  
	              

            	  query = "select * from movies where id in (" + id_list.get(0);          	  
            	  for(int i = 1; i < id_list.size(); ++i)
            		 query += ", " + id_list.get(i); 
            	  query += ") " + sort+ perpage +";";


	              ArrayList<String> item_list = new ArrayList<String>();
	              ArrayList<String> info_list = new ArrayList<String>();
	              ResultSet rs = statement.executeQuery(query);
            	  int total_perpage = 0;
            	  
            	  while(rs.next()){
            		  Statement g_statement = dbcon.createStatement();
		              String genre_query = "SELECT * FROM genres_in_movies m, genres g where m.genre_id = g.id AND m.movie_id = "+rs.getString("id")+";";
		              ResultSet rs_genre = g_statement.executeQuery(genre_query);
		              
		              String genre_name = "";
		              while(rs_genre.next()){
		            	  genre_name +=  "&nbsp;&nbsp;&nbsp;" + rs_genre.getString("name");
		              }
		              
		              Statement s_statement = dbcon.createStatement();
		              String star_query = "SELECT * FROM stars_in_movies s, movies m, stars t where s.movie_id = m.id AND s.star_id = t.id AND m.id = "+rs.getString("id")+";";
		              ResultSet rs_star = s_statement.executeQuery(star_query);
		              
		              String star = "";
		              while(rs_star.next()){
		            	  star += "<form method=\"get\" action= \"star\">"
		            	  		+ "<input type=\"hidden\" name=\"star_id\" value="+ rs_star.getString("star_id") +">"
		            	  		+ "<button type=\"submit\" class=\"btn btn-link\" role=\"link\" name=\"star_name\" value=\'"
		            	  		+ rs_star.getString("first_name") +" "+ rs_star.getString("last_name")+"'>" 
		            	  		+ rs_star.getString("first_name") +" "+ rs_star.getString("last_name")+"</button></form>";
		              }
		              s_statement.close();
		              rs_star.close();
		              
		              
		              String m_title = "";
		              m_title += "<form method=\"get\" action= \"singleMovie\">"
		            	  		+ "<input type=\"hidden\" name=\"id\" value="+ rs.getString("id") +">"
		            	  		+ "<button type=\"submit\" class=\"btn btn-link\" role=\"link\" name=\"movie_title\">" 
		            	  		+ rs.getString("title") + "</button></form>";
		              
		              
		              String item = "";
		              
		              
		            	 
	            	  item =  "<table><tr><td width=\"200\"><img src=" + rs.getString("banner_url") 
	            			  + " onerror=\"this.src='https://s3.amazonaws.com/images.seroundtable.com/t-google-404-1299071983.jpg'\""
	            			  +" class=\"img\" width=\"100\" height=\"150\"><br/><br/>"
	            			  + "<form method=\"post\" action=\"shoppingcart\" target=\"shoppingcart\">" 
	            			  +"<input type=\"hidden\" name=\"id\" value="+ rs.getString("id") +">"
	            			  + "<button  type=\"submit\" name=\"cart\" class=\"btn btn-default\">Add to Cart</button></form></td>"
	         
	            	  		  +	"<td width=\"600\"><table class=\"table table-striped\">" 
			            	  +"<tr><th>Title</th><td>"+m_title+"</td></tr>"
			            	  +"<tr><th>Year</th><td>&nbsp;&nbsp;&nbsp;"+rs.getString("year")+"</td></tr>"
			            	  +"<tr><th>Director</th><td>&nbsp;&nbsp;&nbsp;"+rs.getString("director")+"</td></tr>"
			            	  +"<tr><th>Movie Id</th><td>&nbsp;&nbsp;&nbsp;"+rs.getString("id")+"</td></tr>"
			            	  +"<tr><th>Stars</th><td>"+star+"</td></tr>"
			            	  +"<tr><th>Genre</th><td>"+genre_name+"</td></tr>"
			            	  +"<tr><th>Trailer</th><td>&nbsp;&nbsp;&nbsp;<a href='" +rs.getString("trailer_url")+"' >Click here!</a></td></tr>"
			            	  +"<tr><th>Price</th><td>&nbsp;&nbsp;&nbsp;$ 10.00</td></tr>"
			            	  +"</table></td></tr></table>";
	            	  
	            	  item_list.add(item); 
	            	  ++total_perpage;
            	  }
            	  
            	  String disabledprev = "";
            	  String disablednext = "";
            	  if(offset == 1)
            		  disabledprev = "disabled";
            	  if(total_perpage < number || total_perpage == total_result)
            		  disablednext = "disabled";

   
	              request.setAttribute("title",title);
	   
	              request.setAttribute("disabledprev", disabledprev);
	              request.setAttribute("disablednext", disablednext);
	              request.setAttribute("offset", offset);
	              request.setAttribute("number", number);
	              request.setAttribute("notFound",notFound);
	              request.setAttribute("item_list",item_list);	
	              request.getRequestDispatcher("displayMovieM.jsp").forward(request, response);
		     
	              rs_input.close();
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
