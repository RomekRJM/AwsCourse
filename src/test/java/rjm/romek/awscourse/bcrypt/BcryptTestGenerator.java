package rjm.romek.awscourse.bcrypt;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.google.common.collect.ImmutableList;

public class BcryptTestGenerator {

    private List<String> passwords = ImmutableList.of("tester", "poop");

    @Test
    public void generate() {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);
        passwords.stream()
                .map(x -> passwordEncoder.encode(x))
                .collect(Collectors.toList())
                .forEach(System.out::println);
    }
}
