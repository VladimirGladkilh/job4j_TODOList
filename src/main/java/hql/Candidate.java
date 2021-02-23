package hql;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "candidates")
public class Candidate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private String experience;

    private int salary;

    @OneToOne(fetch = FetchType.LAZY)
    private PostBase postBase;

    public Candidate() {
    }

    public static Candidate of(int id, String name, String experience, int salary, PostBase postBase) {
        Candidate candidate = new Candidate();
        candidate.id = id;
        candidate.name = name;
        candidate.experience = experience;
        candidate.salary = salary;
        candidate.postBase = postBase;
        return  candidate;
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

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public PostBase getPostBase() {
        return postBase;
    }

    public void setPostBase(PostBase postBase) {
        this.postBase = postBase;
    }
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Candidate candidate = (Candidate) o;
        return id == candidate.id;
    }

    @Override
    public String toString() {
        return "Candidate{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", experience='" + experience + '\'' +
                ", salary=" + salary +
                '}';
    }
}
