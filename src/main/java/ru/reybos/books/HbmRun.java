package ru.reybos.books;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.reybos.books.model.Author;
import ru.reybos.books.model.BookTest;

public class HbmRun {
    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();

            Author author1 = Author.of("Pushkin");
            Author author2 = Author.of("Goncharov");

            BookTest book1 = BookTest.of("War and peace");
            book1.getAuthors().add(author1);
            book1.getAuthors().add(author2);

            BookTest book2 = BookTest.of("Idiot");
            book2.getAuthors().add(author1);

            session.persist(book1);
            session.persist(book2);

            BookTest book = session.get(BookTest.class, book1.getId());
            session.remove(book);

            session.getTransaction().commit();
            session.close();
        }  catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}
