
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

public class ID_Overlap_DB {

	//중복확인 클래스
	
   static  boolean id_overlap;
   String driver ="com.mysql.cj.jdbc.Driver";
   String url = "jdbc:mysql://220.67.115.32:3306/stdt055?useSSL=false&serverTimezone=Asia/Seoul";

    public ID_Overlap_DB(){
        try{
            Class.forName(driver);
            System.out.println("오라클 드라이버 성공");   
         }catch(Exception e) {
            System.out.println("드라이버 실패!!!");
         }
         confirm();
      }
  
    
    public Connection getConnection()
      {
         Connection conn = null;
         
         try{
            conn = (Connection) DriverManager.getConnection(url, "stdt055", "anstn606!");
            System.out.println("드라이버 연동 성공");
         }catch(Exception e){
            e.printStackTrace();
            System.out.println("실패");
         }
         return conn;
      }
    
  public void confirm() {
     String sql= "select id from OWNER where ID='"+NewUser_DB.ID+"'";
   
     //resultset 형태의 변수에다가 select sql문에서부터 값을 받을 수 있다.
     ResultSet _id=null;
    
     //1.db연결
      Connection conn = null;
      //2.statement
      Statement stmt = null;
     
      conn=getConnection();
      
      try {
      stmt=conn.createStatement();
       _id=stmt.executeQuery(sql);
       
       //아이디가 데이터베이스에있는것과 일치하는지 검사하기위한 변수 
         String exam=null;
         
       //이해하기
         while(_id.next()) {
            exam=_id.getString(1);
         }
                         
                          if(exam!=null) {
                             JOptionPane.showMessageDialog(null, "아이디가 중복됩니다.");
                             NewUser_DB.usefull_id=false;

                          }else {
                             JOptionPane.showMessageDialog(null, "아이디가 사용가능합니다.");
                             NewUser_DB.usefull_id=true;
                          }
                        
      
      
   } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
   }
  }
    
  
    }
