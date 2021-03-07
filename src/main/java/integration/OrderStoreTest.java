package integration;
import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class OrderStoreTest {
    private BasicDataSource pool = new BasicDataSource();
private static OrderStore store;

    @Before
    public void setUp() throws SQLException {
        pool.setDriverClassName("org.hsqldb.jdbcDriver");
        pool.setUrl("jdbc:hsqldb:mem:tests;sql.syntax_pgs=true");
        pool.setUsername("sa");
        pool.setPassword("");
        pool.setMaxTotal(20);
        pool.setMaxOpenPreparedStatements(10);
        StringBuilder builder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream("src/main/db/update_001.sql")))
        ) {
            br.lines().forEach(line -> builder.append(line).append(System.lineSeparator()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        pool.getConnection().prepareStatement(builder.toString()).executeUpdate();
        store = new OrderStore(pool);
    }

    @Test
    public void whenSaveOrderAndFindAllOneRowWithDescription() {

        store.save(Order.of("name1", "description1"));

        List<Order> all = (List<Order>) store.findAll();

        assertThat(all.size(), is(1));
        assertThat(all.get(0).getDescription(), is("description1"));
        assertThat(all.get(0).getId(), is(1));
    }

    @Test
    public void whenFindByName() {

        store.save(Order.of("name1", "description1"));

        Order find = store.findByName("name1");
        assertThat(find.getDescription(), is("description1"));
        assertThat(find.getName(), is("name1"));

    }

    @Test
    public void whenUpdate() {

        store.save(Order.of("name1", "description1"));

        Order newOrder = Order.of("newName", "newDescription");
        newOrder.setId(store.findByName("name1").getId());
        store.update(newOrder);
        Order find = store.findByName("newName");
        assertThat(find.getDescription(), is("newDescription"));
        assertThat(find.getName(), is("newName"));
    }

    @After
    public void afterClose() throws SQLException {
        pool.getConnection().prepareStatement("drop table orders").executeUpdate();
    }
}