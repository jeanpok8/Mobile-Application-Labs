package ltu.m7019e.t8netudpechoclient;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;



/**
 * Class that implements an Echo Server, it has two main parts:
 * the Server and the client and they can run on both devices.
 *  
 * 
 * @author 
 */
public class MainActivity extends Activity {
	 
	private EditText portNumber;    
	private EditText hostName;    
	private EditText inputMsg;    
	private EditText resultMsg;    
    private Socket mySocket;    
	private InputStream isIn;    
	private OutputStream psOut;    
	private byte abIn[];
	private String sHostName;
	private int iPortNumber;
	private Handler mHandler;
	private int iNumRead;
	
	
	/** Called when the activity is first created. */   
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
     
		hostName = (EditText) findViewById(R.id.editText1);        
		portNumber = (EditText) findViewById(R.id.editText2);        
		resultMsg = (EditText) findViewById(R.id.editText3);        
		inputMsg = (EditText) findViewById(R.id.editText4);  
		mHandler= new Handler();

        new Thread(new EchoServer()).start();

	}

    class EchoServer implements Runnable {

        static final int BUFSIZE=1024;

        @Override
        public void run() {
            int port = Integer.parseInt("3456");
            try {
                // Create Server Socket (passive socket)
                ServerSocket ss = new ServerSocket(port);
                while (true) {
                    Socket s = ss.accept();
                    handleClient(s);
                }
            } catch (IOException e) {
                System.out.println("Fatal I/O Error !");
                System.exit(0);
            }
        }

        void handleClient(Socket s) throws IOException {
            byte[] buff = new byte[BUFSIZE];
            int bytesread = 0;
            // print out client's address
            System.out.println("Connection from " +
                    s.getInetAddress().getHostAddress());
            // Set up streams
            InputStream in = s.getInputStream();
            OutputStream out = s.getOutputStream();
            // read/write loop
            while ((bytesread = in.read(buff)) != -1) {
                out.write(buff,0,bytesread);
            }
            System.out.println("Client has left\n");
            s.close();
        }
    }
	
	class ClientThread implements Runnable {
		 @Override  
		 public void run() {
			 try {
				 InetAddress serverAddr = InetAddress.getByName(sHostName);
				 try {
					mySocket = new Socket(serverAddr, iPortNumber);
                     Log.i("socket fasdfdsfffffff", mySocket.toString());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				 } 
			 catch (UnknownHostException e1) 
				 {
					 e1.printStackTrace();
				 } 

		 }
	}
	class ResponseThread implements Runnable {

		@Override  
		 
		 public void run() {
 			try {
				isIn = mySocket.getInputStream();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
 			iNumRead = 0;    		
    		abIn = new byte[1024];


			try {
				iNumRead = isIn.read(abIn);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			mHandler.post(new Runnable(){
				public void run(){
		    		String sResponse, sTemp;

					sTemp = new String(abIn, 0, iNumRead);
					sResponse = "We got back: " + sTemp;
		    		resultMsg.setText(sResponse);
				
				}
				
			});

		 }
	}
	
public void myEchoHandler(View view) {    	
	switch (view.getId()) {    	
	case R.id.button1:    		/* This is the connect button */
		sHostName = hostName.getText().toString();
   		iPortNumber = Integer.parseInt(portNumber.getText().toString());



        new Thread(new ClientThread()).start();

		
		break;
	case R.id.button2:    /* This is the send data button */ 

	    		byte[] sInputMsg = inputMsg.getText().toString().getBytes();
	    		try  {  
	    			psOut = mySocket.getOutputStream();
	    			psOut.write(sInputMsg,0, sInputMsg.length);
	    			new Thread(new ResponseThread()).start();
	    			
	        		}   
	    		catch (Exception ex) {
	   			Toast.makeText(this,"Send data failed.  Exception" + ex + "\n",	 Toast.LENGTH_LONG).show();   	
	    		}
	        
	   

		break;			
	case R.id.button3:   // This is the quit button.
		String sTemp2;
		try {
			mySocket.close();
			inputMsg.setText("");
			sTemp2 = new String ("bye bye ...");
			resultMsg.setText(sTemp2);
			}    		
		catch (Exception ex) 
		{
			Toast.makeText(this,"Close socket failed.  Exception " + ex + "\n", Toast.LENGTH_LONG).show(); 
		}
	} //end of switch 
}//end of myEchoHandler




	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
