/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package assginmentjava3gd;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.table.DefaultTableCellRenderer;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.table.DefaultTableModel;
import Model.MajorandSubject;
import DAO.MajorandSubjectDAO2;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author ACER
 */
public class SubjectandMajor2 extends javax.swing.JInternalFrame {

    private final List<MajorandSubject> mjas = new ArrayList<>();
    private DefaultTableModel table;

    /**
     * Creates new form SubjectandMajor2
     */
    public SubjectandMajor2() {
        initComponents();
        this.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        BasicInternalFrameUI ui = (BasicInternalFrameUI) this.getUI();
        ui.setNorthPane(null);
        chinhjtable();
        chinhbutton();
        loadMajorID();
        loadSubjectID();
        fillTable();
    }

    public void chinhjtable() {
        // Tùy chỉnh giao diện JTable
        tblsbandma.setFont(new Font("Segoe UI", Font.PLAIN, 16)); // chỉnh chữ
        tblsbandma.setRowHeight(30);// chỉnh độ cao của bảng
        tblsbandma.setGridColor(new Color(230, 230, 230));
        tblsbandma.setBackground(new Color(245, 245, 245));
        tblsbandma.setForeground(new Color(50, 50, 50));
        tblsbandma.setSelectionBackground(new Color(0, 120, 215));
        tblsbandma.setSelectionForeground(Color.WHITE);

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
        for (int i = 0; i < tblsbandma.getColumnCount(); i++) {
            tblsbandma.getColumnModel().getColumn(i).setHeaderRenderer(headerRenderer);
        }

        // Căn giữa nội dung các ô
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < tblsbandma.getColumnCount(); i++) {
            tblsbandma.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
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

    private Connection connect() throws Exception {
        String url = "jdbc:mysql://localhost:3306/qlsv"; // Thay 'ten_database' bằng tên database
        String user = "root"; // Thay username
        String password = "tranhainam123"; // Thay password
        return DriverManager.getConnection(url, user, password);
    }

    private void loadSubjectID() {
        String query = getSelectSubjectCodeQuery(); // Gọi câu lệnh SELECT từ phương thức khác
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(query); ResultSet rs = pstmt.executeQuery()) {

            cbbSubjectId.removeAllItems(); // Xóa tất cả các mục hiện có trong ComboBox
            while (rs.next()) {
                cbbSubjectId.addItem(rs.getString(1)); // Thêm tên lớp vào ComboBox
            }
        } catch (Exception e) {
            e.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(this, "Lỗi khi tải danh sách lớp.");
        }
    }

    private String getSelectSubjectCodeQuery() {
        return "SELECT maMon FROM MonHoc"; // Sửa câu lệnh này tùy thuộc vào cơ sở dữ liệu của bạn
    }

