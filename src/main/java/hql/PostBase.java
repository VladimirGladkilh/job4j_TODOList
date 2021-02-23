package hql;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "postbase")
public class PostBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String baseName;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PostHql> postHqls = new ArrayList<>();

    public PostBase() {

    }

    public static PostBase of(String baseName) {
        PostBase postBase = new PostBase();
        postBase.baseName = baseName;
        return postBase;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBaseName() {
        return baseName;
    }

    public void setBaseName(String baseName) {
        this.baseName = baseName;
    }

    public List<PostHql> getPostHqls() {
        return postHqls;
    }

    public void setPostHqls(List<PostHql> postHqls) {
        this.postHqls = postHqls;
    }

    @Override
    public String toString() {
        return "PostBase{" +
                "id=" + id +
                ", baseName='" + baseName + '\'' +
                '}';
    }

    public void addPostHql(PostHql postHql) {
        this.postHqls.add(postHql);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PostBase postBase = (PostBase) o;
        return id == postBase.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
