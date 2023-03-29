package com.lib.mgmt.controls;

import com.lib.mgmt.models.ReturnBook;
import com.lib.mgmt.services.BookReturnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/return")
public class BookReturnController {

    private BookReturnService bookReturnService;

    @Autowired
    public void setBookReturnService(BookReturnService bookReturnService) {
        this.bookReturnService = bookReturnService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<ReturnBook>> allReturnedBooks() {
        List<ReturnBook> bookList = bookReturnService.allReturnedBooks();
        return new ResponseEntity<>(bookList, HttpStatus.OK);
    }

    @PostMapping("/returnBook")
    public ResponseEntity<ReturnBook> returnOldBook(
            @RequestBody ReturnBook returnBook) {
        ReturnBook _returnBook = bookReturnService.returnOldBook(returnBook);
        return new ResponseEntity<>(_returnBook, HttpStatus.CREATED);
    }
}
