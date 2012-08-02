package api.file;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.flume.EventDeliveryException;

import api.Client;
import api.rpc.exceptions.MaxBatchSizeException;

public class FileClient implements Client{

	private String path;
	private FileOutputStream fos;
	
	public FileClient(String path){
		try {
			this.path=path;
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

}
