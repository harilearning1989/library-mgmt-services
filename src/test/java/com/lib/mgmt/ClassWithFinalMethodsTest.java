package com.lib.mgmt;

import com.lib.mgmt.mock.ClassWithFinalMethods;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
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
public class ClassWithFinalMethodsTest {
    @Test
    public void testClassWithFinalMethods_printMessage_finalMethod() throws Exception {

        String message = "Hello PowerMockito1"; //1

        ClassWithFinalMethods mockObject = PowerMockito.mock(ClassWithFinalMethods.class); //2
        PowerMockito.whenNew(ClassWithFinalMethods.class).withNoArguments().thenReturn(mockObject); //3

        ClassWithFinalMethods object = new ClassWithFinalMethods(); //4
        PowerMockito.verifyNew(ClassWithFinalMethods.class).withNoArguments(); //5

        PowerMockito.when(object.printMessage(message)).thenReturn(message); //6
        String helloPowerMockito = object.printMessage(message); //7
        Mockito.verify(object).printMessage(message); //8
        Assert.assertEquals(message, helloPowerMockito); //9
    }
}