package com.example.app.data.repository;

import com.example.app.data.entity.Language;
import com.example.app.data.entity.Person;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.criteria.Predicate;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

// db műveleteket oldja meg helyettünk, update, select...
public interface PersonRepository extends JpaRepository<Person, UUID>, JpaSpecificationExecutor<Person> {           // 1. JpaSpecificationExecutor : kézzel összerakhatok parancsokat. Ez használja a Specification interfacet lentebb

    @Query("select c from Person c " +
            "where lower(c.firstName) like lower(concat('%', :searchTerm, '%')) " +
            "or lower(c.lastName) like lower(concat('%', :searchTerm, '%'))")
    List<Person> searchByName(@Param("searchTerm") String filterText); // ez egy JPQL Query

   /* @Query("select c from Person c " +
            "where lower(c.firstName) like lower(concat('%', :searchTerm, '%')) " +
            "or lower(c.lastName) like lower(concat('%', :searchTerm, '%'))" +
            "or c.language in :lang")
    List<Person> searchByName(@Param("searchTerm")String filterText, LocalDate dt, String lang); // ez egy JPQL Query*/

    default List<Person> complexSearch(String person, LocalDate date, String lang) {
        if ((null == person || person.isEmpty()) && null == date && (null == lang || lang.isEmpty())) {
            return findAll();
        }
        Specification<Person> condition = (root, query, builder) -> {   // Lambda                                               // 3. egy Specification interface-t, azaz a metódusati implementálhatjuk, de mivel csak 1 db metódusa van, és így csak 1-et kell implementálni -> az új java már megengedi, hogy egy Lambda-ban implementáljuk ezt itt. Tehát maga a teljes lambda =-jeltől kezdve alul a  return findAll-ig a teljes egytelen metódust jelenti
                                                                                                                                // megkapjuk a root-ot: ez mindig az az Entitás, amivel dolgozunk, ez a Person most
                                                                                                                                // megkapjuk a query-t
                                                                                                                                // és a builder-t, amivel összállítjuk, hogy milyen paramétereket szeretnénk
            List<Predicate> predicates = new ArrayList<>();                                                                     // 4. a Specification interface-nek ez a toPredicate() egyetlen metódusa van, amit implementálunk itt
            if (null != person && !person.isEmpty()) {
                predicates.add(builder.like(builder.upper(root.get("firstName")), "%" + person.toUpperCase() + "%"));   // 5. a nagybetűs like-al megkeresett personokat átadja a perdicates-nak
                predicates.add(builder.like(builder.upper(root.get("lastName")), "%" + person.toUpperCase() + "%"));
            }
            if (null != date) {
                predicates.add(builder.greaterThanOrEqualTo(root.get("bornDate"), date));
            }
            if (null != lang && !lang.isEmpty()) {
                predicates.add(builder.like(builder.upper(root.join("language").get("name")), "%" + lang.toUpperCase() + "%"));
            }

            return builder.or(predicates.toArray(new Predicate[predicates.size()]));                                            // 6. és a predicates a végén összekapcsolja ezeket or-al
        };
        return findAll(condition);                                                                                              // 2. ekkor a findAll-ba condition-ok tölthetőek, és ő készíti el a tényleges, végső SQL utasítást
    }

    @Query("select c from Person c where c.language=?1")
    List<Person> searchByLanguageId(UUID filterText); // ez egy JPQL Query

    @Query("select c from Person c where c.language=?1")
    Set<Person> searchByLanguage(Set<Language> filterText); // ez egy JPQL Query

    @Query("select c from Person c where c.bornDate=?1")
    List<Person> searchByDate(LocalDate filterText);

    List<Person> searchByFirstNameLikeOrLastNameLike(String firstName, String lastName);

    List<Person> searchByFirstNameLikeOrLastNameLikeOrBornDateLike(String firstName, String lastName, LocalDate bornDate);

    List<Person> searchByFirstNameLikeOrLastNameLikeAndBornDateEquals(String firstName, String lastName, LocalDate bornDate);

    List<Person> findAllByLanguageIn(Set<Language> language);

    List<Person> findAllByLanguageInOrFirstNameLikeOrLastNameLike(Set<Language> language, String firstName, String lastName);

   /* @Query("select u from User u where u.firstname = :firstname or u.lastname = :lastname")
    Person findByLastnameOrFirstname(@Param("lastname") String lastname,
                                   @Param("firstname") String firstname);*/

    /*@Query("select u from #{#entityName} u where u.lastname = ?1")
    List<Person> findByLastname(String lastname);*/
}


