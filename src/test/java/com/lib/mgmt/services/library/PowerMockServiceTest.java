package com.lib.mgmt.services.library;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(PowerMockRunner.class)
@PowerMockIgnore({"javax.management.*",
        "com.sun.org.apache.xerces.",
        "org.apache.http.conn.ssl.*",
        "com.amazonaws.http.conn.ssl.*", "javax.net.ssl.*",
        "javax.xml.", "org.xml.", "org.w3c.dom.","jdk.internal.reflect.*",
        "com.sun.org.apache.xalan.", "javax.activation.*",
        "javax.net.ssl.*","jdk.internal.reflect.*"})
@PrepareForTest({PowerMockService.class})
public class PowerMockServiceTest {

    @Test
    public void mockStaticMethodTest() {
        //Mock static method
        PowerMockito.mockStatic(PowerMockService.class);
        when(PowerMockService.staticMessage()).thenReturn("New Message from Mock!");
        String message = PowerMockService.staticMessage();
        assertEquals(message, "New Message from Mock!");
        //Verify static method invocation
        PowerMockito.verifyStatic(PowerMockService.class, times(1));
        PowerMockService.staticMessage();
    }

    @Test
    public void mockFinalMethodTest() {
        //Mock final method
        PowerMockService serviceMock = PowerMockito.mock(PowerMockService.class, Mockito
                .withSettings()
                .name("ServiceMock")
                .verboseLogging());

        when(serviceMock.finalMessage()).thenReturn("New Message from " +
                "Mock!");
        String message = serviceMock.finalMessage();
        assertEquals(message, "New Message from Mock!");
        verify(serviceMock).finalMessage();
    }

    @Test
    public void mockPrivateMethodTest() throws Exception {
        PowerMockService mock = PowerMockito.spy(new PowerMockService());
        PowerMockito.doReturn("New Message from Mock!").when(mock,
                "privateMessage");
        String privateMessage = Whitebox.invokeMethod(mock, "privateMessage");
        assertEquals(privateMessage, "New Message from Mock!");

        PowerMockito.verifyPrivate(mock, times(1)).invoke("privateMessage");
    }

}