package com.example.Quiz_app.repository;

import com.example.Quiz_app.model.QuizResult;
import com.example.Quiz_app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface QuizResultRepository extends JpaRepository<QuizResult, Long> {

    List<QuizResult> findByUserOrderByDateTakenDesc(User user);

}
