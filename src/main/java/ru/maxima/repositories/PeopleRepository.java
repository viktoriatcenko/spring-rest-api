package ru.maxima.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.maxima.models.Person;

import java.util.List;

@Repository
public interface PeopleRepository extends JpaRepository<Person, Long> {
    List<Person> findAllByNameLike(String like); // .get(0)

    List<Person> findByName(String name);

    List<Person> findByEmail(String email);

    List<Person> findByNameOrEmail(String name, String email);

    List<Person> findByNameStartingWith(String startingWith);

    List<Person> findByNameStartingWithOrderByAge(String name);
}
