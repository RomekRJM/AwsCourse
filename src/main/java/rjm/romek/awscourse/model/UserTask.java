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

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;

@Entity
@IdClass(UserTaskPK.class)
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

    private String answer;

    @Transient
    private Map<String, String> answers;

    @PrePersist
    public void prePersist() {
        if(done == null)
            done = Boolean.FALSE;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User book) {
        this.user = user;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task publisher) {
        this.task = task;
    }

    public Boolean getDone() {
        return done;
    }

    public void setDone(Boolean done) {
        this.done = done;
    }

    private String getAnswer() {
        return answer;
    }

    private void setAnswer(String answer) {
        this.answer = answer;
    }

    public Map<String, String> getAnswers() {
        answers = Splitter.on(",").withKeyValueSeparator("=").split(getAnswer());
        return answers;
    }

    public void setAnswers(Map<String, String> answers) {
        this.answers = answers;
        setAnswer(Joiner.on(",").withKeyValueSeparator("=").join(answers));
    }
}
