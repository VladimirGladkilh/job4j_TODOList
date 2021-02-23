package hql;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;

public class HbmRubCandidate {
    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();

            /* Init
            Candidate one = Candidate.of(1,"Alex", "Junior", 50000);
            Candidate two = Candidate.of(2, "Nikolay", "Middle", 100000);
            Candidate three = Candidate.of(3,"Nikita", "Senior", 1300000);

            session.save(one);
            session.save(two);
            session.save(three);
            */
            //*  Select all
            Query query = session.createQuery("from Candidate ");
            for (Object st : query.list()) {
                System.out.println(st);
            }
            //*  Select by id
            Query queryById = session.createQuery("from Candidate c where c.id=:id")
                    .setParameter("id", 2);
            System.out.println(queryById.uniqueResult());
            //*/
            //* select by name
            Query queryByName = session.createQuery("from Candidate c where lower(c.name)=:name")
                    .setParameter("name", "Alex".toLowerCase());
            System.out.println(queryByName.uniqueResult());
            // */

            //* Update
            Query queryUpdate = session.createQuery(
                    "update Candidate s set s.name = :name, s.experience = :exp, " +
                            "s.salary = : salary where s.id = :fId"
            );
            queryUpdate.setParameter("name", "Alex");
            queryUpdate.setParameter("exp", "Middle");
            queryUpdate.setParameter("salary", 75000);
            queryUpdate.setParameter("fId", 1);
            queryUpdate.executeUpdate();
            //*/

            //* delete
            session.createQuery("delete from Candidate where id = :fId")
                    .setParameter("fId", 3)
                    .executeUpdate();
            //*/

            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }

}
