package com.example.Quiz_app;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")  // tells Spring to use test configs
class QuizAppApplicationTests {

    @Test
    void contextLoads() {
        // context should load without DB or Security errors
    }
}
