package rjm.romek.awscourse.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import rjm.romek.awscourse.util.DescriptionFragment;
import rjm.romek.awscourse.util.DescriptionParser;
import rjm.romek.awscourse.verifier.TaskVerifier;

@Entity
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="task_id")
    private Long taskId;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Chapter chapter;

    private String title;

    private String description;

    private Class<? extends TaskVerifier> verifier;

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserTask> userTasks = new HashSet<UserTask>();

    public Task () {
    }

    public Task (Chapter chapter) {
        this.chapter = chapter;
    }

    public Task(Chapter chapter, String title, String description, Class<? extends TaskVerifier> verifier) {
        this.chapter = chapter;
        this.title = title;
        this.description = description;
        this.verifier = verifier;
    }

    public Class<? extends TaskVerifier> getVerifier() {
        return verifier;
    }

    public void setVerifier(Class<? extends TaskVerifier> verifier) {
        this.verifier = verifier;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String [] getParameterNamesFromDescription() {
        return DescriptionParser.extractParameterNames(description);
    }

    public List<DescriptionFragment> getDescriptionFragments() {
        return DescriptionParser.parseDescription(description);
    }

    public Chapter getChapter() {
        return chapter;
    }

    public void setChapter(Chapter chapter) {
        this.chapter = chapter;
    }

    public Set<UserTask> getUserTasks() {
        return userTasks;
    }

    public void setUserTasks(Set<UserTask> userTasks) {
        this.userTasks = userTasks;
    }
}
