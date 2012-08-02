package api.logger;

import java.util.LinkedList;
import java.util.List;
import api.Client;
import api.console.ConsoleClient;
import api.rmi.RmiClient;
import api.rpc.ClientRpc;
import api.file.FileClient;

public class Logger {

	private List<Client> clients;
	
	public Logger(){
		clients=new LinkedList<Client>();
	}
	
	public Logger(Client cl){
		clients=new LinkedList<Client>();
		this.addDestiny(cl);
	}
	
	public void addDestiny(Client cl) {
		clients.add(cl);
	}
	
	public void addConsoleDestiny(){
		clients.add(new ConsoleClient());
	}
	
	public void addFileDestiny(String path){
		clients.add(new FileClient(path));
	}
	
	public void addRpcDestiny(String host, int port){
		clients.add(new ClientRpc(host,port));
	}
	
	public void addRmiDestiny(String host, int port){
		clients.add(new RmiClient(host,port));
	}
}
