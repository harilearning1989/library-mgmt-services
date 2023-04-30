package com.lib.mgmt.controls;

import com.lib.mgmt.dtos.IssueBookDto;
import com.lib.mgmt.dtos.IssuedBookStudentDto;
import com.lib.mgmt.models.library.IssueBook;
import com.lib.mgmt.response.LibraryResponse;
import com.lib.mgmt.services.library.BookIssueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/issue")
public class BookIssueController {

    private BookIssueService bookIssueService;

    @Autowired
    public void setBookIssueService(BookIssueService bookIssueService) {
        this.bookIssueService = bookIssueService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<IssueBookDto>> findAllIssuedBooks() {
        try {
            List<IssueBookDto> issueBookList = bookIssueService.findAllIssuedBooks();
            /*Optional.ofNullable(issueBookList)
                    .orElseGet(Collections::emptyList)
                    .stream()
                    .filter(Objects::nonNull)
                    .forEach(e -> {
                        System.out.println("Controller::"+e.getIssuedDate());
                    });*/
            if (issueBookList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(issueBookList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/issueNewBook")
    public ResponseEntity<LibraryResponse> issueBook(
            @RequestBody IssueBookDto issueBookDto) {
        IssueBook _issueBook = bookIssueService.issueNewBook(issueBookDto);
        if(_issueBook == null)
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        LibraryResponse libraryResponse = new LibraryResponse();
        libraryResponse.setStatus(HttpStatus.CREATED.value());
        libraryResponse.setMessage("Book Name: "+_issueBook.getBookName()+" successfully Issued");
        return new ResponseEntity<>(libraryResponse, HttpStatus.CREATED);
    }
    //Api for How many books issued for the Student
    @GetMapping("/issuedBooksForStudent/{studentId}")
    public ResponseEntity<List<IssuedBookStudentDto>> findIssuedBooksForStudent(
            @PathVariable("studentId") int studentId) {
        try {
            List<IssuedBookStudentDto> issueBookList = bookIssueService.findIssuedBooksForStudent(studentId);
            if (issueBookList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(issueBookList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
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
