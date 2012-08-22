package api;

import java.util.List;
import org.apache.flume.EventDeliveryException;
import org.apache.flume.Sink;

import api.rpc.exceptions.MaxBatchSizeException;
/**
 * 
 * @author OIL-Conwet
 * This interface is the blueprint for all the clients.
 */

public interface Client extends Sink{
	
	/**
	 * This method sends one log (with the String contained in 'log') using this Client.
	 * @param log The String to send.
	 * @throws EventDeliveryException This Exception is thrown when there is an issue delivering the log.
	 */
	void write(String log)throws EventDeliveryException;
	
	/**
	 * This method sends a List of logs using this Client.
	 * @param logs list of Strings to send.
	 * @throws MaxBatchSizeException This exception is thrown when you are trying to send a List of logs that's too long.
	 * @throws EventDeliveryException This Exception is thrown when there is an issue delivering the log.
	 */
	void writeList(List<String> logs) throws MaxBatchSizeException, EventDeliveryException;
	
	/**
	 * This method closes the connection between this Client and the associated target.
	 */
	void close();
	
	boolean configure(String... params);
}
