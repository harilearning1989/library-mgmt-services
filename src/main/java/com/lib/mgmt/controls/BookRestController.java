package com.lib.mgmt.controls;

import com.lib.mgmt.dtos.BooksDTO;
import com.lib.mgmt.models.Book;
import com.lib.mgmt.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/book")
public class BookRestController {

    private BookService bookService;

    @Autowired
    public void setBookService(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Book>> findAllBooks() {
        List<Book> bookList = bookService.findAll();
        /*Optional.ofNullable(bookList)
                .orElseGet(Collections::emptyList)
                .stream()
                .filter(f -> f.getPublishedDate() == null || f.getPublishedDate() == "")
                .forEach(f -> {
                    System.out.println(f.getPublishedDate());
                });*/
        return new ResponseEntity<>(bookList, HttpStatus.OK);
    }

    @PostMapping("/searchBookCustom")
    public ResponseEntity<List<Book>> searchBookCustom(@RequestBody Book book) {
        List<Book> bookList = bookService.searchBookCustom(book);
        return new ResponseEntity<>(bookList, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<Book> createBook(
            @RequestBody Book book) {
        try {
            Book _book = bookService.createBook(book);
            if(_book == null)
                return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(_book, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/availableBooks")
    public ResponseEntity<List<Book>> findAvailableBooks() {
        List<Book> bookList = bookService.findAvailableBooks();
        return new ResponseEntity<>(bookList, HttpStatus.OK);
    }

    @GetMapping("/saveAllBooks")
    public ResponseEntity<List<Book>> saveAllBooks() {
        List<Book> bookList = bookService.saveAllBooks();
        return new ResponseEntity<>(bookList, HttpStatus.OK);
    }

    @GetMapping("/readJson")
    public ResponseEntity<List<BooksDTO>> readJson() {
        List<BooksDTO> booksDTOList = bookService.readJson();
        return new ResponseEntity<>(booksDTOList, HttpStatus.OK);
    }
    @PostMapping("/searchBookOld")
    public ResponseEntity<List<Book>> searchBookOld(@RequestBody Book book) {
        List<Book> bookList = bookService.searchBook(book);
        return new ResponseEntity<>(bookList, HttpStatus.OK);
    }

    @GetMapping("/searchBook")
    public ResponseEntity<List<Book>> findBookSearchCriteria(
            @RequestParam(value = "isbn") String isbn,
            @RequestParam(value = "subject") String subject,
            @RequestParam(value = "bookName") String bookName
    ) {
        //@RequestParam(value = "isbn",defaultValue = "1617290904") String isbn,
        List<Book> booksList = bookService.findBookSearchCriteria(isbn, subject, bookName);
        return new ResponseEntity<>(booksList, HttpStatus.OK);
    }

    @PutMapping("/updateBook")
    public ResponseEntity<Book> updateStudent(@RequestBody Book book) {
        try {
            ResponseEntity<Book> _book = bookService.updateBook(book);
            return _book;
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteStudent(@PathVariable("id") int bookId) {
        try {
            bookService.deleteByBookId(bookId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
