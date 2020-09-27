package model;

import javax.persistence.*;
import java.util.Objects;

/**
 * Class is an User
 *
 * @author Денис Висков
 * @version 1.0
 * @since 27.09.2020
 */
@Entity
@Table(name = "user")
public class User {
    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    /**
     * Login
     */
    private String login;
    /**
     * Task
     */
    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;

    public User() {
    }

    public User(String login, Task task) {
        this.login = login;
        this.task = task;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id &&
                Objects.equals(login, user.login) &&
                Objects.equals(task, user.task);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, task);
    }
}
