package ru.reybos.hql;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.reybos.hql.model.Candidate;

public class HbmRunCandidate {
    private static final Logger LOG = LoggerFactory.getLogger(HbmRunCandidate.class.getName());

    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();

            Candidate candidate1 = Candidate.of("Ivan", 0, 100000);
            Candidate candidate2 = Candidate.of("Olga", 5, 500000);
            Candidate candidate3 = Candidate.of("Marina", 2, 1000);

            session.save(candidate1);
            session.save(candidate2);
            session.save(candidate3);

            Query query = session.createQuery("from Candidate");
            LOG.debug("----------Добавлено три кандидата----------");
            for (Object candidate : query.list()) {
                LOG.debug(candidate.toString());
            }
            LOG.debug("---------------------");

            query = session.createQuery("from Candidate can where can.id = :cid");
            query.setParameter("cid", candidate1.getId());
            LOG.debug("----------Найден кандидат по айди----------");
            LOG.debug(query.uniqueResult().toString());
            LOG.debug("---------------------");

            query = session.createQuery("from Candidate can where can.name = :cname");
            query.setParameter("cname", candidate2.getName());
            LOG.debug("----------Найден кандидат по имени----------");
            LOG.debug(query.uniqueResult().toString());
            LOG.debug("---------------------");

            session.createQuery(
                    "update Candidate can set can.salary = :newSalary where can.id = :cid"
            )
                    .setParameter("newSalary", 99999)
                    .setParameter("cid", candidate3.getId())
                    .executeUpdate();
            LOG.debug("----------Изменен кандидат----------");
            LOG.debug(session.get(Candidate.class, candidate3.getId()).toString());
            LOG.debug("---------------------");

            session.createQuery("delete from Candidate can where can.id = :cid")
                    .setParameter("cid", candidate1.getId())
                    .executeUpdate();
            query = session.createQuery("from Candidate");
            LOG.debug("----------Удален кандидат----------");
            for (Object candidate : query.list()) {
                LOG.debug(candidate.toString());
            }
            LOG.debug("---------------------");

            session.getTransaction().rollback();
            session.close();
        }  catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}
