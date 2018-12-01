package rjm.romek.awscourse.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Chapter {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long chapterId;

    private String title;

    public Chapter() {
    }

    public Chapter(String title) {
        this.title = title;
    }

    public Chapter(Long chapterId, String title) {
        this.chapterId = chapterId;
        this.title = title;
    }

    public Long getChapterId() {
        return chapterId;
    }

    public void setChapterId(Long chapterId) {
        this.chapterId = chapterId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
