package manytomany;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HbmRunAuthorBook {
    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();

            Book b1 = Book.of("Knigga 1");
            Book b2 = Book.of("Knigga 2");
            Book b3 = Book.of("Knigga 3");
            Book b4 = Book.of("Knigga 4");
            Book b5 = Book.of("Knigga 5");
            Author a1 = Author.of("Ivanov");
            Author a2 = Author.of("Petrov");
            Author a3 = Author.of("Sidorov");
            Author a4 = Author.of("Pupkin");
            Author a5 = Author.of("Pushkin");

            a1.getBooks().add(b1);
            a1.getBooks().add(b2);

            a2.getBooks().add(b3);
            a2.getBooks().add(b4);

            a3.getBooks().add(b1);
            a3.getBooks().add(b4);

            a4.getBooks().add(b4);

            a5.getBooks().add(b5);
            a5.getBooks().add(b4);


            session.persist(a1);
            session.persist(a2);
            session.persist(a3);

            session.persist(a4);
            session.persist(a5);

            Author deletedAuthor = a1;
            session.remove(a1);

            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}