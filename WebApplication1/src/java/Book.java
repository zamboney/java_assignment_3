/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import dal.ListDAL;
import modals.BookModel;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
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

    public List<BookModel> Books;

    public Book() {
        this.Books = new ListDAL<>("book.sh");
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
        if (request.getParameter("image") != null) {
            response.setContentType("image/jpeg");
            try (ServletOutputStream out = response.getOutputStream()) {
                ServletContext servletContext = getServletContext();
                String contextPath = servletContext.getRealPath(File.separator);
                FileInputStream fin = new FileInputStream(contextPath + request.getParameter("image"));

                BufferedInputStream bin = new BufferedInputStream(fin);
                BufferedOutputStream bout = new BufferedOutputStream(out);
                int ch = 0;;
                while ((ch = bin.read()) != -1) {
                    bout.write(ch);
                }

                bin.close();
                fin.close();
                bout.close();
                out.close();

            }

            return;
        }

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
                    + "<div class=\"jumbotron\"><div class=\"container\"><h2>Add Book</h2>"
                    + "<form action=\"Book\" method=\"post\" class=\"form-horizontal\" enctype=\"multipart/form-data\">\n"
                    + "    <div class=\"form-group\"><label for=\"name\" class=\"col-sm-2 control-label\">Book Name</label>\n"
                    + "        <div class=\"col-sm-10\"><input required  class=\"form-control\"  type=\"text\" name=\"name\"></div>\n"
                    + "    </div>\n"
                    + "    <div class=\"form-group\"><label for=\"aname\" class=\"col-sm-2 control-label\">Arthur Name</label>\n"
                    + "        <div class=\"col-sm-10\"><input required  class=\"form-control\"  type=\"text\" name=\"aname\"></div>\n"
                    + "    </div>\n"
                    + "    <div class=\"form-group\"><label for=\"category\" class=\"col-sm-2 control-label\">Category</label>\n"
                    + "        <div class=\"col-sm-10\"><input required   class=\"form-control\" type=\"text\" name=\"category\"></div>\n"
                    + "    </div>\n"
                    + "    <div class=\"form-group\"><label for=\"year\" class=\"col-sm-2 control-label\">Year Of Publish</label>\n"
                    + "        <div class=\"col-sm-10\"><input required  class=\"form-control\"  type=\"text\" name=\"year\"></div>\n"
                    + "    </div>\n"
                    + "\n"
                    + "    <div class=\"form-group\"><label for=\"picture\" class=\"col-sm-2 control-label\">Picture</label>\n"
                    + "        <div class=\"col-sm-10\"><input required type=\"file\" accept=\"image/jpeg\" name=\"picture\"></div>\n"
                    + "    </div>\n<div class=\"form-group\">\n"
                    + "        <div class=\"col-sm-offset-2 col-sm-10\">\n"
                    + "    <button type=\"submit\" class=\"btn btn-default\">add book</button>\n"
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
                    + "    <tr><th>Book Name</th><th>Arthur Name</th><th>Category</th><th>Year</th><th>Image</th></tr>");
            this.Books.forEach((b) -> out.println(String.format("<tr><td>%s</td><td>%s</td><td>%s</td><td>%s</td><td>%s</td></tr>", b.getName(), b.getaName(), b.getCategory(), b.getYear(), "<img style=\"\n"
                    + "    height: 60px;\n"
                    + "\" class=\"img-thumbnail\" src='Book?image=" + b.getPicturePath() + "'/>")));
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

        BookModel b = new BookModel(
                request.getParameter("name"),
                request.getParameter("aname"),
                request.getParameter("category"),
                request.getParameter("year"));

        Part filePart = request.getPart("picture"); // Retrieves <input type="file" name="file">
        String fileName = b.getId() + ".jpg";
        InputStream fileContent = filePart.getInputStream();
        byte[] buffer = new byte[fileContent.available()];
        fileContent.read(buffer);
        ServletContext servletContext = getServletContext();
        String contextPath = servletContext.getRealPath(File.separator);
        File targetFile = new File(contextPath + fileName);
        targetFile.createNewFile();
        OutputStream outStream = new FileOutputStream(targetFile);

        outStream.write(buffer);
        if (this.Books.add(b)) {
            response.sendRedirect("Book");
        } else {
            response.sendError(404);

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
