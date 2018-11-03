package rjm.romek.awscourse;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WebSecurityConfigTest {

    private static final String TEST_USER = "tester";

    @Autowired
    private WebSecurityConfig webSecurityConfig;

    @Test
    public void userDetailsService() throws Exception {
        UserDetailsService userDetailsService = webSecurityConfig.userDetailsService();
        UserDetails userDetails = userDetailsService.loadUserByUsername(TEST_USER);

        assertEquals(TEST_USER, userDetails.getUsername());
    }

}