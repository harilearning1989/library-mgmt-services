package com.lib.mgmt.controls;

import com.lib.mgmt.models.Student;
import com.lib.mgmt.services.StudentServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.persistence.Column;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest(fullyQualifiedNames = "com.lib.mgmt")
@PowerMockIgnore({"javax.management.*",
        "com.sun.org.apache.xerces.",
        "org.apache.http.conn.ssl.*",
        "com.amazonaws.http.conn.ssl.*", "javax.net.ssl.*",
        "javax.xml.", "org.xml.", "org.w3c.dom.","jdk.internal.reflect.*",
        "com.sun.org.apache.xalan.", "javax.activation.*",
        "javax.net.ssl.*","jdk.internal.reflect.*"})
public class StudentRestControllerTest {

    @InjectMocks
    StudentRestController studentRestController;

    @Mock
    StudentServiceImpl studentService;

    @Test
    public void helloWorldTest(){
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        when(studentService.helloWorld()).thenReturn("Hello");

        ResponseEntity<String> responseEntity = studentRestController.helloWorld();

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
        assertThat(responseEntity.getBody().toString()).isEqualTo("Hello");
    }

    @Test
    public void findAllEmptyTest(){
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        List<Student> studentList = new ArrayList<>();
        when(studentService.findAll()).thenReturn(studentList);

        ResponseEntity<List<Student>> responseEntity = studentRestController.findAll();

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(204);
    }

    @Test
    public void findAllNotEmptyTest(){
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        List<Student> studentList = getStudentData();
        when(studentService.findAll()).thenReturn(studentList);

        ResponseEntity<List<Student>> responseEntity = studentRestController.findAll();

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
        assertThat(responseEntity.getBody().size()).isEqualTo(studentList.size());
    }

    @Test
    public void findAllThrowExceptionTest(){
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        when(studentService.findAll()).thenReturn(null);

        ResponseEntity<List<Student>> responseEntity = studentRestController.findAll();

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(500);
    }

    @Test
    public void findByStudentId() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        Student student = new Student();
        student.setId(12);
        student.setStudentId(76127);
        student.setStudentName("Hari");
        student.setFatherName("Rama");
        student.setGender("Male");
        student.setMobile(9494968081L);
        student.setCategory("MCA");
        when(studentService.findByStudentId(76127)).thenReturn(Optional.of(student));

        ResponseEntity<Student> responseEntity = studentRestController.findByStudentId(76127);

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
    }

    @Test
    public void getStudentByIdEmptyTest(){
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        when(studentService.findByStudentId(76127)).thenReturn(Optional.empty());

        ResponseEntity<Student> responseEntity = studentRestController.findByStudentId(76127);

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(204);
    }

    @Test
    public void createStudent() {
        Student request = new Student();
        request.setId(12);
        request.setStudentId(76127);
        request.setStudentName("Hari");
        request.setFatherName("Rama");
        request.setGender("Male");
        request.setMobile(9494968081L);
        request.setCategory("MCA");

        Student response = new Student();
        response.setId(12);
        response.setStudentId(76127);
        response.setStudentName("Hari");
        response.setFatherName("Rama");
        response.setGender("Male");
        response.setMobile(9494968081L);
        response.setCategory("MCA");

        when(studentService.createStudent(request)).thenReturn(response);
        ResponseEntity<Student> responseEntity = studentRestController.createStudent(request);
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(201);
    }

    @Test
    public void updateStudent() {
    }

    @Test
    public void deleteStudent() {
    }

    @Test
    public void deleteAllStudents() {
    }

    @Test
    public void readJson() {
    }

    private List<Student> getStudentData() {
        List<Student> studentList = new ArrayList<>();

        Student student = new Student();
        student.setId(12);
        student.setStudentId(76127);
        student.setStudentName("Hari");
        student.setFatherName("Rama");
        student.setGender("Male");
        student.setMobile(9494968081L);
        student.setCategory("MCA");

        studentList.add(student);

        student = new Student();
        student.setId(11);
        student.setStudentId(76128);
        student.setStudentName("Bablu");
        student.setFatherName("Hari");
        student.setGender("Male");
        student.setMobile(9494968081L);
        student.setCategory("UKG");

        studentList.add(student);

        return studentList;
    }

}