package com.lib.mgmt.services;

import com.lib.mgmt.dtos.EmployeeDTO;
import com.lib.mgmt.models.Employee;
import com.lib.mgmt.repos.EmployeeRepo;
import com.lib.mgmt.utils.EmployeeUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest(fullyQualifiedNames = "com.web.emp.*")
@PowerMockIgnore({"javax.management.*",
        "com.sun.org.apache.xerces.",
        "org.apache.http.conn.ssl.*",
        "com.amazonaws.http.conn.ssl.*", "javax.net.ssl.*",
        "javax.xml.", "org.xml.", "org.w3c.dom.","jdk.internal.reflect.*",
        "com.sun.org.apache.xalan.", "javax.activation.*",
        "javax.net.ssl.*","jdk.internal.reflect.*"})
public class EmployeeServiceImplTestMostImportant {
    @Mock
    private EmployeeRepo employeeRepo;
    @InjectMocks
    private EmployeeServiceImpl employeeServiceImpl;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void findAllTest() {
        List<Employee> empData = getEmployeeList();
        when(employeeRepo.findAll()).thenReturn(empData);

        List<Employee> empList = employeeServiceImpl.findAll();
        assertEquals(empList,empData);
        assertEquals(empList.size(),empData.size());
    }

    @Test
    public void findAllByEmpIdTest() {
        int empId = 76127;
        Optional<Employee> empData = findAllByEmpIdData();
        when(employeeRepo.findAllByEmpId(empId)).thenReturn(empData);

        Optional<Employee> empDataOpt = employeeServiceImpl.findAllByEmpId(empId);
        assertEquals(empData.get(),empDataOpt.get());
    }

    @Test
    public void createEmployeeTest(){
        Employee emp = createEmployeeRequestData();
        when(employeeRepo.save(emp)).thenReturn(emp);
        Employee empResp = employeeServiceImpl.createEmployee(emp);
        assertEquals(empResp,emp);
    }

    @Test
    public void findByIdTest(){
        int empId = 76127;
        Optional<Employee> empData = findAllByEmpIdData();
        when(employeeRepo.findByEmpId(empId)).thenReturn(empData);
        Optional<Employee> empDataOpt = employeeServiceImpl.findById(empId);
        assertEquals(empData.get(),empDataOpt.get());
    }

    @Test
    public void findAllUnderManagerTest() throws Exception {
        List<Employee> empData = getManagerData();
        when(employeeRepo.findAll()).thenReturn(empData);

        List<EmployeeDTO> empDataFilter = getManagerDataFilter();
        final EmployeeServiceImpl spiedEmpService = PowerMockito.spy(employeeServiceImpl);
        PowerMockito.doReturn(empDataFilter).when(spiedEmpService, "getEmployeesUnderManager", anyInt(), anyList());

        int managerId = 76127;
        List<EmployeeDTO> empList = employeeServiceImpl.findAllUnderManager(managerId);

        assertEquals(empDataFilter.size(),empList.size());
    }

    @Test
    public void deleteByIdTest(){
        Employee emp = new Employee();
        emp.setEmpName("Test Name");
        emp.setId(10);

        when(employeeRepo.findById(emp.getId())).thenReturn(Optional.of(emp));

        employeeServiceImpl.deleteById(emp.getId());
        verify(employeeRepo).deleteById(emp.getId());
    }

    @Test
    public void deleteByIdTestTemp() {
        Employee entity = new Employee();
        entity.setEmpName("Test Name");
        entity.setId(10);
        Optional<Employee> optionalEntityType = Optional.of(entity);
        when(employeeRepo.findById(10)).thenReturn(optionalEntityType);

        employeeServiceImpl.deleteById(entity.getId());

        verify(employeeRepo, times(1)).deleteById(entity.getId());
        //assertThat(employeeRepo.findById(entity.getId()).get()).isNull();
    }

    @DisplayName("JUnit test for deleteEmployee method")
    @Test
    public void deleteEmployeeThenNothing(){
        int employeeId = 10;
        willDoNothing().given(employeeRepo).deleteById(employeeId);
        employeeServiceImpl.deleteById(employeeId);
        verify(employeeRepo, times(1)).deleteById(employeeId);
    }

