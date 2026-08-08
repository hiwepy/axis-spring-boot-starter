package org.apache.axis.spring.boot.handler;

import java.rmi.RemoteException;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;

/**
 * Strategy interface for customizing and invoking an Apache Axis web-service call.
 * <p>
 * Implementations may pre-process the {@link Service} and/or {@link Call} before
 * performing the actual invocation.</p>
 *
 * @param <T> the result type returned by {@link #handleCall(Call, Object[])}
 * @author [@Loong Wan](https://github.com/loong10k)
 * @since 1.0.0
 */
public interface InvokeHandler<T> {

	/**
	 * Pre-processes the Axis {@link Service} before a call is made.
	 * @param service the Axis service to prepare
	 */
	public void handleServ(Service service);

	/**
	 * Pre-processes the Axis {@link Call} and performs the invocation, returning the
	 * result.
	 * @param call the Axis call to invoke
	 * @param args the arguments to pass to the call
	 * @return the result of the invocation
	 * @throws RemoteException if the invocation fails
	 */
	public T handleCall(Call call,Object[] args) throws RemoteException;

}
