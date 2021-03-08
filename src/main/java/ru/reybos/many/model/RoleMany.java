package ru.reybos.many.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "j_role_many")
public class RoleMany {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserMany> users = new ArrayList<>();

    public static RoleMany of(String name) {
        RoleMany roleMany = new RoleMany();
        roleMany.name = name;
        return roleMany;
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

    public List<UserMany> getUsers() {
        return users;
    }

    public void setUsers(List<UserMany> users) {
        this.users = users;
    }

    public void addUser(UserMany u) {
        this.users.add(u);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RoleMany roleMany = (RoleMany) o;
        return id == roleMany.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}