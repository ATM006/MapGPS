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
     public static final String driver = "com.mysql.jdbc.Driver" ; // 驱动  
     public static final String url = "jdbc:mysql://localhost:3306/GPS" ;// 定义URL  
     public static final String user = "root" ; // 用户名字  
     public static final String password = "11111111" ; // 用户密码  
       
  
     
     private static Connection connection ;// 用于建立连接  
     private static Statement statement ;// 用于执行  
       
    
     public static void main( String[] args ) throws ClassNotFoundException ,  
             SQLException , UnsupportedEncodingException  
     {  
         connect() ;// 先和数据库服务器建立连接  
         //createTable() ;// 建立表  
        // insert() ;// 向表中插入值  
         insert1(16,13,11,2017,7,8,109,36);
         query() ;// 查询  
         close() ;// 关闭连接  
     }  
       
     /** 
      * @throws ClassNotFoundException 
      * @throws SQLException 
      *             此方法建立连接 
      */  
     public static void connect() throws ClassNotFoundException , SQLException  
     {  
           
         Class.forName( driver ) ;  
         connection = DriverManager.getConnection( url , user , password ) ;// 建立连接  
         if( !connection.isClosed() )  
         {  
             System.out.println( "Succeeded connecting to the Database!" ) ;  
         }  
         statement = connection.createStatement() ;// 建立statement  
     }  
       
     /** 
      * @throws SQLException 
      *             建立表 
      */  
     private static void createTable() throws SQLException  
     {  
         String sql = "CREATE TABLE `gps`.`gpsinfo` ( `shi` INT NULL , `fen` INT NULL , `miao` INT NULL , `nian` INT NULL , `yue` INT NULL , `ri` INT NULL , `jing` INT NULL , `wei` INT NULL ) ENGINE = InnoDB ;" ;  
         statement.executeUpdate( "drop table if exists Student ;" ) ;// 如果该表已经有了则删除  
         statement.executeUpdate( sql ) ;// 建立表  
     }  
       
     /** 
      * @throws UnsupportedEncodingException 
      * @throws SQLException 
      *             插入值 
      */  
     private static void insert() throws UnsupportedEncodingException ,  
             SQLException  
     {  
         // 声明常量  
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
             
             preparedStatement.executeUpdate() ;// 执行  
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
     
     preparedStatement.executeUpdate() ;// 执行  
   
}  
     /** 
      * @throws SQLException 
      * @throws UnsupportedEncodingException 
      *             查询 
      */  
     private static void query() throws SQLException ,  
             UnsupportedEncodingException  
     {  
         String query = "select * from gpsinfo" ;  
         ResultSet resultset = null ;  
           
        resultset = statement.executeQuery( query ) ;// 先将结果保存到resultset中  
           
         System.out.println( "时\t分\t秒\t年\t月\t日\t经\t纬" ) ;  
         System.out  
                 .println( "---------------------------------------------------------------" ) ;  
         // 将结果读出来  
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
      *             关闭连接 
      */  
     public static void close() throws SQLException  
     {  
         statement.close() ;// 关闭statement  
         connection.close() ;// 关闭连接  
     }  
       
 }  