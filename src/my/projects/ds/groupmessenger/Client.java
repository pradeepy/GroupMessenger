package my.projects.ds.groupmessenger;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Map;

import android.util.Log;

public interface Client {
	void Client (int port);
}

class ClientThread implements Runnable
{
	int count;
	int port;
	private static final String TAG = "MyActivity";
	Socket clientSocket = null;
	public ClientThread (int count2)
	{
	  count = count2;	
	}
	public void run()
	{
	  clientPorts cPort = new clientPorts();	
	  clientConnect CN = new clientConnect();
	  //Log.v(TAG, " value of count is "+count);
	  for (int i = 0; i < count; i++)
	  { //Log.v(TAG," connecting to port "+cPort.clientInputRetrieve(i));
        CN.clientConnectServer(cPort.clientInputRetrieve(i));
	  }
	  valLock vl = new valLock();
      vl.valLock_set(true);
	}
	
	public Socket socketID () {
	  return clientSocket;	
	}
	
	
} // end class ClientThread

class clientConnect 
{
  Socket clientSocket;
  static Socket seqSocket = null;
  private static final String TAG = "MyActivity";	
  public clientConnect()
  {
  }
  public void clientConnectServer(int port)
  {
	try {
	  clientSocket = new Socket("10.0.2.2",port);
	  if (port == 11108)
	  {
		seqSocket = clientSocket;   
	  }
	  //Log.v(TAG," client connecting to port "+port);
	  clientPorts cPort = new clientPorts();
	  cPort.clientPortAdd(port);
	  cPort.clientSocketAdd(clientSocket);
	} catch (Exception e) {
	  //Log.v(TAG,e.getMessage());
	}	  
  }
  public Socket seqSocketRtrv()
  {
    return seqSocket;		
  }
} // end class clientConnect

class clientPorts 
{
  static int count = 0;
  static int count2 = 0;
  static Socket[] cSocketValue = new Socket[4];
  static int port[] = new int[4];
  public clientPorts ()
  { //count = 0;
  }
  public void clientPortAdd(int portNo)
  {
	//Log.v("MyActivity", " adding port to the  list "+portNo+"  "+(count+1));  
	port[count++] = portNo;
  }
  public void clientSocketAdd (Socket cSocket)
  {
	//Log.v("MyActivity", " adding socket to the list "+cSocket);  
	//int i = 0;
	cSocketValue[count2] = cSocket;
	//Log.v("MyActivity"," DISPLAYING SOCKET : "+cSocketValue[count2]+"   count: "+count2);
	count2++;
  }
  public int clientInputRetrieve(int n)
  {
    return port[n];	
  }
  public Socket clientPortsRetrieve (int n)
  {
	return cSocketValue[n];
  }
} // end class clientPorts

