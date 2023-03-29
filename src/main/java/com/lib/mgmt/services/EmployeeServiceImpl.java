package com.lib.mgmt.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import com.lib.mgmt.dtos.EmployeeDTO;
import com.lib.mgmt.models.Employee;
import com.lib.mgmt.repos.EmployeeRepo;
import com.lib.mgmt.utils.EmployeeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepo employeeRepo;

    public void setEmployeeRepo(EmployeeRepo employeeRepo) {
        this.employeeRepo = employeeRepo;
    }

    @Override
    public List<Employee> findAll() {
        return employeeRepo.findAll();
    }

    @Override
    public Optional<Employee> findAllByEmpId(int empId) {
        return employeeRepo.findAllByEmpId(empId);
    }

    @Override
    public Employee createEmployee(Employee employee) {
        return employeeRepo.save(employee);
    }

    @Override
    public Optional<Employee> findById(int empId) {
        return employeeRepo.findByEmpId(empId);
    }

    @Override
    public void deleteById(int empId) {
        employeeRepo.deleteById(empId);
    }

    @Override
    public void deleteAll() {
        employeeRepo.deleteAll();
    }

    @Override
    public Employee updateEmp(int empId, Employee employee) {
        Optional<Employee> emp = employeeRepo.findByEmpId(empId);
        if (emp.isPresent()) {
            Employee _emp = emp.get();
            _emp.setEmpName(employee.getEmpName());
            return employeeRepo.save(_emp);
        } else {
            return employeeRepo.save(employee);
        }
    }

    @Override
    public List<EmployeeDTO> updateFindAll() {
        List<Employee> allEmp = employeeRepo.findAll();
        List<Integer> managerIds = getMangerIds(allEmp);
        List<String> designations = getDesignations();

        return allEmp.stream()
                .map(m -> {
                    EmployeeDTO dto = convertToDto(m);
                    if (!managerIds.contains(dto.getEmpId())) {
                        int index = getRandomNumber(0, 8);
                        dto.setDesignation(designations.get(index));
                        int salary = getRandomNumber(10000, 70000);
                        dto.setSalary(salary);
                    } else {
                        dto.setDesignation(designations.get(10));
                        int salary = getRandomNumber(80000, 99000);
                        dto.setSalary(salary);
                    }
                    return dto;
                }).collect(Collectors.toList());
    }

    @Override
    public List<EmployeeDTO> readJson() {
        List<EmployeeDTO> readEmpDto = null;
        try {
            String fixture = Resources.toString(Resources.getResource("EmployeeData.json"), Charsets.UTF_8);
            ObjectMapper objectMapper = new ObjectMapper();
            readEmpDto = objectMapper.readValue(fixture,
                    objectMapper.getTypeFactory().constructCollectionType(List.class, EmployeeDTO.class));

            readEmpDto.stream().forEach(f -> {
                Optional<Employee> empOpt = employeeRepo.findByEmpId(f.getEmpId());
                if (empOpt.isPresent()) {
                    System.out.println(f.getEmpId());
                    Employee emp = empOpt.get();
                    //if(f.getSalary() != null){
                    emp.setSalary(f.getSalary());
                    //}
                    emp.setDesignation(f.getDesignation());
                    employeeRepo.save(emp);
                }
            });

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return readEmpDto;
    }

    @Override
    public List<EmployeeDTO> findAllUnderManager(int managerId) {
        List<Employee> allEmp = employeeRepo.findAll();
        return getEmployeesUnderManager(managerId, allEmp);
    }

    @Override
    public String helloWorld() {
        return "Hello World";
    }

    private List<EmployeeDTO> getEmployeesUnderManager(int managerId, List<Employee> allEmp) {
        return allEmp.stream()
                .filter(f -> f.getManager_id() == managerId)
                .map(m -> convertToDto(m))
                .collect(Collectors.toList());
    }

    public EmployeeDTO fetchEmployee(final Employee employee) {
        final EmployeeDTO employeeDto = new EmployeeDTO();
        employeeDto.setEmpName(employee.getEmpName());
        employeeDto.setFatherName(employee.getFatherName());
        employeeDto.setDesignation(getEmployeeInitials(employee.getEmpName(), employee.getFatherName()));
        return employeeDto;
    }

    public EmployeeDTO fetchEmployeeStatically(final Employee employee) {
        EmployeeDTO employeeDto = new EmployeeDTO();
        employeeDto.setEmpName(employee.getEmpName());
        employeeDto.setFatherName(employee.getFatherName());
        employeeDto.setDesignation(EmployeeUtils.getInitials(employee.getEmpName(), employee.getFatherName()));
        return employeeDto;
    }

    private String getEmployeeInitials(final String fName, final String lName) {
        return fName + lName;
    }

    private int getRandomNumber(int min, int max) {
        Random r = new Random();
        int result = r.nextInt(max - min) + min;
        return result;
    }

    private EmployeeDTO convertToDto(Employee m) {
        EmployeeDTO dto = new EmployeeDTO();
        dto.setId(m.getId());
        dto.setEmpId(m.getEmpId());
        dto.setEmpName(m.getEmpName());
        dto.setFatherName(m.getFatherName());
        dto.setCategory(m.getCategory());
        dto.setGender(m.getGender());
        dto.setMobile(m.getMobile());
        dto.setManager_id(m.getManager_id());
        dto.setSalary(m.getSalary());
        dto.setDesignation(m.getDesignation());

        return dto;
    }

    private List<String> getDesignations() {
        List<String> designations = new ArrayList<>();
        designations.add("Associate Consultant");
        designations.add("Software Engineer");
        designations.add("Senior Software Engineer");
        designations.add("Software Developer");
        designations.add("Senior Software Developer");
        designations.add("Technical Lead");
        designations.add("Senior Technical Lead");
        designations.add("Architect");
        designations.add("Senior Architect");
        designations.add("Associate Manager");
        designations.add("Senior Manager");
        designations.add("Group Manager");

        return designations;
    }

    private List<Integer> getMangerIds(List<Employee> allEmp) {
        return allEmp.stream()
                .map(m -> m.getManager_id())
                .distinct()
                .collect(Collectors.toList());
    }


    public String getDetails() {
        return "Mock private method example: " + iAmPrivate();
    }

    private String iAmPrivate() {
        return new Date().toString();
    }
}
