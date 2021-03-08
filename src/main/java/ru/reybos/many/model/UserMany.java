package ru.reybos.many.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "j_user_many")
public class UserMany {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;

    public static UserMany of(String name) {
        UserMany userMany = new UserMany();
        userMany.name = name;
        return userMany;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserMany userMany = (UserMany) o;
        return id == userMany.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}