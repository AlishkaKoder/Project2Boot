package ru.alishev.springcourse.Project2Boot.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.alishev.springcourse.Project2Boot.models.Person;
import ru.alishev.springcourse.Project2Boot.services.PeopleService;
import ru.alishev.springcourse.Project2Boot.util.PersonValidator;

import javax.validation.Valid;

@Controller
@RequestMapping("/people")
public class PeopleController {
    private final PeopleService peopleService;
    private final PersonValidator personValidator;

    @Autowired
    public PeopleController(PeopleService peopleService, PersonValidator personValidator) {
        this.peopleService = peopleService;
        this.personValidator = personValidator;
    }

    //Показывает список людей
    @GetMapping()
    public String showAll(Model model){
        model.addAttribute("people",peopleService.findAll());

        return "people/showAll";
    }

    //Показывает страницу человека по его id и список его кинг
    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model){
        model.addAttribute("person",peopleService.findOne(id));
        model.addAttribute("books",peopleService.getBooksByPersonId(id));

        return "people/show";
    }

    //возвращает html страницу для создания нового человека
    @GetMapping("/new")
    public String newPerson(@ModelAttribute("person") Person person){
        return "people/new";
    }

    //Получает данные для создания нового человека (newPerson()) и помещает человека в БД
    @PostMapping()
    public String create(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult){
        personValidator.validate(person,bindingResult);
        if(bindingResult.hasErrors())
            return "people/new";

        peopleService.save(person);
        return "redirect:/people";
    }

    //возвращает html страницу для редактирования профиля человека
    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id){
        model.addAttribute("person",peopleService.findOne(id));
        return "people/edit";
    }

    //получает данные с html страницы для редактирования
    // профиля человека(метод edit()) и обновляет старые данные человека на свежие
    @PatchMapping("/{id}")
    public String update(@ModelAttribute("person") @Valid Person person,BindingResult bindingResult, @PathVariable("id") int id){
        //personValidator.validate(person,bindingResult); убрал так как мешает редактировать год у человека говоря что такое ФИО существует
        if(bindingResult.hasErrors())
            return "people/edit";

        peopleService.update(id,person);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        peopleService.delete(id);
        return "redirect:/people";
    }
}
