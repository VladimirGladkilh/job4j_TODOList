package model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Calendar;

@Entity
@Table(name = "item")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String description;
    private Timestamp created = new Timestamp(Calendar.getInstance().getTimeInMillis());
    private Boolean done = false;
    @ManyToOne
    @JoinColumn(name = "userid")
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    public Boolean getDone() {
        return done;
    }

    public void setDone(Boolean done) {
        this.done = done;
    }

    public Item(int id, String description, Timestamp created, Boolean done, User user) {
        createItem(id, description, created, done, user);
    }

    public Item(String description, Boolean done, User user) {
        createItem(0, description, new Timestamp(Calendar.getInstance().getTimeInMillis()), done, user);
    }

    public Item(String description, User user) {
        createItem(0, description, new Timestamp(Calendar.getInstance().getTimeInMillis()), false, user);
    }

    public Item(int id, String description, boolean done, User user) {
        createItem(id, description, new Timestamp(Calendar.getInstance().getTimeInMillis()), done, user);
    }

    public Item() {}

    private void createItem(int id, String description, Timestamp created, Boolean done, User user) {
        this.id = id;
        this.description = description;
        this.created = created;
        this.done = done;
        this.user = user;
    }
}
