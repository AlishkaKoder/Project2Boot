package ru.alishev.springcourse.Project2Boot.dao;/*package myProject.dao;

import myProject.models.Book;
import myProject.models.Person;
import myProject.test.RowMapperBook;
import myProject.test.RowMapperPerson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class BookDAO {
    private final JdbcTemplate jdbcTemplate;
    private final RowMapperBook rowMapperBook;
    private final RowMapperPerson rowMapperPerson;
    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate, RowMapperBook rowMapperBook, RowMapperPerson rowMapperPerson) {
        this.jdbcTemplate = jdbcTemplate;
        this.rowMapperBook = rowMapperBook;
        this.rowMapperPerson = rowMapperPerson;
    }

    public List<Book> showAll(){
        return jdbcTemplate.query("SELECT * FROM Book", rowMapperBook);
    }
    public Book show(int id){
        return jdbcTemplate.query("SELECT * FROM Book WHERE book_id=?",new Object[]{id}, rowMapperBook).stream().findAny().orElse(null);
    }
    public void save(Book book){
        jdbcTemplate.update("INSERT INTO Book(name,author,year) VALUES (?,?,?)",book.getName(),book.getAuthor(),book.getYear());
    }
    public void update(Book updatedbook,int id){
        jdbcTemplate.update("UPDATE Book SET name=?,author=?,year=? WHERE book_id=?", updatedbook.getName(),updatedbook.getAuthor(),updatedbook.getYear(),id);
    }
    public void delete(int id){
        jdbcTemplate.update("DELETE FROM Book WHERE book_id=?",id);
    }

    //метод getBookOwner хотим получить владельца книги если он есть
    public Optional<Person> getBookOwner(int id){//Person.* означает что мы выбираем все поля из таблицы Person, а не из соединения 2-х табиц(но это не точно)
        return jdbcTemplate.query("SELECT Person.* FROM Book JOIN Person ON Book.person_id=Person.person_id Where book_id=?",new Object[]{id},rowMapperPerson).stream().findAny();
    }
    //release
    public void release(int id){
        jdbcTemplate.update("UPDATE Book SET person_id=null WHERE book_id=?",id);
    }
    //assign
    public void assign(int id,Person selectedPerson){
        jdbcTemplate.update("UPDATE Book SET person_id=? WHERE book_id=?",selectedPerson.getId(),id);
    }
}*/
