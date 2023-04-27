package com.lib.mgmt.services.library;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import com.lib.mgmt.dtos.StudentDTO;
import com.lib.mgmt.exceptions.GlobalMessageException;
import com.lib.mgmt.models.library.Student;
import com.lib.mgmt.repos.library.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.lib.mgmt.utils.CommonUtils.isGreaterThan;

@Service("studentService")
public class StudentServiceImpl implements StudentService {

    private StudentRepository studentRepository;

    @Autowired
    public void setStudentRepository(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public String helloWorld() {
        return "Hello World";
    }

    public int getMax(int integer1, int integer2) {
        if (isGreaterThan(integer1, integer2)) {
            return integer1;
        }
        return integer2;
    }

    public int getMin(int integer1, int integer2) {
        return Math.min(integer1, integer2);
    }

    @Override
    public List<Student> findAll() {
        return studentRepository.findAll();
    }

    @Override
    public Optional<Student> findByStudentId(int studentId) {
        return studentRepository.findByStudentId(studentId);
    }


    @Override
    public Student createStudent(StudentDTO dto) {
        studentRepository.findByStudentId(dto.getStudentId()).ifPresent(s -> {
                    throw new GlobalMessageException("Student Already Exists", HttpStatus.CONFLICT);
                }
        );
        studentRepository.findByEmail(dto.getEmail()).ifPresent(s -> {
                    throw new GlobalMessageException("Email Already Exists", HttpStatus.CONFLICT);
                }
        );
        Student student = convertDtoToModel(dto);
        return studentRepository.save(student);
    }

    private Student convertDtoToModel(StudentDTO dto) {
        Student student = new Student();
        student.setStudentId(dto.getStudentId());
        student.setStudentName(dto.getStudentName());
        student.setEmail(dto.getEmail());
        student.setFatherName(dto.getFatherName());
        student.setGender(dto.getGender());
        student.setCategory(dto.getGender());
        return student;
    }

    @Override
    public Student updateStudent(int studentId, Student student) {
        Optional<Student> studentOpt = studentRepository.findByStudentId(studentId);
        if (studentOpt.isPresent()) {
            Student _student = studentOpt.get();
            return studentRepository.save(_student);
        }
        return null;
    }

    @Override
    public Student updateStudentPatch(int studentId, Map<String,Object> fields) {
        Optional<Student> studentOpt = studentRepository.findByStudentId(studentId);
        if (studentOpt.isPresent()) {
            Student _student = studentOpt.get();

            fields.forEach((key,value) -> {
                Field field = ReflectionUtils.findField(Student.class, key);
                assert field != null;
                field.setAccessible(true);
                ReflectionUtils.setField(field,_student,value);
            });
            return studentRepository.save(_student);
        }
        return null;
    }

    @Override
    public Student updateStudentById(int studentId, Student student) {
        Optional<Student> tutorialData = studentRepository.findByStudentId(studentId);
        if (tutorialData.isPresent()) {
            return tutorialData.get();
        }
        return null;
    }

    @Override
    @Transactional
    public void deleteByStudentId(int studentId) {
        studentRepository.deleteByStudentId(studentId);
    }

    @Override
    public void deleteAll() {
        studentRepository.deleteAll();
    }

    @Override
    @Transactional
    public boolean findByEmail(String email) {
        return studentRepository.findByEmail(email).isPresent();
    }

    @Override
    public void saveAll(List<StudentDTO> studentDTOS) {
        List<Student> studentList = convertToStudent(studentDTOS);
        studentRepository.saveAll(studentList);
    }

    private List<Student> convertToStudent(List<StudentDTO> studentDTOS) {
        return studentDTOS.stream().map(m -> {
            Student student = new Student();
            student.setStudentId(m.getStudentId());
            student.setStudentName(m.getStudentName());
            student.setFatherName(m.getFatherName());
            student.setCategory(m.getCategory());
            student.setGender(m.getGender());
            student.setMobile(m.getMobile());
            return student;
        }).collect(Collectors.toList());
    }

    @Override
    public List<StudentDTO> readJson() {
        List<StudentDTO> readStudentDto = null;
        try {
            String fixture = Resources.toString(Resources.getResource("EmployeeData.json"), Charsets.UTF_8);
            ObjectMapper objectMapper = new ObjectMapper();
            readStudentDto = objectMapper.readValue(fixture,
                    objectMapper.getTypeFactory().constructCollectionType(List.class, StudentDTO.class));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return readStudentDto;
    }

    @Override
    public long countStudents() {
        return studentRepository.count();
    }

    public List<Integer> sortIntegers(Collection<Integer> integerCollection) {
        return integerCollection.stream().sorted().collect(Collectors.toList());
    }

}
