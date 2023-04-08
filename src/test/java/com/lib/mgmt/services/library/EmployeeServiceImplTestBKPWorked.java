package com.lib.mgmt.services.library;

import com.lib.mgmt.dtos.EmployeeDTO;
import com.lib.mgmt.models.library.Employee;
import com.lib.mgmt.utils.EmployeeUtils;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

//@SpringBootTest
//@RunWith(PowerMockRunner.class)
//@PrepareForTest(EmployeeServiceImpl.class)
//@PrepareForTest({EmployeeServiceImpl.class, EmployeeUtils.class})

@RunWith(PowerMockRunner.class)
@PrepareForTest(fullyQualifiedNames = "com.web.emp.*")
@PowerMockIgnore({"javax.management.*",
        "com.sun.org.apache.xerces.",
        "org.apache.http.conn.ssl.*",
        "com.amazonaws.http.conn.ssl.*", "javax.net.ssl.*",
        "javax.xml.", "org.xml.", "org.w3c.dom.","jdk.internal.reflect.*",
        "com.sun.org.apache.xalan.", "javax.activation.*",
        "javax.net.ssl.*","jdk.internal.reflect.*"})
@Ignore
public class EmployeeServiceImplTestBKPWorked {

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    @Test
    public void fetchEmployee() throws Exception {
        final EmployeeServiceImpl spiedEmpService = PowerMockito.spy(employeeService);
        final Employee employee = new Employee();
        employee.setEmpName("Dexter");
        employee.setFatherName("Morgan");
        PowerMockito.doReturn("DM").when(spiedEmpService, "getEmployeeInitials", ArgumentMatchers.anyString(), ArgumentMatchers.anyString());
        final EmployeeDTO employeeDto = spiedEmpService.fetchEmployee(employee);
        assertNotNull(employeeDto);
        assertEquals("DM", employeeDto.getDesignation());
    }

    @Test
    public void fetchEmployee_static() throws Exception {
        /*String message = "Hello PowerMockito";
        String expectation = "Expectation";
        PowerMockito.mockStatic(ClassWithStaticMethod.class);
        PowerMockito.when(ClassWithStaticMethod.printMessage(message)).thenReturn(expectation);
        String actual = ClassWithStaticMethod.printMessage("Hello PowerMockito");
        assertEquals(expectation, actual); */


        PowerMockito.mockStatic(EmployeeUtils.class);
        PowerMockito.when(EmployeeUtils.getInitials("Dexter", "Morgan")).thenReturn("DM");
        String result = EmployeeUtils.getInitials("Dexter", "Morgan");
        assertEquals("DM", result);
        /*final Employee employee = new Employee();
        employee.setEmpName("Dexter");
        employee.setFatherName("Morgan");

        final EmployeeDTO employeeDto = employeeService.fetchEmployeeStatically(employee);
        assertNotNull(employeeDto);
        assertEquals("DM", employeeDto.getDesignation());*/
    }

    /*@Test
    public void fetchEmployee() throws Exception {
        final EmployeeService spiedEmpService = PowerMockito.spy(employeeService);
        final Employee employee = new Employee();
        employee.setFName("Dexter");
        employee.setLName("Morgan");
        PowerMockito.doReturn("DM").when(spiedEmpService, "getEmployeeInitials", ArgumentMatchers.anyString(), ArgumentMatchers.anyString());
        final EmployeeDto employeeDto = spiedEmpService.fetchEmployee(employee);
        Assert.assertNotNull(employeeDto);
        Assert.assertEquals("DM", employeeDto.getInitials());
    }*/

    /*@Test
    public void updateFindAllTest() throws Exception {
        employeeService = PowerMockito.spy(employeeService);

        List<Integer> managerIds = new ArrayList<>();
        doReturn(managerIds).when(employeeService, "getMangerIds", anyObject());
        employeeService.updateFindAll();
    }*/
}