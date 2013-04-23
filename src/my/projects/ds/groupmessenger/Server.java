package my.projects.ds.groupmessenger;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import my.projects.ds.groupmessenger.GroupMessengerActivity.*;

import android.util.Log;

public interface Server {

	
	/**
	 * Launch the server at the given port.
	 * Accept incoming messages and deliver them to the client after executing the 
	 * necessary algorithms for required ordering.
	 * 
	 * @param port
	 */
	void launch(int port);
}

class ServerPort implements Server 
{   
	private static final String TAG = "MyActivity";
	public int portNo;
	@Override
	public void launch(int port) {
		// TODO Auto-generated method stub
		Log.v(TAG, " inside server...  about to start thread");
		portNo = port;
//		Thread St = new Thread (new ServerStartThread(portNo));
//		St.start();
	}
}




class servConnPort
{
  static Socket servConnSocket = null;
  static ServerSocket serverSocket = null;
  static int count = 0;
  static Socket[] ServerConn = new Socket[4];
  public servConnPort ()
  {
	  
  }
  public servConnPort (ServerSocket socket)
  {
	serverSocket = socket;  
  }
  public servConnPort (Socket socket)
  {
	servConnSocket = socket;  
  }
  public Socket serverConnRetrieve ()
  {
	return servConnSocket;  
  }
  public ServerSocket serverSocketRetrieve ()
  {
	  return serverSocket;  
  }
  public void ServerConn_add (Socket socket)
  {
	ServerConn[count] = socket;
	count++;
  }
  public Socket ServerConn_rtrv (int n)
  {
	return ServerConn[n];  
  }
} // end class serverPort

