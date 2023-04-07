package com.lib.mgmt.repos.library;

import com.lib.mgmt.models.ReturnBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookReturnRepository extends JpaRepository<ReturnBook, Integer> {
}
