package store;

import model.Item;

import java.util.List;

public interface Store extends AutoCloseable{
        Item add(Item item);
        boolean replace(Integer id, Item item);
        boolean delete(Integer id);
        List<Item> findAll();
        List<Item> findActive();
        Item findById(Integer id);
        void save(Item item);
}
