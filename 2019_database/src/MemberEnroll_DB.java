
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import javax.swing.JOptionPane;

//����ó�� Ŭ����

public class MemberEnroll_DB {
   
	String driver ="com.mysql.cj.jdbc.Driver";
	   String url = "jdbc:mysql://220.67.115.32:3306/stdt055?useSSL=false&serverTimezone=Asia/Seoul";
	
     
      public MemberEnroll_DB(){
      
            try {
               Class.forName(driver);
               System.out.println("����̹� ����");   
            } catch (Exception e) {
               System.out.println("����̹� ����!!!");
            }
         
         insert();
         }
      
      
      public Connection getConnection()
      {
         Connection conn = null;
         
         try {
            conn = (Connection) DriverManager.getConnection(url, "stdt055", "anstn606!");
            System.out.println("����̹� ���� ����");
         } catch (Exception e) {
            e.printStackTrace();
            System.out.println("����");
         }
         return conn;
      }
      
      public void insert()
      {
       String sql;
       sql = "insert into OWNER values ('"+NewUser_DB.ID+"',UNHEX(MD5('"+NewUser_DB.PW+"')));";
        NewUser_DB.ID=null;NewUser_DB.PW=null;
        
         //1.db����
         Connection conn = null;
         //2.statement
         Statement stmt = null;
         
        
         conn=getConnection();
         try { 
               stmt=conn.createStatement();
               
               //�ߺ��� ����� Ȯ���ϴ� if��
               if(NewUser_DB.usefull_id==true) {
               stmt.executeUpdate(sql);
               }else {
                  JOptionPane.showMessageDialog(null, "�ߺ�Ȯ���� �ȉ���ϴ�.");
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
                   JOptionPane.showMessageDialog(null, "������ �ʵ� �����ϴ� �ùٸ��� �Է��Ͻÿ�");
                   
                   return;
                }
               JOptionPane.showMessageDialog(null, "������ �Ϸ� �Ǿ����ϴ�");
                new Login_DB();
             }
         
      }


   
}