package gasty

import com.jasty.core.Form
import com.jasty.core.ParameterProvider
import com.jasty.core.UploadedFile
import grails.test.GrailsUnitTestCase
import com.jasty.components.std.TextBox
import com.jasty.components.std.Button
import com.jasty.core.FormEngine

/**
 * Created with IntelliJ IDEA.
 * User: stas
 * Date: 8/26/13
 * Time: 11:49 PM
 * To change this template use File | Settings | File Templates.
 */
class ParameterResolvingMethodInvokerTests extends GrailsUnitTestCase{

    void testNotFound() {
        def invoker = new ParameterResolvingMethodInvoker(null)
        def form = new TestForm()
        try {
            invoker.invoke(form, new TestParameterProvider(map: [
                    eventHandler: "someMethod"
            ]))
            fail()
        }
        catch(NoSuchMethodException) {

        }
    }

    void testMethod1() {
        def invoker = new ParameterResolvingMethodInvoker(null)
        def form = new TestForm()
        invoker.invoke(form, new TestParameterProvider(map: [
                eventHandler: "method1"
        ]))
        assertEquals("method1", form.methodCalled)
    }

    void testMethod2() {
        def invoker = new ParameterResolvingMethodInvoker(null)
        def parameterProvider = new TestParameterProvider(map: [
                eventHandler: "method2",
                "myform.text": "test text",
                "myform.number1": "123",
                "myform.number2": "456",
                "myform.textBox": "textBox value"
        ])
        def form = new TestForm(id: "myform", formEngine: new FormEngine(parameterProvider, null, null, null))
        invoker.invoke(form, parameterProvider)
        assertEquals("method2", form.methodCalled)
        assertEquals("test text", form.text)
        assertEquals(123, form.number1)
        assertEquals(456, form.number2)
        assertNotNull(form.textBox)
        assertEquals("myform.textBox", form.textBox.id)
        assertEquals("textBox value", form.textBox.value)
        assertNotNull(form.btn)
        assertEquals("myform.btn", form.btn.id)
    }

    void testMethod3() {
        def invoker = new ParameterResolvingMethodInvoker(null)
        def parameterProvider = new TestParameterProvider(map: [
                eventHandler: "method3",
                "myform.text": "test text",
                "myform.textBox": "textBox value",
                "EVT.srcId": "myform_textBox",
                "EVT.data": "777"
        ])
        def form = new TestForm(id: "myform", formEngine: new FormEngine(parameterProvider, null, null, null))
        invoker.invoke(form, parameterProvider)
        assertEquals("method3", form.methodCalled)
        assertEquals("test text", form.text)
        assertEquals(777, form.number1)
        assertNotNull(form.textBox)
        assertEquals("myform.textBox", form.textBox.id)
        assertEquals("textBox value", form.textBox.value)
        assertNull(form.btn)
    }

    void testException() {
        def invoker = new ParameterResolvingMethodInvoker(null)
        def form = new TestForm(id: "myform")
        try {
            invoker.invoke(form, new TestParameterProvider(map: [
                    eventHandler: "method4",
                    "myform.data": "!!!"
            ]))
            fail()
        }
        catch(RuntimeException e) {
            assertEquals("!!!", e.message)
        }
    }

    class TestForm extends Form {

        String methodCalled
        String text
        int number1
        long number2
        TextBox textBox
        Button btn

        void method1() {
            methodCalled = "method1"
        }

        void method2(String text, int number1, long number2, TextBox textBox, Button btn) {
            methodCalled = "method2"
            this.text = text
            this.number1 = number1
            this.number2 = number2
            this.textBox = textBox
            this.btn = btn
        }

        void method3(TextBox eventSource, int eventData, String text) {
            methodCalled = "method3"
            this.text = text
            this.number1 = eventData
            this.textBox = eventSource
        }

        void method4(String data) {
            throw new RuntimeException(data)
        }

    }

    class TestParameterProvider implements ParameterProvider {

        Map<String, String> map

        @Override
        String getParameter(String name) {
            map[name]
        }

        @Override
        UploadedFile getFile(String name) {
            null
        }

        @Override
        Collection<String> getParameterNames() {
            map.keySet()
        }

        @Override
        String[] getParameterValues(String name) {
            return new String[0]
        }
    }

}
