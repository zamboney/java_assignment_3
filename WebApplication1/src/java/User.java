/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import dal.ListDAL;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modals.BookModel;
import modals.UserModel;

/**
 *
 * @author rani
 */
@WebServlet(urlPatterns = {"/User"})
public class User extends HttpServlet {


    public User() {
     
    }
    private final String userPath = "user.sh";
    private List<UserModel> _users;

    private List<UserModel> GeUsers() {
        if (this._users == null) {
            this._users = new ListDAL<>(getServletContext().getRealPath(File.separator) + this.userPath);
        }
        return this._users;
    }

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
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet User</title>");
            out.println("<link href=\"//maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css\" rel=\"stylesheet\" integrity=\"sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u\" crossorigin=\"anonymous\">");
            out.println("</head>");
            out.println("<body>");
            out.println("        <ul class='nav nav-tabs'>\n"
                    + "            <li ><a href=\"Home\">Home</a></li>\n"
                    + "            <li><a href=\"Rent\">Rent</a></li>\n"
                    + "            <li class=\"active\"><a href=\"User\">User</a></li>\n"
                    + "            <li ><a href=\"Book\">Books</a></li>\n"
                    + "\n"
                    + "        </ul>\n"
                    + "");
            out.println("<div class=\"jumbotron\"><div class=\"container\"><h2>Add User</h2>");
            out.println("<form action=\"User\" method=\"post\" class=\"form-horizontal\">\n"
                    + "    <div class=\"form-group\">\n"
                    + "        <label  class=\"col-sm-2 control-label\" for=\"fname\">First Name</label>\n"
                    + "        <div class=\"col-sm-10\"><input type=\"text\" required class=\"form-control\" name=\"fname\"></div>\n"
                    + "        </div>\n"
                    + "    <div class=\"form-group\">\n"
                    + "        <label  class=\"col-sm-2 control-label\" for=\"lname\">Last Name</label>\n"
                    + "        <div class=\"col-sm-10\"><input type=\"text\" required class=\"form-control\" name=\"lname\"></div>\n"
                    + "        </div>\n"
                    + "    <div class=\"form-group\">\n"
                    + "        <label  class=\"col-sm-2 control-label\" for=\"email\">E-Mail</label>\n"
                    + "        <div class=\"col-sm-10\"><input type=\"email\" required class=\"form-control\" name=\"email\"></div>\n"
                    + "    </div>\n"
                    + "    <div class=\"form-group\">\n"
                    + "        <div class=\"col-sm-offset-2 col-sm-10\">\n"
                    + "    <button type=\"submit\" class=\"btn btn-default\">add user</button>\n"
                    + "            </div>\n"
                    + "        </div>\n"
                    + "</form>"
                    + "<hr>"
                    + "<div class=\"panel panel-default\">\n"
                    + "  <!-- Default panel contents -->\n"
                    + "  <div class=\"panel-heading\">List of users</div>\n"
                    + "\n"
                    + "  <!-- Table -->\n"
                    + "  <table class=\"table\">\n"
                    + "    <tr><th>First Name</th><th>Last Name</th><th>Email</th</tr>");
            this.GeUsers().forEach((user)->out.println(String.format("<tr><td>%s</td><td>%s</td><td>%s</td></tr>",user.getFname(),user.getLname(),user.getEmail())));
            out.println("  </table>\n"
                    + "</div>");
            out.println("</div></div></body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
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
        String fname = request.getParameter("fname"),
                lname = request.getParameter("lname"),
                email = request.getParameter("email");
        if (fname == null || lname == null || email == null) {
            response.sendError(301);
        } else {
            this.GeUsers().add(new UserModel(fname, lname, email));
            processRequest(request, response);
        }

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
