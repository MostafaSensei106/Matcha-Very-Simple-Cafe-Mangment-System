/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel; ///  هام جداا
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Stack;
import java.util.Vector;


public class UI_Cash extends javax.swing.JFrame {
    /**
     * @author  Mostafa Sensei106
     */


    //كلاس يعمل حفظ للجدول و الفاتورة و المجموع السابق sub totoal
    public static class Action {
        //جدول
        DefaultTableModel model;
        String receipt;
        double subtotal;

        public Action(DefaultTableModel model, String receipt, double subtotal) { // set fun
            this.model = model;
            this.receipt = receipt;
            this.subtotal = subtotal;
        }

        //get fun
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

    //ستاك عام في الكلاس كله
    Stack<Action> actions = new Stack<>();//   Stack
    Runnable updateTimeRunnable = new Runnable() {
        @Override
        public void run() {
            while (true) {
                LocalDateTime now = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss a");
                String formattedNow = now.format(formatter);

                // Update the time label
                Date_txt.setText(formattedNow);

                // Sleep for one second
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    /**
     *
     */

    //كونستراكتور
    public UI_Cash(String E) {
        initComponents();
        this.setLocationRelativeTo(this);
        //قيم افتراضية
        Bill_Box.setEditable(false); // قفل التعديل في الفاتورة

        sup_total.setEditable(false); // قفل التعيدل في المجموع

        discount_cont.setEditable(false);// قفل التعديل علي عداد الخصم

        grand_total.setEditable(false);/// قفل التعدل علي عداد  المجمع الكامل

        // قمي افتراضية
        sup_total.setText("0");// قيمة افتارضية للمجموع

        grand_total.setText("0");

        discount_cont.setText("0");

        discount_per.setText("0");

        Emp_Name.setText(E);

        //الفاتورة الافضتراضية
        String asciiArt =
                        "*****************\n" +
                        "* Matcha Cafe *\n" +
                        "*****************";

        // Get the name of the employee
        String employeeName = Emp_Name.getText();
        new Thread(updateTimeRunnable).start();
        // Concatenate the ASCII art, date, and employee name
        String receiptHeader = asciiArt + "\nEmployee: " + employeeName + "\n-----------------------------\n";

        // Add the receipt header to the beginning of the receipt
        Bill_Box.setText(receiptHeader + Bill_Box.getText());

        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/matcha_cafe", "root", "root");
            String sql = "SELECT * FROM m_items";
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet resultSet = stmt.executeQuery();
            DefaultTableModel model = new DefaultTableModel(new String[]{"ID", "Category", "Name", "Price", "Amount"}, 0) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };


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

        // لو اتغيرت نسبة الخصم تعديل مباشر
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
        discountPercentage = Double.parseDouble(discount_per.getText()) / 100;
    }

    if(discountPercentage < 0 || discountPercentage > 1){
        JOptionPane.showMessageDialog(null , "Discount percentage should be between 0 and 100");
        return;
    }

    double discountAmount = subtotal * discountPercentage;

    discount_cont.setText(String.valueOf(discountAmount));

    double grandTotal = subtotal - discountAmount;

    grand_total.setText(String.valueOf(grandTotal));
}
        });

        // كود البحث
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

                    DefaultTableModel model = new DefaultTableModel(new String[]{"ID", "Category", "Name", "Price", "Amount"}, 0) {

                        @Override
                        public boolean isCellEditable(int row, int column) { // غير قابلة للتعديل
                            // This causes all cells to be not editable
                            return false;
                        }
                    };

                    while (resultSet.next()) {
                        int itemId = resultSet.getInt("items_id");
                        String category = resultSet.getString("items_category");
                        String itemName = resultSet.getString("items_name");
                        double price = resultSet.getDouble("items_prices");
                        int amount = resultSet.getInt("items_amount");
                        model.addRow(new Object[]{itemId, category, itemName, price, amount});
                    }

