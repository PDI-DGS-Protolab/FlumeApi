package api.console;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.flume.Channel;
import org.apache.flume.EventDeliveryException;
import org.apache.flume.lifecycle.LifecycleState;

import api.Client;
import api.rpc.exceptions.MaxBatchSizeException;

/**
 *	This class implements a Client that shows by console all the logs received
 * @author OIL-Conwet
 */

public class ConsoleClient implements Client{

	Channel c;
	String n;
	
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

	@Override
	public boolean configure(String... params) {
		return false;
	}

	@Override
	public Channel getChannel() {
		// TODO Auto-generated method stub
		return c;
	}

	@Override
	public Status process(){
		try{
			write(String.valueOf(c.take().getBody()));
			return Status.READY;
		}catch(EventDeliveryException e){
			return Status.BACKOFF;
		}
	}

	@Override
	public void setChannel(Channel arg0) {
		this.c=arg0;		
	}

	@Override
	public LifecycleState getLifecycleState() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void start() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub

	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return n;
	}

	@Override
	public void setName(String arg0) {
		this.n=arg0;		
	}

}
