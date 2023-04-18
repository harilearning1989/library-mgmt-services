package com.lib.mgmt.services.library;

import com.lib.mgmt.constants.LibraryConstants;
import com.lib.mgmt.dtos.IssueBookDto;
import com.lib.mgmt.dtos.IssuedBookStudentDto;
import com.lib.mgmt.exceptions.GlobalMessageException;
import com.lib.mgmt.models.library.Book;
import com.lib.mgmt.models.library.IssueBook;
import com.lib.mgmt.models.library.Student;
import com.lib.mgmt.repos.library.BookIssueRepository;
import com.lib.mgmt.repos.library.BookRepository;
import com.lib.mgmt.repos.library.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class BookIssueServiceImpl implements BookIssueService{

    private BookIssueRepository bookIssueRepository;

    private StudentRepository studentRepository;

    private BookRepository bookRepository;

    @Autowired
    public void setBookIssueRepository(BookIssueRepository bookIssueRepository) {
        this.bookIssueRepository = bookIssueRepository;
    }

    @Autowired
    public void setStudentRepository(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Autowired
    public void setBookRepository(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public List<IssueBook> findAllIssuedBooks() {
        return bookIssueRepository.findAll();
    }

    @Override
    @Transactional
    public IssueBook issueNewBook(IssueBookDto issueBookDto) {
        Student student = studentRepository.findByStudentIdAndStudentName(
                issueBookDto.getStudentId(),issueBookDto.getStudentName()).orElseThrow(
                ()-> new GlobalMessageException(
                        LibraryConstants.NO_STUDENT_FOUND_WITH_THIS_ID + issueBookDto.getStudentId(), HttpStatus.NO_CONTENT));
        Book book = bookRepository.findByIsbnAndBookNameAndAuthorsAndAvailBooksGreaterThanEqual(
                issueBookDto.getIsbn(),issueBookDto.getBookName(),issueBookDto.getAuthors(),1).orElseThrow(
                ()-> new GlobalMessageException(
                        LibraryConstants.NO_BOOK_FOUND_WITH_THIS_ID + issueBookDto.getIsbn(), HttpStatus.NO_CONTENT));
        long count = bookIssueRepository.countByIsbnAndStudentIdAndBookNameAndSubject
                (issueBookDto.getIsbn(),issueBookDto.getStudentId(),issueBookDto.getBookName(),issueBookDto.getSubject());
        if(count > 2)
            throw new GlobalMessageException(String.format(
                    LibraryConstants.BOOK_ALREADY_ISSUED,student.getStudentName(),count),HttpStatus.CONFLICT);
        IssueBook issueBook = convertDtoToModel(issueBookDto);
        issueBook = bookIssueRepository.save(issueBook);
        if(issueBook != null){
            int availableBooks = book.getAvailBooks();
            bookRepository.updateAvailableBooks(book.getId(),availableBooks-1);
        }
        return issueBook;
    }

    @Override
    public List<IssuedBookStudentDto> findIssuedBooksForStudent(int studentId) {
        return bookIssueRepository.findIssuedBooksForStudent(studentId);
    }


    private IssueBook convertDtoToModel(IssueBookDto dto) {
        IssueBook issueBook = new IssueBook();
        issueBook.setStudentId(dto.getStudentId());
        issueBook.setIssuedDate(new Date());
        issueBook.setStudentName(dto.getStudentName());
        issueBook.setSubject(dto.getSubject());
        issueBook.setBookName(dto.getBookName());
        issueBook.setIsbn(dto.getIsbn());
        issueBook.setAuthors(dto.getAuthors());
        issueBook.setPrice(dto.getPrice());

        return issueBook;
    }
}
