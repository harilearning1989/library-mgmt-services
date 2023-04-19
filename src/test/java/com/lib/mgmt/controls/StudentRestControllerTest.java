package com.lib.mgmt.controls;

import com.lib.mgmt.dtos.StudentDTO;
import com.lib.mgmt.models.library.Student;
import com.lib.mgmt.services.library.StudentServiceImpl;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

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
    public void findAllStudentsTest(){
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        List<Student> studentList = getStudentData();
        when(studentService.findAll()).thenReturn(studentList);

        ResponseEntity<List<Student>> responseEntity = studentRestController.findAllStudents();

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
        assertThat(responseEntity.getBody().size()).isEqualTo(studentList.size());
    }
    @Test
    public void findAllStudentsEmptyTest(){
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        when(studentService.findAll()).thenReturn(new ArrayList<>());

        ResponseEntity<List<Student>> responseEntity = studentRestController.findAllStudents();

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(204);
    }

    @Test
    public void findAllStudentsExceptionTest(){
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        when(studentService.findAll()).thenReturn(null);

        ResponseEntity<List<Student>> responseEntity = studentRestController.findAllStudents();

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(500);
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
    public void updateStudentTest(){
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

        when(studentService.updateStudent(76127,request)).thenReturn(response);
        ResponseEntity<Student> responseEntity = studentRestController.updateStudent(76127,request);
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(201);
    }

    @Test
    public void updateStudentExceptionTest(){
        Student request = new Student();
        request.setId(12);
        request.setStudentId(76127);
        request.setStudentName("Hari");
        request.setFatherName("Rama");
        request.setGender("Male");
        request.setMobile(9494968081L);
        request.setCategory("MCA");

        when(studentService.updateStudent(76127,request)).thenReturn(null);
        ResponseEntity<Student> responseEntity = studentRestController.updateStudent(76127,request);
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(500);
    }

    @Test
    public void createStudent() {
        StudentDTO request = new StudentDTO();
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
    public void createEmployeeNullTest(){
        StudentDTO request = new StudentDTO();
        request.setId(12);
        request.setStudentId(76127);
        request.setStudentName("Hari");
        when(studentRestController.createStudent(request)).thenReturn(null);
        ResponseEntity<Student> responseEntity = studentRestController.createStudent(request);
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(500);
    }
    /*@Test
    public void updateStudentTest(){
        Student request = new Student();
        request.setId(12);
        request.setStudentId(76127);
        request.setStudentName("Hari");

        Student response = new Student();
        request.setId(12);
        request.setStudentId(76127);
        request.setStudentName("Hari");

        when(studentService.findByStudentId(76127)).thenReturn(Optional.of(response));
        when(studentService.createStudent(request)).thenReturn(response);

        ResponseEntity<Student> responseEntity = studentRestController.updateStudent(76127,request);
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
    }*/

    @Test
    public void updateEmployeeEmptyTest(){
        Student request = new Student();
        request.setId(12);
        request.setStudentId(76127);
        request.setStudentName("Hari");

        when(studentService.findByStudentId(76127)).thenReturn(Optional.empty());

        ResponseEntity<Student> responseEntity = studentRestController.updateStudentById(76127,request);
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
    }

    @Test
    public void deleteStudentTest() {
        doNothing().when(studentService).deleteByStudentId(10);
        ResponseEntity<String> responseEntity = studentRestController.deleteStudent(10);
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
    }

    @Test
    public void deleteStudentExceptionTest() {
        doThrow(new NullPointerException()).when(studentService).deleteByStudentId(10);
        ResponseEntity<String> responseEntity = studentRestController.deleteStudent(10);
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(500);
    }

    @Test
    public void deleteAllStudentTest() {
        doNothing().when(studentService).deleteAll();
        ResponseEntity<String> responseEntity = studentRestController.deleteAllStudents();
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
    }

    @Test
    public void deleteAllStudentExceptionTest() {
        doThrow(new NullPointerException()).when(studentService).deleteAll();
        ResponseEntity<String> responseEntity = studentRestController.deleteAllStudents();
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(500);
    }

    @Test
    public void readJsonTest(){
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        List<StudentDTO> studentList = getStudentDtoData();
        when(studentService.readJson()).thenReturn(studentList);

        ResponseEntity<List<StudentDTO>> responseEntity = studentRestController.readJson();

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
        assertThat(responseEntity.getBody().size()).isEqualTo(studentList.size());
    }

    @Test
    public void readJsonEmptyTest(){
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        when(studentService.readJson()).thenReturn(new ArrayList<>());
        ResponseEntity<List<StudentDTO>> responseEntity = studentRestController.readJson();

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(204);
    }

    @Test
    public void readJsonExceptionTest(){
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        when(studentService.readJson()).thenReturn(null);
        ResponseEntity<List<StudentDTO>> responseEntity = studentRestController.readJson();

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(500);
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

    private List<StudentDTO> getStudentDtoData() {
        List<StudentDTO> dtos = new ArrayList<>();
        StudentDTO dto = new StudentDTO();
        dto.setStudentId(76127);

        dtos.add(dto);

        return dtos;
    }

/*

    @Test
    public void updateEmpTest(){
        Employee empRequest = new Employee();
        empRequest.setEmpName("Chandra");
        empRequest.setManager_id(76127);

        Employee empResponse = new Employee();
        empResponse.setEmpId(11);
        empResponse.setEmpName("Chandra");
        empResponse.setManager_id(76127);

        when(employeeService.updateEmp(76127,empRequest)).thenReturn(empResponse);

        ResponseEntity<Employee> responseEntity = employeeController.updateEmp(76127,empRequest);
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
    }

    @Test
    public void deleteEmployeeTest(){
        doNothing().when(employeeService).deleteById(76127);
        //verify(employeeService,times(1)).deleteById(76127);
        ResponseEntity<HttpStatus> responseEntity = employeeController.deleteEmployee(76127);
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(204);
    }

    @Test
    public void deleteEmployeeExceptionTest(){
        doThrow(new RuntimeException()).when(employeeService).deleteById(76127);
        ResponseEntity<HttpStatus> responseEntity = employeeController.deleteEmployee(76127);
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(500);
    }

    @Test
    public void deleteAllEmployeesTest(){
        doNothing().when(employeeService).deleteAll();
        //verify(employeeService,times(1)).deleteById(76127);
        ResponseEntity<HttpStatus> responseEntity = employeeController.deleteAllEmployees();
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(204);
    }

    @Test
    public void deleteAllEmployeesExceptionTest(){
        doThrow(new RuntimeException()).when(employeeService).deleteAll();
        ResponseEntity<HttpStatus> responseEntity = employeeController.deleteAllEmployees();
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(500);
    }

    @Test
    public void updateFindAllEmptyTest(){
        when(employeeService.updateFindAll()).thenReturn(new ArrayList<>());

        ResponseEntity<List<EmployeeDTO>> responseEntity = employeeController.updateFindAll();
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(204);
    }

    @Test
    public void updateFindAllTest(){
        List<EmployeeDTO> empDto = getManagerDataDto();
        when(employeeService.updateFindAll()).thenReturn(empDto);

        ResponseEntity<List<EmployeeDTO>> responseEntity = employeeController.updateFindAll();
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
    }

    @Test
    public void updateFindAllExceptionTest(){
        when(employeeService.updateFindAll()).thenThrow(new RuntimeException());

        ResponseEntity<List<EmployeeDTO>> responseEntity = employeeController.updateFindAll();
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(500);
    }

    @Test
    public void readJsonTest(){
        List<EmployeeDTO> empDto = getManagerDataDto();
        when(employeeService.readJson()).thenReturn(empDto);

        ResponseEntity<List<EmployeeDTO>> responseEntity = employeeController.readJson();
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
    }

    @Test
    public void readJsonEmptyTest(){
        when(employeeService.readJson()).thenReturn(new ArrayList<>());

        ResponseEntity<List<EmployeeDTO>> responseEntity = employeeController.readJson();
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(204);
    }

    @Test
    public void readJsonExceptionTest(){
        when(employeeService.readJson()).thenThrow(new RuntimeException());

        ResponseEntity<List<EmployeeDTO>> responseEntity = employeeController.readJson();
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(500);
    }


    private List<EmployeeDTO> getManagerDataDto() {
        List<EmployeeDTO> empList = new ArrayList<>();

        EmployeeDTO emp = new EmployeeDTO();
        emp.setId(12);
        emp.setEmpName("Chandra");
        emp.setManager_id(76127);
        empList.add(emp);

        emp = new EmployeeDTO();
        emp.setId(13);
        emp.setEmpName("Pramod");
        emp.setManager_id(76127);
        empList.add(emp);

        emp = new EmployeeDTO();
        emp.setId(14);
        emp.setEmpName("Bablu");
        emp.setManager_id(76128);
        empList.add(emp);

        emp = new EmployeeDTO();
        emp.setId(15);
        emp.setEmpName("Josh");
        emp.setManager_id(76129);
        empList.add(emp);

        emp = new EmployeeDTO();
        emp.setId(15);
        emp.setEmpId(76127);
        emp.setEmpName("Josh");
        emp.setManager_id(76129);
        empList.add(emp);

        return empList;
    }*/

}