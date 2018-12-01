package rjm.romek.awscourse.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import rjm.romek.awscourse.validator.TaskValidator;

@Entity
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long taskId;

    @ManyToOne
    private Chapter chapter;

    private String title;

    private String description;

    private Class<? extends TaskValidator> validator;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<User> users;

    public Task () {
    }

    public Task(Chapter chapter, String title, String description, Class<? extends TaskValidator> validator) {
        this.chapter = chapter;
        this.title = title;
        this.description = description;
        this.validator = validator;
    }

    public Class<? extends TaskValidator> getValidator() {
        return validator;
    }

    public void setValidator(Class<? extends TaskValidator> validator) {
        this.validator = validator;
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

    public Chapter getChapter() {
        return chapter;
    }

    public void setChapter(Chapter chapter) {
        this.chapter = chapter;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

}
