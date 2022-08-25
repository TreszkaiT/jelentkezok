package com.example.app.data.repository;

import com.example.app.data.entity.PersonNyelv;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PersonNyelvRepository extends JpaRepository<PersonNyelv, UUID> {
}
