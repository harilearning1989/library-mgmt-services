package com.lib.mgmt.controls;

import com.lib.mgmt.dtos.StudentDTO;
import com.lib.mgmt.models.Book;
import com.lib.mgmt.models.Student;
import com.lib.mgmt.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/student")
public class StudentRestController {

    private StudentService studentService;

    @Autowired
    public void setStudentService(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/hello")
    public ResponseEntity<String> helloWorld(){
        String message = studentService.helloWorld();
        return new ResponseEntity<>(message,HttpStatus.OK);
    }
    @GetMapping("/all")
    public ResponseEntity<List<Student>> findAllStudents() {
        try {
            List<Student> studentList = studentService.findAll();
            if (studentList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }else{
                studentList = studentList
                        .stream()
                        .limit(1000)
                        .collect(Collectors.toList());
                return new ResponseEntity<>(studentList, HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/list")
    public ResponseEntity<List<Student>> findAll() {
        try {
            List<Student> studentList = studentService.findAll();
            if (studentList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(studentList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/byStudentId/{studentId}")
    public ResponseEntity<Student> findByStudentId(
            @PathVariable("studentId") int studentId) {
        Optional<Student> studentOpt = studentService.findByStudentId(studentId);

        if (studentOpt.isPresent()) {
            return new ResponseEntity<>(studentOpt.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<Student> createStudent(
            @RequestBody Student student) {
        try {
            Student _student = studentService.createStudent(student);
            return new ResponseEntity<>(_student, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update/{studentId}")
    public ResponseEntity<Student> updateStudent(
            @PathVariable("studentId") int studentId, @RequestBody Student student) {
        try {
            ResponseEntity<Student> _student = studentService.updateStudent(student,studentId);
            return _student;
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Student> updateEmployee(
            @PathVariable("id") int empId, @RequestBody(required = false) Student employee) {
        Optional<Student> empData = studentService.findByStudentId(empId);
        if (empData.isPresent()) {
            Student _employee = empData.get();
            return new ResponseEntity<>(studentService.createStudent(_employee), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Student> updateStudentById(
            @PathVariable("id") int stdId, @RequestBody(required = false) Student student) {
        Student _student = studentService.updateStudentById(student,stdId);
        return new ResponseEntity<>(_student, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{studentId}")
    public ResponseEntity<HttpStatus> deleteStudent(@PathVariable("studentId") int studentId) {
        try {
            studentService.deleteByStudentId(studentId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/deleteAll")
    public ResponseEntity<HttpStatus> deleteAllStudents() {
        try {
            studentService.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/readJson")
    public ResponseEntity<List<StudentDTO>> readJson() {
        List<StudentDTO> studentList = studentService.readJson();
        studentList = studentList
                .stream()
                .limit(1000)
                .collect(Collectors.toList());
        return new ResponseEntity<>(studentList, HttpStatus.OK);
    }

}

