package my.projects.ds.groupmessenger;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import android.util.Log;

public interface Message {

	public int tempValue = 10010;
}

class MessageRW
{
  private static final String TAG = "MyActivity";
  static int portNo;
  public MessageRW()
  {
  }
  public void MessageRW_port(int port)
  {
	portNo = port;  
  }
  public void MessageRW_write (Socket inputSocket, String inputMessage)
  {
		try {
			Log.v(TAG,"CommSocket remote  port is "+inputSocket.getPort()+" "+inputMessage);
			PrintWriter out = new PrintWriter(inputSocket.getOutputStream(), true);
			//out.println(portNo+":" +inputMessage);
			out.println(inputMessage);
			Log.v(TAG,"message written");
		} catch (Exception e) {
			Log.v(TAG,e.getMessage());
		}  
  }
  public String MessageRW_read (Socket inputSocket)
  {
	String inputLine = null;  
	try {  
	  Log.v(TAG," MessageRW_read receive thread "+inputSocket+" ");	
	  BufferedReader in = new BufferedReader(
		new InputStreamReader(
		  inputSocket.getInputStream()));
      inputLine = in.readLine();
      if (inputLine == null)
    	inputLine = "null value";	  
      Log.v(TAG,inputSocket+" received  "+inputLine);
	} catch (Exception e)
	{
	  Log.v(TAG,e.getMessage());
	}
	return inputLine;
  }
} // end class MessageRW

class MessageStore 
{ 
  static String[] inputMessage = new String[20];
  static int count = 0;
  static int count2 = 0;
  public MessageStore ()
  {
  }
  public MessageStore (String inputString)
  {
	if (inputString != null)
	{ inputMessage[count] = inputString;
	  count++;
	} else {
	  //inputMessage = "nothing obtained";	
	}
  }
  public String MessageRetrieve (int n)
  {
	return inputMessage[n];  
  }
  public String MessageRetrieve_lowest ()
  { 
	count2++;
	return null;  
  }
}

class valLock
{
  static boolean flag = false;
  public valLock ()
  {
  }
  public void valLock_set (boolean input)
  {
	flag = input;  
  }
  public boolean valLock_retrieve ()
  {
	return flag;  
  }
}

class MessageOrder
{ static int Sn = 0;
  static int[] port	= new int[4];
  static int count = 0;
  public MessageOrder()
  {
  }
  public void MessageOrder_add(int value)
  {
	port[count] = value;
	count++;
  }
  public int MessageOrder_retrieve(int n)
  {
	return port[n];  
  }
} // end class MessageOrder

