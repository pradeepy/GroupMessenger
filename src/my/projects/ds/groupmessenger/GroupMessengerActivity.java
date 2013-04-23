package my.projects.ds.groupmessenger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.regex.Pattern;

import my.projects.ds.groupmessenger.GroupMessengerActivity.seqSend;

//import com.helloWorld.And0Activity.ReceiveThread;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;



public class GroupMessengerActivity extends Activity {
  private static final String TAG = "MyActivity";
  public static Socket seqSocket = null;
  public static Button button1,button2,button3,button4,button5;
  public static TextView tv1;
  public int localPort = 0;
  public static int count = 1;
  public static int Scount = 1;
  public static int Ccount = 0;
  public static int mCount = 0;
  public static int SeqCount = 0;
  public static int sequence = 0;
  public static int sequence2 = 0;
  public static int sequenceCount = 0;
  public static int testcase2Count = 0;
  public Pattern pattern = Pattern.compile("[\\:*:]");
  public static int mCount2 = 0;
  public int Tcount = 5;
  public String seqMessage = null;
  public String inputStringVal = null;	
  
  public  static int recvCount = 0;
  public boolean flag2 = false, removeQueue = false, localClient = false,RP = true;
  public boolean testcase1 = false, testcase2 = false;
  public static EditText   txtbox1,txtbox2,txtbox3;
  public static TextView tv;
  public static String[] Seq;
  public String portStr = "0";
  String tServer = null, tClient = null;
  Socket cSocket = null;
  ServerSocket sSocket = null;
  Queue queueB = new PriorityQueue();
  Queue queueBuffer = new PriorityQueue();
  Queue queue5554 = new PriorityQueue();
  Queue queue5556 = new PriorityQueue();
  Queue queue5558 = new PriorityQueue();
  Queue queue5560 = new PriorityQueue();
  Queue queue5562 = new PriorityQueue();
  public int[] peerSeq = new int[5];
  
  /** Called when the activity is first created. */
  @Override
  public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.main);
      //txtbox1 = (EditText) findViewById(R.id.editText1);
      txtbox2 = (EditText) findViewById(R.id.editText2);
      txtbox3 = (EditText) findViewById(R.id.editText3);
      button1 = (Button) findViewById(R.id.button1);
      button2 = (Button) findViewById(R.id.button2);
      button3 = (Button) findViewById(R.id.button3);
      button4 = (Button) findViewById(R.id.button4);
      button5 = (Button) findViewById(R.id.button5);
      //txtbox1.setText("");
      txtbox2.setText("enter client port");
      tv1 = (TextView) findViewById(R.id.tv1);
      tv1.setText("");
      tv1.setMovementMethod(new ScrollingMovementMethod());
      button5.setText("TESTCASE 2");
      button1.setOnClickListener(new clicker());
      button2.setOnClickListener(new clicker2());
