package com.omkar.jobaiassistant.repository;

import com.omkar.jobaiassistant.entity.PendingQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PendingQuestionRepository extends JpaRepository<PendingQuestion, Long> {

    List<PendingQuestion> findByUserId(Long userId);

    List<PendingQuestion> findByAnsweredFalse();
}