
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;



//�α��� ���̵�� ����� ��ġ�ϴ��� Ȯ���ϴ� Ŭ����

public class Confrim_DB{
	String driver ="com.mysql.cj.jdbc.Driver";
	   String url = "jdbc:mysql://220.67.115.32:3306/stdt055?useSSL=false&serverTimezone=Asia/Seoul";
	

   static  String ID=null;
   static String PW=null;
   public Confrim_DB(){
       
       try {
          Class.forName(driver);
          System.out.println("����̹� ����");   
       } catch (Exception e) {
          System.out.println("����̹� ����!!!");
       }
       confirm();
     
    }
      
      public Connection getConnection()
      {
         Connection conn = null;
         
         try {
            conn = (Connection)DriverManager.getConnection(url, "stdt055", "anstn606!");
            System.out.println("����̹� ���� ����");
         }catch(Exception e){
            e.printStackTrace();
            System.out.println("����");
         }
         return conn;
         
      }
      
      //���̵�� ����� ��ġ �ϴ°� �ִ���  Ȯ���ϴ� �Լ� 
      public void confirm() {
         
        String sql= "select * from OWNER where ID='"+Login_DB.ID+"' and PW=UNHEX(MD5('"+Login_DB.PW+"'));";
        
       
         //resultset ������ �������ٰ� select sql���������� ���� ���� �� �ִ�.
        ResultSet _id=null;
       
        
        //1.db����
         Connection conn = null;
         //2.statement
         Statement stmt = null;
         
         
         conn=getConnection();
        //executeQuery�Լ��� select ������ sql���� ����Ҷ� ���µ� �̰��� ���ԵǸ� resultset���¸� ��ȯ�Ѵ� �׷��� �̸� resultset������ ������ �����ߴ�
         
            try { 
               stmt=conn.createStatement();
               _id=stmt.executeQuery(sql);
               
               //���̵� �����ͺ��̽����ִ°Ͱ� ��ġ�ϴ��� �˻��ϱ����� ���� 
                 String exam=null;

//�����ϱ�
                 while(_id.next()) {
                	 ID=_id.getString(1);
                	 PW=_id.getString(2);
                	 exam=_id.getString(1);
                	 }
                
                 if(exam==null) {
                    JOptionPane.showMessageDialog(null, "���̵� Ȥ�� ��й�ȣ�� �ٽ��Է��Ͻÿ�");
                     new Login_DB();

                 }else {
                	 new MyInfo_DB();
                    new Home_DB(ID);
                   }
               
              // System.out.println("******�߰��Ǿ����ϴ�.*********");
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