
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.*;

public class Home_DB extends JFrame implements ActionListener{

	//�޴� Ŭ����
	
	private JPanel contentPane;

	

	
	public Home_DB(String id) {
		super("�ǰ���ɽ�ǰDB");
		
		setVisible(true);
		setSize(400,300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("�ǰ���� ������ǰ");
		lblNewLabel.setBounds(154, 28, 57, 15);
		getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("�̹��� ����");
		lblNewLabel_1.setBounds(101, 53, 170, 123);
		getContentPane().add(lblNewLabel_1);
		
		JButton button_3 = new JButton("�޴�");
		button_3.setBounds(28, 186, 71, 23);
		getContentPane().add(button_3);
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				new Main(id);
			}
		});
		
		JButton button = new JButton("�����");
		button.setBounds(111, 186, 71, 23);
		getContentPane().add(button);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				new Nutriment(id);
			}
		});
		
		JButton button_1 = new JButton("���ã��");
		button_1.setBounds(194, 186, 71, 23);
		getContentPane().add(button_1);
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				new Bookmark(id);
			}
		});
		
		JButton button_2 = new JButton("����");
		button_2.setBounds(277, 186, 71, 23);
		getContentPane().add(button_2);
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				new Cooking(id);
			}
		});
		
	}




	@Override
	public void actionPerformed(ActionEvent e) {
		
	}
}
