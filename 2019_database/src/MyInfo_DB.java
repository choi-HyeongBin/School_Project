
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

//ȸ�� ���� ��ȸ Ŭ����

public class MyInfo_DB {
	String driver ="com.mysql.cj.jdbc.Driver";
	   String url = "jdbc:mysql://220.67.115.32:3306/stdt055?useSSL=false&serverTimezone=Asia/Seoul";
	
	   static String ID,NAME,STUNUMBER,PHONE,LICENSE;
public MyInfo_DB() {

      try {
            Class.forName(driver);
            System.out.println("����̹� ����");   
         } catch (Exception e) {
            System.out.println("����̹� ����!!!");
         }
      id_Check();
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
//id�� ��й�ȣ ���� �α����� ȸ���� ������ ��ȸ�ϴ� �Լ�
public void id_Check() {
     String sql= "select * from OWNER where ID='"+Login_DB.ID+"' and PASSWORD=UNHEX(MD5('"+Login_DB.PW+"'))";
     
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
   //�����ϱ�
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
//////////////��������� id_check()


}