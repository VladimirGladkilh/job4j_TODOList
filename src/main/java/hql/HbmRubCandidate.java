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

            /* Init posts
            PostBase postBase = PostBase.of("HH-ru");
            PostHql postHql = PostHql.of(1, "Java developer");
            session.save(postHql);
            PostHql postHql2 = PostHql.of(2, "QA analyzer");
            session.save(postHql2);
            PostHql postHql3 = PostHql.of(3, "Devops");
            session.save(postHql3);

            postBase.addPostHql(postHql);
            postBase.addPostHql(postHql2);
            postBase.addPostHql(postHql3);
            session.saveOrUpdate(postBase);

            PostBase postBase2 = PostBase.of("SuperJob");
            PostHql postHql4 = PostHql.of(4, "Java developer");
            session.save(postHql4);
            PostHql postHql5 = PostHql.of(5, "QA analyzer");
            session.save(postHql5);
            PostHql postHql6 = PostHql.of(6, "Devops");
            session.save(postHql6);

            postBase2.addPostHql(postHql4);
            postBase2.addPostHql(postHql5);
            postBase2.addPostHql(postHql6);
            session.saveOrUpdate(postBase2);
            */
            /* Init
            Candidate one = Candidate.of(1,"Alex", "Junior", 50000, postBase);
            Candidate two = Candidate.of(2, "Nikolay", "Middle", 100000, postBase2);
            Candidate three = Candidate.of(3,"Nikita", "Senior", 1300000, postBase);

            session.save(one);
            session.save(two);
            session.save(three);

             */

            Candidate rsl = session.createQuery(
                    "select distinct ct from Candidate ct "
                            + "join fetch ct.postBase pb "
                            + "join fetch pb.postHqls ph "
                            + "where ct.id = :sId", Candidate.class
            ).setParameter("sId", 6).uniqueResult();
            System.out.println(rsl);




            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }

}
