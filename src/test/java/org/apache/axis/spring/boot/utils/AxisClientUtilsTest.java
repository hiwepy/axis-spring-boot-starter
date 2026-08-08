/*
 * Copyright (c) 2018, hiwepy (https://github.com/hiwepy).
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.apache.axis.spring.boot.utils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.lang.reflect.Field;

import javax.xml.namespace.QName;
import javax.xml.rpc.ParameterMode;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.encoding.XMLType;
import org.apache.axis.spring.boot.Parameter;
import org.apache.axis.spring.boot.handler.InvokeHandler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link AxisClientUtils}.
 *
 * <p>The static {@code service} field is swapped for a Mockito mock before each
 * test and restored afterwards so the production field is never mutated beyond
 * the test run.</p>
 *
 * @author [@Loong Wan](https://github.com/loong10k)
 * @since 1.0.0
 */
@DisplayName("AxisClientUtils Tests")
class AxisClientUtilsTest {

    private static final QName STRING = XMLType.XSD_STRING;
    private static final QName OPERATION = new QName("http://example.com", "doStuff");

    private Service originalService;
    private Service mockService;
    private Call mockCall;

    @BeforeEach
    void setUp() throws Exception {
        originalService = getStaticService();
        mockService = mock(Service.class);
        mockCall = mock(Call.class);
        when(mockService.createCall()).thenReturn(mockCall);
        setStaticService(mockService);
    }

    @AfterEach
    void tearDown() throws Exception {
        // restore the original static service so other tests are unaffected
        setStaticService(originalService);
    }

    @Test
    @DisplayName("invoke builds and executes the Call for a single parameter")
    void testInvokeWithSingleParameter() throws Exception {
        Parameter param = new Parameter("user", STRING, "jack");
        when(mockCall.invoke(any(Object[].class))).thenReturn("result");

        Object result = AxisClientUtils.invoke("http://example.com/endpoint", OPERATION, true, STRING, param);

        assertThat(result).isEqualTo("result");
        verify(mockCall).setTargetEndpointAddress(any(java.net.URL.class));
        verify(mockCall).setOperationName(OPERATION);
        verify(mockCall).addParameter(eq("user"), eq(STRING), eq(ParameterMode.IN));
        verify(mockCall).setReturnType(STRING);
        verify(mockCall).setUseSOAPAction(true);
        verify(mockCall).invoke(any(Object[].class));
    }

    @Test
    @DisplayName("invoke registers every parameter and forwards invocation")
    void testInvokeWithMultipleParameters() throws Exception {
        Parameter first = new Parameter("a", STRING, "1");
        Parameter second = new Parameter("b", STRING, "2", ParameterMode.INOUT);
        when(mockCall.invoke(any(Object[].class))).thenReturn(42);

        Object result = AxisClientUtils.invoke("http://example.com/svc", OPERATION, false, STRING, first, second);

        assertThat(result).isEqualTo(42);
        verify(mockCall).addParameter(eq("a"), eq(STRING), eq(ParameterMode.IN));
        verify(mockCall).addParameter(eq("b"), eq(STRING), eq(ParameterMode.INOUT));
        verify(mockCall).setUseSOAPAction(false);
    }

    @Test
    @DisplayName("invoke with InvokeHandler delegates to the handler")
    void testInvokeWithHandler() throws Exception {
        Parameter param = new Parameter("user", STRING, "jack");
        InvokeHandler<String> handler = mock(InvokeHandler.class);
        when(handler.handleCall(eq(mockCall), any(Object[].class))).thenReturn("handled");

        String result = AxisClientUtils.invoke("http://example.com/handler", handler, param);

        assertThat(result).isEqualTo("handled");
        verify(mockCall).setTargetEndpointAddress(any(java.net.URL.class));
        // handler variant does not set operation name / return type / SOAP action
        verify(mockCall).addParameter(eq("user"), eq(STRING), eq(ParameterMode.IN));
        verify(handler).handleCall(eq(mockCall), any(Object[].class));
    }

    @Test
    @DisplayName("invoke with InvokeHandler supports empty parameter list")
    void testInvokeWithHandlerNoParams() throws Exception {
        InvokeHandler<Long> handler = mock(InvokeHandler.class);
        when(handler.handleCall(eq(mockCall), any(Object[].class))).thenReturn(7L);

        Long result = AxisClientUtils.invoke("http://example.com/empty", handler);

        assertThat(result).isEqualTo(7L);
        verify(handler).handleCall(eq(mockCall), eq(new Object[0]));
    }

    private static Service getStaticService() throws Exception {
        Field field = AxisClientUtils.class.getDeclaredField("service");
        field.setAccessible(true);
        return (Service) field.get(null);
    }

    private static void setStaticService(Service value) throws Exception {
        Field field = AxisClientUtils.class.getDeclaredField("service");
        field.setAccessible(true);
        field.set(null, value);
    }
}
