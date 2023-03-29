package com.lib.mgmt.models;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "ISSUED_BOOKS")
public class IssueBook {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "STUDENT_ID")
    private int studentId;
    @Column(name = "BOOK_ID")
    private int bookId;
    @Column(name = "ISSUED_DATE")
    private Date issuedDate;
    @Column(name = "PERIOD")
    private int period;
    @Column(name = "DURATION")
    private String duration;

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

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public Date getIssuedDate() {
        return issuedDate;
    }

    public void setIssuedDate(Date issuedDate) {
        this.issuedDate = issuedDate;
    }
}
