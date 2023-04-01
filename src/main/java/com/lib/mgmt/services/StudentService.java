package com.lib.mgmt.services;

import com.lib.mgmt.dtos.StudentDTO;
import com.lib.mgmt.models.Student;

import java.util.List;
import java.util.Optional;

public interface StudentService {
    List<Student> findAll();

    List<StudentDTO> readJson();

    Optional<Student> findByStudentId(int studentId);

    Student createStudent(Student student);

    Student updateStudent(Student student, int studentId);

    Student updateStudentById(Student student, int studentId);

    void deleteByStudentId(int id);

    void deleteAll();

    void saveAll(List<StudentDTO> studentDTOS);

    long countStudents();

    String helloWorld();
}
