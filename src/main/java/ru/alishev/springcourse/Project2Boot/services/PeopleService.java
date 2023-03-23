package ru.alishev.springcourse.Project2Boot.services;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.alishev.springcourse.Project2Boot.models.Book;
import ru.alishev.springcourse.Project2Boot.models.Person;
import ru.alishev.springcourse.Project2Boot.repositories.BooksRepositories;
import ru.alishev.springcourse.Project2Boot.repositories.PeopleRepositories;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PeopleService {

    private final PeopleRepositories peopleRepositories;
    private final BooksRepositories booksRepositories;

    @Autowired
    public PeopleService(PeopleRepositories peopleRepositories, BooksRepositories booksRepositories) {
        this.peopleRepositories = peopleRepositories;
        this.booksRepositories = booksRepositories;
    }

    public List<Person> findAll(){
        return peopleRepositories.findAll();
    }

    public Person findOne(int id){
        Optional<Person> foundPerson = peopleRepositories.findById(id);
        return foundPerson.orElse(null);
    }

    public Person findOne(String name){
        Person foundPerson = peopleRepositories.findByFullName(name);
        return foundPerson;
    }

    @Transactional
    public void save(Person person){
        peopleRepositories.save(person);
    }

    @Transactional
    public void update(int id,Person personUpdated){
        personUpdated.setId(id);
        peopleRepositories.save(personUpdated);
    }

    @Transactional
    public void delete(int id){
        peopleRepositories.deleteById(id);
    }

    @Transactional // Без аннотации он не даёт подгружать зависимые сущности, видимо только чтение ему не хватает
    public List<Book> getBooksByPersonId(int id){
        Optional<Person> person = peopleRepositories.findById(id);

        if(person.isPresent()){
            Hibernate.initialize(person.get().getBooks());
            //логика просрочки книги
            person.get().getBooks().forEach(book -> {
                long p1=book.getTakenAt().getTime();
                long p2 =new Date().getTime();
                long diffMillies = Math.abs(p1- p2);
                //864000000 миллисек = 10 дней
                if (864000000<diffMillies)
                    book.setOverdue(true); //книга просрочена
            });

            return person.get().getBooks();
        }
        //////////////////////////////////////////////////////////
        return Collections.emptyList();
    }



}
