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
			
			//����
			client.connect(ipep);
         
			//send,reciever ��Ʈ�� �޾ƿ���
			//�ڵ� close
		
			try(OutputStream sender = client.getOutputStream(); InputStream receiver = client.getInputStream();)
			{
				
				sender.write(RaspAuthentication.getBytes());
				
				
				// ù��°�� �������� LoginQuery�� ����.
				sender.write((loginQuery+"ID_").getBytes());
				
				
				///////////////////////////////////// �����κ��� response1 �޾ƿ� //////////////
				byte[] temp = new byte[100000];
						
				int num = receiver.read(temp);
				//System.out.println(num);
				response1 = new String(temp);
				response1 = response1.substring(0, num);
				
				System.out.println("������ �޾ƿ� �� 1: "+ response1);
				
				///////////////////////////////////// �����κ��� response1 �޾ƿ� /////////////
				
				//�� �޾Ҵٰ� OKQuery�� ����.
				sender.write(OKQuery.getBytes());
				
				
				//////////////////////////////////// �����κ��� response2 �޾ƿ� ///////////////
				num = receiver.read(temp);
				response2 = new String(temp);
				response2 = response2.substring(0, num);
				
            	System.out.println("������ ���� �޾ƿ� �� 2 : " + response2);
            	//////////////////////////////////// �����κ��� response2 �޾ƿ� //////////////
            	
			}
			
			
			client.close();
        }
		catch(Throwable e){
            e.printStackTrace();
        }

	}
	
}
