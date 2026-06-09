package com.fms.app;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class TestPasswordGenerator implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        String rawPassword = "admin123";
        String encodedPassword = encoder.encode(rawPassword);

        System.out.println("RAW PASSWORD: " + rawPassword);
        System.out.println("ENCODED PASSWORD: " + encodedPassword);
    }
}
