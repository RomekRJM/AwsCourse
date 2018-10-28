package rjm.romek.awscourse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("rjm.romek.awscourse")
public class AwsCourseApplication {
	public static void main(String[] args) {
		SpringApplication.run(AwsCourseApplication.class, args);
	}
}
