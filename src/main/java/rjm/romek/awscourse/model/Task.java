package rjm.romek.awscourse.model;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
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

import lombok.Data;
import rjm.romek.awscourse.util.DescriptionFragment;
import rjm.romek.awscourse.util.DescriptionParser;
import rjm.romek.awscourse.verifier.TaskVerifier;

@Entity
@Data
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "task_id")
    private Long taskId;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Chapter chapter;

    @Column(length=512)
    private String description;

    private String page;

    private Class<? extends TaskVerifier> verifier;

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserTask> userTasks = new HashSet<UserTask>();

    public Task() {
    }

    public Task(Chapter chapter) {
        this.chapter = chapter;
    }

    public Task(Chapter chapter, String description, Class<? extends TaskVerifier> verifier) {
        this.chapter = chapter;
        this.description = description;
        this.verifier = verifier;
    }

    public Map<String, String> getParametersFromDescription() {
        return DescriptionParser.extractParameters(description);
    }

    public List<DescriptionFragment> getDescriptionFragments() {
        return DescriptionParser.parseDescription(description);
    }
}
