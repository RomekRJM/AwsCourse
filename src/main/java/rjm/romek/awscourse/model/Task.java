package rjm.romek.awscourse.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import rjm.romek.awscourse.validator.TaskValidator;

@Entity
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long taskId;

    @ManyToOne
    private Chapter chapter;

    private String title;
    private String description;
    private Boolean done;
    private Class<? extends TaskValidator> validator;

    public Task () {
    }

    public Task(Chapter chapter, String title, String description, Boolean done, Class<? extends TaskValidator> validator) {
        this.chapter = chapter;
        this.title = title;
        this.description = description;
        this.done = done;
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

    public Boolean getDone() {
        return done;
    }

    public void setDone(Boolean done) {
        this.done = done;
    }

    public Chapter getChapter() {
        return chapter;
    }

    public void setChapter(Chapter chapter) {
        this.chapter = chapter;
    }

}
