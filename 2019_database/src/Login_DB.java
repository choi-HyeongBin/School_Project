
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
   
   //�α��� Ŭ����
   
   //confrimŬ���� �� homeŬ���� �� �ѱ�� ���� ����
   static String ID,PW;
   
   public Login_DB() {
      super("�α���");
      id=new JTextField(20);
      pw=new JPasswordField(4);
      jp1=new JPanel();
      lb1=new JLabel("���̵�   ");
      lb2=new JLabel("��й�ȣ");
      lb3=new JLabel("�α��� ");
      btn1 = new JButton("Ȯ��");
      btn2 = new JButton("ȸ������");
      //������Ʈ�� �ǳڿ� ��� ��ġ�Ұ����� ���ϰ� �߰�
      //�α��� â�̶�� �˸��°�
      jp1.setLayout(null);
      lb3.setBounds(110,40,80,25);
      jp1.add(lb3);
      //���̵�
      
      lb1.setBounds(10,100,80,25);
      jp1.add(lb1);
      
      id.setBounds(100,100,160,25);
      jp1.add(id);
      
      //���
      lb2.setBounds(10,130,80,25);
      jp1.add(lb2);
      
      pw.setBounds(100,130,160,25);
      jp1.add(pw);
      
      //Ȯ�ι�ư
      btn1.setBounds(10,160,100,25);
      jp1.add(btn1);
      //�ֿ����Թ�ư
      btn2.setBounds(160,160,100,25);
      jp1.add(btn2);
      //������Ʈ�� �ǳڿ� �߰�
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
       
       
      
       //Ȯ�ι�ư ������ �Է��� ���̵�� ����� ��� ��ȸ���� ��ġ�Ѱ��� ������ Ȩȭ�鿡 ������ ���� ����Ѵ�
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
       
       //ȸ������ ��ư ������ ���ο� ȸ������ â���� �ǰ� �ϴ� �Լ��� ����
       btn2.addActionListener(new ActionListener() {
         
         @Override
         public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            
            dispose();
            new NewUser_DB();
            
            
            //�α��� â���� �Է��ߴ� ���̵�� ��й�ȣ �ʱ�ȭ 
            Login_DB.ID=null;Login_DB.PW=null;
            
         }
      });
       
       
   }
   
   //���� �ʳ��� �ϱ����� ����� (������ �������̽��� ��ӹ����� ��ӹ��� �������̽��� ��� �޼ҵ�� �������̵� �ؼ� ������ �ؾ��� ���� �� �ʳ��� ������ ...)
   @Override
   public void actionPerformed(ActionEvent e) {
      // TODO Auto-generated method stub
      
      
      
   }

   public static void main(String[] args) {
      // TODO Auto-generated method stub
      new Login_DB();
      
   }

}