package com.lib.mgmt.repos.library;

import com.lib.mgmt.models.library.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student,Integer> {
    Optional<Student> findByStudentId(int studentId);
    Optional<Student> findByStudentIdAndStudentName(int studentId,String studentName);
    Optional<Student> findByEmail(String email);

    void deleteByStudentId(int studentId);
}
