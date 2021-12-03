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

public class Main extends JFrame implements ActionListener {
	static String sqlSelectAll = "select * from MENU;";
	static String sqlDelete = "delete from MENU where MENU_NM = ?;";
	static String sqlUpdate = "update MENU set COOKING_LIST = ?, NUTRIMENT_NUMBER = ?, COOKING_NUMBER = ? where MENU_NM = ?;";
	static String sqlSearch = "select * from MENU where MENU_NM like ?;";
	static String sqlSearchAll = "select * from MENU;";

	Connection conn = null;
	
	JTable table;
	JTextField menuText, cookingListText, nutrimentNumText, cockText;
	JButton insButton, delButton, updateButton, searchButton, bookMark, goback;
	JPanel panel;
	JLabel status;
	
	Main(String id) {
		super("MENU DB ����");
		//â ���� �� DB ���� �����ϵ��� ����
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				closeDBConnection();
				System.exit(0);
			}
		});
		
		Container contentPane = getContentPane();
		
		// table
		String colNames[] = {"�޴���", "��������", "����й�ȣ", "������ȣ"};
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
		menuText = new JTextField(6);
		cookingListText = new JTextField(6);
		nutrimentNumText = new JTextField(5);
		cockText = new JTextField(5);
		insButton = new JButton("����");
		delButton = new JButton("����");
		updateButton = new JButton("����");
		searchButton = new JButton("�˻�");
		bookMark = new JButton("���ã�� ���");
		goback = new JButton("�ڷΰ���");
		inputPanel.add(new JLabel("�޴���"));
		inputPanel.add(menuText);
		inputPanel.add(new JLabel("��������"));
		inputPanel.add(cookingListText);
		inputPanel.add(new JLabel("����й�ȣ"));
		inputPanel.add(nutrimentNumText);
		inputPanel.add(new JLabel("������ȣ"));
		inputPanel.add(cockText);
		inputPanel.add(insButton);
		inputPanel.add(delButton);
		inputPanel.add(updateButton);
		inputPanel.add(searchButton);
		inputPanel.add(bookMark);
		inputPanel.add(goback);

		panel = new JPanel(new BorderLayout());
		panel.add(inputPanel, BorderLayout.CENTER);
		panel.add(status, BorderLayout.SOUTH);
		
		contentPane.add(panel, BorderLayout.SOUTH);
		
		// action listener
		goback.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(e.getSource() == goback) {
					dispose();
					new Home_DB(id);
				}
			}
		});
		insButton.addActionListener(this);
		delButton.addActionListener(this);
		updateButton.addActionListener(this);
		searchButton.addActionListener(this);
		bookMark.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				PreparedStatement stmt = null;
				ResultSet rs = null;
				validateDBConnection();
				Object src = ae.getSource();
				
				try {
					int SERIAL_NUM = 0;
					for(int i=1;i<1000;i++) {
			            String sql= "select SERIAL_NUM FROM BOOKMARK WHERE SERIAL_NUM='"+i+"';";
			            stmt=conn.prepareStatement(sql);
			            ResultSet _id=null;
			        	_id=stmt.executeQuery(sql);
			        	String exam=null;
			        	while(_id.next()) {
			        		exam=_id.getString(1);
			        		}
			        	if(exam!=null) {
			        	}else {
			        		SERIAL_NUM = i;
			        		break;
			        	}
					}
					int row = table.getSelectedRow();
					if (row == -1) {
						JOptionPane.showMessageDialog(null, "���ã�⿡ �߰��� ���� ������ �� Ŭ���ϼ���.");
					}
					else {
						String sqlbook = "insert into BOOKMARK values(?,?,?);";
						DefaultTableModel model = (DefaultTableModel) table.getModel();
						stmt = conn.prepareStatement(sqlbook);
						stmt.setInt(1, SERIAL_NUM);
						stmt.setString(2, id);
						stmt.setString(3, model.getValueAt(row, 0).toString());
						
						setStatus("DB���� ���ã�� ��� ��...");
						int num = stmt.executeUpdate();
						
						if (num > 0) {
							setStatus(num + "���� �޴� ������ ���ã�� �Ǿ����ϴ�.");
						}
						else
							setStatus("�޴� ������ ���ã�� ���� �ʾҽ��ϴ�.");

					}
				}catch (SQLException e) {
					setStatus("DB�� ������ �� ���ų� SQL�� ������ �� �����ϴ�.");
					e.printStackTrace();
				}finally {
					// ���ҽ� ��ȯ
					if (rs != null)		try { rs.close(); }		catch (Exception e) {}
					if (stmt != null)	try { stmt.close(); }	catch (Exception e) {}
				}
			}
		});
		setPreferredSize(new Dimension(1000, 300));
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
			String arr[] = new String[4];
			
			while (rs.next()) {
				arr[0] = rs.getString(1);
				arr[1] = rs.getString(2);
				arr[2] = rs.getString(3);
				arr[3] = rs.getString(4);
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
			
			setStatus(count + "���� �޴������� DB�� �����մϴ�.");
		}
		
		return count;
	}

	// ����/����/����/�˻� ó��
	public void actionPerformed(ActionEvent ae) {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			// DB ���� Ȯ��
			validateDBConnection();
			
			Object src = ae.getSource();
			if (src == insButton) { 		// ����
				String sqlInsert = "insert MENU values(?, ?, ?, ?);";
				stmt = conn.prepareStatement(sqlInsert);
				
				String idStr = menuText.getText().trim();
				String nameStr = cookingListText.getText().trim();
				String phoneStr = nutrimentNumText.getText().trim();
				String cockStr = cockText.getText().trim();
				
				String nutrisql = "insert NUTRIMENT(NUTRIMENT_NUMBER, MENU_NM) values(?, ?);";
				String cooksql = "INSERT COOKING(COOKING_NUMBER, MENU_NM) VALUES(?,?);";
				PreparedStatement nstmt = null;
				PreparedStatement cstmt = null;
				
				nstmt = conn.prepareStatement(nutrisql);
				cstmt = conn.prepareStatement(cooksql);
				
				stmt.setString(1, idStr);
				stmt.setString(2, nameStr);
				stmt.setInt(3, Integer.parseInt(phoneStr));
				stmt.setInt(4, Integer.parseInt(cockStr));
				
				setStatus("DB�� �߰� ��...");
				int num = stmt.executeUpdate();
				
				nstmt.setInt(1, Integer.parseInt(phoneStr));
				nstmt.setString(2, idStr);
				nstmt.executeUpdate();
				
				cstmt.setInt(1, Integer.parseInt(cockStr));
				cstmt.setString(2, idStr);
				cstmt.executeUpdate();
				
				if (num > 0) {
					DefaultTableModel model = (DefaultTableModel) table.getModel();
					String arr[] = new String[4];
					arr[0] = idStr;
					arr[1] = nameStr;
					arr[2] = phoneStr;
					arr[2] = cockStr;
					model.addRow(arr);
					
					setStatus(num + "���� �޴� ������ �߰��Ǿ����ϴ�.");
				}
				else
					setStatus("�޴� ������ �߰����� �ʾҽ��ϴ�.");
			}
			else if (src == delButton) {	// ����
				int row = table.getSelectedRow();
				if (row == -1) {
					JOptionPane.showMessageDialog(null, "������ ���� ���� ������ �� ���� ��ư�� Ŭ���ϼ���.");
				}
				else {
					DefaultTableModel model = (DefaultTableModel) table.getModel();
					stmt = conn.prepareStatement(sqlDelete);
					stmt.setString(1, model.getValueAt(row, 0).toString());
					
					setStatus("DB���� ���� ��...");
					int num = stmt.executeUpdate();
					
					if (num > 0) {
						model.removeRow(row);
						setStatus(num + "���� �޴� ������ �����Ǿ����ϴ�.");
					}
					else
						setStatus("�޴� ������ �������� �ʾҽ��ϴ�.");

				}
			}
			else if (src == updateButton) {	// ����
				int row = table.getSelectedRow();
				if (row == -1) {
					JOptionPane.showMessageDialog(null, "������ ���� �����Ͽ� ���̺��� ���� ���� ������ �� ���� ��ư�� Ŭ���ϼ���.");
				}
				else {
					DefaultTableModel model = (DefaultTableModel) table.getModel();
					stmt = conn.prepareStatement(sqlUpdate);
					stmt.setString(1, model.getValueAt(row, 1).toString().trim());
					stmt.setInt(2, Integer.parseInt(model.getValueAt(row, 2).toString()));
					stmt.setInt(3, Integer.parseInt(model.getValueAt(row, 3).toString()));
					stmt.setString(4, model.getValueAt(row, 0).toString().trim());
					
					setStatus("DB ���� ��...");
					int num = stmt.executeUpdate();
					
					if (num > 0) {
						model.fireTableDataChanged();
						setStatus(num + "���� �޴� ������ �����Ǿ����ϴ�.");
					}
					else
						setStatus("�޴� ������ �������� �ʾҽ��ϴ�.");
				}
			}
			else if (src == searchButton) {	// �˻�
				
				String id = menuText.getText().trim();
				if (id.compareTo("") == 0)
					stmt = conn.prepareStatement(sqlSearchAll);
				else {
					stmt = conn.prepareStatement(sqlSearch);	
					stmt.setString(1, "%"+id+"%");
				}

				setStatus("DB �˻� ��...");
				rs = stmt.executeQuery();

				// ���̺� ����
				int count=0;
				DefaultTableModel model = (DefaultTableModel) table.getModel();
				int rowNum = model.getRowCount();
				for(int i=rowNum-1; i >= 0; i--)
					model.removeRow(i);
				String arr[] = new String[4];
				while (rs.next()) {
					arr[0] = rs.getString(1);
					arr[1] = rs.getString(2);
					arr[2] = rs.getString(3);
					arr[3] = rs.getString(4);
					model.addRow(arr);
					count++;
				}
				
				setStatus(count + "���� �޴��� �˻��Ǿ����ϴ�.");
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


}
