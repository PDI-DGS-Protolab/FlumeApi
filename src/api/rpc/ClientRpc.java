package api.rpc;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.apache.flume.api.*;
import org.apache.flume.*;
import org.apache.flume.event.*;
import org.apache.flume.lifecycle.LifecycleState;
import org.apache.flume.Event;
import api.ReliableClient;
import api.rpc.exceptions.MaxBatchSizeException;


/**
 * This class implements a Client that sends its logs via an Rpc connection
 * @author OIL-Conwet
 *
 */
public class ClientRpc implements ReliableClient{

	private RpcClient c;
	private List<Event> q;
	
	public ClientRpc(){
		c= RpcClientFactory.getDefaultInstance("127.0.0.1",55555);
	}
	
	public ClientRpc(String host, int port){
		c=RpcClientFactory.getDefaultInstance(host, port);
		q=new LinkedList<Event>();
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

	@Override
	public boolean configure(String... params) {
		if(params==null || params.length!=2){
			return false;	
		}else{
			c=RpcClientFactory.getDefaultInstance(params[0], Integer.valueOf(params[1]));
			return true;
		}
		
	}

	@Override
	public void ReliableWrite(String event) {
		boolean sent=false;
		try {
			c.append(EventBuilder.withBody(event.getBytes()));
			sent=true;
			if(q.size()>0 && q.size()<=c.getBatchSize()){
				c.appendBatch(q);
				q.clear();
			}else if (q.size()>0){
				for(Event e:q){
					c.append(e);
					q.remove(e);
				}
			}
			
		} catch (EventDeliveryException e) {
			if(!sent){
				q.add(EventBuilder.withBody(event.getBytes()));
			}
		}
	}
}
