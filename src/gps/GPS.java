package gps;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;

public class GPS {
	
	public  GPSInfo info = new GPSInfo();
	public  GPSDB gpsdb = new GPSDB();
	
	//�õ�ָ����ŵĶ���λ��
	public static  int GetComma(int num,byte str[])
	{
	    int i,j=0;
	    int len=str.length;
	    for(i=0;i<len;i++)
	    {
	        if(str[i]==',')
	        {
	             j++;
	        }

	        if(j==num)
	            return i+1;
	    }
	    return 0;
	}

	
	@SuppressWarnings("static-access")
	public void gps_parse(byte str[]) throws UnsupportedEncodingException, SQLException
	{
		
		int tmp=0;
		
	    if(str[0]=='$'&&str[1]=='G'&&str[2]=='P'&&str[3]=='R'&&str[4]=='M'&&str[5]=='C')
	    {
	        //"GPRMC"
	    	info.setHour((str[7]-'0')*10+(str[8]-'0'));
	    	info.setMinute((str[9]-'0')*10+(str[10]-'0'));
	    	info.setSecond((str[11]-'0')*10+(str[12]-'0'));
	    	tmp = GetComma(9,str);
	    	info.setDay((str[tmp+0]-'0')*10+(str[tmp+1]-'0'));
	    	info.setMonth((str[tmp+2]-'0')*10+(str[tmp+3]-'0'));
	    	info.setYear((str[tmp+4]-'0')*10+(str[tmp+5]-'0')+2000);
	    	
	    	tmp = GetComma(5,str);
	    	info.setJing((str[tmp+0]-'0')*100+(str[tmp+1]-'0')*10+(str[tmp+2]-'0'));
	    	tmp = GetComma(6,str);
	    	info.setJ((char)str[tmp]);
	    	
	    	tmp = GetComma(3,str);
	    	info.setWei((str[tmp+0]-'0')*10+(str[tmp+1]-'0'));
	    	tmp = GetComma(4,str);
	    	info.setW( (char)str[tmp]);
	    	
	    }
//	    else if(str[0]=='$'&&str[1]=='G'&&str[2]=='P'&&str[3]=='G'&&str[4]=='G'&&str[5]=='A')
//	    {
//	    	
//	    	tmp = GetComma(4,str);
//	    	info.setJing((str[tmp+0]-'0')*100+(str[tmp+1]-'0')*10+(str[tmp+2]-'0'));
//	    	tmp = GetComma(5,str);
//	    	info.setJ((char)str[tmp]);
//	    	
//	    	tmp = GetComma(2,str);
//	    	info.setWei((str[tmp+0]-'0')*10+(str[tmp+1]-'0'));
//	    	tmp = GetComma(3,str);
//	    	info.setW( (char)str[tmp]);
//	    }
	    try {
			gpsdb.connect();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	   // if((0!=(info.getYear()+info.getMonth()+info.getDay())&&(info.getMinute()%5==0)))
	    {
	    	
	    gpsdb.insert1(info.getHour(), info.getMinute(), info.getSecond(), info.getYear(), info.getMonth(), info.getDay(), info.getJing(), info.getWei());
	    }
	    gpsdb.close();
	}
	
	
	//У��
	public boolean checksum(byte[] b)
	{
		byte chk=0;// У���
		byte cb=b[1];// ��ǰ�ֽ�
		int i=0;
		if(b[0]!='$')
		return false;
		for(i=2;i<b.length;i++)//����У���
		{
			if(b[i]=='*')
				break;
			cb=(byte)(cb^b[i]);
		}

		if(i!=b.length-3)//У��λ������
			return false;
		i++;
		byte[] bb=new byte[2];//���ڴ��������λ
		bb[0]=b[i++];bb[1]=b[i];
		try
		{
			chk=(byte)Integer.parseInt(new String(bb),16);//����λת��Ϊһ���ֽ�
		}
		catch(Exception e)//����λ�޷�ת��Ϊһ���ֽڣ���ʽ����
		{
		return false;
		}
		
		System.out.println(chk);
		System.out.println(cb);
		return chk==cb;//�������У��������������Ƿ�һ��

	}
	
	public static void main(String[] args) throws UnsupportedEncodingException, SQLException {
		String s1="$GPRMC,105512.026,V,3423.2475,N,10858.3416,E,0.00,0.00,290617,,,N*7E";
		//String s1="GPRMC,015501.130, A, 3606.6834, N, 12021.7778, E, 0.0, 238.3, 010807,,,A*6C";
		String s2="$GPGGA,161229.478,3723.2475,N,12158.3416,W,1,07,1,1.0,9.0,M,7.3,M,,0000,*18";
		byte[] b1=s1.getBytes();
		byte[] b2=s2.getBytes();
		GPS gps = new GPS();
		gps.checksum(b1);
		//if(gps.checksum(b1)==true)
		{
			gps.gps_parse(b1);	
		}
		
		//gps.gps_parse(b2);
		
		System.out.println(gps.info.getYear());
		System.out.println(gps.info.getMonth());
		System.out.println(gps.info.getDay());
		
		System.out.println(gps.info.getHour()+":"+gps.info.getMinute()+":"+gps.info.getSecond());
		
		
		System.out.println(gps.info.getJing()+" "+gps.info.getJ());
		System.out.println(gps.info.getWei()+" "+gps.info.getW());
	}
	
	
}
