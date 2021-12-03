
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

//회원 정보 조회 클래스

public class MyInfo_DB {
	String driver ="com.mysql.cj.jdbc.Driver";
	   String url = "jdbc:mysql://220.67.115.32:3306/stdt055?useSSL=false&serverTimezone=Asia/Seoul";
	
	   static String ID,NAME,STUNUMBER,PHONE,LICENSE;
public MyInfo_DB() {

      try {
            Class.forName(driver);
            System.out.println("드라이버 성공");   
         } catch (Exception e) {
            System.out.println("드라이버 실패!!!");
         }
      id_Check();
}

public Connection getConnection()
{
   Connection conn = null;
   
   try {
      conn = (Connection)DriverManager.getConnection(url, "stdt055", "anstn606!");
      System.out.println("드라이버 연동 성공");
   }catch(Exception e){
      e.printStackTrace();
      System.out.println("실패");
   }
   return conn;
   
}
//id와 비밀번호 각종 로그인한 회원의 정보를 조회하는 함수
public void id_Check() {
     String sql= "select * from OWNER where ID='"+Login_DB.ID+"' and PASSWORD=UNHEX(MD5('"+Login_DB.PW+"'))";
     
       //resultset 형태의 변수에다가 select sql문에서부터 값을 받을 수 있다.
           ResultSet _id=null;
          
           
           //1.db연결
            Connection conn = null;
            //2.statement
            Statement stmt = null;
            
            conn=getConnection();
              //executeQuery함수는 select 종류의 sql문을 사용할때 쓰는데 이것을 쓰게되면 resultset형태를 반환한다 그래서 미리 resultset형태의 변수를 선언했다
            try { 
                  stmt=conn.createStatement();
                  _id=stmt.executeQuery(sql);
   //이해하기
   while(_id.next()){
      ID=_id.getString(1);
           
   }}
    catch (Exception e) {
    // TODO: handle exception
   } finally {
        try {
            stmt.close();
             conn.close();
             
          } catch (SQLException e) {
             // TODO Auto-generated catch block
             e.printStackTrace();
          }
         
       }
            }
//////////////여기까지가 id_check()


}