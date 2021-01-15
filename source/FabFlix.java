
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


@WebServlet("/FabFlix")
public class FabFlix extends HttpServlet {
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
	              Statement statement = dbcon.createStatement();
	              String email = request.getParameter("email");
	              String password = request.getParameter("pwd");
	              String query = "SELECT * from customers where email = '" + email + "'AND password = '" + password + "';"; 
	              ResultSet rs = statement.executeQuery(query);
	              	              
	              if (!rs.isBeforeFirst()) { 
	            	  request.setAttribute("message","<div class=\"alert alert-danger\" role=\"alert\"><span class=\"glyphicon glyphicon-exclamation-sign\" aria-hidden=\"true\"></span><span class=\"sr-only\">Error:</span>The email or password is incorrect.</div>");
	            	  request.getRequestDispatcher("/index.jsp").forward(request, response);
	      			  return;
	              }else {
	            	  String name = "";
	            	  while (rs.next()){
	            		  name = rs.getString("first_name") +" " + rs.getString("last_name");
	            	  }
	            	  HttpSession session = request.getSession();
		              session.setAttribute("email",email);
		              session.setAttribute("password",password);
		              session.setAttribute("username",name);
		              Cookie userName = new Cookie("email", email);
		              Cookie pwd = new Cookie("pwd", password);
		              response.addCookie(userName);
		              response.addCookie(pwd);
	              }
	              
	              
	              
	              Statement statement1 = dbcon.createStatement();

	              
	              String query1 = "SELECT * from movies where banner_url LIKE '%imdb%.jpg' order by year DESC limit 7;"; 
	              ResultSet rs1 = statement1.executeQuery(query1);
	    
	              // Iterate through each row of rs
            	  HttpSession session1 = request.getSession();

	              ArrayList<String> list = new ArrayList<String>();
	              String item = "";
	              while (rs1.next())
	              {
	            	  item =  "<div class=\"desc\">"
	            			  + "<form method=\"get\" action= \"singleMovie\"><input type=\"image\" src=" + rs1.getString("banner_url")+ " class=\"img\" width=\"100\" height=\"150\">"
	            			  + "<input type=\"hidden\" name=\"id\" value="+ rs1.getString("id") +">"
	            			  +"<button class=\"btn btn-link\" role=\"link\" type=\"submit\">"+ rs1.getString("title") + "<br/>(" + rs1.getString("year")+")</button><br/>$ 10.00<br/></form>" 
	            			  +"<form method=\"post\" action=\"shoppingcart\" target=\"shoppingcart\">" 
	            			  +"<input type=\"hidden\" name=\"id\" id=\"id\" value="+ rs1.getString("id") +">"
	            			  + "<button type=\"submit\" class=\"btn btn-default\">Add to Cart</button></form></div>";
	            	  list.add(item);

	              }
	              session1.setAttribute("list",list);
            	  request.getRequestDispatcher("home.jsp").forward(request, response);
	              
	              
	              rs1.close();
	              statement1.close();
	              //response.sendRedirect("home.jsp");
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
