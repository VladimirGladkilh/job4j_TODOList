package servlet;

import model.Item;
import store.HbmStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.StringJoiner;

public class IndexServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //create item and refresh data

        String taskTable = generateTable();
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/plain");
        resp.getWriter().write(taskTable);

    }

    private String generateTable() {
        List<Item> itemList = HbmStore.instOf().findAll();
        StringBuilder sj = new StringBuilder();
        return "";
    }
}
