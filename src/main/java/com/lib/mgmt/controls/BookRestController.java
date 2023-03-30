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
        try {
            List<Book> bookList = bookService.findAll();
            if (bookList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(bookList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        //List<Book> bookList = bookService.findAll();
        /*Optional.ofNullable(bookList)
                .orElseGet(Collections::emptyList)
                .stream()
                .filter(f -> f.getPublishedDate() == null || f.getPublishedDate() == "")
                .forEach(f -> {
                    System.out.println(f.getPublishedDate());
                });*/
        //return new ResponseEntity<>(bookList, HttpStatus.OK);
    }

    @PostMapping("/searchBookCustom")
    public ResponseEntity<List<Book>> searchBookCustom(@RequestBody Book book) {
        try {
            List<Book> bookList = bookService.searchBookCustom(book);
            if (bookList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(bookList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<Book> createBook(
            @RequestBody Book book) {
        Book _book = bookService.createBook(book);
        if(_book == null)
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(_book, HttpStatus.CREATED);
    }

    @GetMapping("/availableBooks")
    public ResponseEntity<List<Book>> findAvailableBooks() {
        try {
            List<Book> bookList = bookService.findAvailableBooks();
            if (bookList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(bookList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/saveAllBooks")
    public ResponseEntity<List<Book>> saveAllBooks() {
        try {
            List<Book> bookList = bookService.saveAllBooks();
            if (bookList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(bookList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/readJson")
    public ResponseEntity<List<BooksDTO>> readJson() {
        try {
            List<BooksDTO> booksDTOList = bookService.readJson();
            if (booksDTOList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(booksDTOList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/searchBookOld")
    public ResponseEntity<List<Book>> searchBookOld(@RequestBody Book book) {
        try {
            List<Book> bookList = bookService.searchBook(book);
            if (bookList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(bookList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/searchBook")
    public ResponseEntity<List<Book>> findBookSearchCriteria(
            @RequestParam(value = "isbn") String isbn,
            @RequestParam(value = "subject") String subject,
            @RequestParam(value = "bookName") String bookName) {
        //@RequestParam(value = "isbn",defaultValue = "1617290904") String isbn,
        try {
            List<Book> bookList = bookService.findBookSearchCriteria(isbn, subject, bookName);
            if (bookList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(bookList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/updateBook")
    public ResponseEntity<Book> updateBook(@RequestBody Book book) {
        Book _book = bookService.updateBook(book);
        if(_book == null)
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(_book, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable("id") int bookId) {
        try {
            bookService.deleteByBookId(bookId);
            return new ResponseEntity<String>("Book deleted successfully!.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
