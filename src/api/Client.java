package api;

import java.util.List;
import org.apache.flume.EventDeliveryException;
import api.rpc.exceptions.MaxBatchSizeException;

public interface Client {
	void write(String string)throws EventDeliveryException;
	void writeList(List<String> strings) throws MaxBatchSizeException, EventDeliveryException ;
	void close();
}
