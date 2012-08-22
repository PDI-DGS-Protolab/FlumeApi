package main;
import org.apache.flume.EventDeliveryException;
import org.apache.flume.SinkProcessor;
import org.apache.flume.api.FailoverRpcClient;
import org.apache.flume.api.RpcClientFactory;
import org.apache.flume.sink.FailoverSinkProcessor;

public class Main {
	public static void main(String[] args) {
		api.rpc.ClientRpc c= new api.rpc.ClientRpc("localhost",55555);
		try {
			for (int i = 0; i < 10000; i++) {
				c.write(String.valueOf(i));
				Thread.sleep(2000);
			}
		} catch (EventDeliveryException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		c.close();
	}
}
