package store;

import model.Item;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

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
        return this.tx(session -> {
            session.save(item);
            return item;
        });
    }

    @Override
    public boolean replace(Integer id, Item item) {
        Item replaced = findById(id);
        if (replaced != null) {
            return this.tx(session -> {
                session.update(item);
                return true;
            });
        } else {
            return false;
        }
    }

    @Override
    public boolean delete(Integer id) {
        Item replaced = findById(id);
        if (replaced != null) {
            return this.tx(session -> {
                session.delete(replaced);
                return true;
            });
        } else {
            return false;
        }
    }

    @Override
    public List<Item> findAll() {
        return this.tx(session -> {
            final Query query = session.createQuery("from model.Item");
            return query.list();
        });
    }

    @Override
    public List<Item> findActive() {
        return this.tx(session -> {
            final Query query = session.createQuery("from model.Item where done = :done");
            query.setParameter("done", false);
            return query.list();
        });
    }


    @Override
    public Item findById(Integer id) {
        Optional<Item> result = this.tx(session -> {
            final Query query = session.createQuery("from model.Item where id = :id");
            query.setParameter("id", id);
            return query.stream().findAny();
        });
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

    private <T> T tx(final Function<Session, T> command) {
        final Session session = sf.openSession();
        final Transaction tx = session.beginTransaction();
        try {
            T rsl = command.apply(session);
            tx.commit();
            return rsl;
        } catch (final Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
    }

}
