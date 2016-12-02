package application;

import java.util.StringTokenizer;

public class DataManager {
	
	ClientManager client = new ClientManager();
	
	static String groupinfo[][] = new String[6][4]; //(ID_이름_성별_나이)
	static String memNutritionInfo[][][] = new String[6][180][3]; // (ID_날짜_음식이름_)
	static double NutritionInfo[][][] = new double[6][180][10]; //(인분, 열량, 탄수화물, 단백질, 지방, 식이섬유, 비타민C, 칼슘, 나트륨, 철) 
	static int memCount = 0;
	
	static Member member[] = new Member[6];	//최대 6명까지
	
	
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
		
		System.out.println("그룹에 속한 인원 수 : " + memCount);
		
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
					//여기에선 string 정보가 저장이 되어야 한다.
					memNutritionInfo[i][j][k] = temp;
					
					if(i==0 && k==0 && j==0) // 이 상황은 ID가 달라질때 생긴다.
					{
						currentID = memNutritionInfo[i][j][k];
					}
					k++;
				}
				else if(k>=3)
				{
					//여기에선 double 정보가 저장이 되어야 한다.
					//temp값을 double 형으로 바꾼 뒤 저장 시켜야 한다.
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
					j++; k=0;	// 한 행이 다 받아 지고, ID도 같다면, 음식이름만 달라진다.
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
		
		
		
		System.out.println("멤버 수 : " + (i+1) + "\n사람당 데이터 갯수 :  " + dataPerMember[0] +" "+dataPerMember[1]+" "+dataPerMember[2]+" "+dataPerMember[3]+" "+ dataPerMember[4]);
		
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
