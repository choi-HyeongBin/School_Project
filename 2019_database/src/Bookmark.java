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
		setTitle("즐겨찾기");
		
		//창 닫을 때 DB 연결 해제하도록 설정
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
	
		delButton = new JButton("삭제");
		searchButton = new JButton("검색");
		goback = new JButton("뒤로가기");
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
					// DB 연결 확인
					validateDBConnection();
					
					Object src1 = ae.getSource();
					if (src1 == delButton) {	// 삭제
						int row = table.getSelectedRow();
						if (row == -1) {
							JOptionPane.showMessageDialog(null, "삭제할 행을 먼저 선택한 후 삭제 버튼을 클릭하세요.");
						}
						else {
							DefaultTableModel model = (DefaultTableModel) table.getModel();
							PreparedStatement dstmt = null;
							String sqlDelete = "delete from BOOKMARK where SERIAL_NUM = ?;";
							dstmt = conn.prepareStatement(sqlDelete);
							dstmt.setString(1, model.getValueAt(row, 0).toString());
							
							setStatus("DB에서 삭제 중...");
							int numb = dstmt.executeUpdate();
							if (numb > 0) {
								model.removeRow(row);
								setStatus(numb + "명의 즐겨찾기가 삭제되었습니다.");
							}
							else
								setStatus("즐겨찾기가 삭제되지 않았습니다.");
						}
					}
				}catch (SQLException e) {
					setStatus("DB에 접근할 수 없거나 SQL을 실행할 수 없습니다.");
					e.printStackTrace();
				}
				finally {
					// 리소스 반환
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
					// DB 연결 확인
					validateDBConnection();
					
					Object src = ae.getSource();
					if (src == searchButton) {	// 검색
						
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

						setStatus("DB 검색 중...");
						rs = stmt.executeQuery();

						// 테이블에 세팅
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
						
						setStatus(count + "명의 즐겨찾기가 검색되었습니다.");
					}
				}
				catch (SQLException e) {
					setStatus("DB에 접근할 수 없거나 SQL을 실행할 수 없습니다.");
					e.printStackTrace();
				}
				finally {
					// 리소스 반환
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

	//소멸자
	protected void finalize() {
		closeDBConnection();
	}
	
	//DB 연결이 유효한지 확인. 유효하지 않으면 새로운 연결 수립
	void validateDBConnection() {
		try {
			if (conn == null) {
				conn = DBConnManager.getConnection();
				System.err.println("DB가 연결되었습니다.");
			}
			else if (!conn.isValid(15)) { //15초 이내에 정상적인 응답이 없으면,
				conn.close();
				conn = DBConnManager.getConnection();
				System.err.println("DB가 재연결되었습니다.");
			}
		}
		catch (SQLException e) {
			System.err.println("DB에 접근할 수 없습니다.");
			e.printStackTrace();
		}
	}
	
	//DB 연결 해제
	void closeDBConnection() {
		try {
			DBConnManager.closeConnection(conn);
			System.err.println("DB 연결이 해제되었습니다.");
		}
		catch (SQLException e) {
			System.err.println("DB 연결 해제 중 에러!");
			e.printStackTrace();
		}
	}
	
	// 테이블 초기화 (모든 레코드)
	public int initTableWithDB(String id) {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		int count = 0;
		
		try {
			// DB 연결
			status.setText("DB 연결 중...");
			validateDBConnection();
			status.setText("DB 연결 완료");

			String sql="select * from BOOKMARK where ID = ?";
			
			// statement 준비
			stmt = conn.prepareStatement(sql);
			
			stmt.setString(1, id);
			// sql 실행
			rs = stmt.executeQuery();
			
			// 테이블에 세팅
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
			// 리소스 반환
			if (rs != null)		try { rs.close(); }		catch (Exception e) {}
			if (stmt != null)	try { stmt.close(); }	catch (Exception e) {}
			
			setStatus(count + "개의 즐겨찾기가 DB에 존재합니다.");
		}
		
		return count;
	}

	// 삽입/삭제/수정/검색 처리
	public void actionPerformed(ActionEvent ae) {
		
	}
	
	public void setStatus(String s) {
		status.setText(s);
		this.validate();
	}


}
