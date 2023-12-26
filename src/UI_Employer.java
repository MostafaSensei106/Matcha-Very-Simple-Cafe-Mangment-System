/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseEvent;
import java.sql.*;
import java.util.Random;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
/**
 * @author DELL
 */
public class UI_Employer extends javax.swing.JFrame {
    class EditableTableModel extends DefaultTableModel {
        EditableTableModel(Object[] columnNames, int rowCount) {
            super(columnNames, rowCount);
        }

        @Override
        public boolean isCellEditable(int row, int column) {
            return true; // This causes all cells to be editable
        }
    }
    /**
     * 
     */
    public UI_Employer() {
    initComponents();
    this.setLocationRelativeTo(this);
    try {
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/matcha_cafe", "root", "root");
        String sql = "SELECT * FROM m_matcha";
        PreparedStatement stmt = con.prepareStatement(sql);
        ResultSet resultSet = stmt.executeQuery();
        EditableTableModel model = new EditableTableModel(new String[]{"ID","UN_ID", "Password ", "Position ", "First Name", "Gender", "Salary", "Phone"}, 0);

        while (resultSet.next()) {
            int id = resultSet.getInt("ID");
            int password = resultSet.getInt("Password");
            String position = resultSet.getString("Position");
            int uniqueID = resultSet.getInt("UN_ID");
            String Name = resultSet.getString("Name");
            String Gender = resultSet.getString("Gender");
            int Salary = resultSet.getInt("Salary");
            double PhoneNumber = resultSet.getDouble("Phone");
            model.addRow(new Object[]{id,uniqueID, password, position, Name, Gender, Salary, PhoneNumber});
        }
        Mem_TP.setModel(model);

        // Add a listener to the table model
        model.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                if (e.getType() == TableModelEvent.UPDATE) {
                    int row = e.getFirstRow();// GET ID
                    int column = e.getColumn();// بجيب العمود الي حصل فية التغير
                    Object newValue = model.getValueAt(row, column);// باخد القيم الجديدة
                                                                                                   // باخد الفاليو من اول عنصر
                    Object id = model.getValueAt(row, 0);
                    String columnName = model.getColumnName(column);// عنوان الصف

                    try {
                        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/matcha_cafe", "root", "root");
                        String sql = "UPDATE m_matcha SET " + columnName + " = ? WHERE ID = ?";
                        PreparedStatement stmt = con.prepareStatement(sql);
                        stmt.setObject(1, newValue);
                        stmt.setObject(2, id);
                        stmt.executeUpdate();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Emp_Panal = new javax.swing.JPanel();
        jDesktopPane1 = new javax.swing.JDesktopPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        Mem_TP = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        text_password = new javax.swing.JTextField();
        Emp_name = new javax.swing.JTextField();
        phone = new javax.swing.JTextField();
        text_salary = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        btn_add = new javax.swing.JButton();
        btn_del = new javax.swing.JButton();
        Back_To_Admin = new javax.swing.JButton();
        Gender = new javax.swing.JComboBox<>();
        Position = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Emp_Mangement");

        Emp_Panal.setName(""); // NOI18N

        Mem_TP.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        Mem_TP.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Password", "Possition", "First name", "Last name", "Gender", "Salary", "Phone number"
            }
        ));
        jScrollPane1.setViewportView(Mem_TP);

        jLabel1.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel1.setText("Password :");

        text_salary.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                text_salaryActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel2.setText(" Name :");

        jLabel3.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel3.setText("Gender : ");

        jLabel4.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel4.setText("Position : ");

        jLabel5.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel5.setText("Salary : ");

        jLabel6.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel6.setText("Phone Number : ");

        btn_add.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        btn_add.setText("Add ");
        btn_add.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_addMouseClicked(evt);
            }
        });
        btn_add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_addActionPerformed(evt);
            }
        });

        btn_del.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        btn_del.setText("Delet ");
        btn_del.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_delMouseClicked(evt);
            }
        });
        btn_del.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_delActionPerformed(evt);
            }
        });

        Back_To_Admin.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        Back_To_Admin.setText("Back");
        Back_To_Admin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Back_To_AdminMouseClicked(evt);
            }
        });

        Gender.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        Gender.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Male", "Femal", "مهندس" }));

        Position.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        Position.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Admin", "Employer" }));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Back_To_Admin, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(100, 100, 100)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(jLabel3)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(Gender, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(jLabel2)
                            .addGap(5, 5, 5)
                            .addComponent(Emp_name, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(jLabel1)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(text_password, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGap(18, 18, 18)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel6)
                        .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(phone)
                        .addComponent(text_salary)
                        .addComponent(Position, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(btn_add, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 143, Short.MAX_VALUE)
                        .addComponent(btn_del, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(100, 100, 100)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(154, Short.MAX_VALUE)
                .addComponent(Back_To_Admin, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(16, 16, 16)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(Gender, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(Position, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(Emp_name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel2))
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(phone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel6)
                                    .addComponent(btn_add)))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel1)
                                    .addComponent(text_password, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel5)
                                    .addComponent(text_salary, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(btn_del))
                            .addGap(42, 42, 42)))
                    .addContainerGap(80, Short.MAX_VALUE)))
        );

        jDesktopPane1.setLayer(jScrollPane1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane1.setLayer(jPanel1, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jDesktopPane1Layout = new javax.swing.GroupLayout(jDesktopPane1);
        jDesktopPane1.setLayout(jDesktopPane1Layout);
        jDesktopPane1Layout.setHorizontalGroup(
            jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDesktopPane1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 815, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jDesktopPane1Layout.setVerticalGroup(
            jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDesktopPane1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout Emp_PanalLayout = new javax.swing.GroupLayout(Emp_Panal);
        Emp_Panal.setLayout(Emp_PanalLayout);
        Emp_PanalLayout.setHorizontalGroup(
            Emp_PanalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Emp_PanalLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jDesktopPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 6, Short.MAX_VALUE))
        );
        Emp_PanalLayout.setVerticalGroup(
            Emp_PanalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Emp_PanalLayout.createSequentialGroup()
                .addComponent(jDesktopPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Emp_Panal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(Emp_Panal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 1, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_delActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_delActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_delActionPerformed

    private void btn_addActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_addActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_addActionPerformed

    private void text_salaryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_text_salaryActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_text_salaryActionPerformed

    private void btn_addMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_addMouseClicked
        // TODO add your handling code here:
        if (Emp_name.getText().isEmpty() || text_password.getText().isEmpty() || phone.getText().isEmpty() || text_salary.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Do Not Let Data Empty", "ERROR", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Random rand = new Random();
        int uniqueID = rand.nextInt(9000) + 1000;

        String Password = text_password.getText();
        String Name = Emp_name.getText();
        int salary = Integer.parseInt(text_salary.getText());
        Double Phone_number = Double.parseDouble(phone.getText());
        String Gen = (String) Gender.getSelectedItem();
        String Posithion_emp = (String) Position.getSelectedItem();


        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/matcha_cafe", "root", "root");
            String sql = "INSERT INTO m_matcha (Password, Position, UN_ID,Name, Gender, Salary, Phone) VALUES (?, ?,?, ?, ?, ?, ?)";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, Password);
            stmt.setString(2, Posithion_emp);
            stmt.setInt(3, uniqueID);
            stmt.setString(4, Name);
            stmt.setString(5, Gen);
            stmt.setInt(6, salary);
            stmt.setDouble(7, Phone_number);
            stmt.executeUpdate();
            Af_Update();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }//GEN-LAST:event_btn_addMouseClicked

    private void btn_delMouseClicked(MouseEvent evt) {//GEN-FIRST:event_btn_delMouseClicked
        // TODO add your handling code here:
        int selectedRowIndex = Mem_TP.getSelectedRow();
        if (selectedRowIndex != -1) {

            // Retrieve data from the selected row
            Object ID = Mem_TP.getValueAt(selectedRowIndex, 0);

            try {
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/matcha_cafe", "root", "root");
                String sql = "DELETE FROM m_matcha WHERE ID = ?";
                PreparedStatement stmt = con.prepareStatement(sql);
                stmt.setString(1, String.valueOf(ID)); // Assuming ID is a string; adjust if it's an integer

                stmt.executeUpdate();
                Af_Update();

                stmt.close();
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


    }//GEN-LAST:event_btn_delMouseClicked

    private void Back_To_AdminMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Back_To_AdminMouseClicked
        // TODO add your handling code here:
     //   try {

//            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/matcha_cafe", "root", "root");
//            String AD_EM_Name = "";
//            String sql = "SELECT * FROM m_matcha WHERE ID = ? AND Password = ? ";
//            PreparedStatement stmt = con.prepareStatement(sql);
//            stmt.setString(1, ID_Login.getText());
//
//                String AD_EM_Name = resultSet.getString("Name");


            UI_Admin Back_Admin = new UI_Admin();
//            Back_Admin.setE(AD_EM_Name);
            Back_Admin.show();
            dispose();
            Back_Admin.setTitle("Matcha Cafe - Admin");
            Back_Admin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }






    }//GEN-LAST:event_Back_To_AdminMouseClicked



    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(UI_Employer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UI_Employer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UI_Employer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UI_Employer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new UI_Employer().setVisible(true);
            }
        });
    }

    public void Af_Update() {
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/matcha_cafe", "root", "root");
            String sql = "SELECT * FROM m_matcha";
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet resultSet = stmt.executeQuery();
            EditableTableModel model = new EditableTableModel(new String[]{"ID","UN_ID", "Password ", "Position ", "First Name", "Gender", "Salary", "Phone"}, 0);

            while (resultSet.next()) {
                int id = resultSet.getInt("ID");
                int uniqueID = resultSet.getInt("UN_ID");
                int password = resultSet.getInt("Password");
                String position = resultSet.getString("Position");
                String Name = resultSet.getString("Name");
                String Gender = resultSet.getString("Gender");
                int Salary = resultSet.getInt("Salary");
                double PhoneNumber = resultSet.getDouble("Phone");
                model.addRow(new Object[]{id,uniqueID, password, position, Name, Gender, Salary, PhoneNumber});
            }            // Set the model to the JTable
            Mem_TP.setModel(model);

            // Add a listener to the table model
            model.addTableModelListener(new TableModelListener() {
@Override
public void tableChanged(TableModelEvent e) {
    if (e.getType() == TableModelEvent.UPDATE) {
        int row = e.getFirstRow();
        int column = e.getColumn();
        Object newValue = model.getValueAt(row, column);

        // Assuming the first column is the ID
        Object id = model.getValueAt(row, 0);

        // Get column name
        String columnName = model.getColumnName(column);

        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/matcha_cafe", "root", "root");
            String sql = "UPDATE m_matcha SET " + columnName + " = ? WHERE ID = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setObject(1, newValue);
            stmt.setObject(2, id);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}            });
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Back_To_Admin;
    private javax.swing.JPanel Emp_Panal;
    private javax.swing.JTextField Emp_name;
    private javax.swing.JComboBox<String> Gender;
    private javax.swing.JTable Mem_TP;
    private javax.swing.JComboBox<String> Position;
    private javax.swing.JButton btn_add;
    private javax.swing.JButton btn_del;
    private javax.swing.JDesktopPane jDesktopPane1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField phone;
    private javax.swing.JTextField text_password;
    private javax.swing.JTextField text_salary;
    // End of variables declaration//GEN-END:variables
}
