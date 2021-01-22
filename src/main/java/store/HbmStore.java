package store;

import model.Category;
import model.Item;
import model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;
import java.util.Collection;
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
    public <T> Collection<T> findAll(Class<T> cl) {
        return this.tx(session -> {
            final Query query = session.createQuery("from "+ cl.getName(), cl);
            return query.list();
        });
    }

    @Override
    public List<Item> findByUser(User user) {
        return this.tx(session -> {
            final Query query = session.createQuery("from model.Item i fetch all properties where i.user = :user");
            query.setParameter("user", user);
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
    public Collection<User> findAllUsers() {
        return this.tx(session -> {
            final Query query = session.createQuery("from model.User");
            return query.list();
        });
    }

    @Override
    public User add(User user) {
        return this.tx(session -> {
            session.save(user);
            return user;
        });
    }

    @Override
    public boolean update(Integer id, User user) {
        User replaced = findUserById(id);
        if (replaced != null) {
            return this.tx(session -> {
                session.update(user);
                return true;
            });
        } else {
            return false;
        }
    }

    @Override
    public User findUserById(int id) {
        Optional<User> result = this.tx(session -> {
            final Query query = session.createQuery("from model.User where id = :id");
            query.setParameter("id", id);
            return query.stream().findAny();
        });
        return result.isPresent() ? result.get() : null;
    }

    @Override
    public User findUserByEmail(String email) {
        Optional<User> result = this.tx(session -> {
            final Query query = session.createQuery("from model.User where email = :email");
            query.setParameter("email", email);
            return query.stream().findAny();
        });
        return result.isPresent() ? result.get() : null;
    }

    @Override
    public void save(User user) {
        if (user.getId() == 0) {
            add(user);
        } else {
            update(user.getId(), user);
        }
    }

    @Override
    public Category findCategoryById(Integer id) {
        Optional<Category> result = this.tx(session -> {
            final Query query = session.createQuery("from model.Category where id = :id");
            query.setParameter("id", id);
            return query.stream().findAny();
        });
        return result.isPresent() ? result.get() : null;
    }

    @Override
    public void save(Item item, String cIds) {
        System.out.println("ids = "+ cIds);
        for (String id : cIds.split(";")) {
            if (id != null && !"".equals(id) && !id.equals(";")) {
                Category category = findCategoryById(Integer.parseInt(id));
                if (category != null) {
                    System.out.println(category.getName());
                    item.addCategory(category);
                }
            }
        }
        save(item);
    }

    @Override
    public boolean delete(User user) {
        User replaced = findUserById(user.getId());
        if (replaced != null) {
            return this.tx(session -> {
                session.delete(replaced);
                return true;
            });
        } else {
            return false;
        }

    }

}
