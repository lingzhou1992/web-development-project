
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


@WebServlet("/home")
public class home extends HttpServlet {
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
				    //String input = "a";
					 String toSearch = "";
					 String[] keywords = input.split(" ");
					  
					 System.out.println(input);
					 
					 for(int i=0; i<keywords.length; ++i)
						toSearch += "+"+keywords[i]+"* ";
					 
		             

		             
					 
				  Statement statement = dbcon.createStatement();
				  String query = "select * from movies where match(title) against ('"+toSearch+"' in boolean mode) limit 10;"; 
	              ResultSet rs = statement.executeQuery(query);
	              
	              
	              String output = "<ul>";
	              while(rs.next()){
	            	  output += "<li onclick=\"changeInput(this.id);\" id='"
	            			  + rs.getString("title") +"' onmouseover=\"infowindow(this.id);\">" + rs.getString("title")+"</li>";
	              }
				
	              output += "<ul>";
	              out.println(output);
	              


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
