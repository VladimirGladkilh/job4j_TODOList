package hql;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "posthql")
public class PostHql {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String postName;

    public PostHql() {
    }

    public static PostHql of(int id, String postName) {
        PostHql postHql = new PostHql();
        postHql.id = id;
        postHql.postName = postName;
        return postHql;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPostName() {
        return postName;
    }

    public void setPostName(String postName) {
        this.postName = postName;
    }

    @Override
    public String toString() {
        return "PostHql{" +
                "id=" + id +
                ", postName='" + postName + '\'' +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PostHql postHql = (PostHql) o;
        return id == postHql.id;
    }
}
