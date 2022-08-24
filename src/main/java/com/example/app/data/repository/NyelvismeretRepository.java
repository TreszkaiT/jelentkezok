package com.example.app.data.repository;

import com.example.app.data.entity.Nyelvismeret;
import com.example.app.data.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface NyelvismeretRepository extends JpaRepository<Nyelvismeret, UUID> {

    @Query("select c from Nyelvismeret c " +
            "where lower(c.name) like lower(concat('%', :searchTerm, '%')) ")
    List<Nyelvismeret> searchByName(@Param("searchTerm")String filterText); // ez egy JPQL Query
}
