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
		setTitle("영양분");
		
		//창 닫을 때 DB 연결 해제하도록 설정
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				closeDBConnection();
				System.exit(0);
			}
		});
		
		Container contentPane = getContentPane();
		
		// table
		String colNames[] = {"영양분번호","메뉴명", "열량", "탄수화물", "단백질", "지방", "나트륨"};
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
		
		updateButton = new JButton("수정");
		searchButton = new JButton("검색");
		goback = new JButton("뒤로가기");
		
		inputPanel.add(new JLabel("영양분번호"));
		inputPanel.add(nutrimentText);
		inputPanel.add(new JLabel("메뉴명"));
		inputPanel.add(menunamText);
		inputPanel.add(new JLabel("열량"));
		inputPanel.add(calorieText);
		inputPanel.add(new JLabel("탄수화물"));
		inputPanel.add(carboText);
		inputPanel.add(new JLabel("단백질"));
		inputPanel.add(proteinText);
		inputPanel.add(new JLabel("지방"));
		inputPanel.add(fatText);
		inputPanel.add(new JLabel("나트륨"));
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
	public int initTableWithDB() {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		int count = 0;
		
		try {
			// DB 연결
			status.setText("DB 연결 중...");
			validateDBConnection();
			status.setText("DB 연결 완료");
			
			// statement 준비
			stmt = conn.prepareStatement(sqlSelectAll);
			
			// sql 실행
			rs = stmt.executeQuery();
			
			// 테이블에 세팅
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
			// 리소스 반환
			if (rs != null)		try { rs.close(); }		catch (Exception e) {}
			if (stmt != null)	try { stmt.close(); }	catch (Exception e) {}
			
			setStatus(count + "개의 영양분 정보가 DB에 존재합니다.");
		}
		
		return count;
	}

	public void actionPerformed(ActionEvent ae) {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			// DB 연결 확인
			validateDBConnection();
			
			Object src = ae.getSource();
			if (src == updateButton) {	// 수정
				int row = table.getSelectedRow();
				if (row == -1) {
					JOptionPane.showMessageDialog(null, "수정할 행을 선택하여 테이블에서 직접 값을 수정한 후 수정 버튼을 클릭하세요.");
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
					
					setStatus("DB 수정 중...");
					int num = stmt.executeUpdate();
					
					if (num > 0) {
						model.fireTableDataChanged();
						setStatus(num + "개의 영양분 정보가 수정되었습니다.");
					}
					else
						setStatus("영양분 정보가 수정되지 않았습니다.");
				}
			}
			else if (src == searchButton) {	// 검색
				
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
				
				
				
				setStatus("DB 검색 중...");
				rs = stmt.executeQuery();

				// 테이블에 세팅
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
				
				setStatus(count + "개의 영양분 정보가 검색되었습니다.");
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
	
	public void setStatus(String s) {
		status.setText(s);
		this.validate();
	}
	
	public void main(String[] args) {
		
	}
}
