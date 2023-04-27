package com.lib.mgmt.models.library;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Builder
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
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

}
