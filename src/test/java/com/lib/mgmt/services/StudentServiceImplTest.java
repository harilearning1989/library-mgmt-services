package com.lib.mgmt.services;

import com.lib.mgmt.utils.CommonUtils;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

@RunWith(PowerMockRunner.class)
@PrepareForTest({CommonUtils.class})
public class StudentServiceImplTest {

    private StudentServiceImpl studentServiceImpl;

    @Before
    public void setup() {
        studentServiceImpl = new StudentServiceImpl();
    }

    @Test
    public void helloWorld() {
        assertEquals("Hello World",studentServiceImpl.helloWorld());
    }

    @Test
    public void getMax() {
        mockStatic(CommonUtils.class);
        when(CommonUtils.isGreaterThan(anyInt(),anyInt())).thenReturn(true);
        assertEquals(2,studentServiceImpl.getMax(2,1));
    }

    @Test
    public void getMin() {
        assertEquals(1,studentServiceImpl.getMin(2,1));
    }

    @Test
    public void findAll() {
    }
}