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
package org.apache.axis.spring.boot.handler;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.rmi.RemoteException;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link DefaultInvokeHandler}.
 *
 * <p>Verifies the no-op {@code handleServ} and that {@code handleCall}
 * delegates to {@link Call#invoke(Object[])}.</p>
 *
 * @author [@Loong Wan](https://github.com/loong10k)
 * @since 1.0.0
 */
@DisplayName("DefaultInvokeHandler Tests")
class DefaultInvokeHandlerTest {

    private DefaultInvokeHandler handler;

    @BeforeEach
    void setUp() {
        handler = new DefaultInvokeHandler();
    }

    @Test
    @DisplayName("handleServ completes without throwing for any Service argument")
    void testHandleServWithNull() {
        handler.handleServ(null);
    }

    @Test
    @DisplayName("handleServ completes without throwing for a mocked Service")
    void testHandleServWithMock() {
        Service service = mock(Service.class);
        handler.handleServ(service);
    }

    @Test
    @DisplayName("handleCall delegates to Call.invoke and returns its result")
    void testHandleCallReturnsInvokeResult() throws RemoteException {
        Call call = mock(Call.class);
        Object[] args = { "p1", "p2" };
        when(call.invoke(args)).thenReturn("ok");

        Object result = handler.handleCall(call, args);

        assertThat(result).isEqualTo("ok");
        verify(call).invoke(args);
    }

    @Test
    @DisplayName("handleCall can return null when the service returns null")
    void testHandleCallReturnsNull() throws RemoteException {
        Call call = mock(Call.class);
        Object result = handler.handleCall(call, new Object[0]);
        assertThat(result).isNull();
        verify(call).invoke(new Object[0]);
    }
}
