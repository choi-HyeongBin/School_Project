
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;



//로그인 아이디와 비번이 일치하는지 확인하는 클래스

public class Confrim_DB{
	String driver ="com.mysql.cj.jdbc.Driver";
	   String url = "jdbc:mysql://220.67.115.32:3306/stdt055?useSSL=false&serverTimezone=Asia/Seoul";
	

   static  String ID=null;
   static String PW=null;
   public Confrim_DB(){
       
       try {
          Class.forName(driver);
          System.out.println("드라이버 성공");   
       } catch (Exception e) {
          System.out.println("드라이버 실패!!!");
       }
       confirm();
     
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
      
      //아이디와 비번이 일치 하는게 있는지  확인하는 함수 
      public void confirm() {
         
        String sql= "select * from OWNER where ID='"+Login_DB.ID+"' and PW=UNHEX(MD5('"+Login_DB.PW+"'));";
        
       
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
               
               //아이디가 데이터베이스에있는것과 일치하는지 검사하기위한 변수 
                 String exam=null;

//이해하기
                 while(_id.next()) {
                	 ID=_id.getString(1);
                	 PW=_id.getString(2);
                	 exam=_id.getString(1);
                	 }
                
                 if(exam==null) {
                    JOptionPane.showMessageDialog(null, "아이디 혹은 비밀번호를 다시입력하시오");
                     new Login_DB();

                 }else {
                	 new MyInfo_DB();
                    new Home_DB(ID);
                   }
               
              // System.out.println("******추가되었습니다.*********");
            } catch (SQLException e) {
               e.printStackTrace();}
       finally {
               try {
                  stmt.close();
                   conn.close();
                   
                } catch (SQLException e) {
                   // TODO Auto-generated catch block
                   e.printStackTrace();
                }
             //   new Login();
             }
      }
      
      
}