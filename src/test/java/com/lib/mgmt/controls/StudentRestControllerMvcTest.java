package com.lib.mgmt.controls;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lib.mgmt.dtos.StudentDTO;
import com.lib.mgmt.models.library.Student;
import com.lib.mgmt.services.library.StudentServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StudentRestController.class)
public class StudentRestControllerMvcTest {

    @MockBean
    private StudentServiceImpl studentService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void helloWorldTest()throws Exception {
        when(studentService.helloWorld()).thenReturn("Hello World");
        mockMvc.perform(get("/student/hello"))
                .andExpect(status().isOk());
    }
    @Test
    public void findAllBooksTest()throws Exception {
        List<Student> studentList = getStudentData();
        when(studentService.findAll()).thenReturn(studentList);
        mockMvc.perform(get("/student/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(studentList.size()))
                .andDo(print());
    }

    @Test
    public void findAllBooksEmptyTest()throws Exception {
        when(studentService.findAll()).thenReturn(new ArrayList<>());
        mockMvc.perform(get("/student/all"))
                .andExpect(status().isNoContent())
                .andDo(print());
    }

    @Test
    public void findAllBooksExceptionTest()throws Exception {
        when(studentService.findAll()).thenReturn(null);
        mockMvc.perform(get("/student/all"))
                .andExpect(status().is5xxServerError())
                .andDo(print());
    }

    @Test
    public void findAllTest()throws Exception {
        List<Student> studentList = getStudentData();
        when(studentService.findAll()).thenReturn(studentList);
        mockMvc.perform(get("/student/list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(studentList.size()))
                .andDo(print());
    }
    @Test
    public void findAllEmptyTest()throws Exception {
        when(studentService.findAll()).thenReturn(new ArrayList<>());
        mockMvc.perform(get("/student/list"))
                .andExpect(status().isNoContent())
                .andDo(print());
    }
    @Test
    public void findAllExceptionTest()throws Exception {
        when(studentService.findAll()).thenReturn(null);
        mockMvc.perform(get("/student/list"))
                .andExpect(status().is5xxServerError())
                .andDo(print());
    }

    @Test
    public void findByStudentIdTest()throws Exception {
        Student student = new Student();
        student.setId(12);
        student.setStudentId(76127);
        student.setStudentName("Hari");
        student.setFatherName("Rama");
        student.setGender("Male");
        student.setMobile(9494968081L);
        student.setCategory("MCA");
        when(studentService.findByStudentId(anyInt())).thenReturn(Optional.of(student));
        mockMvc.perform(get("/student/byStudentId/{studentId}",76127))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void findByStudentIdEmptyTest()throws Exception {
        when(studentService.findByStudentId(anyInt())).thenReturn(Optional.empty());
        mockMvc.perform(get("/student/byStudentId/{studentId}",76127))
                .andExpect(status().isNoContent())
                .andDo(print());
    }

    @Test
    public void createStudentTest()throws Exception {
        Student student = new Student();
        student.setId(12);
        student.setStudentId(76127);
        student.setStudentName("Hari");
        student.setFatherName("Rama");
        student.setGender("Male");
        student.setMobile(9494968081L);
        student.setCategory("MCA");
        given(studentService.createStudent(any(StudentDTO.class)))
                .willReturn(student);

        ResultActions response = mockMvc.perform(post("/student/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(student)));

        response.andDo(print()).
                andExpect(status().isCreated())
                .andExpect(jsonPath("$.studentName").value(student.getStudentName()))
                .andDo(print());
    }

    @Test
    public void createStudentExceptionTest()throws Exception {
        Student student = new Student();
        student.setId(12);
        student.setStudentId(76127);
        student.setStudentName("Hari");
        student.setFatherName("Rama");
        student.setGender("Male");
        student.setMobile(9494968081L);
        student.setCategory("MCA");
        given(studentService.createStudent(any(StudentDTO.class)))
                .willReturn(null);

        ResultActions response = mockMvc.perform(post("/student/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(student)));

        response.andDo(print()).
                andExpect(status().is5xxServerError())
                .andDo(print());
    }

    @Test
    public void updateStudentTest()throws Exception {
        Student student = new Student();
        student.setId(12);
        student.setStudentId(76127);
        student.setStudentName("Hari");
        student.setFatherName("Rama");
        student.setGender("Male");
        student.setMobile(9494968081L);
        student.setCategory("MCA");
        given(studentService.updateStudent(anyInt(),any(Student.class)))
                .willReturn(student);

        ResultActions response = mockMvc.perform(put("/student/updateStudent/{studentId}",76127)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(student)));

        response.andDo(print()).
                andExpect(status().isCreated())
                .andExpect(jsonPath("$.studentName").value(student.getStudentName()))
                .andDo(print());
    }

    @Test
    public void updateStudentExceptionTest()throws Exception {
        Student student = new Student();
        student.setId(12);
        student.setStudentId(76127);
        student.setStudentName("Hari");
        student.setFatherName("Rama");
        student.setGender("Male");
        student.setMobile(9494968081L);
        student.setCategory("MCA");
        given(studentService.updateStudent(anyInt(),any(Student.class)))
                .willReturn(null);

        ResultActions response = mockMvc.perform(put("/student/updateStudent/{studentId}",76127)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(student)));

        response.andDo(print()).
                andExpect(status().is5xxServerError())
                .andDo(print());
    }

    @Test
    public void updateStudentByIdTest()throws Exception {
        Student student = new Student();
        student.setId(12);
        student.setStudentId(76127);
        student.setStudentName("Hari");
        student.setFatherName("Rama");
        student.setGender("Male");
        student.setMobile(9494968081L);
        student.setCategory("MCA");
        given(studentService.updateStudentById(anyInt(),any(Student.class)))
                .willReturn(student);

        ResultActions response = mockMvc.perform(put("/student/update/{id}",76127)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(student)));

        response.andDo(print()).
                andExpect(status().isCreated())
                .andExpect(jsonPath("$.studentName").value(student.getStudentName()))
                .andDo(print());
    }

    @Test
    public void updateStudentByIdExceptionTest()throws Exception {
        Student student = new Student();
        student.setId(12);
        student.setStudentId(76127);
        student.setStudentName("Hari");
        student.setFatherName("Rama");
        student.setGender("Male");
        student.setMobile(9494968081L);
        student.setCategory("MCA");
        given(studentService.updateStudentById(anyInt(),any(Student.class)))
                .willReturn(null);

        ResultActions response = mockMvc.perform(put("/student/update/{id}",76127)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(student)));

        response.andDo(print()).
                andExpect(status().is5xxServerError())
                .andDo(print());
    }

    @Test
    public void deleteStudentTest() throws Exception {
        doNothing().when(studentService).deleteByStudentId(76127);
        ResultActions response = mockMvc.perform(delete("/student/delete/{id}",10));

        response.andDo(print()).
                andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void deleteStudentExceptionTest() throws Exception {
        doThrow(new NullPointerException()).when(studentService).deleteByStudentId(10);
        ResultActions response = mockMvc.perform(delete("/student/delete/{id}",10));

        response.andDo(print()).
                andExpect(status().is5xxServerError())
                .andDo(print());
    }

    @Test
    public void deleteAllStudentsTest() throws Exception {
        doNothing().when(studentService).deleteAll();
        ResultActions response = mockMvc.perform(delete("/student/deleteAll"));

        response.andDo(print()).
                andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void deleteAllStudentsExceptionTest() throws Exception {
        doThrow(new NullPointerException()).when(studentService).deleteAll();
        ResultActions response = mockMvc.perform(delete("/student/deleteAll"));

        response.andDo(print()).
                andExpect(status().is5xxServerError())
                .andDo(print());
    }

    @Test
    public void readJsonTest() throws Exception {
        List<StudentDTO> studentDTOList = getStudentDtoData();
        when(studentService.readJson()).thenReturn(studentDTOList);
        mockMvc.perform(get("/student/readJson"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(studentDTOList.size()))
                .andDo(print());
    }

    @Test
    public void readJsonEmptyTest() throws Exception {
        when(studentService.readJson()).thenReturn(new ArrayList<>());
        mockMvc.perform(get("/student/readJson"))
                .andExpect(status().isNoContent())
                .andDo(print());
    }

    @Test
    public void readJsonExceptionTest() throws Exception {
        when(studentService.readJson()).thenReturn(null);
        mockMvc.perform(get("/student/readJson"))
                .andExpect(status().is5xxServerError())
                .andDo(print());
    }

    private List<StudentDTO> getStudentDtoData() {
        List<StudentDTO> studentDTOS = new ArrayList<>();

        StudentDTO dto = new StudentDTO();
        dto.setStudentName("Hari");

        studentDTOS.add(dto);

        return studentDTOS;
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
