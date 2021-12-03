
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Login_DB extends JFrame implements ActionListener{

   
   JTextField id;
   JPasswordField pw; 
   JButton btn1,btn2;
   JPanel jp1,jp2;
   JLabel lb1,lb2,lb3;
   
   //로그인 클래스
   
   //confrim클래스 와 home클래스 로 넘기기 위한 변수
   static String ID,PW;
   
   public Login_DB() {
      super("로그인");
      id=new JTextField(20);
      pw=new JPasswordField(4);
      jp1=new JPanel();
      lb1=new JLabel("아이디   ");
      lb2=new JLabel("비밀번호");
      lb3=new JLabel("로그인 ");
      btn1 = new JButton("확인");
      btn2 = new JButton("회원가입");
      //컴포넌트를 판넬에 어디에 위치할것인지 정하고 추가
      //로그인 창이라고 알리는것
      jp1.setLayout(null);
      lb3.setBounds(110,40,80,25);
      jp1.add(lb3);
      //아이디
      
      lb1.setBounds(10,100,80,25);
      jp1.add(lb1);
      
      id.setBounds(100,100,160,25);
      jp1.add(id);
      
      //비번
      lb2.setBounds(10,130,80,25);
      jp1.add(lb2);
      
      pw.setBounds(100,130,160,25);
      jp1.add(pw);
      
      //확인버튼
      btn1.setBounds(10,160,100,25);
      jp1.add(btn1);
      //휘원가입버튼
      btn2.setBounds(160,160,100,25);
      jp1.add(btn2);
      //컴포넌트를 판넬에 추가
      //jp1.add(lb1);
      //jp1.add(id);
      //jp1.add(lb2);
      //jp1.add(pw);
      //jp1.add(btn1);
      
      
      
      add(jp1,BorderLayout.CENTER);
       setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       setResizable(false);
       setVisible(true);
       setSize(300,300);
       
       
      
       //확인버튼 누르면 입력한 아이디와 비번을 디비를 조회한후 일치한것이 있으면 홈화면에 들어오는 것을 허락한다
        btn1.addActionListener(new ActionListener() {
         
         @Override
         public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            ID=id.getText();
            PW=pw.getText();

            dispose();
            new Confrim_DB();
         }
      });
       
       //회원가입 버튼 누르면 새로운 회원가입 창으로 되게 하는 함수를 구현
       btn2.addActionListener(new ActionListener() {
         
         @Override
         public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            
            dispose();
            new NewUser_DB();
            
            
            //로그인 창에서 입력했던 아이디와 비밀번호 초기화 
            Login_DB.ID=null;Login_DB.PW=null;
            
         }
      });
       
       
   }
   
   //오류 않나게 하기위한 허상임 (이유는 인터페이스를 상속받으면 상속받은 인터페이스의 모든 메소드는 오버라이드 해서 구현을 해야지 오류 가 않나기 때문에 ...)
   @Override
   public void actionPerformed(ActionEvent e) {
      // TODO Auto-generated method stub
      
      
      
   }

   public static void main(String[] args) {
      // TODO Auto-generated method stub
      new Login_DB();
      
   }

}