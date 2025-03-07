/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package assginmentjava3gd;

import DAO.ListDAO;
import DAO.StudentDAO2;
import Model.Student2;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import Excel.StudentExcel;
import Util.Auth;
import Util.jdbcHelper;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.util.Arrays;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author ACER
 */
public class student2 extends javax.swing.JInternalFrame {

    private final List<Student2> sinhvien = new ArrayList<>();
    private final ListDAO ldo;
    private TableRowSorter<TableModel> sorter;

    /**
     * Creates new form student2
     */
    public student2() {
        initComponents();
        this.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        BasicInternalFrameUI ui = (BasicInternalFrameUI) this.getUI();
        ui.setNorthPane(null);
        phanquyen();
        ldo = new ListDAO();
        loadClassNames();
        loadMajorID();
        fillToTable();
        chinhbutton();
        chinhjtable();
        setupComboBox();
        // Thiết lập TableRowSorter và JComboBox
        setupTableSorter((DefaultTableModel) tblSV.getModel(), tblSV);
    }
    public void phanquyen(){
        if (Auth.isAdmin()) {
            // Admin có toàn quyền, không cần hạn chế gì
            return;
        } else if (Auth.isTeacher()) {
            // Giảng viên chỉ có thể xem và chỉnh sửa nhưng không có quyền xóa
            btnxoa.setEnabled(false);
        } else if (Auth.isStudent()) {
            // Sinh viên chỉ có quyền xem, vô hiệu hóa tất cả các button
            btncapnhat.setEnabled(false);
            btnreset.setEnabled(false);
            btnthem.setEnabled(false);
            btnxoa.setEnabled(false);
        } 
    }
    public void chinhjtable() {
        // Tùy chỉnh giao diện JTable
        tblSV.setFont(new Font("Segoe UI", Font.PLAIN, 16)); // chỉnh chữ
        tblSV.setRowHeight(30);// chỉnh độ cao của bảng
        tblSV.setGridColor(new Color(230, 230, 230));
        tblSV.setBackground(new Color(245, 245, 245));
        tblSV.setForeground(new Color(50, 50, 50));
        tblSV.setSelectionBackground(new Color(0, 120, 215));
        tblSV.setSelectionForeground(Color.WHITE);

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
        for (int i = 0; i < tblSV.getColumnCount(); i++) {
            tblSV.getColumnModel().getColumn(i).setHeaderRenderer(headerRenderer);
        }

        // Căn giữa nội dung các ô
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < tblSV.getColumnCount(); i++) {
            tblSV.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
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
                jButton4.setBackground(new Color(0, 153, 204));
            }
        });
    }

    public JComboBox<String> getCboLop() {
        return cboLop;
    }

    // Phương thức để load danh sách lớp vào ComboBox
    private void loadClassNames() {
        String query = getSelectClassQuery(); // Lấy câu lệnh SELECT từ phương thức khác
        try {
            // Sử dụng jdbcHelper để thực thi truy vấn và trả về ResultSet
            ResultSet rs = jdbcHelper.executeQuery(query); // Dùng ResultSet trực tiếp

            cboLop.removeAllItems(); // Xóa tất cả các mục hiện có trong ComboBox

            // Duyệt qua kết quả trong ResultSet
            while (rs.next()) {
                String tenLop = rs.getString("tenLop"); // Lấy tên lớp từ ResultSet
                cboLop.addItem(tenLop); // Thêm tên lớp vào ComboBox
            }
        } catch (Exception e) {
            e.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(this, "Lỗi khi tải danh sách lớp.");
        }
    }

// Phương thức để trả về câu lệnh SELECT cho danh sách lớp
    private String getSelectClassQuery() {
        return "SELECT tenLop FROM LopHoc"; // Câu lệnh SELECT cho tên lớp
    }

