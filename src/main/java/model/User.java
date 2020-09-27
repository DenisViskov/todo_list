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
@Table(name = "users")
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
     * Password
     */
    private String password;
    /**
     * Task
     */
    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;

    public User() {
    }

    public User(int id, String login, String password, Task task) {
        this.id = id;
        this.login = login;
        this.password = password;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
                Objects.equals(password, user.password) &&
                Objects.equals(task, user.task);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, password, task);
    }
}
