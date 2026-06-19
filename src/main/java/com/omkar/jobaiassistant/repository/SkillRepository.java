package com.omkar.jobaiassistant.repository;

import com.omkar.jobaiassistant.entity.Skill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SkillRepository extends JpaRepository<Skill, Long> {

    List<Skill> findByUserId(Long userId);

    boolean existsByUserIdAndSkillNameIgnoreCase(Long userId, String skillName);
}