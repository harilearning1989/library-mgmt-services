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
        book.setIsbn(book.getIsbn());
        if (StringUtils.isNotEmpty(book.getBookName())) {
            book.setBookName(book.getBookName().trim());
        }
        if (StringUtils.isNotEmpty(book.getSubject())) {
            book.setSubject(book.getSubject().trim());
        }
        List<Book> listBook = findBookSearchCriteria(book.getIsbn(), book.getSubject(), book.getBookName());
        if (listBook.isEmpty() || listBook.size() == 0) {
            book.setCreatedDate(new Date());
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

            List<Book> listBook = readBookDto
                    .stream()
                    .map(this::convertDtoToModel)
                    .collect(toList());

            bookRepository.saveAll(listBook);
            return listBook;

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    private Book convertDtoToModel(BooksDTO m) {
        int isbn = 0;
        if (StringUtils.isNotEmpty(m.getIsbn())) {
            if (m.getIsbn().length() > 8) {
                String isbnTmp = m.getIsbn().substring(0, 8);
                isbn = Integer.parseInt(isbnTmp.replaceAll("[^0-9]", ""));
            }
        } else {
            isbn = LibraryUtils.getRandomNumber(1417290084, 1617290084);
        }

        String publishedDate;
        if (m.getPublishedDate() != null && StringUtils.isNotEmpty(m.getPublishedDate().getDate().strip())) {
            publishedDate = m.getPublishedDate().getDate();//2009-04-01T00:00:00.000-0700
        } else {
            publishedDate = new Date().toString();
        }
        String shortDescription;
        if (StringUtils.isNotEmpty(m.getShortDescription())) {
            shortDescription = LibraryUtils.getSubString(m.getShortDescription(), 500);
        } else {
            shortDescription = m.getTitle();
        }
        String longDescription;
        if (StringUtils.isNotEmpty(m.getLongDescription())) {
            longDescription = LibraryUtils.getSubString(m.getLongDescription(), 500);
        } else {
            longDescription = m.getTitle();
        }
        return Book
                .builder()
                .subject(LibraryUtils.getSubString(m.getTitle(), 20))
                .bookName(m.getTitle())
                .bookQty(LibraryUtils.getRandomNumber(100, 1000))
                .isbn(isbn)
                .publishedDate(publishedDate)
                .shortDescription(shortDescription)
                .longDescription(longDescription)
                .authors(LibraryUtils.listOfStringToString(m.getAuthors()))
                .price(LibraryUtils.getRandomNumber(500, 5000))
                .createdDate(new Date())
                .availBooks(LibraryUtils.getRandomNumber(0, 200))
                .build();
    }

    @Override
    public List<Book> findBookSearchCriteria(int isbn, String subject, String bookName) {
        logger.info("ISBN::" + isbn + "====Subject::" + subject + "====BookName::" + bookName);
        return bookRepository.findAll((Specification<Book>) (root, query, criteriaBuilder) -> {

            List<Predicate> predicates = new ArrayList<>();
            if (isbn >= 1) {
                //predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("isbn"), "%"+isbn+"%")));
                predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("isbn"), isbn)));
            }
            if (subject != null && StringUtils.isNotEmpty(subject.trim())) {
                predicates.add(criteriaBuilder.and(criteriaBuilder.like(
                        criteriaBuilder.upper(root.get("subject")), "%" + subject.trim().toUpperCase() + "%"
                )));
            }
            if (StringUtils.isNotEmpty(bookName)) {
                predicates.add(criteriaBuilder.and(criteriaBuilder.like(
                        criteriaBuilder.upper(root.get("bookName")), "%" + bookName.trim().toUpperCase() + "%")));
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
    public long countBooks() {
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
