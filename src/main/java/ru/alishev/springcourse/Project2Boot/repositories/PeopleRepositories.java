package ru.alishev.springcourse.Project2Boot.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.alishev.springcourse.Project2Boot.models.Person;

@Repository
public interface PeopleRepositories extends JpaRepository<Person,Integer> {



     Person findByFullName(String fullName);//для валидатора

}
