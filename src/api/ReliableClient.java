package api;

public interface ReliableClient extends Client{
	void ReliableWrite(String event);
}
