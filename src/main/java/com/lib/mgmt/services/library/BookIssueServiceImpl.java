package com.lib.mgmt.services.library;

import com.lib.mgmt.constants.LibraryConstants;
import com.lib.mgmt.dtos.IssueBookDto;
import com.lib.mgmt.dtos.IssuedBookStudentDto;
import com.lib.mgmt.exceptions.BookAlreadyIssuedException;
import com.lib.mgmt.exceptions.BookNotFoundException;
import com.lib.mgmt.exceptions.StudentNotFoundException;
import com.lib.mgmt.models.library.Book;
import com.lib.mgmt.models.library.IssueBook;
import com.lib.mgmt.models.library.Student;
import com.lib.mgmt.repos.library.BookIssueRepository;
import com.lib.mgmt.repos.library.BookRepository;
import com.lib.mgmt.repos.library.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
        Student student = studentRepository.findByStudentId(issueBookDto.getStudentId()).orElseThrow(
                ()-> new StudentNotFoundException(LibraryConstants.NO_STUDENT_FOUND_WITH_THIS_ID + issueBookDto.getStudentId()));
        /*Book book = bookRepository.findById(issueBookDto.getBookId()).orElseThrow(
                ()-> new BookNotFoundException(LibraryConstants.NO_BOOK_FOUND_WITH_THIS_ID + issueBookDto.getBookId()));*/
        Book book = bookRepository.findByIdAndAvailBooksGreaterThanEqual(issueBookDto.getBookId(),1).orElseThrow(
                ()-> new BookNotFoundException(LibraryConstants.NO_BOOK_FOUND_WITH_THIS_ID + issueBookDto.getBookId()));
        long count = bookIssueRepository.countByBookIdAndStudentId(issueBookDto.getBookId(),issueBookDto.getStudentId());
        if(count > 2)
            throw new BookAlreadyIssuedException(String.format(LibraryConstants.BOOK_ALREADY_ISSUED,student.getStudentName(),count));
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

    @Override
    public List<IssuedBookStudentDto> sameBookIssuedForStudents(int bookId) {
        return bookIssueRepository.sameBookIssuedForStudents(bookId);
    }

    private IssueBook convertDtoToModel(IssueBookDto issueBookDto) {
        IssueBook issueBook = new IssueBook();
        issueBook.setDuration(issueBookDto.getDuration());
        issueBook.setPeriod(issueBookDto.getPeriod());
        issueBook.setStudentId(issueBookDto.getStudentId());
        issueBook.setBookId(issueBookDto.getBookId());
        issueBook.setIssuedDate(new Date());

        return issueBook;
    }
}
