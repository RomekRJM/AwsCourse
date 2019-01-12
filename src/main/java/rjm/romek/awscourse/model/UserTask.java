package rjm.romek.awscourse.model;

import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Transient;

import lombok.Data;
import rjm.romek.awscourse.util.StringMapper;

@Entity
@IdClass(UserTaskPK.class)
@Data
public class UserTask {

    @Id
    @ManyToOne
    @JoinColumn(referencedColumnName = "user_id")
    private User user;

    @Id
    @ManyToOne
    @JoinColumn(referencedColumnName = "task_id")
    private Task task;

    @Column(nullable = false)
    private Boolean done;

    private String answer = "";

    @Transient
    private Map<String, String> answers;

    public UserTask() {
    }

    public UserTask(User user, Task task) {
        this.user = user;
        this.task = task;
    }

    @PrePersist
    public void prePersist() {
        if(done == null)
            done = Boolean.FALSE;

        if(answer == null)
            answer = "";
    }

    public Map<String, String> getAnswers() {
        answers = StringMapper.toMap(this.answer);
        return answers;
    }

    public void setAnswers(Map<String, String> answers) {
        this.answers = answers;
        this.answer = StringMapper.toString(this.answers);
    }
}
