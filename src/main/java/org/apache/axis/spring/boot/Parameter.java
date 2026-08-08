package org.apache.axis.spring.boot;

import javax.xml.namespace.QName;
import javax.xml.rpc.ParameterMode;

/**
 * Represents a single Apache Axis web-service call parameter, capturing its name, XML
 * type, value and parameter mode (in/out/inout).
 *
 * @author [@Loong Wan](https://github.com/loong10k)
 * @since 1.0.0
 */
public class Parameter {

	protected String name;
	protected QName xmlType;
	protected Object value;
	protected ParameterMode mode;

	/**
	 * Constructs an IN-mode parameter.
	 * @param name the parameter name
	 * @param xmlType the XML type of the parameter
	 * @param value the parameter value
	 */
	public Parameter(String name, QName xmlType, Object value) {
		this.name = name;
		this.xmlType = xmlType;
		this.value = value;
	}

	/**
	 * Constructs a parameter with an explicit mode.
	 * @param name the parameter name
	 * @param xmlType the XML type of the parameter
	 * @param value the parameter value
	 * @param mode the parameter mode (in/out/inout)
	 */
	public Parameter(String name, QName xmlType, Object value,ParameterMode mode) {
		this.name = name;
		this.xmlType = xmlType;
		this.value = value;
		this.mode = mode;
	}

	/**
	 * Returns the parameter name.
	 * @return the parameter name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the parameter name.
	 * @param name the parameter name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Returns the XML type of the parameter.
	 * @return the XML type
	 */
	public QName getXmlType() {
		return xmlType;
	}

	/**
	 * Sets the XML type of the parameter.
	 * @param xmlType the XML type
	 */
	public void setXmlType(QName xmlType) {
		this.xmlType = xmlType;
	}

	/**
	 * Returns the parameter value.
	 * @return the parameter value
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * Sets the parameter value.
	 * @param value the parameter value
	 */
	public void setValue(Object value) {
		this.value = value;
	}

	/**
	 * Returns the parameter mode, defaulting to {@link ParameterMode#IN} when unset.
	 * @return the parameter mode
	 */
	public ParameterMode getMode() {
		return mode == null ? ParameterMode.IN : mode;
	}

	/**
	 * Sets the parameter mode.
	 * @param mode the parameter mode
	 */
	public void setMode(ParameterMode mode) {
		this.mode = mode;
	}


}
