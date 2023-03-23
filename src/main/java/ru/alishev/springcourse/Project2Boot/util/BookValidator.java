package ru.alishev.springcourse.Project2Boot.util;/*package myProject.util;

import myProject.dao.PersonDAO;
import myProject.models.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class BookValidator implements Validator {
    private final PersonDAO bookDAO;
    @Autowired
    public BookValidator(PersonDAO bookDAO) {
        this.bookDAO = bookDAO;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Book.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Book book=(Book) o;

        //Проверь поля на уникальность
    }
}*/
