package api.console;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.flume.EventDeliveryException;

import api.Client;
import api.rpc.exceptions.MaxBatchSizeException;

/**
 *	This class implements a Client that shows by console all the logs received
 * @author OIL-Conwet
 */

public class ConsoleClient implements Client{

	@Override
	public void write(String string) throws EventDeliveryException {
		DateFormat df=new SimpleDateFormat("yyyy/mm/dd hh:mm:ss");
		Date d=new Date();
		System.out.println(df.format(d)+ " --> " + string);
		System.out.flush();
	}

	@Override
	public void writeList(List<String> strings) throws MaxBatchSizeException,
			EventDeliveryException {
		for (String s : strings) {
			write(s);
		}
	}

	@Override
	public void close() {
		System.out.flush();
		System.out.close();
	}

}
