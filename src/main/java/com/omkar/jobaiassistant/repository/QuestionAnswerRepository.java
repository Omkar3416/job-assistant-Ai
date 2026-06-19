package com.omkar.jobaiassistant.repository;

import com.omkar.jobaiassistant.entity.QuestionAnswer;
import com.omkar.jobaiassistant.entity.QuestionType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionAnswerRepository extends JpaRepository<QuestionAnswer, Long> {

    List<QuestionAnswer> findByUserId(Long userId);

    List<QuestionAnswer> findByQuestionType(QuestionType questionType);
}