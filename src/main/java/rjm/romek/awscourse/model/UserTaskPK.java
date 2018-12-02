package rjm.romek.awscourse.model;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class UserTaskPK implements Serializable {

    private Long user;

    private Long task;

    public Long getTask() {
        return task;
    }

    public void setTask(Long task) {
        this.task = task;
    }

    public Long getUser() {
        return user;
    }

    public void setUser(Long user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserTaskPK that = (UserTaskPK) o;

        if (!user.equals(that.user)) return false;
        return task.equals(that.task);
    }

    @Override
    public int hashCode() {
        int result = user.hashCode();
        result = 31 * result + task.hashCode();
        return result;
    }
}
