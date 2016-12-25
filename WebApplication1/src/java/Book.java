/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.file.Paths;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/**
 *
 * @author rani
 */
@WebServlet(urlPatterns = {"/Book"})
@MultipartConfig
public class Book extends HttpServlet {

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
                     out.println("<link href=\"//maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css\" rel=\"stylesheet\" integrity=\"sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u\" crossorigin=\"anonymous\">");
   out.println("<title>Servlet Book</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("        <ul class='nav nav-tabs'>\n"
                    + "            <li ><a href=\"Home\">Home</a></li>\n"
                    + "            <li><a href=\"Rent\">Rent</a></li>\n"
                    + "            <li><a href=\"User\">User</a></li>\n"
                    + "            <li class=\"active\"><a href=\"Book\">Books</a></li>\n"
                    + "\n"
                    + "        </ul>\n"
                    + "<form action=\"Book\" method=\"post\" class=\"form-horizontal\" enctype=\"multipart/form-data\">\n" +
"    <div class=\"form-group\"><label for=\"name\" class=\"col-sm-2 control-label\">Book Name</label>\n" +
"        <div class=\"col-sm-10\"><input type=\"text\" name=\"name\"></div>\n" +
"    </div>\n" +
"    <div class=\"form-group\"><label for=\"aname\" class=\"col-sm-2 control-label\">Arthur Name</label>\n" +
"        <div class=\"col-sm-10\"><input type=\"text\" name=\"aname\"></div>\n" +
"    </div>\n" +
"    <div class=\"form-group\"><label for=\"category\" class=\"col-sm-2 control-label\">Category</label>\n" +
"        <div class=\"col-sm-10\"><input type=\"text\" name=\"category\"></div>\n" +
"    </div>\n" +
"    <div class=\"form-group\"><label for=\"year\" class=\"col-sm-2 control-label\">Year Of Publish</label>\n" +
"        <div class=\"col-sm-10\"><input type=\"text\" name=\"year\"></div>\n" +
"    </div>\n" +
"\n" +
"    <div class=\"form-group\"><label for=\"picture\" class=\"col-sm-2 control-label\">Picture</label>\n" +
"        <div class=\"col-sm-10\"><input type=\"file\" name=\"picture\"></div>\n" +
"    </div>\n<div class=\"form-group\">\n"
                    + "        <div class=\"col-sm-offset-2 col-sm-10\">\n"
                    + "    <button type=\"submit\" class=\"btn btn-default\">add user</button>\n"
                    + "            </div>\n"
                    + "        </div>\n" +
"</form>");
            
            out.println("<h1>Servlet Book at " + request.getContextPath() + "</h1>");
            out.println("</body>");
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
//         String description = request.getParameter("description"); // Retrieves <input type="text" name="description">

    Part filePart = request.getPart("picture"); // Retrieves <input type="file" name="file">
    String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString(); // MSIE fix.
    InputStream fileContent = filePart.getInputStream();
    byte[] buffer = new byte[fileContent.available()];
    fileContent.read(buffer);
 
   File targetFile = new File("/Users/rani/NetBeansProjects/java_assignment_3/WebApplication1/web/WEB-INF/" + fileName);
   targetFile.createNewFile();
   OutputStream outStream = new FileOutputStream(targetFile);
    
    outStream.write(buffer);
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
