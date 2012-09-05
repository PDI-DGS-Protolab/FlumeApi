FlumeApi
========

A small, simple library to wrap features provided by the official Flume API in order to make easier the integration of Flume 1.2 (onwards) with custom applications

## Philosophy

The approach we propose to use is similar to the Winston approach, so you just have to create a logger instance, and whenever you want, you are able to add or remove loggers dynamically. This idea is simple to understand and flexible at developing.

You can compare the differences between the features given with this library and the same code written using the official API

### Code sample using this library

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

  // Optional part below (not written with the official API)
  
  l.send( consoleIndex, msg)           // Sending events only to the console
  l.removeDestiny( consoleIndex );     // Removing the specified Console destiny
  l.removeDestiny(ClientType.FILE);    // Removing every File destiny
  l.sendAll( msg );                    // Using the remaining RPC destiny
```

### Code sample with the official Flume API

```java
  FileOutputStream fos;
  try {
	  fos=new FileOutputStream("/home/alberto/flume.log");
  } catch (FileNotFoundException e) {
	  e.printStackTrace();
  }

  RpcClient rpc = RpcClientFactory.getDefaultInstance("localhost",55555);

  Random r = new Random();
  String rand = "";
  String msg = "", msgWithDate = "";
  for (int i = 0; i < 10000; i++) {
	  // Date
	  DateFormat df=new SimpleDateFormat("yyyy/mm/dd hh:mm:ss");
	  Date d=new Date();

      // Setting the message to be sent
	  rand = String.valueOf(r.nextInt(1000));
	  msg = "Cliente "+ this.id + rand + "/n";
	  msgWithDate = df.format(d)+ " --> " + msg;

      // Console Client
	  System.out.println(msgWithDate);
	  System.out.flush();

	  // File Client
	  try {
		  fos.write((df.format(d)+ " --> " + msg).getBytes());	
	  } catch (IOException e) {
		  e.printStackTrace();
	  }

      // RPC Client
	  Event e = EventBuilder.withBody(msg.getBytes());
	  try {
		  rpc.append(e);
	  } catch (EventDeliveryException e1) {
		  e1.printStackTrace();
	  }
  }
```

## Authors

Alberto Blázquez (@albertoblaz)
Salvador Pérez (@kuraime)