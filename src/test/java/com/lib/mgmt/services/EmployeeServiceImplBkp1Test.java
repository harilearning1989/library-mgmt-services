package com.lib.mgmt.services;

import com.lib.mgmt.models.library.Employee;
import com.lib.mgmt.repos.library.EmployeeRepo;
import org.junit.Ignore;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@Ignore
public class EmployeeServiceImplBkp1Test {

    @Mock
    private EmployeeRepo employeeRepo;

    @InjectMocks
    private EmployeeServiceImpl employeeServiceImpl;


    @Test
    void createEmployee() {
        Employee emp = getRequestData();
        Employee resp = getResponseData();
        when(employeeRepo.save(emp)).thenReturn(resp);

        Employee empServiceResp = employeeServiceImpl.createEmployee(emp);
        assertEquals(resp,empServiceResp);
    }


    @Test
    void updateEmp() {
        Employee emp = getRequestData();
        Employee resp = getResponseData();
        when(employeeRepo.findByEmpId(76127)).thenReturn(Optional.empty());
        when(employeeRepo.save(emp)).thenReturn(resp);

        Employee empServiceResp = employeeServiceImpl.updateEmp(76127,emp);
        assertEquals(resp,empServiceResp);
    }

    @Test
    void updateEmpWithFindEmpty() {
        Employee emp = getRequestData();
        Employee resp = getResponseData();
        when(employeeRepo.findByEmpId(76127)).thenReturn(Optional.empty());
        when(employeeRepo.save(emp)).thenReturn(resp);

        Employee empServiceResp = employeeServiceImpl.updateEmp(76127,emp);
        assertEquals(resp,empServiceResp);
    }

    @Test
    void updateEmpWithFind() {
        Employee emp = getRequestData();
        Employee resp = getResponseData();
        when(employeeRepo.findByEmpId(76127)).thenReturn(Optional.ofNullable(resp));
        when(employeeRepo.save(emp)).thenReturn(resp);

        Employee empServiceResp = employeeServiceImpl.updateEmp(76127,emp);
        assertEquals(resp,empServiceResp);
    }

    public void testCreateUserWhenSaved()    {
        when(employeeRepo.findByEmpId(44444)).thenReturn(null);
        Employee newUser=new Employee();
        when(employeeRepo.save(Mockito.any(Employee.class))).thenReturn(newUser);
        Employee returnedUser=employeeServiceImpl.createEmployee(newUser);
        verify(employeeRepo, times(1)).save(Mockito.any(Employee.class));
    }

    @Test
    void findAllEmpty() {
        when(employeeRepo.findAll()).thenReturn(new ArrayList<>());
        List<Employee> empList = employeeServiceImpl.findAll();
        assertEquals(empList,new ArrayList<>());
    }

    @Test
    void findAll() {
        List<Employee> empData = getEmployeeList();
        when(employeeRepo.findAll()).thenReturn(empData);
        List<Employee> empList = employeeServiceImpl.findAll();
        assertEquals(empList,empData);
        assertEquals(empList.size(),empData.size());
    }

    @Test
    void findById() {
        when(employeeRepo.findByEmpId(10)).thenReturn(Optional.empty());
        Optional<Employee> empOpt = employeeServiceImpl.findById(10);
        assertEquals(empOpt,Optional.empty());
    }

    @Test
    void findByIdWithEmpId() {
        Employee emp = new Employee();
        emp.setEmpId(76127);
        emp.setEmpName("Hari");
        when(employeeRepo.findByEmpId(76127)).thenReturn(Optional.ofNullable(emp));

        Optional<Employee> empOpt = employeeServiceImpl.findById(76127);
        assertEquals(empOpt,Optional.ofNullable(emp));
        assertEquals(empOpt.get().getEmpId(),Optional.ofNullable(emp).get().getEmpId());
    }

    @Test
    void findAllByEmpId() {
        Employee empData = getRequestData();
        when(employeeRepo.findAllByEmpId(76127)).thenReturn(Optional.ofNullable(empData));

        Optional<Employee> empOpt = employeeServiceImpl.findAllByEmpId(76127);
        assertEquals(empOpt,Optional.ofNullable(empData));
    }

    @DisplayName("JUnit test for deleteEmployee method")
    @Test
    public void givenEmployeeId_whenDeleteEmployee_thenNothing(){
        int employeeId = 1;
        willDoNothing().given(employeeRepo).deleteById(employeeId);
        employeeServiceImpl.deleteById(employeeId);
        verify(employeeRepo, times(1)).deleteById(employeeId);
    }

    @DisplayName("JUnit test for delete all method")
    @Test
    public void deleteAllTest(){
        willDoNothing().given(employeeRepo).deleteAll();
        employeeServiceImpl.deleteAll();
        verify(employeeRepo, times(1)).deleteAll();
    }

    private List<Employee> getEmployeeList() {
        List<Employee> empList = new ArrayList<>();

        Employee emp = new Employee();
        emp.setId(12);
        emp.setEmpName("Hari");
        emp.setEmpId(76127);
        empList.add(emp);

        emp = new Employee();
        emp.setId(13);
        emp.setEmpName("Pramod");
        emp.setEmpId(75127);
        empList.add(emp);
        return empList;
    }

    private Employee getRequestData() {
        Employee emp = new Employee();
        emp.setId(12);
        emp.setEmpName("Hari");
        emp.setEmpId(76127);
        emp.setFatherName("Krishna");
        emp.setGender("Male");
        emp.setCategory("OC");
        emp.setMobile(9494968081L);
        emp.setManager_id(65473);

        return emp;
    }

    private Employee getResponseData() {
        Employee emp = new Employee();
        emp.setId(12);
        emp.setEmpName("Hari");
        emp.setEmpId(76127);
        emp.setFatherName("Krishna");
        emp.setGender("Male");
        emp.setCategory("OC");
        emp.setMobile(9494968081L);
        emp.setManager_id(65473);

        return emp;
    }

}