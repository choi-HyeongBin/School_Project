
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import javax.swing.JOptionPane;

//가입처리 클래스

public class MemberEnroll_DB {
   
	String driver ="com.mysql.cj.jdbc.Driver";
	   String url = "jdbc:mysql://220.67.115.32:3306/stdt055?useSSL=false&serverTimezone=Asia/Seoul";
	
     
      public MemberEnroll_DB(){
      
            try {
               Class.forName(driver);
               System.out.println("드라이버 성공");   
            } catch (Exception e) {
               System.out.println("드라이버 실패!!!");
            }
         
         insert();
         }
      
      
      public Connection getConnection()
      {
         Connection conn = null;
         
         try {
            conn = (Connection) DriverManager.getConnection(url, "stdt055", "anstn606!");
            System.out.println("드라이버 연동 성공");
         } catch (Exception e) {
            e.printStackTrace();
            System.out.println("실패");
         }
         return conn;
      }
      
      public void insert()
      {
       String sql;
       sql = "insert into OWNER values ('"+NewUser_DB.ID+"',UNHEX(MD5('"+NewUser_DB.PW+"')));";
        NewUser_DB.ID=null;NewUser_DB.PW=null;
        
         //1.db연결
         Connection conn = null;
         //2.statement
         Statement stmt = null;
         
        
         conn=getConnection();
         try { 
               stmt=conn.createStatement();
               
               //중복이 됬는지 확인하는 if문
               if(NewUser_DB.usefull_id==true) {
               stmt.executeUpdate(sql);
               }else {
                  JOptionPane.showMessageDialog(null, "중복확인이 안됬습니다.");
                  return;
               }
            
            } catch (SQLException e) {
               e.printStackTrace();}
       finally {
               try {
                   stmt.close();
                   conn.close();
                   
                } catch (SQLException e){
                   // TODO Auto-generated catch block
                   e.printStackTrace();
                   JOptionPane.showMessageDialog(null, "가입이 않되 었습니다 올바르게 입력하시오");
                   
                   return;
                }
               JOptionPane.showMessageDialog(null, "가입이 완료 되었습니다");
                new Login_DB();
             }
         
      }


   
}