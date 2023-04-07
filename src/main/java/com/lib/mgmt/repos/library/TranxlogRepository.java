package com.lib.mgmt.repos.library;

import com.lib.mgmt.models.library.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface TranxlogRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {
}
