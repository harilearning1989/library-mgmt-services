package com.lib.mgmt.services.library;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import com.lib.mgmt.dtos.BooksDTO;
import com.lib.mgmt.models.library.Book;
import com.lib.mgmt.repos.library.BookRepository;
import com.lib.mgmt.repos.library.TranxlogRepository;
import com.lib.mgmt.utils.LibraryUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.jpa.convert.QueryByExamplePredicateBuilder;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
public class BookServiceImpl implements BookService {

    private static final Logger logger = LoggerFactory.getLogger(BookServiceImpl.class);

    private BookRepository bookRepository;

    @Autowired
    public void setBookRepository(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    private TranxlogRepository tranxlogRepository;

    @Autowired
    public void setTranxlogRepository(TranxlogRepository tranxlogRepository) {
        this.tranxlogRepository = tranxlogRepository;
    }

    @Override
    public List<Book> findAvailableBooks() {
        List<Book> bookList = bookRepository.findByAvailBooksGreaterThan(0);
        return LibraryUtils.processBooks(bookList);
    }

    @Override
    public List<Book> findAll() {
        List<Book> bookList = bookRepository.findAll();
        /*Optional.ofNullable(bookList)
                .orElseGet(Collections::emptyList)
                .stream()
                .filter(f -> f.getPublishedDate() == null || f.getPublishedDate() == "")
                .forEach(f -> {
                    System.out.println(f.getPublishedDate());
                });*/
        return LibraryUtils.processBooks(bookList);
    }

    @Override
    public Book createBook(Book book) {
        if(StringUtils.isNotEmpty(book.getIsbn())){
            book.setIsbn(book.getIsbn().trim());
        }
        if(StringUtils.isNotEmpty(book.getBookName())){
            book.setBookName(book.getBookName().trim());
        }
        if(StringUtils.isNotEmpty(book.getSubject())){
            book.setSubject(book.getSubject().trim());
        }
        List<Book> listBook = findBookSearchCriteria(book.getIsbn(),book.getSubject(),book.getBookName());
        if(listBook.isEmpty() || listBook.size() == 0){
            book.setCreatedDate(new Date());
            book.setUpdatedDate(new Date());
            return bookRepository.save(book);
        }
        return null;
    }

    @Override
    public List<BooksDTO> readJson() {
        List<BooksDTO> readBookDto = null;
        try {
            String fixture = Resources.toString(Resources.getResource("json/books.json"), Charsets.UTF_8);
            ObjectMapper objectMapper = new ObjectMapper();
            readBookDto = objectMapper.readValue(fixture,
                    objectMapper.getTypeFactory().constructCollectionType(List.class, BooksDTO.class));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return readBookDto;
    }

    @Override
    public List<Book> searchBook(Book book) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                //.withMatcher("subject", ExampleMatcher.GenericPropertyMatchers.startsWith().ignoreCase())
                .withMatcher("subject", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                .withMatcher("bookName", ExampleMatcher.GenericPropertyMatchers.startsWith().ignoreCase())
                .withMatcher("isbn", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                .withMatcher("shortDescription", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                .withMatcher("longDescription", ExampleMatcher.GenericPropertyMatchers.exact())
                //.withMatcher("pageCount", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                /*.withMatcher("bookQty",
                        new ExampleMatcher.MatcherConfigurer<ExampleMatcher.GenericPropertyMatcher>() {
                            @Override
                            public void configureMatcher(ExampleMatcher.GenericPropertyMatcher matcher) {
                                matcher.transform();
                            }
                        })*/
                .withIgnoreNullValues();

        Example<Book> exampleQuery = Example.of(book, matcher);
        List<Book> bookList = bookRepository.findAll(exampleQuery);
        return bookList;
    }

    @Override
    public List<Book> searchBookCustom(Book book) {
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withNullHandler(ExampleMatcher.NullHandler.IGNORE)
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
                .withIgnoreCase()
                .withIgnorePaths("id")
                .withIgnorePaths("bookQty")
                .withIgnorePaths("price")
                .withIgnorePaths("pageCount")
                .withIgnoreNullValues();
        Example<Book> example = Example.of(book, matcher);
        return tranxlogRepository.findAll(getSpecFromDatesAndExample(book, example));
    }

    public Specification<Book> getSpecFromDatesAndExample(Book book, Example<Book> example) {

        return (Specification<Book>) (root, query, builder) -> {
            final List<Predicate> predicates = new ArrayList<>();
            if (book.getBookQty() > 0) {
                predicates.add(builder.greaterThanOrEqualTo(root.get("bookQty"), book.getBookQty()));
            }
            if (book.getPrice() > 0) {
                predicates.add(builder.lessThanOrEqualTo(root.get("price"), book.getPrice()));
            }
            if (book.getPageCount() > 0) {
                predicates.add(builder.equal(root.get("pageCount"), book.getPageCount()));
            }
            predicates.add(QueryByExamplePredicateBuilder.getPredicate(root, builder, example));
            return builder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }

    ;

    @Override
    public List<Book> saveAllBooks() {
        List<BooksDTO> readBookDto = null;
        try {
            String fixture = Resources.toString(Resources.getResource("json/books.json"), Charsets.UTF_8);
            ObjectMapper objectMapper = new ObjectMapper();
            readBookDto = objectMapper.readValue(fixture,
                    objectMapper.getTypeFactory().constructCollectionType(List.class, BooksDTO.class));

            List<Book> listBook = readBookDto.stream().map(m -> {
                Book book = new Book();

                book.setSubject(LibraryUtils.getSubString(m.getTitle(), 20));
                book.setBookName(m.getTitle());
                book.setBookQty(LibraryUtils.getRandomNumber(100, 1000));
                if (StringUtils.isNotEmpty(m.getIsbn())) {
                    book.setIsbn(m.getIsbn());
                } else {
                    book.setIsbn(m.getTitle());
                }
                if (m.getPublishedDate() != null && StringUtils.isNotEmpty(m.getPublishedDate().getDate().strip())) {
                    book.setPublishedDate(m.getPublishedDate().getDate());//2009-04-01T00:00:00.000-0700
                } else {
                    book.setPublishedDate(new Date().toString());
                }
                book.setThumbnailUrl(m.getThumbnailUrl());
                if (StringUtils.isNotEmpty(m.getShortDescription())) {
                    book.setShortDescription(LibraryUtils.getSubString(m.getShortDescription(), 500));
                } else {
                    book.setShortDescription(m.getTitle());
                }

                if (StringUtils.isNotEmpty(m.getLongDescription())) {
                    book.setLongDescription(LibraryUtils.getSubString(m.getLongDescription(), 500));
                } else {
                    book.setLongDescription(m.getTitle());
                }

                book.setStatus(m.getStatus());
                book.setAuthors(LibraryUtils.listOfStringToString(m.getAuthors()));

                if (!m.getCategories().isEmpty()) {
                    book.setCategories(LibraryUtils.listOfStringToString(m.getCategories()));
                } else {
                    book.setCategories(m.getTitle());
                }

                book.setPrice(LibraryUtils.getRandomNumber(500, 5000));
                if (m.getPageCount() > 0) {
                    book.setPageCount(m.getPageCount());
                } else {
                    book.setPageCount(LibraryUtils.getRandomNumber(50, 500));
                }
                book.setCreatedDate(new Date());
                book.setUpdatedDate(new Date());

                book.setAvailBooks(LibraryUtils.getRandomNumber(5, 200));

                return book;
            }).collect(toList());

            bookRepository.saveAll(listBook);
            return listBook;

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Book> findBookSearchCriteria(String isbn, String subject, String bookName) {
        logger.info("ISBN::"+isbn+"====Subject::"+subject+"====BookName::"+bookName);
        return bookRepository.findAll((Specification<Book>) (root, query, criteriaBuilder) -> {

            List<Predicate> predicates = new ArrayList<>();
            if (isbn != null && StringUtils.isNotEmpty(isbn)) {
                //predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("isbn"), "%"+isbn+"%")));
                predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("isbn"), isbn.trim())));
            }
            if (subject != null && StringUtils.isNotEmpty(subject.trim())) {
                predicates.add(criteriaBuilder.and(criteriaBuilder.like(
                        criteriaBuilder.upper(root.get("subject")), "%"+subject.trim().toUpperCase()+"%"
                )));
            }
            if (bookName != null && StringUtils.isNotEmpty(bookName)) {
                predicates.add(criteriaBuilder.and(criteriaBuilder.like(
                        criteriaBuilder.upper(root.get("bookName")), "%"+bookName.trim().toUpperCase()+"%")));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        });
    }

    @Override
    public Book updateBook(Book book) {
        Optional<Book> bookData = bookRepository.findById(book.getId());
        if (bookData.isPresent()) {
            Book _book = bookData.get();
            return bookRepository.save(_book);
        }
        return null;
    }

    @Override
    public void deleteByBookId(int bookId) {
        bookRepository.deleteById(bookId);
    }

    @Override
    public long countBooks(){
        return bookRepository.count();
    }
/*
    public static Specification<WorkInProgress> findByCriteria(final SearchCriteria searchCriteria) {

        return new Specification<WorkInProgress>() {

            @Override
            public Predicate toPredicate(
                    Root<WorkInProgress> root,
                    CriteriaQuery<?> query, CriteriaBuilder cb) {

                List<Predicate> predicates = new ArrayList<Predicate>();

                if (searchCriteria.getView() != null && !searchCriteria.getView().isEmpty()) {
                    predicates.add(cb.equal(root.get("viewType"), searchCriteria.getView()));
                }
                if (searchCriteria.getFeature() != null && !searchCriteria.getFeature().isEmpty()) {
                    predicates.add(cb.equal(root.get("title"), searchCriteria.getFeature()));
                }
                if (searchCriteria.getEpic() != null && !searchCriteria.getEpic().isEmpty()) {
                    predicates.add(cb.equal(root.get("epic"), searchCriteria.getEpic()));
                }
                if (searchCriteria.getPerformingGroup() != null && !searchCriteria.getPerformingGroup().isEmpty()) {
                    predicates.add(cb.equal(root.get("performingGroup"), searchCriteria.getPerformingGroup()));
                }
                if (searchCriteria.getPlannedStartDate() != null) {
                    System.out.println("searchCriteria.getPlannedStartDate():" + searchCriteria.getPlannedStartDate());
                    predicates.add(cb.greaterThanOrEqualTo(root.<Date>get("plndStartDate"), searchCriteria.getPlannedStartDate()));
                }
                if (searchCriteria.getPlannedCompletionDate() != null) {
                    predicates.add(cb.lessThanOrEqualTo(root.<Date>get("plndComplDate"), searchCriteria.getPlannedCompletionDate()));
                }
                if (searchCriteria.getTeam() != null && !searchCriteria.getTeam().isEmpty()) {
                    predicates.add(cb.equal(root.get("agileTeam"), searchCriteria.getTeam()));
                }

                return cb.and(predicates.toArray(new Predicate[] {}));
            }
        };
    }
    public static Specification<WorkInProgress> findByCriteria(final SearchCriteria searchCriteria) {
        Example<Person> example = Example.of(new Person("Jon", "Snow"));
        repo.findAll(example);

        ExampleMatcher matcher = ExampleMatcher.matching().withMatcher("firstname", endsWith())
                .withMatcher("lastname", startsWith().ignoreCase());

        Example<Person> example = Example.of(new Person("Jon", "Snow"), matcher);
        repo.count(example);
    }

    @Override
    public List<EntityExample> findByCombinedFilter(
            final String type,
            final String accountHolderName,
            final String accountHolderLastName,
            final String agencyNumber,
            final String accountNumber) {

        final EntityExample entityExample = EntityExampleBuilder
                .builder()
                .addType(type)
                .addAccountHolderName(accountHolderName)
                .addAccountHolderLastName(accountHolderLastName)
                .addAgencyNumber(agencyNumber)
                .addAccountNumber(accountNumber)
                .addActive(1)
                .build();
        return entityExampleRepository.findAll(Example.of(entityExample));
    }*/


}