    private void loadMajorID() {
        String query = getSelectMajorCodeQuery(); // Gọi câu lệnh SELECT từ phương thức khác
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(query); ResultSet rs = pstmt.executeQuery()) {

            cbbMajorId.removeAllItems(); // Xóa tất cả các mục hiện có trong ComboBox
            while (rs.next()) {
                cbbMajorId.addItem(rs.getString(1)); // Thêm tên lớp vào ComboBox
            }
        } catch (Exception e) {
            e.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(this, "Lỗi khi tải danh sách lớp.");
        }
    }

    private String getSelectMajorCodeQuery() {
        return "SELECT maNganh FROM NganhHoc"; // Sửa câu lệnh này tùy thuộc vào cơ sở dữ liệu của bạn
    }

    public void fillTable() {
        // Lấy dữ liệu từ cơ sở dữ liệu
        List<MajorandSubject> classes = MajorandSubjectDAO2.getAllClasses();
        mjas.clear(); // Xóa danh sách cũ
        mjas.addAll(classes); // Cập nhật danh sách mới

        // Cập nhật JTable
        DefaultTableModel model = (DefaultTableModel) tblsbandma.getModel();
        model.setRowCount(0); // Xóa dữ liệu cũ trong bảng

        for (MajorandSubject dp : mjas) {
            Object[] row = new Object[]{dp.MaMonHocNganhHoc, dp.getMamon(), dp.getMajorId()};
            model.addRow(row); // Thêm dữ liệu vào bảng
        }
    }

    public void addmajorSubject() {
        if (cbbMajorId.getSelectedIndex() == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn mã ngành học!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (cbbSubjectId.getSelectedIndex() == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn môn học!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        MajorandSubject mj = new MajorandSubject();
        mj.majorId = (String) cbbMajorId.getSelectedItem();
        mj.mamon = (String) cbbSubjectId.getSelectedItem();
        MajorandSubjectDAO2.insertDe(mj);
        fillTable();
        JOptionPane.showMessageDialog(this, "Thêm thành công");

    }

    public void removeMajor() {
        // Lấy danh sách các dòng được chọn từ bảng
        int[] selectedRows = tblsbandma.getSelectedRows();

        if (selectedRows.length > 0) { // Kiểm tra xem có dòng nào được chọn hay không
            int choice = JOptionPane.showConfirmDialog(this,
                    "Bạn có chắc chắn muốn xóa các môn học đã chọn?",
                    "Xác nhận", JOptionPane.YES_NO_OPTION);

            if (choice == JOptionPane.YES_OPTION) {
                try {
                    boolean hasError = false;

                    // Duyệt qua các dòng được chọn
                    for (int i = selectedRows.length - 1; i >= 0; i--) {
                        int index = selectedRows[i]; // Lấy chỉ số dòng được chọn
                        String maMonnganh = String.valueOf(tblsbandma.getValueAt(index, 0)); // Lấy mã môn học

                        // Xóa môn học khỏi cơ sở dữ liệu
                        boolean isDeleted = MajorandSubjectDAO2.deleteMajorandSubject(maMonnganh);

                        if (isDeleted) {
                            // Xóa khỏi danh sách `mjas`
                            for (int j = 0; j < mjas.size(); j++) {
                                if (mjas.get(j).getMaMonHocNganhHoc() == Integer.parseInt(maMonnganh)) {
                                    mjas.remove(j);
                                    break;
                                }
                            }
                        } else {
                            hasError = true; // Đánh dấu nếu có lỗi xảy ra khi xóa
                        }
                    }

                    // Cập nhật lại bảng
                    fillTable();

                    if (hasError) {
                        JOptionPane.showMessageDialog(this,
                                "Một số môn học không thể xóa do ràng buộc dữ liệu (foreign key).",
                                "Thông báo", JOptionPane.WARNING_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(this, "Xóa thành công!");
                    }

                    // Kiểm tra nếu bảng còn dữ liệu
                    if (tblsbandma.getRowCount() > 0) {
                        tblsbandma.setRowSelectionInterval(0, 0); // Chọn dòng đầu tiên
                        loadRowindexfield(0); // Đưa dữ liệu dòng lên các field
                    } else {
                        clean(); // Xóa trống các field
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this,
                            "Lỗi khi xóa môn học: " + e.getMessage(),
                            "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this,
                    "Chưa chọn hàng nào để xóa!",
                    "Thông báo", JOptionPane.WARNING_MESSAGE);
        }
    }

    public void loadRowindexfield(int newrowintdex) {
        String maMon = (String) tblsbandma.getValueAt(newrowintdex, 1);
        String maNganh = (String) tblsbandma.getValueAt(newrowintdex, 2);
        cbbSubjectId.setSelectedItem(maMon);
        cbbMajorId.setSelectedItem(maNganh);
    }

    public void clean() {
        cbbSubjectId.setSelectedIndex(-1); // Xóa lựa chọn trong JComboBox
        cbbMajorId.setSelectedIndex(-1);  // Xóa lựa chọn trong JComboBox
    }

    public void updateMajor() {
        // Lấy dòng được chọn trong bảng
        int index = tblsbandma.getSelectedRow();
        if (index == -1 || index >= mjas.size()) {
            JOptionPane.showMessageDialog(this, "Chưa chọn hàng nào để cập nhật!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (cbbMajorId.getSelectedIndex() == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn mã sinh viên!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (cbbSubjectId.getSelectedIndex() == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn môn học!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Cập nhật thông tin ngành học trong danh sách
        MajorandSubject major = mjas.get(index);
        if (major != null) {  // Kiểm tra xem đối tượng có tồn tại không
            // Lấy giá trị từ JComboBox (getSelectedItem sẽ trả về đối tượng đã chọn)
            String maMon = (String) cbbSubjectId.getSelectedItem();
            String majorId = (String) cbbMajorId.getSelectedItem();

            // Cập nhật maMon và majorId vào đối tượng MajorandSubject
            major.setMamon(maMon);
            major.setMajorId(majorId);
        } else {
            System.out.println("Không tìm thấy đối tượng với index " + index);
        }

        // Cập nhật ngành học trong cơ sở dữ liệu
        MajorandSubjectDAO2.updateMajor(major);
        fillTable();

        JOptionPane.showMessageDialog(this, "Cập nhật ngành học thành công!");
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tblsbandma = new javax.swing.JTable();
        cbbMajorId = new javax.swing.JComboBox<>();
        cbbSubjectId = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        btnthem = new javax.swing.JButton();
        btnxoa = new javax.swing.JButton();
        btncapnhat = new javax.swing.JButton();
        btnreset = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));

        tblsbandma.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "ID S&M", "ID Subject", "ID Major"
            }
        ));
        tblsbandma.setFocusable(false);
        tblsbandma.setRowHeight(25);
        tblsbandma.setSelectionBackground(new java.awt.Color(245, 245, 245));
        tblsbandma.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(tblsbandma);

        cbbMajorId.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cbbMajorId.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbbMajorId.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbMajorIdActionPerformed(evt);
            }
        });

        cbbSubjectId.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cbbSubjectId.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel1.setText("Major ID");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel2.setText("Subject ID");

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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(278, 278, 278)
                                .addComponent(jLabel1)
                                .addGap(29, 29, 29)
                                .addComponent(cbbMajorId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(79, 79, 79)
                                .addComponent(jLabel2)
                                .addGap(18, 18, 18)
                                .addComponent(cbbSubjectId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(64, 64, 64)
                                .addComponent(btnthem, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(42, 42, 42)
                                .addComponent(btnxoa, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btncapnhat, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(34, 34, 34)
                                .addComponent(btnreset, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 151, Short.MAX_VALUE))
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbbMajorId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbbSubjectId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 476, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnthem)
                    .addComponent(btnxoa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btncapnhat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnreset, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cbbMajorIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbMajorIdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbbMajorIdActionPerformed

    private void btnthemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnthemActionPerformed
        addmajorSubject();
    }//GEN-LAST:event_btnthemActionPerformed

    private void btnxoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnxoaActionPerformed
        removeMajor();
    }//GEN-LAST:event_btnxoaActionPerformed

    private void btncapnhatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncapnhatActionPerformed
        updateMajor();
    }//GEN-LAST:event_btncapnhatActionPerformed

    private void btnresetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnresetActionPerformed
        clean();
    }//GEN-LAST:event_btnresetActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btncapnhat;
    private javax.swing.JButton btnreset;
    private javax.swing.JButton btnthem;
    private javax.swing.JButton btnxoa;
    private javax.swing.JComboBox<String> cbbMajorId;
    private javax.swing.JComboBox<String> cbbSubjectId;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblsbandma;
    // End of variables declaration//GEN-END:variables
}
