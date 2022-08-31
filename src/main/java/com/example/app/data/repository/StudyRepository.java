package com.example.app.data.repository;

import com.example.app.data.entity.Study;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface StudyRepository extends JpaRepository<Study, UUID> {
}
