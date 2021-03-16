package ru.reybos.hh;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.reybos.hh.model.CandidateHH;
import ru.reybos.hh.model.Vacancy;
import ru.reybos.hh.model.VacancyDb;

public class HbmInit {
    private static final Logger LOG = LoggerFactory.getLogger(HbmInit.class.getName());

    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();

            CandidateHH candidateHH = CandidateHH.of("Timur");
            VacancyDb vacancyDb = VacancyDb.of("Baza");
            candidateHH.setVacancyDb(vacancyDb);
            Vacancy vacancy1 = Vacancy.of("Vakansiay 1");
            Vacancy vacancy2 = Vacancy.of("Vakansiay 2");
            vacancyDb.addVacancy(vacancy1);
            vacancyDb.addVacancy(vacancy2);

            session.save(candidateHH);
            session.save(vacancyDb);

            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            LOG.error("Ошибка", e);
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}