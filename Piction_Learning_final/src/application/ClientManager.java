package application;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
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
		DataManager datamanager = new DataManager();
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
				
				//sender.write(RaspAuthentication.getBytes());
				
				
				// 첫번째로 서버에게 LoginQuery를 던짐.
				sender.write((RaspAuthentication+loginQuery).getBytes());
				
				
				///////////////////////////////////// 서버로부터 response1 받아옴 //////////////
				byte[] temp = new byte[100000];
						
				int num = receiver.read(temp);

				response1 = new String(temp);
				response1 = response1.substring(0, num);
				
				///////////////////////////////////// 서버로부터 response1 받아옴 /////////////
				
				//잘 받았다고 OKQuery를 던짐.
				sender.write(OKQuery.getBytes());
				
				
				//////////////////////////////////// 서버로부터 response2 받아옴 ///////////////
				num = receiver.read(temp);
				response2 = new String(temp);
				response2 = response2.substring(0, num);
				
            	//////////////////////////////////// 서버로부터 response2 받아옴 //////////////
            	
			}
			
			datamanager.memberCount(response1); // 인원 정보 받아옴
			datamanager.saveNutruition(response2); // 28일동안 그룹에 있는 사람들 데이터 받아서 저장.
			
			client.close();
        }
		catch(Throwable e){
            e.printStackTrace();
        }

	}

}
