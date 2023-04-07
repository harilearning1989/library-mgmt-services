package com.lib.mgmt.controls;

import com.lib.mgmt.models.library.ReturnBook;
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
        try {
            List<ReturnBook> bookList = bookReturnService.allReturnedBooks();
            if (bookList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(bookList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/returnBook")
    public ResponseEntity<ReturnBook> returnOldBook(
            @RequestBody ReturnBook returnBook) {
        ReturnBook _returnBook = bookReturnService.returnOldBook(returnBook);
        if(_returnBook == null)
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(_returnBook, HttpStatus.CREATED);
    }
}
