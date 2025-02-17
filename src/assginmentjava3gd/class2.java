/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package assginmentjava3gd;

import Model.Class2;
import java.awt.BorderLayout;
import java.util.List; // phai sai util
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.BoxLayout;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;
import assginmentjava3gd.student2;
import DAO.ClassDAO2;
import DAO.ListDAO;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.util.Arrays;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author ACER
 */
public class class2 extends javax.swing.JInternalFrame {

    private final List<Class2> lop = new ArrayList<>();
    private DefaultTableModel table;
    private final student2 st;
    private final ListDAO ldo;
    private TableRowSorter<TableModel> sorter;

    public class2() {
        initComponents();
        this.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        BasicInternalFrameUI ui = (BasicInternalFrameUI) this.getUI();
        ui.setNorthPane(null);
        st = new student2();
        ldo = new ListDAO();
        setupComboBox();
        // Thiết lập TableRowSorter và JComboBox
        setupTableSorter((DefaultTableModel) btnTablelop.getModel(), btnTablelop);
        fillTable();
        chinhjtable();
        chinhbutton();

//    public void fillTable() {
//        DefaultTableModel model = (DefaultTableModel) btnTablelop.getModel();
//        model.setRowCount(0);      
//          st.getCboLop().removeAllItems();
//        for (Class2 dp : lop) {
//            Object[] row = new Object[]{dp.malop, dp.tenlop, dp.mota};
//            model.addRow(row);         
//            st.getCboLop().addItem(dp.tenlop);
//        }
//    }
    }

