package com.example.app.data.repository;

import com.example.app.data.entity.Nyelvismeret;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface NyelvismeretRepository extends JpaRepository<Nyelvismeret, UUID> {
}
