package servlet;

import model.Category;
import model.Item;
import model.User;
import store.HbmStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class IndexServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getSession().getAttribute("user") != null) {
            User user = (User) req.getSession().getAttribute("user");
            req.setAttribute("user", user);
            req.setAttribute("items", HbmStore.instOf().findByUser(user));
            List<Item> items = HbmStore.instOf().findByUser(user);
            items.forEach(System.out::println);
            req.setAttribute("allCategory", HbmStore.instOf().findAll(Category.class));
            req.getRequestDispatcher("index.jsp").forward(req, resp);
        } else {
            resp.sendRedirect(req.getContextPath() + "/login.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        String cIds = req.getParameter("cIds");
        //create item and refresh data
        if (req.getParameter("deleteid") != null) {
            HbmStore.instOf().delete(Integer.parseInt(req.getParameter("deleteid")));
        } else if (req.getParameter("doneid") != null) {
            Item checkItem = HbmStore.instOf().findById(Integer.parseInt(req.getParameter("doneid")));
            if (checkItem != null) {
                checkItem.setDone(!checkItem.getDone());
                HbmStore.instOf().save(checkItem);
            }
        } else if (req.getParameter("id") != null) {
            HbmStore.instOf().save(
                    new Item(
                            Integer.parseInt(req.getParameter("id")),
                            req.getParameter("description"),
                            req.getParameter("done").equalsIgnoreCase("true"),
                            user
                    ),
                    cIds
            );
        }
        req.setAttribute("user", user);
        req.setAttribute("items", HbmStore.instOf().findByUser(user));
        resp.sendRedirect(req.getContextPath() + "/index.do");
    }


}
