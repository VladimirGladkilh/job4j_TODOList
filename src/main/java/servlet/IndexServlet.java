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
import java.util.concurrent.atomic.AtomicInteger;

public class IndexServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //create item and refresh data
        if (req.getParameter("deleteid") != null ){
            HbmStore.instOf().delete(Integer.parseInt(req.getParameter("deleteid")));
        } else if (req.getParameter("doneid") != null) {
            Item checkItem = HbmStore.instOf().findById(Integer.parseInt(req.getParameter("id")));
            if (checkItem != null) {
                checkItem.setDone(req.getParameter("done").equalsIgnoreCase("true"));
                HbmStore.instOf().save(checkItem);
            }
        } else if (req.getParameter("id") != null){
            HbmStore.instOf().save(
                    new Item(
                            Integer.parseInt(req.getParameter("id")),
                            req.getParameter("description"),
                            req.getParameter("done").equalsIgnoreCase("true")
                    )
            );
        }

        String taskTable = generateTable();
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/plain");
        resp.getWriter().write(taskTable);

    }

    private String generateTable() {
        List<Item> itemList = HbmStore.instOf().findAll();
        StringJoiner sj = new StringJoiner(System.lineSeparator());
        sj.add("<table class=\"table\" id=\"itemTable\"");
        sj.add("<tr><th>№</th><th>Содержание</th><th>Выполнено</th><th>Удалить</th></tr>");
        AtomicInteger i = new AtomicInteger(1);
        itemList.forEach(item -> {
            String row = String.format("<tr id=\"%s\"><td>%d</td><td>%s</td><td>%s</td><td>%s</td></tr>",
                    item.getId(), i.getAndIncrement(), item.getDescription(),
                    generateCheckBox(item), generateDelBtn(item));
            sj.add(row);
        });
        sj.add("</table>");

        return sj.toString();
    }

    private String generateDelBtn(Item item) {
        return String.format("<i class=\"fa fa-remove mr-3\" onclick=\"deleteid(%s)\"></i>", item.getId());
    }

    private String generateCheckBox(Item item) {
        String checkBox = String.format("<input type=\"checkbox\" id=\"%s\" %s onchange=\"doneid(%s)\" />",
                item.getId(), item.getDone() ? "checked disabled" : "", item.getId());
        return  checkBox;
    }

}
