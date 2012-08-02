package api.rpc;
import java.util.List;

import org.apache.flume.api.*;
import org.apache.flume.*;
import org.apache.flume.event.*;
import org.apache.flume.Event;

import api.Client;
import api.rpc.exceptions.MaxBatchSizeException;

public class ClientRpc implements Client{

	private RpcClient c;
	
	public ClientRpc(){
		c= RpcClientFactory.getDefaultInstance("127.0.0.1",55555);
	}
	
	public ClientRpc(String host, int port){
		c=RpcClientFactory.getDefaultInstance(host, port);
	}
	
	public void write(String s)throws EventDeliveryException {
		Event e= EventBuilder.withBody(s.getBytes());
		c.append(e);
	}
	
	public void writeList(List<String> strings) throws MaxBatchSizeException, EventDeliveryException {
		if (c.getBatchSize() > strings.size()) {
			throw new MaxBatchSizeException();
			
		} else {
			for (String s : strings) {
				write(s);
			}	
		}
	}
	
	public void close() {
		if (!c.isActive()) {
			c.close();
		}
	}
}
