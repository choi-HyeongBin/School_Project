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

public class Bookmark extends JFrame implements ActionListener {
	static String sqlDelete = "delete from BOOKMARK where SERIAL_NUM = ?;";
	static String sqlSearch = "select * from BOOKMARK where ID = ? and MENU_NAM like ?;";
	static String sqlSearchAll = "select * from BOOKMARK where ID = ?;";

	Connection conn = null;
	
	JTable table;
	JTextField nameText, phoneText;
	JButton delButton, searchButton, goback;
	JPanel panel;
	JLabel status;
	
	Bookmark(String id) {
		setTitle("���ã��");
		
		//â ���� �� DB ���� �����ϵ��� ����
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				closeDBConnection();
				System.exit(0);
			}
		});
		
		Container contentPane = getContentPane();
		
		// table
		String colNames[] = {"SERIAL_NUM","ID", "MENUNM"};
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
		nameText = new JTextField(6);
	
		delButton = new JButton("����");
		searchButton = new JButton("�˻�");
		goback = new JButton("�ڷΰ���");
		inputPanel.add(new JLabel("MENUNM"));
		inputPanel.add(nameText);
		
		inputPanel.add(delButton);
		inputPanel.add(searchButton);
		inputPanel.add(goback);

		panel = new JPanel(new BorderLayout());
		panel.add(inputPanel, BorderLayout.CENTER);
		panel.add(status, BorderLayout.SOUTH);
		
		contentPane.add(panel, BorderLayout.SOUTH);
		
		// action listener
		delButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				PreparedStatement stmt = null;
				ResultSet rs = null;
				validateDBConnection();
				Object src = ae.getSource();
				try {
					// DB ���� Ȯ��
					validateDBConnection();
					
					Object src1 = ae.getSource();
					if (src1 == delButton) {	// ����
						int row = table.getSelectedRow();
						if (row == -1) {
							JOptionPane.showMessageDialog(null, "������ ���� ���� ������ �� ���� ��ư�� Ŭ���ϼ���.");
						}
						else {
							DefaultTableModel model = (DefaultTableModel) table.getModel();
							PreparedStatement dstmt = null;
							String sqlDelete = "delete from BOOKMARK where SERIAL_NUM = ?;";
							dstmt = conn.prepareStatement(sqlDelete);
							dstmt.setString(1, model.getValueAt(row, 0).toString());
							
							setStatus("DB���� ���� ��...");
							int numb = dstmt.executeUpdate();
							if (numb > 0) {
								model.removeRow(row);
								setStatus(numb + "���� ���ã�Ⱑ �����Ǿ����ϴ�.");
							}
							else
								setStatus("���ã�Ⱑ �������� �ʾҽ��ϴ�.");
						}
					}
				}catch (SQLException e) {
					setStatus("DB�� ������ �� ���ų� SQL�� ������ �� �����ϴ�.");
					e.printStackTrace();
				}
				finally {
					// ���ҽ� ��ȯ
					if (rs != null)		try { rs.close(); }		catch (Exception e) {}
					if (stmt != null)	try { stmt.close(); }	catch (Exception e) {}
				}
			}
		});
		
		
		
		searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				PreparedStatement stmt = null;
				ResultSet rs = null;
				
				try {
					// DB ���� Ȯ��
					validateDBConnection();
					
					Object src = ae.getSource();
					if (src == searchButton) {	// �˻�
						
						String name = nameText.getText().trim();
						if (name.compareTo("") == 0) {
							stmt = conn.prepareStatement(sqlSearchAll);
							stmt.setString(1, id);
						}
							
						else {
							stmt = conn.prepareStatement(sqlSearch);
							stmt.setString(1, id);
							stmt.setString(2, "%"+name+"%");
						}

						setStatus("DB �˻� ��...");
						rs = stmt.executeQuery();

						// ���̺� ����
						int count=0;
						DefaultTableModel model = (DefaultTableModel) table.getModel();
						int rowNum = model.getRowCount();
						for(int i=rowNum-1; i >= 0; i--)
							model.removeRow(i);
						String arr[] = new String[3];
						while (rs.next()) {
							arr[0] = rs.getString(1);
							arr[1] = rs.getString(2);
							arr[2] = rs.getString(3);
				
							model.addRow(arr);
							count++;
						}
						
						setStatus(count + "���� ���ã�Ⱑ �˻��Ǿ����ϴ�.");
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
		});
		goback.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(e.getSource() == goback) {
					dispose();
					new Home_DB(id);
				}
			}
		});
		
		setPreferredSize(new Dimension(680, 300));
		setLocation(500, 400);
		pack();
		setVisible(true);
		
		initTableWithDB(id);
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
	public int initTableWithDB(String id) {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		int count = 0;
		
		try {
			// DB ����
			status.setText("DB ���� ��...");
			validateDBConnection();
			status.setText("DB ���� �Ϸ�");

			String sql="select * from BOOKMARK where ID = ?";
			
			// statement �غ�
			stmt = conn.prepareStatement(sql);
			
			stmt.setString(1, id);
			// sql ����
			rs = stmt.executeQuery();
			
			// ���̺� ����
			DefaultTableModel model = (DefaultTableModel) table.getModel();
			String arr[] = new String[3];
			while (rs.next()) {
				System.out.println(rs.getString(1));
				arr[0] = rs.getString(1);
				arr[1] = rs.getString(2);
				arr[2] = rs.getString(3);
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
			
			setStatus(count + "���� ���ã�Ⱑ DB�� �����մϴ�.");
		}
		
		return count;
	}

	// ����/����/����/�˻� ó��
	public void actionPerformed(ActionEvent ae) {
		
	}
	
	public void setStatus(String s) {
		status.setText(s);
		this.validate();
	}


}
