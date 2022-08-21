package com.example.app.data.repository;

import com.example.app.data.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

// db műveleteket oldja meg helyettünk, update, select...
public interface PersonRepository extends JpaRepository<Person, UUID> {

    @Query("select c from Person c " +
            "where lower(c.firstName) like lower(concat('%', :searchTerm, '%')) " +
            "or lower(c.lastName) like lower(concat('%', :searchTerm, '%'))")
    List<Person> search(@Param("searchTerm")String filterText); // ez egy JPQL Query
}


