package com.lib.mgmt.services.library;

import com.lib.mgmt.dtos.StudentDTO;
import com.lib.mgmt.models.library.Student;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface StudentService {
    List<Student> findAll();

    List<StudentDTO> readJson();

    Optional<Student> findByStudentId(int studentId);

    Student createStudent(StudentDTO dto);

    Student updateStudent(int studentId,Student student);

    Student updateStudentById(int studentId,Student student);

    void deleteByStudentId(int id);

    void deleteAll();

    void saveAll(List<StudentDTO> studentDTOS);

    long countStudents();

    String helloWorld();

    boolean findByEmail(String email);

    Student updateStudentPatch(int studentId, Map<String,Object> fields);
}
