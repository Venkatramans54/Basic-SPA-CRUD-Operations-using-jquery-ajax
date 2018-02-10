import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.mysql.fabric.Response;
import com.mysql.jdbc.JDBC4Connection;
import com.mysql.jdbc.PreparedStatement;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * BookServlet
 */
@WebServlet({"/book", "/book/delete","/book/list","/book/edit","/book/update"})
public class BookServlet extends HttpServlet {
    private BookDAO bookDAO;

    public void init() {
        String jdbcURL = getServletContext().getInitParameter("jdbcURL");
        String jdbcUsername = getServletContext().getInitParameter("jdbcUsername");
        String jdbcPassword = getServletContext().getInitParameter("jdbcPassword");

        bookDAO = new BookDAO(jdbcURL, jdbcUsername, jdbcPassword);

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String action = req.getServletPath();
        System.out.println(action);
        try {
            switch (action) {
            case "/new":
                System.out.println("new action called");
                //    showNewForm(req, resp);
                break;
            case "/book/delete":
                System.out.println("delete action called");
                //    deleteBook(req, resp);
                deleteBook(req,resp);
                
                break;
            case "/book/edit":
                System.out.println("edit action called");
                showBook(req, resp);
                 // updateBook(req, resp);
                break;
            case "/book/update":
                System.out.println("update action called");
                updateBook(req, resp);
                 // updateBook(req, resp);
                break;
            default:
                System.out.println("list action called");
                listBook(req, resp);
                break;
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ServletException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    private void listBook(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        List<Book> listBook = bookDAO.listAllBooks();
        request.setAttribute("listBook", listBook);

        Gson gson = new Gson();
        JsonElement element = gson.toJsonTree(listBook, new TypeToken<List<Book>>() {}.getType());        
        JsonArray jsonArray = element.getAsJsonArray();
        response.setContentType("application/json");
        request.setAttribute("book", listBook);
        response.getWriter().print(jsonArray);
      // response.sendRedirect("/");

        }
    protected void deleteBook(HttpServletRequest request,HttpServletResponse response) throws ServletException, SQLException,IOException
    {
        int id = Integer.parseInt(request.getParameter("code"));
   
        Book book = new Book(id);
    bookDAO.deleteBook(book);
    
    response.sendRedirect("/");
    }
    protected void updateBook(HttpServletRequest request,HttpServletResponse response) throws ServletException, SQLException,IOException
    {
      int id=Integer.parseInt(request.getParameter("id"));
      String title = request.getParameter("title");
      String author = request.getParameter("author");
      float price = Float.parseFloat(request.getParameter("price"));

      Book book = new Book(title, author, price);     
      book.setId(id);
      bookDAO.updateBook(book);    
      response.sendRedirect("/");
     

    }
    protected void showBook(HttpServletRequest request,HttpServletResponse response) throws ServletException, SQLException,IOException
    {
Book book=new Book();
    int id = Integer.parseInt(request.getParameter("value"));
   
    book.setId(id);
 List<Book> listBook = bookDAO.show(book);

        request.setAttribute("listBook", listBook);
        System.out.println(listBook);
        Gson gson = new Gson();
        JsonElement element = gson.toJsonTree(listBook, new TypeToken<List<Book>>() {}.getType());        
        JsonArray jsonArray = element.getAsJsonArray();
        response.setContentType("application/json");
        request.setAttribute("book", listBook);
        response.getWriter().print(jsonArray);
    }
}