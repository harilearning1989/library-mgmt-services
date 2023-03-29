package com.lib.mgmt.controls;

import com.lib.mgmt.dtos.IssueBookDto;
import com.lib.mgmt.dtos.IssuedBookStudentDto;
import com.lib.mgmt.models.IssueBook;
import com.lib.mgmt.services.BookIssueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/issue")
public class BookIssueController {

    private BookIssueService bookIssueService;

    @Autowired
    public void setBookIssueService(BookIssueService bookIssueService) {
        this.bookIssueService = bookIssueService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<IssueBook>> allIssuedBooks() {
        List<IssueBook> bookList = bookIssueService.allIssuedBooks();
        return new ResponseEntity<>(bookList, HttpStatus.OK);
    }

    @PostMapping("/issueBook")
    public ResponseEntity<IssueBook> issueNewBook(
            @Valid @RequestBody IssueBookDto issueBookDto) {
        IssueBook _issueBook = bookIssueService.issueNewBook(issueBookDto);
        return new ResponseEntity<>(_issueBook, HttpStatus.CREATED);
    }
    //Api for How many books issued for the Student
    @GetMapping("/issuedBooksForStudent/{studentId}")
    public ResponseEntity<List<IssuedBookStudentDto>> findIssuedBooksForStudent(
            @PathVariable("studentId") int studentId) {
        List<IssuedBookStudentDto> bookList = bookIssueService.findIssuedBooksForStudent(studentId);
        return new ResponseEntity<>(bookList, HttpStatus.OK);
    }
    //API for how many students taken the Same Book
    @GetMapping("/sameBookIssuedForStudents/{bookId}")
    public ResponseEntity<List<IssuedBookStudentDto>> sameBookIssuedForStudents(
            @PathVariable("bookId") int bookId) {
        List<IssuedBookStudentDto> bookList = bookIssueService.sameBookIssuedForStudents(bookId);
        return new ResponseEntity<>(bookList, HttpStatus.OK);
    }

    /*@PostMapping("/issueBook")
    public ResponseEntity<IssueBook> issueNewBook(
            @RequestBody IssueBook issueBook) {
        try {
            IssueBook _issueBook = bookIssueService.issueNewBook(issueBook);
            return new ResponseEntity<>(_issueBook, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }*/

}