    public void chinhjtable() {
        // Tùy chỉnh giao diện JTable
        btnTablelop.setFont(new Font("Segoe UI", Font.PLAIN, 16)); // chỉnh chữ
        btnTablelop.setRowHeight(30);// chỉnh độ cao của bảng
        btnTablelop.setGridColor(new Color(230, 230, 230));
        btnTablelop.setBackground(new Color(245, 245, 245));
        btnTablelop.setForeground(new Color(50, 50, 50));
        btnTablelop.setSelectionBackground(new Color(0, 120, 215));
        btnTablelop.setSelectionForeground(Color.WHITE);

        // Tùy chỉnh header
        DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component comp = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                // Giữ màu nền và màu chữ của header
                comp.setBackground(new Color(0, 153, 204)); // Màu nền của header
                comp.setForeground(Color.WHITE); // Màu chữ
                setFont(new Font("Segoe UI", Font.BOLD, 18)); // Phông chữ
                setHorizontalAlignment(JLabel.CENTER); // Căn giữa

                return comp;
            }
        };

        // Áp dụng renderer cho từng cột
        for (int i = 0; i < btnTablelop.getColumnCount(); i++) {
            btnTablelop.getColumnModel().getColumn(i).setHeaderRenderer(headerRenderer);
        }

        // Căn giữa nội dung các ô
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < btnTablelop.getColumnCount(); i++) {
            btnTablelop.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

    }

    public void chinhbutton() {
        // chỉnh màu và font chữ của btnthem
        btnthem.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnthem.setBackground(new Color(0, 153, 204)); // Màu nền của button
        btnthem.setForeground(Color.black); // Màu chữ
        btnthem.setPreferredSize(new Dimension(120, 40));
        btnthem.setBorder(BorderFactory.createLineBorder(new Color(0, 120, 215), 2));
        btnthem.setFocusPainted(false);
        btnthem.setOpaque(true);
        btnthem.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnthem.setBackground(new Color(0, 120, 215)); // Đổi màu nền khi chuột di chuyển qua
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnthem.setBackground(new Color(0, 153, 204)); // Trở lại màu nền ban đầu khi chuột ra khỏi button
            }
        });
        // chỉnh màu và font chữ của btnxoa
        btnxoa.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnxoa.setBackground(new Color(0, 153, 204));
        btnxoa.setForeground(Color.black);
        btnxoa.setPreferredSize(new Dimension(120, 40));
        btnxoa.setBorder(BorderFactory.createLineBorder(new Color(0, 120, 215), 2));
        btnxoa.setFocusPainted(false);
        btnxoa.setOpaque(true);
        btnxoa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnxoa.setBackground(new Color(0, 120, 215));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnxoa.setBackground(new Color(0, 153, 204));
            }
        });
        // chỉnh màu và font chữ của btncapnhat
        btncapnhat.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btncapnhat.setBackground(new Color(0, 153, 204));
        btncapnhat.setForeground(Color.black);
        btncapnhat.setPreferredSize(new Dimension(120, 40));
        btncapnhat.setBorder(BorderFactory.createLineBorder(new Color(0, 120, 215), 2));
        btncapnhat.setFocusPainted(false);
        btncapnhat.setOpaque(true);
        btncapnhat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btncapnhat.setBackground(new Color(0, 120, 215));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                btncapnhat.setBackground(new Color(0, 153, 204));
            }
        });
        // chỉnh màu và font chữ của btnreset
        btnreset.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnreset.setBackground(new Color(0, 153, 204));
        btnreset.setForeground(Color.black);
        btnreset.setPreferredSize(new Dimension(120, 40));
        btnreset.setBorder(BorderFactory.createLineBorder(new Color(0, 120, 215), 2));
        btnreset.setFocusPainted(false);
        btnreset.setOpaque(true);
        btnreset.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnreset.setBackground(new Color(0, 120, 215));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnreset.setBackground(new Color(0, 153, 204));
            }
        });

    }

    public void chinhlabel() {

    }

    public void addClass() {
        if (txtMa.getText().equals("") && txtTen.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Chưa nhập tên và mã lớp", "Error", JOptionPane.WARNING_MESSAGE);
            JOptionPane.showMessageDialog(this, "Thêm thất bại", "Error", JOptionPane.WARNING_MESSAGE);

            return;
        }
        if (txtTen.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Chưa nhập tên", "Error", JOptionPane.WARNING_MESSAGE);
            JOptionPane.showMessageDialog(this, "Thêm thất bại", "Error", JOptionPane.WARNING_MESSAGE);

            return;
        }
        if (txtMa.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Chưa nhập mã lớp", "Error", JOptionPane.WARNING_MESSAGE);
            JOptionPane.showMessageDialog(this, "Thêm thất bại", "Error", JOptionPane.WARNING_MESSAGE);

            return;
        }
        Class2 dp = new Class2();
        dp.malop = txtMa.getText();
        dp.tenlop = txtTen.getText();
        dp.mota = txtMota.getText();
        lop.add(dp);
        st.getCboLop().addItem(dp.tenlop);
        st.getCboLop().revalidate();
        st.getCboLop().repaint();
        ClassDAO2.insert(dp);
        fillTable();
    }

    public void updateClass() {
        int index = btnTablelop.getSelectedRow();
        if (index == -1 || index >= lop.size()) {
            JOptionPane.showMessageDialog(this, "Chưa chọn hàng nào để cập nhật!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (txtMa.getText().equals("") && txtTen.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Chưa nhập tên và mã lớp", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (txtTen.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Chưa nhập tên", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (txtMa.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Chưa nhập mã lớp", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Class2 dp = lop.get(index);
        dp.malop = txtMa.getText();
        dp.tenlop = txtTen.getText();
        dp.mota = txtMota.getText();
        st.getCboLop().removeItemAt(index);
        st.getCboLop().insertItemAt(dp.tenlop, index);
        st.getCboLop().revalidate();
        st.getCboLop().repaint();
        ClassDAO2.update(dp);
        fillTable();
    }

    public void removeclass() {
        int index = btnTablelop.getSelectedRow(); // Lấy dòng được chọn từ bảng

        if (index != -1) { // Kiểm tra dòng hợp lệ
            int choice = JOptionPane.showConfirmDialog(this, "Bạn có muốn xóa môn học này?", "Xác nhận", JOptionPane.YES_NO_OPTION);

            if (choice == JOptionPane.YES_OPTION) {
                try {
                    // Lấy mã môn học từ bảng
                    String maLop = (String) btnTablelop.getValueAt(index, 0);

                    // Xóa môn học khỏi cơ sở dữ liệu
                    boolean isDeleted = ClassDAO2.delete(maLop); // Trả về true nếu xóa thành công, false nếu không

                    if (isDeleted) {
                        // Xóa môn học khỏi danh sách `mon` dựa vào mã môn
                        for (int i = 0; i < lop.size(); i++) {
                            if (lop.get(i).getMalop().equals(maLop)) {
                                lop.remove(i);
                                break;
                            }
                        }

                        fillTable();
                        JOptionPane.showMessageDialog(this, "Xóa thành công!");

                        if (btnTablelop.getRowCount() > 0) { // kiểm tra còn dữ liệu trong bảng ko
                            int newindex = Math.min(index, btnTablelop.getRowCount() - 1); // lấy dòng gần nhất
                            btnTablelop.setRowSelectionInterval(newindex, newindex); // CHọn dòng mới
                            loadRowindexfield(newindex); // đưa dữ liệu dòng lên các field
                        } else {
                            clean();
                        }

                    } else {
                        JOptionPane.showMessageDialog(this, "Không thể xóa môn học do ràng buộc dữ liệu (foreign key).", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "Lỗi khi xóa môn học: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Chưa chọn hàng nào để xóa hoặc dữ liệu không hợp lệ!", "Thông báo", JOptionPane.WARNING_MESSAGE);
        }
    }

    public void clean() {
        txtTen.setText("");
        txtMa.setText("");
        txtMota.setText("");
    }

    public void loadRowindexfield(int newrowintdex) {
        String maLop = (String) btnTablelop.getValueAt(newrowintdex, 0);
        String tenLop = (String) btnTablelop.getValueAt(newrowintdex, 1);
        String mota = (String) btnTablelop.getValueAt(newrowintdex, 2);

        txtMa.setText(maLop);
        txtTen.setText(tenLop);
        txtMota.setText(mota);

    }

    // đây là code để đẩy cái dữ liệu ở database lên table 
    public void fillTable() {
        // Lấy dữ liệu từ cơ sở dữ liệu
        List<Class2> classes = ClassDAO2.getAll();
        lop.clear(); // Xóa danh sách cũ
        lop.addAll(classes); // Cập nhật danh sách mới

        // Cập nhật JTable
        DefaultTableModel model = (DefaultTableModel) btnTablelop.getModel();
        model.setRowCount(0); // Xóa dữ liệu cũ trong bảng

        for (Class2 dp : lop) {
            Object[] row = new Object[]{dp.malop, dp.tenlop, dp.mota};
            model.addRow(row); // Thêm dữ liệu vào bảng
        }

//        // Cập nhật combobox (nếu cần)
//        st.getCboLop().removeAllItems();
//        for (Class2 dp : lop) {
//            st.getCboLop().addItem(dp.tenlop);
//        }
    }

    // cái này là để click vào table để nó hiện lên mấy cái textfield á nha 
    public void clickHere() {
        int row = btnTablelop.getSelectedRow();  // Lấy chỉ số dòng được chọn

        // Kiểm tra xem có dòng nào được chọn không
        if (row != -1) {
            // Lấy dữ liệu từ bảng và điền vào các trường nhập liệu
            String maLop = btnTablelop.getValueAt(row, 0).toString();  // Lấy mã lớp từ cột 1
            String tenLop = btnTablelop.getValueAt(row, 1).toString();  // Lấy tên lớp từ cột 2
            String moTa = btnTablelop.getValueAt(row, 2).toString();    // Lấy mô tả từ cột 3

            // Cập nhật các trường nhập liệu
            txtMa.setText(maLop);
            txtTen.setText(tenLop);
            txtMota.setText(moTa);
        }
    }
    // code của search nha 
    public void showData(List<Class2> class2) {
        DefaultTableModel model = (DefaultTableModel) btnTablelop.getModel();
        model.setRowCount(0); // Xóa dữ liệu cũ
        for (Class2 cla : class2) {
            model.addRow(new Object[]{
                cla.getMalop(),
                cla.getTenlop(),
                cla.getMota()
            });
        }
    }
    public void search() {
        String keyword = txtsearch.getText().trim(); // Lấy từ khóa và xóa khoảng trắng thừa
        if (keyword.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập từ khóa tìm kiếm!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        ClassDAO2 dao = new ClassDAO2(); // Tạo đối tượng DAO
        List<Class2> class2 = ldo.searchClass(keyword); 

        if (class2.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy lop nào!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        } else {
            showData(class2);
        }
    }
    // SET DỮ LIỆU CHO COMBOBOX 
    public void setupComboBox() {
        // Tạo DefaultComboBoxModel với dữ liệu
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(new String[]{"A-Z", "Z-A"});
        cbbsapxep.setModel(model);
    }

    // Thiết lập TableRowSorter cho JTable
    public void setupTableSorter(DefaultTableModel model, JTable table) {
        sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        btnTablelop = new javax.swing.JTable();
        btnthem = new javax.swing.JButton();
        btnxoa = new javax.swing.JButton();
        btncapnhat = new javax.swing.JButton();
        btnreset = new javax.swing.JButton();
        txtTen = new javax.swing.JTextField();
        txtMa = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtMota = new javax.swing.JTextArea();
        txtsearch = new javax.swing.JTextField();
        btnsearch = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        cbbsapxep = new javax.swing.JComboBox<>();

        setBackground(new java.awt.Color(255, 255, 255));

        btnTablelop.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Class ID", "Class Name", "Status"
            }
        ));
        btnTablelop.setSelectionBackground(new java.awt.Color(245, 245, 245));
        btnTablelop.getTableHeader().setReorderingAllowed(false);
        btnTablelop.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnTablelopMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(btnTablelop);

        btnthem.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnthem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/299068_add_sign_icon.png"))); // NOI18N
        btnthem.setText("Add");
        btnthem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnthemActionPerformed(evt);
            }
        });

        btnxoa.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnxoa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/9004852_trash_delete_bin_remove_icon.png"))); // NOI18N
        btnxoa.setText("Delete");
        btnxoa.setPreferredSize(new java.awt.Dimension(98, 37));
        btnxoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnxoaActionPerformed(evt);
            }
        });

        btncapnhat.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btncapnhat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/8726496_upload_icon.png"))); // NOI18N
        btncapnhat.setText("Update");
        btncapnhat.setPreferredSize(new java.awt.Dimension(98, 37));
        btncapnhat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncapnhatActionPerformed(evt);
            }
        });

        btnreset.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnreset.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/9856287_reset_reload_sync_update_icon.png"))); // NOI18N
        btnreset.setText("Reset");
        btnreset.setPreferredSize(new java.awt.Dimension(98, 37));
        btnreset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnresetActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel1.setText("Class ID:");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel2.setText("Class Name");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel3.setText("Status");

        txtMota.setColumns(20);
        txtMota.setRows(5);
        jScrollPane2.setViewportView(txtMota);

        btnsearch.setText("Search");
        btnsearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnsearchActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel4.setText("Arrange");

        cbbsapxep.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cbbsapxep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbsapxepActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(117, 117, 117)
                        .addComponent(btnthem, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(53, 53, 53)
                        .addComponent(btnxoa, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(51, 51, 51)
                        .addComponent(btncapnhat, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(40, 40, 40)
                        .addComponent(btnreset, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(126, 126, 126)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtTen, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtMa, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(32, 32, 32)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnsearch)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtsearch, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbbsapxep, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1065, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(47, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(56, 56, 56)
                        .addComponent(jLabel2))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel3)
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtMa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel1))
                            .addGap(24, 24, 24)
                            .addComponent(txtTen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtsearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnsearch))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel4)
                                    .addComponent(cbbsapxep, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 425, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnthem)
                    .addComponent(btnxoa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btncapnhat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnreset, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnthemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnthemActionPerformed
        addClass();
    }//GEN-LAST:event_btnthemActionPerformed

    private void btncapnhatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncapnhatActionPerformed
        updateClass();
    }//GEN-LAST:event_btncapnhatActionPerformed

    private void btnxoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnxoaActionPerformed
        removeclass();
    }//GEN-LAST:event_btnxoaActionPerformed

    private void btnTablelopMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnTablelopMouseClicked
        clickHere();
    }//GEN-LAST:event_btnTablelopMouseClicked

    private void btnresetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnresetActionPerformed
        clean();
    }//GEN-LAST:event_btnresetActionPerformed

    private void btnsearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnsearchActionPerformed
       search();
    }//GEN-LAST:event_btnsearchActionPerformed

    private void cbbsapxepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbsapxepActionPerformed
        String selected = (String) cbbsapxep.getSelectedItem(); // Lấy lựa chọn từ ComboBox
        if (selected == null) {
            return;
        }
        // Sắp xếp theo cột đầu tiên (0)
        switch (selected) {
            case "A-Z":
                sorter.setSortKeys(Arrays.asList(new RowSorter.SortKey(0, SortOrder.ASCENDING)));
                break;
            case "Z-A":
                sorter.setSortKeys(Arrays.asList(new RowSorter.SortKey(0, SortOrder.DESCENDING)));
                break;
            default:
                break;
        }

    }//GEN-LAST:event_cbbsapxepActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable btnTablelop;
    private javax.swing.JButton btncapnhat;
    private javax.swing.JButton btnreset;
    private javax.swing.JButton btnsearch;
    private javax.swing.JButton btnthem;
    private javax.swing.JButton btnxoa;
    private javax.swing.JComboBox<String> cbbsapxep;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField txtMa;
    private javax.swing.JTextArea txtMota;
    private javax.swing.JTextField txtTen;
    private javax.swing.JTextField txtsearch;
    // End of variables declaration//GEN-END:variables
}
