package controller;

import model.User;
import service.UserDao;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.text.BreakIterator;
import java.util.List;

@WebServlet(name = "UserServlet", value = "/users")
public class UserServlet extends HttpServlet {
        private static final long serialVersionUID = 1L;
        private UserDao userDAO;

        public void init() {
            userDAO = new UserDao();
        }

        protected void doPost(HttpServletRequest request, HttpServletResponse response)
                throws ServletException, IOException {
            String action = request.getParameter("action");
            if (action == null) {
                action = "";
            }
            try {
                switch (action) {
                    case "create":
                        insertUser(request, response);
                        break;
                    case "edit":
                        updateUser(request, response);
                        break;
                    case "search":
                        searchUser  (request, response);
                        break;
                }
            } catch (SQLException ex) {
                throw new ServletException(ex);
            }
        }



    protected void doGet(HttpServletRequest request, HttpServletResponse response)
                throws ServletException, IOException {
            String action = request.getParameter("action");
            if (action == null) {
                action = "";
            }

            try {
                switch (action) {
                    case "create":
                        showNewForm(request, response);
                        break;
                    case "edit":
                        showEditForm(request, response);
                        break;
                    case "delete":
                        deleteUser(request, response);
                        break;
                    case "search":
                        showSearchForm(request, response);

                        break;
                    case "sort":
                        sortByName(request, response);
                        break;
                    case "permision":
                        addUserPermision(request, response);
                        break;
                        default:
                        listUser(request, response);
                        break;
                }
            } catch (SQLException ex) {
                throw new ServletException(ex);
            }
        }

    private void addUserPermision(HttpServletRequest request, HttpServletResponse response) {
    User user = new User("tuan", "tuan.nguyen@gmail.com", "nam dinh mai dinh");
    int[] permision = {1, 2, 4};
    userDAO.addUserTransaction(user, permision);
        }

    private void sortByName(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
            RequestDispatcher dispatcher = request.getRequestDispatcher("user/sort.jsp");
            List<User> users = userDAO.sortUserByName();
            request.setAttribute("user", users);
            dispatcher.forward(request, response);
    }

    private void listUser(HttpServletRequest request, HttpServletResponse response)
                throws SQLException, IOException, ServletException {
            List<User> listUser = userDAO.selectAllUsers();
            request.setAttribute("listUser", listUser);
            RequestDispatcher dispatcher = request.getRequestDispatcher("user/list.jsp");
            dispatcher.forward(request, response);
        }

        private void showNewForm(HttpServletRequest request, HttpServletResponse response)
                throws ServletException, IOException {
            RequestDispatcher dispatcher = request.getRequestDispatcher("user/create.jsp");
            dispatcher.forward(request, response);
        }

        private void showEditForm(HttpServletRequest request, HttpServletResponse response)
                throws SQLException, ServletException, IOException {
            int id = Integer.parseInt(request.getParameter("id"));
            User existingUser = userDAO.getUserById(id);
            RequestDispatcher dispatcher = request.getRequestDispatcher("user/edit.jsp");
            request.setAttribute("user", existingUser);
            dispatcher.forward(request, response);

        }

        private void insertUser(HttpServletRequest request, HttpServletResponse response)
                throws SQLException, IOException, ServletException {
            String name = request.getParameter("name");
            String email = request.getParameter("email");
            String country = request.getParameter("country");
            User newUser = new User(name, email, country);
            userDAO.insertUserStore(newUser);
            RequestDispatcher dispatcher = request.getRequestDispatcher("user/create.jsp");
            dispatcher.forward(request, response);
        }

        private void updateUser(HttpServletRequest request, HttpServletResponse response)
                throws SQLException, IOException, ServletException {
            int id = Integer.parseInt(request.getParameter("id"));
            String name = request.getParameter("name");
            String email = request.getParameter("email");
            String country = request.getParameter("country");

            User book = new User(id, name, email, country);
            userDAO.updateUser(book);
            RequestDispatcher dispatcher = request.getRequestDispatcher("user/edit.jsp");
            dispatcher.forward(request, response);
        }

        private void deleteUser(HttpServletRequest request, HttpServletResponse response)
                throws SQLException, IOException, ServletException {
            int id = Integer.parseInt(request.getParameter("id"));
            userDAO.deleteUser(id);

            List<User> listUser = userDAO.selectAllUsers();
            request.setAttribute("listUser", listUser);
            RequestDispatcher dispatcher = request.getRequestDispatcher("user/list.jsp");
            dispatcher.forward(request, response);
        }
        private void searchUser(HttpServletRequest request, HttpServletResponse response)
                throws SQLException, IOException, ServletException{
            String country = request.getParameter("country");
            List<User> user = userDAO.findByCountry(country);
            request.setAttribute("user", user);
            RequestDispatcher dispatcher = request.getRequestDispatcher("user/search.jsp");
            dispatcher.forward(request, response);
        }
    private void showSearchForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException{
            RequestDispatcher dispatcher = request.getRequestDispatcher("user/search.jsp");
            dispatcher.forward(request, response);
    }

}
