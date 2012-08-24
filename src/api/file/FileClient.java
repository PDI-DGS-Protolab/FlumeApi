package api.file;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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
 *	This class implements a Client that writes to a File all the logs received
 * @author OIL-Conwet
 */

public class FileClient implements Client{

	private FileOutputStream fos;
	
	public FileClient(String path){
		try {
			fos=new FileOutputStream(path);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void write(String string) throws EventDeliveryException {
		try {
			DateFormat df=new SimpleDateFormat("yyyy/mm/dd hh:mm:ss");
			Date d=new Date();
			fos.write((df.format(d)+ " --> " + string).getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
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
		try {
			fos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}

	@Override
	public boolean configure(String... params) {
		if (params==null || params.length!=1){
			return false;
		}else{
			try {
				fos=new FileOutputStream(params[0]);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			return true;
		}
	}
}
