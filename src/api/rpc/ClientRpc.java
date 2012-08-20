package api.rpc;
import java.util.List;
import java.util.Properties;

import org.apache.flume.api.*;
import org.apache.flume.*;
import org.apache.flume.event.*;
import org.apache.flume.Event;

import api.Client;
import api.rpc.exceptions.MaxBatchSizeException;


/**
 * This class implements a Client that sends its logs via an Rpc connection
 * @author OIL-Conwet
 *
 */
public class ClientRpc implements Client{

	private RpcClient c;
	
	public ClientRpc(){
		c= RpcClientFactory.getDefaultInstance("127.0.0.1",55555);
	}
	
	public ClientRpc(String host, int port){
		c=RpcClientFactory.getDefaultInstance(host, port);
	}
	
	public ClientRpc(String host,int port, String priority){
		// Setup properties for the failover
		Properties props = new Properties();
		props.put("client.type", "default_failover");
		// list of hosts
		props.put("hosts", "host1 host2 host3");
		// address/port pair for each host
		props.put("hosts.host1", "localhost" + ":" + 55554);

		// create the client with failover properties
		c = (FailoverRpcClient) RpcClientFactory.getInstance(props);
	}
	@Override
	public void write(String s)throws EventDeliveryException {
		Event e= EventBuilder.withBody(s.getBytes());
		c.append(e);
	}
	
	@Override
	public void writeList(List<String> strings) throws MaxBatchSizeException, EventDeliveryException {
		if (c.getBatchSize() > strings.size()) {
			throw new MaxBatchSizeException();
			
		} else {
			for (String s : strings) {
				write(s);
			}	
		}
	}
	
	@Override
	public void close() {
		if (!c.isActive()) {
			c.close();
		}
	}
}
