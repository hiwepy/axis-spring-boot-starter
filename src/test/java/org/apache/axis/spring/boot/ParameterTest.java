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
package org.apache.axis.spring.boot;

import javax.xml.namespace.QName;
import javax.xml.rpc.ParameterMode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for {@link Parameter}.
 *
 * <p>Verifies both constructors, all getters/setters and the
 * {@code ParameterMode.IN} default returned by {@link Parameter#getMode()}.</p>
 *
 * @author <a href="https://github.com/loong10k">Loong Wan</a>
 * @since 1.0.0
 */
@DisplayName("Parameter Tests")
class ParameterTest {

    private static final QName STRING_TYPE = new QName("http://www.w3.org/2001/XMLSchema", "string");

    private Parameter parameter;

    @BeforeEach
    void setUp() {
        parameter = new Parameter("name", STRING_TYPE, "value");
    }

    @Test
    @DisplayName("Three-arg constructor populates name, xmlType and value and leaves mode null")
    void testThreeArgConstructor() {
        Parameter param = new Parameter("user", STRING_TYPE, "jack");
        assertThat(param.getName()).isEqualTo("user");
        assertThat(param.getXmlType()).isEqualTo(STRING_TYPE);
        assertThat(param.getValue()).isEqualTo("jack");
        // default behaviour: null mode resolves to ParameterMode.IN
        assertThat(param.getMode()).isEqualTo(ParameterMode.IN);
    }

    @Test
    @DisplayName("Four-arg constructor populates name, xmlType, value and mode")
    void testFourArgConstructor() {
        Parameter param = new Parameter("user", STRING_TYPE, "jack", ParameterMode.INOUT);
        assertThat(param.getName()).isEqualTo("user");
        assertThat(param.getXmlType()).isEqualTo(STRING_TYPE);
        assertThat(param.getValue()).isEqualTo("jack");
        assertThat(param.getMode()).isEqualTo(ParameterMode.INOUT);
    }

    @Test
    @DisplayName("getMode falls back to ParameterMode.IN when mode is null")
    void testGetModeDefaultsToIn() {
        assertThat(parameter.getMode()).isEqualTo(ParameterMode.IN);
    }

    @Test
    @DisplayName("Setter for name updates the value")
    void testSetName() {
        parameter.setName("updated");
        assertThat(parameter.getName()).isEqualTo("updated");
    }

    @Test
    @DisplayName("Setter for xmlType updates the value")
    void testSetXmlType() {
        QName type = new QName("http://example.com", "int");
        parameter.setXmlType(type);
        assertThat(parameter.getXmlType()).isEqualTo(type);
    }

    @Test
    @DisplayName("Setter for value updates the value")
    void testSetValue() {
        parameter.setValue(42);
        assertThat(parameter.getValue()).isEqualTo(42);
    }

    @Test
    @DisplayName("Setter for mode updates the value and overrides the IN default")
    void testSetMode() {
        parameter.setMode(ParameterMode.OUT);
        assertThat(parameter.getMode()).isEqualTo(ParameterMode.OUT);
    }
}