// Phương thức để load danh sách mã ngành vào ComboBox
    private void loadMajorID() {
        String query = getSelectSubjectCodeQuery(); // Lấy câu lệnh SELECT từ phương thức khác
        try {
            // Sử dụng jdbcHelper để thực thi truy vấn và trả về ResultSet
            ResultSet rs = jdbcHelper.executeQuery(query); // Dùng ResultSet trực tiếp

            cboMajor.removeAllItems(); // Xóa tất cả các mục hiện có trong ComboBox

            // Duyệt qua kết quả trong ResultSet
            while (rs.next()) {
                String maNganh = rs.getString("maNganh"); // Lấy mã ngành từ ResultSet
                cboMajor.addItem(maNganh); // Thêm mã ngành vào ComboBox
            }
        } catch (Exception e) {
            e.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(this, "Lỗi khi tải danh sách ngành.");
        }
    }

// Phương thức để trả về câu lệnh SELECT cho danh sách mã ngành
    private String getSelectSubjectCodeQuery() {
        return "SELECT maNganh FROM nganhhoc"; // Câu lệnh SELECT cho mã ngành
    }

    // 
    public void addStudent2() {
        // Kiểm tra các trường bắt buộc 
        if (cboMajor.getSelectedIndex() == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn mã ngành học!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (txtTenSV.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập tên sinh viên!");
            return;
        }
        if (txtTuoi.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập tuổi sinh viên!");
            return;
        }
        try {
            int tuoi = Integer.parseInt(txtTuoi.getText());
            if (tuoi <= 0) {
                JOptionPane.showMessageDialog(this, "Tuổi phải lớn hơn 0!");
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Tuổi phải là một số hợp lệ!");
            return;
        }

        // Kiểm tra giới tính
        if (!rdbNam.isSelected() && !rdbNu.isSelected()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn giới tính!");
            return;
        }

        // Kiểm tra xem đã chọn lớp chưa
        if (cboLop.getSelectedIndex() == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn lớp học!");
            return;
        }

        // Lấy thông tin từ giao diện
        Student2 st = new Student2();
        st.setTensinhvien(txtTenSV.getText());
        st.setMaNganh((String) cboMajor.getSelectedItem());
        st.setGioitinh(rdbNam.isSelected()); // Nam: true, Nữ: false
        st.setTuoi(Integer.parseInt(txtTuoi.getText()));
        st.setTenLop((String) cboLop.getSelectedItem()); // Lấy tên lớp từ cboLop
        st.setMalop((String) cboLop.getSelectedItem()); // Cập nhật mã lớp theo tên lớp đã chọn

        // Kiểm tra mã lớp
        if (st.getMalop() == null || st.getMalop().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn lớp học!");
            return;
        }

        // Gọi phương thức thêm sinh viên
        StudentDAO2.addStudent(st);
        fillToTable();
        JOptionPane.showMessageDialog(this, "Thêm thành công");

    }

    //
    public void updateStudent2() {
        try {
            // Kiểm tra người dùng đã chọn hàng trong bảng hay chưa
            int selectedRow = tblSV.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một sinh viên để cập nhật!");
                return;
            }

            // Lấy mã sinh viên từ hàng được chọn
            String maSV = (String) tblSV.getValueAt(selectedRow, 0); // Giả sử mã sinh viên nằm ở cột 0

            // Kiểm tra thông tin hợp lệ
            if (txtTenSV.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập tên sinh viên!");
                return;
            }

            if (txtTuoi.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập tuổi!");
                return;
            }

            int tuoi;
            try {
                tuoi = Integer.parseInt(txtTuoi.getText());
                if (tuoi <= 0) {
                    JOptionPane.showMessageDialog(this, "Tuổi phải lớn hơn 0!");
                    return;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Tuổi phải là số nguyên hợp lệ!");
                return;
            }

            if (cboLop.getSelectedIndex() == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn mã lớp!");
                return;
            }

            if (cboMajor.getSelectedIndex() == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn mã ngành!");
                return;
            }

            if (!rdbNam.isSelected() && !rdbNu.isSelected()) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn giới tính!");
                return;
            }

            // Lấy thông tin từ các trường nhập liệu
            String tenSV = txtTenSV.getText();
            String tenLop = (String) cboLop.getSelectedItem(); // Lấy tên lớp từ ComboBox
            String maNganh = (String) cboMajor.getSelectedItem();
            boolean gioiTinh = rdbNam.isSelected();

            // Lấy mã lớp từ tên lớp
            String maLop = StudentDAO2.getMaLopFromTenLop(tenLop);
            if (maLop == null) {
                JOptionPane.showMessageDialog(this, "Mã lớp không tồn tại!");
                return;
            }

            // Tạo đối tượng Student2
            Student2 student = new Student2();
            student.setMasinhvien(maSV);  // Lấy mã sinh viên từ JTable
            student.setTensinhvien(tenSV);
            student.setMalop(maLop); // Sử dụng mã lớp lấy từ tên lớp
            student.setMaNganh(maNganh);
            student.setGioitinh(gioiTinh);
            student.setTuoi(tuoi);

            // Gọi phương thức updateStudent
            StudentDAO2 studentDAO = new StudentDAO2();
            boolean isUpdated = studentDAO.updateStudent(student);

            if (isUpdated) {
                JOptionPane.showMessageDialog(this, "Cập nhật thành công!");
                fillToTable(); // Reload lại dữ liệu trong bảng nếu cần
            } else {
                JOptionPane.showMessageDialog(this, "Cập nhật thất bại! Vui lòng kiểm tra lại thông tin.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Có lỗi xảy ra khi cập nhật sinh viên: " + e.getMessage());
        }
    }

    public void fillToTable() {
        DefaultTableModel model = (DefaultTableModel) tblSV.getModel();
        model.setRowCount(0); // Xóa dữ liệu hiện tại trên bảng

        // Truy vấn SQL với JOIN để lấy tên lớp
        String query = "SELECT sv.maSV, sv.tenSV, sv.maNganh, sv.gioiTinh, sv.tuoi, sv.maLop, lh.tenLop "
                + "FROM SinhVien sv "
                + "JOIN LopHoc lh ON sv.maLop = lh.maLop";

        try {
            // Sử dụng jdbcHelper để thực thi truy vấn SQL
            ResultSet rs = jdbcHelper.executeQuery(query);

            if (rs != null) {
                while (rs.next()) {
                    String maSV = rs.getString("maSV");         // Lấy mã sinh viên
                    String tenSV = rs.getString("tenSV");       // Lấy tên sinh viên
                    String maNganh = rs.getString("maNganh");   // Lấy mã ngành
                    boolean gioiTinh = rs.getBoolean("gioiTinh"); // Lấy giới tính
                    int tuoi = rs.getInt("tuoi");              // Lấy tuổi
                    String tenLop = rs.getString("tenLop");     // Lấy tên lớp

                    // Thêm một dòng mới vào JTable
                    model.addRow(new Object[]{
                        maSV,
                        tenSV,
                        maNganh,
                        gioiTinh ? "Nam" : "Nữ", // Hiển thị Nam hoặc Nữ
                        tuoi,
                        tenLop // Hiển thị tên lớp
                    });
                }
            } else {
                JOptionPane.showMessageDialog(this, "Không có dữ liệu sinh viên.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi tải dữ liệu từ cơ sở dữ liệu.");
        }
    }

    private void clearForm() {
        txtTenSV.setText(""); // Xóa tên sinh viên
        cboMajor.setSelectedIndex(-1); // Reset combobox 
        txtTuoi.setText(""); // Xóa tuổi
        rdbNam.setSelected(true); // Đặt giới tính mặc định là Nam
        cboLop.setSelectedIndex(0); // Reset combo box mã lớp về mục đầu tiên
    }

    private void fillFormTable() {  
        int selectedRow = tblSV.getSelectedRow(); // Lấy chỉ số dòng được chọn
        if (selectedRow >= 0) {
            // Lấy thông tin từ bảng và điền vào các trường nhập liệu
            txtTenSV.setText(tblSV.getValueAt(selectedRow, 1).toString());
            cboMajor.setSelectedItem(tblSV.getValueAt(selectedRow, 2).toString());
            cboLop.setSelectedItem(tblSV.getValueAt(selectedRow, 5).toString());
            boolean isNam = tblSV.getValueAt(selectedRow, 3).toString().equals("Nam");
            rdbNam.setSelected(isNam);
            rdbNu.setSelected(!isNam);
            txtTuoi.setText(tblSV.getValueAt(selectedRow, 4).toString());
        }
    }

    public void removeStudent() {
        int[] selectedRows = tblSV.getSelectedRows(); // Lấy tất cả các dòng được chọn

        if (selectedRows.length == 0) { // Nếu không có dòng nào được chọn
            JOptionPane.showMessageDialog(this,
                    "Chưa chọn hàng nào để xóa!",
                    "Thông báo",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        int choice = JOptionPane.showConfirmDialog(this,
                "Bạn có muốn xóa các sinh viên đã chọn?",
                "Xác nhận",
                JOptionPane.YES_NO_OPTION);

        if (choice == JOptionPane.YES_OPTION) {
            try {
                boolean hasError = false; // Để theo dõi xem có lỗi xảy ra không

                // Lặp qua các dòng được chọn
                for (int row : selectedRows) {
                    String maSinhVien = (String) tblSV.getValueAt(row, 0); // Lấy mã sinh viên từ bảng

                    // Xóa sinh viên khỏi cơ sở dữ liệu
                    boolean isDeleted = StudentDAO2.deleteST(maSinhVien);

                    if (isDeleted) {
                        // Xóa sinh viên khỏi danh sách `sinhvien`
                        sinhvien.removeIf(sv -> sv.getMasinhvien().equals(maSinhVien));
                    } else {
                        hasError = true; // Đánh dấu lỗi nếu không thể xóa sinh viên
                    }
                }

                // Cập nhật lại bảng
                fillToTable();

                // Hiển thị thông báo kết quả
                if (hasError) {
                    JOptionPane.showMessageDialog(this,
                            "Một số sinh viên không thể xóa do ràng buộc dữ liệu.",
                            "Cảnh báo",
                            JOptionPane.WARNING_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Xóa thành công tất cả sinh viên đã chọn!",
                            "Thông báo",
                            JOptionPane.INFORMATION_MESSAGE);
                }

                // Cập nhật giao diện sau khi xóa
                if (tblSV.getRowCount() > 0) { // Nếu vẫn còn dữ liệu
                    cleans(); // Xóa các field nếu cần
                }

            } catch (Exception e) {
                JOptionPane.showMessageDialog(this,
                        "Lỗi khi xóa sinh viên: " + e.getMessage(),
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void loadROwindexfield(int newRowIndex) {
        try {
            // Lấy thông tin từ hàng được chọn trong JTable
            String tenSV = tblSV.getValueAt(newRowIndex, 1).toString(); // Tên sinh viên
            String maLop = tblSV.getValueAt(newRowIndex, 2).toString(); // Mã lớp
            String maNganh = tblSV.getValueAt(newRowIndex, 3).toString(); // Mã ngành
            String gioiTinh = tblSV.getValueAt(newRowIndex, 4).toString(); // Giới tính
            int tuoi = (Integer) tblSV.getValueAt(newRowIndex, 5); // Tuổi là Integer

            // Cập nhật các trường nhập liệu khác
            txtTenSV.setText(tenSV);
            cboMajor.setSelectedItem(maNganh); // Chọn ngành từ cboMajor (ComboBox)
            txtTuoi.setText(String.valueOf(tuoi)); // Chuyển đổi tuổi thành chuỗi và hiển thị

            // Chọn đúng radio button cho giới tính
            if (gioiTinh.equalsIgnoreCase("Nam")) {
                rdbNam.setSelected(true);
            } else {
                rdbNu.setSelected(true);
            }

            // Chọn lớp từ mã lớp
            cboLop.setSelectedItem(maLop); // Chọn mã lớp từ ComboBox
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi tải dữ liệu sinh viên: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void cleans() {
        txtTenSV.setText("");
        txtTuoi.setText("");
        cboMajor.setSelectedIndex(-1); // Reset ComboBox
        cboLop.setSelectedIndex(-1);
        buttonGroup1.clearSelection(); // Bỏ chọn radio button
        txtsearch.setText("");
        JOptionPane.showMessageDialog(this, "Đã làm mới!");
    }
    // code của search nha 

    public void showData(List<Student2> students) {
        DefaultTableModel model = (DefaultTableModel) tblSV.getModel();
        model.setRowCount(0); // Xóa dữ liệu cũ
        for (Student2 student : students) {
            model.addRow(new Object[]{
                student.getMasinhvien(),
                student.getTensinhvien(),
                student.getMaNganh(),
                student.isGioitinh() ? "Nam" : "Nữ", // Chuyển boolean thành "Nam" hoặc "Nữ"
                student.getTuoi(),
                student.getTenLop()// Hiển thị tên lớp từ cơ sở dữ liệu
            });
        }
    }

    public void search() {
        String keyword = txtsearch.getText().trim(); // Lấy từ khóa và xóa khoảng trắng thừa
        if (keyword.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập từ khóa tìm kiếm!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        StudentDAO2 dao = new StudentDAO2(); // Tạo đối tượng DAO
        List<Student2> students = ldo.searchStudents(keyword); // Gọi phương thức tìm kiếm

        if (students.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy sinh viên nào!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        } else {
            showData(students); // Hiển thị dữ liệu lên bảng
        }
    }

    // SET DỮ LIỆU CHO COMBOBOX 
    public void setupComboBox() {
        // Tạo DefaultComboBoxModel với dữ liệu
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(new String[]{"A-Z", "Z-A"});
        cbbArrange.setModel(model);
    }

    // Thiết lập TableRowSorter cho JTable
    public void setupTableSorter(DefaultTableModel model, JTable table) {
        sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);
    }

    public void arrange() {
        String selected = (String) cbbArrange.getSelectedItem(); // Lấy lựa chọn từ ComboBox
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
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        buttonGroup1 = new javax.swing.ButtonGroup();
        btncapnhat = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblSV = new javax.swing.JTable();
        txtTenSV = new javax.swing.JTextField();
        txtTuoi = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        rdbNam = new javax.swing.JRadioButton();
        jLabel4 = new javax.swing.JLabel();
        rdbNu = new javax.swing.JRadioButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        btnthem = new javax.swing.JButton();
        btnxoa = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        cboLop = new javax.swing.JComboBox<>();
        cboMajor = new javax.swing.JComboBox<>();
        btnreset = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        txtsearch = new javax.swing.JTextField();
        btnsearch = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        cbbArrange = new javax.swing.JComboBox<>();

        jLabel1.setText("jLabel1");

        setBackground(new java.awt.Color(255, 255, 255));

        btncapnhat.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btncapnhat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/8726496_upload_icon.png"))); // NOI18N
        btncapnhat.setText("Update");
        btncapnhat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncapnhatActionPerformed(evt);
            }
        });

        jButton4.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/6296676_excel_microsoft_office_office365_icon.png"))); // NOI18N
        jButton4.setText("Export");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        tblSV.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Student ID", "Student Name", "Major ID", "Sex", "Age", "Class"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblSV.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblSVMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblSV);

        txtTenSV.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtTenSV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTenSVActionPerformed(evt);
            }
        });

        txtTuoi.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel3.setText("Student Name");

        rdbNam.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup1.add(rdbNam);
        rdbNam.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        rdbNam.setText("Male");
        rdbNam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdbNamActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel4.setText("Major ID");

        rdbNu.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup1.add(rdbNu);
        rdbNu.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        rdbNu.setText("Female");
        rdbNu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdbNuActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel5.setText("Sex");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel6.setText("Age");

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
        btnxoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnxoaActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel7.setText("Class");

        cboLop.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cboLop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboLopActionPerformed(evt);
            }
        });

        cboMajor.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        btnreset.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnreset.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/9856287_reset_reload_sync_update_icon.png"))); // NOI18N
        btnreset.setText("Reset");
        btnreset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnresetActionPerformed(evt);
            }
        });

        btnsearch.setText("Search");
        btnsearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnsearchActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setText("Arrange");

        cbbArrange.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cbbArrange.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbArrangeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(695, 695, 695)
                        .addComponent(jLabel8))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1045, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnthem, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnxoa, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btncapnhat, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnreset, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(77, 77, 77)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cboMajor, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTenSV, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(35, 35, 35)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtTuoi, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(43, 43, 43)
                                .addComponent(jLabel7)
                                .addGap(34, 34, 34)
                                .addComponent(cboLop, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(46, 46, 46)
                                .addComponent(txtsearch, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnsearch))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addGap(27, 27, 27)
                                .addComponent(rdbNam, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(rdbNu, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(45, 45, 45)
                                .addComponent(jLabel2)
                                .addGap(18, 18, 18)
                                .addComponent(cbbArrange, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(35, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtTuoi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(cboMajor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(cboLop, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtsearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnsearch))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txtTenSV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5)
                            .addComponent(rdbNam)
                            .addComponent(rdbNu)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(cbbArrange, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 6, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel8)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnthem)
                    .addComponent(btnxoa)
                    .addComponent(btnreset)
                    .addComponent(btncapnhat)
                    .addComponent(jButton4))
                .addContainerGap(56, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        StudentExcel.exportToExcel(tblSV);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void btnthemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnthemActionPerformed
        addStudent2();
    }//GEN-LAST:event_btnthemActionPerformed

    private void btncapnhatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncapnhatActionPerformed
        updateStudent2();
    }//GEN-LAST:event_btncapnhatActionPerformed

    private void tblSVMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSVMouseClicked
        fillFormTable();
    }//GEN-LAST:event_tblSVMouseClicked

    private void btnxoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnxoaActionPerformed
        removeStudent();
    }//GEN-LAST:event_btnxoaActionPerformed

    private void btnresetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnresetActionPerformed
        cleans();
    }//GEN-LAST:event_btnresetActionPerformed

    private void rdbNamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdbNamActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rdbNamActionPerformed

    private void rdbNuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdbNuActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rdbNuActionPerformed

    private void cboLopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboLopActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboLopActionPerformed

    private void txtTenSVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTenSVActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTenSVActionPerformed

    private void btnsearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnsearchActionPerformed
        search();
    }//GEN-LAST:event_btnsearchActionPerformed

    private void cbbArrangeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbArrangeActionPerformed
        arrange();
    }//GEN-LAST:event_cbbArrangeActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btncapnhat;
    private javax.swing.JButton btnreset;
    private javax.swing.JButton btnsearch;
    private javax.swing.JButton btnthem;
    private javax.swing.JButton btnxoa;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cbbArrange;
    private javax.swing.JComboBox<String> cboLop;
    private javax.swing.JComboBox<String> cboMajor;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JRadioButton rdbNam;
    private javax.swing.JRadioButton rdbNu;
    private javax.swing.JTable tblSV;
    private javax.swing.JTextField txtTenSV;
    private javax.swing.JTextField txtTuoi;
    private javax.swing.JTextField txtsearch;
    // End of variables declaration//GEN-END:variables

}