    @DisplayName("DeleteAll method")
    @Test
    public void deleteAllTest(){
        willDoNothing().given(employeeRepo).deleteAll();
        employeeServiceImpl.deleteAll();
        verify(employeeRepo, times(1)).deleteAll();
    }

    @DisplayName("update Emp Not Empty method")
    @Test
    public void updateEmpNotEmptyTest(){
        Employee emp = new Employee();
        emp.setEmpName("Test Name");
        emp.setId(10);
        emp.setEmpId(76127);

        when(employeeRepo.findByEmpId(emp.getEmpId())).thenReturn(Optional.of(emp));
        when(employeeRepo.save(emp)).thenReturn(emp);

        Employee empResp = employeeServiceImpl.updateEmp(emp.getEmpId(),emp);

        assertEquals(emp,empResp);
    }

    @DisplayName("update Emp Empty method")
    @Test
    public void updateEmpEmptyTest(){
        Employee emp = new Employee();
        emp.setEmpName("Test Name");
        emp.setId(10);
        emp.setEmpId(76127);

        when(employeeRepo.findByEmpId(emp.getEmpId())).thenReturn(Optional.empty());
        when(employeeRepo.save(emp)).thenReturn(emp);

        Employee empResp = employeeServiceImpl.updateEmp(emp.getEmpId(),emp);

        assertEquals(emp,empResp);
    }

    @DisplayName("update Emp updateFindAllTest method")
    @Test
    public void updateFindAllTest() throws Exception {
        List<Employee> empData = updateEmployeeData();
        when(employeeRepo.findAll()).thenReturn(empData);
        List<Integer> managerIds = getMangerIds();
        final EmployeeServiceImpl spiedEmpService = PowerMockito.spy(employeeServiceImpl);
        PowerMockito.doReturn(managerIds).when(spiedEmpService, "getMangerIds", anyList());

        List<String> designations = getDesignations();
        PowerMockito.doReturn(designations).when(spiedEmpService, "getDesignations");

        List<EmployeeDTO> empResp = employeeServiceImpl.updateFindAll();

        assertEquals(empData.size(),empResp.size());
    }

    @DisplayName("update Emp getDetailsTest method")
    @Test
    public void getDetailsTest() throws Exception {
        String privateResp = "World";
        final EmployeeServiceImpl spiedEmpService = PowerMockito.spy(employeeServiceImpl);
        PowerMockito.doReturn(privateResp).when(spiedEmpService, "iAmPrivate");

        String message = spiedEmpService.getDetails();
        assertEquals("Mock private method example: "+privateResp,message);
    }

    @DisplayName("update Emp getDetailsTest method")
    @Test
    public void readJsonTest(){
        Employee emp = new Employee();
        emp.setEmpName("Test Name");
        emp.setId(10);
        emp.setEmpId(76127);
        when(employeeRepo.findByEmpId(emp.getEmpId())).thenReturn(Optional.of(emp));
        List<EmployeeDTO> empDtoList = employeeServiceImpl.readJson();
        assertEquals(30000,empDtoList.size());
    }

    @DisplayName("update Emp getDetailsTest method")
    @Test(expected = Exception.class)
    public void readJsonExceptionTest(){
        Employee emp = new Employee();
        emp.setEmpName("Test Name");
        emp.setId(10);
        emp.setEmpId(76127);
        when(employeeRepo.findByEmpId(emp.getEmpId())).thenReturn(Optional.of(emp));
        //when(employeeRepo.save(emp)).thenThrow(Exception.class);
        List<EmployeeDTO> empDtoList = employeeServiceImpl.readJson();
    }

    @DisplayName("update Emp fetchEmployeeTest method")
    @Test
    public void fetchEmployeeTest() throws Exception {
        Employee emp = new Employee();
        String empName = "Hari";
        String fatherName = "Krishna";
        emp.setEmpName(empName);
        emp.setFatherName(fatherName);

        String designation = empName+fatherName;
        final EmployeeServiceImpl mock = PowerMockito.spy(employeeServiceImpl);
        PowerMockito.doReturn(designation).when(mock, "getEmployeeInitials", anyString(),anyString());

        EmployeeDTO expected = new EmployeeDTO();
        expected.setEmpName(empName);
        expected.setFatherName(fatherName);
        expected.setDesignation(empName+fatherName);

        EmployeeDTO response = employeeServiceImpl.fetchEmployee(emp);

        assertEquals(expected.getDesignation(),response.getDesignation());
    }

