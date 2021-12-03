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
		super("MENU DB 관리");
		//창 닫을 때 DB 연결 해제하도록 설정
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				closeDBConnection();
				System.exit(0);
			}
		});
		
		Container contentPane = getContentPane();
		
		// table
		String colNames[] = {"메뉴명", "조리종류", "영양분번호", "조리번호"};
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
		insButton = new JButton("삽입");
		delButton = new JButton("삭제");
		updateButton = new JButton("수정");
		searchButton = new JButton("검색");
		bookMark = new JButton("즐겨찾기 등록");
		goback = new JButton("뒤로가기");
		inputPanel.add(new JLabel("메뉴명"));
		inputPanel.add(menuText);
		inputPanel.add(new JLabel("조리종류"));
		inputPanel.add(cookingListText);
		inputPanel.add(new JLabel("영양분번호"));
		inputPanel.add(nutrimentNumText);
		inputPanel.add(new JLabel("조리번호"));
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
						JOptionPane.showMessageDialog(null, "즐겨찾기에 추가할 행을 선택한 후 클릭하세요.");
					}
					else {
						String sqlbook = "insert into BOOKMARK values(?,?,?);";
						DefaultTableModel model = (DefaultTableModel) table.getModel();
						stmt = conn.prepareStatement(sqlbook);
						stmt.setInt(1, SERIAL_NUM);
						stmt.setString(2, id);
						stmt.setString(3, model.getValueAt(row, 0).toString());
						
						setStatus("DB에서 즐겨찾기 등록 중...");
						int num = stmt.executeUpdate();
						
						if (num > 0) {
							setStatus(num + "개의 메뉴 정보가 즐겨찾기 되었습니다.");
						}
						else
							setStatus("메뉴 정보가 즐겨찾기 되지 않았습니다.");

					}
				}catch (SQLException e) {
					setStatus("DB에 접근할 수 없거나 SQL을 실행할 수 없습니다.");
					e.printStackTrace();
				}finally {
					// 리소스 반환
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
			// 리소스 반환
			if (rs != null)		try { rs.close(); }		catch (Exception e) {}
			if (stmt != null)	try { stmt.close(); }	catch (Exception e) {}
			
			setStatus(count + "개의 메뉴정보가 DB에 존재합니다.");
		}
		
		return count;
	}

	// 삽입/삭제/수정/검색 처리
	public void actionPerformed(ActionEvent ae) {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			// DB 연결 확인
			validateDBConnection();
			
			Object src = ae.getSource();
			if (src == insButton) { 		// 삽입
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
				
				setStatus("DB에 추가 중...");
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
					
					setStatus(num + "개의 메뉴 정보가 추가되었습니다.");
				}
				else
					setStatus("메뉴 정보가 추가되지 않았습니다.");
			}
			else if (src == delButton) {	// 삭제
				int row = table.getSelectedRow();
				if (row == -1) {
					JOptionPane.showMessageDialog(null, "삭제할 행을 먼저 선택한 후 삭제 버튼을 클릭하세요.");
				}
				else {
					DefaultTableModel model = (DefaultTableModel) table.getModel();
					stmt = conn.prepareStatement(sqlDelete);
					stmt.setString(1, model.getValueAt(row, 0).toString());
					
					setStatus("DB에서 삭제 중...");
					int num = stmt.executeUpdate();
					
					if (num > 0) {
						model.removeRow(row);
						setStatus(num + "개의 메뉴 정보가 삭제되었습니다.");
					}
					else
						setStatus("메뉴 정보가 삭제되지 않았습니다.");

				}
			}
			else if (src == updateButton) {	// 수정
				int row = table.getSelectedRow();
				if (row == -1) {
					JOptionPane.showMessageDialog(null, "수정할 행을 선택하여 테이블에서 직접 값을 수정한 후 수정 버튼을 클릭하세요.");
				}
				else {
					DefaultTableModel model = (DefaultTableModel) table.getModel();
					stmt = conn.prepareStatement(sqlUpdate);
					stmt.setString(1, model.getValueAt(row, 1).toString().trim());
					stmt.setInt(2, Integer.parseInt(model.getValueAt(row, 2).toString()));
					stmt.setInt(3, Integer.parseInt(model.getValueAt(row, 3).toString()));
					stmt.setString(4, model.getValueAt(row, 0).toString().trim());
					
					setStatus("DB 수정 중...");
					int num = stmt.executeUpdate();
					
					if (num > 0) {
						model.fireTableDataChanged();
						setStatus(num + "개의 메뉴 정보가 수정되었습니다.");
					}
					else
						setStatus("메뉴 정보가 수정되지 않았습니다.");
				}
			}
			else if (src == searchButton) {	// 검색
				
				String id = menuText.getText().trim();
				if (id.compareTo("") == 0)
					stmt = conn.prepareStatement(sqlSearchAll);
				else {
					stmt = conn.prepareStatement(sqlSearch);	
					stmt.setString(1, "%"+id+"%");
				}

				setStatus("DB 검색 중...");
				rs = stmt.executeQuery();

				// 테이블에 세팅
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
				
				setStatus(count + "개의 메뉴가 검색되었습니다.");
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


}
