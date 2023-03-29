package com.lib.mgmt.dtos;

import com.lib.mgmt.validators.DurationEnum;
import com.lib.mgmt.validators.DurationValidator;

import javax.validation.constraints.Min;

public class IssueBookDto {

    @Min(value = 1, message = "To must be greater than zero")
    private int studentId;
    @Min(value = 1, message = "To must be greater than zero")
    private int bookId;
    @Min(value = 1, message = "To must be greater than zero")
    private int period;
    @DurationValidator(enumClass = DurationEnum.class)
    private String duration;

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

}
