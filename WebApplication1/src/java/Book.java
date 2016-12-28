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
import java.util.Map;
import java.util.stream.Collectors;
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

    public static final String bookPath = "book.sh";
    public static Map<String, List<BookModel>> GroupBook(List<BookModel> books){
        return books.stream().collect(Collectors.groupingBy(b -> (b.getName() + b.getaName()).replace(' ', '_')));
    }
    private List<BookModel> _books;

    private List<BookModel> GetBooks() {
            return new ListDAL<>(getServletContext().getRealPath(File.separator) + bookPath);
    }

    public Book() {

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
        Map<String, List<BookModel>> groupBook = Book.GroupBook(this.GetBooks());
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println(
                    "<link href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css\" rel=\"stylesheet\" integrity=\"sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u\" crossorigin=\"anonymous\">\n");
            out.println("<title>Servlet Book</title>");
            out.println("<style>.row.hover:hover{background:#d0d0d0;}</style>"
                    + "</head>");
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
                    + "    <div class=\"form-group\"><label for=\"aname\" class=\"col-sm-2 control-label\">Author Name</label>\n"
                    + "        <div class=\"col-sm-10\"><input required  class=\"form-control\"  type=\"text\" name=\"aname\"></div>\n"
                    + "    </div>\n"
                    + "    <div class=\"form-group\"><label for=\"category\" class=\"col-sm-2 control-label\">Category</label>\n"
                    + "        <div class=\"col-sm-10\"><input required   class=\"form-control\" type=\"text\" name=\"category\"></div>\n"
                    + "    </div>\n"
                    + "    <div class=\"form-group\"><label for=\"year\" class=\"col-sm-2 control-label\">Year Of Publish</label>\n"
                    + "        <div class=\"col-sm-10\"><input pattern=\"[0-9]{4}\" required  class=\"form-control\"  type=\"text\" name=\"year\"></div>\n"
                    + "    </div>\n"
                    + "    <div class=\"form-group\"><label for=\"copies\" class=\"col-sm-2 control-label\">Copies</label>\n"
                    + "        <div class=\"col-sm-2\"><input required  class=\"form-control\"  type=\"number\" value=\"1\" min=\"1\" name=\"copies\"></div>\n"
                    + "    </div>\n"
                    + "    <div class=\"form-group\"><label for=\"condition\" class=\"col-sm-2 control-label\">Condition</label>\n"
                    + "        <div class=\"col-sm-2\">"
                    + "<select required name=\"condition\"  class=\"form-control\">\n"
                    + "  <option value='Good'>Good</option>\n"
                    + "  <option value='Bad'>Bad</option>\n"
                    + "  <option value='UnUsed'>UnUsed</option>\n"
                    + "</select>"
                    + "        </div>\n"
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
                    + "          <table class=\"table\">\n"
                    + "            <thead>\n"
                    + "                <tr>\n"
                    + "                    <th>Book Name</th><th>Arthur Name</th><th>Category</th><th>Year</th><th>Image</th><th>Copies</th>\n"
                    + "                </tr>\n"
                    + "            </thead>\n"
                    + "            <tbody>");
            groupBook
                    .forEach((key, list) -> {
                        out.println(
                                "<tr class=\"accordion-toggle\">\n"
                                + "                    <td data-toggle=\"collapse\" data-target=\"#collapse" + key + "\">" + list.get(0).getName() + "</td>\n"
                                + "                    <td data-toggle=\"collapse\" data-target=\"#collapse" + key + "\">" + list.get(0).getaName() + "</td>\n"
                                + "                    <td data-toggle=\"collapse\" data-target=\"#collapse" + key + "\">" + list.get(0).getCategory() + "</td>\n"
                                + "                    <td data-toggle=\"collapse\" data-target=\"#collapse" + key + "\">" + list.get(0).getYear() + "</td>\n"
                                + "                    <td data-toggle=\"collapse\" data-target=\"#collapse" + key + "\"><img style=\"height: 60px;\" class=\"img-thumbnail\" src='Book?image=" + list.get(0).getPicturePath() + "'\"/></td>\n"
                                + "                    <td data-toggle=\"collapse\" data-target=\"#collapse" + key + "\">" + list.size() + "</td>\n"
                                + "                </tr>"
                                + "                <tr>\n"
                                + "                    <td colspan=\"6\">\n" + "  <!-- Table -->\n"
                                + "          <div id=\"collapse" + key + "\" class=\"table collapse\">\n"
                                + "            <div class=\"row\">\n"
                                + "                    <div class=\"col col-xs-7\">Id</div>"
                                + "                    <div class=\"col col-xs-3\">Condition</div>"
                                + "                    <div class=\"col col-xs-1\">Rented</div>"
                                + "                    <div class=\"col col-xs-1\">Remove</div>\n"
                                + "            </div>"
                                + "<hr/>"
                                + "            ");
                        list.forEach((b) -> out.println(""
                                + "<div class=\"row hover\"><div class=\"col col-xs-7\">" + b.getId() + "</div>"
                                + "<div class=\"col col-xs-3\">" + b.getCondition().name() + "</div>"
                                + "<div class=\"col col-xs-1 text-center\">" + (b.isRented() ? "<span class=\"glyphicon-class\">glyphicon glyphicon-ok</span>" : "") + "</div>"
                                + "<div class=\"col col-xs-1 text-center\">" + (b.isRented() ? "" : "<form action=\"Book\" method=\"post\"><button type=\"submit\" class=\"btn btn-default\"><input type='hidden' name='removeID' value='" + b.getId() + "'/><span class=\"glyphicon glyphicon-remove\" aria-hidden=\"true\"></span></button></form>") + "</div></div>"));
                    });
            out.println("  </tbody></div>\n"
                    + "</div>");

            out.println("</div></div>\n"
                    + "<script>\n"
                    + "	window.addEventListener(\"keypress\", function(e){\n"
                    + "		if(e.ctrlKey && e.code === 'KeyQ' ){\n"
                    + "			document.querySelector('[name=\"name\"]').value = 'Book1'\n"
                    + "			document.querySelector('[name=\"aname\"]').value = 'Author 1'\n"
                    + "			document.querySelector('[name=\"category\"]').value = 'Action'\n"
                    + "			document.querySelector('[name=\"year\"]').value = '1988'\n"
                    + "		}\n"
                    + "			\n"
                    + "	}, false);\n"
                    + "</script>"
                    + "    <script src=\"http://getbootstrap.com/2.3.2/assets/js/jquery.js\"></script>\n"
                    + "    <script src=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js\"></script>\n"
                    + "</body>");
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
        if(request.getParameter("removeID") != null){
            String id = request.getParameter("removeID");
            BookModel removeMe = this.GetBooks().stream().filter((b)->(b.getId() == null ? id == null : b.getId().equals(id))).findFirst().get();
            this.GetBooks().remove(removeMe);
            
            response.sendRedirect("Book");
            return;
        }
        BookModel b = new BookModel(
                request.getParameter("name"),
                request.getParameter("aname"),
                request.getParameter("category"),
                request.getParameter("year"),
                BookModel.BookCondition.valueOf(request.getParameter("condition")));

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
        List<BookModel> books = this.GetBooks();
        books.add(b);
        for (int i = 1; i < Integer.parseInt(request.getParameter("copies")); i++) {
            books.add(b.clone());
        }
        response.sendRedirect("Book");

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
