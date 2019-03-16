package gps;

import java.io.UnsupportedEncodingException ;  
import java.sql.Connection ;  
import java.sql.DriverManager ;  
import java.sql.PreparedStatement ;  
import java.sql.ResultSet ;  
import java.sql.SQLException ;  
import java.sql.Statement ; 

public class GPSDB  
{  
     public static final String driver = "com.mysql.jdbc.Driver" ; // ����  
     public static final String url = "jdbc:mysql://localhost:3306/GPS" ;// ����URL  
     public static final String user = "root" ; // �û�����  
     public static final String password = "11111111" ; // �û�����  
       
  
     
     private static Connection connection ;// ���ڽ�������  
     private static Statement statement ;// ����ִ��  
       
    
     public static void main( String[] args ) throws ClassNotFoundException ,  
             SQLException , UnsupportedEncodingException  
     {  
         connect() ;// �Ⱥ����ݿ��������������  
         //createTable() ;// ������  
        // insert() ;// ����в���ֵ  
         insert1(16,13,11,2017,7,8,109,36);
         query() ;// ��ѯ  
         close() ;// �ر�����  
     }  
       
     /** 
      * @throws ClassNotFoundException 
      * @throws SQLException 
      *             �˷����������� 
      */  
     public static void connect() throws ClassNotFoundException , SQLException  
     {  
           
         Class.forName( driver ) ;  
         connection = DriverManager.getConnection( url , user , password ) ;// ��������  
         if( !connection.isClosed() )  
         {  
             System.out.println( "Succeeded connecting to the Database!" ) ;  
         }  
         statement = connection.createStatement() ;// ����statement  
     }  
       
     /** 
      * @throws SQLException 
      *             ������ 
      */  
     private static void createTable() throws SQLException  
     {  
         String sql = "CREATE TABLE `gps`.`gpsinfo` ( `shi` INT NULL , `fen` INT NULL , `miao` INT NULL , `nian` INT NULL , `yue` INT NULL , `ri` INT NULL , `jing` INT NULL , `wei` INT NULL ) ENGINE = InnoDB ;" ;  
         statement.executeUpdate( "drop table if exists Student ;" ) ;// ����ñ��Ѿ�������ɾ��  
         statement.executeUpdate( sql ) ;// ������  
     }  
       
     /** 
      * @throws UnsupportedEncodingException 
      * @throws SQLException 
      *             ����ֵ 
      */  
     private static void insert() throws UnsupportedEncodingException ,  
             SQLException  
     {  
         // ��������  
    	 int[] shis = { 1 , 1 , 1 } ;
    	 int[] fens = { 34 , 35 , 36 } ;
    	 int[] miaos = { 34 , 35 , 36 } ;
    	 int[] nians = { 2017 , 2017 , 2017} ;
    	 int[] yues= { 7 , 2 , 2 } ;
    	 int[] ris = { 23 , 1 , 1 } ;
         int[] jings = { 108 , 109 , 110 } ;  
         int[] weis = { 34 , 35 , 36 } ;  
         
           
         int shi=-1,fen=0,miao=0,nian=0,yue=0,ri=0,jing=0,wei=0; 
         //String sql = "insert into Student( Id , Name , Sex , Mail , Adress ) values( ? , ? , ? , ? , ? ) ;" ;  
         String sql = "INSERT INTO `gpsinfo` (`shi`, `fen`, `miao`, `nian`, `yue`, `ri`, `jing`, `wei`) VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
         PreparedStatement preparedStatement = connection.prepareStatement( sql ) ;  
         for( int i = 0 ; i < 3 ; ++ i )  
         {  
             //id = ids[ i ] ;  
             shi = shis[i];
             fen = fens[i];
             miao = miaos[i];
             nian = nians[i];
             yue = yues[i];
             ri = ris[i];
             jing = jings[i];
             wei = weis[i];
            
               
             preparedStatement.setInt(1, shi); 
             preparedStatement.setInt(2, fen); 
             preparedStatement.setInt(3, miao); 
             preparedStatement.setInt(4, nian); 
             preparedStatement.setInt(5, yue); 
             preparedStatement.setInt(6, ri); 
             preparedStatement.setInt(7, jing); 
             preparedStatement.setInt(8, wei); 
             
             preparedStatement.executeUpdate() ;// ִ��  
         }  
     }  
       
static void insert1(int shi,int fen,int miao,int nian,int yue,int ri,int jing,int wei) throws UnsupportedEncodingException ,  SQLException  
{  
 
   
 //int shi=0,fen=0,miao=0,nian=0,yue=0,ri=0,jing=0,wei=0; 
 //String sql = "insert into Student( Id , Name , Sex , Mail , Adress ) values( ? , ? , ? , ? , ? ) ;" ;  
 String sql = "INSERT INTO `gpsinfo` (`shi`, `fen`, `miao`, `nian`, `yue`, `ri`, `jing`, `wei`) VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
 PreparedStatement preparedStatement = connection.prepareStatement( sql ) ;  
 
    
       
     preparedStatement.setInt(1, shi); 
     preparedStatement.setInt(2, fen); 
     preparedStatement.setInt(3, miao); 
     preparedStatement.setInt(4, nian); 
     preparedStatement.setInt(5, yue); 
     preparedStatement.setInt(6, ri); 
     preparedStatement.setInt(7, jing); 
     preparedStatement.setInt(8, wei); 
     
     preparedStatement.executeUpdate() ;// ִ��  
   
}  
     /** 
      * @throws SQLException 
      * @throws UnsupportedEncodingException 
      *             ��ѯ 
      */  
     private static void query() throws SQLException ,  
             UnsupportedEncodingException  
     {  
         String query = "select * from gpsinfo" ;  
         ResultSet resultset = null ;  
           
        resultset = statement.executeQuery( query ) ;// �Ƚ�������浽resultset��  
           
         System.out.println( "ʱ\t��\t��\t��\t��\t��\t��\tγ" ) ;  
         System.out  
                 .println( "---------------------------------------------------------------" ) ;  
         // �����������  
         while( resultset.next() )  
         {  
             String shi = resultset.getString( "shi" ) ; 
             String fen = resultset.getString( "fen" ) ; 
             String miao = resultset.getString( "miao" ) ; 
             String nian = resultset.getString( "nian" ) ; 
             String yue = resultset.getString( "yue" ) ; 
             String ri = resultset.getString( "ri" ) ; 
             String jing = resultset.getString( "jing" ) ; 
             String wei = resultset.getString( "wei" ) ; 
             
             
             System.out.println( shi + "\t" + fen + "\t" + miao + "\t" + nian  
                     + "\t" + yue + "\t" + ri + "\t" + jing +"\t" + wei) ;  
         }  
         System.out  
                 .println( "---------------------------------------------------------------" ) ;  
     }  
       
     /** 
      * @throws SQLException 
      *             �ر����� 
      */  
     public static void close() throws SQLException  
     {  
         statement.close() ;// �ر�statement  
         connection.close() ;// �ر�����  
     }  
       
 }  