/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package assginmentjava3gd;

import java.sql.SQLException;
import Excel.PointExcel;
import DAO.PointDAO2;
import Model.Point2;
import Util.jdbcHelper;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ACER
 */
public class point2 extends javax.swing.JInternalFrame {

    /**
     * Creates new form point2
     */
    public point2() {
        initComponents();
        this.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        BasicInternalFrameUI ui = (BasicInternalFrameUI) this.getUI();
        ui.setNorthPane(null);
        loadStudentID();
        loadClassName();
        fillToTable();
        chinhjtable();
        chinhbutton();
    }

    public void chinhjtable() {
        // Tùy chỉnh giao diện JTable
        tblpoint.setFont(new Font("Segoe UI", Font.PLAIN, 16)); // chỉnh chữ
        tblpoint.setRowHeight(30);// chỉnh độ cao của bảng
        tblpoint.setGridColor(new Color(230, 230, 230));
        tblpoint.setBackground(new Color(245, 245, 245));
        tblpoint.setForeground(new Color(50, 50, 50));
        tblpoint.setSelectionBackground(new Color(0, 120, 215));
        tblpoint.setSelectionForeground(Color.WHITE);

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
        for (int i = 0; i < tblpoint.getColumnCount(); i++) {
            tblpoint.getColumnModel().getColumn(i).setHeaderRenderer(headerRenderer);
        }

        // Căn giữa nội dung các ô
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < tblpoint.getColumnCount(); i++) {
            tblpoint.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
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
                btnthem.setBackground(new Color(153, 255, 255)); // Trở lại màu nền ban đầu khi chuột ra khỏi button
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
                btnxoa.setBackground(new Color(153, 255, 255));
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
                btncapnhat.setBackground(new Color(153, 255, 255));
            }
        });
        // chỉnh màu và font chữ của btnreset
        jButton4.setFont(new Font("Segoe UI", Font.BOLD, 16));
        jButton4.setBackground(new Color(0, 153, 204));
        jButton4.setForeground(Color.black);
        jButton4.setPreferredSize(new Dimension(120, 40));
        jButton4.setBorder(BorderFactory.createLineBorder(new Color(0, 120, 215), 2));
        jButton4.setFocusPainted(false);
        jButton4.setOpaque(true);
        jButton4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jButton4.setBackground(new Color(0, 120, 215));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                jButton4.setBackground(new Color(153, 255, 255));
            }
        });

    }

    private void loadStudentID() {
        String query = getSelectStudentCodeQuery(); // Gọi câu lệnh SELECT từ phương thức khác
        try {
            // Sử dụng jdbcHelper để thực thi truy vấn và trả về ResultSet
            ResultSet rs = jdbcHelper.executeQuery(query); // Dùng ResultSet trực tiếp

            conbbxmasinhvien.removeAllItems(); // Xóa tất cả các mục hiện có trong ComboBox

            // Duyệt qua kết quả trong ResultSet
            while (rs.next()) {
                String maSV = rs.getString("maSV"); // Lấy mã sinh viên từ ResultSet
                conbbxmasinhvien.addItem(maSV); // Thêm maSV vào ComboBox
            }
        } catch (Exception e) {
            e.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(this, "Lỗi khi tải danh sách sinh viên.");
        }
    }

    private String getSelectStudentCodeQuery() {
        return "SELECT maSV FROM SinhVien"; // Sửa câu lệnh này tùy thuộc vào cơ sở dữ liệu của bạn
    }

    // phần trên là combobox lấy dữ liệu từ database á
    private void loadClassName() {
        String query = getSelectClassNameCodeQuery(); // Gọi câu lệnh SELECT từ phương thức khác
        try {
            // Sử dụng jdbcHelper để thực thi truy vấn và trả về ResultSet
            ResultSet rs = jdbcHelper.executeQuery(query); // Dùng ResultSet trực tiếp

            cbbClassName.removeAllItems(); // Xóa tất cả các mục hiện có trong ComboBox

            // Duyệt qua kết quả trong ResultSet
            while (rs.next()) {
                String className = rs.getString("tenLop"); // Lấy tên lớp từ ResultSet
                cbbClassName.addItem(className); // Thêm tên lớp vào ComboBox
            }

            // Đảm bảo đóng ResultSet sau khi sử dụng
            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(this, "Lỗi khi tải danh sách lớp.");
        }
    }

    // Phương thức để trả về câu lệnh SELECT
    private String getSelectClassNameCodeQuery() {
        return "SELECT tenLop  FROM lophoc"; // Sửa câu lệnh này tùy thuộc vào cơ sở dữ liệu của bạn
    }

    // code trên đã code vui lòng không được đụng vào 
    public void addpoint() throws Exception {
        // Kiểm tra các trường bắt buộc
        if (txtmadiem.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập mã điểm!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (txtdiemlab.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập điểm Lab!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (txtdiemthxuyen.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập điểm Thường Xuyên!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (txtdiemass.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập điểm Assignment!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Kiểm tra định dạng điểm (0 - 10)
        double diemLab, diemThuongXuyen, diemAssignment;
        try {
            diemLab = Double.parseDouble(txtdiemlab.getText().trim());
            diemThuongXuyen = Double.parseDouble(txtdiemthxuyen.getText().trim());
            diemAssignment = Double.parseDouble(txtdiemass.getText().trim());

            if (diemLab < 0 || diemLab > 10) {
                JOptionPane.showMessageDialog(this, "Điểm Lab phải nằm trong khoảng 0 - 10!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (diemThuongXuyen < 0 || diemThuongXuyen > 10) {
                JOptionPane.showMessageDialog(this, "Điểm Thường Xuyên phải nằm trong khoảng 0 - 10!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (diemAssignment < 0 || diemAssignment > 10) {
                JOptionPane.showMessageDialog(this, "Điểm Assignment phải nằm trong khoảng 0 - 10!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Điểm nhập vào phải là số hợp lệ!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Kiểm tra các combobox
        if (conbbxmasinhvien.getSelectedIndex() == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn mã sinh viên!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (coboboxmon.getSelectedIndex() == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn môn học!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Lấy dữ liệu từ giao diện
        String maDiem = txtmadiem.getText().trim();
        String maSV = (String) conbbxmasinhvien.getSelectedItem();
        String maMon = (String) coboboxmon.getSelectedItem();

        // Tính điểm trung bình
        double diemTrungBinh = (diemLab + diemThuongXuyen + diemAssignment) / 3;

        // Tính xếp loại
        String xepLoai = diemTrungBinh > 9 ? "Xuất sắc"
                : diemTrungBinh > 8 ? "Giỏi"
                        : diemTrungBinh >= 6.5 ? "Khá"
                                : diemTrungBinh >= 5 ? "Trung Bình" : "Yếu";

        // Tính trạng thái (điểm qua môn cần lấy từ database)
        PointDAO2 dao = new PointDAO2();
        double diemQuaMon = dao.getDiemQuaMon(maMon); // Phương thức này cần được thêm vào DAO
        String trangThai = diemTrungBinh >= diemQuaMon ? "Đạt" : "Rớt";

        // Kiểm tra mã điểm có tồn tại hay không
        if (dao.checkPointExists(maDiem)) {
            JOptionPane.showMessageDialog(this, "Mã điểm đã tồn tại, vui lòng nhập mã khác!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Tạo đối tượng Point2
        Point2 point = new Point2(maDiem, maSV, maMon, diemThuongXuyen, diemLab, diemAssignment, diemTrungBinh, xepLoai, trangThai);

        // Gọi phương thức thêm điểm
        boolean result = dao.addPoint(point);

        // Kiểm tra kết quả
        if (result) {
            fillToTable(); // Cập nhật bảng
            clearForm();   // Xóa dữ liệu trong form
        } else {
            JOptionPane.showMessageDialog(this, "Có lỗi xảy ra khi thêm điểm!", "Thông báo", JOptionPane.ERROR_MESSAGE);
        }
    }

// Hàm clearForm()
    private void clearForm() {
        txtmadiem.setText("");
        txtdiemlab.setText("");
        txtdiemthxuyen.setText("");
        txtdiemass.setText("");
        coboboxmon.setSelectedIndex(0);
        conbbxmasinhvien.setSelectedIndex(0);
    }

    public void updatepoint() throws Exception {
        // Kiểm tra các trường bắt buộc
        if (txtmadiem.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập mã điểm!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (txtdiemlab.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập điểm Lab!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (txtdiemthxuyen.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập điểm Thường Xuyên!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (txtdiemass.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập điểm Assignment!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Kiểm tra định dạng điểm (0 - 10)
        double diemLab, diemThuongXuyen, diemAssignment;
        try {
            diemLab = Double.parseDouble(txtdiemlab.getText().trim());
            diemThuongXuyen = Double.parseDouble(txtdiemthxuyen.getText().trim());
            diemAssignment = Double.parseDouble(txtdiemass.getText().trim());

            if (diemLab < 0 || diemLab > 10) {
                JOptionPane.showMessageDialog(this, "Điểm Lab phải nằm trong khoảng 0 - 10!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (diemThuongXuyen < 0 || diemThuongXuyen > 10) {
                JOptionPane.showMessageDialog(this, "Điểm Thường Xuyên phải nằm trong khoảng 0 - 10!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (diemAssignment < 0 || diemAssignment > 10) {
                JOptionPane.showMessageDialog(this, "Điểm Assignment phải nằm trong khoảng 0 - 10!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Điểm nhập vào phải là số hợp lệ!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Lấy dữ liệu từ giao diện
        String maDiem = txtmadiem.getText().trim();
        String maSV = (String) conbbxmasinhvien.getSelectedItem();
        String maMon = (String) coboboxmon.getSelectedItem();

        // Tính điểm trung bình
        double diemTrungBinh = (diemLab + diemThuongXuyen + diemAssignment) / 3;

        // Lấy điểm qua môn từ DAO
        PointDAO2 dao = new PointDAO2();
        double diemQuaMon = dao.getDiemQuaMon(maMon); // Phương thức này trả về điểm qua môn

        // Kiểm tra nếu điểm qua môn hợp lệ
        if (diemQuaMon < 0 || diemQuaMon > 10) {
            JOptionPane.showMessageDialog(this, "Điểm qua môn không hợp lệ!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Tính trạng thái
        String trangThai = diemTrungBinh >= diemQuaMon ? "Đạt" : "Rớt";

        // Xếp loại
        String xepLoai;
        if (diemTrungBinh >= 8) {
            xepLoai = "Giỏi";
        } else if (diemTrungBinh >= 6.5) {
            xepLoai = "Khá";
        } else if (diemTrungBinh >= 5) {
            xepLoai = "Trung Bình";
        } else {
            xepLoai = "Yếu";
        }

        // Cập nhật điểm
        Point2 point = new Point2(maDiem, maSV, maMon, diemThuongXuyen, diemLab, diemAssignment, diemTrungBinh, xepLoai, trangThai);

        // Gọi phương thức cập nhật điểm
        boolean result = dao.updatePoint(point);

        // Kiểm tra kết quả
        if (result) {
            JOptionPane.showMessageDialog(this, "Cập nhật điểm thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            fillToTable(); // Cập nhật bảng
            clearForm();   // Xóa dữ liệu trong form
        } else {
            JOptionPane.showMessageDialog(this, "Có lỗi xảy ra khi cập nhật điểm!", "Thông báo", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void deletepoint() throws SQLException {
        // Lấy danh sách các dòng được chọn từ bảng
        int[] selectedRows = tblpoint.getSelectedRows();

        // Kiểm tra nếu không có dòng nào được chọn
        if (selectedRows.length == 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn ít nhất một điểm để xóa!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return; // Dừng phương thức
        }

        // Hiển thị hộp thoại xác nhận xóa
        int choice = JOptionPane.showConfirmDialog(this,
                "Bạn có chắc chắn muốn xóa các điểm đã chọn?",
                "Xác nhận", JOptionPane.YES_NO_OPTION);

        // Nếu người dùng chọn "YES", thực hiện xóa
        if (choice == JOptionPane.YES_OPTION) {
            PointDAO2 dao = new PointDAO2(); // Tạo đối tượng DAO để xử lý cơ sở dữ liệu
            boolean hasError = false; // Đánh dấu lỗi nếu có vấn đề khi xóa

            // Duyệt qua danh sách các dòng được chọn (từ cuối lên đầu để tránh xung đột chỉ số)
            for (int i = selectedRows.length - 1; i >= 0; i--) {
                // Lấy mã điểm từ cột đầu tiên của dòng được chọn
                String maDiem = String.valueOf(tblpoint.getValueAt(selectedRows[i], 0));

                // Gọi DAO để xóa điểm trong cơ sở dữ liệu
                if (!dao.deletePoint(maDiem)) {
                    hasError = true; // Đánh dấu lỗi nếu không thể xóa
                }
            }

            // Cập nhật lại dữ liệu hiển thị trên bảng sau khi xóa
            fillToTable();

            // Kiểm tra xem bảng còn dữ liệu không
            if (tblpoint.getRowCount() > 0) {
                // Xác định dòng gần nhất để chọn (giới hạn trong số dòng hiện tại)
                int newindex = Math.min(selectedRows[0], tblpoint.getRowCount() - 1);

                // Chọn dòng mới trên bảng
                tblpoint.setRowSelectionInterval(newindex, newindex);

                // Đưa dữ liệu dòng được chọn lên các trường nhập liệu
                loadRowindexfield(newindex);
            } else {
                // Nếu bảng không còn dữ liệu, xóa sạch các trường nhập liệu
                clearForm();
            }

            // Hiển thị thông báo dựa trên kết quả
            if (hasError) {
                JOptionPane.showMessageDialog(this,
                        "Một số điểm không thể xóa do lỗi dữ liệu hoặc ràng buộc!",
                        "Thông báo", JOptionPane.WARNING_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this,
                        "Xóa điểm thành công!",
                        "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    private void fillToTable() {
        // Lấy mô hình bảng (DefaultTableModel) từ JTable
        DefaultTableModel model = (DefaultTableModel) tblpoint.getModel();

        // Xóa toàn bộ dữ liệu cũ trên bảng
        model.setRowCount(0);

        // Tạo đối tượng DAO để lấy dữ liệu
        PointDAO2 dao = new PointDAO2();
        List<Point2> points = dao.getAllPoints(); // Lấy danh sách điểm từ cơ sở dữ liệu

        // Kiểm tra danh sách không rỗng
        if (points == null || points.isEmpty()) {
            System.out.println("Không có dữ liệu để hiển thị.");
            return;
        }

        // Duyệt qua danh sách và thêm từng điểm vào bảng
        for (Point2 point : points) {
            model.addRow(new Object[]{
                point.getMaDiem(), // Mã điểm
                point.getMaSV(), // Mã sinh viên
                point.getMaMon(), // Mã môn
                point.getDiemTrungBinh(), // Điểm trung bình
                point.getXepLoai(), // Xếp loại
                point.getTrangThai() // Trạng thái
            });
        }
    }

    // click hiện lên cái textfield
    private void fillFormTable() {
        int selectedRow = tblpoint.getSelectedRow(); // Lấy chỉ số dòng được chọn trong bảng
        if (selectedRow >= 0) {
            try {
                // Lấy thông tin từ bảng
                String maDiem = tblpoint.getValueAt(selectedRow, 0).toString(); // Mã điểm
                String maSinhVien = tblpoint.getValueAt(selectedRow, 1).toString(); // Mã sinh viên
                String maMonHoc = tblpoint.getValueAt(selectedRow, 2).toString(); // Mã môn học
                String trangThai = tblpoint.getValueAt(selectedRow, 5).toString(); // Trạng thái

                // Điền dữ liệu vào các trường
                txtmadiem.setText(maDiem);
                conbbxmasinhvien.setSelectedItem(maSinhVien);
                coboboxmon.setSelectedItem(maMonHoc);

                // Sử dụng PointDAO2 để lấy chi tiết điểm
                PointDAO2 pointDao = new PointDAO2();
                Map<String, Double> detailPoints = pointDao.getDetailPoints(maDiem);

                // Điền các điểm chi tiết nếu tồn tại
                if (detailPoints != null && !detailPoints.isEmpty()) {
                    txtdiemthxuyen.setText(String.valueOf(detailPoints.getOrDefault("diemThuongXuyen", 0.0)));
                    txtdiemlab.setText(String.valueOf(detailPoints.getOrDefault("diemLab", 0.0)));
                    txtdiemass.setText(String.valueOf(detailPoints.getOrDefault("diemAssignment", 0.0)));
                } else {
                    // Làm trống nếu không có dữ liệu
                    txtdiemthxuyen.setText("");
                    txtdiemlab.setText("");
                    txtdiemass.setText("");
                }
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Lỗi khi điền dữ liệu: " + e.getMessage());
            }
        }
    }

    public void fillComboBoxMaSV() {
        String sql = "SELECT maSV FROM SinhVien";
        try {
            // Lấy danh sách mã sinh viên từ cơ sở dữ liệu
            List<String> studentIds = jdbcHelper.executeQuery(sql, rs -> rs.getString("maSV"));

            if (studentIds.isEmpty()) {
                // Thông báo nếu không có dữ liệu
                JOptionPane.showMessageDialog(this, "Không có dữ liệu sinh viên.");
            } else {
                // Xóa tất cả các mục hiện có trong ComboBox
                conbbxmasinhvien.removeAllItems();

                // Thêm các mã sinh viên vào ComboBox
                for (String maSV : studentIds) {
                    conbbxmasinhvien.addItem(maSV);
                }
            }
        } catch (Exception e) {
            // Xử lý ngoại lệ nếu có lỗi trong quá trình truy vấn
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi tải danh sách mã sinh viên.");
        }
    }

    public void cboMaSVActionPerformed() {
        String maSV = (String) conbbxmasinhvien.getSelectedItem();
        if (maSV != null) {
            // Lấy mã ngành từ mã sinh viên
            String maNganh = getMaNganhFromMaSV(maSV);
            if (maNganh != null) {
                // Sau khi có mã ngành, lấy các môn học từ mã ngành này
                fillMonHocByMaNganh(maNganh);
            }
        }
    }

    // Lấy mã ngành từ mã sinh viên
    private String getMaNganhFromMaSV(String maSV) {
        String maNganh = null;
        String sql = "SELECT maNganh FROM SinhVien WHERE maSV = ?";
        try {
            // Thực thi câu truy vấn và nhận ResultSet
            ResultSet rs = jdbcHelper.executeQuery(sql, maSV);

            // Duyệt qua kết quả trong ResultSet và lấy mã ngành
            if (rs.next()) {
                maNganh = rs.getString("maNganh");
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi lấy mã ngành từ mã sinh viên.");
        }
        return maNganh;  // Trả về mã ngành hoặc null nếu không tìm thấy
    }

    // Lấy danh sách môn học theo mã ngành
    private void fillMonHocByMaNganh(String maNganh) {
        coboboxmon.removeAllItems();  // Xóa danh sách môn học cũ
        String sql = "SELECT m.maMon FROM MonHocNganhHoc mn "
                + "JOIN MonHoc m ON mn.maMon = m.maMon WHERE mn.maNganh = ?";
        try {
            // Sử dụng jdbcHelper để thực thi câu truy vấn với tham số truyền vào
            ResultSet rs = jdbcHelper.executeQuery(sql, maNganh);

            // Duyệt qua kết quả và thêm mã môn vào ComboBox
            while (rs.next()) {
                coboboxmon.addItem(rs.getString("maMon"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(this, "Lỗi khi tải danh sách môn học.");
        }
    }

    private void updateStudentComboBox(String selectedClass) {
        conbbxmasinhvien.removeAllItems();  // Xóa dữ liệu cũ trong ComboBox
        String sql = "SELECT sv.maSV FROM SinhVien sv "
                + "JOIN LopHoc lh ON sv.maLop = lh.maLop WHERE lh.tenLop = ?";  // Lấy sinh viên theo lớp

        try {
            // Thực thi câu truy vấn và nhận kết quả trực tiếp từ ResultSet
            ResultSet rs = jdbcHelper.executeQuery(sql, selectedClass);  // Truyền tham số lớp học

            // Duyệt qua kết quả trong ResultSet và thêm vào ComboBox
            while (rs.next()) {
                String studentId = rs.getString("maSV");
                conbbxmasinhvien.addItem(studentId);  // Thêm maSV vào ComboBox
            }

            // Nếu có sinh viên, chọn một mã sinh viên mặc định
            if (conbbxmasinhvien.getItemCount() > 0) {
                conbbxmasinhvien.setSelectedIndex(0);  // Chọn mã sinh viên đầu tiên trong danh sách
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi tải danh sách sinh viên.");
        }
    }

    public void loadRowindexfield(int newrowintdex) {
        String maDiem = String.valueOf(tblpoint.getValueAt(newrowintdex, 0)); // Mã điểm
        String maSinhVien = String.valueOf(tblpoint.getValueAt(newrowintdex, 1)); // Mã sinh viên
        String maMon = String.valueOf(tblpoint.getValueAt(newrowintdex, 2)); // Mã môn

        txtmadiem.setText(maDiem);
        conbbxmasinhvien.setSelectedItem(maSinhVien);
        coboboxmon.setSelectedItem(maMon);

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel3 = new javax.swing.JLabel();
        btnxoa = new javax.swing.JButton();
        txtmadiem = new javax.swing.JTextField();
        txtdiemass = new javax.swing.JTextField();
        txtdiemthxuyen = new javax.swing.JTextField();
        btncapnhat = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblpoint = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtdiemlab = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        btnthem = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        coboboxmon = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        conbbxmasinhvien = new javax.swing.JComboBox<>();
        cbbClassName = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel3.setText("Subject ID");

        btnxoa.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnxoa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/9004852_trash_delete_bin_remove_icon.png"))); // NOI18N
        btnxoa.setText("Delete");
        btnxoa.setPreferredSize(new java.awt.Dimension(98, 37));
        btnxoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnxoaActionPerformed(evt);
            }
        });

        txtmadiem.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        txtdiemass.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        txtdiemthxuyen.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        btncapnhat.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btncapnhat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/8726496_upload_icon.png"))); // NOI18N
        btncapnhat.setText("Update");
        btncapnhat.setPreferredSize(new java.awt.Dimension(98, 37));
        btncapnhat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncapnhatActionPerformed(evt);
            }
        });

        jButton4.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/6296676_excel_microsoft_office_office365_icon.png"))); // NOI18N
        jButton4.setText("Export");
        jButton4.setPreferredSize(new java.awt.Dimension(98, 37));
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        tblpoint.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Point ID", "Student ID", "Subject ID", "Regular Point", "Classification", "Status"
            }
        ));
        tblpoint.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblpointMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblpoint);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel1.setText("Point ID");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel4.setText("Regular Points");

        txtdiemlab.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel2.setText("Student ID");

        btnthem.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnthem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/299068_add_sign_icon.png"))); // NOI18N
        btnthem.setText("Add");
        btnthem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnthemActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel5.setText("Lab Point");

        coboboxmon.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel6.setText("Assginments Point");

        conbbxmasinhvien.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        conbbxmasinhvien.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                conbbxmasinhvienMouseClicked(evt);
            }
        });
        conbbxmasinhvien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                conbbxmasinhvienActionPerformed(evt);
            }
        });

        cbbClassName.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "All" }));
        cbbClassName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbClassNameActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel7.setText("Class");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addComponent(btnthem, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
                .addComponent(btnxoa, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(btncapnhat, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(55, 55, 55)
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(68, 68, 68))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1))
            .addGroup(layout.createSequentialGroup()
                .addGap(88, 88, 88)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(conbbxmasinhvien, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtdiemthxuyen, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(65, 65, 65)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtdiemass, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(47, 47, 47)
                        .addComponent(txtmadiem, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(jLabel3)
                        .addGap(41, 41, 41)
                        .addComponent(coboboxmon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(65, 65, 65)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtdiemlab, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(26, 26, 26)
                .addComponent(jLabel7)
                .addGap(18, 18, 18)
                .addComponent(cbbClassName, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtdiemlab, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cbbClassName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel7))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel1)
                                .addComponent(txtmadiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel3)
                                .addComponent(coboboxmon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel5)))
                        .addGap(28, 28, 28)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel2)
                                .addComponent(conbbxmasinhvien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel4)
                                .addComponent(txtdiemthxuyen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel6)
                                .addComponent(txtdiemass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 395, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 5, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btncapnhat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnxoa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnthem))
                .addGap(384, 384, 384))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnthemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnthemActionPerformed
        try {
            addpoint();
        } catch (Exception ex) {
            Logger.getLogger(point2.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnthemActionPerformed

    private void btnxoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnxoaActionPerformed
        try {
            deletepoint();
        } catch (SQLException ex) {
            Logger.getLogger(point2.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnxoaActionPerformed

    private void btncapnhatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncapnhatActionPerformed
        try {
            updatepoint();
        } catch (Exception ex) {
            Logger.getLogger(point2.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btncapnhatActionPerformed

    private void tblpointMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblpointMouseClicked
        fillFormTable();
    }//GEN-LAST:event_tblpointMouseClicked

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        PointExcel.exportToExcel(tblpoint);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void conbbxmasinhvienMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_conbbxmasinhvienMouseClicked

    }//GEN-LAST:event_conbbxmasinhvienMouseClicked

    private void conbbxmasinhvienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_conbbxmasinhvienActionPerformed
        try {
            cboMaSVActionPerformed();
        } catch (Exception ex) {
            Logger.getLogger(point2.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_conbbxmasinhvienActionPerformed

    private void cbbClassNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbClassNameActionPerformed
        String selectedClass = (String) cbbClassName.getSelectedItem();
        updateStudentComboBox(selectedClass);
    }//GEN-LAST:event_cbbClassNameActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btncapnhat;
    private javax.swing.JButton btnthem;
    private javax.swing.JButton btnxoa;
    private javax.swing.JComboBox<String> cbbClassName;
    private javax.swing.JComboBox<String> coboboxmon;
    private javax.swing.JComboBox<String> conbbxmasinhvien;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblpoint;
    private javax.swing.JTextField txtdiemass;
    private javax.swing.JTextField txtdiemlab;
    private javax.swing.JTextField txtdiemthxuyen;
    private javax.swing.JTextField txtmadiem;
    // End of variables declaration//GEN-END:variables
}
