package com.lib.mgmt.dtos;

import java.util.Date;

public class IssuedBookStudentDto {

    private int studentId;
    private String studentName;
    private int bookId;
    private String bookName;
    private Date issuedDate;
    private int period;
    private String duration;

    public IssuedBookStudentDto(){}
    public IssuedBookStudentDto(int studentId,String studentName,int bookId,String bookName,Date issuedDate,int period,String duration){
        this.studentId = studentId;
        this.studentName = studentName;
        this.bookId = bookId;
        this.bookName = bookName;
        this.issuedDate = issuedDate;
        this.period = period;
        this.duration = duration;
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

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
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
}
