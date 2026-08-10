package org.apache.axis.spring.boot.utils;

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;

import javax.xml.namespace.QName;
import javax.xml.rpc.ServiceException;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.spring.boot.Parameter;
import org.apache.axis.spring.boot.handler.InvokeHandler;



/**
 * Utility methods for invoking Apache Axis web-service operations.
 * <p>
 * Provides convenience methods to build an Axis {@link Call} from a set of
 * {@link Parameter}s and either invoke it directly or delegate the invocation to a
 * custom {@link InvokeHandler}.</p>
 *
 * @author <a href="https://github.com/loong10k">Loong Wan</a>
 * @since 1.0.0
 */
public final class AxisClientUtils {

	// Shared Axis Service instance used to create Call objects.
	protected static Service service = new Service();

	/**
	 * Invokes an Axis web-service operation, invoking the {@link Call} directly.
	 * @param endpoint the target endpoint address of the service
	 * @param optName the operation name described in the WSDL
	 * @param usesoap whether to use SOAP action mode
	 * @param returnType the return type, e.g.
	 *        {@code org.apache.axis.encoding.XMLType.XSD_STRING}
	 * @param params the call parameters
	 * @return the invocation result
	 * @throws ServiceException if the {@link Call} cannot be created
	 * @throws MalformedURLException if the endpoint is not a valid URL
	 * @throws RemoteException if the invocation fails
	 */
	public static Object invoke(String endpoint, QName optName,boolean usesoap,QName returnType,Parameter ... params) throws ServiceException, MalformedURLException, RemoteException{
		// Create the Call object.
		Call call = (Call) service.createCall();
		call.setTargetEndpointAddress(new URL(endpoint));
		// The operation name described in the WSDL.
		call.setOperationName(optName);
		// Configure parameters.
		Object[] args = new Object[params.length];
		for (int i = 0; i < params.length; i++) {
			Parameter param = params[i];
			// Assemble the argument value.
			args[i] = param.getValue();
			// Register the remote call parameter type.
			call.addParameter(param.getName(), param.getXmlType(), param.getMode());
		}
		call.setReturnType(returnType);
		call.setUseSOAPAction(usesoap);
		// Invoke the call and return the result.
		return call.invoke(args);
	}

	/**
	 * Invokes an Axis web-service operation, delegating the actual invocation to the
	 * supplied {@link InvokeHandler}.
	 * @param targetURL the target endpoint address of the service
	 * @param handler the handler used to pre-process and invoke the call
	 * @param params the call parameters
	 * @param <T> the result type returned by the handler
	 * @return the invocation result
	 * @throws ServiceException if the {@link Call} cannot be created
	 * @throws MalformedURLException if the target URL is not valid
	 * @throws RemoteException if the invocation fails
	 */
	public static <T> T invoke(String targetURL,InvokeHandler<T> handler,Parameter ... params) throws ServiceException, MalformedURLException, RemoteException{
		// Create the Call object.
		Call call = (Call) service.createCall();
		call.setTargetEndpointAddress(new URL(targetURL));
		// Configure parameters.
		Object[] args = new Object[params.length];
		for (int i = 0; i < params.length; i++) {
			Parameter paramModel = params[i];
			// Assemble the argument value.
			args[i] = paramModel.getValue();
			// Register the remote call parameter type.
			call.addParameter(paramModel.getName(), paramModel.getXmlType(), paramModel.getMode());
		}
		// Invoke via the handler and return the result.
		return handler.handleCall(call,args);
	}

}
