package com.lib.mgmt.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class StudentServiceImplTest1 {

    private StudentServiceImpl studentServiceImpl;

    @BeforeEach
    public void setup() {
        studentServiceImpl = new StudentServiceImpl();
    }

    @Test
    void findAll() {
        assertEquals(Arrays.asList(1,2,3), studentServiceImpl.sortIntegers(Arrays.asList(3, 2, 1)));
    }

    @Test
    void findByStudentId() {
    }

    @Test
    void createStudent() {
    }

    @Test
    void updateStudent() {
    }

    @Test
    void deleteByStudentId() {
    }

    @Test
    void deleteAll() {
    }

    @Test
    void saveAll() {
    }

    @Test
    void readJson() {
    }

    @Test
    void countStudents() {
    }
}