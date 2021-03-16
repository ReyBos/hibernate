package ru.reybos.hh.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "candidate_hh")
public class CandidateHH {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vacancy_db_id")
    private VacancyDb vacancyDb;

    public static CandidateHH of(String name) {
        CandidateHH candidateHH = new CandidateHH();
        candidateHH.setName(name);
        return candidateHH;
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

    public VacancyDb getVacancyDb() {
        return vacancyDb;
    }

    public void setVacancyDb(VacancyDb vacancyDb) {
        this.vacancyDb = vacancyDb;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CandidateHH that = (CandidateHH) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "CandidateHH{"
                + "id=" + id
                + ", name='" + name + '\''
                + "}";
    }
}
