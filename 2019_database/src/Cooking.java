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
      setTitle("COOKING DB ����");
      
      //â ���� �� DB ���� �����ϵ��� ����
   /*   addWindowListener(new WindowAdapter() {
         public void windowClosing(WindowEvent e) {
            closeDBConnection();
            System.exit(0);
         }
      });*/
      
      Container contentPane = getContentPane();
      
      // table
      String colNames[] = {"COOKING��ȣ", "�޴���", "���", "COOKING���"};
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
      updateButton = new JButton("����");
      searchButton = new JButton("�˻�");
      goback = new JButton("�ڷΰ���");
      inputPanel.add(new JLabel("COOKING��ȣ"));
      inputPanel.add(idText);
      inputPanel.add(new JLabel("�޴���"));
      inputPanel.add(MENU_NMText);
      inputPanel.add(new JLabel("���"));
      inputPanel.add(nameText);
      inputPanel.add(new JLabel("COOKING���"));
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
         if (rs != null)      try { rs.close(); }      catch (Exception e) {}
         if (stmt != null)   try { stmt.close(); }   catch (Exception e) {}
         
         setStatus(count + "���� COOKING������ DB�� �����մϴ�.");
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
         
         if (src == updateButton) {   // ����
            int row = table.getSelectedRow();
            if (row == -1) {
               JOptionPane.showMessageDialog(null, "������ ���� �����Ͽ� ���̺��� ���� ���� ������ �� ���� ��ư�� Ŭ���ϼ���.");
            }
            else {
               DefaultTableModel model = (DefaultTableModel) table.getModel();
               String sqlUpdate = "update COOKING set MATERIAL = ?, RECIPE = ? where COOKING_NUMBER = ?;";
               stmt = conn.prepareStatement(sqlUpdate);
               stmt.setString(1, model.getValueAt(row, 2).toString().trim());
               stmt.setString(2, model.getValueAt(row, 3).toString().trim());
               stmt.setInt(3, Integer.parseInt(model.getValueAt(row, 0).toString()));
               
               
               setStatus("DB ���� ��...");
               int num = stmt.executeUpdate();
               
               if (num > 0) {
                  model.fireTableDataChanged();
                  setStatus(num + "���� COOKING ������ �����Ǿ����ϴ�.");
               }
               else
                  setStatus("COOKING ������ �������� �ʾҽ��ϴ�.");
            }
         }
         else if (src == searchButton) {   // �˻�
            
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
            if(count==0) {
               JOptionPane.showMessageDialog(null, "�ش� COOKING ������ �������� �ʽ��ϴ�. �ùٸ��� �ٽ� �ۼ����ּ���");
            }else
            setStatus(count + "���� ���������� �˻��Ǿ����ϴ�.");
         }
      }
      catch (SQLException e) {
         setStatus("DB�� ������ �� ���ų� SQL�� ������ �� �����ϴ�.");
         
         e.printStackTrace();
      }
      finally {
         // ���ҽ� ��ȯ
         if (rs != null)      try { rs.close(); }      catch (Exception e) {}
         if (stmt != null)   try { stmt.close(); }   catch (Exception e) {}
      }
   }
   
   public void setStatus(String s) {
      status.setText(s);
      this.validate();
   }
   


}