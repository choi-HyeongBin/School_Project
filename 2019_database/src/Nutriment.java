import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class Nutriment extends JFrame implements ActionListener {
	static String sqlSelectAll = "select NUTRIMENT_NUMBER, MENU_NM, CALORIE, CARBOHYDRATE, PROTEIN, FAT, SODIUM from NUTRIMENT;";
	static String sqlSearchAll = "select * from NUTRIMENT;";

	Connection conn = null;
	
	JTable table;
	JTextField nutrimentText, menunamText, calorieText, carboText, proteinText, fatText, sodiumText;
	JButton updateButton, searchButton, goback;
	JPanel panel;
	JLabel status;
	
	Nutriment(String id) {
		setTitle("�����");
		
		//â ���� �� DB ���� �����ϵ��� ����
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				closeDBConnection();
				System.exit(0);
			}
		});
		
		Container contentPane = getContentPane();
		
		// table
		String colNames[] = {"����й�ȣ","�޴���", "����", "ź��ȭ��", "�ܹ���", "����", "��Ʈ��"};
		DefaultTableModel model = new DefaultTableModel(colNames, 0) {
			public boolean isCellEditable(int row, int col) {
				if (col == 0) return false;
				else return true;
			}
		};
		table = new JTable(model);
		status = new JLabel();
		
		contentPane.add(new JScrollPane(table), BorderLayout.CENTER);
		
		// panel
		JPanel inputPanel = new JPanel();
		nutrimentText = new JTextField(6);
		menunamText = new JTextField(6);
		calorieText = new JTextField(6);
		carboText = new JTextField(6);
		proteinText = new JTextField(6);
		fatText = new JTextField(6);
		sodiumText = new JTextField(6);
		
		updateButton = new JButton("����");
		searchButton = new JButton("�˻�");
		goback = new JButton("�ڷΰ���");
		
		inputPanel.add(new JLabel("����й�ȣ"));
		inputPanel.add(nutrimentText);
		inputPanel.add(new JLabel("�޴���"));
		inputPanel.add(menunamText);
		inputPanel.add(new JLabel("����"));
		inputPanel.add(calorieText);
		inputPanel.add(new JLabel("ź��ȭ��"));
		inputPanel.add(carboText);
		inputPanel.add(new JLabel("�ܹ���"));
		inputPanel.add(proteinText);
		inputPanel.add(new JLabel("����"));
		inputPanel.add(fatText);
		inputPanel.add(new JLabel("��Ʈ��"));
		inputPanel.add(sodiumText);
		
		inputPanel.add(updateButton);
		inputPanel.add(searchButton);
		inputPanel.add(goback);

		panel = new JPanel(new BorderLayout());
		panel.add(inputPanel, BorderLayout.CENTER);
		panel.add(status, BorderLayout.SOUTH);
		
		contentPane.add(panel, BorderLayout.SOUTH);
		
		// action listener
		updateButton.addActionListener(this);
		searchButton.addActionListener(this);
		goback.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(e.getSource() == goback) {
					dispose();
					new Home_DB(id);
				}
			}
		});
		setPreferredSize(new Dimension(1300, 300));
		setLocation(500, 400);
		pack();
		setVisible(true);
		
		initTableWithDB();
	}

	//�Ҹ���
	protected void finalize() {
		closeDBConnection();
	}
	
	//DB ������ ��ȿ���� Ȯ��. ��ȿ���� ������ ���ο� ���� ����
	void validateDBConnection() {
		try {
			if (conn == null) {
				conn = DBConnManager.getConnection();
				System.err.println("DB�� ����Ǿ����ϴ�.");
			}
			else if (!conn.isValid(15)) { //15�� �̳��� �������� ������ ������,
				conn.close();
				conn = DBConnManager.getConnection();
				System.err.println("DB�� �翬��Ǿ����ϴ�.");
			}
		}
		catch (SQLException e) {
			System.err.println("DB�� ������ �� �����ϴ�.");
			e.printStackTrace();
		}
	}
	
	//DB ���� ����
	void closeDBConnection() {
		try {
			DBConnManager.closeConnection(conn);
			System.err.println("DB ������ �����Ǿ����ϴ�.");
		}
		catch (SQLException e) {
			System.err.println("DB ���� ���� �� ����!");
			e.printStackTrace();
		}
	}
	
	// ���̺� �ʱ�ȭ (��� ���ڵ�)
	public int initTableWithDB() {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		int count = 0;
		
		try {
			// DB ����
			status.setText("DB ���� ��...");
			validateDBConnection();
			status.setText("DB ���� �Ϸ�");
			
			// statement �غ�
			stmt = conn.prepareStatement(sqlSelectAll);
			
			// sql ����
			rs = stmt.executeQuery();
			
			// ���̺� ����
			DefaultTableModel model = (DefaultTableModel) table.getModel();
			String arr[] = new String[7];
			
			while (rs.next()) {
				arr[0] = rs.getString(1);
				arr[1] = rs.getString(2);
				arr[2] = rs.getString(3);
				arr[3] = rs.getString(4);
				arr[4] = rs.getString(5);
				arr[5] = rs.getString(6);
				arr[6] = rs.getString(7);
				model.addRow(arr);
				count++;
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			// ���ҽ� ��ȯ
			if (rs != null)		try { rs.close(); }		catch (Exception e) {}
			if (stmt != null)	try { stmt.close(); }	catch (Exception e) {}
			
			setStatus(count + "���� ����� ������ DB�� �����մϴ�.");
		}
		
		return count;
	}

	public void actionPerformed(ActionEvent ae) {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			// DB ���� Ȯ��
			validateDBConnection();
			
			Object src = ae.getSource();
			if (src == updateButton) {	// ����
				int row = table.getSelectedRow();
				if (row == -1) {
					JOptionPane.showMessageDialog(null, "������ ���� �����Ͽ� ���̺��� ���� ���� ������ �� ���� ��ư�� Ŭ���ϼ���.");
				}
				else {
					String sqlUpdate = "update NUTRIMENT set CALORIE = ?, CARBOHYDRATE = ?, PROTEIN = ?, FAT = ?, SODIUM = ? where NUTRIMENT_NUMBER = ?;";
					DefaultTableModel model = (DefaultTableModel) table.getModel();
					stmt = conn.prepareStatement(sqlUpdate);
					System.out.println("6 = "+model.getValueAt(row, 0).toString());
					System.out.println("2 = "+model.getValueAt(row, 2).toString());
					stmt.setInt(1, Integer.parseInt(model.getValueAt(row, 2).toString()));
					System.out.println("3 = "+model.getValueAt(row, 3).toString());
					stmt.setInt(2, Integer.parseInt(model.getValueAt(row, 3).toString()));
					System.out.println("4 = "+model.getValueAt(row, 4).toString());
					stmt.setInt(3, Integer.parseInt(model.getValueAt(row, 4).toString()));
					System.out.println("5 = "+model.getValueAt(row, 5).toString());
					stmt.setInt(4, Integer.parseInt(model.getValueAt(row, 5).toString()));
					System.out.println("6 = "+model.getValueAt(row, 6).toString());
					stmt.setInt(5, Integer.parseInt(model.getValueAt(row, 6).toString()));
					stmt.setInt(6, Integer.parseInt(model.getValueAt(row, 0).toString()));
					
					setStatus("DB ���� ��...");
					int num = stmt.executeUpdate();
					
					if (num > 0) {
						model.fireTableDataChanged();
						setStatus(num + "���� ����� ������ �����Ǿ����ϴ�.");
					}
					else
						setStatus("����� ������ �������� �ʾҽ��ϴ�.");
				}
			}
			else if (src == searchButton) {	// �˻�
				
				String nutriment = nutrimentText.getText().trim();
				String menunam = menunamText.getText().trim();
				String numsql = "select * from NUTRIMENT where NUTRIMENT_NUMBER = ?;";
				String menusql = "select * from NUTRIMENT where MENU_NM like ?;";
				String sqlSearch = "select * from NUTRIMENT where NUTRIMENT_NUMBER = ? or MENU_NM like ?;";
				if (nutriment.compareTo("") == 0)
					if(menunam.compareTo("")==0) {
	            		stmt = conn.prepareStatement(sqlSearchAll);
	            	}else {
	            		stmt = conn.prepareStatement(menusql);
	                    stmt.setString(1, "%"+menunam+"%");
	            	}
				else {
					if(menunam.compareTo("")==0) {
	            		stmt = conn.prepareStatement(numsql);
	                    stmt.setString(1, nutriment);
	            	}else {
	            		stmt = conn.prepareStatement(sqlSearch);
	                    stmt.setString(1, nutriment);
	                    stmt.setString(2, "%"+menunam+"%");
	            	}
				}
				
				
				
				setStatus("DB �˻� ��...");
				rs = stmt.executeQuery();

				// ���̺� ����
				int count=0;
				DefaultTableModel model = (DefaultTableModel) table.getModel();
				int rowNum = model.getRowCount();
				for(int i=rowNum-1; i >= 0; i--)
					model.removeRow(i);
				String arr[] = new String[7];
				while (rs.next()) {
					arr[0] = rs.getString(1);
					arr[1] = rs.getString(2);
					arr[2] = rs.getString(3);
					arr[3] = rs.getString(4);
					arr[4] = rs.getString(5);
					arr[5] = rs.getString(6);
					arr[6] = rs.getString(7);
					model.addRow(arr);
					count++;
				}
				
				setStatus(count + "���� ����� ������ �˻��Ǿ����ϴ�.");
			}
		}
		catch (SQLException e) {
			setStatus("DB�� ������ �� ���ų� SQL�� ������ �� �����ϴ�.");
			e.printStackTrace();
		}
		finally {
			// ���ҽ� ��ȯ
			if (rs != null)		try { rs.close(); }		catch (Exception e) {}
			if (stmt != null)	try { stmt.close(); }	catch (Exception e) {}
		}
	}
	
	public void setStatus(String s) {
		status.setText(s);
		this.validate();
	}
	
	public void main(String[] args) {
		
	}
}
