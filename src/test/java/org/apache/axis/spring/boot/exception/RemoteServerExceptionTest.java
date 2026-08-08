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
package org.apache.axis.spring.boot.exception;

import java.rmi.RemoteException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for {@link RemoteServerException}.
 *
 * @author [@Loong Wan](https://github.com/loong10k)
 * @since 1.0.0
 */
@DisplayName("RemoteServerException Tests")
class RemoteServerExceptionTest {

    @Test
    @DisplayName("Default constructor creates a non-null instance extending RemoteException")
    void testDefaultConstructor() {
        RemoteServerException exception = new RemoteServerException();
        assertThat(exception).isNotNull();
        assertThat(exception).isInstanceOf(RemoteException.class);
    }

    @Test
    @DisplayName("Message getter/setter round-trips the value")
    void testMessageGetterSetter() {
        RemoteServerException exception = new RemoteServerException();
        assertThat(exception.getMessage()).isNull();
        exception.setMessage("boom");
        assertThat(exception.getMessage()).isEqualTo("boom");
    }

    @Test
    @DisplayName("showMessage runs without throwing")
    void testShowMessage() {
        RemoteServerException exception = new RemoteServerException();
        exception.setMessage("server down");
        exception.showMessage();
    }
}
