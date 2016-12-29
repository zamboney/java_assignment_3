/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import dal.ListDAL;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
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

    private Map<String, List<BookModel>> getCategories() {
        return this.GetBooks().stream().filter((b) -> !b.isRented()).collect(Collectors.groupingBy((b) -> b.getCategory()));
    }
    public static final String rentPath = "rents.sh";

    private ListDAL<RentModel> GetRents() {
        return new ListDAL<>(getServletContext().getRealPath(File.separator) + rentPath);
    }

    private ListDAL<BookModel> GetBooks() {
        return new ListDAL<>(getServletContext().getRealPath(File.separator) + Book.bookPath);
    }

    private ListDAL<UserModel> GeUsers() {
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
                    + "  <div id=\"home\" class=\"tab-pane fade in active\">\n");
            if (request.getParameter("category") == null || request.getParameter("category") == "") {
                out.println("<form action=\"Rent\" method=\"get\">\n"
                        + "\n"
                        + "  <div class=\"form-group\">\n"
                        + "    <label for=\"category\">Category</label>\n"
                        + "    <select class=\"form-control\" id=\"category\" name=\"category\">\n");
                this.getCategories().forEach((c, list)
                        -> out.printf("<option value='%s'>%s</option>", c, c));
                out.println("    </select></div>\n"
                        + "  <div class=\"row\"><div class='col-xs-12'><button type=\"submit\" class=\"col-xs-12 btn btn-primary \">Peak</button></div></div>\n"
                        + "</form>\n");
            } else {

                out.println("<form action=\"Rent\" method=\"post\">\n"
                        + "\n"
                        + "  <div class=\"form-group\">\n"
                        + "    <label for=\"userid\">User</label>\n"
                        + "    <select class=\"form-control\" id=\"userid\" name=\"userid\">\n");
                this.GeUsers().stream().filter((u) -> u.getDayLete() <= User.lateDays).forEach((u)
                        -> out.printf("<option value='%s'>%s</option>",
                                u.getId(), u.getFname() + " " + u.getLname()));
                out.println("    </select></div>\n"
                        + "    <div class=\"form-group\"><label for=\"bookname\">Book</label>\n"
                        + "    <select class=\"form-control\" id=\"bookname\" name=\"bookName\">");
                Book.GroupBook(this.GetBooks()
                        .stream()
                        .filter((b) -> !b.isRented())
                        .filter((b)
                                -> (b.getCategory() == null ? request.getParameter("category") == null : b.getCategory().equals(request.getParameter("category")))).collect(Collectors.toList()))
                        .forEach((index, list)
                                -> out.printf("<option value='%s'>%s</option>",
                                list.get(0).getId(), list.get(0).getName()));
                out.println("</select>\n"
                        + "  </div>\n"
                        + "  \n"
                        + "    <div class=\"form-group\"><label for=\"days\">Days</label>\n"
                        + "        <div class='row'><div class=\"col-sm-2\"><input required  class=\"form-control\"  type=\"number\" value=\"1\" min=\"1\" name=\"days\"></div></div>\n"
                        + "    </div>\n"
                        + "  <div class=\"row\"><div class='col-xs-12'><button type=\"submit\" class=\"col-xs-12 btn btn-primary \">Rent</button></div></div>\n"
                        + "<input type='hidden' name='rent' value=''/></form>\n");
            }
            out.println("  </div>\n"
                    + "  <div id=\"menu1\" class=\"tab-pane fade\">\n"
                    + "        <form action=\"Rent\" method=\"get\">\n"
                    + "        <div class=\"form-group\">\n"
                    + "              <label for=\"bookid\" >Book Id</label>\n"
                    + "              <input required=\"\" class=\"form-control\" type=\"text\" name=\"bookid\">\n"
                    + "        </div>\n"
                    + "        <div class=\"form-group\">\n"
                    + "            <label for=\"condition\">Condition</label>\n"
                    + "            <div class=\"row\">\n"
                    + "            <div class=\"col-sm-2\">\n"
                    + "                <select required=\"\" name=\"condition\" class=\"form-control\">\n"
                    + "                    <option value=\"Good\">Good</option>\n"
                    + "                    <option value=\"Bad\">Bad</option>\n"
                    + "                    <option value=\"UnUsed\">UnUsed</option>\n"
                    + "                </select>\n"
                    + "            </div>\n"
                    + "            </div>\n"
                    + "            </div>\n"
                    + "  <div class=\"row\"><div class='col-xs-12'><button type=\"submit\" class=\"col-xs-12 btn btn-primary \">Return</button></div></div>\n"
                    + "        <input type='hidden' name='return' value='return'/></form>"
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
        String bookid = request.getParameter("bookid");

        if ("return".equals(request.getParameter("return"))) {

            Optional<BookModel> returnBook = this.GetBooks().stream().filter((b) -> b.getId().equals(bookid)).findAny();
            if (!returnBook.isPresent()) {
                try (PrintWriter out = response.getWriter()) {
                    out.println("<!DOCTYPE html>");
                    out.println("<html>");
                    out.println("<head>");
                    out.println("<link href=\"//maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css\" rel=\"stylesheet\" integrity=\"sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u\" crossorigin=\"anonymous\">");
                    out.println("<title>Servlet Rent</title>");
                    out.println("</head>");
                    out.println("<body>"
                            + "");
                    out.println("<div class='container'><h1>Book not found</h1><div class='row'><a type=\"button\" class=\"col-xs-12 btn btn-info\" href=\"" + request.getRequestURI() + "\">Go Back</a></div><div>");
                    out.println("</body>");
                    out.println("</html>");

                }
            } else {
                BookModel book = returnBook.get();
                RentModel rent = this.GetRents().stream().filter((r) -> (r.getBookId() == null ? bookid == null : r.getBookId().equals(bookid)) && r.getEndTime() == null).findFirst().get();

                rent.setEndTime(new Date());

                Double doubleGap = Math.floor(((rent.getEndTime().getTime() - rent.getStartTime().getTime()) / 86400000) - rent.getDays());
                if (doubleGap.intValue() < 0) {
                    UserModel user = this.GeUsers().stream().filter((u) -> u.getId().equals(rent.getUserId())).findFirst().get();
                    user.setDayLete(user.getDayLete() + Math.abs(doubleGap.intValue()));
                    this.GeUsers().update(user);
                }

                if (request.getParameter("condition") != null) {
                    BookModel.BookCondition condition = BookModel.BookCondition.valueOf(request.getParameter("condition"));
                    if (condition != null) {
                        book.setCondition(condition);

                    }
                }
                book.setRented(false);

                this.GetBooks().update(book);

                this.GetRents().update(rent);

                try (PrintWriter out = response.getWriter()) {
                    out.println("<!DOCTYPE html>");
                    out.println("<html>");
                    out.println("<head>");
                    out.println("<link href=\"//maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css\" rel=\"stylesheet\" integrity=\"sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u\" crossorigin=\"anonymous\">");
                    out.println("<title>Servlet Rent</title>");
                    out.println("</head>");
                    out.println("<body>");
                    out.println("<div class='container'><h1>Book Return</h1>"
                            + "<div class='row'><a type=\"button\" class=\"col-xs-12 btn btn-info\" href=\"" + request.getRequestURI() + "\">Go Back</a></div><div>");
                    out.println("</body>");
                    out.println("</html>");
                }
            }
        } else {
            processRequest(request, response);
        }

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
        if (request.getParameter("rent") != null) {
            String bookId = request.getParameter("bookName");
            BookModel book = this.GetBooks().stream().filter((b) -> (b.getId() == null ? bookId == null : b.getId().equals(bookId))).findFirst().get();
            book.setRented(true);
            this.GetBooks().update(book);
            this.GetRents().add(new RentModel(bookId, request.getParameter("userid"), Integer.parseInt(request.getParameter("days"))));
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
