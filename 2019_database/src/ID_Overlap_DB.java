
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

public class ID_Overlap_DB {

	//�ߺ�Ȯ�� Ŭ����
	
   static  boolean id_overlap;
   String driver ="com.mysql.cj.jdbc.Driver";
   String url = "jdbc:mysql://220.67.115.32:3306/stdt055?useSSL=false&serverTimezone=Asia/Seoul";

    public ID_Overlap_DB(){
        try{
            Class.forName(driver);
            System.out.println("����Ŭ ����̹� ����");   
         }catch(Exception e) {
            System.out.println("����̹� ����!!!");
         }
         confirm();
      }
  
    
    public Connection getConnection()
      {
         Connection conn = null;
         
         try{
            conn = (Connection) DriverManager.getConnection(url, "stdt055", "anstn606!");
            System.out.println("����̹� ���� ����");
         }catch(Exception e){
            e.printStackTrace();
            System.out.println("����");
         }
         return conn;
      }
    
  public void confirm() {
     String sql= "select id from OWNER where ID='"+NewUser_DB.ID+"'";
   
     //resultset ������ �������ٰ� select sql���������� ���� ���� �� �ִ�.
     ResultSet _id=null;
    
     //1.db����
      Connection conn = null;
      //2.statement
      Statement stmt = null;
     
      conn=getConnection();
      
      try {
      stmt=conn.createStatement();
       _id=stmt.executeQuery(sql);
       
       //���̵� �����ͺ��̽����ִ°Ͱ� ��ġ�ϴ��� �˻��ϱ����� ���� 
         String exam=null;
         
       //�����ϱ�
         while(_id.next()) {
            exam=_id.getString(1);
         }
                         
                          if(exam!=null) {
                             JOptionPane.showMessageDialog(null, "���̵� �ߺ��˴ϴ�.");
                             NewUser_DB.usefull_id=false;

                          }else {
                             JOptionPane.showMessageDialog(null, "���̵� ��밡���մϴ�.");
                             NewUser_DB.usefull_id=true;
                          }
                        
      
      
   } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
   }
  }
    
  
    }
