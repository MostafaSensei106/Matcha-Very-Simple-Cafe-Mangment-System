/* * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template */import javax.swing.*;import java.sql.*;/** * @author Mostafa_Sensei106 */public class UI_Login extends javax.swing.JFrame {    /**     * Creates new form UI_Login     */    public UI_Login() {        initComponents();        this.setLocationRelativeTo(this);    }    /**     * This method is called from within the constructor to initialize the form.     * WARNING: Do NOT modify this code. The content of this method is always     * regenerated by the Form Editor.     */    @SuppressWarnings("unchecked")    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents    private void initComponents() {        jDesktopPane1 = new javax.swing.JDesktopPane();        Login_panal = new javax.swing.JPanel();        ID_Login = new javax.swing.JTextField();        ID_Password = new javax.swing.JPasswordField();        jLabel1 = new javax.swing.JLabel();        jLabel2 = new javax.swing.JLabel();        jLabel3 = new javax.swing.JLabel();        Login_btn = new javax.swing.JButton();        Show_pass = new javax.swing.JCheckBox();        javax.swing.GroupLayout jDesktopPane1Layout = new javax.swing.GroupLayout(jDesktopPane1);        jDesktopPane1.setLayout(jDesktopPane1Layout);        jDesktopPane1Layout.setHorizontalGroup(            jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)            .addGap(0, 100, Short.MAX_VALUE)        );        jDesktopPane1Layout.setVerticalGroup(            jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)            .addGap(0, 100, Short.MAX_VALUE)        );        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);        Login_panal.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N        ID_Login.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N        ID_Login.addActionListener(new java.awt.event.ActionListener() {            public void actionPerformed(java.awt.event.ActionEvent evt) {                ID_LoginActionPerformed(evt);            }        });        ID_Password.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N        ID_Password.addActionListener(new java.awt.event.ActionListener() {            public void actionPerformed(java.awt.event.ActionEvent evt) {                ID_PasswordActionPerformed(evt);            }        });        jLabel1.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N        jLabel1.setText("ID");        jLabel2.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N        jLabel2.setText("Psassword");        jLabel2.setToolTipText("");        jLabel3.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N        jLabel3.setText("Welcome To Matcha Cafe");        Login_btn.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N        Login_btn.setText("Login");        Login_btn.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));        Login_btn.addMouseListener(new java.awt.event.MouseAdapter() {            public void mouseClicked(java.awt.event.MouseEvent evt) {                Login_btnMouseClicked(evt);            }        });        Login_btn.addActionListener(new java.awt.event.ActionListener() {            public void actionPerformed(java.awt.event.ActionEvent evt) {                Login_btnActionPerformed(evt);            }        });        Show_pass.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N        Show_pass.setText("Show Password");        Show_pass.addActionListener(new java.awt.event.ActionListener() {            public void actionPerformed(java.awt.event.ActionEvent evt) {                Show_passActionPerformed(evt);            }        });        javax.swing.GroupLayout Login_panalLayout = new javax.swing.GroupLayout(Login_panal);        Login_panal.setLayout(Login_panalLayout);        Login_panalLayout.setHorizontalGroup(            Login_panalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Login_panalLayout.createSequentialGroup()                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)                .addComponent(jLabel3)                .addGap(80, 80, 80))            .addGroup(Login_panalLayout.createSequentialGroup()                .addGap(54, 54, 54)                .addGroup(Login_panalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)                    .addGroup(Login_panalLayout.createSequentialGroup()                        .addGroup(Login_panalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)                            .addComponent(jLabel2)                            .addComponent(jLabel1))                        .addGap(18, 18, 18)                        .addGroup(Login_panalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)                            .addComponent(ID_Login, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)                            .addComponent(ID_Password, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)                            .addComponent(Show_pass))                        .addGap(0, 40, Short.MAX_VALUE))                    .addGroup(javax.swing.GroupLayout.Alignment.CENTER, Login_panalLayout.createSequentialGroup()                        .addComponent(Login_btn)                        .addGap(34, 34, 34))))        );        Login_panalLayout.setVerticalGroup(            Login_panalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Login_panalLayout.createSequentialGroup()                .addGap(53, 53, 53)                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)                .addGap(61, 61, 61)                .addGroup(Login_panalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)                    .addComponent(ID_Login, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)                    .addComponent(jLabel1))                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)                .addGroup(Login_panalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)                    .addComponent(ID_Password, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)                    .addComponent(jLabel2))                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)                .addComponent(Show_pass)                .addGap(18, 18, 18)                .addComponent(Login_btn)                .addGap(42, 42, 42))        );        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());        getContentPane().setLayout(layout);        layout.setHorizontalGroup(            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)            .addComponent(Login_panal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)        );        layout.setVerticalGroup(            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)            .addComponent(Login_panal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)        );        pack();    }// </editor-fold>//GEN-END:initComponents    private void jPasswordField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jPasswordField2ActionPerformed        // TODO add your handling code here:    }//GEN-LAST:event_jPasswordField2ActionPerformed    private void Show_passActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Show_passActionPerformed        // TODO add your handling code here:        if (Show_pass.isSelected()) {            ID_Password.setEchoChar((char) 0);        } else {            ID_Password.setEchoChar('*');        }    }//GEN-LAST:event_Show_passActionPerformed    private void Login_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Login_btnActionPerformed        // TODO add your handling code here:    }//GEN-LAST:event_Login_btnActionPerformed    private void ID_PasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ID_PasswordActionPerformed        // TODO add your handling code here:    }//GEN-LAST:event_ID_PasswordActionPerformed    private void ID_LoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ID_LoginActionPerformed        // TODO add your handling code here:    }//GEN-LAST:event_ID_LoginActionPerformed    private void Login_btnMouseClicked(java.awt.event.MouseEvent evt) {        // TODO add your handling code here:        Admin_ch();    }    /**     * @param args the command line arguments     */    public static void main(String args[]) {        /* Set the Nimbus look and feel */        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html         */        try {            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {                if ("Nimbus".equals(info.getName())) {                    javax.swing.UIManager.setLookAndFeel(info.getClassName());                    break;                }            }        } catch (ClassNotFoundException ex) {            java.util.logging.Logger.getLogger(UI_Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);        } catch (InstantiationException ex) {            java.util.logging.Logger.getLogger(UI_Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);        } catch (IllegalAccessException ex) {            java.util.logging.Logger.getLogger(UI_Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);        } catch (javax.swing.UnsupportedLookAndFeelException ex) {            java.util.logging.Logger.getLogger(UI_Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);        }        //</editor-fold>        /* Create and display the form */        java.awt.EventQueue.invokeLater(new Runnable() {            public void run() {                new UI_Login().setVisible(true);            }        });    }    public void Admin_ch() {        try {            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/matcha_cafe", "root", "root");            if (ID_Login.getText().isEmpty()) {                JOptionPane.showMessageDialog(null, "Do Not Let UserName Empty", "Error", JOptionPane.ERROR_MESSAGE);                return;            }            if (ID_Password.getText().isEmpty()) {                JOptionPane.showMessageDialog(null, "Do Not Let Password Empty", "Error", JOptionPane.ERROR_MESSAGE);                return;            }            String sql = "SELECT * FROM m_matcha WHERE ID = ? AND Password = ? ";            PreparedStatement stmt = con.prepareStatement(sql);            stmt.setString(1, ID_Login.getText());            stmt.setString(2, ID_Password.getText());            ResultSet resultSet = stmt.executeQuery();            if (resultSet.next()) {                String position = resultSet.getString("Position"); // get                String Emp_Name = resultSet.getString("First Name");                if (position.equals("Employer")) {                    UI_Cash Cahser = new UI_Cash(Emp_Name);                    Cahser.show();                    Cahser.setTitle("Matcah Cafe - Casher");                    Cahser.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);                    return;                }                dispose();                UI_Admin Admin_choose = new UI_Admin();                Admin_choose.show();                Admin_choose.setTitle("Matcha Cafe - Admin");                Admin_choose.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);            } else {                JOptionPane.showMessageDialog(null, "Error in UserName or Password", "Error", JOptionPane.ERROR_MESSAGE);            }        } catch (SQLException e) {            throw new RuntimeException(e);        }    }    // Variables declaration - do not modify//GEN-BEGIN:variables    private javax.swing.JTextField ID_Login;    private javax.swing.JPasswordField ID_Password;    private javax.swing.JButton Login_btn;    public javax.swing.JPanel Login_panal;    private javax.swing.JCheckBox Show_pass;    private javax.swing.JDesktopPane jDesktopPane1;    private javax.swing.JLabel jLabel1;    private javax.swing.JLabel jLabel2;    private javax.swing.JLabel jLabel3;    // End of variables declaration//GEN-END:variables}