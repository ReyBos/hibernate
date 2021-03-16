package ru.reybos.integration;

import static org.junit.Assert.*;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.List;

import static org.hamcrest.core.Is.is;

public class OrdersStoreTest {
    private static final Logger LOG = LoggerFactory.getLogger(OrdersStoreTest.class.getName());
    private BasicDataSource pool = new BasicDataSource();

    @Before
    public void setUp() throws SQLException {
        pool.setDriverClassName("org.hsqldb.jdbcDriver");
        pool.setUrl("jdbc:hsqldb:mem:tests;sql.syntax_pgs=true");
        pool.setUsername("sa");
        pool.setPassword("");
        pool.setMaxTotal(2);
        StringBuilder builder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream("./db/scripts/update_003.sql")))
        ) {
            br.lines().forEach(line -> builder.append(line).append(System.lineSeparator()));
        } catch (IOException e) {
            LOG.debug("ошибка", e);
        }
        pool.getConnection().prepareStatement(builder.toString()).executeUpdate();
    }

    @Test
    public void whenSaveOrderAndFindAllOneRowWithDescription() {
        OrdersStore store = new OrdersStore(pool);

        store.save(Order.of("name1", "description1"));

        List<Order> all = (List<Order>) store.findAll();

        assertThat(all.size(), is(1));
        assertThat(all.get(0).getDescription(), is("description1"));
        assertThat(all.get(0).getId(), is(1));
    }

    @Test
    public void whenUpdateOrderAndFindByNameAndFindById() {
        OrdersStore store = new OrdersStore(pool);
        Order order = Order.of("name", "description");
        store.save(order);
        order.setName("test");
        store.update(order);
        Order orderDb = store.findByName("test");
        assertThat(orderDb, is(order));
        assertNotNull(store.findById(order.getId()));
    }
}