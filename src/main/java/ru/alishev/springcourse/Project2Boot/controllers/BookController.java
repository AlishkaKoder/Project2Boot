package ru.alishev.springcourse.Project2Boot.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.alishev.springcourse.Project2Boot.models.Book;
import ru.alishev.springcourse.Project2Boot.models.Person;
import ru.alishev.springcourse.Project2Boot.services.BookService;
import ru.alishev.springcourse.Project2Boot.services.PeopleService;

import javax.validation.Valid;

@Controller
@RequestMapping("/book")
public class BookController {

    private final BookService bookService;
    private final PeopleService peopleService;//он нужен так как мы через него в методе show вытаскиваем людей используя метод showAll

    @Autowired
    public BookController(BookService bookService, PeopleService peopleService) {
        this.bookService = bookService;
        this.peopleService = peopleService;
    }

    @GetMapping
    public String showAll(Model model,@RequestParam(value = "page",required = false) Integer page,
                                      @RequestParam(value = "books_per_page",required = false) Integer books_per_page,
                                      @RequestParam(value = "sort_by_year", required = false) boolean sortByYear){

        if((page==null) || (books_per_page==null))
            model.addAttribute("books",bookService.findAll(sortByYear));

        else
            model.addAttribute("books", bookService.findAll(page, books_per_page,sortByYear));
        return "book/showAll";
    }
    //Если книга принадлежит человеку надо показать этого человека, если нет, то показываем список (Выпадающий) из людей, чтобы мы могли выбрать того кому её назначаем
    @GetMapping("/{id}")
    public String show(Model model, @PathVariable("id")int id,@ModelAttribute("person") Person person){
        model.addAttribute("book",bookService.findOne(id));
        // здесь получаем через букДАО(метод getBookOwner(int id)) человека, которому принадлежит книга и кладём его в модель, а если его нет значит книга свободна
        // и мы должны на странице показать выпадающий список с людьми(кладём в модель список из всех людей)
        Person bookOwner = bookService.getBookOwner(id);

        if(bookOwner!=null){
            model.addAttribute("owner",bookOwner);
        }
        else
            model.addAttribute("people",peopleService.findAll());
        return "book/show";
    }
    @GetMapping("/new")
    public String newBook(Model model){
        model.addAttribute("book", new Book());
        return "book/new";
    }
    @PostMapping()
    public String create(@ModelAttribute("book") @Valid Book book,BindingResult bindingResult){
        //bookValidator.validate(book,bindingResult); убрали так как ничего валидировать не надо было

        if(bindingResult.hasErrors())
            return "book/new";
        bookService.save(book);
        return "redirect:/book";
    }
    @GetMapping("/{id}/edit")
    public String edit(Model model,@PathVariable("id")int id){
        model.addAttribute("book",bookService.findOne(id));
        return "book/edit";
    }
    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult,@PathVariable("id") int id){
        if(bindingResult.hasErrors())
            return "book/edit";
        bookService.update(id,book);
        return "redirect:/book";
    }
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        bookService.delete(id);
        return "redirect:/book";
    }
    //release освобождает книгу при нажатии на кнопку
    @PatchMapping("/{id}/release")
    public String release(@PathVariable("id")int id){
        bookService.release(id);
        return "redirect:/book/"+id;//типо остаёмся на той же странице
    }
    //assign назнаает книгу человеку при нажатии на кнопку
    @PatchMapping("/{id}/assign")
    public String assign(@PathVariable("id")int id,@ModelAttribute("person") Person selectedPerson){
        //нужен айди человека которому назначаем
        //У selectedPerson назначено только поле id остальные поля null (id  мы получаем из выпадающего списка)
        bookService.assign(id,selectedPerson);
        return "redirect:/book/"+id;
    }

    //////Для поиска книги по начальным буквам

    @GetMapping("/search")
    public String search(){
        return "book/search";
    }


    @PostMapping("/search")
    public String makeSearch(Model model,@RequestParam() String query){
        model.addAttribute("books",bookService.searchByName(query));

        return "book/search";
    }
}

