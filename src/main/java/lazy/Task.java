package lazy;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String description;
    @ManyToOne
    @JoinColumn(name = "categoryLazy_id")
    private CategoryLazy categoryLazy;

    public static Task of(String description, CategoryLazy categoryLazy) {
        Task task = new Task();
        task.description = description;
        task.categoryLazy = categoryLazy;
        return task;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CategoryLazy getCategory() {
        return categoryLazy;
    }

    public void setCategory(CategoryLazy categoryLazy) {
        this.categoryLazy = categoryLazy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", category=" + categoryLazy +
                '}';
    }
}