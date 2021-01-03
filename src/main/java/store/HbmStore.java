package store;

import model.Item;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.List;
import java.util.Optional;

public class HbmStore implements Store {
    private final StandardServiceRegistry registry;
    private final SessionFactory sf;

    public HbmStore() {
        registry = new StandardServiceRegistryBuilder()
                .configure().build();
        sf = new MetadataSources(registry)
                .buildMetadata().buildSessionFactory();
    }

    private static final class Lazy {
        private static final Store INST = new HbmStore();
    }

    public static Store instOf() {
        return Lazy.INST;
    }

    @Override
    public Item add(Item item) {
        Session session = sf.openSession();
        session.beginTransaction();
        session.save(item);
        session.getTransaction().commit();
        session.close();
        return item;
    }

    @Override
    public boolean replace(Integer id, Item item) {
        Item replaced = findById(id);
        if (replaced != null) {
            Session session = sf.openSession();
            session.beginTransaction();
            session.update(item);
            session.getTransaction().commit();
            session.close();
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean delete(Integer id) {
        Item replaced = findById(id);
        if (replaced != null) {
            Session session = sf.openSession();
            session.beginTransaction();
            Item item = new Item(null);
            item.setId(id);
            session.delete(item);
            session.getTransaction().commit();
            session.close();
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<Item> findAll() {
        Session session = sf.openSession();
        session.beginTransaction();
        List result = session.createQuery("from Item ").list();
        session.getTransaction().commit();
        session.close();
        return result;
    }

    @Override
    public List<Item> findActive() {
        Session session = sf.openSession();
        session.beginTransaction();
        List result = session.createQuery("from Item where done = :done").setParameter("done", false).list();
        session.getTransaction().commit();
        session.close();
        return result;
    }


    @Override
    public Item findById(Integer id) {
        Session session = sf.openSession();
        session.beginTransaction();
        Optional<Item> result = session.createQuery("from Item where id = :id").setParameter("id", id).stream().findAny();
        session.getTransaction().commit();
        session.close();
        return result.isPresent() ? result.get() : null;
    }

    @Override
    public void save(Item item) {
        if (item.getId() == 0) {
            add(item);
        } else {
            replace(item.getId(), item);
        }
    }

    @Override
    public void close() throws Exception {
        StandardServiceRegistryBuilder.destroy(registry);
    }
}
