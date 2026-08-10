package org.apache.axis.spring.boot.handler;

import java.rmi.RemoteException;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;


/**
 * Default {@link InvokeHandler} implementation that performs no service pre-processing
 * and invokes the Axis {@link Call} directly with the supplied arguments.
 *
 * @author <a href="https://github.com/loong10k">Loong Wan</a>
 * @since 1.0.0
 */
public class DefaultInvokeHandler implements InvokeHandler<Object> {

	/**
	 * No-op service pre-processing hook.
	 * @param service the Axis service to prepare
	 */
	@Override
	public void handleServ(Service service) {

	}

	/**
	 * Invokes the Axis {@link Call} directly with the supplied arguments.
	 * @param call the Axis call to invoke
	 * @param args the arguments to pass to the call
	 * @return the result of the invocation
	 * @throws RemoteException if the invocation fails
	 */
	@Override
	public Object handleCall(Call call, Object[] args) throws RemoteException {
		return call.invoke(args);
	}

}
