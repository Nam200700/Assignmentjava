/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package assginmentjava3gd;

import DAO.ListDAO;
import DAO.PointDAO2;
import Model.Point2;
import Util.jdbcHelper;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public class Rank2 extends javax.swing.JInternalFrame {

    DefaultTableModel model = new DefaultTableModel();
    private final ListDAO ldo;
    private TableRowSorter<TableModel> sorter;

    public Rank2() {
        initComponents();
        this.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        BasicInternalFrameUI ui = (BasicInternalFrameUI) this.getUI();
        ui.setNorthPane(null);
        ldo = new ListDAO();
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

    private void loadMajorID() {
        String sql = "SELECT maNganh FROM nganhhoc"; // Thay đổi theo bảng của bạn

        try (ResultSet rs = jdbcHelper.executeQuery(sql)) {
            cboMaNganh.removeAllItems(); // Xóa tất cả các mục hiện có trong ComboBox

            while (rs != null && rs.next()) {
                cboMaNganh.addItem(rs.getString("maNganh")); // Thêm mã ngành vào ComboBox
            }
        } catch (Exception e) {
            e.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(this, "Lỗi khi tải danh sách ngành.");
        }
    }

    // Phương thức để trả về câu lệnh SELECT
    private String getSelectSubjectCodeQuery() {
        return "SELECT maNganh FROM NganhHoc"; // Sửa câu lệnh này tùy thuộc vào cơ sở dữ liệu của bạn
    }

    // Lưu trữ danh sách dữ liệu gốc để tránh việc thay đổi thứ hạng khi tìm kiếm
    private List<Object[]> originalDataList = new ArrayList<>();

// Hàm này dùng để tải điểm cho ngành học và hiển thị kết quả vào bảng
    private void loadDiemForNganhHoc() {
        String selectedMaNganh = (String) cboMaNganh.getSelectedItem();
        if (selectedMaNganh == null) {
            return; // Tránh lỗi nếu chưa chọn ngành học
        }
        // SQL lấy danh sách sinh viên theo ngành
        String sqlSinhVien = "SELECT maSV, tenSV, maNganh FROM SinhVien WHERE maNganh = ?";

        // SQL lấy điểm trung bình của sinh viên theo ngành học
        String sqlDiemTrungBinh = "SELECT d.maSV, AVG(d.diemTrungBinh) AS avgScore "
                + "FROM Diem d JOIN MonHocNganhHoc mn ON d.maMon = mn.maMon "
                + "WHERE mn.maNganh = ? GROUP BY d.maSV";

        try (
                ResultSet rsDiem = jdbcHelper.executeQuery(sqlDiemTrungBinh, selectedMaNganh); ResultSet rsSinhVien = jdbcHelper.executeQuery(sqlSinhVien, selectedMaNganh)) {
            // Lưu điểm trung bình của từng sinh viên vào Map
            Map<String, Double> diemTBMap = new HashMap<>();
            while (rsDiem.next()) {
                diemTBMap.put(rsDiem.getString("maSV"), rsDiem.getDouble("avgScore"));
            }

            // Xóa dữ liệu cũ trước khi thêm mới
            originalDataList.clear();
            int index = 1;

            while (rsSinhVien.next()) {
                String maSV = rsSinhVien.getString("maSV");
                String tenSV = rsSinhVien.getString("tenSV");
                String maNganh = rsSinhVien.getString("maNganh");

                // Lấy điểm trung bình từ Map (nếu có)
                double averageScore = diemTBMap.getOrDefault(maSV, 0.0);
                averageScore = Math.round(averageScore * 10) / 10.0; // Làm tròn 1 số thập phân

                // Xếp loại sinh viên
                String classification = averageScore > 9 ? "Xuất sắc"
                        : averageScore > 8 ? "Giỏi"
                                : averageScore >= 6.5 ? "Khá"
                                        : averageScore >= 5 ? "Trung Bình" : "Yếu";

                // Kiểm tra trạng thái tốt nghiệp
                String status = (averageScore == 0 || averageScore < 5) ? "Không đạt" : "Tốt nghiệp";

                // Thêm vào danh sách
                originalDataList.add(new Object[]{index++, maSV, tenSV, maNganh, averageScore, classification, status});
            }

            // Hiển thị dữ liệu lên bảng
            refreshTable(originalDataList);

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi tải điểm sinh viên.");
        }
    }

// Hàm dùng để cập nhật bảng với danh sách dữ liệu
    private void refreshTable(List<Object[]> dataList) {
        DefaultTableModel model = (DefaultTableModel) tblRank.getModel();
        model.setRowCount(0); // Xóa dữ liệu cũ trong bảng

        // Thêm từng dòng dữ liệu vào bảng
        for (Object[] row : dataList) {
            model.addRow(row);
        }
    }

// Hàm tìm kiếm sinh viên
    private void searchSinhVien() {
        String keyword = txtsearch.getText().trim().toLowerCase(); // Lấy từ khóa tìm kiếm từ JTextField và chuyển thành chữ thường

        // Lọc danh sách dữ liệu ban đầu (originalDataList) theo từ khóa tìm kiếm
        List<Object[]> filteredList = originalDataList.stream()
                .filter(row -> row[1].toString().toLowerCase().contains(keyword) // Tìm theo mã sinh viên
                || row[2].toString().toLowerCase().contains(keyword)) // Tìm theo tên sinh viên
                .toList();

        // Hiển thị kết quả tìm kiếm vào bảng mà không thay đổi thứ hạng
        refreshTable(filteredList);
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
        txtsearch = new javax.swing.JTextField();
        btnsearch = new javax.swing.JButton();

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

        btnsearch.setText("Search");
        btnsearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnsearchActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(503, 503, 503)
                        .addComponent(jLabel2))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addGap(27, 27, 27)
                            .addComponent(cboMaNganh, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(190, 190, 190)
                            .addComponent(txtsearch, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(btnsearch)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel1)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(cbbosapxep, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1093, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(75, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1)
                        .addComponent(cbbosapxep, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cboMaNganh, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtsearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnsearch)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 508, javax.swing.GroupLayout.PREFERRED_SIZE)
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

    private void btnsearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnsearchActionPerformed
        searchSinhVien();
    }//GEN-LAST:event_btnsearchActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnsearch;
    private javax.swing.JComboBox<String> cbbosapxep;
    private javax.swing.JComboBox<String> cboMaNganh;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblRank;
    private javax.swing.JTextField txtsearch;
    // End of variables declaration//GEN-END:variables
}
