/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package assginmentjava3gd;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.plaf.basic.BasicInternalFrameUI;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public class Rank2 extends javax.swing.JInternalFrame {

    DefaultTableModel model = new DefaultTableModel();
    private TableRowSorter<TableModel> sorter;

    public Rank2() {
        initComponents();
        this.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        BasicInternalFrameUI ui = (BasicInternalFrameUI) this.getUI();
        ui.setNorthPane(null);
        loadMajorID();
        chinhjtable();
        setupComboBox();
        // Thiết lập TableRowSorter và JComboBox
        setupTableSorter((DefaultTableModel) tblRank.getModel(), tblRank);

    }

    public void chinhjtable() {
        // Tùy chỉnh giao diện JTable
        tblRank.setFont(new Font("Segoe UI", Font.PLAIN, 16)); // chỉnh chữ
        tblRank.setRowHeight(30);// chỉnh độ cao của bảng
        tblRank.setGridColor(new Color(230, 230, 230));
        tblRank.setBackground(new Color(245, 245, 245));
        tblRank.setForeground(new Color(50, 50, 50));
        tblRank.setSelectionBackground(new Color(0, 120, 215));
        tblRank.setSelectionForeground(Color.WHITE);

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
        for (int i = 0; i < tblRank.getColumnCount(); i++) {
            tblRank.getColumnModel().getColumn(i).setHeaderRenderer(headerRenderer);
        }

        // Căn giữa nội dung các ô
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < tblRank.getColumnCount(); i++) {
            tblRank.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

    }

    private Connection connect() throws Exception {
        String url = "jdbc:mysql://localhost:3306/qlsv"; // Thay 'ten_database' bằng tên database
        String user = "root"; // Thay username
        String password = "tranhainam123"; // Thay password
        return DriverManager.getConnection(url, user, password);
    }

    private void loadMajorID() {
        String query = getSelectSubjectCodeQuery(); // Gọi câu lệnh SELECT từ phương thức khác
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(query); ResultSet rs = pstmt.executeQuery()) {

            cboMaNganh.removeAllItems(); // Xóa tất cả các mục hiện có trong ComboBox
            while (rs.next()) {
                cboMaNganh.addItem(rs.getString(1)); // Thêm tên lớp vào ComboBox
            }
        } catch (Exception e) {
            e.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(this, "Lỗi khi tải danh sách lớp.");
        }
    }

    // Phương thức để trả về câu lệnh SELECT
    private String getSelectSubjectCodeQuery() {
        return "SELECT maNganh FROM NganhHoc"; // Sửa câu lệnh này tùy thuộc vào cơ sở dữ liệu của bạn
    }

    private void loadDiemForNganhHoc() throws Exception {
        // Lấy mã ngành đã chọn trong ComboBox
        String selectedMaNganh = (String) cboMaNganh.getSelectedItem();

        // Kết nối cơ sở dữ liệu và lấy tất cả các môn học của ngành học đã chọn
        String sqlMonHoc = "SELECT m.maMon, m.tenMon FROM MonHocNganhHoc mn "
                + "JOIN MonHoc m ON mn.maMon = m.maMon WHERE mn.maNganh = ?";

        try (Connection conn = connect(); PreparedStatement psMonHoc = conn.prepareStatement(sqlMonHoc)) {

            psMonHoc.setString(1, selectedMaNganh);  // Đặt mã ngành vào câu truy vấn

            try (ResultSet rsMonHoc = psMonHoc.executeQuery()) {
                List<String> listMaMon = new ArrayList<>();
                while (rsMonHoc.next()) {
                    listMaMon.add(rsMonHoc.getString("maMon"));
                }

                // Lấy tất cả sinh viên thuộc mã ngành đã chọn
                String sqlSinhVien = "SELECT maSV, tenSV, maNganh FROM SinhVien WHERE maNganh = ?";
                try (PreparedStatement psSinhVien = conn.prepareStatement(sqlSinhVien)) {
                    psSinhVien.setString(1, selectedMaNganh);

                    try (ResultSet rsSinhVien = psSinhVien.executeQuery()) {
                        List<Object[]> dataList = new ArrayList<>();

                        while (rsSinhVien.next()) {
                            String maSV = rsSinhVien.getString("maSV");
                            String tenSV = rsSinhVien.getString("tenSV");
                            String maNganh = rsSinhVien.getString("maNganh");

                            // Tính tổng điểm của sinh viên cho tất cả các môn học trong ngành học này
                            double totalScore = 0;
                            int countValidScores = 0;
                            boolean hasMissingScores = false;

                            // Lấy điểm cho từng môn của sinh viên
                            for (String maMon : listMaMon) {
                                try (PreparedStatement psDiem = conn.prepareStatement(
                                        "SELECT diemTrungBinh FROM Diem WHERE maSV = ? AND maMon = ?")) {
                                    psDiem.setString(1, maSV);
                                    psDiem.setString(2, maMon);

                                    try (ResultSet rsDiem = psDiem.executeQuery()) {
                                        if (rsDiem.next()) {
                                            double diemTrungBinh = rsDiem.getDouble("diemTrungBinh");
                                            if (diemTrungBinh == 0) {  // Nếu điểm chưa được nhập (hoặc là giá trị 0)
                                                hasMissingScores = true;
                                            } else {
                                                totalScore += diemTrungBinh;
                                                countValidScores++;
                                            }
                                        }
                                    }
                                }
                            }

                            // Số môn học thực tế trong ngành
                            int totalSubjectsInMajor = listMaMon.size();

                            // Nếu có môn học đã nhập điểm, tính điểm trung bình
                            double averageScore = countValidScores > 0 ? totalScore / totalSubjectsInMajor : 0;

                            // Làm tròn điểm trung bình
                            averageScore = Math.round(averageScore * 10) / 10.0;  // Làm tròn đến 1 chữ số thập phân

                            // Xếp loại (classification) dựa trên điểm trung bình
                            String classification = averageScore > 9 ? "Xuất sắc"
                                    : averageScore > 8 ? "Giỏi"
                                            : averageScore >= 6.5 ? "Khá"
                                                    : averageScore >= 5 ? "Trung Bình" : "Yếu";

                            // Trạng thái (status) dựa trên việc nhập điểm đầy đủ
                            String status = (hasMissingScores || averageScore < 5) ? "Không đạt" : "Tốt nghiệp";

                            // Thêm sinh viên và tổng điểm vào danh sách
                            dataList.add(new Object[]{dataList.size() + 1, maSV, tenSV, maNganh, averageScore, classification, status});
                        }

                        // Hiển thị dữ liệu trong bảng
                        DefaultTableModel model = (DefaultTableModel) tblRank.getModel();
                        model.setRowCount(0);  // Xóa dữ liệu cũ trong bảng

                        // Sắp xếp theo điểm trung bình giảm dần
                        dataList.sort((o1, o2) -> Double.compare((double) o2[4], (double) o1[4]));

                        // Thêm dữ liệu vào bảng
                        for (Object[] row : dataList) {
                            model.addRow(row);
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // SET DỮ LIỆU CHO COMBOBOX 
    public void setupComboBox() {
        // Tạo DefaultComboBoxModel với dữ liệu
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(new String[]{"A-Z", "Z-A"});
        cbbosapxep.setModel(model);
    }

    // Thiết lập TableRowSorter cho JTable
    public void setupTableSorter(DefaultTableModel model, JTable table) {
        sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        cboMaNganh = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblRank = new javax.swing.JTable();
        cbbosapxep = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));

        cboMaNganh.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        cboMaNganh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboMaNganhActionPerformed(evt);
            }
        });

        tblRank.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Rank", "Student ID", "Student Name", "Subject ID", "Avg Point ", "Classification", "Status"
            }
        ));
        jScrollPane1.setViewportView(tblRank);

        cbbosapxep.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        cbbosapxep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbosapxepActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel1.setText("Arrange");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel2.setText("Rank");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1093, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(395, 395, 395)
                        .addComponent(cboMaNganh, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(70, 70, 70)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cbbosapxep, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(503, 503, 503)
                        .addComponent(jLabel2)))
                .addContainerGap(73, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(cboMaNganh, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 508, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1)
                        .addComponent(cbbosapxep, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cboMaNganhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboMaNganhActionPerformed
        try {
            loadDiemForNganhHoc();
        } catch (Exception ex) {
            Logger.getLogger(Rank2.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_cboMaNganhActionPerformed

    private void cbbosapxepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbosapxepActionPerformed
        String selected = (String) cbbosapxep.getSelectedItem(); // Lấy lựa chọn từ ComboBox
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
    }//GEN-LAST:event_cbbosapxepActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> cbbosapxep;
    private javax.swing.JComboBox<String> cboMaNganh;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblRank;
    // End of variables declaration//GEN-END:variables
}
