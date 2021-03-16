package ru.reybos.hql;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.reybos.hql.model.Account;
import ru.reybos.hql.model.Book;
import ru.reybos.hql.model.Student;

public class HbmAccountInit {
    private static final Logger LOG = LoggerFactory.getLogger(HbmRunCandidate.class.getName());

    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();

            Student student = Student.of("Иванов Иван", 20, "Москва");
            Account account = Account.of("root");
            student.setAccount(account);
            Book book = Book.of("Отцы и дети", "АСТ");
            Book book2 = Book.of("Идиот", "ЭКСМО");
            account.addBook(book);
            account.addBook(book2);

            session.save(student);
            session.save(account);

            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            LOG.error("Ошибка", e);
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}
