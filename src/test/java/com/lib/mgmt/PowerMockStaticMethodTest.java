package com.lib.mgmt;

import com.lib.mgmt.mock.ClassWithStaticMethod;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest(fullyQualifiedNames = "com.lib.mgmt.*")
@PowerMockIgnore({"javax.management.*",
        "com.sun.org.apache.xerces.",
        "org.apache.http.conn.ssl.*",
        "com.amazonaws.http.conn.ssl.*", "javax.net.ssl.*",
        "javax.xml.", "org.xml.", "org.w3c.dom.","jdk.internal.reflect.*",
        "com.sun.org.apache.xalan.", "javax.activation.*",
        "javax.net.ssl.*","jdk.internal.reflect.*"})
public class PowerMockStaticMethodTest{
    @Test
    public void testClassWithStaticMethod_printMessage_staticMethod() {

        String message = "Hello PowerMockito"; //1
        String expectation = "Expectation"; //2

        PowerMockito.mockStatic(ClassWithStaticMethod.class); //3
        PowerMockito.when(ClassWithStaticMethod.printMessage(message)).thenReturn(expectation); //4
        String actual = ClassWithStaticMethod.printMessage("Hello PowerMockito"); //5
        Assert.assertEquals(expectation, actual); //6
    }
}
