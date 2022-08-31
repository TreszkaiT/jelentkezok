package com.example.app.data.repository;

import com.example.app.data.entity.Language;
import com.example.app.data.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.UUID;

// db műveleteket oldja meg helyettünk, update, select...
public interface PersonRepository extends JpaRepository<Person, UUID> {

    @Query("select c from Person c " +
            "where lower(c.firstName) like lower(concat('%', :searchTerm, '%')) " +
            "or lower(c.lastName) like lower(concat('%', :searchTerm, '%'))")
    List<Person> searchByName(@Param("searchTerm")String filterText); // ez egy JPQL Query

   /* @Query("select c from Person c " +
            "where lower(c.firstName) like lower(concat('%', :searchTerm, '%')) " +
            "or lower(c.lastName) like lower(concat('%', :searchTerm, '%'))" +
            "or c.language in :lang")
    List<Person> searchByName(@Param("searchTerm")String filterText, LocalDate dt, String lang); // ez egy JPQL Query*/

    @Query("select c from Person c where c.language=?1")
    List<Person> searchByLanguageId(UUID filterText); // ez egy JPQL Query

    @Query("select c from Person c where c.language=?1")
    Set<Person> searchByLanguage(Set<Language> filterText); // ez egy JPQL Query

    @Query("select c from Person c where c.bornDate=?1")
    List<Person> searchByDate(LocalDate filterText);

    List<Person> searchByFirstNameLikeOrLastNameLike(String firstName, String lastName);

    List<Person> searchByFirstNameLikeOrLastNameLikeOrBornDateLike(String firstName, String lastName, LocalDate bornDate);

    List<Person> searchByFirstNameLikeOrLastNameLikeAndBornDate(String firstName, String lastName, LocalDate bornDate);

    List<Person> findAllByLanguageIn(Set<Language> language);

   /* @Query("select u from User u where u.firstname = :firstname or u.lastname = :lastname")
    Person findByLastnameOrFirstname(@Param("lastname") String lastname,
                                   @Param("firstname") String firstname);*/

    /*@Query("select u from #{#entityName} u where u.lastname = ?1")
    List<Person> findByLastname(String lastname);*/
}


