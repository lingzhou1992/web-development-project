
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


@WebServlet("/checkout")
public class checkout extends HttpServlet {
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
	              String firstname = request.getParameter("firstname");
	              String lastname = request.getParameter("lastname");
	              String creditcard = request.getParameter("creditcard");
	              String expdate = request.getParameter("expdate");
	              String query = "SELECT * from creditcards where first_name = '" + firstname + "'AND last_name = '" 
	            		  + lastname + "' AND id='"+ creditcard + "' AND expiration='"+ expdate +"';"; 
	              ResultSet rs = statement.executeQuery(query);
	              

	              if(!rs.isBeforeFirst()){
	            	  request.setAttribute("message","<div class=\"alert alert-danger\" role=\"alert\"><span class=\"glyphicon glyphicon-exclamation-sign\" aria-hidden=\"true\"></span><span class=\"sr-only\">Error:</span>The information is incorrect.</div>");
	            	  request.getRequestDispatcher("checkout.jsp").forward(request, response);
	              }else{
		              query = "SELECT id FROM customers WHERE first_name = '" + firstname + "' AND last_name = '" 
		            		  + lastname + "' AND cc_id = "+ creditcard +";";
		              rs = statement.executeQuery(query);
		              String cus_id = "";
		              while(rs.next())
		            	  cus_id = rs.getString("id");
		              HttpSession session = request.getSession(true);
		              HashMap<String, Integer> cart =  (HashMap<String, Integer>) session.getAttribute("cart");
		              for(Map.Entry<String, Integer> kv: cart.entrySet()){
		            	  String to_insert = "INSERT INTO sales VALUES(NULL, "+ cus_id +", "+kv.getKey()+", CURRENT_DATE());";
		            	  statement.executeUpdate(to_insert);
		              }
		              
		              out.println("<html><head><meta http-equiv=\"refresh\" content=\"3;url=home\" />" +
		              		"<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css\">"+
		              		"<link href=\"LoginPage.css\" rel=\"stylesheet\"></head><body>" + 
		              		"<div clas=\"container\"><section class=\"text-center\">" +
		              		"<h3>Your order has been confirmed!</h3><br/>Redirecting in 3 seconds...</section></body></html>");
		              session.removeAttribute("cart");
	              }

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
