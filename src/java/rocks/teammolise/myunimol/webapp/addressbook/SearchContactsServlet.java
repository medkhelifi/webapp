/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rocks.teammolise.myunimol.webapp.addressbook;

import com.mashape.unirest.http.exceptions.UnirestException;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import rocks.teammolise.myunimol.api.APIConsumer;
import rocks.teammolise.myunimol.webapp.UserInfo;

/**
*
* @author Pasquale
*/
@WebServlet(name = "SearchContactsServlet", urlPatterns = {"/SearchContactsServlet"})
public class SearchContactsServlet extends HttpServlet {
  /**
    * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
    * methods.
    *
    * @param request servlet request
    * @param response servlet response
    * @throws ServletException if a servlet-specific error occurs
    * @throws IOException if an I/O error occurs
    */
   protected void processRequest(HttpServletRequest request, HttpServletResponse response)
           throws ServletException, IOException {

       response.setContentType("application/json; charset=UTF-8");
       PrintWriter out = response.getWriter();

       try {
           if (request.getSession().getAttribute("userInfo") == null) {
               response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
               return;
           }

           UserInfo userInfo = (UserInfo) request.getSession().getAttribute("userInfo");

           String username = userInfo.getUsername();
           String password = userInfo.getPassword();
           String name = request.getParameter("search");
           
           Map<String, Object> parameters = new HashMap<String, Object>();
           parameters.put("search", name);

           JSONObject examSessionsJSON = new APIConsumer().consume("searchContacts", username, password, parameters);
           
           out.print(examSessionsJSON);
       } catch (UnirestException ex) {
           response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal server error");
       } finally {
           out.close();
       }
   }

   /**
    * Handles the HTTP <code>GET</code> method.
    *
    * @param request servlet request
    * @param response servlet response
    * @throws ServletException if a servlet-specific error occurs
    * @throws IOException if an I/O error occurs
    */
   @Override
   protected void doGet(HttpServletRequest request, HttpServletResponse response)
           throws ServletException, IOException {
       processRequest(request, response);
   }

   /**
    * Handles the HTTP <code>POST</code> method.
    *
    * @param request servlet request
    * @param response servlet response
    * @throws ServletException if a servlet-specific error occurs
    * @throws IOException if an I/O error occurs
    */
   @Override
   protected void doPost(HttpServletRequest request, HttpServletResponse response)
           throws ServletException, IOException {
       processRequest(request, response);
   }

   /**
    * Returns a short description of the servlet.
    *
    * @return a String containing servlet description
    */
   @Override
   public String getServletInfo() {
       return "Short description";
   }// </editor-fold>

}