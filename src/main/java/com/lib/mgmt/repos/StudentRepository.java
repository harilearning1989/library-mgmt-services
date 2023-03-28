package com.lib.mgmt.repos;

import com.lib.mgmt.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student,Integer> {
    Optional<Student> findByStudentId(int studentId);

    void deleteByStudentId(int studentId);
}