                    Casher_TP.setModel(model);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        });
    }


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jComboBox1 = new javax.swing.JComboBox<>();
        Landing_Casher = new javax.swing.JPanel();
        jDesktopPane1 = new javax.swing.JDesktopPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        Casher_TP = new javax.swing.JTable();
        Head_panal = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        Date_txt = new javax.swing.JLabel();
        Emp_Name = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        Qant = new javax.swing.JSpinner();
        Cat_i = new javax.swing.JComboBox<>();
        Search_I = new javax.swing.JTextField();
        Sup_Total_Panal = new javax.swing.JPanel();
        sup_total = new javax.swing.JTextField();
        discount_cont = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        grand_total = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        discount_per = new javax.swing.JTextField();
        undoButton = new javax.swing.JButton();
        Add_Btn = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        Bill_Box = new javax.swing.JTextPane();
        Print_Btn = new javax.swing.JButton();

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Cash");

        Casher_TP.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        Casher_TP.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Item ID", "Item Name", "Category", "Price", "Quantity", "Date"
            }
        ));
        jScrollPane1.setViewportView(Casher_TP);

        jLabel3.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel3.setText("Employer Name:");

        jLabel1.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel1.setText("Date:");

        jLabel5.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel5.setText(" Category: ");

        Date_txt.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        Date_txt.setText("date&time");

        Emp_Name.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        Emp_Name.setText(" Jasmin");

        jLabel10.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel10.setText("Search Item:");

        jLabel12.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        jLabel12.setText(" Quantity:");

        Cat_i.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        Cat_i.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tea", "Coffee", "Dessert" }));
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

        Search_I.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        Search_I.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Search_IActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout Head_panalLayout = new javax.swing.GroupLayout(Head_panal);
        Head_panal.setLayout(Head_panalLayout);
        Head_panalLayout.setHorizontalGroup(
            Head_panalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Head_panalLayout.createSequentialGroup()
                .addGap(101, 101, 101)
                .addComponent(Search_I, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(Head_panalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(Head_panalLayout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(Head_panalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(Head_panalLayout.createSequentialGroup()
                            .addComponent(jLabel3)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(Emp_Name, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jLabel10))
                    .addGroup(Head_panalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(Head_panalLayout.createSequentialGroup()
                            .addGap(56, 56, 56)
                            .addComponent(jLabel1)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(Date_txt))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Head_panalLayout.createSequentialGroup()
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jLabel5)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(Cat_i, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(Qant, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addContainerGap(169, Short.MAX_VALUE)))
        );
        Head_panalLayout.setVerticalGroup(
            Head_panalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Head_panalLayout.createSequentialGroup()
                .addGap(64, 64, 64)
                .addComponent(Search_I, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(7, Short.MAX_VALUE))
            .addGroup(Head_panalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(Head_panalLayout.createSequentialGroup()
                    .addGap(8, 8, 8)
                    .addGroup(Head_panalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1)
                        .addComponent(Date_txt)
                        .addComponent(jLabel3)
                        .addComponent(Emp_Name))
                    .addGap(40, 40, 40)
                    .addGroup(Head_panalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel5)
                        .addComponent(Cat_i, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel10)
                        .addComponent(jLabel12)
                        .addComponent(Qant, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

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

        javax.swing.GroupLayout Sup_Total_PanalLayout = new javax.swing.GroupLayout(Sup_Total_Panal);
        Sup_Total_Panal.setLayout(Sup_Total_PanalLayout);
        Sup_Total_PanalLayout.setHorizontalGroup(
            Sup_Total_PanalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Sup_Total_PanalLayout.createSequentialGroup()
                .addContainerGap(38, Short.MAX_VALUE)
                .addGroup(Sup_Total_PanalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
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
        Sup_Total_PanalLayout.setVerticalGroup(
            Sup_Total_PanalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Sup_Total_PanalLayout.createSequentialGroup()
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

        jButton4.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jButton4.setText("Back");
        jButton4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton4MouseClicked(evt);
            }
        });
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jScrollPane2.setViewportView(Bill_Box);

        Print_Btn.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        Print_Btn.setText(" Print");
        Print_Btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Print_BtnMouseClicked(evt);
            }
        });
        Print_Btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Print_BtnActionPerformed(evt);
            }
        });

        jDesktopPane1.setLayer(jScrollPane1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane1.setLayer(Head_panal, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane1.setLayer(Sup_Total_Panal, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane1.setLayer(undoButton, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane1.setLayer(Add_Btn, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane1.setLayer(jButton4, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane1.setLayer(jScrollPane2, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane1.setLayer(Print_Btn, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jDesktopPane1Layout = new javax.swing.GroupLayout(jDesktopPane1);
        jDesktopPane1.setLayout(jDesktopPane1Layout);
        jDesktopPane1Layout.setHorizontalGroup(
            jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jDesktopPane1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jDesktopPane1Layout.createSequentialGroup()
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(undoButton, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(Add_Btn)
                        .addGap(18, 18, 18)
                        .addComponent(Print_Btn, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jDesktopPane1Layout.createSequentialGroup()
                        .addGroup(jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 736, Short.MAX_VALUE)
                            .addComponent(Head_panal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(Sup_Total_Panal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane2))))
                .addContainerGap(28, Short.MAX_VALUE))
        );
        jDesktopPane1Layout.setVerticalGroup(
            jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDesktopPane1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jDesktopPane1Layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(Sup_Total_Panal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jDesktopPane1Layout.createSequentialGroup()
                        .addComponent(Head_panal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(undoButton)
                    .addComponent(Add_Btn)
                    .addComponent(Print_Btn, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(480, 480, 480))
        );

        javax.swing.GroupLayout Landing_CasherLayout = new javax.swing.GroupLayout(Landing_Casher);
        Landing_Casher.setLayout(Landing_CasherLayout);
        Landing_CasherLayout.setHorizontalGroup(
            Landing_CasherLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jDesktopPane1)
        );
        Landing_CasherLayout.setVerticalGroup(
            Landing_CasherLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Landing_CasherLayout.createSequentialGroup()
                .addComponent(jDesktopPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Landing_Casher, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Landing_Casher, javax.swing.GroupLayout.PREFERRED_SIZE, 559, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    private void recalculateTotals() {

        if (!actions.isEmpty()) {
            Action lastAction = actions.peek();// اخر شيء عملية اضافة

            double lastSubtotal = lastAction.getSubtotal();// حساب  المجموع

            double discountPercentage = Double.parseDouble(discount_per.getText()) / 100;

            double discountAmount = lastSubtotal * discountPercentage;

            discount_cont.setText(String.valueOf(discountAmount));// برجع القيمة الي نص  مره اخري

            double grandTotal = lastSubtotal - discountAmount;

            grand_total.setText(String.valueOf(grandTotal));//برجع القيمة الي نص  مره اخري
        }
    }
    private void Print_BtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Print_BtnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Print_BtnActionPerformed

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

    // كود اضافة عملية جدية
    private void Add_BtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Add_BtnMouseClicked
        // TODO add your handling code here:

        double subtotalBeforeAdding = Double.parseDouble(sup_total.getText()); // حفظ المجموع قبل الاضفاة للعودة له مره اخري

        DefaultTableModel originalModel = (DefaultTableModel) Casher_TP.getModel(); // حفظ الجدول

        Vector data = new Vector();// ما هي الفيكتورز
        //وسيلة لحفظ البيانات جاجه شبه المصفوفة
        // المصفوفة محدوده الحجم او متغير بحجم معين ال فيكوتر نفس وظيفة المصفوفه و مع كل عنصر حجمة يزيد
        // فرضا عملنا فيكنتور 10 لو تم اضافة عنصر يتحول الحجم الي 11 وهكذا

        // بخزن الجدول كله صف في الفيكتور
        for (int i = 0; i < originalModel.getRowCount(); i++) {  // بحزن جيمع الصفوف في الجدول
            data.add((Vector) originalModel.getDataVector().get(i).clone());
        }

        Vector columnIdentifiers = new Vector(); // بخزن جميع الاعمدة في الجدول
        for (int i = 0; i < originalModel.getColumnCount(); i++) {
            columnIdentifiers.add(originalModel.getColumnName(i));
        }

        DefaultTableModel modelBeforeAdding = new DefaultTableModel(data, columnIdentifiers);// بعمل بيها جدول جديد قبل الاضافة

        Action actionBeforeAdding = new Action(modelBeforeAdding, Bill_Box.getText(), subtotalBeforeAdding); // عملنا ابوجيت من كلاس الاستاك وبعمل سيف لل جدول و الفاتورة و المجموع

        actions.push(actionBeforeAdding);// جطينا لابوجيكت جوا الاستاك

        int selectedRowIndex = Casher_TP.getSelectedRow(); // بيضيف الصف المختار الي الفاتورة

        int Qant_5 = (int) Qant.getValue();// بيحسب الكمية المراد شراء ها

        // لو مش مختار اي حاجة يختار الصف رقم -1
        if (selectedRowIndex != -1 && (int) Qant_5 > 0) {

            // بسحب البيانات من الصفوف المختارة
            Object itemId = Casher_TP.getValueAt(selectedRowIndex, 0);
            Object category = Casher_TP.getValueAt(selectedRowIndex, 1);
            Object itemName = Casher_TP.getValueAt(selectedRowIndex, 2);
            Object price = Casher_TP.getValueAt(selectedRowIndex, 3);
            int amount = (int) Casher_TP.getValueAt(selectedRowIndex, 4);

            if (Qant_5 <= amount) {
                amount -= Qant_5; // هسحب من المخزون
                Casher_TP.setValueAt(amount, selectedRowIndex, 4);
                String receipt = "ID: " + itemId + "\n" + "Name: " + itemName + "\n" + "Category: " + category + "\n" + "Price: " + price + "\n" + "Quantity: " + Qant_5 + "\n" + "\n" + "-----------------------------\n";
                Bill_Box.setText(Bill_Box.getText() + receipt);

                try {
                    Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/matcha_cafe", "root", "root");
                    String sql = "UPDATE m_items SET items_amount = ? WHERE items_id = ?";
                    PreparedStatement stmt = con.prepareStatement(sql);
                    stmt.setInt(1, amount);
                    stmt.setObject(2, itemId);
                    stmt.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            else {
                JOptionPane.showMessageDialog(null, "The quantity is greater than the amount of items available.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            double subtotal = Double.parseDouble(sup_total.getText());

            subtotal += (double) price * Qant_5;

            sup_total.setText(String.valueOf(subtotal));

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

        String searchText = (String) Search_I.getText(); // هي في كلام جو خانة البحث

        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/matcha_cafe", "root", "root");

            String sql;

            PreparedStatement stmt;

            if (searchText.isEmpty()) {

                sql = "SELECT * FROM m_items WHERE items_category = ?";

                stmt = con.prepareStatement(sql);

                stmt.setString(1, category);

            }
            else {

                sql = "SELECT * FROM m_items WHERE items_category = ? AND items_name LIKE ?";

                stmt = con.prepareStatement(sql);

                stmt.setString(1, category);

                stmt.setString(2, "%" + searchText + "%");

            }

            ResultSet resultSet = stmt.executeQuery();
            DefaultTableModel model = new DefaultTableModel(new String[]{"Item ID", "Category", "Item Name", "Price", "Amount"}, 0) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
            while (resultSet.next()) {
                int itemId = resultSet.getInt("items_id");
                String categories = resultSet.getString("items_category");
                String itemName = resultSet.getString("items_name");
                double price = resultSet.getDouble("items_prices");
                int amount = resultSet.getInt("items_amount");
                model.addRow(new Object[]{itemId, categories, itemName, price, amount});
            }
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

    private void Print_BtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Print_BtnMouseClicked
        // TODO add your handling code here:
        // Get the current date and time
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedNow = now.format(formatter);
        String Print_Bill = Bill_Box.getText();

//القيمة الافتراضية للفاتورة
        String asciiArt =
                        "*****************\n" +
                        "* Matcha Cafe *\n" +
                        "*****************";
        //باخد اسم الموظف
        String employeeName = Emp_Name.getText();

        // Concatenate the ASCII art, date, and employee name
        String receiptHeader = asciiArt + "\nEmployee: " + employeeName + "\n-----------------------------\n";

        // Check if the Pill_Box text is the same as the initial receipt text
        if (Print_Bill.equals(receiptHeader)) {
            // Show a message to the user that no item was added
            JOptionPane.showMessageDialog(null, "No item was added.");
        } else {
            JOptionPane.showMessageDialog(null, Print_Bill+ "\nPrinted on: " + formattedNow);
        }    }//GEN-LAST:event_Print_BtnMouseClicked

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton4ActionPerformed

    private void undoButtonMouseClicked(java.awt.event.MouseEvent evt) {
        // لو الاستاك بتاعي مليان
        if (!actions.isEmpty()) {

            Action lastAction = actions.pop(); // برجع اخر  تعيدل جواه

            Casher_TP.setModel(lastAction.getModel());// برجع الجدول الي [حفظته]

            Bill_Box.setText(lastAction.getReceipt()); // برجع الرسيت

            sup_total.setText(String.valueOf(lastAction.getSubtotal())); // برجع السبتوتال

            recalculateTotals();// بترسيت الجدول من اول وجديد
            // Update the database

            DefaultTableModel model = lastAction.getModel();
            for (int i = 0; i < model.getRowCount(); i++) {
                int itemId = (int) model.getValueAt(i, 0);
                String category = (String) model.getValueAt(i, 1);
                String itemName = (String) model.getValueAt(i, 2);
                double price = (double) model.getValueAt(i, 3);
                int amount = (int) model.getValueAt(i, 4);

                try {
                    Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/matcha_cafe", "root", "root");
                    String sql = "UPDATE m_items SET items_category = ?, items_name = ?, items_prices = ?, items_amount = ? WHERE items_id = ?";
                    PreparedStatement stmt = con.prepareStatement(sql);
                    stmt.setString(1, category);
                    stmt.setString(2, itemName);
                    stmt.setDouble(3, price);
                    stmt.setInt(4, amount);
                    stmt.setInt(5, itemId);
                    stmt.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }else {
            JOptionPane.showMessageDialog(null,"No Add OP Have Been Selected");
        }
    }                                       

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
          //     new UI_Cash().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Add_Btn;
    private javax.swing.JTable Casher_TP;
    private javax.swing.JComboBox<String> Cat_i;
    private javax.swing.JLabel Date_txt;
    private javax.swing.JLabel Emp_Name;
    private javax.swing.JPanel Head_panal;
    private javax.swing.JPanel Landing_Casher;
    private javax.swing.JTextPane Bill_Box;
    private javax.swing.JButton Print_Btn;
    private javax.swing.JSpinner Qant;
    private javax.swing.JTextField Search_I;
    private javax.swing.JPanel Sup_Total_Panal;
    private javax.swing.JTextField discount_cont;
    private javax.swing.JTextField discount_per;
    private javax.swing.JTextField grand_total;
    private javax.swing.JButton jButton4;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JDesktopPane jDesktopPane1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField sup_total;
    private javax.swing.JButton undoButton;
    // End of variables declaration//GEN-END:variables
}
