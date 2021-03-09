package ru.reybos.lazycars;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.reybos.lazycars.model.Model;
import ru.reybos.lazycars.model.Producer;

public class HbmInit {
    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();

            Producer producer = Producer.of("BMW");
            session.save(producer);

            Model model1 = Model.of("x1", producer);
            Model model2 = Model.of("x3", producer);
            Model model3 = Model.of("x5", producer);
            session.save(model1);
            session.save(model2);
            session.save(model3);

            session.getTransaction().commit();
            session.close();
        }  catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}
