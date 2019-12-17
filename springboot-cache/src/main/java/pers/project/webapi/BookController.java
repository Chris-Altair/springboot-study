package pers.project.webapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pers.project.domain.BookDO;
import pers.project.service.BookService;

@RestController
public class BookController {
    @Autowired
    private BookService bookService;

    @GetMapping("/get/{id}")
    public BookDO get(@PathVariable(name = "id") String id) {
        BookDO book = bookService.getById(id);
        return book;
    }

    @GetMapping("/save")
    public BookDO save(@RequestParam(name = "name") String name,
                       @RequestParam(name = "author") String author) {
        BookDO book = new BookDO();
        book.setAuthor(author);
        book.setName(name);
        book = bookService.save(book);
        return book;
    }

    @GetMapping("/update")
    public BookDO update(@RequestParam(name = "id") String id,
                         @RequestParam(name = "name") String name,
                         @RequestParam(name = "author") String author) {
        BookDO book = new BookDO();
        book.setId(id);
        book.setAuthor(author);
        book.setName(name);
        book = bookService.update(book);
        return book;
    }

    @GetMapping("/delete/{id}")
    public void delete(@PathVariable(name = "id") String id) {
        bookService.delete(id);
    }

}
