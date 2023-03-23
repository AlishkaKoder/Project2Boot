package ru.alishev.springcourse.Project2Boot.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ru.alishev.springcourse.Project2Boot.models.Book;

import java.util.List;

@Repository
public interface BooksRepositories extends JpaRepository<Book,Integer>, PagingAndSortingRepository<Book,Integer> {

    //Книг может быть много и они могут не поместиться на одной странице в браузере,
    // для этого метод контроллера должен уметь выдавать не только все книги разом,
    // но и разбивать их на страницы





    //Сортировка книг по году(year) метод контроллера должен уметь выдавать их в отсортированном порядке




    List<Book> findByNameStartingWith(String name); //Ищет книгу по начальным буквам

}
