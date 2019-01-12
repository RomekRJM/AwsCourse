package rjm.romek.awscourse.model;

import java.io.Serializable;

import javax.persistence.Embeddable;

import lombok.Data;

@Embeddable
@Data
public class UserTaskPK implements Serializable {
    private Long user;
    private Long task;
}
