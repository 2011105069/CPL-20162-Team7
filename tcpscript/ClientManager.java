package communicationtest;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;


public class ClientManager {

	private static final int PORT = 10001;
	private static String RaspAuthentication = "RASP_"; 
	private static String loginQuery = "LOGIN_";
	private static String OKQuery = "OK_";
	String response1= "0";
	String response2= "0";
	
	public void connect()
	{
		try
		{
			
			Socket client = new Socket();
		
			InetSocketAddress ipep = new InetSocketAddress("127.0.0.1", PORT);
			
			//접속
			client.connect(ipep);
         
			//send,reciever 스트림 받아오기
			//자동 close
		
			try(OutputStream sender = client.getOutputStream(); InputStream receiver = client.getInputStream();)
			{
				
				sender.write(RaspAuthentication.getBytes());
				
				
				// 첫번째로 서버에게 LoginQuery를 던짐.
				sender.write((loginQuery+"ID_").getBytes());
				
				
				///////////////////////////////////// 서버로부터 response1 받아옴 //////////////
				byte[] temp = new byte[100000];
						
				int num = receiver.read(temp);
				//System.out.println(num);
				response1 = new String(temp);
				response1 = response1.substring(0, num);
				
				System.out.println("서버로 받아온 값 1: "+ response1);
				
				///////////////////////////////////// 서버로부터 response1 받아옴 /////////////
				
				//잘 받았다고 OKQuery를 던짐.
				sender.write(OKQuery.getBytes());
				
				
				//////////////////////////////////// 서버로부터 response2 받아옴 ///////////////
				num = receiver.read(temp);
				response2 = new String(temp);
				response2 = response2.substring(0, num);
				
            	System.out.println("서버로 부터 받아온 값 2 : " + response2);
            	//////////////////////////////////// 서버로부터 response2 받아옴 //////////////
            	
			}
			
			
			client.close();
        }
		catch(Throwable e){
            e.printStackTrace();
        }

	}
	
}