//      button3.setOnClickListener(new clicker3());
//      button4.setOnClickListener(new clicker4());
      button3.setOnClickListener(new clicker3());
      button4.setOnClickListener(new clicker4());
      button5.setOnClickListener(new clicker5());
      button1.performClick();
  } // end onCreate
  
  class  clicker implements Button.OnClickListener
  {
	private static final String TAG = "My_Activity";
  	@Override
  	public void onClick(View v) {
      TelephonyManager tel = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
      tel.getLine1Number();
      portStr = tel.getLine1Number().substring(tel.getLine1Number().length() - 4);
      localPort = Integer.parseInt(portStr);
      Thread Sq = new Thread (new SequencerThread(Integer.parseInt(portStr)));
      Sq.start();
      //Thread RP = new Thread(new ReceiveProcessThread());
      //RP.start();
      Log.v(TAG,"in clicker1");
      int i = 10000;
      Thread St = new Thread (new ServerStartThread(i));
	  St.start();
      Log.v(TAG,"launching  server  thread");
      servConnPort SP = new servConnPort();
      Socket CommSocket = SP.serverConnRetrieve();
      button3.setText("Send Message");
  	} // end onClick
  } // end clicker
  
  class clicker2 implements Button.OnClickListener
  {
	public void onClick(View v)
	{   //Ccount = 0;
		clientPorts CN = new clientPorts();
		int portVal = 11108;
        Log.v(TAG,"   Tcount is "+Tcount);
		for ( int i = 0; i < Tcount; i++)
        { 
          Log.v(TAG," In clicker 2 "+(i+1)+"  "+portVal+"  "+portStr);	
          if (portVal != Integer.parseInt(portStr)*2)
          { Log.v(TAG," Added portVal "+portVal+" Ccount  "+Ccount);
        	CN.clientPortAdd(portVal);
            Ccount++;
          } 
          portVal = portVal+4;
        }
        Log.v(TAG," Done adding the ports ");
        MessageRW m = new MessageRW();
        m.MessageRW_port(Integer.parseInt(portStr));
	    Thread ct = new Thread(new ClientThread(Ccount));
		ct.start();
		Log.v(TAG, "displaying the connections ");
		/*for (int i = 0; i < Ccount; i++)
		{
		  Log.v(TAG,"  "+CN.clientInputRetrieve(i)+"  "+CN.clientPortsRetrieve(i));	
		}
		Log.v(TAG, "displaying the connections - end");
		*/
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Log.v(TAG, "displaying the connections ");
		for (int i = 0; i < Ccount; i++)
		{
		  Log.v(TAG,"  "+CN.clientInputRetrieve(i)+"  "+CN.clientPortsRetrieve(i));	
		}
		seqSocket = CN.clientPortsRetrieve(0);
		Log.v(TAG, "displaying the connections - end     "+seqSocket);
		
	}
  } // end clicker2
  
  class clicker3 implements Button.OnClickListener
  {
	public void onClick (View v)
	{
	  boolean repeat = false;	
	  //button4.setText(" inside   clicker4");	
	  if (testcase1 == false && testcase2 == false)
		inputStringVal = txtbox3.getText().toString();
	  clientPorts CN = new clientPorts();
      seqSocket = CN.clientPortsRetrieve(0);
      Log.v(TAG, "  seqSocket is "+seqSocket);
	  if (inputStringVal == null)
		inputStringVal = "RaNDoMvAlUe";
	  Log.v(TAG," inputStringVal is     "+inputStringVal+" seqSocket  is "+seqSocket);
	  seqMessage = inputStringVal;
	  MessageRW m = new MessageRW();
      clientPorts CP = new clientPorts();
      if (sequence == 0)
      {
    	sequence = 1;  
      }
      boolean temp2 = false;
      if (localPort != 5554)
      { Log.v(TAG,"   NOT port 5554  ... this is port "+localPort+" "+seqMessage);
        if (testcase1 == true)
        {
          m.MessageRW_write(seqSocket,seqMessage+":"+"requestSequence");	
        } else
        if (testcase2 == true)
        { sequenceCount++;
          m.MessageRW_write(seqSocket,seqMessage+":"+"requestSequence");	
        } else        	
    	if(inputStringVal == null)
        { m.MessageRW_write(seqSocket,portStr+":"+sequence+":"+"requestSequence");
        } else {
          m.MessageRW_write(seqSocket,portStr+":"+sequence+":"+inputStringVal+"requestSequence");  
        }

      } else {
    	Log.v(TAG,"   PORT 5554  ... this is port "+localPort);
        Message msg = new Message();
    	if (testcase1 == true)
        { seqMessage = sequence2+":"+seqMessage;
    	  //m.MessageRW_write(seqSocket,seqMessage+":"+"requestSequence");	
        } else    	 
        if (testcase2 == true)
        { seqMessage = sequence2+":"+seqMessage;
    	  sequenceCount++;
          //m.MessageRW_write(seqSocket,seqMessage+":"+"requestSequence"+":testcase2");	
          //seqMessage = seqMessage+":testcase2";
        } else        	    	
    	if(inputStringVal == null)
        { seqMessage = sequence2+":"+portStr+":"+sequence+":";
        } else {
          //seqMessage = sequence2+":"+portStr+":"+sequence+":"+inputStringVal;
          seqMessage = sequence2+":"+portStr+":"+sequence+":";	
        }
    	Log.v(TAG,"    set seqMessage for 5554 .. .    "+seqMessage);
    	localClient = true;
    	msg.obj = seqMessage; 
    	mHandlerRecv.sendMessage(msg);
    	for (int i = 0; i < Ccount; i++)
        { m.MessageRW_write(CN.clientPortsRetrieve(i), seqMessage);
        }
    	sequence2++;
      }
      sequence++;
	}
  } // end class clicker4

  class clicker4 implements Button.OnClickListener
  {
	public void onClick (View v)
	{ 
	  testcase1 = true;	
	  //button1.performClick();
	  try {
		Thread.sleep(10000);
		button2.performClick();
		for (int i = 0; i < 2; i++)
		{
		  inputStringVal = localPort+":"+i;
		  Log.v(TAG, " inputStringVal is "+inputStringVal);
		  button3.performClick();
		  Thread.sleep(3000);
		}
	  } catch (InterruptedException e) {
		Log.v(TAG,e.getMessage());
	  } 
	  
	  Iterator it = queueB.iterator();
	  while(it.hasNext())
	  {
		Log.v("RCVMSG_buffer"," "+it.next());
	  }
	  queueB.clear();
	}
  } // end class clicker4
  
  class clicker5 implements Button.OnClickListener
  {
	public void onClick (View v)
	{
	  testcase2 = true;
	  try {
		//Thread.sleep(10000);
		//button2.performClick();
		for (int i = 0; i < 1; i++)
		{
		  inputStringVal = localPort+":"+i+":testcase2";
		  Log.v(TAG, " inputStringVal is "+inputStringVal);
		  button3.performClick();
		  Thread.sleep(3000);
		}
	  } catch (InterruptedException e) {
		Log.v(TAG,e.getMessage());
	  }
	}
  }

  class addSequence
  {
	public addSequence ()
	{
	  	
	  txtbox2.setText(""+queueB.size());
	  Iterator i = queueB.iterator();
	  while (i.hasNext())
	  {	  
	    Log.v(TAG,"  In the " +
	    		"priority  queue "+i.next());
	  }
	  i = queueBuffer.iterator();
	  while (i.hasNext())
	  {	  
	    Log.v(TAG,"  In the priority queueBuffer "+i.next());
	  }
	  
	  Log.v(TAG,"  Ccount is "+Ccount+"  Tcount is "+Tcount);
	  String[] compareMsg = new String[Tcount];
	  i = queueBuffer.iterator();
	  int countBuf = 0;
	  int countMsg = 0;
	  while (i.hasNext())
	  {
		String tempString = (String) i.next();  
		if (countMsg == 0)
		{ compareMsg[countBuf] = tempString;
		}
		countMsg++;
		String[] result = pattern.split(tempString);
		boolean addMsg = true;
		for (int j = 0; j <= countBuf; j++)
		{ String[] result2 = pattern.split(compareMsg[j]);
		  if (Integer.parseInt(result[1]) == Integer.parseInt(result2[1]))
		  { addMsg = false;
		    Log.v(TAG, "   result is  "+result[1]+"   result2 is "+result2[1]);
			if (Integer.parseInt(result[0]) >= Integer.parseInt(result2[0]))
			{ 
			  if(Integer.parseInt(result[2]) < Integer.parseInt(result2[2]))
			  {
				Log.v(TAG,"  interchange   "+tempString+" "+compareMsg[countBuf]);  
			  }
			}
		  }
		} // end for
		if (addMsg == true)
		{
		  countBuf++;
		  Log.v(TAG,"    adding "+tempString+"  in slot "+countBuf);
		  compareMsg[countBuf] = tempString;
		}
	  }
	  removeQueue = true;
	}
  }
  
  class SeqServerThread implements Runnable
  {
   
	@Override
	public void run() {
		// TODO Auto-generated method stub
	  try{
		  ServerSocket seqServerSocket = null;
		  seqServerSocket = new ServerSocket(9000);
		  while(true)
		  { Socket inputSocket = null;
		    inputSocket = seqServerSocket.accept();
		    PrintWriter out = new PrintWriter(inputSocket.getOutputStream(), true);
		    out.println(sequence2++);
		  }
	  } catch (Exception e) {
		Log.v(TAG,e.getMessage());  
	  }
	}
	  
  }
  
  class ServerStartThread implements Runnable
  { 
	int portNo;
	boolean flag = true;
	public ServerStartThread (int port)
  	{
  	  portNo = port;	
  	}
  	private static final String TAG = "MyActivity";
  	@Override
  	public void run() {
  		ServerSocket serverSocket = null;
  		try {
  		  serverSocket = new ServerSocket(portNo);
	      if (localPort == 5554)
	      { 
	    	//Sequencer Sq = new Sequencer(localPort);
		    //Sq.SequencerExecute();
	    	Thread ST = new Thread (new SequencerThread(localPort));
	    	ST.start();
	      }
  		  Log.v(TAG," server socket  started at  "+portNo);
  		  servConnPort SP = new servConnPort(serverSocket);
  		} catch (Exception e) {
  			Log.v(TAG,e.getMessage());
  			flag = false;
  		}
  		if (flag == true)
  		try {
  		  servConnPort SP = new servConnPort();	
  		  while(true)
  		  { Socket inputSocket = null;
  		    Log.v(TAG, " waiting for incoming connections ");
    		inputSocket = serverSocket.accept();
  		    Log.v(TAG," Server  connection accepted. inputSocket is "+inputSocket);
  		    SP.ServerConn_add(inputSocket);
            if (Scount == 0)
  		    { Thread rt = new Thread (new ReceiveThread(inputSocket));
  		      Log.v(TAG, " thread rt started "+inputSocket);
              rt.start();
  		    }
            if (Scount == 1)
  		    { Thread rt1 = new Thread (new ReceiveThread(inputSocket));
  		      Log.v(TAG, " thread rt1 started "+inputSocket);
  		      rt1.start();
		    }
            if (Scount == 2)
  		    { Thread rt2 = new Thread (new ReceiveThread(inputSocket));
  		      Log.v(TAG, " thread rt2 started "+inputSocket);
              rt2.start();
  		    }
            if (Scount == 3)
  		    { Thread rt3 = new Thread (new ReceiveThread(inputSocket));
  		      Log.v(TAG, " thread rt3 started "+inputSocket);
              rt3.start();
		    }            
            if (Scount++ >= 2)
              break;
  		  }
  		} catch (IOException e) {
  		  Log.v(TAG,e.getMessage());
  		}
  	}
  }
  
  
  public String seqMessage_rt, inputMessage_rt;
  class ReceiveThread implements Runnable {
	  private static final String TAG = "MyActivity";
	  Socket inputSocket = null;
	  Message msg = new Message();
	  boolean recv = true;
	  ReceiveProcess rp = new ReceiveProcess();
	  public ReceiveThread (Socket socket) {
		inputSocket = socket;  
	  }
	  public void run() {
		try {
		  Log.v(TAG," server connected to "+inputSocket);
		  msg.obj = inputSocket.getPort()+" ";
		  //mHandlerRecv.sendMessage(msg);
		  clientPorts CN = new clientPorts();
          int count = 1;
		  while (true)
		  { recv = true;
		    
		    if (count > 10)
		    { RP = true;
		      count = 1;
		    }
	  		servConnPort SP = new servConnPort(inputSocket);
	  		MessageRW m = new MessageRW();
	  		String inputMessage = null;
            inputMessage = m.MessageRW_read(inputSocket);
            inputMessage_rt = inputMessage;
	        seqMessage = inputMessage;
	        seqMessage_rt = seqMessage;
	        RP = false;
	        rp.ReceiveProcessExecute();
 	        //Thread rp = new Thread (new ReceiveProcessThread());
 	        //rp.start();
	        Log.v("ReceiveThread","  unlocking RP");
	  		count++;
	        boolean cont = false;
	        // from this point
            // till this point
		  }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//Log.v(TAG,e.getMessage());
			Log.v(TAG,"");
		}
		try {
			inputSocket.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.v(TAG," Connection  lost? ");
		}
		
	  }
  }
  
  
  
  //class ReceiveProcessThread implements Runnable
  class ReceiveProcess
  {
	//private static final String TAG = "MyActivity";
	Socket inputSocket = null;
	Message msg = new Message();
	boolean recv = true;
	MessageRW m = new MessageRW();
	public ReceiveProcess() {
	}
	clientPorts CN = new clientPorts();
	public void ReceiveProcessExecute() {
	  //public void run() {
      Log.v("ReceiveProcess"," in ReceiveProcess thread");
	  //while(true)
	  { String inputMessage_rp = inputMessage_rt;
		 
		//if(RP == false)
		{ Log.v("ReceiveProcess"," Unlocked RP");
            boolean cont = false;
            seqMessage = inputMessage_rp;
            Log.v(TAG,"   Message received from client "+seqMessage);
	        if (seqMessage.contains("testcase2"))
	        { testcase2 = true;
	          //String tempMessage = seqMessage.substring(0,seqMessage.length()-10);	
	          //String[] result = pattern.split(tempMessage);
	          String[] result = pattern.split(seqMessage);
	          String tempMessage = result[0]+result[1]+result[2];
	          Log.v("testcase2","  received msg  "+seqMessage+"   "+tempMessage);
	          if (seqMessage.contains("OK"))
	          { cont = true;
	        	String inputString = inputMessage_rp;
	  	        seqSend sS = new seqSend();
	  	        sS.seqPortRtrv(inputString);
	  	        localClient = false;  
	          }
	          
	          msg.obj = tempMessage;
	        } // end testcase2
	        
	        if(seqMessage.contains("requestSequence"))
	        { cont = true;
	          recv = false;
	          //Log.v(TAG,"  added message "+seqMessage+" to queueB");
	          Log.v("requestSequence","  seqMessage "+seqMessage);
	          String queueMessage = sequence2+":"+seqMessage.substring(0, seqMessage.indexOf(":requestSequence"));
	          Log.v("requestSequence",queueMessage);
	          Pattern p = Pattern.compile("[\\:*]");
	          String result[] = p.split(queueMessage);
	          Log.v(TAG,"   "+result[0]+"   "+result[1]);
	          for (int i = 0; i < Ccount; i++)
	          { Log.v(TAG,"  "+result[1]+"       "+CN.clientInputRetrieve(i)+"   "+CN.clientPortsRetrieve(i));
	        	if (Integer.parseInt(result[1])*2 == CN.clientInputRetrieve(i))
	        	{ m.MessageRW_write(CN.clientPortsRetrieve(i), "OK-"+queueMessage);
	        	  Log.v(TAG,"  Message written  "+"OK-"+queueMessage+"   to port "+CN.clientInputRetrieve(i)+"  "+CN.clientPortsRetrieve(i));
	        	  break;
	        	}
	          } 

	          Log.v(TAG,"  queue message is "+queueMessage);
	          if (localPort == 5554)
	        	inputMessage_rp = "OK-"+queueMessage;
	          sequence2++;
			} // end requestSequence
	        if (cont == false)
	        { String inputString = inputMessage_rp;
	          seqSend sS = new seqSend();
	          sS.seqPortRtrv(inputString);
	          localClient = false;
	        }  
          //  if (inputMessage_rp.contains("the end"))
        	//  break;

		}
	  }
	}
  } // end class ReceiveProcessThread
  
  class seqSend
  {
	public seqSend ()
	{
	}
	public Socket seqPortRtrv (String inputMessage)
	{ boolean recv = true;
	  MessageRW m = new MessageRW();
	  Message msg = new Message();
	  msg.obj = inputMessage;
	  clientPorts CN = new clientPorts();
      Log.v("RCVMSG","   ### inputString is "+inputMessage+"    sequenceCount is "+sequenceCount);
      String result3[] = pattern.split(inputMessage);
      int okPort = Integer.parseInt(result3[1]);
      if (inputMessage.contains("OK"))
      { Log.v("RCVMSG"," OK- present in input message");
        //sequence = 1+Integer.parseInt(inputString.substring(inputString.indexOf("OK-")+3,inputString.length()));	
        seqMessage = inputMessage.substring(inputMessage.indexOf("OK-")+3,inputMessage.length());	
        Log.v(TAG,"       "+seqMessage);
        msg.obj = seqMessage;
        mHandlerRecv.sendMessage(msg);
        //sequenceCount++;
        for (int i = 0; i < Ccount; i++)
        { m.MessageRW_write(CN.clientPortsRetrieve(i), seqMessage);
        }
        Log.v("RCVMSG","   new  sequence  number obtained "+sequence);
        recv = false;
      }
      //if (recv == true && testcase2 == true)
      //  sequenceCount++;  
      Pattern p3 = Pattern.compile("[\\:*]");
      String result[] = p3.split(inputMessage);
      int seqReceived = Integer.parseInt(result[0]);
      Log.v ("RCVMSG","  received message "+inputMessage+"   expected sequence "+sequenceCount);
  	  if (seqReceived != sequenceCount)
      { Log.v("RCVMSG","  received sequence " + inputMessage+"  greater than expected sequence "+sequenceCount);
  		recv = false;
    	queueB.add(inputMessage);
    	Iterator itMsg = queueB.iterator();
    	while (itMsg.hasNext())
    	{ Log.v("RCVMSG", "inside the buffer  "+itMsg.next());
    	  String itString = (String) itMsg.next();
    	  String result2[] = p3.split(itString);
    	  seqReceived = Integer.parseInt(result2[0]);
    	  if (seqReceived > sequenceCount)
    		break;
    	  msg.obj = itString;
    	  sequenceCount++;
    	  queueB.remove();
    	  mHandlerRecv.sendMessage(msg);
    	}
      }
      if (recv == true)
      { Log.v("sequenceCount","  current sequenceCount is "+sequenceCount+"    "+msg);
    	sequenceCount++;
    	if (inputMessage.contains("testcase2"))
    	{ testcase2Count++;
    	}  
        mHandlerRecv.sendMessage(msg);
      }
      if (inputMessage.contains("testcase2"))
      {
          int expectedPort = localPort -2;
          if (localPort == 5554)
        	expectedPort = localPort + 2*(Tcount-1);  
          if (Integer.parseInt(result3[1]) == expectedPort)
          { //if (Integer.parseInt(result[2]) < 2 )
        	Log.v("testcase2","   testcase2Count  "+testcase2Count+"  "+(Tcount+2));  
        	if (testcase2Count < Tcount+2)  
            { String sendMessage = sequence2+":"+localPort+":"+sequence;
              //for (int i = 0; i < Ccount; i++)
              {
            	  
            	try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            	if (localPort != 5554)
            	{  //m.MessageRW_write(seqSocket,sendMessage+":testcase2"+"requestSequence");
            	  String inputStringServ = localPort+":"+sequence+":testcase2"+":requestSequence";	
            		Log.v("testcase2"," Sending message "+inputStringServ);
                  m.MessageRW_write(seqSocket,inputStringServ);
                  sequenceCount++;
            	}
            	else
            	{ 
            	  String inputStringServ = sequence2+":"+localPort+":"+sequence+":"+"testcase2";
            	  for (int i = 0; i < Ccount; i++)
            	  { Log.v("testcase2"," Sending message "+inputStringServ+"   "+CN.clientPortsRetrieve(i));
            		m.MessageRW_write(CN.clientPortsRetrieve(i), inputStringServ);
            	  }
            	  msg.obj = inputStringServ;
            	  mHandlerRecv.sendMessage(msg);
            	  sequenceCount++;
            	  sequence2++;
            	}
            	//sequence++;
          	  }
              sequence++;
            } 
          }  
      }
      
      return null;
	}
  }
