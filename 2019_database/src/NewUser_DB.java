

import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class NewUser_DB extends JFrame implements ActionListener{

	//회원가입 클래스
   
   JTextField id;
   JPanel jpl;
   JLabel jlb1,jlb2,jlb3;
   JPasswordField pw;
   JRadioButton jrb1, jrb2;
   JButton btn1, btn2, btn3;
   
   //회원등록하는 클래스에서 값을 사용하기위해서 클래스변수를 선언 한다.
   static String ID,PW;
   //아이디가 사용가능한지의 여부를 알려주는 변수
   static boolean usefull_id=false;
   NewUser_DB(){
      super("회원가입");
      
   jpl = new JPanel();
   jlb1 = new JLabel("회원가입");
   jlb2= new JLabel("ID");
   id = new JTextField(20);
   jlb3= new JLabel("PW");
   pw = new JPasswordField(20);
  
   //중복확인 버튼
btn1 = new JButton("중복확인");


   //가입버튼
   btn2 = new JButton("가입");
   
   
   //처음화면버튼
btn3 = new JButton("처음으로");


   
   //판넬 안에서 컴포넌트 위치 조정...
   jpl.setLayout(null);
   
   //회원가입이라는 글
   jlb1.setBounds(120,40,80,25);
   jpl.add(jlb1);
   
   //아이디
   jlb2.setBounds(50,70,80,25);
   jpl.add(jlb2);
      
   id.setBounds(120,70,180,25);
   jpl.add(id);
   
   
   //패스워드
   jlb3.setBounds(50,100,80,25);
   jpl.add(jlb3);
   
   pw.setBounds(120,100,180,25);
   jpl.add(pw);
   
   
   //중복확인 버튼
   btn1.setBounds(300,70,90,25);
   jpl.add(btn1);
   
   //회원가입 버튼
   btn2.setBounds(50,250,90,25);
   jpl.add(btn2);
   
   //처음화면버튼
   btn3.setBounds(210,250,90,25);
   jpl.add(btn3);
   
   //중복확인 버튼을 누르면 Id_Overlap클래스로 가서 중복을 확인함
    btn1.addActionListener(new ActionListener() {
   
       @Override
       public void actionPerformed(ActionEvent e) {
      // TODO Auto-generated method stub
           ID=id.getText();
          new ID_Overlap_DB();
          
       }
    });
   //확인버튼을 누르면 가입이되고 로그민화면으로 돌아감
   btn2.addActionListener(new ActionListener() {
      
      @Override
      public void actionPerformed(ActionEvent e) {
         // TODO Auto-generated method stub
          ID=id.getText();
          PW=pw.getText();
        
          
         dispose();
         new MemberEnroll_DB();
      }
   });
   
   //처음화면버튼을 누르면 로그인화면으로 돌아감
    btn3.addActionListener(new ActionListener() {
         
         @Override
         public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            dispose();
            new Login_DB();
         }
      });
    
  add(jpl);
      setSize(500, 400); 
      setResizable(false);
         setVisible(true); 
         setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
   }

@Override//오류 않나게 하기위한 허상임 (이유는 인터페이스를 상속받으면 상속받은 인터페이스의 모든 메소드는 오버라이드 해서 구현을 해야지 오류 가 않나기 때문에 ...)ide
public void actionPerformed(ActionEvent e) {
   // TODO Auto-generated method stub
   
}
public static void main(String[] args) {
   //new NewUser();
   
   
}
}