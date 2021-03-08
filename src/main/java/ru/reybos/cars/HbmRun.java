package ru.reybos.cars;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.reybos.cars.model.CarModel;
import ru.reybos.cars.model.CarProducer;

public class HbmRun {
    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();

            CarModel one = CarModel.of("x1");
            CarModel two = CarModel.of("x3");
            CarModel three = CarModel.of("x5");
            CarModel four = CarModel.of("x6");
            CarModel five = CarModel.of("m1");
            session.save(one);
            session.save(two);
            session.save(three);
            session.save(four);
            session.save(five);

            CarProducer carProducer = CarProducer.of("BMW");
            carProducer.addModel(session.load(CarModel.class, one.getId()));
            carProducer.addModel(session.load(CarModel.class, two.getId()));
            carProducer.addModel(session.load(CarModel.class, three.getId()));
            carProducer.addModel(session.load(CarModel.class, four.getId()));
            carProducer.addModel(session.load(CarModel.class, five.getId()));
            session.save(carProducer);

            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}
