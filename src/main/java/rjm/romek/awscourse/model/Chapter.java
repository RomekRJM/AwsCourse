package rjm.romek.awscourse.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Chapter {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long chapterId;

    private String title;

    public Chapter(String title) {
        this.title = title;
    }

    public Chapter(Long chapterId, String title) {
        this.chapterId = chapterId;
        this.title = title;
    }

}
