package com.lib.mgmt.controls;

import com.lib.mgmt.services.StudentService;
import org.junit.jupiter.api.Test;

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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest(fullyQualifiedNames = "com.lib.mgmt.*")
@PowerMockIgnore({"javax.management.*",
        "com.sun.org.apache.xerces.",
        "org.apache.http.conn.ssl.*",
        "com.amazonaws.http.conn.ssl.*", "javax.net.ssl.*",
        "javax.xml.", "org.xml.", "org.w3c.dom.","jdk.internal.reflect.*",
        "com.sun.org.apache.xalan.", "javax.activation.*",
        "javax.net.ssl.*","jdk.internal.reflect.*"})
public class StudentRestControllerTest1 {

    @InjectMocks
    StudentRestController studentRestController;

    @Mock
    StudentService studentService;

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
    void setStudentService() {
    }

    @Test
    void findAllStudents() {
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
    void deleteStudent() {
    }

    @Test
    void deleteAllStudents() {
    }

    @Test
    void readJson() {
    }
}