package api.logger;

import java.util.LinkedList;
import java.util.List;

import org.apache.flume.EventDeliveryException;

import api.Client;
import api.console.ConsoleClient;
import api.rmi.RmiClient;
import api.rpc.ClientRpc;
import api.file.FileClient;

/**
 *	This class implements a Logger, it is, a group of Clients. 
 * @author OIL-Conwet
 */

public class Logger {

	private List<Client> clients;

	public Logger(){
		clients=new LinkedList<Client>();
	}
	
	public Logger(Client cl){
		clients=new LinkedList<Client>();
		this.addDestiny(cl);
	}
	
	/**
	 * This method appends a generic client to the list.
	 * @param cl The client to add.
	 * @return The index of the client in the list.
	 */
	public int addDestiny(Client cl) {
		clients.add(cl);
		return clients.indexOf(cl);
	}
	
	/**
	 * This method appends a ConsoleClient to the list of clients.
	 * @return The index of the client in the list.
	 */
	public int addConsoleDestiny(){
		Client cl=new ConsoleClient();
		clients.add(cl);
		return clients.indexOf(cl);
	}
	
	/**
	 * 	This method appends a FileClient to the list of clients.
	 * @param path the path to the file in which to write
	 * @return The index of the client in the list.
	 */
	public int addFileDestiny(String path){
		Client cl=new FileClient(path);
		clients.add(cl);
		return clients.indexOf(cl);
	}
	
	/**
	 * This method appends a RpcClient to the list of clients.
	 * @param host The host of the server.
	 * @param port The port in which the server is listening.
	 * @return The index of the client in the list.
	 */
	public int addRpcDestiny(String host, int port){
		Client cl=new ClientRpc(host,port);
		clients.add(cl);
		return clients.indexOf(cl);
	}
	
	/**
	 * This method appends a RmiClient to the list of clients. 
	 * @param host The host of the server.
	 * @param port The port in which the server is listening.
	 * @return The index of the client in the list.
	 */
	public int addRmiDestiny(String host, int port){
		Client cl=new RmiClient(host,port);
		clients.add(cl);
		return clients.indexOf(cl);
	}
	
	/**
	 * This method sends one log to all the clients registered.
	 * @param log The log to send.
	 * @throws EventDeliveryException This Exception is thrown when there is an issue delivering the log. 
	 */
	public void sendAll(String log) throws EventDeliveryException{
		for(Client c:clients) c.write(log);
}
	/**
	 * This method sends a batch of logs to all the clients registered.
	 * @param l the list of logs to send
	 * @throws EventDeliveryException This Exception is thrown when there is an issue delivering the log.
	 */
	public void sendBatchAll(List<String> l) throws EventDeliveryException{
		for(String s:l) sendAll(s);
	}
	
	/**
	 * This method sends one log to the Client identified by 'index'.
	 * @param index The index of the client.
	 * @param log The log to send.
	 * @throws EventDeliveryException This Exception is thrown when there is an issue delivering the log.
	 */
	public void send (int index,String log) throws EventDeliveryException{
		Client cl=clients.get(index);
		cl.write(log);
	}
	
	/**
	 * This method sends a List of logs to the Client identified by 'index'.
	 * @param index The index of the client.
	 * @param lThe list of logs to send.
	 * @throws EventDeliveryException This Exception is thrown when there is an issue delivering the log.
	 */
	public void sendBatch (int index, List<String> l) throws EventDeliveryException{
		for (String s:l) send(index, s);
	}
	
}
 