package com.lib.mgmt.controls;

import com.lib.mgmt.dtos.StudentDTO;
import com.lib.mgmt.models.library.Student;
import com.lib.mgmt.services.library.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Collections.reverseOrder;
import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

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
                        .sorted(comparing(Student::getId, reverseOrder()))
                        .limit(1000)
                        .collect(toList());
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

    @PostMapping("/saveStudent")
    public ResponseEntity<Student> createStudent(
            @Valid @RequestBody StudentDTO dto) {
        Student _student = studentService.createStudent(dto);
        if(_student == null)
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(_student, HttpStatus.CREATED);
    }

    @PutMapping("/updateStudent/{studentId}")
    public ResponseEntity<Student> updateStudent(
            @PathVariable("studentId") int studentId, @RequestBody Student student) {
        Student _student = studentService.updateStudent(studentId,student);
        if(_student == null)
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(_student, HttpStatus.CREATED);
    }

    /*@PutMapping("/{id}")
    public ResponseEntity<Student> updateEmployee(
            @PathVariable("id") int empId, @RequestBody(required = false) Student employee) {
        Optional<Student> empData = studentService.findByStudentId(empId);
        if (empData.isPresent()) {
            Student _employee = empData.get();
            return new ResponseEntity<>(studentService.createStudent(_employee), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }*/

    @PutMapping("/update/{id}")
    public ResponseEntity<Student> updateStudentById(
            @PathVariable("id") int stdId, @RequestBody(required = false) Student student) {
        Student _student = studentService.updateStudentById(stdId,student);
        if(_student == null)
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(_student, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{studentId}")
    public ResponseEntity<String> deleteStudent(@PathVariable("studentId") int studentId) {
        try {
            studentService.deleteByStudentId(studentId);
            return new ResponseEntity<String>("Student deleted successfully!.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/deleteAll")
    public ResponseEntity<String> deleteAllStudents() {
        try {
            studentService.deleteAll();
            return new ResponseEntity<String>("deleted all students successfully!.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/readJson")
    public ResponseEntity<List<StudentDTO>> readJson() {
        try {
            List<StudentDTO> studentList = studentService.readJson();
            if (studentList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }else{
                studentList = studentList
                        .stream()
                        .limit(1000)
                        .collect(toList());
                return new ResponseEntity<>(studentList, HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}

