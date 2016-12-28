/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import dal.ListDAL;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modals.BookModel;
import modals.RentModel;
import modals.UserModel;

/**
 *
 * @author rani
 */
@WebServlet(urlPatterns = {"/Rent"})
public class Rent extends HttpServlet {

    public static final String rentPath = "rents.sh";
    private List<RentModel> _rents;

    private List<RentModel> GetRents() {
            return new ListDAL<>(getServletContext().getRealPath(File.separator) + rentPath);
    }

    private List<BookModel> _books;

    private List<BookModel> GetBooks() {
            return new ListDAL<>(getServletContext().getRealPath(File.separator) + Book.bookPath);
    }

    private List<UserModel> _users;

    private List<UserModel> GeUsers() {
        return new ListDAL<>(getServletContext().getRealPath(File.separator) + User.userPath);
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
            out.println("<link href=\"//maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css\" rel=\"stylesheet\" integrity=\"sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u\" crossorigin=\"anonymous\">");
            out.println("<title>Servlet Rent</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("        <ul class='nav nav-tabs'>\n"
                    + "            <li ><a href=\"Home\">Home</a></li>\n"
                    + "            <li class=\"active\"><a href=\"Rent\">Rent</a></li>\n"
                    + "            <li><a href=\"User\">User</a></li>\n"
                    + "            <li ><a href=\"Book\">Books</a></li>\n"
                    + "\n"
                    + "        </ul>\n"
                    + "");

            out.println("<div class=\"jumbotron\"><div class=\"container\"><h2>Rent Page</h2>\n"
                    + "<ul class=\"nav nav-tabs\">\n"
                    + "  <li class=\"active\"><a data-toggle=\"tab\" href=\"#home\">Rent A Book</a></li>\n"
                    + "  <li><a data-toggle=\"tab\" href=\"#menu1\">Return A Book</a></li>\n"
                    + "</ul>\n"
                    + "\n"
                    + "<div class=\"tab-content\">\n"
                    + "  <div id=\"home\" class=\"tab-pane fade in active\">\n"
                    + "    <form action=\"Rent\" method=\"post\">\n"
                    + "\n"
                    + "  <div class=\"form-group\">\n"
                    + "    <label for=\"userid\">User</label>\n"
                    + "    <select class=\"form-control\" id=\"userid\" name=\"userid\">\n");
            this.GeUsers().forEach((u)
                    -> out.printf("<option value='%s'>%s</option>",
                            u.getId(), u.getFname() + " " + u.getLname()));
            out.println("    </select>\n"
                    + "    <label for=\"bookname\">Book</label>\n"
                    + "    <select class=\"form-control\" id=\"bookname\" name=\"bookName\">");
            Book.GroupBook(this.GetBooks()).forEach((index, list)
                    -> out.printf("<option value='%s'>%s</option>",
                            list.get(0).getId(), list.get(0).getName()));
            out.println("</select>\n"
                    + "  </div>\n"
                    + "  \n"
                                        + "    <div class=\"form-group\"><label for=\"days\" class=\"col-sm-2 control-label\">Days</label>\n"
                    + "        <div class=\"col-sm-2\"><input required  class=\"form-control\"  type=\"number\" value=\"1\" min=\"1\" name=\"days\"></div>\n"
                    + "    </div>\n"
                    + "  <button type=\"submit\" class=\"pull-right btn btn-primary \">Rent</button>\n"
                    + "<input type='hidden' name='rent' value=''/></form>\n"
                    + "  </div>\n"
                    + "  <div id=\"menu1\" class=\"tab-pane fade\">\n"
                    + "    <p>Some content in menu 1.</p>\n"
                    + "  </div>\n"
                    + "</div>\n"
                    + "</div>\n"
                    + "  <script src=\"https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js\"></script>\n"
                    + "  <script src=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js\"></script>");

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
        if(request.getParameter("rent") != null){
            String bookId = request.getParameter("bookName");
            BookModel book = this.GetBooks().stream().filter((b)->(b.getId() == null ? bookId == null : b.getId().equals(bookId))).findFirst().get();
            this.GetBooks().remove(book);
            book.setRented(true);
            this.GetBooks().add(book);
            this.GetRents().add(new RentModel(request.getParameter("userid"), bookId, 0));
        }
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
