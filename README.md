FlumeApi
========

A small, simple library to wrap features provided by the official Flume API in order to make easier the integration of Flume 1.2 (onwards) with custom applications

### Code Sample

```java
  // Creating the logger
  Logger l = new Logger();

  // Adding some default built destinies
  l.addFileDestiny("/home/alberto/flume.log");
  l.addRpcDestiny("192.168.1.65", 55554);
  int consoleIndex = l.addConsoleDestiny();

  // Adding a custom client (database? datawarehouse? whatever)
  l.addDestiny( new MyCustomClient() );

  // Sending events
  Random r = new Random();
  String rand = "", msg = "";
  for (int i = 0; i < 10000; i++) {
        rand = String.valueOf(r.nextInt(1000));
  msg = "Client " + this.id + rand + "\n";
	l.sendAll(msg);
  }

  // Removing the Console destiny by the index received previously
  l.removeDestiny( consoleIndex );

  // Removing every File Destiny
  l.removeDestiny(ClientType.FILE);

  // Sending events just by RPC (because the others were removed)
  l.sendAll( msg );
```