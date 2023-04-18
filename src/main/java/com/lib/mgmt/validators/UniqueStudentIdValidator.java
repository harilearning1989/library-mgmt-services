package com.lib.mgmt.validators;

import com.lib.mgmt.repos.library.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueStudentIdValidator implements ConstraintValidator<UniqueStudentId, Integer> {

    StudentRepository studentRepository;

    @Autowired
    public void setStudentRepository(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public boolean isValid(Integer studentId, ConstraintValidatorContext context) {
        return !studentRepository.findByStudentId(studentId).isPresent();
    }
}
