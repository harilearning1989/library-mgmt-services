package com.lib.mgmt;

import com.lib.mgmt.mock.ClassWithPrivateMethods;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(PowerMockRunner.class)
@PrepareForTest(fullyQualifiedNames = "com.lib.mgmt.*")
@PowerMockIgnore({"javax.management.*",
        "com.sun.org.apache.xerces.",
        "org.apache.http.conn.ssl.*",
        "com.amazonaws.http.conn.ssl.*", "javax.net.ssl.*",
        "javax.xml.", "org.xml.", "org.w3c.dom.","jdk.internal.reflect.*",
        "com.sun.org.apache.xalan.", "javax.activation.*",
        "javax.net.ssl.*","jdk.internal.reflect.*"})
public class PowerMockPrivateMethodTest{
    @Test
    public void testClassWithPrivateMethods_printMessage_privateMethod() throws Exception {

        String message = "Hello PowerMockito";
        String expectation = "Expectation";

        ClassWithPrivateMethods mock = PowerMockito.spy(new ClassWithPrivateMethods());
        PowerMockito.doReturn(expectation).when(mock, "printMessage", message);

        String actual = mock.privateCall(message);
        assertEquals(expectation, actual);
    }
}
