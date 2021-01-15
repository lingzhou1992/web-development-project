
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

@WebServlet("/shoppingcart")
public class shoppingcart extends HttpServlet {
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
				  
				  
				  
				  HttpSession session = request.getSession (true);

			      // Get the cart
			      HashMap<String, Integer> cart = (HashMap) session.getAttribute ("cart");
	              String movie_id = request.getParameter("id");


			      // if the session is new, the cart won't exist.
			      if (cart == null)
			      {
			         cart = new HashMap<String, Integer>();
			         if (movie_id != null)
			        	 cart.put(movie_id, 1);
			         session.setAttribute ("cart", cart);

			      }
			      else{
				      if (movie_id != null){
					      	if (cart.containsKey(movie_id))
								cart.put(movie_id,cart.get(movie_id)+1);
							else
								cart.put(movie_id,1);
					      }
			      }

			      
			      if(request.getParameter("quantity") != null && request.getParameter("quantity").matches("[0-9]+")){
				      String change_id = request.getParameter("change_id");
				      String quant = request.getParameter("quantity");
			    	  int quantity = Integer.parseInt(quant);
			    	  if(quantity == 0)
			    		  cart.remove(change_id);
			    	  else
			    		  cart.put(change_id, quantity);

			      }
		      
			     
			     if(request.getParameter("remove") != null){
				     String change_id = request.getParameter("change_id");
			    	 String remove = request.getParameter("remove");
			    	 cart.remove(change_id);
			     }
			     
			     String button_disable ="";
			     if(cart.isEmpty()){
			    	  button_disable = "disabled";
			    	  request.setAttribute("disable", button_disable);
	            	  request.getRequestDispatcher("shoppingcart.jsp").forward(request, response);
			     }

			     ArrayList<String> id_list = new ArrayList<String>();
		    	  for(Map.Entry<String, Integer> kv: cart.entrySet()){
		    		  id_list.add(kv.getKey());
		    		  
		    	  }
			     
	              // Declare our statement
	              Statement statement = dbcon.createStatement();	             
	              String query = "SELECT * FROM movies WHERE id in (";
	              query += "'"+id_list.get(0)+"'";
	              for(int i = 1; i < id_list.size(); ++i){
	                  query += ",'" + id_list.get(i) + "'";
	              }
	              query += ")";
		          ResultSet rs = statement.executeQuery(query);

		          
	              ArrayList<String> list = new ArrayList<String>();
	              String item = "";
	              while (rs.next())
	              {
	            	  item = "<td>"+ rs.getString("title") + "(" + rs.getString("year")+")</td>"
	            			  +"<td>$ 10.00 </td><td>"+cart.get(rs.getString("id"))+"</td>"
	            			  +"<td><form method=\"post\" action=\"shoppingcart\">"
	            			  +"<input type=\"number\" size=\"4\" name=\"quantity\">"
	            			  +"<input type=\"hidden\" name=\"change_id\" value="+rs.getString("id")+">"
	            			  +"&nbsp;<button class=\"btn btn-default\" type=\"submit\">Update</button>"
	            			  + "&nbsp;<button class=\"btn btn-default\" type=\"submit\" name=\"remove\">Remove</button>"
	            			  +"</form></td>";
	            	  list.add(item);
	         
	              }
	              

	              
	              
	              
	              request.setAttribute("disable", button_disable);
	              request.setAttribute("list",list);
            	  request.getRequestDispatcher("shoppingcart.jsp").forward(request, response);
            	  

	    
            	  
            	  
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
