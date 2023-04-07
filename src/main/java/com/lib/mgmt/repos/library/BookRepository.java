package com.lib.mgmt.repos.library;

import com.lib.mgmt.models.Book;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book,Integer> {

    Optional<Book> findByIdAndAvailBooksGreaterThanEqual(int id, int availBooks);
    @Modifying
    @Query("update Book b set b.availBooks = :availBooks where b.id = :id")
    void updateAvailableBooks(@Param(value = "id") int id, @Param(value = "availBooks") int availBooks);

    List<Book> findByAvailBooksGreaterThan(int i);

    List<Book> findAll(Specification<Book> bookSpecification);
}
