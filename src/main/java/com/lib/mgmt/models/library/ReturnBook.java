package com.lib.mgmt.models.library;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "RETURNED_BOOKS")
public class ReturnBook {

    @Id
    @Column(name = "RID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "STUDENT_ID")
    private int studentId;
    @Column(name = "BOOK_ID")
    private int bookId;
    @Column(name = "RETURN_DATE")
    private Date returnDate;
    @Column(name = "FINE")
    private int fine;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public int getFine() {
        return fine;
    }

    public void setFine(int fine) {
        this.fine = fine;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }
}
