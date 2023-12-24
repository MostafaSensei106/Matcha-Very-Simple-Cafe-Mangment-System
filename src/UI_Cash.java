/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */


import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Stack;
import java.util.Vector;


public class UI_Cash extends javax.swing.JFrame {
    /**
     * @author Yasmeen
     */
    public static class Action {
        DefaultTableModel model;
        String receipt;
        double subtotal;

        public Action(DefaultTableModel model, String receipt, double subtotal) {
            this.model = model;
            this.receipt = receipt;
            this.subtotal = subtotal;
        }

        public DefaultTableModel getModel() {
            return model;
        }

        public String getReceipt() {
            return receipt;
        }

        public double getSubtotal() {
            return subtotal;
        }
    }

    Stack<Action> actions = new Stack<>();

    /**
     * Creates new form cash2
     */
    public UI_Cash(String E) {
        initComponents();
        this.setLocationRelativeTo(this);
        Pill_Box.setEditable(false);
        sup_total.setEditable(false);
        discount_cont.setEditable(false);
        grand_total.setEditable(false);
        sup_total.setText("0");
        grand_total.setText("0");
        discount_cont.setText("0");
        discount_per.setText("0");
        Emp_Name.setText(E);

        // باخد الوقت والتاريخ الحالي
        LocalDateTime now = LocalDateTime.now();


// Define the desired format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

// Format the current date and time
        String formattedNow = now.format(formatter);

// Set the text of the date label
        Date_txt.setText(formattedNow);
        Emp_Name.setText(E);
        String asciiArt =
                "*****************\n" +
                        "* Matcha Cafe *\n" +
                        "*****************\n";

        // Get the name of the employee
        String employeeName = Emp_Name.getText();

        // Concatenate the ASCII art, date, and employee name
        String receiptHeader = asciiArt + "\nDate: " + formattedNow + "\nEmployee: " + employeeName + "\n-----------------------------\n";

        // Add the receipt header to the beginning of the receipt
        Pill_Box.setText(receiptHeader + Pill_Box.getText());
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/matcha_cafe", "root", "root");
            String sql = "SELECT * FROM m_items";
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet resultSet = stmt.executeQuery();
            DefaultTableModel model = new DefaultTableModel(new String[]{"Item ID", "Category", "Item Name", "Price", "Amount"}, 0) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    // This causes all cells to be not editable
                    return false;
                }
            };
            // Iterate over the ResultSet and add each row to the model
            while (resultSet.next()) {
                int itemId = resultSet.getInt("items_id");
                String category = resultSet.getString("items_category");
                String itemName = resultSet.getString("items_name");
                double price = resultSet.getDouble("items_prices");
                int amount = resultSet.getInt("items_amount");
                model.addRow(new Object[]{itemId, category, itemName, price, amount});
            }
            // Set the model to the JTable
            Casher_TP.setModel(model);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        discount_per.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateDiscountAndTotal();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateDiscountAndTotal();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateDiscountAndTotal();
            }

            private void updateDiscountAndTotal() {
                double subtotal = Double.parseDouble(sup_total.getText());
                double discountPercentage = 0;
                if (!discount_per.getText().isEmpty()) {
                    //تغير الاسترينج
                    discountPercentage = Double.parseDouble(discount_per.getText()) / 100;
                }
                double discountAmount = subtotal * discountPercentage;
                discount_cont.setText(String.valueOf(discountAmount));
                double grandTotal = subtotal - discountAmount;
                grand_total.setText(String.valueOf(grandTotal));
            }
        });
        Search_I.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateTable();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateTable();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateTable();
            }

            private void updateTable() {
                String searchText = Search_I.getText();
                try {
                    Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/matcha_cafe", "root", "root");
                    String sql = "SELECT * FROM m_items WHERE items_name LIKE ?";
                    PreparedStatement stmt = con.prepareStatement(sql);
                    stmt.setString(1, "%" + searchText + "%"); // Use % for wildcard search
                    ResultSet resultSet = stmt.executeQuery();

                    DefaultTableModel model = new DefaultTableModel(new String[]{"Item ID", "Category", "Item Name", "Price", "Amount", "Discount"}, 0) {
                        @Override
                        public boolean isCellEditable(int row, int column) {
                            // This causes all cells to be not editable
                            return false;
                        }
                    };

                    // Iterate over the ResultSet and add each row to the model
                    while (resultSet.next()) {
                        int itemId = resultSet.getInt("items_id");
                        String category = resultSet.getString("items_category");
                        String itemName = resultSet.getString("items_name");
                        double price = resultSet.getDouble("items_prices");
                        int amount = resultSet.getInt("items_amount");
                        double discount = resultSet.getDouble("items_discount");
                        model.addRow(new Object[]{itemId, category, itemName, price, amount, discount});
                    }

                    // Set the model to the JTable
                    Casher_TP.setModel(model);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        });
    }

    // Assuming Search_I is your JTextField for search input

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jComboBox1 = new javax.swing.JComboBox<>();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        Casher_TP = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        sup_total = new javax.swing.JTextField();
        discount_cont = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        grand_total = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        discount_per = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        undoButton = new javax.swing.JButton();
        Add_Btn = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        Date_txt = new javax.swing.JLabel();
        Emp_Name = new javax.swing.JLabel();
        Cat_i = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        Qant = new javax.swing.JSpinner();
        Search_I = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        Pill_Box = new javax.swing.JTextPane();

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"Item 1", "Item 2", "Item 3", "Item 4"}));

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Cash");

        Casher_TP.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        Casher_TP.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{
                        {null, null, null, null, null, null},
                        {null, null, null, null, null, null},
                        {null, null, null, null, null, null},
                        {null, null, null, null, null, null},
                        {null, null, null, null, null, null},
                        {null, null, null, null, null, null},
                        {null, null, null, null, null, null}
                },
                new String[]{
                        "Item ID", "Item Name", "Category", "Price", "Quantity", "Date"
                }
        ));
        jScrollPane1.setViewportView(Casher_TP);

        sup_total.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sup_totalActionPerformed(evt);
            }
        });

        discount_cont.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                discount_contActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel2.setText("SubTotal");

        jLabel4.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel4.setText("Discount%");

        jLabel6.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel6.setText("Discount");

        grand_total.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                grand_totalActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel8.setText("Grand Total");

        discount_per.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                discount_perActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addContainerGap(26, Short.MAX_VALUE)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(grand_total, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(discount_cont, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(sup_total, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(discount_per, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(19, 19, 19))
        );
        jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(23, 23, 23)
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(sup_total, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(discount_per, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(discount_cont, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(grand_total, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(15, 15, 15))
        );

        jLabel3.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel3.setText("Employer Name:");

        undoButton.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        undoButton.setText("Un Do");
        undoButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                undoButtonMouseClicked(evt);
            }
        });
        undoButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                undoButtonActionPerformed(evt);
            }
        });

        Add_Btn.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        Add_Btn.setText(" Add");
        Add_Btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Add_BtnMouseClicked(evt);
            }
        });
        Add_Btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Add_BtnActionPerformed(evt);
            }
        });

        jButton3.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jButton3.setText(" Print");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jButton4.setText("Back");
        jButton4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton4MouseClicked(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel1.setText("Date:");

        jLabel5.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel5.setText(" Category: ");

        Date_txt.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        Date_txt.setText("date&time");

        Emp_Name.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        Emp_Name.setText(" Jasmin");

        Cat_i.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        Cat_i.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"Tea", "Coffee", "Dessert"}));
        Cat_i.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Cat_iActionPerformed(evt);
            }
        });
        Cat_i.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                Cat_iPropertyChange(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel10.setText("Search Item:");

        jLabel12.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        jLabel12.setText(" Quantity:");

        Search_I.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        Search_I.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Search_IActionPerformed(evt);
            }
        });

        jScrollPane2.setViewportView(Pill_Box);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
                jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 225, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
                jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 286, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                                .addComponent(jLabel3)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(Emp_Name, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                                .addComponent(jLabel10)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(Search_I, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                                .addGap(56, 56, 56)
                                                                .addComponent(jLabel1)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(Date_txt))
                                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(jLabel5)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(Cat_i, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(Qant, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(undoButton, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(Add_Btn)))
                                .addGap(23, 23, 23)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(6, 6, 6)
                                                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap(8, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap(18, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(11, 11, 11)
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(jLabel1)
                                                        .addComponent(Date_txt)
                                                        .addComponent(jLabel3)
                                                        .addComponent(Emp_Name))
                                                .addGap(40, 40, 40)
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(jLabel5)
                                                        .addComponent(Cat_i, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(jLabel10)
                                                        .addComponent(Search_I, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(jLabel12)
                                                        .addComponent(Qant, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(18, 18, 18)
                                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 425, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(26, 26, 26)
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(Add_Btn)
                                                        .addComponent(undoButton)
                                                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void recalculateTotals() {
        if (!actions.isEmpty()) {
            Action lastAction = actions.peek();
            double lastSubtotal = lastAction.getSubtotal();
            double discountPercentage = Double.parseDouble(discount_per.getText()) / 100;
            double discountAmount = lastSubtotal * discountPercentage;
            discount_cont.setText(String.valueOf(discountAmount));
            double grandTotal = lastSubtotal - discountAmount;
            grand_total.setText(String.valueOf(grandTotal));
        }
    }
    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed

    private void Add_BtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Add_BtnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Add_BtnActionPerformed

    private void sup_totalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sup_totalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_sup_totalActionPerformed

    private void grand_totalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_grand_totalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_grand_totalActionPerformed

    private void discount_perActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_discount_perActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_discount_perActionPerformed

    private void discount_contActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_discount_contActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_discount_contActionPerformed

    private void Add_BtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Add_BtnMouseClicked
        // TODO add your handling code here:
        double subtotalBeforeAdding = Double.parseDouble(sup_total.getText());
        DefaultTableModel originalModel = (DefaultTableModel) Casher_TP.getModel();
        Vector data = new Vector();
        for (int i = 0; i < originalModel.getRowCount(); i++) {
            data.add((Vector) originalModel.getDataVector().get(i).clone());
        }
        Vector columnIdentifiers = new Vector();
        for (int i = 0; i < originalModel.getColumnCount(); i++) {
            columnIdentifiers.add(originalModel.getColumnName(i));
        }
        DefaultTableModel modelBeforeAdding = new DefaultTableModel(data, columnIdentifiers);
        Action actionBeforeAdding = new Action(modelBeforeAdding, Pill_Box.getText(), subtotalBeforeAdding);
        actions.push(actionBeforeAdding);
        int selectedRowIndex = Casher_TP.getSelectedRow();
        int Qant_5 = (int) Qant.getValue();
        // Check if a row is actually selected
        if (selectedRowIndex != -1 && (int) Qant_5 > 0) {
            // Get values of the selected row
            Object itemId = Casher_TP.getValueAt(selectedRowIndex, 0);
            Object category = Casher_TP.getValueAt(selectedRowIndex, 1);
            Object itemName = Casher_TP.getValueAt(selectedRowIndex, 2);
            Object price = Casher_TP.getValueAt(selectedRowIndex, 3);
            int amount = (int) Casher_TP.getValueAt(selectedRowIndex, 4);
            double subtotal = Double.parseDouble(sup_total.getText());
            subtotal += (double) price * Qant_5;
            sup_total.setText(String.valueOf(subtotal));
            if (Qant_5 <= amount) {
                amount -= Qant_5;
                Casher_TP.setValueAt(amount, selectedRowIndex, 4);
                String receipt = "Item ID: " + itemId + "\n" + "Item Name: " + itemName + "\n" + "Category: " + category + "\n" + "Price: " + price + "\n" + "Quantity: " + Qant_5 + "\n" + "\n" + "-----------------------------\n";
                Pill_Box.setText(Pill_Box.getText() + receipt);
            } else {
                JOptionPane.showMessageDialog(null, "The quantity is greater than the amount of items available.", "Error", JOptionPane.ERROR_MESSAGE);
            }
            double discountPercentage = Double.parseDouble(discount_per.getText()) / 100;
            double discountAmount = subtotal * discountPercentage;
            discount_cont.setText(String.valueOf(discountAmount));
            double grandTotal = subtotal - discountAmount;
            grand_total.setText(String.valueOf(grandTotal));
        }
    }//GEN-LAST:event_Add_BtnMouseClicked

    private void jButton4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton4MouseClicked
        // TODO add your handling code here:
        UI_Login Login_Back = new UI_Login();
        Login_Back.show();
        dispose();
        Login_Back.setTitle("Matcha Cafe - Admin");
        Login_Back.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }//GEN-LAST:event_jButton4MouseClicked

    private void Cat_iActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Cat_iActionPerformed
        // TODO add your handling code here:
        String category = (String) Cat_i.getSelectedItem();
        String searchText = (String) Search_I.getText();

        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/matcha_cafe", "root", "root");
            String sql;
            PreparedStatement stmt;
            if (searchText.isEmpty()) {
                sql = "SELECT * FROM m_items WHERE items_category = ?";
                stmt = con.prepareStatement(sql);
                stmt.setString(1, category);
            } else {
                sql = "SELECT * FROM m_items WHERE items_category = ? AND items_name LIKE ?";
                stmt = con.prepareStatement(sql);
                stmt.setString(1, category);
                stmt.setString(2, "%" + searchText + "%");
            }

            ResultSet resultSet = stmt.executeQuery();
            DefaultTableModel model = new DefaultTableModel(new String[]{"Item ID", "Category", "Item Name", "Price", "Amount", "Discount"}, 0) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    // This causes all cells to be not editable
                    return false;
                }
            };

            // Iterate over the ResultSet and add each row to the model
            while (resultSet.next()) {
                int itemId = resultSet.getInt("items_id");
                String categories = resultSet.getString("items_category");
                String itemName = resultSet.getString("items_name");
                double price = resultSet.getDouble("items_prices");
                int amount = resultSet.getInt("items_amount");
                double discount = resultSet.getDouble("items_discount");
                model.addRow(new Object[]{itemId, categories, itemName, price, amount, discount});
            }

            // Set the model to the JTable
            Casher_TP.setModel(model);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_Cat_iActionPerformed

    private void Search_IActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Search_IActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Search_IActionPerformed

    private void undoButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_undoButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_undoButtonActionPerformed

    private void Cat_iPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_Cat_iPropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_Cat_iPropertyChange

    private void undoButtonMouseClicked(java.awt.event.MouseEvent evt) {
        if (!actions.isEmpty()) {
            Action lastAction = actions.pop();
            Casher_TP.setModel(lastAction.getModel());
            Pill_Box.setText(lastAction.getReceipt());
            sup_total.setText(String.valueOf(lastAction.getSubtotal()));
            recalculateTotals();
        }
    }//GEN-LAST:event_undoButtonMouseClicked

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
            java.util.logging.Logger.getLogger(UI_Cash.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UI_Cash.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UI_Cash.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UI_Cash.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
//                new UI_Cash().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Add_Btn;
    private javax.swing.JTable Casher_TP;
    private javax.swing.JComboBox<String> Cat_i;
    private javax.swing.JLabel Date_txt;
    private javax.swing.JLabel Emp_Name;
    private javax.swing.JTextPane Pill_Box;
    private javax.swing.JSpinner Qant;
    private javax.swing.JTextField Search_I;
    private javax.swing.JTextField discount_cont;
    private javax.swing.JTextField discount_per;
    private javax.swing.JTextField grand_total;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField sup_total;
    private javax.swing.JButton undoButton;
    // End of variables declaration//GEN-END:variables
}
