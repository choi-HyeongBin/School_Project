

import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class NewUser_DB extends JFrame implements ActionListener{

	//ȸ������ Ŭ����
   
   JTextField id;
   JPanel jpl;
   JLabel jlb1,jlb2,jlb3;
   JPasswordField pw;
   JRadioButton jrb1, jrb2;
   JButton btn1, btn2, btn3;
   
   //ȸ������ϴ� Ŭ�������� ���� ����ϱ����ؼ� Ŭ���������� ���� �Ѵ�.
   static String ID,PW;
   //���̵� ��밡�������� ���θ� �˷��ִ� ����
   static boolean usefull_id=false;
   NewUser_DB(){
      super("ȸ������");
      
   jpl = new JPanel();
   jlb1 = new JLabel("ȸ������");
   jlb2= new JLabel("ID");
   id = new JTextField(20);
   jlb3= new JLabel("PW");
   pw = new JPasswordField(20);
  
   //�ߺ�Ȯ�� ��ư
btn1 = new JButton("�ߺ�Ȯ��");


   //���Թ�ư
   btn2 = new JButton("����");
   
   
   //ó��ȭ���ư
btn3 = new JButton("ó������");


   
   //�ǳ� �ȿ��� ������Ʈ ��ġ ����...
   jpl.setLayout(null);
   
   //ȸ�������̶�� ��
   jlb1.setBounds(120,40,80,25);
   jpl.add(jlb1);
   
   //���̵�
   jlb2.setBounds(50,70,80,25);
   jpl.add(jlb2);
      
   id.setBounds(120,70,180,25);
   jpl.add(id);
   
   
   //�н�����
   jlb3.setBounds(50,100,80,25);
   jpl.add(jlb3);
   
   pw.setBounds(120,100,180,25);
   jpl.add(pw);
   
   
   //�ߺ�Ȯ�� ��ư
   btn1.setBounds(300,70,90,25);
   jpl.add(btn1);
   
   //ȸ������ ��ư
   btn2.setBounds(50,250,90,25);
   jpl.add(btn2);
   
   //ó��ȭ���ư
   btn3.setBounds(210,250,90,25);
   jpl.add(btn3);
   
   //�ߺ�Ȯ�� ��ư�� ������ Id_OverlapŬ������ ���� �ߺ��� Ȯ����
    btn1.addActionListener(new ActionListener() {
   
       @Override
       public void actionPerformed(ActionEvent e) {
      // TODO Auto-generated method stub
           ID=id.getText();
          new ID_Overlap_DB();
          
       }
    });
   //Ȯ�ι�ư�� ������ �����̵ǰ� �α׹�ȭ������ ���ư�
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
   
   //ó��ȭ���ư�� ������ �α���ȭ������ ���ư�
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

@Override//���� �ʳ��� �ϱ����� ����� (������ �������̽��� ��ӹ����� ��ӹ��� �������̽��� ��� �޼ҵ�� �������̵� �ؼ� ������ �ؾ��� ���� �� �ʳ��� ������ ...)ide
public void actionPerformed(ActionEvent e) {
   // TODO Auto-generated method stub
   
}
public static void main(String[] args) {
   //new NewUser();
   
   
}
}