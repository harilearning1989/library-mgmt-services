package com.lib.mgmt.validators;

import com.lib.mgmt.repos.library.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

    StudentRepository studentRepository;

    @Autowired
    public void setStudentRepository(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        return !studentRepository.findByEmail(email).isPresent();
    }
}
