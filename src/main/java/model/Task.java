package model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Class is a task
 *
 * @author Денис Висков
 * @version 1.0
 * @since 20.09.2020
 */
@Entity
@Table(name = "tasks")
public class Task {
    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    /**
     * Name
     */
    private String name;
    /**
     * Description
     */
    private String description;
    /**
     * Time created
     */
    private Timestamp created;
    /**
     * Done
     */
    private boolean done;
    /**
     * Categories
     */
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Category> categories = new ArrayList<>();

    public Task() {
    }

    public Task(int id, String name, String description, Timestamp created, boolean done) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.created = created;
        this.done = done;
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

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id &&
                done == task.done &&
                Objects.equals(name, task.name) &&
                Objects.equals(description, task.description) &&
                Objects.equals(created, task.created);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, created, done);
    }
}
