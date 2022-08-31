package com.example.app.data.repository;

import com.example.app.data.entity.Language;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

// JpaRepository<User, Long>
public interface LanguageRepository extends JpaRepository<Language, UUID> {

    @Query("select c from Language c " +
            "where lower(c.name) like lower(concat('%', :searchTerm, '%')) ")
    List<Language> searchByName(@Param("searchTerm") String filterText); // ez egy JPQL Query


    /*List<Language> searchByNameLike(String name); // ez egy JPQL Query
     */
}
