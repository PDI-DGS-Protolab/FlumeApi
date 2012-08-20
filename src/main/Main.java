package main;
import org.apache.flume.EventDeliveryException;


public class Main {
	public static void main(String[] args) {
		api.rpc.ClientRpc c= new api.rpc.ClientRpc("192.168.1.79",55555);
		try {
			for (int i = 0; i < 10000; i++) {
				c.write("asdasdasdasdasdasdasdasdasdasdasdasdasd");
			}
		} catch (EventDeliveryException e) {
			e.printStackTrace();
		}
		c.close();
	}
}
