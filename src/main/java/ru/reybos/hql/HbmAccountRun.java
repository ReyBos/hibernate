package ru.reybos.hql;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.reybos.hql.model.Student;

public class HbmAccountRun {
    private static final Logger LOG = LoggerFactory.getLogger(HbmAccountRun.class.getName());

    public static void main(String[] args) {
        Student rsl = null;

        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry)
                    .buildMetadata()
                    .buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();

//            rsl = session.createQuery(
//                    "select s from Student s where s.id = :sId", Student.class
//            )
//                    .setParameter("sId", 5)
//                    .uniqueResult();

            rsl = session.createQuery(
                    "select distinct st from Student st "
                            + "join fetch st.account a "
                            + "join fetch a.books b "
                            + "where st.id = :sId", Student.class
            ).setParameter("sId", 5).uniqueResult();

            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            LOG.error("error", e);
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }

        LOG.debug(rsl.getAccount().getBooks().toString());
    }
}
