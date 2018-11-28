package rjm.romek.awscourse.bcrypt;

import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BcryptTestGenerator {
    @Test
    public void generate() {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);
        String hashedPassword = passwordEncoder.encode("tester");
        System.out.println(hashedPassword);
    }
}
