package rjm.romek.awscourse.bcrypt;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import com.google.common.collect.ImmutableList;

public class UserInsertSqlGenerator {

    private List<UserInsertSql> userInsertSqls = ImmutableList.of(
            UserInsertSql.builder().withUserName("tester").withPassword("tester").build()
    );

    @Test
    public void generate() {
        userInsertSqls.stream()
                .map(s -> s.convertToSqlInsert())
                .collect(Collectors.toList())
                .forEach(System.out::println);
    }
}
