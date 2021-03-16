package ru.reybos.hh.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "vacancy_db")
public class VacancyDb {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Vacancy> vacancies = new ArrayList<>();

    public static VacancyDb of(String name) {
        VacancyDb vacancyDb = new VacancyDb();
        vacancyDb.setName(name);
        return vacancyDb;
    }

    public void addVacancy(Vacancy vacancy) {
        this.vacancies.add(vacancy);
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

    public List<Vacancy> getVacancies() {
        return vacancies;
    }

    public void setVacancies(List<Vacancy> vacancies) {
        this.vacancies = vacancies;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        VacancyDb vacancyDb = (VacancyDb) o;
        return id == vacancyDb.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "VacancyDb{"
                + "id=" + id
                + ", name='" + name + '\''
                + "}";
    }
}
