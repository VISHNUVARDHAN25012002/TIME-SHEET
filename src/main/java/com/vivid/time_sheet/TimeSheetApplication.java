package com.vivid.time_sheet;

import com.vivid.time_sheet.service.DatabaseTestService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class TimeSheetApplication {

    public static void main(String[] args) {
        SpringApplication.run(TimeSheetApplication.class, args);

        // Generate BCrypt hash for a plain text password
        System.out.println(new BCryptPasswordEncoder().encode("vishnu"));
    }

    @Bean
    CommandLineRunner run(DatabaseTestService testService) {
        return args -> testService.testConnection();
    }
}
