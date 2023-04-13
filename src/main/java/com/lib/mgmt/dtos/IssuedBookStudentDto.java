package com.lib.mgmt.dtos;

import java.util.Date;

public class IssuedBookStudentDto {

    private int studentId;
    private String studentName;
    private int isbn;
    private String bookName;
    private Date issuedDate;

    public IssuedBookStudentDto(){}
    public IssuedBookStudentDto(int studentId,String studentName,int isbn,String bookName,Date issuedDate){
        this.studentId = studentId;
        this.studentName = studentName;
        this.bookName = bookName;
        this.issuedDate = issuedDate;
        this.isbn = isbn;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public Date getIssuedDate() {
        return issuedDate;
    }

    public void setIssuedDate(Date issuedDate) {
        this.issuedDate = issuedDate;
    }

    public int getIsbn() {
        return isbn;
    }

    public void setIsbn(int isbn) {
        this.isbn = isbn;
    }
}
