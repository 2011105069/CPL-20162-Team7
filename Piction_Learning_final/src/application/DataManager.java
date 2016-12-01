package application;

import java.util.StringTokenizer;

public class DataManager {
	
	ClientManager client = new ClientManager();
	
	static String groupinfo[][] = new String[6][4]; //(ID_�̸�_����_����)
	static String memNutritionInfo[][][] = new String[6][180][3]; // (ID_��¥_�����̸�_)
	static double NutritionInfo[][][] = new double[6][180][10]; //(�κ�, ����, ź��ȭ��, �ܹ���, ����, ���̼���, ��Ÿ��C, Į��, ��Ʈ��, ö) 
	static int memCount = 0;
	
	static Member member[] = new Member[6];	//�ִ� 6�����
	
	
	public void memberCount(String response1)
	{
		int i=0,j=0;
		StringTokenizer st = new StringTokenizer(response1, "_");
		
		while(st.hasMoreTokens())
		{
			if( (groupinfo[i][j] = st.nextToken()).equals(null))
					break;
			j++;
			if(j==4)
			{
				i++; j=0;
			}
		}
		
		memCount = i;
		
		System.out.println("�׷쿡 ���� �ο� �� : " + memCount);
		
	}
	
	public void saveNutruition(String response2)
	{
		int i=0, j=0, k=0, count = -1, dataPerMemberCount = 0;
		String currentID = null, temp = null;
		int[] dataPerMember = new int[memCount];
		
		StringTokenizer st = new StringTokenizer(response2, "_");
		
		
		while(st.hasMoreTokens())
		{
			temp = st.nextToken();
			count++;
			
			if(count < 13)
			{
				
				if(k<3)
				{
					//���⿡�� string ������ ������ �Ǿ�� �Ѵ�.
					memNutritionInfo[i][j][k] = temp;
					
					if(i==0 && k==0 && j==0) // �� ��Ȳ�� ID�� �޶����� �����.
					{
						currentID = memNutritionInfo[i][j][k];
					}
					k++;
				}
				else if(k>=3)
				{
					//���⿡�� double ������ ������ �Ǿ�� �Ѵ�.
					//temp���� double ������ �ٲ� �� ���� ���Ѿ� �Ѵ�.
					double tmp = Double.parseDouble(temp);
					NutritionInfo[i][j][k-3] = tmp;
					k++;
				}
			}
			else if(count == 13)
			{
				count = -1;
				
				if(currentID.equals(temp))
				{
					if(((i+1) == memCount))
						dataPerMember[i]++;
					j++; k=0;	// �� ���� �� �޾� ����, ID�� ���ٸ�, �����̸��� �޶�����.
					memNutritionInfo[i][j][k] = temp;
					k++; count++;
				}
				else
				{
					dataPerMember[i] = j+1;
					j=0; k=0; i++;

					memNutritionInfo[i][j][k] = temp;
					currentID = temp;
					k++; count++;
				}
				
			}
				
		}
		if(i != 1)
			dataPerMember[i]++;
		
		
		
		System.out.println("��� �� : " + (i+1) + "\n����� ������ ���� :  " + dataPerMember[0] +" "+dataPerMember[1]+" "+dataPerMember[2]+" "+dataPerMember[3]+" "+ dataPerMember[4]);
		
		Member[] mem = new Member[memCount];
		
		for(int mcount =0; mcount<memCount; mcount++)
		{
			mem[mcount] = new Member(mcount, dataPerMember[mcount] );
			member[mcount] = mem[mcount];
		}
		
	}
	
	
	public void average()
	{
		
	}
	

}
