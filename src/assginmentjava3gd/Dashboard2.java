/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package assginmentjava3gd;

import DAO.BarchartDAO2;
import DAO.PiechartDAO2;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.PreparedStatement;

public class Dashboard2 extends javax.swing.JInternalFrame {

    /**
     * Creates new form Dashboard2
     */
    public Dashboard2() {
        initComponents();
        this.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        BasicInternalFrameUI ui = (BasicInternalFrameUI) this.getUI();
        ui.setNorthPane(null);
        PiechartDAO2.showPiechart(piechart);
        BarchartDAO2.showbarchartWithData(barchart, 0, 0, 0, 0, 0); // Hiển thị biểu đồ trống ban đầu
        loadMajorID();

    }

    // Phương thức kết nối cơ sở dữ liệu
    private Connection connect() throws Exception {
        String url = "jdbc:mysql://localhost:3306/qlsv";
        String user = "root";
        String password = "tranhainam123";
        return DriverManager.getConnection(url, user, password);
    }
    // Load danh sách ngành vào ComboBox

    private void loadMajorID() {
        String query = "SELECT maNganh FROM NganhHoc";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(query); ResultSet rs = pstmt.executeQuery()) {

            cboboxNganh.removeAllItems();
            while (rs.next()) {
                cboboxNganh.addItem(rs.getString(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(this, "Lỗi khi tải danh sách ngành.");
        }
    }
    // Cập nhật biểu đồ cột theo ngành được chọn

    private void updateBarChart(String selectedMajor) {
        // Câu truy vấn SQL để tính toán phần trăm sinh viên theo mức điểm
        String query = """
        SELECT 
            COUNT(CASE WHEN diemTrungBinh < 5 THEN 1 END) * 100 / COUNT(*) AS under_5_percent,
            COUNT(CASE WHEN diemTrungBinh >= 5 AND diemTrungBinh < 6.5 THEN 1 END) * 100 / COUNT(*) AS between_5_6_5_percent,
            COUNT(CASE WHEN diemTrungBinh >= 6.5 AND diemTrungBinh < 8 THEN 1 END) * 100 / COUNT(*) AS between_6_5_8_percent,
            COUNT(CASE WHEN diemTrungBinh >= 8 AND diemTrungBinh < 9 THEN 1 END) * 100 / COUNT(*) AS between_8_9_percent,
            COUNT(CASE WHEN diemTrungBinh >= 9 THEN 1 END) * 100 / COUNT(*) AS above_9_percent
        FROM Diem d
        JOIN SinhVien sv ON d.maSV = sv.maSV
        WHERE sv.maNganh = ?
    """;

        // Kết nối đến cơ sở dữ liệu và thực thi truy vấn
        try (Connection conn = connect(); // Kết nối cơ sở dữ liệu
                 PreparedStatement pstmt = conn.prepareStatement(query)) { // Chuẩn bị câu truy vấn SQL

            // Gán giá trị của ngành học được chọn (selectedMajor) vào vị trí placeholder (?)
            pstmt.setString(1, selectedMajor);

            // Thực thi câu truy vấn và lấy kết quả
            try (ResultSet rs = pstmt.executeQuery()) {
                // Kiểm tra nếu có dữ liệu trả về
                if (rs.next()) {
                    // Lấy các giá trị phần trăm từ kết quả truy vấn
                    double under5 = rs.getDouble("under_5_percent"); // Dưới 5
                    double between5and6_5 = rs.getDouble("between_5_6_5_percent"); // Từ 5 đến 6.5
                    double between6_5and8 = rs.getDouble("between_6_5_8_percent"); // Từ 6.5 đến 8
                    double between8and9 = rs.getDouble("between_8_9_percent"); // Từ 8 đến 9
                    double above9 = rs.getDouble("above_9_percent"); // Trên 9

                    // Cập nhật biểu đồ cột với dữ liệu vừa lấy được
                    BarchartDAO2.showbarchartWithData(barchart, under5, between5and6_5, between6_5and8, between8and9, above9);
                }
            }
        } catch (Exception e) {
            // Xử lý lỗi nếu có vấn đề xảy ra trong quá trình kết nối hoặc thực thi
            e.printStackTrace(); // Hiển thị lỗi trên console
            javax.swing.JOptionPane.showMessageDialog(this, "Lỗi khi thống kê dữ liệu."); // Thông báo lỗi cho người dùng
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        piechart = new javax.swing.JPanel();
        barchart = new javax.swing.JPanel();
        cboboxNganh = new javax.swing.JComboBox<>();

        setPreferredSize(new java.awt.Dimension(962, 482));

        javax.swing.GroupLayout piechartLayout = new javax.swing.GroupLayout(piechart);
        piechart.setLayout(piechartLayout);
        piechartLayout.setHorizontalGroup(
            piechartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 345, Short.MAX_VALUE)
        );
        piechartLayout.setVerticalGroup(
            piechartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 316, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout barchartLayout = new javax.swing.GroupLayout(barchart);
        barchart.setLayout(barchartLayout);
        barchartLayout.setHorizontalGroup(
            barchartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 411, Short.MAX_VALUE)
        );
        barchartLayout.setVerticalGroup(
            barchartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 304, Short.MAX_VALUE)
        );

        cboboxNganh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboboxNganhActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(piechart, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(38, 38, 38)
                        .addComponent(barchart, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(42, 42, 42))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(cboboxNganh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(57, 57, 57))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cboboxNganh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(barchart, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(piechart, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(96, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cboboxNganhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboboxNganhActionPerformed
        String selectedMajor = (String) cboboxNganh.getSelectedItem();
        if (selectedMajor != null) {
            updateBarChart(selectedMajor);
        }
    }//GEN-LAST:event_cboboxNganhActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel barchart;
    private javax.swing.JComboBox<String> cboboxNganh;
    private javax.swing.JPanel piechart;
    // End of variables declaration//GEN-END:variables
}
