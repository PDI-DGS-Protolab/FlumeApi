package main;
import org.apache.flume.EventDeliveryException;


public class Main {
	public static void main(String[] args) {
		api.console.ConsoleClient c= new api.console.ConsoleClient();
		try {
			for (int i = 0; i < 910000; i++) {
				c.write("asdasdasdasdasdasdasdasdasdasdasdasdasd");
			}
		} catch (EventDeliveryException e) {
			e.printStackTrace();
		}
	}
}