    @DisplayName("update Emp fetchEmployeeStaticallyTest method")
    @Test
    public void fetchEmployeeStaticallyTest(){
        Employee emp = new Employee();
        String empName = "Hari";
        String fatherName = "Krishna";
        emp.setEmpName(empName);
        emp.setFatherName(fatherName);

        PowerMockito.mockStatic(EmployeeUtils.class);
        PowerMockito.when(EmployeeUtils.getInitials(empName,fatherName)).thenReturn(empName+fatherName);

        EmployeeDTO expected = new EmployeeDTO();
        expected.setEmpName(empName);
        expected.setFatherName(fatherName);
        expected.setDesignation(empName+fatherName);
        EmployeeDTO response = employeeServiceImpl.fetchEmployeeStatically(emp);

        assertEquals(expected.getDesignation(),response.getDesignation());
    }

    private List<Integer> getMangerIds() {
       return Arrays.asList(76127,76129,76567);
    }

    private List<String> getDesignations() {
        List<String> designations = new ArrayList<>();
        designations.add("Senior Architect");
        designations.add("Associate Manager");
        designations.add("Senior Manager");

        return designations;
    }

    private Employee createEmployeeRequestData() {
        Employee emp = new Employee();
        emp.setId(12);
        emp.setEmpName("Chandra");
        emp.setManager_id(76127);
        return emp;
    }
    private Optional<Employee> findAllByEmpIdData() {
        Employee emp = new Employee();
        emp.setId(12);
        emp.setEmpName("Chandra");
        emp.setManager_id(76127);
        return Optional.ofNullable(emp);
    }

    private List<EmployeeDTO> getManagerDataFilter() {
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

        return empList;
    }

    private List<Employee> getManagerData() {
        List<Employee> empList = new ArrayList<>();

        Employee emp = new Employee();
        emp.setId(12);
        emp.setEmpName("Chandra");
        emp.setManager_id(76127);
        empList.add(emp);

        emp = new Employee();
        emp.setId(13);
        emp.setEmpName("Pramod");
        emp.setManager_id(76127);
        empList.add(emp);

        emp = new Employee();
        emp.setId(14);
        emp.setEmpName("Bablu");
        emp.setManager_id(76128);
        empList.add(emp);

        emp = new Employee();
        emp.setId(15);
        emp.setEmpName("Josh");
        emp.setManager_id(76129);
        empList.add(emp);

        emp = new Employee();
        emp.setId(15);
        emp.setEmpId(76127);
        emp.setEmpName("Josh");
        emp.setManager_id(76129);
        empList.add(emp);

        return empList;
    }

    private List<Employee> updateEmployeeData() {
        List<Employee> empList = new ArrayList<>();

        Employee emp = new Employee();
        emp.setId(12);
        emp.setEmpName("Chandra");
        emp.setManager_id(76127);
        empList.add(emp);

        emp = new Employee();
        emp.setId(13);
        emp.setEmpName("Pramod");
        emp.setManager_id(76127);
        empList.add(emp);

        emp = new Employee();
        emp.setId(14);
        emp.setEmpId(76127);
        emp.setEmpName("Bablu");
        emp.setManager_id(76128);
        empList.add(emp);

        emp = new Employee();
        emp.setId(15);
        emp.setEmpName("Josh");
        emp.setManager_id(76129);
        empList.add(emp);

        return empList;
    }

    private List<Employee> getEmployeeList() {
        List<Employee> empList = new ArrayList<>();

        Employee emp = new Employee();
        emp.setId(12);
        emp.setEmpName("Chandra");
        emp.setEmpId(76127);
        empList.add(emp);

        emp = new Employee();
        emp.setId(13);
        emp.setEmpName("Pramod");
        emp.setEmpId(75127);
        empList.add(emp);
        return empList;
    }
}