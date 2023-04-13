package com.lib.mgmt.models.library;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "BOOKS")
public class Book {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "BOOK_NAME")
    private String bookName;
    @Column(name = "SUBJECT")
    private String subject;
    @Column(name = "ISBN")
    private int isbn;
    @Column(name = "PRICE")
    private int price;
    @Column(name = "BOOK_QUANTITY")
    private int bookQty;
    @Column(name = "AVAIL_BOOKS")
    private int availBooks;
    @Column(name = "AUTHORS")
    private String authors;
    @Column(name = "CREATED_DATE")
    private Date createdDate;
    @Column(name = "PUBLISHED_DATE")
    private String publishedDate;
    @Column(name = "SHORT_DESCRIPTION")
    private String shortDescription;
    @Column(name = "LONG_DESCRIPTION")
    private String longDescription;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getBookQty() {
        return bookQty;
    }

    public void setBookQty(int bookQty) {
        this.bookQty = bookQty;
    }

    public int getAvailBooks() {
        return availBooks;
    }

    public void setAvailBooks(int availBooks) {
        this.availBooks = availBooks;
    }

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    public int getIsbn() {
        return isbn;
    }

    public void setIsbn(int isbn) {
        this.isbn = isbn;
    }
}
