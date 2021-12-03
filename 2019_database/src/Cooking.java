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

public class Cooking extends JFrame implements ActionListener {
   static String sqlSelectAll = "select COOKING_NUMBER, MENU_NM, MATERIAL, RECIPE from COOKING;";
   static String sqlInsert = "insert COOKING(COOKING_NUMBER, MENU_NM, MATERIAL, RECIPE) values(?,?, ?, ?);";
   static String sqlDelete = "delete from COOKING where COOKING_NUMBER = ?;";
   static String sqlSearch = "select COOKING_NUMBER, MENU_NM, MATERIAL, RECIPE from COOKING where MENU_NM Like '%?%';";
   static String sqlSearchAll = "select COOKING_NUMBER, MENU_NM, MATERIAL, RECIPE from COOKING;";

   Connection conn = null;
   
   JTable table;
   JTextField idText, nameText, phoneText, MENU_NMText;
   JButton updateButton, searchButton, goback;
   JPanel panel;
   JLabel status;
   
   Cooking(String id) {
      setTitle("COOKING DB 관리");
      
      //창 닫을 때 DB 연결 해제하도록 설정
   /*   addWindowListener(new WindowAdapter() {
         public void windowClosing(WindowEvent e) {
            closeDBConnection();
            System.exit(0);
         }
      });*/
      
      Container contentPane = getContentPane();
      
      // table
      String colNames[] = {"COOKING번호", "메뉴명", "재료", "COOKING방법"};
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
      idText = new JTextField(6);
      MENU_NMText = new JTextField(10);
      nameText = new JTextField(6);
      phoneText = new JTextField(10);
      updateButton = new JButton("수정");
      searchButton = new JButton("검색");
      goback = new JButton("뒤로가기");
      inputPanel.add(new JLabel("COOKING번호"));
      inputPanel.add(idText);
      inputPanel.add(new JLabel("메뉴명"));
      inputPanel.add(MENU_NMText);
      inputPanel.add(new JLabel("재료"));
      inputPanel.add(nameText);
      inputPanel.add(new JLabel("COOKING방법"));
      inputPanel.add(phoneText);
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
      
      setPreferredSize(new Dimension(1400, 750));
      setLocation(0, 0);
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
         if (rs != null)      try { rs.close(); }      catch (Exception e) {}
         if (stmt != null)   try { stmt.close(); }   catch (Exception e) {}
         
         setStatus(count + "개의 COOKING정보가 DB에 존재합니다.");
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
         
         if (src == updateButton) {   // 수정
            int row = table.getSelectedRow();
            if (row == -1) {
               JOptionPane.showMessageDialog(null, "수정할 행을 선택하여 테이블에서 직접 값을 수정한 후 수정 버튼을 클릭하세요.");
            }
            else {
               DefaultTableModel model = (DefaultTableModel) table.getModel();
               String sqlUpdate = "update COOKING set MATERIAL = ?, RECIPE = ? where COOKING_NUMBER = ?;";
               stmt = conn.prepareStatement(sqlUpdate);
               stmt.setString(1, model.getValueAt(row, 2).toString().trim());
               stmt.setString(2, model.getValueAt(row, 3).toString().trim());
               stmt.setInt(3, Integer.parseInt(model.getValueAt(row, 0).toString()));
               
               
               setStatus("DB 수정 중...");
               int num = stmt.executeUpdate();
               
               if (num > 0) {
                  model.fireTableDataChanged();
                  setStatus(num + "명의 COOKING 정보가 수정되었습니다.");
               }
               else
                  setStatus("COOKING 정보가 수정되지 않았습니다.");
            }
         }
         else if (src == searchButton) {   // 검색
            
            String id = idText.getText().trim();
            String nam = MENU_NMText.getText().trim();
            if (id.compareTo("") == 0) {
            	if(nam.compareTo("")==0) {
            		stmt = conn.prepareStatement(sqlSearchAll);
            	}else {
            		stmt = conn.prepareStatement("select COOKING_NUMBER, MENU_NM, MATERIAL, RECIPE from COOKING  where MENU_NM like ?;");
                    stmt.setString(1, "%"+nam+"%");
            	}
            }else {
            	if(nam.compareTo("")==0) {
            		stmt = conn.prepareStatement("select COOKING_NUMBER, MENU_NM, MATERIAL, RECIPE from COOKING  where COOKING_NUMBER = ?");
                    stmt.setString(1, id);
            	}else {
            		stmt = conn.prepareStatement("select COOKING_NUMBER, MENU_NM, MATERIAL, RECIPE from COOKING  where COOKING_NUMBER = ? or MENU_NM like ?;");
                    stmt.setString(1, id);
                    stmt.setString(2, "%"+nam+"%");
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
            String arr[] = new String[4];
            while (rs.next()) {
               arr[0] = rs.getString(1);
               arr[1] = rs.getString(2);
               arr[2] = rs.getString(3);
               arr[3] = rs.getString(4);
               
               model.addRow(arr);
               count++;
            }
            if(count==0) {
               JOptionPane.showMessageDialog(null, "해당 COOKING 정보가 존재하지 않습니다. 올바르게 다시 작성해주세요");
            }else
            setStatus(count + "개의 조리정보가 검색되었습니다.");
         }
      }
      catch (SQLException e) {
         setStatus("DB에 접근할 수 없거나 SQL을 실행할 수 없습니다.");
         
         e.printStackTrace();
      }
      finally {
         // 리소스 반환
         if (rs != null)      try { rs.close(); }      catch (Exception e) {}
         if (stmt != null)   try { stmt.close(); }   catch (Exception e) {}
      }
   }
   
   public void setStatus(String s) {
      status.setText(s);
      this.validate();
   }
   


}