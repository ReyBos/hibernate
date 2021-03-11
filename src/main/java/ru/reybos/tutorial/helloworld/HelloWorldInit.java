package ru.reybos.tutorial.helloworld;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HelloWorldInit {
    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();

            Message message = new Message();
            message.setText("Hello world");
            /*
            Помещаем объект из памяти приложения (transient instance) в хранилище, делая его
            хранимым. Теперь Hibernate знает, что вы хотели бы сохранить эти данные, но он не
            обязательно обратится к базе данных в тот же момент.
             */
            session.persist(message);

            /*
            Завершаем транзакцию. Hibernate автоматически проверит контекст хранения и выполнит
            необходимую SQL-инструкцию
             */
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}
