
import dal.ListDAL;
import java.util.ArrayList;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import modals.BookModel;

@WebListener
public class Application implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();
//        context.addServlet("Book", new Book(new ListDAL<>("book.sh"))).addMapping("/*");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }
}