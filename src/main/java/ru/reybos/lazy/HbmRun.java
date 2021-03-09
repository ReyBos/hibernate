package ru.reybos.lazy;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.reybos.lazy.model.Category;
import ru.reybos.lazy.model.Task;

public class HbmRun {
    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();

            Category category = Category.of("Consulting");
            session.save(category);

            Task task1 = Task.of("Consultation of Hibernate", category);
            Task task2 = Task.of("Consultation of Spring", category);
            Task task3 = Task.of("Consultation of Servlet", category);
            session.save(task1);
            session.save(task2);
            session.save(task3);

            session.getTransaction().commit();
            session.close();
        }  catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}