/*  
  class SequencerThread implements Runnable
  {
	int port;  
	public SequencerThread(int portNo)
	{
	  port = portNo;	
	  
	}
	public void run() {
      Message msg = new Message();
      msg.obj = " in SequencerThread";
      //mHandlerRecv.sendMessage(msg);
      Log.v(TAG, " in SequencerThread");
      boolean flag = false;
      
	  while(true)	
	  { if (Ccount >= Tcount-1)
	    { 
		  msg.obj = " in  SequencerLoop";
	      mHandlerRecv.sendMessage(msg);
	      //Sequencer Sq = new Sequencer(port);
	      //Sq.SequencerExecute();
	      break;
	    }
	  }
	}
  }
*/
  class SequencerThread implements Runnable
  {
	int port;  
	public SequencerThread(int portNo)
	{
	  port = portNo;	
	  
	}
	public void run() {
      Message msg = new Message();
      msg.obj = " in SequencerThread";
      //mHandlerRecv.sendMessage(msg);
      Log.v(TAG, " in SequencerThread");
      boolean flag = false;
	  MessageRW m = new MessageRW();
      clientPorts CN = new clientPorts();
      while(true)	
	  { 
        while(true)
        {
    	  if (removeQueue == true)
    	  break;	
        }
	    seqMessage = null;
		if (!queueB.isEmpty())  
		  seqMessage = (String) queueB.remove();  
		
		if(seqMessage == null)
		{ try {
			Thread.sleep(300);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			Log.v(TAG,e.getMessage());
		}
		  continue;
		}
		removeQueue = false;
		String seqMessage2 = seqMessage;
		if (seqMessage2.contains("requestSequence"))
	    {
	      Log.v(TAG," requestSequence   found  "+Ccount+"   "+Tcount);
		  Log.v(TAG," requestMessage is "+seqMessage2);
	      String[] result = pattern.split(seqMessage2);
		  int seqNo = Integer.parseInt(result[1]);
		  Log.v(TAG,"  "+result[0]+"  "+result[1]+"  "+result[2]+"  "+seqNo+"  "+sequence);
		  if (seqNo == sequence+1)
		  {
			sequence++;  
			// give the go-ahead
			for(int i = 0; i < Tcount-1; i++)
			{ Log.v(TAG,"  clientInput "+CN.clientInputRetrieve(i)+"  clientPorts "+CN.clientPortsRetrieve(i));
			  if(CN.clientInputRetrieve(i)/2 == Integer.parseInt(result[0]))
			  {
				Log.v(TAG," sent OK");
				m.MessageRW_write(CN.clientPortsRetrieve(i), "OK-"+sequence);
				break;
			  }
		    }
		  } else {
		  // store the message  
		  } // end if seqNo is the next in line
	    }
	  }
	}
  }  
  Handler mHandlerRecv = new Handler() {
		@Override
        public void handleMessage(Message msg) {
		  String input = (String)msg.obj;	
          //txtbox1.append(input);
          //tv.append("\n"+portNo+": "+input);
          //button4.setText(input);
		  Pattern pattern2 = Pattern.compile("[\\:*:*:]");
		  String result[] = pattern2.split(input);
		  input = result[1]+":"+result[2];
		  if (input.contains("testcase2"))
			testcase2Count++;  
		  String tempStringValue = tv1.getText().toString();
		  if (!tempStringValue.contains(input))
			tv1.append("\n"+input);
        }
  };   
  
  Handler mHandlerClick = new Handler() {
		@Override
        public void handleMessage(Message msg) {
		  button2.performClick();	
		}
  };
  
  class closeConn
  {
	public closeConn()
	{
	  clientPorts CP = new clientPorts();
	  int i = 0;
	  //for (i = 0; i < 4; i++)
	  {
		try {
			if (CP.clientPortsRetrieve(i) != null)
			{ CP.clientPortsRetrieve(i).close();
			Log.v(TAG,"server connection closed "+CP.clientPortsRetrieve(i));
			}
		} catch (Exception e) {
			Log.v(TAG,e.getMessage());
		}  
	  }
	  servConnPort SP = new servConnPort();
	  try {
		if (SP.serverConnRetrieve() != null)  
		{ SP.serverConnRetrieve().close();
		  Log.v(TAG,"server connection closed "+SP.serverConnRetrieve());
		}
		if (SP.serverSocketRetrieve() != null)  
		{ SP.serverSocketRetrieve().close();
		  Log.v(TAG,"server socket closed "+SP.serverSocketRetrieve());
		}
	  } catch (Exception e) {
		Log.v(TAG,e.getMessage());
	  }
	}
  } // end class closeConn
  /*
  class Sequencer
  {
	int portNo;
	public Sequencer(int port)
    {
  	portNo = port;  
    }
    public void SequencerExecute()
    { 
      Message msg = new Message();
      msg.obj = " class Sequencer";
      mHandlerRecv.sendMessage(msg);
      if (portNo == 5554)
      {
  	    String inputString = null;
  	    inputString = seqMessage;
  	    Log.v(TAG, " obtained seqMessage "+inputString);
  	    seqMessage = null;
  	    if (inputString == null)
  		  inputString = "RaNDoMvAlUe ";  
  	    MessageRW m = new MessageRW();
        clientPorts CP = new clientPorts();
        valLock vl = new valLock();
        MessageOrder MO = new MessageOrder();
  	    Pattern pattern = Pattern.compile("[\\:*:]");
  	    String[] result = pattern.split(inputString);
  	    MO.MessageOrder_add(Integer.parseInt(result[0]));
  	    SeqCount++;
  	    if (SeqCount >= 2)
  	    { Log.v(TAG, "  !!!!!!Server sending the sequences !!!!!!!!!");
  	      for (int i = 0; i < Ccount; i++)	
	      { 
  	    	Log.v(TAG," "+"Order-"+result[0]);
  	    	m.MessageRW_write(CP.clientPortsRetrieve(i), "Order-"+result[0]);
	      }
	      for (int i = 0; i < 2; i++)
  	      {
  	    	Log.v(TAG,"  "+MO.MessageOrder_retrieve(i));  
  	      }
  	      SeqCount = 0;
  	    }
        vl.valLock_set(false);
  	  } // end if
    }
  } // end class Sequencer
  */
  
  class Sequencer
  {
	int portNo;
	public Sequencer(int port)
	{
      portNo = port;  
	}
	public void SequencerPort (int port)
	{
	  portNo = port;	
	}
	public void SequencerExecute()
	{
	  Pattern pattern = Pattern.compile("[\\:*:]");
	  Message msg = new Message();
	  MessageRW m = new MessageRW();
      clientPorts CN = new clientPorts();
	  if (portNo == 5554)
      { 
		if (seqMessage.contains("requestSequence"))
    	{
    	  Log.v(TAG," requestSequence   found   "+Ccount+"   "+Tcount);
		  Log.v(TAG," requestMessage is "+seqMessage);
    	  String[] result = pattern.split(seqMessage);
		  int seqNo = Integer.parseInt(result[1]);
		  Log.v(TAG,"  "+result[0]+"  "+result[1]+"  "+result[2]+"  "+seqNo+"  "+sequence);
		  if (seqNo == sequence+1)
		  {
			sequence++;  
			// give the go-ahead
			for(int i = 0; i < Tcount-1; i++)
			{ Log.v(TAG,"  clientInput "+CN.clientInputRetrieve(i)+"  clientPorts "+CN.clientPortsRetrieve(i));
			  if(CN.clientInputRetrieve(i)/2 == Integer.parseInt(result[0]))
			  {
				Log.v(TAG," sent OK");
				m.MessageRW_write(CN.clientPortsRetrieve(i), "OK-"+sequence);
				break;
			  }
			}
		  } else {
			// store the message  
		  } // end if seqNo is the next in line
    	}
      } // end checking for port 5554
	} // end SequencerExecute
  }

  class OrderMessage
  {
	public OrderMessage ()
	{
	  MessageOrder MO = new MessageOrder();
	  MessageStore MS = new MessageStore();
	  Log.v(TAG," Output!!! "+Ccount);
	  for (int i = 0; i < Ccount; i++)
	  {
		int inputPort = MO.MessageOrder_retrieve(i);
		Log.v(TAG, " outer loop: "+inputPort);
		for (int j = 0; j < Ccount; j++)
		{
		  String inputString = MS.MessageRetrieve(j);
		  Log.v(TAG, " inner loop: "+inputString);
		  Pattern pattern = Pattern.compile("[\\:*:]");
		  String[] result = pattern.split(inputString);
		  if (inputPort == Integer.parseInt(result[0]))
		  {
			Log.v(TAG,"  "+inputString);  
		  }
		}
	  }
	}
  } // end class OrderMessage
}

