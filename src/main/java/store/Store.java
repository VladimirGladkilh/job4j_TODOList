package store;

import model.Item;
import model.User;

import java.util.Collection;
import java.util.List;

public interface Store extends AutoCloseable{
        Item add(Item item);
        boolean replace(Integer id, Item item);
        boolean delete(Integer id);

        <T> Collection<T> findAll(Class<T> cl);

        List<Item> findByUser(User user);
        Item findById(Integer id);
        void save(Item item);

        Collection<User> findAllUsers();
        User add(User user);
        boolean update(Integer id, User user);
        boolean delete(User user);
        User findUserById(int id);
        User findUserByEmail(String name);
        void save(User user);
}
